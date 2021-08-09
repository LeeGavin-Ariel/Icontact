import AWS from "aws-sdk"
import JSZip from 'jszip'

var albumBucketName = 'ssafy-cmmpjt304';
var bucketRegion = 'ap-northeast-2';
var IdentityPoolId = 'ap-northeast-2:e3c6b4be-818c-4db9-8fcb-de19f7c62fa7';

AWS.config.update({
  region: bucketRegion,
  credentials: new AWS.CognitoIdentityCredentials({
    IdentityPoolId: IdentityPoolId
  })
});

var s3 = new AWS.S3({
  apiVersion: '2006-03-01',
  params: {Bucket: albumBucketName}
});

export const awss3 = {
	// 수정
	// 첨부 여부 확인 -> 첨부한게 없으면 return
	// 첨부한게 있는데 phtoKey 있다.(이미 저장을 한번 했었다.) -> 기존에 있던 걸 삭제하고 재 업로드 
	updatePhoto: async (albumName, photoKey, elId) => {
	
		var file = document.getElementById(elId).files;
		if (file.length == 0) {
			console.log("no img");
			return;
		}

		// 삭제
		if (photoKey) awss3.deletePhoto([photoKey], "");
		// 재업로드
		// url(photoKey) 반환 -> photoKey -> url 백으로 넘겨주기
		return await awss3.uploadPhoto(albumName, elId);

	},

	// 알림장, 식단, 공지, 일정, 프로필사진
	// 글 작성시
	uploadPhoto: async (albumName, elId) => {
		var files = document.getElementById(elId).files;

		if (!files.length) {
			return console.log('사진 없음');
		} 

		// 백으로 넘겨줘야하는 url list
		// 알림장, 식단, 공지, 일정 -> 1개(list의 첫번째)
		var photoKeyList = [];

		var promises = [];
		
		files.forEach(async file => {
			var fileName = file.name;
			var albumPhotosKey = albumName + '/';

			var slice = fileName.split(".");

			var photoKey = albumPhotosKey + slice[0] + "_" + new Date().getTime() + "." + slice[1];
			
			const result = s3.upload({
				Key: photoKey,
				Body: file,
				ACL: 'public-read'
			}).promise().then(function (data) {
				console.log(data);
				photoKeyList.push(photoKey);
			}).catch(function (err) {
				console.log(err);
			});

			await promises.push(result);

		})
		
		await Promise.all(promises);
		
		return photoKeyList;
	},

	// 앨범만
	// 다운로드를 위한 zip 만들기
	uploadZip: () => {
		var files = document.getElementById('photoupload').files;

		// make zip file
		const zip = new JSZip;
		
		files.forEach( file => {
			console.log(file);
			zip.file(file.name, file, { base64: true })	
		})

		var albumPhotosKey = 'album/';
		var zipName = new Date().getTime() + ".zip";
		var photoKey = albumPhotosKey + zipName;
		zip.generateAsync({ type: 'blob' })
			.then(function (content) {
				console.log(content);
				console.log(photoKey);
				
				s3.upload({
					Key: photoKey,
					Body: content,
					ACL: 'public-read'
				},
				function (err, data) {
					if (err) {
						console.log(err);
					}
					else {
						console.log(data);
					}
				});
			})
		return photoKey;
	},
	
	// 삭제
	// photoKeys -> photoList
	// zipKey -> downloadUrl
	deletePhoto(photoKeys, zipKey) {
		photoKeys.forEach(photoKey => {		
			s3.deleteObject({ Key: photoKey }, function (err) {
				if (err) {
						return console.log('There was an error deleting your photo: ', err.message);
				}
				console.log('Successfully deleted photo.');
			});
		});

		if (zipKey) {
			s3.deleteObject({ Key: zipKey }, function (err) {
				if (err) {
					return console.log('There was an error deleting your photo: ', err.message);
				}
				console.log('Successfully deleted photo.');
			});
		}		
	}
}

export default awss3;
