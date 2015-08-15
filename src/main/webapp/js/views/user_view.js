"use strict";
APP.UserShowView = Backbone.View.extend({  
  // the constructor
  initialize: function (options) {
    this.user = options.user;
    this.devices = options.devices;
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#userShowTemplate').html(), this.user.toJSON()));
    
    this.addDevices();
    
    return this;
  },
  
  addDevices: function () {
	  // clear out the container each time you render index
	  this.$el.find('table#devices tbody').children().remove();
	  _.each(this.devices.models, $.proxy(this, 'addDevice'));
	},
	
   addDevice: function (device) {
	   var view = new APP.DeviceRowView({
		    devices: this.devices, 
		    device: device,
		    user: this.user
	   });
	   this.$el.find('table#devices tbody').append(view.render().el);
	}
});