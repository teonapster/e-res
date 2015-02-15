'use strict';

fdescribe('Controller: HouselistCtrl', function () {
  var houselistCtrl,loginCtrl,loginScope,httpBackend,
    scope,houseService,originalTimeout;
  var returned = [{
	   "ac" : 0,
	   "balcony" : 0,
	   "beds" : 0,
	   "id" : 2,
	   "location" : "Μεταμόρφωση",
	   "name" : "Blue dolphin",
	   "reservations" : [ ],
	   "rooms" : 2,
	   "tv" : 0
	} ];
	//load the controller's module
  beforeEach(module('webappApp'));
  
  
  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope,HouseListFactory,$httpBackend) {
    scope = $rootScope.$new();
    houseService = HouseListFactory;
    houselistCtrl = $controller('HouselistCtrl', {
        $scope: scope
      });
    httpBackend = $httpBackend;
    httpBackend.whenGET("rest/userAccess/status").respond({});
	httpBackend.whenGET("rest/house/list").respond(returned);
	httpBackend.whenGET("views/main.html").respond({});
  }));

  
  it('should simulate promise', inject(function($q, $rootScope) {
	  //define an async call
	  var deferred = houseService.fetchHousesDef();

	  // asyn call promise handle
	  var promise = deferred.promise;
	  promise.then(function(value) { scope.houses = value; },function(){ scope.houses = undefined});
	  
	  //Simulate resolving of promise (fake data set)
	  deferred.resolve(returned);
	  $rootScope.$apply();

	  //==========Check if fetchHousesDef returned any house========//
	  expect(scope.houses.length).not.toBe(0);

	  //Validation check (raises error msg)
	  scope.validateHouses();
	  
	  //==========Error must be disabled============================//
	  expect(scope.errorMsg).toBe(false); 
	}));
  
  it('should simulate promise reject', inject(function($q, $rootScope) {
	  //define an async call
	  var deferred = houseService.fetchHousesDef();

	  // asyn call promise handle
	  var promise = deferred.promise;
	  promise.then(function(value) { scope.houses = value; },function(){ scope.houses = undefined});
	  
	  //Simulate rejecting of promise (no data)
	  deferred.reject();
	  $rootScope.$apply();
	  expect(scope.houses).toBe(undefined);
	  scope.validateHouses();
	  
	  //==========Error must be enabled============================//
	  expect(scope.errorMsg).toBe(true); 
	}));
});


