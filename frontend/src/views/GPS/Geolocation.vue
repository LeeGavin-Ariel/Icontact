<template>
	<div>
		<button @click='geofind'>위치찾기</button><br>
		<button @click='createRoom'>방 생성</button><br>
		<button @click='sendLatLng'>메시지 보내기</button><br>
		<button @click='gps'>GPS</button><br>
	</div>
</template>

<script>
import  { requestPost } from '@/utils/request.js';
export default {
	name: 'Geolocation',
	data () {
		return {
				latitude: '',
				longitude: '',
				textContent: '',
				socket: null
		}
	},
	created() {
		this.socket = new WebSocket("ws://localhost:8080/ws/gps");

		this.socket.onopen = (e) => {
			console.log(e);
			console.log('연결 성공')
		}

		this.socket.onmessage = ({data}) => {
			console.log(data);
		}
	},
	methods: {
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
		receiptLatLng() {
			console.log("실행1")
			this.socket.onmessage = ({data}) => {
				console.log("실행2")
				console.log(data);
			}
		},
		gps() {
			setInterval(() => {
				this.geofind(); 
				this.sendLatLng();}, 2000);
		},
	}
}
</script>

<style scoped>
	
</style>