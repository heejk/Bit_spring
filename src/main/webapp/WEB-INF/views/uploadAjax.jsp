<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
</head>
<body>
	<!-- Ajax를 이용한 파일 업로드 -->
	<div class="uploadDiv">
		<input type="file" name="uploadAjax" multiple>
	</div>
	<button id="uploadBtn">Upload</button>
	
	
	<script type="text/javascript">
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
				
				$.ajax({
					// processData 속성과 contentType 속성이 'false'로 설정된 경우, 전송
					url: '/uploadAjaxAction',
					processData: false,
					contentType: false,
					data: formData, 	// 전달할 데이터
					type: 'POST',
					success: function(result) {
						alert('Uploaded');
					}
				})
				
				
				
			})
		})
	</script>
</body>
</html>