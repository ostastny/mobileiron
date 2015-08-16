"use strict";
APP.ApplicationFormRowView = Backbone.View.extend({
  // the constructor
  initialize: function (options) {
    this.app = options.app;
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#appFormRowTemplate').html(), this.app.toJSON()));
    return this;
  }
});