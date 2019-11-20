/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010, 2011. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.jkemq.jpa;

import javax.persistence.*;

@Table(name = "JKENEWACCOUNT")
@Entity
public class NewAccount {

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;
    @Column(name = "USERNAME")
    private String userName;
    @Column(name = "FIRSTNAME")
    private String firstName;
    @Column(name = "LASTNAME")
    private String lastName;
    @Column(name = "PASSWORD")
    private String password;

    @Column(name = "COMPANYNAME")
    private String companyName;
    @Column(name = "HOMEADDRESS")
    private String homeAddress;
    @Column(name = "OFFICEADDRESS")
    private String officeAddress;
    @Column(name = "MOBILENO")
    private String mobileNo;
    @Column(name = "ALTERNATEMOBILENO")
    private String alternateMobileNo;
    @Column(name = "EMAILID")
    private String emailId;
    @Column(name = "ACCOUNTTYPE")
    private String accountType;
    @Column(name = "KYCDOCUMENTS")
    private String kycDocuments;
    @Column(name = "STATUS")
    private String status;
    @Column(name = "REMARKS")
    private String remarks;

    @Column(name = "ROLES")
    private String roles;


    @Transient
    private String requestId1;
    @Transient
    private String responseCode;
    @Transient
    private String responseMsg;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getOfficeAddress() {
        return officeAddress;
    }

    public void setOfficeAddress(String officeAddress) {
        this.officeAddress = officeAddress;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getAlternateMobileNo() {
        return alternateMobileNo;
    }

    public void setAlternateMobileNo(String alternateMobileNo) {
        this.alternateMobileNo = alternateMobileNo;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getKycDocuments() {
        return kycDocuments;
    }

    public void setKycDocuments(String kycDocuments) {
        this.kycDocuments = kycDocuments;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRoles() {
        return roles;
    }

    public void setRoles(String roles) {
        this.roles = roles;
    }

    public String getRequestId1() {
        return requestId1;
    }

    public void setRequestId1(String requestId) {
        this.requestId1= requestId;
    }

    public String getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(String responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMsg() {
        return responseMsg;
    }

    public void setResponseMsg(String responseMsg) {
        this.responseMsg = responseMsg;
    }

    @Override
    public String toString() {
        return "NewAccount{" +
                "id=" + id +
                ", userName='" + userName + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", companyName='" + companyName + '\'' +
                ", homeAddress='" + homeAddress + '\'' +
                ", officeAddress='" + officeAddress + '\'' +
                ", mobileNo='" + mobileNo + '\'' +
                ", alternateMobileNo='" + alternateMobileNo + '\'' +
                ", emailId='" + emailId + '\'' +
                ", accountType='" + accountType + '\'' +
                ", kycDocuments='" + kycDocuments + '\'' +
                ", status='" + status + '\'' +
                ", remarks='" + remarks + '\'' +
                ", roles='" + roles + '\'' +
//                ", requestId='" + requestId + '\'' +
//                ", responseCode='" + responseCode + '\'' +
//                ", responseMsg='" + responseMsg + '\'' +
                '}';
    }
}