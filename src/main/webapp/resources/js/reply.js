/**
 * 
 */
 
console.log("Reply Module........");
 

var replyServiceEx = (function() {
	// replyService 라는 변수에 name이라는 속성, "AAA"라는 값을 가진 객체 할당
	return { name: "AAA" };
})(); 


/* 모듈 패턴: 즉시 실행하는 함수 내부에 메소드를 구성해서 객체로 반환 */
var replyService = (function() {
	/* 댓글 생성 */
	function add(reply, callback, error) {
		console.log("reply....");
		$.ajax({
			type: 'post',
			url: '/replies/new',
			data: JSON.stringify(reply),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr) {
				if (callback) 
					callback(result);
			},
			error: function(xhr, status, er) {
				if (error)
					error(er);
			}
		})	
	}
	
	/* 댓글 조회 */
	function getList(param, callback, error) {
		var bno = param.bno;
		var page = param.page || 1;
		
		$.getJSON("/replies/pages/" + bno + "/" + page + ".json",
					function(data) {
						if (callback)
							callback(data);
					}).fail(function(xhr, status, err) {
						if (error)
							error();
					});
	}
	
	/* 특정 댓글 조회 */
	function get(rno, callback, error) {
		$.get("/replies/" + rno + ".json",
				function(data) {
					if (callback)
						callback(data);
				}
			).fail(function(xhr, status, err) {
				if (error)
					error();
			});
	}
	
	/* 댓글 삭제 */
	function remove(rno, callback, error) {
		$.ajax({
			type: 'delete',
			url: '/replies/' + rno,
			success: function(deleteResult, status, xhr) {
				if(callback) 
					callback(deleteResult);
			},
			error: function(xhr, status, er){
				if(error)
					error(er);
			}
		})
	}
	
	/* 댓글 수정 */
	function update(reply, callback, error) {
		$.ajax({
			type: 'put',
			url: '/replies/' + reply.rno,
			data: JSON.stringify(reply),
			contentType: "application/json;charset=utf-8",
			success: function(result, status, xhr) {
				if (callback) 
					callback(result);
			},
			error: function(xhr, status, er) {
				if (error)
					error(er);
			}
		})	
	}
	
	/* 댓글 목록 시간 처리 */
	function displayTime(timeValue) {
		var today = new Date();
		var gap = today.getTime() - timeValue;
		var dateObj = new Date(timeValue);
		var str = "";
		
		if (gap < (1000 * 60 * 60 * 24)) {
			var hh = dateObj.getHours();
			var mi = dateObj.getMinutes();
			var ss = dateObj.getSeconds();
			return [(hh>9 ? '' : '0') + hh, ':', (mi > 9 ? '' : '0') + mi, ':', (ss > 9 ? '' : '0') + ss].join('');
		} else {
			var yy = dateObj.getFullYear();
			var mm = dateObj.getMonth() + 1;
			var dd = dateObj.getDate();
			return [yy, '/', (mm > 9 ? '' : '0')+mm, '/', (dd > 9 ? '' : '0') + dd].join('');
		}
	}
	
	// 모듈 패턴으로 외부에 노출하는 정보
	return { add: add, getList: getList, get: get, remove: remove, update: update, displayTime: displayTime };  
})(); 