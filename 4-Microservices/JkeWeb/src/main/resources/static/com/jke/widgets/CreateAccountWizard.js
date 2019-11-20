/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2011. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:  
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 *******************************************************************************/
// dojo.provide allows pages to use all of the types declared in this resource.
dojo.provide("com.jke.widgets.CreateAccountWizard");

// dojo.require the necessary dijit hierarchy
dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.Dialog");
dojo.require("dojox.widget.Wizard");
dojo.require("dijit.form.Select");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dijit.form.NumberTextBox");
dojo.require("dijit.form.TextBox");
dojo.require("dojo.currency");

dojo.require("com.jke.AccountTypeMap");
dojo.require("com.jke.AppController");

dojo.declare("com.jke.widgets.CreateAccountWizard", [ dijit._Widget, dijit._Templated ], {
	// Path to the template
	templateString : dojo.cache("com.jke.widgets", "templates/CreateAccountWizard.html"),

	// Set this to true if your widget contains other widgets
	widgetsInTemplate : true,
	user : {},
	accountIdToModify : "",
	account : {},

	constructor : function(args) {
        console.log("constructor  account 1");

		this.user = com.jke.AppController.getLoggedInUser();
        this.accountIdToModify = args.accountIdToModify;
        this.account = args.account;
        console.log("constructor  account 2 accountIdToModify->" + this.accountIdToModify);
	},

	postCreate : function() {

	    //Populate the text boxes with the values for Modify
	    if (this.account) {
            this.accountIdBox.setValue( this.accountIdToModify);
            this.userNameBox.setValue( this.account.userName);
            this.firstNameBox.setValue( this.account.firstName);
            this.lastNameBox.setValue( this.account.lastName);
            this.companyNameBox.setValue( this.account.companyName);
            this.homeAddressBox.setValue( this.account.homeAddress);
            this.officeAddressBox.setValue( this.account.officeAddress);
            this.mobileNoBox.setValue( this.account.mobileNo);
            this.alternateMobileNoBox.setValue( this.account.alternateMobileNo);
            this.emailIdBox.setValue( this.account.emailId);
            this.accountTypeBox.setValue( this.account.accountType);
            this.kycDocumentsBox.setValue( this.account.kycDocuments);
            this.remarksBox.setValue( this.account.remarks);

            dojo.style(this.createNewAccountHeader, "display", "none");
            dojo.style(this.modifyNewAccountHeader, "display", "block");
	    } else {
	        dojo.style(this.createNewAccountHeader, "display", "block");
	        dojo.style(this.modifyNewAccountHeader, "display", "none");
	    }


		this.donePane.doneFunction = dojo.hitch(this, function() {
			var userName = this.userNameBox.getValue();
			var firstName = this.firstNameBox.getValue();
			var lastName = this.lastNameBox.getValue();
			var balance = 10000;
			var accountId = this.accountIdBox.getValue();
			if (!accountId) {
			    accountId = 0;
			}
            var url = "saveNewAccount?accountId=" + accountId + "&userName=" + userName + "&firstName=" + firstName + "&lastName=" + lastName + "&balance=" + balance +
                        "&companyName="  + this.companyNameBox.getValue() + "&homeAddress="  + this.homeAddressBox.getValue() + "&officeAddress="  + this.officeAddressBox.getValue() +
                         "&mobileNo="  + this.mobileNoBox.getValue() +  "&alternateMobileNo="  + this.alternateMobileNoBox.getValue() +
                         "&emailId="  + this.emailIdBox.getValue() + "&accountType="  + this.accountTypeBox.getValue() + "&kycDocuments="  + this.kycDocumentsBox.getValue() +
                           "&remarks="  + this.remarksBox.getValue() + "&roles="  + ""  ;

			dojo.xhrPost({
				url : url,
				handleAs : "text",
				load : function(response, ioArgs) {
					// navigate to the account list
					com.jke.AppController.navigate({
						state : "accountListNew",
                        messageType: "create"
					});
				},
				error : function(response, ioArgs) {
				    console.log("error user creation !!!!!!!" + response);
				}
			});
		});

		this.createAccountPane.passFunction = dojo.hitch(this, function() {
		    //var passed = this.balanceBox.isInRange() && this.balanceBox.isValid();
			var response = this.validateData();
			var passed = response.boolValue1;

			if (passed) {
				dojo.style(this.errorMessage, "display", "none");
			} else {
				this.errorMessage.innerHTML =  response.value1;
    			dojo.style(this.errorMessage, "display", "block");
    		}

            this.userNameSummary.innerHTML = this.userNameBox.getValue();
			this.firstNameSummary.innerHTML = this.firstNameBox.getValue();
			this.lastNameSummary.innerHTML = this.lastNameBox.getValue();
			this.companyNameSummary.innerHTML = this.companyNameBox.getValue();
			this.homeAddressSummary.innerHTML = this.homeAddressBox.getValue();
			this.officeAddressSummary.innerHTML = this.officeAddressBox.getValue();
			this.mobileNoSummary.innerHTML = this.mobileNoBox.getValue();
			this.alternateMobileNoSummary.innerHTML = this.alternateMobileNoBox.getValue();
			this.emailIdSummary.innerHTML = this.emailIdBox.getValue();
			this.accountTypeSummary.innerHTML = this.accountTypeBox.getValue();
			this.kycDocumentsSummary.innerHTML = this.kycDocumentsBox.getValue();
			this.remarksSummary.innerHTML = this.remarksBox.getValue();
			return passed;
		});

		this.contWizard.cancelFunction = dojo.hitch(this, function() {
			// take them back to whereever they were before
			com.jke.AppController.reloadPage();
		});
	},

   validateData : function() {
        var result = {};

        console.log("validateData test 1 --->") ;

        dojo.xhrGet({
            url : "validUser/" + this.userNameBox.getValue(),
            sync : true,
            handleAs : "json",
            load : function(response, ioArgs) {
                console.log("validateData test 2 --->") ;
                result = response;
            },
            error : function(response, ioArgs) {
              console.log("validateData Error --->" + response);
              result = response;
            }
        });

        console.log("validateData test 3 --->") ;

        return result;
    }

});