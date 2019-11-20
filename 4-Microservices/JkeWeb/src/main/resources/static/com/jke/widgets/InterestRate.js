/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2011. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:  
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 *******************************************************************************/
dojo.provide("com.jke.widgets.InterestRate");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.currency");

dojo.declare("com.jke.widgets.InterestRate", [ dijit._Widget, dijit._Templated ], {

	// Path to the template
	templateString : dojo.cache("com.jke.widgets", "templates/InterestRate.html"),
	widgetsInTemplate : true,
	constructor : function(args) {
	},

	postCreate : function() {
        var i = 0;
        dojo.forEach(this.params.transactions, dojo.hitch(this, function(interestRate) {
            var tr, td;
            tr = dojo.create("tr", null, this.tableBody);
            if (i++ % 2 == 0)
                dojo.attr(tr, "style", "background-color: #efefef");

            td = dojo.create("td", null, tr);
            td.innerHTML = interestRate.category;

            td = dojo.create("td", null, tr);
            td.innerHTML = interestRate.tenure;

            td = dojo.create("td", null, tr);
            td.innerHTML = interestRate.interestRateRegular;

            td = dojo.create("td", null, tr);
            td.innerHTML = interestRate.interestRateSenior;
		}));

	}
});