<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>var, let, const</h1>
	<h2>변수 선언 시 사용할 수 있는 키워드들</h2>
	<button onclick="defDefclare()">안녕 나는 버튼</button>
		
	<script>
	// var, let, const 차이?
		function defDeclare() {
			
			var userId = 'user01';
			console.log(userId);
			
			var userId = 'user02';
			console.log(userId);
			
			console.log('-------------')
			let userName = '홍길동';
			console.log(userName);
			
			console.log('-------------')
			const userPwd = 123; // 반드시 선언을 통해 초기화를 해야함.
			console.log(userPwd);
	
			const titleEl = document.getElementById('title');
			// 속성의 값을 바꾸는 것이기 때문에 요소속성 객체의 불변성을 지킬 수 있다.
			
			
		}
	</script>
	
	<h1 id="title">요소</h1>
	
	<button onclick="keywordScope()">버튼</button>
	
	<script>
		function keywordScope() {
			
			// let, const
			
			if(1) {
				let gender = 'M';
				const hobby = '취미';
				// if문을 나가면 생명주기가 끝난다.	
				name = '강범준'; 
				// 사실 var, let, const 안붙여도 변수선언가능.
			}
			// var == functionScope == 변수가 선언된 "함수영역" 내에서 사용가능
			
			for(var i = 0; i < 5; i++) {
				
			}
			console.log(i);
			// var로 선언되었기 때문에 function안에서는 어디에서든 사용가능.
			
		}
	</script>
</body>
</html>