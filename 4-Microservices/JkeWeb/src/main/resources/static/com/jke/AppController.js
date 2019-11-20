/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2011. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:  
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 *******************************************************************************/
// dojo.provide allows pages to use all of the types declared in this resource.
dojo.provide("com.jke.AppController");

dojo.require("dojo.hash");
dojo.require("dojo.cookie");

dojo.require("com.jke.widgets.AccountSummary");
dojo.require("com.jke.widgets.DonationWizard");
dojo.require("com.jke.widgets.AccountDetails");
dojo.require("com.jke.widgets.AccountList");
dojo.require("com.jke.widgets.AccountListNew");
dojo.require("com.jke.widgets.InterestRate");
dojo.require("com.jke.widgets.Stock");
dojo.require("com.jke.widgets.CreateAccountWizard");
dojo.require("com.jke.widgets.LandingPage");
dojo.require("com.jke.widgets.TransactionHistory");
dojo.require("com.jke.widgets.StockQuote");


com.jke.AppController.login = function(name) {
	dojo.xhrGet({
		url : "/user/" + name,
		handleAs : "json",
		load : function(response, ioArgs) {
			if (response) {
				// Response format is { first, last, userId }
				dojo.cookie("JKEUser", dojo.objectToQuery(response));

				com.jke.AppController.navigate({
					state : "homePageAfterLogin"
				});
			}
		},
		error : function(response, ioArgs) {
			dojo.style("loginError", "display", "block");
		},
		handle : function(response, ioArgs) {
			// always clear the form, no matter what
			dijit.byId('loginForm').reset();
		}
	});
};

com.jke.AppController.logout = function() {
	// delete the login cookie
	dojo.cookie("JKEUser", null, {
		expires : -1
	});

	com.jke.AppController.navigate({
		state : "welcome"
	});
};

com.jke.AppController.navigate = function(state) {
   var obj = {
        state: state,
        messageType: ""
    };
    dojo.hash(dojo.objectToQuery(state));
	com.jke.AppController.reloadPage();
};

com.jke.AppController.navigateWithMessage = function(state, messageType) {
   var obj = {
        state: state,
        messageType: messageType
    };
	dojo.hash(dojo.objectToQuery(state));
	com.jke.AppController.reloadPage();
};

