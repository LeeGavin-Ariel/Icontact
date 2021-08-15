

export const server = {


    ready:() =>{
                    
        console.log("시작!");
        //서버와연결합니다.    웹소켓서버    Uri    :    ex)    ws://YourDomain/
        this.websocket = new WebSocket("ws://localhost:8080/ws/gps");
        //    서버와연결되면실행됩니다
        this.websocket.onopen = function (evt) { this.onOpen(evt) };
        //    서버와연결이종료되면실행됩니다
        this.websocket.onclose = function (evt) { this.onClose(evt) };
        //    서버에서메세지를받으면실행됩니다
        this.websocket.onmessage = function (evt) { this.onMessage(evt) };
        //    서버연결에서오류가발생하면실행됩니다.
        this.websocket.onerror = function (evt) { this.onError(evt) };

    },
    onOpen:(evt)=>{
        console.log("CONNECTED");
        console.log(evt);
        this.start();
    },
    onClose(evt)
    {
        console.log("DISCONNECTED");
        console.log(evt);
        this.end();
    },
    onError(evt)
    {
        console.log('<span style=" color: red; "> ERROR:</span >    '    +    evt.data);
    },

    // //    서버에    Message를    전달    합니다.
    // function doSend(message) {
    //     console.log("SENT:"    +    message);
    //     websocket.send(message);
    // }

    //    Geolocation    API를    사용해    위치    추적을    시작합니다.
    start() {
        //watchPosition을    사용하여    지속적으로    위치값을    가져옵니다.
        this.nav = navigator.geolocation.watchPosition(this.success, this.error);
    },
    success(position) {
        //    성공적으로    위치값을    얻어오면    다시    위치값을    서버로    전송    합니다.
        //    웹    소켓은    Text형식으로    데이터를    주고    받습니다.
        this.websocket.send("latitude: "    +    position.coords.latitude);
        // doSend("longitude: "    +    position.coords.longitude);
    },
    error(error) {
        alert("Error    :"    +    error.code);
    },
    end() {
        this.websocket.close();                    //    웹    소켓    연결을    종료합니다.
        navigator.geolocation.clearWatch(this.nav);
    },

    onMessage(evt) {
        console.log(evt);

        this.resCnt++;
        console.log(this.resCnt);
        // if (resCnt > 0) {
        //     if (resCnt % 2 != 0) {
        //         //    웹소켓은    Text형식으로    데이터를    주고    받습니다.
        //         resPositionArr.push(evt.data.replace("latitude:",""));
        //     }
        //     else {
        //         //    웹소켓은    Text형식으로    데이터를    주고    받습니다.
        //         resPositionArr.push(evt.data.replace("longitude: ",""));
        //         var resultLat = resPositionArr[resCnt - 2];
        //         var resultLng = resPositionArr[resCnt - 1];
        //         var latlng = new google.maps.LatLng(resultLat, resultLng);

        //         var marker = new google.maps.Marker({ position: latlng, map: Map });

        //         Map.setCenter(latlng);
        //         Map.setZoom(18);
        //     }
        // }
    }

}
export default server;