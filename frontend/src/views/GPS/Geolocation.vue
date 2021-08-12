<template>
	<div id="map" class="map"></div>
	
</template>

<script>
import SERVER from '@/api/drf.js';

export default {
	name: 'Geolocation',
	data () {
		return {
			latitude: 33.45253988515528,
			longitude: 126.57061988235415,
			textContent: '',
			path: [],
			socket: null,

			map: null,
			polyline: null,
			marker: null,
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
		
	},
	mounted() {
	if (window.kakao && window.kakao.maps) {
      this.initMap()
		}
    // } else {
    //   const script = document.createElement('script')
    //   script.onload = () => kakao.maps.load(this.initMap);
    //   script.src = 'http://dapi.kakao.com/v2/maps/sdk.js?autoload=false&appkey=ab9dd868c1a1477aa0a4ef31fe1bd8e0'
    //   document.head.appendChild(script)
    // }

		this.socket.onmessage = ({data}) => {
			console.log(data);			
			var jsonData = JSON.parse(data);	
			
			if(jsonData.lat!= "0" && jsonData.lat != "undefined" && jsonData.lat != undefined) {
				this.latitude = jsonData.lat;
				this.longitude = jsonData.lon;
				
				var curLatLng = new kakao.maps.LatLng(this.latitude, this.longitude);

				// Update polyline
				this.path.push(curLatLng);
				this.polyline.setPath(this.path);

				// Update marker
				if(this.marker != null){
					this.marker.setMap(null);
				}
				this.marker = new kakao.maps.Marker({
					map: this.map,
					position: curLatLng,
					image: new kakao.maps.MarkerImage(require('@/assets/flaticon/busicon.png'), 
					new kakao.maps.Size(48, 51), 
					new kakao.maps.Point(24, 51))
				})
				
				this.map.panTo(curLatLng);
			}
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
			this.map = new kakao.maps.Map(mapContainer, mapOption); 

			this.polyline = new kakao.maps.Polyline({
				map: this.map,
				strokeWeight: 5,
				strokeColor: '#000000',
				strokeOpacity: 0.8,
			});
			this.polyline.setMap(this.map);
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