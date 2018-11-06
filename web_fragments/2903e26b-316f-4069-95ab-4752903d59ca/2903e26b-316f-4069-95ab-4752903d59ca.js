var module;
try {
  module = angular.module('bonitasoft.ui.fragments');
} catch (e) {
  module = angular.module('bonitasoft.ui.fragments', []);
  angular.module('bonitasoft.ui').requires.push('bonitasoft.ui.fragments');
}
module.directive('pbFragmentCreateActivity', function() {
  return {
    template: '<div>    <div class="row">\n        <div pb-property-values=\'d2454d8d-80c1-4f6c-a335-b900a05f04f6\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-text></pb-text>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'aeb92b56-9818-43c2-8cac-bf8730c58ff1\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-input></pb-input>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'be136fee-7f2d-49b1-9e21-f4d526469854\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-textarea></pb-textarea>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'1c9c0b8e-e1cc-4448-ab4c-fed9e90fbb12\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-button></pb-button>\n    </div>\n</div>\n    </div>\n</div>'
  };
});
