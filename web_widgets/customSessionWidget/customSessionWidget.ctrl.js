/**
 * Controller is a javascript function that augment the AngularJS scope and expose functions that can be used in custom widget template.
 * 
 * Custom widget properties created on the right panel can be used as variables in controller with $scope.properties.
 * To use AngularJS standard services you must declare them into the main function arguments. 
 * 
 * You can leave the controller empty if you don't need it.
 */
function WidgetsimpleButtonController($scope, $http, $location) {
   'use strict';
  var ctrl = this;
  
   function getSession() {
       return  $http.get('/bonita/API/system/session/unusedid');
    }
    
    function getUser(id) {
        $http.get('/bonita/API/identity/user/'+id).success(function(data){
            $scope.firstName = data.firstname;
            $scope.lastName = data.lastname;
            //$scope.icon = "/bonita/portal/attachmentImage?src="+data.icon;
            //$scope.icon = "/bonita/avatars/"+data.icon;
            
        });
    }
    
   getSession().then(function(response) {
        var session = response.data;
        getUser(session.user_id);
   });
   
   
   $scope.logout = function(){
     $http.get('/bonita/logoutservice').
      success(function(data, status, headers, config) {
        //window.top.location.href = "/bonita/login.jsp?redirectUrl="+$scope.properties.redirectUrl;
        window.top.location.href = window.location.href;
      });
  };
   
}