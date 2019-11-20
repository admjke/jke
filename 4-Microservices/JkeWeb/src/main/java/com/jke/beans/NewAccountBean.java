/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.beans;

import com.ibm.team.json.JSONObject;

import java.io.IOException;
import java.io.Reader;

public class NewAccountBean {

    private int id;
    private String userName;
	private String firstName;
	private String lastName;
	private String password;
	private String companyName;
	private String homeAddress;
	private String officeAddress;
	private String mobileNo;
	private String alternateMobileNo;
    private String emailId;
    private String accountType;
    private String kycDocuments;
    private String status;
    private String remarks;
    private String roles;


    public NewAccountBean() {
		//Default Constructor
	}

    public NewAccountBean(int id, String userName, String firstName, String lastName, String password, String companyName, String homeAddress, String officeAddress, String mobileNo, String alternateMobileNo, String emailId, String accountType, String kycDocuments, String status, String remarks, String roles) {
        this.id = id;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.companyName = companyName;
        this.homeAddress = homeAddress;
        this.officeAddress = officeAddress;
        this.mobileNo = mobileNo;
        this.alternateMobileNo = alternateMobileNo;
        this.emailId = emailId;
        this.accountType = accountType;
        this.kycDocuments = kycDocuments;
        this.status = status;
        this.remarks = remarks;
        this.roles = roles;
    }

    public JSONObject toJson() {
		JSONObject userObj= new JSONObject();
        userObj.put("id", getId());
        userObj.put("userName", getUserName());
        userObj.put("firstName", getFirstName());
        userObj.put("lastName", getLastName());

        userObj.put("password", getPassword());
        userObj.put("companyName", getCompanyName());
        userObj.put("homeAddress", getHomeAddress());
        userObj.put("officeAddress", getOfficeAddress());
        userObj.put("mobileNo", getMobileNo());
        userObj.put("alternateMobileNo", getAlternateMobileNo());
        userObj.put("emailId", getEmailId());
        userObj.put("accountType", getAccountType());
        userObj.put("kycDocuments", getKycDocuments());
        userObj.put("status", getStatus());
        userObj.put("remarks", getRemarks());
        userObj.put("roles", getRoles());

		return userObj;
	}

	public void deserializeFromJson(Reader r) throws IOException {
		JSONObject userObj= JSONObject.parse(r);
		setId((Integer) userObj.get("id"));
		setUserName((String) userObj.get("userName"));
		setFirstName((String) userObj.get("firstName"));
		setUserName((String) userObj.get("lastName"));

		setPassword((String) userObj.get("password"));
        setCompanyName((String) userObj.get("companyName"));
        setHomeAddress((String) userObj.get("homeAddress"));
        setOfficeAddress((String) userObj.get("officeAddress"));
        setMobileNo((String) userObj.get("mobileNo"));
        setAlternateMobileNo((String) userObj.get("alternateMobileNo"));
        setEmailId((String) userObj.get("emailId"));
        setAccountType((String) userObj.get("accountType"));
        setKycDocuments((String) userObj.get("kycDocuments"));
        setStatus((String) userObj.get("status"));
        setRemarks((String) userObj.get("remarks"));
        setRoles((String) userObj.get("roles"));
	}


    public int getId() {
        return id;
    }

    public void setId(int id) {
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
}