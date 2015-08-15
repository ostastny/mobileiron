"use strict";
APP.DeviceNewView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save",
    "click a.cancel": "cancel"
  },

  // the constructor
  initialize: function (options) {
	this.user = options.user;
    this.device  = options.device;
    this.devices = options.devices;
    this.device.bind('invalid', this.showErrors, this);
  },

  showErrors: function (user, errors) {
    this.$el.find('.error').removeClass('error');
    this.$el.find('.alert').html(_.values(errors).join('<br>')).show();
    // highlight the fields with errors
    _.each(_.keys(errors), _.bind(function (key) {
      this.$el.find('*[name=' + key + ']').parent().addClass('error');
    }, this));
  },

  save: function (event) {
    event.stopPropagation();
    event.preventDefault();

    var self = this;
    // update our model with values from the form
    self.device.set({
    	phoneNumber: this.$el.find('input[name=phoneNumber]').val(),
    	operatingSystem: this.$el.find('input[name=operatingSystem]').val()
    });
    if (self.device.isValid()){
    	//persist on the server
    	self.device.save(null, {
            success: function(device) {
              // add it to the collection
            	self.devices.add(device);
              
              // redirect back to the index
              window.location.hash = "users/" + self.user.id + "/view";
            },
            error: function(user, response) {
            	self.showErrors(user, { reponse : response.responseText});
            }
       });
        
    }
  },
  
  cancel: function() {
      window.router.navigate("users/" + this.user.id + "/view", {trigger: true});
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#deviceFormTemplate').html(), this.device.toJSON()));
    return this;
  }
});