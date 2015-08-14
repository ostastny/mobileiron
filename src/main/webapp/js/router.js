"use strict";
window.APP = window.APP || {};
APP.Router = Backbone.Router.extend({
  routes: {
    "users/index": "index",
    "users/new": "create",
    "users/:id/view": "show",
    "users/:id/edit": "edit"
  },

  initialize: function (options) {
    this.users = options.users;

    this.index();
  },

  index: function () {
	var self = this;
	this.users.fetch({
        success: function(users) {
        	self.currentView = new APP.UserIndexView({
        	    	users: users
        	    });
        	 $('#primary-content').html(self.currentView.render().el);
          }
        });
  },
  
  create: function () {
	  this.currentView = new APP.UserNewView({
	      users: this.users, user: new APP.UserModel()
	  });

	  $('#primary-content').html(this.currentView.render().el);
  },
  
  edit: function (id) {
	    var user = this.users.get(id);
	    this.currentView = new APP.UserNewView({
	    	users: this.users, user: user
	    });
	    $('#primary-content').html(this.currentView.render().el);
  },
  
  show: function (id) {
		var self = this;
	    var user = self.users.get(id);
	    
	    //fetch list of devices owned by this user
	    //we get the user id from URL
	    var devices = new APP.DeviceCollection();
	    devices.fetch({
	        success: function(devices) {
	        	self.currentView = new APP.UserShowView({
	      	      user: user,
	      	      devices: devices
	      	    });
	      	    $('#primary-content').html(self.currentView.render().el);
	          }
	        });
   },
	  
   current: function() {
	   	var Router = this,
		fragment = Backbone.history.fragment,
		routes = _.pairs(Router.routes),
		route = null, params = null, matched;

		matched = _.find(routes, function(handler) {
			route = _.isRegExp(handler[0]) ? handler[0] : Router._routeToRegExp(handler[0]);
		    return route.test(fragment);
		});

		if(matched) {
			params = Router._extractParameters(route, fragment);
		    route = matched[1];
		}

		return {
			route : route,
		    fragment : fragment,
		    params : params
		};
     }
});