/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2009, 2010. All Rights Reserved.
 * 
 * Note to U.S. Government Users Restricted Rights:  Use,
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.server;

import com.ibm.team.json.JSONArray;
import com.jke.beans.AccountTypeBean;
import com.jke.beans.ContributionBean;
import com.jke.beans.TransactionBean;
import com.jke.beans.UserBean;
import com.jke.logic.TransactionLogic;
import com.jke.logic.TransactionLogic.ProposedContributionResults;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
@WebServlet(urlPatterns = "/transactions/*", loadOnStartup = 1)
public class TransactionsResource extends HttpServlet {

	public TransactionsResource() {}

	/**
	 * GET ../transactions/<userid>/<accountTypeString> returns the list of
	 * transactions on the given account if the user and account are known, 404
	 * (NOT_FOUND) otherwise GET
	 * ../transactions/preview?account=<accountId>?org=<organisationName
	 * >&date=<startDate>&percent=<amountToAllocate> returns a preview of the
	 * transaction for the specified account and the specified transaction
	 * details, or 404 (NOT_FOUND) if the account or organization cannot be found
	 */
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);

		System.out.println("Transactions doGet URL :" + request.getRequestURI());

		List<String> paths= new ArrayList<String>();
		StringTokenizer st= new StringTokenizer(request.getRequestURI(), "/", false);
		while (st.hasMoreTokens())
			paths.add(st.nextToken());

		String action= getString(paths, 1);

        System.out.println("Transactions doGet action :" + action);


        if (paths.isEmpty() || action == null) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out= new PrintWriter(response.getOutputStream());
			out.println("Missing the user id in the request url");
			out.close();
		}

		TransactionLogic logic= new TransactionLogic();

		if ("preview".equals(action)) {

			ContributionBean contribution= new ContributionBean();
			contribution.setAccountNumber(Integer.parseInt(request.getParameter("account")));
			contribution.setOrganization(request.getParameter("org"));
			contribution.setDate(request.getParameter("date"));
			contribution.setPercentage(Double.valueOf(request.getParameter("percent")));
			ProposedContributionResults proposal= logic.getProposedContributionTransaction(contribution);
			TransactionBean t= proposal.trans;
			t.toJson().serialize(response.getWriter());
		} else {
			UserBean user= new UserBean(action, "", "");


			AccountTypeBean accountType= AccountTypeBean.valueOf(getString(paths, 2));

            System.out.println("Transactions doGet accountType :" + accountType);

			List<TransactionBean> transactions= logic.getTransactionHistory(user, accountType);
			JSONArray transObj= new JSONArray();
			for (TransactionBean t : transactions) {
				transObj.add(t.toJson());
                System.out.println(" doGet accountType Inside iteration :" + t.toJson());

            }
            System.out.println(" doGet accountType :" + accountType);

			transObj.serialize(response.getWriter());
		}
	}

	/**
	 * POST ../transactions/create?account=<accountNumber>&org=<organisationName>&
	 * date=<startDate>&percent=<amountToAllocate> creates a new transaction for
	 * the provided account and returns a summary of the transaction that was
	 * processed
	 */
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		response.setStatus(HttpServletResponse.SC_OK);
		ContributionBean contribution= new ContributionBean();
		contribution.setAccountNumber(Integer.parseInt(request.getParameter("account")));
		contribution.setOrganization(request.getParameter("org"));
		contribution.setDate(request.getParameter("date"));
		contribution.setPercentage(Double.valueOf(request.getParameter("percent")));

        System.out.println("Transactions doPost account :" + Integer.parseInt(request.getParameter("account")));
        System.out.println("Transactions doPost org :" + request.getParameter("org"));
        System.out.println("Transactions doPost date :" + request.getParameter("date"));
        System.out.println("Transactions doPost percent :" + request.getParameter("percent"));

		try {
			contribution.validate();
		} catch (IllegalArgumentException e) {
			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
			PrintWriter out= new PrintWriter(response.getOutputStream());
			out.println("Invalid contribution received: " + e.getMessage());
			out.close();
			return;
		}

		TransactionLogic logic= new TransactionLogic();

		try {
			TransactionBean t= logic.performContribution(contribution);
			t.toJson().serialize(response.getWriter());
		} catch (SQLException e) {
			response.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
			PrintWriter out= new PrintWriter(response.getOutputStream());
			out.println("Internal Server Error: " + e.getMessage());
			out.close();
			return;
		}
	}

	private String getString(List<String> strings, int index) {
		try {
			return strings.get(index);
		} catch (IndexOutOfBoundsException e) {
			return null;
		}
	}
}