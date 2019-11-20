/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.beans;

public class InterestRateBean {
	private int id;
	private String category;
	private String tenure;
	private String interestRateRegular;
    private String interestRateSenior;
    private String comments;

    public InterestRateBean(String category, String tenure, String interestRateRegular, String interestRateSenior) {
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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
}