com.jke.AppController.reloadPage = function() {
	var hash = dojo.queryToObject(dojo.hash());
	var state = hash.state;
	var messageType = hash.messageType;
	var accountId = hash.accountId;

	var user = com.jke.AppController.getLoggedInUser();
	if (!state) {
		if (user) {
            if (user.roles.includes("ACCOUNT_MANAGER") || user.roles.includes("FRONT_OFFICE")) {
                // populate with the Account Summary page
                com.jke.AppController._showLoggedIn(user);
				com.jke.AppController._showAccountListNew(user, messageType);
            } else if (user.roles.includes("CUSTOMER")) {
                // populate with the Account Summary page
                com.jke.AppController._showLoggedIn(user);
                com.jke.AppController._showAccountSummary(user);
            }
		} else {
			com.jke.AppController.navigate({
				state : "welcome"
			});
		}
	} else if (state == "welcome") {
		// only go back to the landing page if they are not logged in, otherwise
		// just stay put
		if (!user) {
			// populate with the landing page
			com.jke.AppController._showLandingPage();
		}
	} else {
		if (!user) {
			// this is a strange state of affairs... the hash goes to a
			// restricted URL, but they are not logged in.
			// take 'em back to the welcome page
			com.jke.AppController.navigate({
				state : "welcome"
			});
		} else {

			if (state == "homePageAfterLogin") {
                com.jke.AppController._showLoggedIn(user);

                if (user.roles.includes("ACCOUNT_MANAGER")) {
				    //approveAccount (accountListNew)
				    com.jke.AppController._showAccountListNew(user, "");
                } else if (user.roles.includes("FRONT_OFFICE")) {
				    // accountListNew
				    com.jke.AppController._showAccountListNew(user, "");
                } else if (user.roles.includes("CUSTOMER")) {
                    //accountSummary
                    com.jke.AppController._showAccountSummary(user);
                }
			} else if (state == "accountSummary") {
				// populate with the Account Summary page
				com.jke.AppController._showLoggedIn(user);
				com.jke.AppController._showAccountSummary(user);
			} else if (state == "accountDetails") {
				// populate with the details page
				var accountNumber = hash.accountNumber;
				com.jke.AppController._showAccountDetails(user, null, accountNumber);
			} else if (state == "newAccountModify") {
				// populate with the details page
				var accountId = hash.accountId;
				com.jke.AppController._showNewAccountModify(user, accountId);
			} else if (state == "approveAccount") {
				// populate with the details page
				var id = hash.accountNumber;
				com.jke.AppController._showApproveAccounts(user, id);
            } else if (state == "accountList") {
				// populate with the accountList page
				com.jke.AppController._showAccountList(user);
            } else if (state == "accountListNew") {
				// populate with the new accountList page
                com.jke.AppController._showLoggedIn(user);
				com.jke.AppController._showAccountListNew(user, messageType);
            } else if (state == "interestRate") {
                // populate with the interestRate page
                com.jke.AppController.showInterestRateWizard(user);
			} else if (state == "contributeDividend") {
				var accountNumber = hash.accountNumber;
			} else if (state == "history") {
				var accountType = hash.accountType;
				com.jke.AppController._showHistory(user, accountType);
			} else if (state == "stockQuote") {
				com.jke.AppController._showStockQuoteForm();
			}else if (state == "stock") {
             	com.jke.AppController.showStockWizard();
            }
		}
	}
};

com.jke.AppController.getLoggedInUser = function() {
  	var userCookie = dojo.cookie("JKEUser");
  	if (userCookie) {
  		return dojo.queryToObject(userCookie);
  	} else {
  		return undefined;
  	}
};

com.jke.AppController.showDonationWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();
	var wiz = new com.jke.widgets.DonationWizard({
		userName : user.userName
	});
	wiz.placeAt("functionalArea", "only");
	wiz.startup();
};

com.jke.AppController.showAccountSummaryWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();
    com.jke.AppController._showAccountSummary(user);
};

com.jke.AppController.showCreateAccountWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();
    var widget = new com.jke.widgets.CreateAccountWizard({
        userName : user.userName
    });

    widget.placeAt("functionalArea", "only");
    widget.startup();
};

com.jke.AppController.showAccountListWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();

    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.AccountList({
            accounts : response
        });
        widget.placeAt("functionalArea", "only");
    };

    dojo.xhrGet({
        url : "usersWithRoleCustomer",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
        }
    });
};

com.jke.AppController.showAccountListNewWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();

    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.AccountListNew({
            accounts : response,
            messageType : ""
        });
        widget.placeAt("functionalArea", "only");
    };

    dojo.xhrGet({
        url : "newAccounts",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
        }
    });
};

com.jke.AppController.showInterestRateWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();

    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.InterestRate({
            transactions : response
        });
        widget.placeAt("functionalArea", "only");
    };
    dojo.xhrGet({
        url : "interestRates",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
            console.log(response);
        }
    });
};

com.jke.AppController.showStockWizard = function() {
	var user = com.jke.AppController.getLoggedInUser();

    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.Stock({
            transactions : response
        });
        widget.placeAt("functionalArea", "only");
    };
    dojo.xhrGet({
        url : "stock",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
            console.log(response);
        }
    });
};

