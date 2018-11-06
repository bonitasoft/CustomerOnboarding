var module;
try {
  module = angular.module('bonitasoft.ui.fragments');
} catch (e) {
  module = angular.module('bonitasoft.ui.fragments', []);
  angular.module('bonitasoft.ui').requires.push('bonitasoft.ui.fragments');
}
module.directive('pbFragmentCaseHistory', function() {
  return {
    template: '<div>    <div class="row">\n        <div pb-property-values=\'efec84ed-a2c3-4278-97e5-f2e67b702b73\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-text></pb-text>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'9a3fe94d-6ac9-4e19-82f9-6bf877b11dd9\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <custom-timeline></custom-timeline>\n    </div>\n</div>\n    </div>\n</div>'
  };
});
