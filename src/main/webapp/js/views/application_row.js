"use strict";
APP.ApplicationRowView = Backbone.View.extend({
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
    this.apps = options.apps;
    this.app = options.app;
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#appRowTemplate').html(), this.app.toJSON()));
    return this;
  },
  
  // delete the model
  destroy: function (event) {
    event.preventDefault();
    event.stopPropagation();
    this.app.destroy();
    this.$el.remove();
  }
});