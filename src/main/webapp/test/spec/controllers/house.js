'use strict';

describe('Controller: HouseCtrl', function () {

  // load the controller's module
  beforeEach(module('webappApp'));

  var HouseCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HouseCtrl = $controller('HouseCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
