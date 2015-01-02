angular.module('eres', [
   	'ngRoute',
   	'ngResource',
   	'ui.bootstrap',
   	'eres.login',
   	'eres.nav',
])
.directive('confirm', [function () {
    return {
      priority: 100,
      restrict: 'A',
      link: {
        pre: function (scope, element, attrs) {
          var msg = attrs.confirm || "Are you sure?";
          element.bind('click', function (event) {
            if (!confirm(msg)) {
              event.stopImmediatePropagation();
              event.preventDefault;
            }
          });
        }
      }
    };
}])
.filter('appending', function() {
  return function(input,extra) {
    return input ? input+extra : '';
  };
})
.filter('prepending', function() {
  return function(input,extra) {
    return input ? extra+input : '';
  };
})
;
