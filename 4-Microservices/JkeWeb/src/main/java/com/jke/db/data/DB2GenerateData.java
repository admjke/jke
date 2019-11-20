/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/
package com.jke.db.data;

/**
 * Overrides the Derby specific column creation of database tables.
 * 
 * @author Florian Rosenberg
 */
public class DB2GenerateData extends GenerateData {

	@Override
	protected String getTransactionTableColumnString() {
		return " (TransactionID INTEGER  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1) , Type VARCHAR(30), AccountNumber INTEGER, Source VARCHAR(50), Amount DOUBLE, PostBalance DOUBLE, Date VARCHAR(20), ContributionID INTEGER, PRIMARY KEY (TransactionID))";
	}

    protected String getUserTableColumnString() {
        return "(FirstName varchar(40), LastName varchar(40), UserName varchar(40)  NOT NULL, Password varchar(40), CompanyName varchar(100), HomeAddress varchar(100), OfficeAddress varchar(100), MobileNo varchar(15),  AlternateMobileNo varchar(15), EmailId varchar(40), AccountType varchar(5), KYCDocuments varchar(40), Status varchar(40), Remarks varchar(40), Roles varchar(100), PRIMARY KEY (UserName))";
    }
    protected String getKeysTableColumnString() {
        return "(KeyID CHAR(8) not null, NextKey INTEGER, PRIMARY KEY (KeyID))";
    }

    protected String getAccountTableColumnString() {
        return "(AccountNumber INTEGER not null GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1), Balance DOUBLE, Dividends DOUBLE, DividendsETD DOUBLE, Contributions DOUBLE, ContributionsETD DOUBLE, UserName varchar(40) NOT NULL, AccountType VARCHAR(30) NOT NULL, PRIMARY KEY (AccountNumber), UNIQUE(UserName, AccountType))";
    }

    protected String getContributionsTableColumnString() {
        return "(ContributionID INTEGER not null GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1), AccountNumber INTEGER, Organization VARCHAR(50), Percentage DOUBLE, Date VARCHAR(20), PRIMARY KEY (ContributionID))";
    }

    @Override
    protected String getInterestRateTableColumnString() {
        return " (id INTEGER  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1) , category VARCHAR(40), tenure VARCHAR(40), interestRateRegular VARCHAR(40), interestRateSenior VARCHAR(40), comments VARCHAR(40), PRIMARY KEY (id))";
    }
    @Override
    protected String getInterestRateFd1YearTableColumnString() {
        return " (id INTEGER  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1) , bank VARCHAR(40), tenure VARCHAR(40), interestRateRegular VARCHAR(40), interestRateSenior VARCHAR(40), comments VARCHAR(40), PRIMARY KEY (id))";
    }

    @Override
    protected String getInterestRateFdTop10TableColumnString() {
        return " (id INTEGER  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1) , bank VARCHAR(40), tenure VARCHAR(40), interestRateRegular VARCHAR(40), interestRateSenior VARCHAR(40), comments VARCHAR(40), PRIMARY KEY (id))";
    }

    protected String getNewAccountTableColumnString() {
        return "(id INTEGER  NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 500, INCREMENT BY 1), FirstName varchar(40), LastName varchar(40), UserName varchar(40), Password varchar(40), CompanyName varchar(100), HomeAddress varchar(100), OfficeAddress varchar(100), MobileNo varchar(15),  AlternateMobileNo varchar(15), EmailId varchar(40), AccountType varchar(5), KYCDocuments varchar(40), Status varchar(40), Remarks varchar(40),  Roles varchar(100), PRIMARY KEY (id))";
    }
}
