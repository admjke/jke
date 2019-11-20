/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
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
import java.sql.SQLException;
import java.util.List;

/**
 * The Class GenerateData. 
 * Comment rosenberg@us.ibm.com: This class contains DERBY specific SQL code 
 * for creating tables. Extracted methods for creating column string to allow
 * overriding in subclasses. 
 */
public class GenerateData {

	/** The user table. */
	public static String userTable= "JKEuser";

	/** The acct table. */
	public static String acctTable= "JKEaccounts";

	/** The trans table. */
	public static String transTable= "JKEtransactions";

	/** the contributions table. */
	public static String contribTable= "JKEcontributions";

	/** the unique keys table. */
	public static String keysTable= "JKEKeys";
	public static String contribKeyRowId= "Contrib";

    /** The interestRate table. */
    public static String interestRate= "JKEInterestRate";

    /** The New Account table. */
    public static String newAccount = "JKENewAccount";

    /** The interestRate FD 1 year table. */
    public static String interestRateFd1Year = "JKEINTERESTRATEFD1YEAR";

    /** The interestRate FD top 10 banks table. */
    public static String interestRateFdTop10 = "JKEINTERESTRATEFDTOP10";

	/** The db. */
	private JKE_DB_I db;

	/** The loader. */
	private BeanLoader loader;

	/**
	 * Instantiates a new generate data.
	 */
	public GenerateData() {
		
		db= JKE_DB_Factory.getFactory().getDB();
		loader= new BeanLoader();
	}

	/**
	 * Drop all database tables.
	 * 
	 * @return true, if successful
	 */
	public static boolean resetDatabaseToInitialState() {
	    System.out.println("resetDatabaseToInitialState 1");
		GenerateData gen= new GenerateData();
        System.out.println("resetDatabaseToInitialState 2");

        gen.dropTables();
        System.out.println("resetDatabaseToInitialState 3");

        return true;
	}

	/**
	 * Clear data.
	 */
	private void dropTables() {
        System.out.println("dropTables started");
		db.dropTable(userTable);
		db.dropTable(acctTable);
        db.dropTable(transTable);
        db.dropTable(contribTable);
        db.dropTable(keysTable);
        db.dropTable(interestRate);
        db.dropTable(interestRateFd1Year);
        db.dropTable(interestRateFdTop10);
        db.dropTable(newAccount);
        System.out.println("dropTables completed");
    }

	/**
	 * Generate tables and data for tables that do not exist yet.
	 * 
	 * @return true, if successful
	 */
	public boolean generateTablesAndInitialData() {
		try {
			generateKeys();
		} catch (SQLException e) {
			e.printStackTrace();
		}
        try {
            generateUsers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateAccounts();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateTransactions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateContributions();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateInterestRate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateInterestRateFd1Year();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateInterestRateFdTop10();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            generateNewAccount();
        } catch (SQLException e) {
            e.printStackTrace();
        }

		return true;
	}

	private void generateKeys() throws SQLException {
		if (db.createTable(keysTable, getKeysTableColumnString())) {
			// Key row for contributions
			String sql= "INSERT INTO JKEKeys (KeyID, NextKey) values ('" + contribKeyRowId + "', 1)";
			PreparedStatement ps= db.getConnection().prepareStatement(sql);
			ps.executeUpdate();
			ps.close();
		} else
			System.out.println("Table " + keysTable + " already exists.");
	}


    protected String getKeysTableColumnString() {
        return "(KeyID CHAR(8) PRIMARY KEY, NextKey INTEGER)";
    }


    /**
	 * Generate users.
	 * 
	 * @throws SQLException the sQL exception
	 */
	protected void generateUsers() throws SQLException {
		if (db.createTable(userTable, getUserTableColumnString())) {

		    loader.pushUserBean(new UserBean("jbrown", "Julie", "Brown", "jbrown", "Flames Brown Garden", "223, Greenford Rd, Greenford UB6 8SH, UK", "5000, Harrow Rd, Wembley HA0 2LW, UK", "123456789", "2345678901", "jbrownjbrown@gmail.com", "SB", "DL", AccountUtilBean.ACCOUNT_STATUS_1_ACTIVE, "", AccountUtilBean. USER_ROLE_1_ACCOUNT_MANAGER));
            loader.pushUserBean(new UserBean("peter", "Rita", "Peter", "peter", "White Corporation", "67, Oldfield Lane, Greenford, London UB6 9LB, UK", "1233, Greenford Rd, Greenford UB6 0UW, UK", "123456789", "2345678901", "rwhiterwhite@gmail.com", "SB", "SSN", AccountUtilBean.ACCOUNT_STATUS_1_ACTIVE, "", AccountUtilBean. USER_ROLE_2_FRONT_OFFICE));
            loader.pushUserBean(new UserBean("victor", "Patrice", "Victor", "victor", "Victor Food Lion", "33, Watford Rd, Wembley HA0 3EP, UK", "424, Harrow Rd, Wembley HA0 2HA, UK", "123456789", "2345678901", "pvinpvin@gmail.com", "SB", "SSN", AccountUtilBean.ACCOUNT_STATUS_1_ACTIVE, "", AccountUtilBean. USER_ROLE_3_CUSTOMER));
        } else
			System.out.println("Table " + userTable + " already exists.");
	}

