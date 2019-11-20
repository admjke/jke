/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2011. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:  
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 *******************************************************************************/
dojo.provide("com.jke.widgets.AccountList");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("com.jke.AccountTypeMap");
dojo.require("dojo.currency");

dojo.declare("com.jke.widgets.AccountList", [ dijit._Widget, dijit._Templated ], {
	// Path to the template
	templateString : dojo.cache("com.jke.widgets", "templates/AccountList.html"),
	widgetsInTemplate : true,
	constructor : function(args) {
	},

	postCreate : function() {
		var i = 0;
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
		}));
	}

});