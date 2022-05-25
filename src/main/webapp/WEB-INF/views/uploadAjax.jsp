<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Upload Ajax</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
<style>
	.uploadResult {
		width: 100%;
		background-color: #ddd;
	}
	
	.uploadResult ul {
		display: flex;
		flex-flow: row;
		justify-content: center;
		align-items: center;
	}
	
	.uploadResult ul li {
		list-style: none;
		padding: 10px;
	}
	
	.uploadResult ul li img {
		width: 20px;
	}
	
	.uploadResult ul li span {
		color: white;
	}
	
	.bigPictureWrapper {
		position: absolute;
		display: none;
		justify-content: center;
		align-items: center;
		top: 0%;
		width: 100%;
		height: 100%;
		background-color: gray;
		z-index: 100;
		background: rgba(255, 255, 255, 0.5);
	}
	
	.bigPicture {
		position: relative;
		display: flex;
		justify-content: center;
		align-items: center;
	}
	
	.bigPicture img {
		width: 400px;
	}
</style>
</head>
<body>
	<!-- Ajax를 이용한 파일 업로드 -->
	<div class="uploadDiv">
		<input type="file" name="uploadAjax" multiple>
	</div>
	<div class="uploadResult">
		<ul></ul>
	</div>
	<button id="uploadBtn">Upload</button>
	
	<div class="bigPictureWrapper">
		<div class="bigPicture"></div>
	</div>
	
	
	<script type="text/javascript">
		/* 원본 이미지 보여주기 */
		function showImage(fileCallPath) {
			$(".bigPictureWrapper").css("display", "flex").show();
			$(".bigPicture").html("<img src='/display?fileName=" + encodeURI(fileCallPath) + "'>")
							.animate({ width: '100%', height: '100%' }, 1000);
			
			// 이미지를 다시 클릭하면 사라지도록 이벤트 처리 
			$(".bigPictureWrapper").on("click", function(e) {
				$(".bigPicture").animate({ width: '0%', height: '0%' }, 1000);
				setTimeout(function() {
					$('.bigPictureWrapper').hide();
				}, 1000)
			})			
		}
	
		$(document).ready(function() {
			$("#uploadBtn").on("click", function(e) {
				/* Ajax를 이용하는 경우, FormData(form태그 역할 수행) 객체 이용 */
				var formData = new FormData();
				var inputFile = $("input[name='uploadAjax']");
				var files = inputFile[0].files;
				console.log(files);
				
				/* 파일의 확장자나 크기 사전처리 */
				// 정규식 이용하여 파일 확장자 체크 
				var regEx = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
				var maxSize = 5242880;
				
				function checkExtension(fileName, fileSize) {
					if(fileSize >= maxSize) {
						alert("파일 크기 초과");
						return false;
					}
					if(regEx.test(fileName)) {
						alert("해당 종류의 파일은 업로드 할 수 없음");
						return false;
					}
					return true;
				}
				
				/* jQuery 이용한 파일 전송 */
				for(var i = 0; i < files.length; i++) { 
					if(!checkExtension(files[i].name, files[i].size)) // 확장자 체크 
						return false;
					
					// add FileData to FormData
					formData.append("uploadFile", files[i]);
				}
				console.log("files.length: " + files.length);
				
				/* 썸네일 처리 */
				// 업로드 전 <input type="file"> 객체가 포함된 <div> 복사
				var cloneObj = $(".uploadDiv").clone(); 
				
				/* 업로드 된 이미지 처리 */
				var uploadResult = $(".uploadResult ul");
				
				function showUploadedFile(uploadResultArr) {
					var str = "";
					/* 일반 파일 처리 */
					$(uploadResultArr).each(function(i, obj) {
						if(!obj.fileType) {// 이미지가 아닐 경우, 간단한 아이콘 등을 이용해 첨부파일 표시 & 아이콘 클릭 시 다운로드 
							var fileCallPath = encodeURIComponent(obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName);
							str += "<li><a href='/download?fileName=" + fileCallPath 
								+ "'><img src='/resources/images/attach.png'>" + obj.fileName + "</a>" 
								+ "<span data-file=\'" + fileCallPath + "\' data-type='file'>x</span></div></li>";
						} else {// 이미지일 경우, 썸네일 이미지 보여주기 
							var fileCallPath = encodeURIComponent(obj.uploadPath + "/s_" + obj.uuid + "_" + obj.fileName);
							// <div>를 이용해서 화면 내 썸네일을 클릭하면 원본 이미지를 보여주기 
							var originPath = obj.uploadPath + "/" + obj.uuid + "_" + obj.fileName;
							originPath = originPath.replace(new RegExp(/\\/g), "/"); // 폴더 구분자인 경우 '/'로 통일 
							str += "<li><a href=\"javascript:showImage(\'" + originPath 
								+ "\')\"><img src='/display?fileName=" + fileCallPath + "'></a>" 
								+ "<span data-file=\'" + fileCallPath+ "\' data-type='image'>x</span></li>";
						}
					})
					uploadResult.append(str);
				}
				
				$.ajax({
					// processData 속성과 contentType 속성이 'false'로 설정된 경우, 전송
					url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData, 	// 전달할 데이터
					dataType: 'json',
					type: 'POST',
					success: function(result) {
						console.log(result);
						showUploadedFile(result); // 업로드 된 결과는 JSON 형태로 수신 >> 화면에 썸네일을 보여주는 등의 형태로 Ajax 처리 결과 출력
						$(".uploadDiv").html(cloneObj.html()); // 업로드 후 업로드 부분 초기화 
					}
				})
			})
			
			/* 화면에서 삭제 기능 */
			$(".uploadResult").on("click", "span", function(e) {
				var targetFile = $(this).data("file");
				var type = $(this).data("type");
				console.log("targetFile");
				
				$.ajax({
					url: '/deleteFile',
					data: { fileName: targetFile, type: type },
					dataType: 'text',
					type: 'POST',
					success: function(result) {
						alert(result);
					}
				})
			})
		})
	</script>
</body>
</html>