com.jke.AppController.showServerInfo = function() {
	var user = com.jke.AppController.getLoggedInUser();

    var callback = function(response, ioArgs) {
        console.log("Server Info2 --->" + response.url);

        var serverInfoSpan = dojo.create("span", {
            id : "serverInfo",
            style : "color:white;",
            innerHTML : response.url
        });
        dojo.place(serverInfoSpan, "serverInfo", "replace");
    };

    dojo.xhrGet({
        url : "serverInfo",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
          console.log("Server Info Error --->" + response);
        }
    });
};


/* private methods below */
com.jke.AppController._showLandingPage = function() {

    com.jke.AppController._showHideForDefault();
    com.jke.AppController.showServerInfo();

    //Login
	dojo.style("loginContent", "display", "block");
	dojo.style("loggedInContent", "display", "none");

	// restore the functional area to the landing page content
	var landingPage = new com.jke.widgets.LandingPage();
	landingPage.placeAt("functionalArea", "only");

	// focus the username field
	dijit.byId("usernameBox").focus();
};

com.jke.AppController._showLoggedIn = function(user) {
	// login was successful
	dojo.style("loginError", "display", "none");

	if (user.roles) {
        if (user.roles.includes("ACCOUNT_MANAGER")) {
             com.jke.AppController._showHideForAccountManger(user);
        } else if (user.roles.includes("FRONT_OFFICE")) {
             com.jke.AppController._showHideForFrontOffice(user);
        } else if (user.roles.includes("CUSTOMER")) {
             com.jke.AppController._showHideForCustomer(user);
        } else {
             com.jke.AppController._showHideForDefault(user);
        }
	} else {
         com.jke.AppController._showHideForDefault(user);
    }

    //Login
	dojo.style("loginContent", "display", "none");
    dojo.style("loggedInContent", "display", "block");

	var usernameSpan = dojo.create("span", {
		id : "usersName",
		innerHTML : user.first + " " + user.last
	});
	dojo.place(usernameSpan, "usersName", "replace");
};

com.jke.AppController._showHideForAccountManger = function(user) {
    //Accounts Management
    dojo.style("accountManagementPortlet", "display", "block");
    dojo.style("openNewAccountLinkDiv", "display", "none");
    dojo.style("approveNewAccountsLinkDiv", "display", "block");
    dojo.style("viewNewAccountsLinkDiv", "display", "none");
    dojo.style("viewAccountsLinkDiv", "display", "block");

    //Transactions
    dojo.style("transactionsPortlet", "display", "none");

    //Information
	dojo.style("informationPortlet", "display", "none");

    dojo.style("accountAccessPortlet", "background", "#fff url(\"../images/titleBar_internal.png\") repeat-x bottom left");
    dojo.style("header", "background", "url(\"../images/jke_banner_internal.png\")");
    //dojo.style("bigBanner", "background", "url(\"../images/jke_header_1.png\")");
//    dojo.style("bigBanner", "display", "none");
};

com.jke.AppController._showHideForFrontOffice = function(user) {
    //Accounts Management
    dojo.style("accountManagementPortlet", "display", "block");
    dojo.style("openNewAccountLinkDiv", "display", "block");
    dojo.style("approveNewAccountsLinkDiv", "display", "none");
    dojo.style("viewNewAccountsLinkDiv", "display", "block");
    dojo.style("viewAccountsLinkDiv", "display", "none");

    //Transactions
    dojo.style("transactionsPortlet", "display", "none");

    //Information
	dojo.style("informationPortlet", "display", "none");

    dojo.style("accountAccessPortlet", "background", "#fff url(\"../images/titleBar_internal.png\") repeat-x bottom left");
    dojo.style("header", "background", "url(\"../images/jke_banner_internal.png\")");
    //dojo.style("bigBanner", "background", "url(\"../images/jke_header_2.png\")");
//    dojo.style("bigBanner", "display", "none");
};

