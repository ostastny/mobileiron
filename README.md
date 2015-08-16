# OndrejStastny MobileIron 

## Overview
This is a sample implementation of [senior-software-engineer-spring-mvc-java](https://github.com/ostastny/hiring/blob/master/senior-software-engineer-spring-mvc-java.md).

The solution is composed of a server side RESTful API and client side JavaScript app.
Server uses Jersey framework to create the API, Hibernate for ORM persistance layer, Jackson for JSON serialization, Maven for builds and Heroku for CI and hosting.
Client side is written in Backbone.js and Bootstrap.

## Demo
Live demo is available on [Heroku](http://ostastny-mobileiron.herokuapp.com).
API can be viewed using Swagger UI on [this endpoint](http://ostastny-mobileiron.herokuapp.com/swagger).

![Application ScreenShot](/screen01.png?raw=true)

## Technologies used
* Jersey
* Jackson
* Maven
* Heroku
* Hibernate
* Backbone.js
* Bootstrap
* Swagger

## Issues/TODO (by priority)
* Add unit tests for server and client
* Add lazy loading support to the API
* Create better server side validations (eg. email format etc.) using Hibernate Validators
* Build Automation
	* Add client side build automation (code analysis, bundling, minification,...)
* Deployment automation
* Client-side view inheritance of common functionality from a baseclass
* Server-side repository inheritance of common functionality from a baseclass
* DB password in Git repo in config file


![Swagger UI Screenshot](/screen02.png?raw=true)