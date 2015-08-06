module.exports = function(config) {
  config.set({
    basePath: '.',
    browsers: ['PhantomJS'],
    frameworks: ['jasmine'],
    files: ['../../main/webapp/bower_components/angular/angular.js',
            '../../main/webapp/bower_components/angular-mocks/angular-mocks.js',
            '../../main/webapp/js/angular-hello.js',
            '**/*.spec.js',],
    plugins: ['karma-jasmine',
              'karma-phantomjs-launcher'],
    singleRun: true
  });
};