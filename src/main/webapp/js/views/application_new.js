"use strict";
APP.ApplicationNewView = Backbone.View.extend({
  // functions to fire on events
  events: {
    "click button.save": "save",
    "click a.cancel": "cancel"
  },

  // the constructor
  initialize: function (options) {
	this.user = options.user;
    this.device  = options.device;
    this.apps = options.apps;
    this.allApps = options.allApps;
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
    var app = new APP.ApplicationModel();
    
    // get selected app id
    app.set({
    	id: this.$el.find('input[name=optapp]:checked').val()
    });
    
    app.save(null, {
    	validate: false,
    	type: "POST",
    	url: self.apps.url(),
    	success: function(app) {
    		
    		   var completeAppInfo = self.allApps.get(app.id);
    		   app.attributes.name = completeAppInfo.attributes.name;
    		   app.attributes.description = completeAppInfo.attributes.description;
              // add it to the collection
            	self.apps.add(app);
              
              // redirect back to the index
              window.location.hash = "users/" + self.user.id + "/devices/" + self.device.id + "/view";
            },
            error: function(user, response) {
            	self.showErrors(user, { reponse : response.responseText});
            }
     });
  },
  
  cancel: function() {
      window.router.navigate( "users/" + self.user.id + "/devices/" + self.device.id + "/view", {trigger: true});
  },

  // populate the html to the dom
  render: function () {
	var data = { user: { id: this.user.id }, device: { id: this.device.id} };
    this.$el.html(_.template($('#appFormTemplate').html(), data));
    
    this.addApps();
    
    return this;
  },
  
  addApps: function () {
	  // clear out the container each time you render index
	  this.$el.find('#apps').children().remove();
	  _.each(this.allApps.models, $.proxy(this, 'addApp'));
   },
	
   addApp: function (app) {
	   var view = new APP.ApplicationFormRowView({
		    app: app
	   });
	   this.$el.find('#apps').append(view.render().el);
	}
});