(function () {
  try {
    return angular.module('bonitasoft.ui.widgets');
  } catch(e) {
    return angular.module('bonitasoft.ui.widgets', []);
  }
})().directive('customTimeline', function() {
    return {
      template: ' <div class="well well-sm">\n     <div class="timeline">\n          <div ng-class="$index % 2 === 0 ? \'box left\' : \'box right\'" ng-repeat="event in properties.events track by $index">\n            <div class="content">\n              <h3 ng-bind-html="event.title"></h3>\n              <p ng-bind-html="event.body"></p>\n            </div>\n          </div>\n    </div>\n</div>'
    };
  });
