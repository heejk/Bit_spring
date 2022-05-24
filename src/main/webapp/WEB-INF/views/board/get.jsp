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
	        	<div class="panel-heading">게시글 조회</div>
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
		           		
	           		<button data-oper='modify' class="btn btn-info" 
		           			onclick="location.href='/board/modify?bno=<c:out value="${board.bno}"/>'">
							Modify
		   			</button>
		   
					<button data-oper="list" class="btn btn-success"
					    	onclick="location.href='/board/list'">
					    	List
					</button>
		
					<p>
		
					<!-- 댓글 목록 처리 -->
					<div class="panel panel-default">  
						<div class="panel-heading"><i class="fa fa-comments fa-fw"></i> Reply<button id='addReplyBtn' class="btn btn-primary btn-xs pull-right">New Reply</button></div>
						<div class="panel-body">
							<ul class="chat">
								<li class="left clearfix" data-rno="12">
									<div>
										<div class="header">
					                        <strong class="primary-font">user00</strong> 
					                        <small class="pull-right text-muted">2021-05-18 13:13</small>
					                 	</div>
					                 	<p>Good job</p>
									</div>
								</li>
							</ul>
		            	</div>
					</div>
					<!-- 모달창 생성 -->
					<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModallabel" aria-hidden="true">
						<div class="modal-dialog">
							<div class="modal-content">
								<div class="modal-header">
					            	<button type="button" class="close" data-dismiss="modal" aria-hidden="true">&times;</button>
						            <h4 class="modal-title" id="myModalLabel">REPLY MODAL</h4>
								</div>
							       
								<div class="modal-body">
									<div class="form-group">
										<label>Reply</label>
										<input class="form-control" name="reply" value="New Reply">
						       		</div>
							       		
						       		<div class="form-group">
						       			<label>Replyer</label>
						       			<input class="form-control" name="replyer" value="Replyer">
						       		</div>
						       		
						       		<div class="form-group">
						       			<label>Reply Date</label>
						       			<input class="form-control" name="replyDate" value="ReplyDate">
						       		</div>
								</div>
									
						        <div class="modal-footer">
									<button id="modalModBtn" type="button" class="btn btn-info">Modify</button>
						            <button id="modalRemoveBtn" type="button" class="btn btn-danger" >Remove</button>
						            <button id="modalRegisterBtn" type="button" class="btn btn-primary" >Register</button>
						            <button id="modalCloseBtn" type="button" class="btn btn-default" >Close</button>
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
	</div>
	<!-- /.row -->
	         
	<script type="text/javascript" src="/resources/js/reply.js"></script>
	<script type="text/javascript">
	$(document).ready(function (){
		// reply module
		console.log(replyServiceEx);
		
		var bnoValue = '<c:out value="${board.bno}"/>';
		
		/* 댓글 생성 */
		/* replyService.add(
			{ reply: "JS TEST", replyer: "js tester", bno: bnoValue	}, // 댓글 데이터 
			function(result) {
				alert("RESULT: " + result);
			}
		) */
			
		/* 댓글 조회 */
		/* replyService.getList(
			{ bno: bnoValue, page: 1 },
			function(list) {
				list.forEach(function(item){
					// 해당 리스트를 콘솔에 출력 
					console.log(item);
				})
			}
		) */
		
		/* 특정 댓글 조회 */
		/* replyService.get(
			7, // rno = 7
			function(data) {
				console.log(data);
			}
		)  */
		
		/* 댓글 삭제 */
		/* replyService.remove(
			3, // rno = 3
			function(count) {
				console.log(count);
				if(count === "success")
					alert("REMOVED");
			}, 
			function(err) {
				alert("error occurred....");
			}
		) */
		
		/* 댓글 수정 */
		/* replyService.update(
			{ rno: 22, bno: bnoValue, reply: "modified reply...." },
			function(result) {
				alert("Modify Success");
			}
		) */
		
		/* 댓글 목록 이벤트 처리 */
		var replyUL = $(".chat");
		showList(1); // showList(페이지번호): 해당 게시글의 댓글을 가져온 후 <li> 태그를 만들어서 화면에 출력 
		function showList(page) {
			replyService.getList(
				{bno: bnoValue, page: page || 1 },
				function(list) {
					var str = "";
					
					if(list == null || list.length == 0){
						replyUL.html("");
						return;
					}
					
					for (var i=0, len=list.length || 0; i < len; i++) {
						str += "<li class='left clearfix' data-rno='" + list[i].rno + "'>";
						str += "<div><div class='header'><strong class='primary-font'>" + list[i].replyer + "</strong>";
						str += "<small class='pull-right text-muted'>" + replyService.displayTime(list[i].replyDate) + "</small></div>";
	                		str += "<p>" + list[i].reply + "</p></div></li>";
					}
					
					replyUL.html(str);
				}
			)
		}
		
		/* 모달창 출현 */
		var modal = $(".modal");
		var modalInputReply = modal.find("input[name='reply']");
		var modalInputReplyer = modal.find("input[name='replyer']");
		var modalInputReplyDate = modal.find("input[name='replyDate']");
		
		var modalModBtn = $("#modalModBtn");
		var modalRemoveBtn = $("#modalRemoveBtn");
		var modalRegisterBtn = $("#modalRegisterBtn");
		
		$("#addReplyBtn").on("click", function(e) {
			modal.find("input").val("");
			modalInputReplyDate.closest("div").hide();
			modal.find("button[id!='modalCloseBtn']").hide();
			modalRegisterBtn.show();
			$(".modal").modal("show");
		})
		
		/* 새로운 댓글 처리 */
		modalRegisterBtn.on("click", function(e) {
			var reply = {
				reply: modalInputReply.val(),
				replyer: modalInputReplyer.val(),
				bno: bnoValue
			};
			
			replyService.add(reply, function(result) {
				alert(result);						// 댓글 등록이 정상 처리됨을 알림 
				modal.find("input").val("");		// 댓글 등록이 정상적으로 이루어지면 내용을 지움
				modal.modal("hide", showList(1));	// 모달창 닫음
			})						// ㄴ 새로 등록된 댓글이 보이도록 목록 갱신 
		})
		
		/* 특정 댓글의 클릭 이벤트 */
		// 댓글은 Ajax로 가져온 결과를 DOM에 추가하는 형태이므로 이벤트 위임(delegation) 방식을 이용해 처리 
		$(".chat").on("click", "li", function(e) {
			var rno = $(this).data("rno");
			//console.log(rno);
			
			replyService.get(rno, function(reply) {
				modalInputReply.val(reply.reply);
				modalInputReplyer.val(reply.replyer);
				modalInputReplyDate.val(replyService.displayTime(reply.replyDate)).attr("readonly", "readonly");
				modal.data("rno", reply.rno);
				
				modal.find("button[id!='modalCloseBtn']").hide();
				modalModBtn.show();
				modalRemoveBtn.show();
				$(".modal").modal("show");
			})
		})
		
		/* 댓글 수정 이벤트 */
		modalModBtn.on("click", function(e) {
			var reply = {
				rno: modal.data("rno"),
				reply: modalInputReply.val(),
			};
			
			replyService.update(reply, function(result) {
				alert(result);						 
				modal.modal("hide");
				showList(1);
			})						 
		})
		
		/* 댓글 삭제 이벤트 */
		modalRemoveBtn.on("click", function(e) {
			var rno = modal.data("rno");
			
			replyService.remove(rno, function(result) {
				alert(result);						 
				modal.modal("hide");
				showList(1);
			})						 
		})
		
	})
	</script>
 <%@include file="../includes/footer.jsp" %>       