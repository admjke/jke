/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010, 2011. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.jkeapi.jpa;


import javax.persistence.*;

@Table(name = "JKEACCOUNTS")
@Entity
public class Account {
    @Id
    @GeneratedValue
    @Column(name = "ACCOUNTNUMBER")
    private int accountnumber;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "ACCOUNTTYPE")
	private String accounttype;
    @Column(name = "BALANCE")
	private double balance;
    @Column(name = "DIVIDENDS")
	private double dividends;
    @Column(name = "DIVIDENDSETD")
	private double dividendsetd;
    @Column(name = "CONTRIBUTIONS")
	private double contributions;
    @Column(name = "CONTRIBUTIONSETD")
	private double contributionsetd;

    public int getAccountnumber() {
        return accountnumber;
    }

    public void setAccountnumber(int accountnumber) {
        this.accountnumber = accountnumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAccounttype() {
        return accounttype;
    }

    public void setAccounttype(String accounttype) {
        this.accounttype = accounttype;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getDividends() {
        return dividends;
    }

    public void setDividends(double dividends) {
        this.dividends = dividends;
    }

    public double getDividendsetd() {
        return dividendsetd;
    }

    public void setDividendsetd(double dividendsetd) {
        this.dividendsetd = dividendsetd;
    }

    public double getContributions() {
        return contributions;
    }

    public void setContributions(double contributions) {
        this.contributions = contributions;
    }

    public double getContributionsetd() {
        return contributionsetd;
    }

    public void setContributionsetd(double contributionsetd) {
        this.contributionsetd = contributionsetd;
    }
}