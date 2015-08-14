"use strict";
window.APP = window.APP || {};
APP.Router = Backbone.Router.extend({
  routes: {
    "users/index": "index"
  },

  initialize: function (options) {
    this.users = options.users;

    this.index();
  },

  index: function () {
	var that = this;
	this.users.fetch({
        success: function(users) {
        	 that.currentView = new APP.UserIndexView({
        	    	users: users
        	    });
        	 $('#primary-content').html(that.currentView.render().el);
          }
        });
  }
});