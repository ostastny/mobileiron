"use strict";
APP.DeviceModel = Backbone.Model.extend({
  urlRoot : function(){
	  var route = window.router._current();
	  //needs better validation - verify that fragment is User
	  return "/api/users/" + route.params[0] + "/devices";
  },
  
  defaults: {
    phoneNumber: "",
    operatingSystem: ""
  },

  validate: function (attrs) {
    var errors = {};
    if (!attrs.phoneNumber) errors.phoneNumber = "Phone number cannot be empty.";
    
    //simple US phone number validation
    var re = /\(?([0-9]{3})\)?([ .-]?)([0-9]{3})\2([0-9]{4})/i;
    if(attrs.phoneNumber && !re.test(attrs.phoneNumber))
    	errors.description = "Please enter valid US number";
    
    if (!attrs.operatingSystem) errors.description = "Operating System cannot be empty";
   
    if (!_.isEmpty(errors)) {
      return errors;
    }
  }
});

APP.DeviceCollection = Backbone.Collection.extend({
  // Reference to this collection's model.
  model: APP.DeviceModel,
  url: function(){
	  var route = window.router._current();
	  //needs better validation - verify that fragment is User
	  return "/api/users/" + route.params[0] + "/devices";
  }
});