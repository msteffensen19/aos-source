// Karma configuration
// Generated on Mon Dec 09 2019 11:54:46 GMT+0200 (Israel Standard Time)

module.exports = function(config) {
  config.set({

    // base path that will be used to resolve all patterns (eg. files, exclude)
    basePath: './',


    // frameworks to use
    // available frameworks: https://npmjs.org/browse/keyword/karma-adapter
    frameworks: ['jasmine', 'requirejs'],


    // list of files / patterns to load in the browser
    files: [
      'tests/main.test.js',
      {
        pattern: 'vendor/**/*.js',
        included: false
      },
      {
        pattern: 'utils/*.js',
        included: false
      },
      {
        pattern: 'utils/**/*.js',
        included: false
      },
      {
        pattern: 'languages/*.js',
        included: false
      },
      {pattern: 'app/**/*.js', included: false},
      {pattern: 'app/**/*.html', included: false},
      {
        pattern: 'app.js',
        included: false
      },
      {pattern: 'tests/**', included: false, nocache: true},




    ],

    client: {
      requireJsShowNoTimestampsError: '^(?!.*(^/base/node_modules/))'
    },
    // list of files / patterns to exclude
    exclude: [
    ],


    // preprocess matching files before serving them to the browser
    // available preprocessors: https://npmjs.org/browse/keyword/karma-preprocessor
    preprocessors: {
    },

    proxies :{
        '/services.properties': 'http://localhost:8080/services.properties',
        '/app/tempFiles/popularProducts.json': 'http://localhost:8080/app/tempFiles/popularProducts.json',
        '/app/views/home-page.html': 'http://localhost:8080/app/views/home-page.html'
    },
    // test results reporter to use
    // possible values: 'dots', 'progress'
    // available reporters: https://npmjs.org/browse/keyword/karma-reporter
    reporters: ['progress'],


    // web server port
    port: 9877,


    // enable / disable colors in the output (reporters and logs)
    colors: true,


    // level of logging
    // possible values: config.LOG_DISABLE || config.LOG_ERROR || config.LOG_WARN || config.LOG_INFO || config.LOG_DEBUG
    logLevel: config.LOG_DEBUG,


    // enable / disable watching file and executing tests whenever any file changes
    autoWatch: true,


    // start these browsers
    // available browser launchers: https://npmjs.org/browse/keyword/karma-launcher
    browsers: ['Chrome'],


    // Continuous Integration mode
    // if true, Karma captures browsers, runs the tests and exits
    singleRun: false,

    // Concurrency level
    // how many browser should be started simultaneous
    concurrency: Infinity
  })
}