	protected String getUserTableColumnString() {
		return "(FirstName varchar(40), LastName varchar(40), UserName varchar(40), Password varchar(40), PRIMARY KEY (UserName))";
	}

	/**
	 * Generate accounts.
	 * 
	 * @throws SQLException the sQL exception
	 */
	protected void generateAccounts() throws SQLException {
		if (db.createTable(acctTable, getAccountTableColumnString())) {
			loader.pushAccountBean(new AccountBean(0,2000.00, 1000.00, 5500.00, 0.00, 0.00, "jbrown", "Checking"));
			loader.pushAccountBean(new AccountBean( 0,12500.00, 500.00, 3500.00, 0.00, 0.00, "jbrown", "Savings"));
			loader.pushAccountBean(new AccountBean( 0,25.00, 500.00, 5.00, 0.00, 0.00, "jbrown", "IRA"));
			loader.pushAccountBean(new AccountBean( 0,500.00, 500.00, 35.00, 0.00, 0.00, "jbrown", "Money_Market"));

			loader.pushAccountBean(new AccountBean( 0,500.00, 500.00, 1000.00, 0.00, 0.00, "peter", "Checking"));
			loader.pushAccountBean(new AccountBean( 0,3400.00, 500.00, 100.00, 0.00, 0.00, "peter", "Savings"));
			loader.pushAccountBean(new AccountBean( 0,5500.00, 500.00, 50.00, 0.00, 0.00, "peter", "IRA"));
			loader.pushAccountBean(new AccountBean( 0,75.00, 500.00, 5.00, 0.00, 0.00, "peter", "Money_Market"));

            loader.pushAccountBean(new AccountBean( 0,2500.00, 2500.00, 21000.00, 0.00, 0.00, "victor", "Checking"));
            loader.pushAccountBean(new AccountBean( 0,23400.00, 2500.00, 2100.00, 0.00, 0.00, "victor", "Savings"));
            loader.pushAccountBean(new AccountBean( 0,25500.00, 2500.00, 250.00, 0.00, 0.00, "victor", "IRA"));
            loader.pushAccountBean(new AccountBean( 0,275.00, 2500.00, 25.00, 0.00, 0.00, "victor", "Money_Market"));
		} else
			System.out.println("Table " + acctTable + " already exists.");
	}

	protected String getAccountTableColumnString() {
		return "(AccountNumber INTEGER PRIMARY KEY, Balance DOUBLE, Dividends DOUBLE, DividendsETD DOUBLE, Contributions DOUBLE, ContributionsETD DOUBLE, UserName varchar(40) NOT NULL, AccountType VARCHAR(30) NOT NULL, UNIQUE(UserName, AccountType))";
	}


    /**
     * Generate InterestRate.
     *
     * @throws SQLException the sQL exception
     */
    protected void generateInterestRate() throws SQLException {
        if (db.createTable(interestRate, getInterestRateTableColumnString())) {
            loader.pushInterestRateBean(new InterestRateBean("FD", "7 days to 45 days", "5.75%", "6.25%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "46 days to 179 days", "6.25%", "6.75%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "180 days to 210 days", "6.35%", "6.85%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "211 days to 364 days", "6.40%", "6.90%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "1 year to 2 years", "6.80%", "7.30%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "2 years to 3 years", "6.80%", "7.30%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "3 years to 4 years", "6.80%", "7.30%"));
            loader.pushInterestRateBean(new InterestRateBean("FD", "5 years to 10 years", "6.85%", "7.35%"));

            loader.pushInterestRateBean(new InterestRateBean("RD", "1 Year", "6.70%", "7.00%"));
            loader.pushInterestRateBean(new InterestRateBean("RD", "2 Years", "6.75%", "7.05%"));
            loader.pushInterestRateBean(new InterestRateBean("RD", "3 Years", "6.80%", "7.10%"));
            loader.pushInterestRateBean(new InterestRateBean("RD", "4 Years", "6.80%", "7.10%"));
            loader.pushInterestRateBean(new InterestRateBean("RD", "5 Years", "6.85%", "7.15%"));
            loader.pushInterestRateBean(new InterestRateBean("RD", "5+ Years", "6.85%", "7.15%"));

            loader.pushInterestRateBean(new InterestRateBean("SB", "NA", "4.00%", "4.15%"));

        } else {
            System.out.println("Table " + interestRate + " already exists.");
        }
    }
    protected String getInterestRateTableColumnString() {
        return "(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), category varchar(40), tenure varchar(40), interestRateRegular varchar(40), interestRateSenior varchar(40), comments varchar(40))";
    }

