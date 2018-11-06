var module;
try {
  module = angular.module('bonitasoft.ui.fragments');
} catch (e) {
  module = angular.module('bonitasoft.ui.fragments', []);
  angular.module('bonitasoft.ui').requires.push('bonitasoft.ui.fragments');
}
module.directive('pbFragmentCaseActivities', function() {
  return {
    template: '<div>    <div class="row">\n        <div pb-property-values=\'6d1c8ddb-7b68-4f08-b50d-cef75b1ee72a\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-text></pb-text>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'3b344fbf-dcd4-4753-a599-93f1ea338e04\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <custom-h-t-m-l-data-table></custom-h-t-m-l-data-table>\n    </div>\n</div>\n    </div>\n</div>'
  };
});
