/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2010, 2011. All Rights Reserved. 
 * 
 * Note to U.S. Government Users Restricted Rights:  Use, 
 * duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp.
 *******************************************************************************/

package com.jke.jkemq.model;

public class AccountUtilBean {

    public static final  String ACCOUNT_TYPE_Checking = "CB";
    public static final  String ACCOUNT_TYPE_Savings = "SB";
    public static final  String ACCOUNT_TYPE_FixedDeposit = "FD";

    public static final  String KYC_TYPE_Driver_License = "DL";
    public static final  String KYC_TYPE_Driver_SSN = "SSN";
    public static final  String KYC_TYPE_Driver_LeaseDoc = "LEASE";
    public static final  String KYC_TYPE_Driver_PAN = "PAN";
    public static final  String KYC_TYPE_Driver_Passport = "Passport";

    public static final  String NEW_ACCOUNT_STATUS_1_REQUESTED = "REQUESTED";
    public static final  String NEW_ACCOUNT_STATUS_2_APPROVED = "APPROVED";
    public static final  String NEW_ACCOUNT_STATUS_3_REJECTED = "REJECTED";
    public static final  String NEW_ACCOUNT_STATUS_4_CREATED = "CREATED";

    public static final  String ACCOUNT_STATUS_1_ACTIVE = "ACTIVE";

    public static final  String USER_ROLE_1_ACCOUNT_MANAGER = "ACCOUNT_MANAGER";
    public static final  String USER_ROLE_2_FRONT_OFFICE = "FRONT_OFFICE";
    public static final  String USER_ROLE_3_CUSTOMER = "CUSTOMER";

}