    /**
     * Generate InterestRate Fd1Year.
     *
     * @throws SQLException the sQL exception
     */
    protected void generateInterestRateFd1Year() throws SQLException {
        if (db.createTable(interestRateFd1Year, getInterestRateFd1YearTableColumnString())) {
            loader.pushInterestRateFd1YearBean(new InterestRateFd1YearBean("Royal Bank of Scotland", "6 months to 8 months", "6.75%", "6.85%"));
            loader.pushInterestRateFd1YearBean(new InterestRateFd1YearBean("Lloyds Banking Group", "180 days to 269 days", "7.00%", "7.20%"));
            loader.pushInterestRateFd1YearBean(new InterestRateFd1YearBean("JKE Bank", "180 days to 210 days", "6.85%", "6.95%"));
            loader.pushInterestRateFd1YearBean(new InterestRateFd1YearBean("HSBC Holdings",  "61 days to 184 days", "6.35%", "6.35%"));
            loader.pushInterestRateFd1YearBean(new InterestRateFd1YearBean("NatWest", "46 days to 6 months", "6.25%", "6.75%"));
            loader.pushInterestRateFd1YearBean(new InterestRateFd1YearBean("Barclays PLC", "91 days to 180 days", "5.75%", "6.25%"));
        } else {
            System.out.println("Table " + interestRateFd1Year + " already exists.");
        }
    }
    protected String getInterestRateFd1YearTableColumnString() {
        return "(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), bank varchar(40), tenure varchar(40), interestRateRegular varchar(40), interestRateSenior varchar(40), comments varchar(40))";
    }


    /**
     * Generate InterestRate Fd Top 10.
     *
     * @throws SQLException the sQL exception
     */
    protected void generateInterestRateFdTop10() throws SQLException {
        if (db.createTable(interestRateFdTop10, getInterestRateFdTop10TableColumnString())) {
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("HSBC Holdings",  "7 days - 10 years", "5.35% - 8.45%", "6.35%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("JKE Bank", "7 days - 10 years", "5.75% - 7.35%", "7.20%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Barclays PLC", "7 days - 10 years", "5.15% - 7.45%", "6.25%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Royal Bank of Scotland", "7 days - 10 years", "5.75% - 7.50%", "6.85%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Lloyds Banking Group", "7 days - 10 years", "5.15% - 8.25%", "6.95%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Santander UK", "7 days - 10 years", "5.25% - 7.95%", "7.95%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Nationwide Building Society", "17 days - 10 years", "5.35% - 7.25%", "6.95%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Schroders", "7 days - 10 years", "5.45% - 7.85", "6.95%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Bank of america", "7 days - 10 years", "5.55% - 7.85%", "6.95%"));
            loader.pushInterestRateFdTop10Bean(new InterestRateFdTop10Bean("Standard Charted", "7 days - 10 years", "5.65% - 6.95%", "6.95%"));
        } else {
            System.out.println("Table " + interestRateFdTop10 + " already exists.");
        }
    }
    protected String getInterestRateFdTop10TableColumnString() {
        return "(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), bank varchar(40), tenure varchar(40), interestRateRegular varchar(40), interestRateSenior varchar(40), comments varchar(40))";
    }

    /**
	 * Generate transactions.
	 * 
	 * @throws SQLException the sQL exception
	 */
	protected void generateTransactions() throws SQLException {
		if (db.createTable(transTable, getTransactionTableColumnString())) {

            int accountNumber;
            List<Integer> list = loader.pullUserAccountNumbers("jbrown");
            if (list.size()==4) {
                 accountNumber = list.get(0);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 10  , 6700.00, "1/03/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 600.00, 6100.00, "1/05/2019"));
                loader.pushTransactionBean(new TransactionBean("Deposit", accountNumber, "Pay", 3000.00, 9100.00, "1/14/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Food", 100.00, 9000.00, "1/22/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Car", 7000.00, 2000.00, "1/25/2019"));

                accountNumber = list.get(1);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 100  , 10000, "2/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 500.00, 9500.00, "2/06/2019"));

                accountNumber = list.get(2);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 500  , 50000, "3/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 400.00, 4600.00, "3/02/2019"));

                accountNumber = list.get(3);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 200  , 20000, "1/02/2019"));
            }

            list = loader.pullUserAccountNumbers("peter");
            if (list.size()==4) {
                accountNumber = list.get(0);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 600  , 50000, "3/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 400.00, 4600.00, "1/03/2019"));

                accountNumber = list.get(1);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 300  , 30000, "1/02/2019"));

