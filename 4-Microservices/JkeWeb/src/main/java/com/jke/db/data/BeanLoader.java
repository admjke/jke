/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010, 2011. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.db.data;

import com.jke.beans.*;
import com.jke.db.connection.JKE_DB_Factory;
import com.jke.db.connection.JKE_DB_I;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class BeanLoader.
 */
public class BeanLoader {

	/** The db. */
	private JKE_DB_I db;

	/**
	 * Instantiates a new bean loader.
	 */
	public BeanLoader() {
		db= JKE_DB_Factory.getFactory().getDB();
	}

	/**
	 * Push user bean.
	 * 
	 * @param b the b
	 * @throws SQLException the sQL exception
	 */
	public void pushUserBean(UserBean b) throws SQLException {
		String sql = "INSERT INTO " + GenerateData.userTable
				+ " (FirstName, LastName, UserName, Password, CompanyName, HomeAddress, OfficeAddress, MobileNo, AlternateMobileNo, EmailId, AccountType, KYCDocuments, Status, Remarks, Roles)"
				+ " values (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setString(1, b.getFirstName());
			ps.setString(2, b.getLastName());
			ps.setString(3, b.getUserName());
            ps.setString(4, b.getPassword());
            ps.setString(5, b.getCompanyName());
            ps.setString(6, b.getHomeAddress());
            ps.setString(7, b.getOfficeAddress());
            ps.setString(8, b.getMobileNo());
            ps.setString(9, b.getAlternateMobileNo());
            ps.setString(10, b.getEmailId());
            ps.setString(11, b.getAccountType());
            ps.setString(12, b.getKycDocuments());
            ps.setString(13, b.getStatus());
            ps.setString(14, b.getRemarks());
            ps.setString(15, b.getRoles());
            ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("User created: " + b.getFirstName() + " "
				+ b.getLastName());
	}
    /**
     * Push NewAccount bean.
     *
     * @param b the b
     * @throws SQLException the sQL exception
     */
    public void pushNewAccountBean(NewAccountBean b) throws SQLException {
        String sql = "INSERT INTO " + GenerateData.newAccount
                + " (FirstName, LastName, UserName, Password, CompanyName, HomeAddress, OfficeAddress, MobileNo, AlternateMobileNo, EmailId, AccountType, KYCDocuments, Status, Remarks, Roles)"
                + " values (?, ?, ?, ?, ?,  ?, ?, ?, ?, ?,  ?, ?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, b.getFirstName());
            ps.setString(2, b.getLastName());
            ps.setString(3, b.getUserName());
            ps.setString(4, b.getPassword());
            ps.setString(5, b.getCompanyName());
            ps.setString(6, b.getHomeAddress());
            ps.setString(7, b.getOfficeAddress());
            ps.setString(8, b.getMobileNo());
            ps.setString(9, b.getAlternateMobileNo());
            ps.setString(10, b.getEmailId());
            ps.setString(11, b.getAccountType());
            ps.setString(12, b.getKycDocuments());
            ps.setString(13, b.getStatus());
            ps.setString(14, b.getRemarks());
            ps.setString(15, b.getRoles());

            ps.executeUpdate();
        } finally {
            if (ps != null)
                ps.close();
        }
        System.out.println("New account created: " + b.getFirstName() + " "
                + b.getLastName());
    }

	/**
	 * Push account bean.
	 * 
	 * @param b the b
	 * @throws SQLException the sQL exception
	 */
	public void pushAccountBean(AccountBean b) throws SQLException {
		String sql = "INSERT INTO "
				+ GenerateData.acctTable
				+ " (Balance, Dividends, DividendsETD, Contributions, ContributionsETD, UserName, AccountType)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setDouble(1, b.getBalance());
			ps.setDouble(2, b.getDividends());
			ps.setDouble(3, b.getDividendsETD());
			ps.setDouble(4, b.getContributions());
			ps.setDouble(5, b.getContributionsETD());
			ps.setString(6, b.getUserName());
			ps.setString(7, b.getType());
			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("Account Created: " + b.getAccountNumber());
	}

	/**
	 * Returns the account for the given user and type. Returns null if it does
	 * not exist.
	 * 
	 * @param user
	 * @param acctType
	 * @return
	 * @throws SQLException
	 */
	public AccountBean pullAccountBean(UserBean user, AccountTypeBean acctType) throws SQLException {
		String sql = "SELECT * FROM " + GenerateData.acctTable
				+ " WHERE UserName = '" + user.getUserName()
				+ "' AND AccountType = '" + acctType.toString() + "'";

		System.out.println("sql -->" + sql);

		ResultSet rs = null;
		Statement s = null;
		AccountBean bean = null;
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(sql);
			if (rs.next()) {
				bean = createAccountBean(rs);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (s != null)
				s.close();
		}
		return bean;
	}

	/**
	 * Returns the account for the given account number. Returns null if it does
	 * not exist.
	 * 
	 * @param acctNumber
	 * @return
	 * @throws SQLException
	 */
	public AccountBean pullAccountBean(int acctNumber) throws SQLException {
		String sql = "SELECT * FROM " + GenerateData.acctTable
				+ " WHERE AccountNumber = " + acctNumber;

		ResultSet rs = null;
		Statement s = null;
		AccountBean bean = null;
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(sql);
			if (rs.next()) {
				bean = createAccountBean(rs);
			}
		} finally {
			if (rs != null)
				rs.close();
			if (s != null)
				s.close();
		}
		return bean;
	}

	/**
	 * Pull account beans for the given user.
	 * 
	 * @param user the user
	 * @return the list
	 * @throws SQLException the sQL exception
	 */
	public List<AccountBean> pullUserAccountBeans(UserBean user) throws SQLException {
		String sql = "SELECT * FROM " + GenerateData.acctTable
				+ " WHERE UserName = " + "'" + user.getUserName() + "'";

		
		ResultSet rs = null;
		Statement s = null;
		List<AccountBean> list = new ArrayList<AccountBean>();
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(sql);

			while (rs.next()) {
				list.add(createAccountBean(rs));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (s != null)
				s.close();
		}
		return list;
	}

    /**
     * Pull account beans for the given user.
     *
     * @param userName the user
     * @return the list
     * @throws SQLException the sQL exception
     */
    public List<Integer> pullUserAccountNumbers(String userName) throws SQLException {
        String sql = "SELECT * FROM " + GenerateData.acctTable
                + " WHERE UserName = " + "'" + userName + "'";


        ResultSet rs = null;
        Statement s = null;
        List<Integer> list = new ArrayList<>();
        try {
            s = db.getConnection().createStatement();
            rs = s.executeQuery(sql);

            while (rs.next()) {
               list.add(rs.getInt("AccountNumber"));
            }
        } finally {
            if (rs != null)
                rs.close();
            if (s != null)
                s.close();
        }
        return list;
    }

	private AccountBean createAccountBean(ResultSet rs) throws SQLException {
		return new AccountBean(rs.getInt("AccountNumber"), rs.getDouble("Balance"), rs.getDouble("Dividends"), rs.getDouble("DividendsETD"), rs.getDouble("Contributions"), rs.getDouble("ContributionsETD"), rs.getString("UserName"), rs.getString("AccountType"));
	}

	/**
	 * Update account.
	 * 
	 * @param b the b
	 * @throws SQLException the sQL exception
	 */
	public void updateAccount(AccountBean b) throws SQLException {
		String sql = "UPDATE " + GenerateData.acctTable + " SET Balance = "
				+ b.getBalance() + ", Dividends = " + b.getDividends()
				+ ", DividendsETD = " + b.getDividendsETD()
				+ ", Contributions = " + b.getContributions()
				+ ", ContributionsETD = " + b.getContributionsETD()
				+ " WHERE AccountNumber = " + b.getAccountNumber();
		Statement s = null;
		try {
			s = db.getConnection().createStatement();
			s.executeUpdate(sql);
		} finally {
			if (s != null)
				s.close();
		}
	}

	/**
	 * Push transaction bean for a normal, non-Donation, transaction.
	 * 
	 * @param trans the transaction
	 * @throws SQLException the sQL exception
	 */
	public void pushTransactionBean(TransactionBean trans) throws SQLException {
		String sql = "INSERT INTO " + GenerateData.transTable
				+ " (Type, AccountNumber, Source, Amount, PostBalance, Date)"
				+ " values (?, ?, ?, ?, ?, ?)";
		
		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setString(1, trans.getTransactionType());
			ps.setInt(2, trans.getAccountNumber());
			ps.setString(3, trans.getSource());
			ps.setDouble(4, trans.getAmount());
			ps.setDouble(5, trans.getPostBalance());
			ps.setString(6, trans.getDate());
			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
		}
		System.out.println("Transaction created: " + trans.getAccountNumber());
	}

	/**
	 * Push transaction bean for a contribution. For repeating contributions,
	 * multiple transaction records will refer to the same contribution. For one
	 * time contributions, only one transaction will refer to the contribution.
	 * 
	 * @param trans the transaction
	 * @throws SQLException the sQL exception
	 */
	public void pushTransactionBean(TransactionBean trans, int contributionID) throws SQLException {
		String sql = "INSERT INTO "
				+ GenerateData.transTable
				+ " (Type, AccountNumber, Source, Amount, PostBalance, Date, ContributionID)"
				+ " values (?, ?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = null;
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setString(1, trans.getTransactionType());
			ps.setInt(2, trans.getAccountNumber());
			ps.setString(3, trans.getSource());
			ps.setDouble(4, trans.getAmount());
			ps.setDouble(5, trans.getPostBalance());
			ps.setString(6, trans.getDate());
			ps.setInt(7, contributionID);
			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
		}
		trans.setTransactionID(countTransactionBeans());
		System.out.println("Transaction created: " + trans.getAccountNumber());
	}

	/**
	 * Pull transaction beans for the specified account.
	 * 
	 * @param accountNumber the account number to pull from
	 * @return a list of transactions
	 * @throws SQLException the sQL exception
	 */
	public List<TransactionBean> pullTransactionBeans(int accountNumber) throws SQLException {
		String sql = "SELECT * FROM " + GenerateData.transTable
				+ " WHERE AccountNumber = " + accountNumber;

		ResultSet rs = null;
		Statement s = null;
		List<TransactionBean> list = new ArrayList<TransactionBean>();
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(sql);

			while (rs.next()) {
				list.add(new TransactionBean(rs.getInt("TransactionID"), rs
						.getString("Type"), rs.getInt("AccountNumber"), rs
						.getString("Source"), rs.getDouble("Amount"), rs
						.getDouble("PostBalance"), rs.getString("Date")));
			}
		} finally {
			if (rs != null)
				rs.close();
			if (s != null)
				s.close();
		}
		return list;
	}
	
	public int countTransactionBeans() throws SQLException {
		String sql = "SELECT * FROM " + GenerateData.transTable;
		
		ResultSet rs = null;
		Statement s = null;
		int count = 0;
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(sql);
			while (rs.next()) {
				count++;
			}
		} finally {
			if (rs != null)
				rs.close();
			if (s != null)
				s.close();
		}
		return count;
	}

	/**
	 * @param b
	 * @throws SQLException
	 */
	public int pushContributionBean(ContributionBean b) throws SQLException {
		String sql = "INSERT INTO "
			+ GenerateData.contribTable
			+ " (AccountNumber, Organization, Percentage, Date)"
			+ " values (?, ?, ?, ?)";

		System.out.println("pushContributionBean Sql" + sql);

		PreparedStatement ps = null;
		int myKey = getKey(GenerateData.contribKeyRowId);
		try {
			ps = db.getConnection().prepareStatement(sql);
			ps.setInt(1, b.getAccountNumber());
			ps.setString(2, b.getOrganization());
			ps.setDouble(3, b.getPercentage());
			ps.setString(4, b.getDate());
			ps.executeUpdate();
		} finally {
			if (ps != null)
				ps.close();
		}
		return myKey;
	}

	/*
	 * Retrieves the next integer key for the named key type. The next key value
	 * in the database is incremented for the next caller.
	 */
	private synchronized int getKey(String keyID) throws SQLException {
		PreparedStatement tps = null;
		ResultSet trs= null;
		try {
			tps= db.getConnection().prepareStatement("SELECT KeyID, NextKey FROM " + GenerateData.keysTable + " WHERE KeyID = '" + keyID + "'", ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_UPDATABLE);
			trs = tps.executeQuery();
			trs.next();
			int myKey= trs.getInt(2);
			int nextKey= myKey + 1;
			trs.updateInt(2, nextKey);
			trs.updateRow();
			return myKey;
		} finally {
			if (tps != null)
				tps.close();
			if (trs != null)
				trs.close();
		}
	}

	public UserBean pullUserBean(String u, String p) {
		String sql = "SELECT * FROM " + GenerateData.userTable
				+ " WHERE UserName = '" + u + "' ";
		if (p != null) {
			sql += " AND Password = '" + p + "'";
		}

		ResultSet rs = null;
		Statement s = null;
		UserBean b = null;
		try {
			s = db.getConnection().createStatement();
			rs = s.executeQuery(sql);
			if (rs.next()) {
				b = new UserBean(rs.getString("UserName"),
                        rs.getString("FirstName"),
                        rs.getString("LastName"),
                        rs.getString("password"),
                        rs.getString("companyName"),
                        rs.getString("homeAddress"),
                        rs.getString("officeAddress"),
                        rs.getString("mobileNo"),
                        rs.getString("alternateMobileNo"),
                        rs.getString("emailId"),
                        rs.getString("accountType"),
                        rs.getString("kycDocuments"),
                        rs.getString("status"),
                        rs.getString("remarks"),
                        rs.getString("roles")
                        );
			}
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				if (rs != null)
					rs.close();
				if (s != null)
					s.close();
			} catch (SQLException e) {
            }
		}

		return b;
	}



    /**
     * Push Interest rates .
     *
     * @param bean the interest rate
     * @throws SQLException the sQL exception
     */
    public void pushInterestRateBean(InterestRateBean bean) throws SQLException {
        String sql = "INSERT INTO " + GenerateData.interestRate
                + " (category, tenure, interestRateRegular, interestRateSenior, comments)"
                + " values (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, bean.getCategory());
            ps.setString(2, bean.getTenure());
            ps.setString(3, bean.getInterestRateRegular());
            ps.setString(4, bean.getInterestRateSenior());
            ps.setString(5, bean.getComments());
            ps.executeUpdate();
        } finally {
            if (ps != null)
                ps.close();
        }
        System.out.println("InterestRates created: " + bean.getId());
    }

    /**
     * Push Interest rates Fd1Year.
     *
     * @param bean the interest rate
     * @throws SQLException the sQL exception
     */
    public void pushInterestRateFd1YearBean(InterestRateFd1YearBean bean) throws SQLException {
        String sql = "INSERT INTO " + GenerateData.interestRateFd1Year
                + " (bank, tenure, interestRateRegular, interestRateSenior, comments)"
                + " values (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, bean.getBank());
            ps.setString(2, bean.getTenure());
            ps.setString(3, bean.getInterestRateRegular());
            ps.setString(4, bean.getInterestRateSenior());
            ps.setString(5, bean.getComments());
            ps.executeUpdate();
        } finally {
            if (ps != null)
                ps.close();
        }
        System.out.println("InterestRates FD 1 year created: " + bean.getId());
    }

    /**
     * Push Interest rates FdTop10.
     *
     * @param bean the interest rate
     * @throws SQLException the sQL exception
     */
    public void pushInterestRateFdTop10Bean(InterestRateFdTop10Bean bean) throws SQLException {
        String sql = "INSERT INTO " + GenerateData.interestRateFdTop10
                + " (bank, tenure, interestRateRegular, interestRateSenior, comments)"
                + " values (?, ?, ?, ?, ?)";

        PreparedStatement ps = null;
        try {
            ps = db.getConnection().prepareStatement(sql);
            ps.setString(1, bean.getBank());
            ps.setString(2, bean.getTenure());
            ps.setString(3, bean.getInterestRateRegular());
            ps.setString(4, bean.getInterestRateSenior());
            ps.setString(5, bean.getComments());
            ps.executeUpdate();
        } finally {
            if (ps != null)
                ps.close();
        }
        System.out.println("InterestRates FD Top 10 created: " + bean.getId());
    }

}
