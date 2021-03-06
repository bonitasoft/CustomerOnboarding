(function () {
  try {
    return angular.module('bonitasoft.ui.widgets');
  } catch(e) {
    return angular.module('bonitasoft.ui.widgets', []);
  }
})().directive('customAssignAndExecuteButton', function() {
    return {
      controllerAs: 'ctrl',
      controller: function PbButtonCtrl($scope, $http, $location, $log, $window) {

   'use strict';

  var vm = this;

  this.action = function action() {
    var id;
    if ($scope.properties.action === 'Submit task') {
      id = getUrlParam('id');
      if (id) {
        vm.busy = true;
        var req = {
          method: 'PUT',
          url: '../API/bpm/userTask/' + getUrlParam('id'),
          data: angular.copy($scope.properties.dataToSend),
          params:  getUserParam()
        };
    
       $http(req)
          .success(function(data, status) {
            doRequest('POST', '../API/bpm/userTask/' + getUrlParam('id') + '/execution', getUserParam());
          })
          .error(function(data, status) {
            $scope.properties.dataFromError = data;
            notifyParentFrame({ message: 'error', status: status, dataFromError: data  });
          })
          .finally(function() {
            vm.busy = false;
          });
      } else {
        $log.log('Impossible to retrieve the task id value from the URL');
      }
    }
  };
  

  /**
   * Execute a get/post request to an URL
   * It also bind custom data from success|error to a data
   * @return {void}
   */
  function doRequest(method, url, params) {
    var req = {
      method: method,
      url: url,
      data: angular.copy($scope.properties.dataToSend),
      params: params
    };

    return $http(req)
      .success(function(data, status) {
        $scope.properties.dataFromSuccess = data;
        notifyParentFrame({ message: 'success', status: status, dataFromSuccess: data });
        if ($scope.properties.targetUrlOnSuccess && method !== 'GET') {
          redirectIfNeeded();
        }
      })
      .error(function(data, status) {
        $scope.properties.dataFromError = data;
        notifyParentFrame({ message: 'error', status: status, dataFromError: data  });
      });
  }

  function redirectIfNeeded() {
    var iframeId = $window.frameElement ? $window.frameElement.id : null;
    //Redirect only if we are not in the portal or a living app
    if (!iframeId || iframeId && iframeId.indexOf('bonitaframe') !== 0) {
      $window.location.assign($scope.properties.targetUrlOnSuccess);
    }
  }

  function notifyParentFrame(additionalProperties) {
    if ($window.parent !== $window.self) {
      var dataToSend = angular.extend({}, $scope.properties, additionalProperties);
      $window.parent.postMessage(JSON.stringify(dataToSend), '*');
    }
  }

  function getUserParam() {
    var userId = getUrlParam('user');
    if (userId) {
      return { 'user': userId };
    }
    return {};
  }

  /**
   * Extract the param value from a URL query
   * e.g. if param = "id", it extracts the id value in the following cases:
   *  1. http://localhost/bonita/portal/resource/process/ProcName/1.0/content/?id=8880000
   *  2. http://localhost/bonita/portal/resource/process/ProcName/1.0/content/?param=value&id=8880000&locale=en
   *  3. http://localhost/bonita/portal/resource/process/ProcName/1.0/content/?param=value&id=8880000&locale=en#hash=value
   * @returns {id}
   */
  function getUrlParam(param) {
    var paramValue = $location.absUrl().match('[//?&]' + param + '=([^&#]*)($|[&#])');
    if (paramValue) {
      return paramValue[1];
    }
    return '';
  }
},
      template: '<div class="text-{{ properties.alignment }}">\n    <button\n        ng-class="\'btn btn-\' + properties.buttonStyle"\n        ng-click="ctrl.action()"\n        type="button"\n        ng-disabled="properties.disabled || ctrl.busy" ng-bind-html="properties.label | uiTranslate"></button>\n</div>\n'
    };
  });
