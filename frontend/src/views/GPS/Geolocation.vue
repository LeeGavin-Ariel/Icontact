<template>
	<div id="bg" align="center">
		<div style="margin-top:1.3rem; margin-bottom:1rem; font-size:1.2rem; text-weight:900;">운행 시간 동안에 등하원 버스의 현 위치를 확인하실 수 있습니다.</div>
		<div id="map" class="map">
		</div>
		
	</div>	
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

		this.socket.onmessage = ({data}) => {
			var jsonData = JSON.parse(data);	
			console.log(jsonData);
			console.log(jsonData.type);
			if(jsonData.type === "not running"){				
				this.$fire({
					html: `<a href="javascript:void(0);"></a><p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">등하원 버스 운행 시간이 아닙니다.</p>`,
					imageUrl: require('@/assets/flaticon/not_running.png'),
					imageWidth: 225,
					imageHeight: 185,
					confirmButtonColor: '#58679A',
					focusConfirm: false
				}).then(r => {
					console.log(r.value);
					this.$router.push({ name: 'MainPage' });
				});				
			}else if(jsonData.type === "Delete"){
				this.$fire({
					html: `<p style="font-size: 0.95rem; font-family: 'NanumSquareRound';">등하원 버스 운행이 완료되었습니다.</p>`,
					imageUrl: require('@/assets/flaticon/Delete.png'),
					imageWidth: 225,
					imageHeight: 185,
					confirmButtonColor: '#58679A'
				}).then(r => {
					console.log(r.value);
					this.$router.push({ name: 'MainPage' });
				});
			}
			
			if(jsonData.lat != "" &&jsonData.lat !="0" && jsonData.lat != "undefined" && jsonData.lat != undefined) {
				if(Math.abs(this.latitude-jsonData.lat)< 0.00005 &&
					Math.abs(this.longitude-jsonData.lon)< 0.00005){
					console.log("do not update");
				}
				else{
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
						new kakao.maps.Size(52, 56), 
						new kakao.maps.Point(26, 42))
					})
					
					this.map.panTo(curLatLng);
				}
			}
		}
	},
	methods: {
		initMap() {
			var mapContainer = document.getElementById('map'),
				mapOption = { 
					center: new kakao.maps.LatLng(this.latitude, this.longitude),
					level: 2
				};

			// 지도를 표시할 div와  지도 옵션으로  지도를 생성합니다
			this.map = new kakao.maps.Map(mapContainer, mapOption); 

			this.polyline = new kakao.maps.Polyline({
				map: this.map,
				strokeWeight: 7,
				strokeColor: '#F64A5F',
				strokeOpacity: 0.7
			});
			this.polyline.setMap(this.map);
		},
	}
}
</script>

<style scoped>
	#map{
		margin: 2%, 3%, 3%, 3%;
    width: 80%;
		height: 80%;
		border-radius: 6px;
		border: solid 10px rgba(256,256,256,0.4)
	}

	#bg {
		background-color: rgba(102,122,188, 0.1);		
		width: 100%;
		height: 100%;
	}
</style>