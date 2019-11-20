/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (c) Copyright IBM Corporation 2011. All Rights Reserved.
 *
 * Note to U.S. Government Users Restricted Rights:  
 * Use, duplication or disclosure restricted by GSA ADP Schedule 
 * Contract with IBM Corp. 
 *******************************************************************************/
dojo.provide("com.jke.widgets.Stock");

dojo.require("dijit._Widget");
dojo.require("dijit._Templated");
dojo.require("dijit.form.Select");
dojo.require("dijit.form.Button");
dojo.require("dojo.data.ItemFileReadStore");
dojo.require("dojo.currency");

dojo.declare("com.jke.widgets.Stock", [ dijit._Widget, dijit._Templated ], {

	// Path to the template
	templateString : dojo.cache("com.jke.widgets", "templates/Stock.html"),
	widgetsInTemplate : true,
	constructor : function(args) {
	},

	postCreate : function() {
        var i = 0;
        dojo.forEach(this.params.transactions, dojo.hitch(this, function(stock) {
            var tr, td;
            tr = dojo.create("tr", null, this.tableBody);
            if (i++ % 2 == 0)
                dojo.attr(tr, "style", "background-color: #efefef");

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.stockCode;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.stockName;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.currency;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.openPrice;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.curPrice;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.changeValue;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.changePercentage;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.high;

            td = dojo.create("td", null, tr);
            td.innerHTML = stock.low;
		}));

	}
});