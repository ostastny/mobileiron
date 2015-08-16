"use strict";
APP.DeviceShowView = Backbone.View.extend({  
  // the constructor
  initialize: function (options) {
    this.user = options.user;
    this.device = options.device;
    this.apps = options.apps;
  },

  // populate the html to the dom
  render: function () {
	var data = this.device.toJSON();
	data.user = { id: this.user.id, name: this.user.name };
	  
    this.$el.html(_.template($('#deviceShowTemplate').html(), data));
    
    this.addApps();
    
    return this;
  },
  
  addApps: function () {
	  // clear out the container each time you render index
	  this.$el.find('table#apps tbody').children().remove();
	  _.each(this.apps.models, $.proxy(this, 'addApp'));
	},
	
   addApp: function (app) {
	   var view = new APP.ApplicationRowView({
		    apps: this.apps, 
		    device: this.device,
		    user: this.user,
		    app: app
	   });
	   this.$el.find('table#apps tbody').append(view.render().el);
	}
});