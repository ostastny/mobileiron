"use strict";
window.APP = window.APP || {};
APP.Router = Backbone.Router.extend({
  routes: {
    "users/index": "index",
    "users/new": "create",
    "users/:id/view": "show",
    "users/:id/edit": "edit",

    "users/:id/devices/new": "addDevice",
    "users/:id/devices/:did/edit": "editDevice",
    "users/:id/devices/:did/view": "showDevice",
    
    "users/:id/devices/:did/applications/new": "addApplication",
  },

  initialize: function (options) {
    this.users = options.users || new APP.UserCollection(),
    this.apps = options.apps || new APP.AllApplicationsCollection();
    this.devices = options.devices || {};
    this.deviceApps = options.deviceApps || {};

    this.index();
  },

  index: function () {
	var self = this;

	//load collections first
	var renderApp = _.after(2, function(){
		self.currentView = new APP.UserIndexView({
	    	users: self.users
	    });
		$('#primary-content').html(self.currentView.render().el);
	});
	this.users.fetch({success: renderApp});
	this.apps.fetch({success: renderApp});
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
		var devices = this._getDeviceCollection(id);
		
	    self.currentView = new APP.UserShowView({
	    	user: user,
	    	devices: devices
	    });
	    $('#primary-content').html(self.currentView.render().el);
   },
   
   addDevice: function (id) {
	     var user = this.users.get(id);
	     var devices = this._getDeviceCollection(id);
	     
		 this.currentView = new APP.DeviceNewView({
		     device: new APP.DeviceModel(), devices: devices, user: user
		 });

		  $('#primary-content').html(this.currentView.render().el);
	  },
	  
   editDevice: function (id, did) {
	   var self = this;
	   var user = self.users.get(id);
	   var devices = this._getDeviceCollection(id);
	   var device = devices.get(did);
	   
	   self.currentView = new APP.DeviceNewView({
		   device: device, 
		   devices: devices, 
		   user: user
		});

	   $('#primary-content').html(self.currentView.render().el);
   },
   
   showDevice: function (id, did) {
		var self = this;
	    var user = self.users.get(id);
	    var devices = this._getDeviceCollection(id);
    	var device = devices.get(did);
    	var apps =  this._getApplicationCollection(did);
	    
    	self.currentView = new APP.DeviceShowView({
			user: user,
			device: device,
			apps: apps
		});
		$('#primary-content').html(self.currentView.render().el);
  },
  
  addApplication: function (id, did) {
	   var self = this;
	   var devices = this._getDeviceCollection(id);
	   var device = devices.get(did);
	   var user = this.users.get(id);
   	   var apps =  this._getApplicationCollection(did);	//apps on device
	    
	   this.currentView = new APP.ApplicationNewView({
		   device: device, 
		   user: user,
		   apps: apps,
		   allApps: self.apps //all apps in system
	   });
	   
	   $('#primary-content').html(this.currentView.render().el);
   },
   
   

   //fetch list of devices owned by this user
   //we get the user id from URL
   _getDeviceCollection: function(uid) {
	   var col = this.devices[uid];
	   if(typeof col === 'undefined') {
		   var self = this;
		   new APP.DeviceCollection().fetch({async:false, success: function(devices) { col = self.devices[uid] = devices;} });	//TODO: make this async
	   }
	   return col;
   },
   
   //fetch list of apps installed on a device
   //we get the user id from URL
   _getApplicationCollection: function(did) {
	   var col = this.deviceApps[did];
	   if(typeof col === 'undefined') {
		   var self = this;
			new APP.ApplicationCollection().fetch({async:false, success: function(apps) { col = self.deviceApps[did] = apps;}});	//TODO: make this async
	   }
	   return col;
   },
	
  //Utility function to parse current URL route
   _current: function() {
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