/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.beans;

public class InterestRateFdTop10Bean {
	private int id;
	private String bank;
	private String tenure;
	private String interestRateRegular;
    private String interestRateSenior;
    private String comments;

    public InterestRateFdTop10Bean(String bank, String tenure, String interestRateRegular, String interestRateSenior) {
        this.bank = bank;
        this.tenure = tenure;
        this.interestRateRegular = interestRateRegular;
        this.interestRateSenior = interestRateSenior;
        this.comments = "";
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenure() {
        return tenure;
    }

    public void setTenure(String tenure) {
       tenure = tenure;
    }

    public String getInterestRateRegular() {
        return interestRateRegular;
    }

    public void setInterestRateRegular(String interestRateRegular) {
        this.interestRateRegular = interestRateRegular;
    }

    public String getInterestRateSenior() {
        return interestRateSenior;
    }

    public void setInterestRateSenior(String interestRateSenior) {
        this.interestRateSenior = interestRateSenior;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }
}