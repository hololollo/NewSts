<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<h1>이메일을 입력해주세요~!</h1>
	
	<form action="auth-mail" method="post">
		<input type="text" name="email" />
		<input type="submit" value="인증번호받기" />
	</form>
</body>
</html>