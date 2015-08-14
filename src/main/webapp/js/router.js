"use strict";
window.APP = window.APP || {};
APP.Router = Backbone.Router.extend({
  routes: {
    "users/index": "index",
    "users/new": "create",
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
  },
  
  create: function () {
	  this.currentView = new APP.UserNewView({
	      users: this.users, user: new APP.UserModel()
	  });

	  $('#primary-content').html(this.currentView.render().el);
  }
});