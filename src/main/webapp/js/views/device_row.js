"use strict";
APP.DeviceRowView = Backbone.View.extend({
  // the wrapper defaults to div, so only need to set this if you want something else
  // like in this case we are in a table so a tr
  tagName: "tr",
  // functions to fire on events
  events: {
    "click a.delete": "destroy"
  },

  // the constructor
  initialize: function (options) {
    // model is passed through
    this.device  = options.device;
    this.devices = options.devices;
    this.user = options.user;
  },

  // populate the html to the dom
  render: function () {
	var data= this.device.toJSON();
	data.user = { id: this.user.id};
	
    this.$el.html(_.template($('#deviceRowTemplate').html(), data));
    return this;
  },

  // delete the model
  destroy: function (event) {
    event.preventDefault();
    event.stopPropagation();
    this.device.destroy();
    this.$el.remove();
  }
});