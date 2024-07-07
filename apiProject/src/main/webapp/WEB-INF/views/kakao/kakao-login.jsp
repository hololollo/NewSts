<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>로긘</title>
<style>
	a{
		display:inline-block;
	}
</style>

</head>
<body>
	<a id="login-btn">
		<img alt="로그인버튼" src="resources/img/kakao_login_button.png">	
	</a>
	
	<br/><br/>
	<<button id="logout">로그아웃</button>
	
	${ loginUser.id }님 환영합니다.
	${ loginUser.nuckName }은 없으시네요.
	
	<img src="${loginUser.thumbnailImg }" />
	
	<script>
		document.getElementById('login-btn').onclick = () => {
			location.href = 'https://kauth.kakao.com/oauth/authorize'
							+ '?client_id=924b1cc2f4e15791c7a9a87ee486d739'
							+ '&redirect_uri=http://localhost/api/oauth' //인가요청 uri(get방식) ? 필수파라미터
							+ '&response_type=code'
							+ '&scope=profile_nickname,profile_image'; // 선택사항
		
		};		
		
		document.getElementById('logout').onclick = () => {
			location.href = 'logout';
		}
		
	</script>
	
	<br><br><br><br><br><br><br><br><br><br><br><br>
</body>
</html>