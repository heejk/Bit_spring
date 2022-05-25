<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@include file="../includes/header.jsp" %>

	<div class="row">
		<div class="col-lg-12">
		    <h1 class="page-header">Board Register Page</h1>
		</div>
		<!-- /.col-lg-12 -->
	</div>
	<!-- /.row -->
            
	<div class="row">
		<div class="col-lg-12">
			<div class="panel panel-default">
	        	<div class="panel-heading">
		        	게시글 목록
	           	</div>
	             <!-- /.panel-heading -->
	             
				<div class="panel-body">
          	     	<form role="form" action="/board/register" method="post">
      	         		<div class="form-group">
                 			<label>Title</label>
                 			<input class="form-control" name="title">
                 		</div>
                 		
                 		<div class="form-group">
                 			<label>Content</label>
                 			<input class="form-control" rows="3" name="content">
                 		</div>
                 		
                 		<div class="form-group">
                 			<label>Writer</label>
                 			<input class="form-control" name="writer">
                 		</div>
                 		
                 		<button type="submit" class="btn btn-primary">Submit</button>
                 		<button type="reset" class="btn btn-default">Reset</button>
                 	</form>
                 	
                 	<p>
                 	
                 	<div class="row">
                 		<div class="col-lg-12">
							<div class="panel panel-info">
								<div class="panel-heading">File Attach</div>
								<div class="panel-body">
									<div class="form-group uploadDiv">
										<input type="file" name='uploadFile' multiple>
									</div>
									<div class="form-group uploadResult">
										<ul></ul>
									</div>
								</div>
							</div>
						</div>
					</div>

                 </div>
                 <!-- /.panel-body -->
         	</div>
        	<!-- /.panel -->
		</div>
		<!-- /.col-lg-6 -->
	</div>
 	<!-- /.row -->
            
	<script>
		var formObj = $("form[role='form']");
	    
	    $("button[type='submit']").on("click", function(e){
	       e.preventDefault();
	       console.log("submit clicked");
	       
	       /* 등록을 위한 화면 처리 */
	       // <form> 전송시 <input type='hidden'> 태그들을 첨부된 파일 수 만큼 생성해서 같이 전송 
	       var str = "";
	       $(".uploadResult ul li").each(function(i, obj) {
	    	   var jobj = $(obj);
	    	   console.dir(jobj);
	    	   str += "<input type='hidden' name='attachList[" + i + "].fileName' value='" + jobj.data("filename") + "'>";
	    	   str += "<input type='hidden' name='attachList[" + i + "].uuid' value='" + jobj.data("uuid") + "'>";
	    	   str += "<input type='hidden' name='attachList[" + i + "].uploadPath' value='" + jobj.data("path") + "'>";
	    	   str += "<input type='hidden' name='attachList[" + i + "].fileType' value='" + jobj.data("type") + "'>";
	       })
	       formObj.append(str).submit();
	    });
	    
	    $("input[type='file']").change(function(e){
			var formData = new FormData();
			var inputFile = $("input[name='uploadFile']");
			var files = inputFile[0].files;
			var cloneObj = $(".uploadDiv").clone();
			console.log(files);
			
			var uploadResult = $(".uploadResult ul");
	            
			function showUploadedFile(uploadResultArr){
				var str = "";
	            /* 일반 파일 처리 */
	            $(uploadResultArr).each(function(i, obj){
					if(!obj.fileType){
						var fileCallPath  = encodeURIComponent(obj.uploadPath+"/"+obj.uuid+"_"+obj.fileName);
			      		str +="<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'><div>";
				      	str +="<span>"+obj.fileName+"</span>";
				      	str +="<button type='button' data-file=\'"+fileCallPath+"\' data-type='file'  class='btn btn-warning btn-circle'><i class='fa fa-times'></i></button><br>";
				      	str +="<img src='/resources/images/attach.png'></a>"
				      	str +="</div></li>";
				   	}else{
				   		var fileCallPath = encodeURIComponent(obj.uploadPath+"/s_"+obj.uuid+"_"+obj.fileName);
			      		str +="<li data-path='"+obj.uploadPath+"' data-uuid='"+obj.uuid+"' data-filename='"+obj.fileName+"' data-type='"+obj.fileType+"'><div>";
			      		str +="<span>"+obj.fileName+"</span>";
			      		str +="<button type='button' data-file=\'"+fileCallPath+"\' data-type='image'  class='btn btn-warning btn-circle'> <i class='fa fa-times'></i></button><br>";
			     		str +="<img src='/display?fileName="+fileCallPath+"'>"; 
			      		str +="</div></li>";
			   		}
				});

				uploadResult.append(str);
			}//showUpload
	         
	        var regex = new RegExp("(.*?)\.(exe|sh|zip|alz)$");
	        var maxSize = 5242880;
	         
	        function checkExtension(fileName, fileSize){
				if(fileSize >= maxSize){
					alert("파일 크기 초과");
                	return false;
	            }
	            if(regex.test(fileName)){
	               	alert("해당 종류의 파일은 업로드 할 수 없음");
	               	return false;
	            }
	            return true;
	         }
	         
	        //업로드 결과
            for(var i=0; i<files.length; i++){
            	if(!checkExtension(files[i].name, files[i].size))
	                  return false;
	           	
				formData.append("uploadFile", files[i]);
			}
	        
            console.log("files.length : "+files.length);
	            
            $.ajax({
            	url:'/uploadAjaxAction',
               	processData: false,
               	contentType: false,
               	data: formData,
               	type: 'POST',
               	dataType: 'json',
               	success: function(result){
               		console.log(result);
                  	showUploadedFile(result);
                  	$(".uploadDiv").html(cloneObj.html());
               	}
            });
            
	      $(".bigPictureWrapper").on("click", function(e){
	         $(".bigPicture").animate({width: '0%', height:'0%'}, 1000);
	         setTimeout(function(){
	            $('.bigPictureWrapper').hide();
	         }, 1000);
	      });
	               
	         
	         $(".uploadResult").on("click","button",function(e){
	            var targetFile = $(this).data("file");
	            var type = $(this).data("type");
	            var targetLi = $(this).closest("li");
	            console.log(targetFile);
	            
	            $.ajax({
	               url:'/deleteFile',
	               data:{fileName: targetFile, type : type},
	               dataType:'text',
	               type:'post',
	               success:function(result){
	                  alert(result);
	                  targetLi.remove();}
	               });
	            });    //uploadResult End
	         
	    });
	</script>

 <%@include file="../includes/footer.jsp" %>       