'use strict';

let fs = require('fs'),
    argv = require('minimist')(process.argv.slice(2)),
    cookieJar = require('request').jar(),
    request = require('request').defaults({jar:cookieJar}),
    async = require('async'),
    Zip = require("adm-zip"),
    FolderZip = require('folder-zip'),
    srlData = {};

const baseUrl = 'https://stormrunner-load.saas.hpe.com/v1/', // 'https://ptalb002qtso.saas.hpe.com/v1/',
    cliArgs = {
        tenantId: argv.tenantId,
        user: argv.user,
        password: argv.password,
        testName: argv.testName,
        autIP: argv.autIP,
        autPort: argv.autPort,
        vusers: argv.vusers || 10,
        rampUp: argv.rampUp || 30,
        tearDown: argv.tearDown || 30,
        duration: argv.duration || 300
    },
    ipData = 'AOS_URL\r\nhttp://' + cliArgs.autIP + ':' + cliArgs.autPort,
    tenantIdQuery = '?TENANTID=' + cliArgs.tenantId,
    baseScriptFilePath = './assets/TC_loginSearchAndBuy.zip',
    modifiedScriptFilePath = './temp/TC_loginSearchAndBuy.zip';

function validateInput(cb) {
    console.log('validateInput - start');
    if (!cliArgs.tenantId) {
        return cb('Error: You must provide tenant id');
    }
    if (!cliArgs.user) {
        return cb('Error: You must provide user');
    }
    if (!cliArgs.password) {
        return cb('Error: You must provide password');
    }
    if (!cliArgs.testName) {
        return cb('Error: You must provide testName');
    }
    if (!cliArgs.autIP) {
        return cb('Error: You must provide autIP');
    }
    if (!cliArgs.autPort) {
        return cb('Error: You must provide autPort');
    }
    cb();
}

function replaceAutParams(cb) {
    console.log('replaceAutParams - start');
    let origZip = new Zip(baseScriptFilePath);
    origZip.extractAllTo('./temp/TC_loginSearchAndBuy', true);
    fs.writeFile('./temp/TC_loginSearchAndBuy/AOS_URL.dat', ipData);

    let zip = new FolderZip();
    zip.zipFolder('./temp/TC_loginSearchAndBuy', { excludeParentFolder: true }, () => {
        zip.writeToFile(modifiedScriptFilePath);
        cb();
    });
}

function getBaseHttpOptions() {
    return {
        url: baseUrl,
        json: true
    };
}

function login(cb) {
    console.log('login - start');
    let options = getBaseHttpOptions();
    options.url += 'auth/' + tenantIdQuery;
    options.body = {
        user: cliArgs.user,
        password: cliArgs.password
    };

    request.post(options, (err, httpResponse, body) => {
        if (err) {
            return cb('login: ' + err);
        }
        if (!httpResponse || httpResponse.statusCode !== 200) {
            return cb('login: bad status code. ' + (httpResponse && httpResponse.statusCode) ? httpResponse.statusCode : '');
        }
        let lwssoCookie = request.cookie('LWSSO_COOKIE_KEY=' + body.token);
        cookieJar.setCookie(lwssoCookie, getBaseHttpOptions().url);
        cb();
    });
}

function uploadScript(cb) {
    console.log('uploadScript - start');
    let options = getBaseHttpOptions();
    let modifiedScriptFile;

    try {
        modifiedScriptFile = fs.createReadStream(modifiedScriptFilePath);
    } catch(e) {
        return cb('uploadScript: ' + e);
    }

    options.url += 'projects/1/scripts/' + tenantIdQuery;
    options.formData = {
        file: modifiedScriptFile
    };

    request.post(options, (err, httpResponse, body) => {
        if (err) {
            return cb('uploadScript: ' + err);
        }
        if (!httpResponse || httpResponse.statusCode !== 200) {
            return cb('uploadScript: bad status code. ' + (httpResponse && httpResponse.statusCode) ? httpResponse.statusCode : '');
        }
        srlData.script = body;
        cb();
    });
}

function createTest(cb) {
    console.log('createTest - start');
    let options = getBaseHttpOptions();
    options.url += 'projects/1/load-tests/' + tenantIdQuery;
    options.body = {
        name: cliArgs.testName
    };

    request.post(options, (err, httpResponse, body) => {
        if (err) {
            return cb('createTest: ' + err);
        }
        if (!httpResponse || httpResponse.statusCode !== 200) {
            return cb('createTest: bad status code. ' + (httpResponse && httpResponse.statusCode) ? httpResponse.statusCode : '');
        }
        srlData.test = body;
        cb();
    });
}

function attachScriptToTest(cb) {
    console.log('attachScriptToTest - start');
    let options = getBaseHttpOptions();
    options.url += 'projects/1/load-tests/' + srlData.test.id + '/scripts' + tenantIdQuery;
    options.body = {
        scriptId: srlData.script.id,
        vusersNum: cliArgs.vusers,
        startTime: 0,
        rampUp: {
            duration: cliArgs.rampUp
        },
        duration: cliArgs.duration,
        tearDown: {
            duration: cliArgs.tearDown
        },
        pacing: 1
    };

    request.post(options, (err, httpResponse, body) => {
        if (err) {
            return cb('attachScriptToTest: ' + err);
        }
        if (!httpResponse || httpResponse.statusCode !== 200) {
            return cb('attachScriptToTest: bad status code. ' + (httpResponse && httpResponse.statusCode) ? httpResponse.statusCode : '');
        }
        srlData.testScript = body;
        cb();
    });
}

function createOutputFile(cb) {
    console.log('createOutputFile - start');
    try {
        fs.writeFile('./temp/srlTest.properties', "testId=" + srlData.test.id);
    } catch (e) {
        return cb('createOutputFile: ' + e);
    }
    cb();
}

async.series(
    [
        validateInput,
        replaceAutParams,
        login,
        uploadScript,
        createTest,
        attachScriptToTest,
        createOutputFile
    ],
    (err) => {
        if (err) {
            console.log(err);
            process.exit(1);
        }
        console.log('Completed successfully. TestId=' + srlData.test.id);
    }
);