<template>
	<div id="map" class="map"></div>
	
</template>

<script>
import SERVER from '@/api/drf.js';

var map = null;
var polyline = null;

export default {
	name: 'Geolocation',
	data () {
		return {
			latitude: 33.45253988515528,
			longitude: 126.57061988235415,
			textContent: '',
			path: [],
			socket: null
		}
	},
	created() {
		this.socket = new WebSocket(`${SERVER.WS}/ws/gps`);

		this.socket.onopen = (e) => {
			console.log(e);
			console.log('연결 성공')

			this.socket.send(`{
				"type": "Enter",
				"code": "${this.$store.state.user.kinderCode}",
				"lat": "0",
				"lon": "0"
			}`)
		}	

		this.socket.onmessage = ({data}) => {
			console.log(data);			
			var jsonData = JSON.parse(data);
			
			this.latitude = jsonData.lat;
			this.longitude = jsonData.lon;
			
			if(this.latitude != "0" && this.latitude != "undefined") {
				console.log(typeof(this.latitude));
				this.path.push(new kakao.maps.LatLng(this.latitude, this.longitude));
				polyline.setPath(this.path);

				map.setCenter(new kakao.maps.LatLng(this.latitude, this.longitude));
			}
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
			var mapContainer = document.getElementById('map'),
				mapOption = { 
					center: new kakao.maps.LatLng(this.latitude, this.longitude),
					level: 3
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