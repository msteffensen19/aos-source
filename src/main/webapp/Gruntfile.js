/**
 * Created by kubany on 11/2/2015.
 */
module.exports = function(grunt) {
    'use strict';
    require('load-grunt-tasks')(grunt);

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
                    optimize: 'uglify2'
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

                ]
            }
        },
        clean: ["target"]

    });
    grunt.registerTask('default', ['clean','requirejs', 'copy', 'cssmin']);
    grunt.registerTask('build', ['clean']);
};