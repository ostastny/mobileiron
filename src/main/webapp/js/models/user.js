"use strict";
APP.UserModel = Backbone.Model.extend({
  urlRoot: '/mobileiron/api/users',
  defaults: {
    name: "",
    email: ""
  },

  validate: function (attrs) {
    var errors = {};
    if (!attrs.name) errors.name = "Name cannot be empty.";
    if (!attrs.email) errors.description = "Email cannot be empty";
    
    if (!_.isEmpty(errors)) {
      return errors;
    }
  }
});

APP.UserCollection = Backbone.Collection.extend({
  // Reference to this collection's model.
  model: APP.UserModel,
  url: '/mobileiron/api/users'
});