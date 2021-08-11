<template>
	<div class="container">
		<button @click='geofind'>위치찾기</button><br>
		<button @click='createRoom'>방 생성</button><br>
		<button @click='sendLatLng'>메시지 보내기</button><br>
		<button @click='gps'>GPS</button><br>
		<div id="map" class="map"></div>		
	</div>
	
</template>

<script>
import  { requestPost } from '@/utils/request.js';

var map = null;
var polyline = null;

export default {
	name: 'Geolocation',
	data () {
		return {
			latitude: 35.19656853772262,
			longitude: 129.0807270648317,
			textContent: '',
			path: [],
			socket: null
		}
	},
	created() {
		this.socket = new WebSocket("ws://localhost:8080/ws/gps");

		this.socket.onopen = (e) => {
			console.log(e);
			console.log('연결 성공')
		}

		this.socket.send({
			// type: Enter, code: kinderCode
		})

		this.socket.onmessage = ({data}) => {
			console.log(data);
		}
	},
	mounted() {
		if (window.kakao && window.kakao.maps) {
      this.initMap()
    } else {
      const script = document.createElement('script')
      script.onload = () => kakao.maps.load(this.initMap);
      script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ab9dd868c1a1477aa0a4ef31fe1bd8e0'
      document.head.appendChild(script)
    }
	},
	methods: {
		initMap() {
			var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
				mapOption = { 
						center: new kakao.maps.LatLng(33.452739313807456, 126.5709308145358), // 지도의 중심좌표
						level: 3 // 지도의 확대 레벨
				};

			// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
			map = new kakao.maps.Map(mapContainer, mapOption); 

			polyline = new kakao.maps.Polyline({
				map: map,
				strokeWeight: 5,
				strokeColor: '#000000',
				strokeOpacity: 0.8,
			});
			polyline.setMap(map);
		},
		geofind() {
			if(!("geolocation" in navigator)) {
				this.textContent = 'Geolocation is not available.';
				return;
			}
			this.textContent = 'Locating...'
			// get position
			navigator.geolocation.getCurrentPosition(pos => {
				this.latitude = pos.coords.latitude;
				this.longitude = pos.coords.longitude;
				this.textContent = 'Your location data is ' + this.latitude + ', ' + this.longitude
				console.log(this.textContent);
			}, err => {
				this.textContent = err.message;		
			});			
		},
		createRoom() {
			requestPost('http://localhost:8080/gps', {name: 'test', code: '1100'});
			console.log('방 생성');
		},
		sendLatLng() {
			var mes = `{
				"type": "Update",
				"code": "1100",
				"lat": "${this.latitude}",
				"lon": "${this.longitude}"
			}`
			this.socket.send(mes);
			console.log(mes);		
		},
		gps() {
			var ind = 0;
			var test = [33.451676967455036, 126.57137702413796,
									33.45128924822145, 126.57136817596627,
									33.451036766900415, 126.57135866254986,
									33.45086565140295, 126.57141328165117,
									33.450766328203585, 126.57137074841778,
									33.450622000037924, 126.5713499475546,
									33.45034265699462, 126.57139434336625,
									33.449972895051175, 126.57136389615431,
									33.449693514795335, 126.57139753643489,
									33.449305795440225, 126.57138868827342,
									33.44908036163707, 126.57137904190405,
									33.44900141065129, 126.5720139888087,
									33.448984566333976, 126.57235823883373,
									33.448987567709, 126.5732293976195]
			setInterval(() => {
				// this.geofind();				
				// this.sendLatLng();
				this.latitude = test[ind];
				this.longitude = test[ind+1];
				this.path.push(new kakao.maps.LatLng(this.latitude, this.longitude));
				polyline.setPath(this.path);
				ind += 2;
			}, 1000);
		},
	}
}
</script>

<style scoped>
	#map{
    width: 100%;
    height: 800px;
		margin-top: 100px;
  }
</style>