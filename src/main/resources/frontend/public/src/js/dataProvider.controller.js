'use strict';

/**
 * data handle
 * @param action
 * @param map
 * @constructor
 */
function DataProvider(action, map){
    this.action = action;
    this.map = map;

    this.socket = new SockJS('http://localhost:8080/ws');

}

/**
 * get data from socket server
 */
DataProvider.prototype.run = function(){
    var me = this;

    var stompClient = Stomp.over(this.socket);
    stompClient.connect({}, function() {
        stompClient.subscribe('/worker/data', function(data) {
            data = JSON.parse(data.body,  true);
            me.update(data);
        });
    });
};

/**
 * update map activity:
 * - display heat of master location
 * - display marker of worker location
 * - animate shoot line from worker to master
 * @param dataSet
 */
DataProvider.prototype.update = function(data){
    this.map.setMasterLocation(data.master);
    this.map.setWorkerLocationWithCluster(data.worker);
    this.action.shoot(data);

    this.saveToLocalStorage(data.worker);
};

DataProvider.prototype.saveToLocalStorage = function (worker) {
    var hostName = "log:" + worker.hostName;

    if (typeof(Storage) !== "undefined"){
        var arrWorkerData = JSON.parse(localStorage.getItem(hostName), true);

        if (!arrWorkerData){
            arrWorkerData = [];
        }

        arrWorkerData.push(worker);

        localStorage.setItem(hostName, JSON.stringify(arrWorkerData));
    } else{
        console.error("no web storage support!");
    }
};

DataProvider.prototype.getLocalStorageData = function () {
    var logData = [];

    for (var i=0; i<localStorage.length; i++){
        var hostName = localStorage.key(i);
        var data = JSON.parse(localStorage.getItem(hostName));
        var tmp = {};
        tmp[hostName] = data;
        logData.push(tmp);
    }

    var element = document.createElement('a');
    element.setAttribute('href', 'data:text/plain;charset=utf-8,' + encodeURIComponent(JSON.stringify(logData)));
    element.setAttribute('download', "webData.json");

    element.style.display = 'none';
    document.body.appendChild(element);

    element.click();

    document.body.removeChild(element);
};