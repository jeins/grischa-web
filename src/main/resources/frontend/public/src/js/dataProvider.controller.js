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

    // this.socket = io.connect('ws://localhost:8888/'); //TODO:: need to be set on config
    //this.sse = new EventSource('/sse');
    this.socket = new SockJS('http://localhost:8080/ws');

}

/**
 * get data from socket server
 */
DataProvider.prototype.run = function(){
    var me = this;

    // this.socket.on('server.data', function (dataSet) {
    //     console.log(dataSet);
    //     me.update(dataSet);
    // });
    // this.sse.addEventListener('server.data', function(dataSet){
    //     console.log(dataSet.data);
    //     me.update(JSON.parse(dataSet.data));
    // });
    var stompClient = Stomp.over(this.socket);
    stompClient.connect({}, function(frame) {
        console.log('Connected: ' + frame);
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
    var me = this;
    me.map.setMasterLocation(data.master);
    me.map.setWorkerLocationWithCluster(data.worker);
    me.action.shoot(data);
};