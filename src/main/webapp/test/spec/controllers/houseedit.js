'use strict';

describe('Controller: HouseeditCtrl', function () {

  // load the controller's module
  beforeEach(module('webappApp'));

  var HouseeditCtrl,
    scope;

  // Initialize the controller and a mock scope
  beforeEach(inject(function ($controller, $rootScope) {
    scope = $rootScope.$new();
    HouseeditCtrl = $controller('HouseeditCtrl', {
      $scope: scope
    });
  }));

  it('should attach a list of awesomeThings to the scope', function () {
    expect(scope.awesomeThings.length).toBe(3);
  });
});
