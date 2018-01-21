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
    var me = this;
    me.map.setMasterLocation(data.master);
    me.map.setWorkerLocationWithCluster(data.worker);
    me.action.shoot(data);
};