"use strict";
APP.ApplicationModel = Backbone.Model.extend({
  urlRoot : function(){
	  var route = window.router._current();
	  //needs better validation - verify that fragment is User
	  return "/api/users/" + route.params[0] + "/devices/" + route.params[1] + "/applications";
  },
  
  defaults: {
    name: "",
    description: ""
  },

  validate: function (attrs) {
    var errors = {};
    
    //if (!attrs.name) errors.name = "Name cannot be empty.";
    //if (!attrs.description) errors.name = "Description cannot be empty.";
    
    if (!_.isEmpty(errors)) {
      return errors;
    }
  }
});

APP.ApplicationCollection = Backbone.Collection.extend({
  // Reference to this collection's model.
  model: APP.ApplicationModel,
  url: function(){
	  var route = window.router._current();
	  //needs better validation - verify that fragment is User
	  return "/api/users/" + route.params[0] + "/devices/" + route.params[1] + "/applications";
  }
});

APP.AllApplicationsCollection = Backbone.Collection.extend({
	// Reference to this collection's model.
	model: APP.ApplicationModel,
	url: "/api/applications"
});