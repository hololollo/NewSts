<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    
    <!-- 메뉴바 -->
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>회원가입</h2>
            <br>

            <form action="join.do" method="post">
                <div class="form-group">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="userId" placeholder="Please Enter ID" name="userId" required> <br>
					<div id="checkResultId" style="display:none; font-size:1em;"></div><br><br>
                    <label for="userPwd">* Password : </label>
                    <input type="password" class="form-control" id="userPwd" placeholder="Please Enter Password" name="userPwd" required> <br>

                    <label for="checkPwd">* Password Check : </label>
                    <input type="password" class="form-control" id="checkPwd" placeholder="Please Enter Password" required> <br><!-- DB로 보낼 이유 없음. 단순확인용 -->
					<div id="checkResultPwd" style="display:none; font-size:1em;"></div><br><br>
                    
                    
                    
                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" placeholder="Please Enter Name" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" placeholder="Please Enter Email" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" placeholder="Please Enter Age" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" placeholder="Please Enter Phone (-없이)" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" placeholder="Please Enter Address" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="" checked> <!-- radio와 checkbox의 차이점 : radio 하나만. checkbox 중복허용 -->
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" id="join-btn" class="btn btn-primary disabled">회원가입</button> <!-- 중복체크를 하지않으면 누를수 없게 -->
                    <button type="reset" class="btn btn-danger">초기화</button>
                </div>
            </form>
            
            <script>
            	$(() => {
            		
            		const $idInput = $('.form-group #userId');
            		const $checkResultId = $('#checkResultId');
            		const $joinSubmit = $('#join-btn');
            		// 비밀번호 확인용 속성 추가
            		const $pwdInput = $('#userPwd'); // 비밀번호
                    const $checkPwdInput = $('#checkPwd'); // 비밀번호확인
                    const $checkResultPwd = $('#checkResultPwd');
            		
            		$idInput.keyup(() => {
            		
            			console.log($idInput.val().length);	
            			// 불필요한 DB접근을 제한하기 위해 다섯글자 이상으로 입력했을 때만 AJAX요청을 보내서 체크
            			if($idInput.val().length >= 5){
            				
            				$.ajax({
            					url : 'idCheck.do',
            					type : 'get',
            					data : {checkId : $idInput.val()
            						},
            						success : response => {
            							// console.log(response);
            							// NNNNN / NNNNY
            							if(response.substr(4) === 'N') { // 중복이면 
            								$checkResultId.show().css('color', 'crimson').text('중복입니다!');
            							    $joinSubmit.attr('disabled', true); 
            							} else { // 중복이 아니면
            								$checkResultId.show().css('color','lightgreen').text('사용가능합니다!');
            								$joinSubmit.removeAttr('disabled');
            							}
            						},
            						error : () => { 
            							console.log('아이디 중복체크용 AJAX통신 실패 ㅠㅠ')
            						}
            				});
            			}
            			else {
            				$checkResultId.hide();
            				$joinSubmit.attr('disabled', true);
            			}
            		});
            		//비밀번호 확인
            		 $checkPwdInput.keyup(() => {
                         if ($pwdInput.val() === $checkPwdInput.val()) {
                             $checkResultPwd.show().css('color', 'lightgreen').text('비밀번호가 일치합니다!');
                             $joinSubmit.removeAttr('disabled'); // 입력할 수 있게.
                         } else {
                             $checkResultPwd.show().css('color', 'crimson').text('비밀번호가 일치하지 않습니다!');
                             $joinSubmit.attr('disabled', true);
                         }
                     });
            	});
            </script>
            
        </div>
        <br><br>

    </div>

    <!-- 푸터바 -->
    <jsp:include page="../common/footer.jsp" />

</body>
</html>