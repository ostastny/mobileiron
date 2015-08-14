"use strict";
APP.UserIndexView = Backbone.View.extend({
  // the constructor
  initialize: function (options) {
    // model is passed through
    this.users = options.users;
    this.users.bind('reset', this.addAll, this);
  },

  // populate the html to the dom
  render: function () {
    this.$el.html($('#userIndexTemplate').html());
    this.addAll();
    return this;
  },

  addAll: function () {
    // clear out the container each time you render index
    this.$el.find('tbody').children().remove();
    _.each(this.users.models, $.proxy(this, 'addOne'));
  },

  addOne: function (user) {
    var view = new APP.UserRowView({
      users: this.users, 
      user: user
    });
    this.$el.find("tbody").append(view.render().el);
  }
});