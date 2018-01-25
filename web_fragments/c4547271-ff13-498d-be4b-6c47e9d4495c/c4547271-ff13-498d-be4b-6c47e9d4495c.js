var module;
try {
  module = angular.module('bonitasoft.ui.fragments');
} catch (e) {
  module = angular.module('bonitasoft.ui.fragments', []);
  angular.module('bonitasoft.ui').requires.push('bonitasoft.ui.fragments');
}
module.directive('pbFragmentCaseComments', function() {
  return {
    template: '<div>    <div class="row">\n        <div pb-property-values=\'dba2382d-c2e4-4b02-87f6-26f4947eed08\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-text></pb-text>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'d100ad7a-d937-44c2-927f-0ab30b167ca1\'>\n\n  <div class="col-xs-12  col-sm-12  col-md-12  col-lg-12"\n       ng-class="properties.cssClasses"\n       ng-if="!properties.hidden"\n         ng-repeat="$item in ($collection = properties.repeatedCollection) track by $index"\n       >\n\n        <div class="row">\n        <div pb-property-values=\'69d4e8a9-5719-4de7-bc2d-bbfd98499593\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-text></pb-text>\n    </div>\n</div>\n    </div>\n\n\n  </div>\n</div>\n\n    </div>\n</div>'
  };
});
