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

@Table(name = "JKEINTERESTRATE")
@Entity
public class InterestRate {
    @Id
    @GeneratedValue
    private int id;
    private String category;
    private String tenure;
    @Column(name = "INTERESTRATEREGULAR")
    private String interestRateRegular;
    @Column(name = "INTERESTRATESENIOR")
    private String interestRateSenior;
    private String comments;

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
        this.tenure = tenure;
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