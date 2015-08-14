"use strict";
APP.UserNewView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save"
  },

  // the constructor
  initialize: function (options) {
    this.user  = options.user;
    this.users = options.users;
    this.user.bind('invalid', this.showErrors, this);
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
    self.user.set({
      name: this.$el.find('input[name=name]').val(),
      email: this.$el.find('input[name=email]').val()
    });
    if (self.user.isValid()){
    	//persist on the server
    	self.user.save(null, {
            success: function(user) {
              // add it to the collection
            	self.users.add(user);
              
              // redirect back to the index
              window.location.hash = "users/index";
            },
            error: function(user, response) {
            	self.showErrors(user, { reponse : response.responseText});
            }
       });
        
    }
  },

  // populate the html to the dom
  render: function () {
    this.$el.html(_.template($('#userFormTemplate').html(), this.user.toJSON()));
    return this;
  }
});