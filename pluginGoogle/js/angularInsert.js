var app = angular.module('iwheelfind', []).controller('SPcontroller1', ['$scope', '$window',
  function($scope, $window) {


    $window.init = function() {
      var rootApi = "https://1-dot-signpetii.appspot.com/_ah/api/";
      gapi.client.load('anotationendpoint', 'v1', function() {
        console.log("score api loaded");
        gapi.client.anotationendpoint.insertAnnotation().execute(
          function(resp) {
            $scope.petition = resp.items;
            $scope.$apply();
            console.log(resp);
          });
        
      }, rootApi);
    }
  }
]);