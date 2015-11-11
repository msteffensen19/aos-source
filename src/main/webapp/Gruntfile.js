/**
 * Created by kubany on 11/2/2015.
 */
module.exports = function(grunt) {
    'use strict';
    require('load-grunt-tasks')(grunt);
    grunt.loadNpmTasks('grunt-angular-templates');
    grunt.initConfig({
        requirejs: {
            js: {
                options: {
                    uglify2: {
                        mangle: false
                    },
                    baseUrl: "",
                    mainConfigFile: "main.js",
                    name: 'main',
                    out: "target/main.min.js",
                    optimize: 'none',
                    paths: {
                        'aos.templates' : 'target/js/templates'
                    },
                    logLevel: 0
                }
            }
        },
        cssmin: {
            options: {
                shorthandCompacting: false,
                roundingPrecision: -1
            },
            target: {
                files: [{
                    expand: true,
                    cwd: 'css',
                    src: ['*.css', '!*.min.css'],
                    dest: 'target/css',
                    ext: '.min.css'
                }]
            }
        },
        copy: {
            main: {
                files: [
                    // includes files within path
                    {expand: true, src: ['css/fonts/*', 'css/images/*', '!css/*.css'], dest: 'target', filter: 'isFile'},
                    {expand: true, src: ['app/views/*', 'app/partials/*'], dest: 'target', filter: 'isFile'},
                    {expand: true, src: ['app/categoryProducts.json', 'app/popularProducts.json'], dest: 'target', filter: 'isFile'},

                ]
            }
        },
        ngtemplates:  {
            app:        {
                src:      ['app/partials/**.html'],
                dest:     'app/templates/module.js',
                options: {
                    bootstrap:  function(module, script) {
                        return '\
                        define(["angular"], function (angular) {\
                            "use strict";\
                            var templates = angular.module("aos.templates", []);\
                            templates.run(function($templateCache) {\
                            ' + script + '\
                            });\
                            return templates;\
                        });';
                    }
                }
            }

        },
        clean: ["target", "app/templates"]

    });
    grunt.registerTask('default', ['clean', 'ngtemplates', 'requirejs', 'copy', 'cssmin']);
    grunt.registerTask('build', ['clean']);
    grunt.registerTask('ngTemplatesBuild', ['ngtemplates']);
};