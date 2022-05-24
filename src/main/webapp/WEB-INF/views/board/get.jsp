<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>


<%@include file="../includes/header.jsp" %>
            <div class="row">
                <div class="col-lg-12">
                    <h1 class="page-header">Board Read Page</h1>
                </div>
                <!-- /.col-lg-12 -->
            </div>
            <!-- /.row -->
            <div class="row">
                <div class="col-lg-12">
                    <div class="panel panel-default">
                        <div class="panel-heading">
                            게시글 조회 
                        </div>
                        <!-- /.panel-heading -->
                        <div class="panel-body">
                       		<div class="form-group">
                       			<label>Bno</label>
                       			<input class="form-control" name="bno" value='<c:out value="${board.bno }"/>' readonly="readonly">
                       		</div>
			                
                       		<div class="form-group">
                       			<label>Title</label>
                       			<input class="form-control" name="title" value='<c:out value="${board.title}"/>' readonly="readonly">
                       		</div>
                       		
                       		<div class="form-group">
                       			<label>Content</label>
                       			<input class="form-control" rows="3" name="content" value='<c:out value="${board.content}"/>' readonly="readonly">
                       		</div>
                       		
                       		<div class="form-group">
                       			<label>Writer</label>
                       			<input class="form-control" name="writer" value='<c:out value="${board.writer}"/>' readonly="readonly">
                       		</div>
                       		
                       		<button data-oper='modify' class="btn btn-default" 
                       				onclick="location.href='/board/modify?bno=<c:out value="${board.bno}"/>'">
                       				Modify
						    </button>
						    
							<button data-oper="list" class="btn btn-default"
							    	onclick="location.href='/board/list'">
							    	List
							</button>
                        </div>
                        <!-- /.panel-body -->
                    </div>
                    <!-- /.panel -->
                </div>
                <!-- /.col-lg-6 -->
            </div>
            <!-- /.row -->
            
			<script type="text/javascript" src="/resources/js/reply.js"></script>
			<script type="text/javascript">
				$(document).ready(function (){
					// reply module
					console.log(replyServiceEx);
					
					var bnoValue = '<c:out value="${board.bno}"/>';
					replyService.add(
						{ reply: "JS TEST", replyer: "js tester", bno: bnoValue	}, // 댓글 데이터 
						function(result) {
							alert("RESULT: " + result);
						})
				})
			</script>
 <%@include file="../includes/footer.jsp" %>       