com.jke.AppController._showHideForCustomer = function(user) {
    //Accounts Management
    dojo.style("accountManagementPortlet", "display", "none");

    //Transactions
    dojo.style("transactionsPortlet", "display", "block");

    //Information
	dojo.style("informationPortlet", "display", "block");

    dojo.style("accountAccessPortlet", "background", "#fff url(\"../images/titleBar_external.png\") repeat-x bottom left");
    dojo.style("header", "background", "url(\"../images/jke_banner_external.png\")");
    //dojo.style("bigBanner", "background", "url(\"../images/jke_header_3.png\")");
//    dojo.style("bigBanner", "display", "none");
};

com.jke.AppController._showHideForDefault = function(user) {
    //Accounts Management
    dojo.style("accountManagementPortlet", "display", "none");

    //Transactions
    dojo.style("transactionsPortlet", "display", "none");

    //Information
	dojo.style("informationPortlet", "display", "none");

    dojo.style("accountAccessPortlet", "background", "#fff url(\"../images/titleBar_default.png\") repeat-x bottom left");
    dojo.style("header", "background", "url(\"../images/jke_banner_default.png\")");
//    dojo.style("bigBanner", "background", "url(\"../images/jke_header_0.png\") repeat no-repeat");
//    dojo.style("bigBanner", "display", "block");
};

com.jke.AppController._showAccountSummary = function(user) {
	com.jke.AppController._showLoggedIn(user);

	// load up this user's account summary
	var accountSummary = new com.jke.widgets.AccountSummary({
		url : "user/" + user.userId + "/accounts"
	});
	accountSummary.placeAt("functionalArea", "only");
};

com.jke.AppController._showAccountList = function(user) {
    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.AccountList({
            accounts : response
        });
        widget.placeAt("functionalArea", "only");
    };

    dojo.xhrGet({
        url : "users",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
        }
    });
};

com.jke.AppController._showAccountListNew = function(user, messageTypeIn) {

	com.jke.AppController._showLoggedIn(user);

    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.AccountListNew({
            accounts : response,
            messageType : messageTypeIn
        });
        widget.placeAt("functionalArea", "only");
    };

    dojo.xhrGet({
        url : "newAccounts",
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
        }
    });
};


com.jke.AppController._showNewAccountModify = function(user, accountId) {

	com.jke.AppController._showLoggedIn(user);

    var callback = function(response, ioArgs) {
        var widget = new com.jke.widgets.CreateAccountWizard({
            userName : user.userName,
            account : response,
            accountIdToModify : accountId
        });
        widget.placeAt("functionalArea", "only");
        widget.startup();
    };

    dojo.xhrGet({
        url : "newAccount/" + accountId,
        handleAs : "json",
        load : callback,
        error : function(response, ioArgs) {
            console.log(response);
        }
    });
};

com.jke.AppController._showAccountDetails = function(user, accounts, accountNumber) {
	com.jke.AppController._showLoggedIn(user);

	// define callback out here to enclose the accountNumber var
	var callback = function(response, ioArgs) {
		var accountDetails = new com.jke.widgets.AccountDetails({
			accounts : response,
			initialSelection : accountNumber
		});
		accountDetails.placeAt("functionalArea", "only");
	};

	dojo.xhrGet({
		url : "user/" + user.userId + "/accounts",
		handleAs : "json",
		load : callback,
		error : function(response, ioArgs) {
		}
	});
};


com.jke.AppController._showHistory = function(user, accountType) {
	com.jke.AppController._showLoggedIn(user);

	var callback = function(response, ioArgs) {
		var widget = new com.jke.widgets.TransactionHistory({
			transactions : response,
			accountType : accountType
		});
		widget.placeAt("functionalArea", "only");
	};

	dojo.xhrGet({
		url : "transactions/" + user.userId + "/" + accountType,
		handleAs : "json",
		load : callback,
		error : function(response, ioArgs) {
		}
	});
};

com.jke.AppController._showStockQuoteForm = function() {

	// load up the Stock Quote form
	var stockQuoteWidget = new com.jke.widgets.StockQuote();
	stockQuoteWidget.placeAt("functionalArea", "only");
};