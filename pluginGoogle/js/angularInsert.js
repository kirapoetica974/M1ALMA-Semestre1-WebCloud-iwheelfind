var app = angular.module('iwheelfind', []).controller('AWFcontroller', ['$scope', '$window',
  function($scope, $window) {

    var url1 = $scope.url;
    
    var ide = 10000000000 * Math.random();
    var lift = false;
    var door = false;
    var rampe = false;
    var lang = false;
    var hear = false;
    var visual = false;
    var park = false;
    var toilets = false;
    
    $scope.clickLift = function(){
      lift = true;
    }
    
    $scope.clickAutoDoor = function(){
      door = true;
    }
    
    $scope.clickRamp = function(){
      rampe = true;
    }
    
    $scope.clickSignLang = function(){
      lang = true;
    }
    
    $scope.clickhearing = function(){
      hear = true;
    }
    
    $scope.clickVisual = function(){
      visual = true;
    }
    
    $scope.clickParking = function(){
      park = true;
    }
    
    $scope.clickToilets = function(){
      toilets = true;
    }

    $scope.insert = function() {
      var rootApi = 'https://iwheelfind.appspot.com/_ah/api/';
      gapi.client.load('annotationsiteendpoint', 'v1', function() {
      console.log("todos api loaded");

      gapi.client.annotationsiteendpoint.insertAnnotationSite({id:ide,automaticDoor:door,disabledParking:park,disabledToilets:toilets,hearingSupportSystem:hear,liftToAllFloors:lift,ramp:rampe,signLang:lang,url:url1,visualSupportSystem:visual,listeMotsCles:["avion"]}).execute(
          function(resp) {
            console.log(resp);
          });
      
      location.reload();


    }, rootApi);
    }
  }
]);