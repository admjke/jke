/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2011. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:  
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 *******************************************************************************/
dojo.provide("com.jke.widgets.AccountListNew");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("com.jke.AccountTypeMap");
dojo.require("dojo.currency");

dojo.declare("com.jke.widgets.AccountListNew", [ dijit._Widget, dijit._Templated ], {
	// Path to the template
	templateString : dojo.cache("com.jke.widgets", "templates/AccountListNew.html"),
	widgetsInTemplate : true,
	userRole : "",
	messageType : "",

	constructor : function(args) {
	    if (com.jke.AppController.getLoggedInUser()) {
            this.userRole = com.jke.AppController.getLoggedInUser().roles;
            this.messageType =  args.messageType;
	    }
	},

	postCreate : function() {
		var i = 0;

        if (this.messageType === "create") {
            dojo.style(this.createdSuccessMsg, "display", "block");
        } else if (this.messageType === "approve") {
              dojo.style(this.approvedSuccessMsg, "display", "block");
        } else if (this.messageType === "reject") {
              dojo.style(this.rejectedSuccessMsg, "display", "block");
        } else if (this.messageType === "delete") {
              dojo.style(this.deletedSuccessMsg, "display", "block");
        }

		dojo.forEach(this.params.accounts, dojo.hitch(this, function(account) {

			var tr, td, a;
            var table2, tr2, td2, a2;

			tr = dojo.create("tr", null, this.tableBody);
			if (i++ % 2 == 0)
				dojo.attr(tr, "style", "background-color: #efefef");

            //  ------------- Col 1
			td = dojo.create("td", {style : "text-align: left"}, tr);
			table2 = dojo.create("table", {style : "text-align: left; vertical-align: top;"}, td);

            //Row 1
			tr2 = dojo.create("tr", null, table2);
            dojo.create("td", { innerHTML: account.userName }, tr2);

            //  ------------- Col 2
			td = dojo.create("td", {style : "text-align: left"}, tr);
			table2 = dojo.create("table", {style : "text-align: left; vertical-align: top;"}, td);

            //Row 1
			tr2 = dojo.create("tr", null, table2);
            //dojo.create("td", { innerHTML: "First Name" }, tr2);
            dojo.create("td", { innerHTML: account.firstName + " " + account.lastName}, tr2);


            //  ------------- Col 3
			td = dojo.create("td", {style : "text-align: left"}, tr);
			table2 = dojo.create("table", {style : "text-align: left; vertical-align: top;"}, td);

            //Row 3
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: account.companyName }, tr2);
            dojo.attr(td2, "title", "Company Name");

            //Row 4
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: account.officeAddress }, tr2);
            dojo.attr(td2, "title", "Office Address");

            //Row 1
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: account.mobileNo + " (" + account.alternateMobileNo + ")"}, tr2);
            dojo.attr(td2, "title", "Mobile");

            //Row 2
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: account.emailId }, tr2);
            dojo.attr(td2, "title", "Email");

            ///Row 5
            tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: account.homeAddress }, tr2);
            dojo.attr(td2, "title", "Home Address");


            //  ------------- Col 4
			td = dojo.create("td", {style : "text-align: left"}, tr);
			table2 = dojo.create("table", {style : "text-align: left; vertical-align: top;"}, td);

            // Row 1
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: "Account Type : " + account.accountType }, tr2);

             // Row 2
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: "KYC Documents: " + account.kycDocuments }, tr2);

            // Row 3
			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: "Status : " + account.status }, tr2);

             // Row 4
 			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: "Remarks : " + account.remarks }, tr2);

             // Row 5
 			tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", { innerHTML: "Roles : " + account.roles }, tr2);

             //  ------------- Col 5
			td = dojo.create("td", {style : "text-align: left"}, tr);
			table2 = dojo.create("table", {style : "text-align: left; vertical-align: top;"}, td);

            // Row 1
            tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", null, tr2);

            if ((account.status === "REQUESTED") && (this.userRole == "ACCOUNT_MANAGER")) {
                a = dojo.create("a", {href : "#", innerHTML : "Approve"}, td2);
                dojo.connect(a, "onclick", dojo.hitch(this, function(event) {
                    event.preventDefault();
                    var url = "approveAccount?id=" + account.id;
                    console.log("URL---->" + url);
                    dojo.xhrPost({
                        url : url,
                        handleAs : "text",
                        load : function(response, ioArgs) {
                            // navigate to the account list
                            com.jke.AppController.navigate({
                                state : "accountListNew",
                                messageType: "approve"
                            });
                        },
                        error : function(response, ioArgs) {
                            console.log("error createAccount  !!!!!!!" + response);
                        }
                    });
                }));
            }

            // Row 2
            tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", null, tr2);

            if ((account.status === "REQUESTED") && (this.userRole == "ACCOUNT_MANAGER")) {
                a = dojo.create("a", {href : "#", innerHTML : "Reject"}, td2);
                dojo.connect(a, "onclick", dojo.hitch(this, function(event) {
                   event.preventDefault();
                    var url = "rejectAccount?id=" + account.id;
                    dojo.xhrPost({
                        url : url,
                        handleAs : "text",
                        load : function(response, ioArgs) {
                            // navigate to the account list
                            com.jke.AppController.navigate({
                                state : "accountListNew",
                                messageType: "reject"
                            });
                        },
                        error : function(response, ioArgs) {
                            console.log("error rejectAccount  !!!!!!!" + response);
                        }
                    });
                }));
             }

            // Row 3
            tr2 = dojo.create("tr", null, table2);
            td2 = dojo.create("td", null, tr2);

            if ((account.status === "REQUESTED") && (this.userRole == "FRONT_OFFICE")) {
                a = dojo.create("a", {href : "#", innerHTML : "Modify"}, td2);
                dojo.connect(a, "onclick", dojo.hitch(this, function(event) {
                   event.preventDefault();
                    // navigate to the account list
                    com.jke.AppController.navigate({
                        state : "newAccountModify",
                        accountId: account.id,
                        temp:"gandhi"
                    });
                }));
            }
		}));
	}

});