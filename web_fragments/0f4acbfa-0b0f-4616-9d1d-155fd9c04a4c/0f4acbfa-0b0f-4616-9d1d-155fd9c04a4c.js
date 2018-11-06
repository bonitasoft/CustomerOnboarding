var module;
try {
  module = angular.module('bonitasoft.ui.fragments');
} catch (e) {
  module = angular.module('bonitasoft.ui.fragments', []);
  angular.module('bonitasoft.ui').requires.push('bonitasoft.ui.fragments');
}
module.directive('pbFragmentCaseDocuments', function() {
  return {
    template: '<div>    <div class="row">\n        <div pb-property-values=\'3b6e04cd-651b-4fc2-9e8b-846ca747ebe8\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-text></pb-text>\n    </div>\n</div>\n    </div>\n    <div class="row">\n        <div pb-property-values=\'1363720d-390a-454d-912b-2bbae62d1cc4\'>\n\n  <div class="col-xs-12  col-sm-12  col-md-12  col-lg-12"\n       ng-class="properties.cssClasses"\n       ng-if="!properties.hidden"\n         ng-repeat="$item in ($collection = properties.repeatedCollection) track by $index"\n       >\n\n        <div class="row">\n        <div pb-property-values=\'56cb6304-7941-45dc-b70e-79e163d6b2ff\'>\n    <div ng-if="!properties.hidden" class="component col-xs-12  col-sm-12  col-md-12  col-lg-12" ng-class="properties.cssClasses">\n        <pb-link></pb-link>\n    </div>\n</div>\n    </div>\n\n\n  </div>\n</div>\n\n    </div>\n</div>'
  };
});