                accountNumber = list.get(2);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 1000  , 6700.00, "2/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 600.00, 6100.00, "2/03/2019"));
                loader.pushTransactionBean(new TransactionBean("Deposit", accountNumber, "Pay", 2000.00, 8100.00, "2/14/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Food", 100.00, 8000.00, "2/22/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Car", 4000.00, 4000.00, "2/25/2019"));

                accountNumber = list.get(3);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 200  , 10000, "3/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 1000.00, 9000.00, "3/03/2019"));
            }

            list = loader.pullUserAccountNumbers("victor");
            if (list.size()==4) {
                accountNumber = list.get(0);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 700  , 70000, "1/02/2019"));

                accountNumber = list.get(1);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 4000  , 8700.00, "2/03/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 600.00, 8100.00, "2/05/2019"));
                loader.pushTransactionBean(new TransactionBean("Deposit", accountNumber, "Pay", 2000.00, 10100.00, "2/14/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Food", 100.00, 10000.00, "2/22/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Car", 4000.00, 6000.00, "2/25/2019"));

                accountNumber = list.get(2);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 500  , 10000, "2/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 1000.00, 9000.00, "2/06/2019"));

                accountNumber = list.get(3);
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 200  , 60000, "3/02/2019"));
                loader.pushTransactionBean(new TransactionBean("Withdrawl", accountNumber, "Bills", 400.00, 5600.00, "3/06/2019"));
            }

        } else
			System.out.println("Table " + transTable + " already exists.");
	}

	protected String getTransactionTableColumnString() {
		return "(TransactionID INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), Type VARCHAR(30), AccountNumber INTEGER, Source VARCHAR(50), Amount DOUBLE, PostBalance DOUBLE, Date VARCHAR(20), ContributionID INTEGER, CHECK(((Type = 'Donation') AND (ContributionID IS NOT NULL)) OR ((Type <> 'Donation') AND (ContributionID IS NULL))), PRIMARY KEY (TransactionID))";
	}

	protected void generateContributions() throws SQLException {
		if (db.createTable(contribTable, getContributionsTableColumnString())) {
			// No Existing Data
		} else
			System.out.println("Table " + contribTable + " already exists.");
	}

	protected String getContributionsTableColumnString() {
		return "(ContributionID INTEGER PRIMARY KEY, AccountNumber INTEGER, Organization VARCHAR(50), Percentage DOUBLE, Date VARCHAR(20))";
	}


    /**
     * Generate NewAccount.
     *
     * @throws SQLException the sQL exception
     */
    protected void generateNewAccount() throws SQLException {
        if (db.createTable(newAccount, getNewAccountTableColumnString())) {
            loader.pushNewAccountBean(new NewAccountBean(100, "Scott", "David", "dscoot", "dscott", "ABC Inc", "Home NY", "Off NY", "123456789", "2345678901", "scottdavdaaa@gmail.com", "SB", "DL", AccountUtilBean.NEW_ACCOUNT_STATUS_1_REQUESTED, "", AccountUtilBean. USER_ROLE_3_CUSTOMER));
            loader.pushNewAccountBean(new NewAccountBean(101, "Alexy", "Vic", "valexy", "valexy", "XYZ Inc", "Home IL", "Off IL", "4567890123", "6789012345", "vicalexybbb@gmail.com", "CB", "SSN", AccountUtilBean.NEW_ACCOUNT_STATUS_1_REQUESTED, "", AccountUtilBean. USER_ROLE_3_CUSTOMER));
        } else {
            System.out.println("Table " + newAccount + " already exists.");
        }
    }

    protected String getNewAccountTableColumnString() {
        return "(id INTEGER GENERATED ALWAYS AS IDENTITY (START WITH 1000, INCREMENT BY 1), FirstName varchar(40), LastName varchar(40), UserName varchar(40), Password varchar(40), CompanyName varchar(100)," +  " HomeAddress varchar(100), OfficeAddress varchar(100), MobileNo varchar(15),  AlternateMobileNo varchar(15), EmailId varchar(40), AccountType varchar(5), KYCDocuments varchar(40), Status varchar(40), Remarks varchar(40), PRIMARY KEY (id))";
    }
}