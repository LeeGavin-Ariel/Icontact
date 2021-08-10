var    websocket;
var    resCnt    =    0;                    //서버에서    응답받은    횟수.
var    resPositionArr    =    [];                    //서버에서    응답받은    좌표값    Array.
$(document).ready(function () {
                
    //서버와연결합니다.    웹소켓서버    Uri    :    ex)    ws://YourDomain/
    websocket = new WebSocket(wsUri);
    //    서버와연결되면실행됩니다
    websocket.onopen = function (evt) { onOpen(evt) };
    //    서버와연결이종료되면실행됩니다
    websocket.onclose = function (evt) { onClose(evt) };
    //    서버에서메세지를받으면실행됩니다
    websocket.onmessage = function (evt) { onMessage(evt) };
    //    서버연결에서오류가발생하면실행됩니다.
    websocket.onerror = function (evt) { onError(evt) };
});
functiononOpen(evt)
{
    writeToScreen("CONNECTED");
}
functiononClose(evt)
{
    writeToScreen("DISCONNECTED");
}
functiononError(evt)
{
    writeToScreen('<span style=" color: red; "> ERROR:</span >    '    +    evt.data);
}
functionwriteToScreen(message)
{
    var pre = document.createElement("P");
    pre.style.wordWrap =   "break-word";
    pre.innerHTML = message;
    output.appendChild(pre);
}

//    서버에    Message를    전달    합니다.
function doSend(message) {
    writeToScreen("SENT:"    +    message);
    websocket.send(message);
}

//    Geolocation    API를    사용해    위치    추적을    시작합니다.
function start() {
    //watchPosition을    사용하여    지속적으로    위치값을    가져옵니다.
    nav = navigator.geolocation.watchPosition(success, error);
}
function success(position) {
    //    성공적으로    위치값을    얻어오면    다시    위치값을    서버로    전송    합니다.
    //    웹    소켓은    Text형식으로    데이터를    주고    받습니다.
    doSend("latitude: "    +    position.coords.latitude);
    doSend("longitude: "    +    position.coords.longitude);
}
function error(error) {
    alert("Error    :"    +    error.code);
}
function end() {
    websocket.close();                    //    웹    소켓    연결을    종료합니다.
    navigator.geolocation.clearWatch(nav);
}

function onMessage(evt) {
    writeToScreen('<span style=" color: blue; "> RESPONSE: '    +    evt.data +    '</span >’);

    resCnt++;

    if (resCnt > 0) {
        if (resCnt % 2 != 0) {
            //    웹소켓은    Text형식으로    데이터를    주고    받습니다.
            resPositionArr.push(evt.data.replace("latitude:",""));
        }
        else {
            //    웹소켓은    Text형식으로    데이터를    주고    받습니다.
            resPositionArr.push(evt.data.replace("longitude: ",""));
            var resultLat = resPositionArr[resCnt - 2];
            var resultLng = resPositionArr[resCnt - 1];
            var latlng = new google.maps.LatLng(resultLat, resultLng);

            var marker = new google.maps.Marker({ position: latlng, map: Map });

            Map.setCenter(latlng);
            Map.setZoom(18);
        }
    }
}