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
	function add (reply, callback, error) {
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
	return { add: add }; // 모듈 패턴으로 외부에 노출하는 정보 
})(); 
