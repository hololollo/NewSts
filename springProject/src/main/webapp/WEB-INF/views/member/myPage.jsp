<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
</head>
<body>
    
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>마이페이지</h2>
            <br>

            <form action="update.do" method="post">
                <div class="form-group">
                    <label for="userId">* ID : </label>
                    <input type="text" class="form-control" id="userId" value="${sessionScope.loginUser.userId }" name="userId" readonly> <br>

                    <label for="userName">* Name : </label>
                    <input type="text" class="form-control" id="userName" value="${sessionScope.loginUser.userName }" name="userName" required> <br>

                    <label for="email"> &nbsp; Email : </label>
                    <input type="text" class="form-control" id="email" value="${sessionScope.loginUser.email }" name="email"> <br>

                    <label for="age"> &nbsp; Age : </label>
                    <input type="number" class="form-control" id="age" value="${sessionScope.loginUser.age }" name="age"> <br>

                    <label for="phone"> &nbsp; Phone : </label>
                    <input type="tel" class="form-control" id="phone" value="${sessionScope.loginUser.phone }" name="phone"> <br>
                    
                    <label for="address"> &nbsp; Address : </label>
                    <input type="text" class="form-control" id="address" value="${sessionScope.loginUser.address }" name="address"> <br>
                    
                    <label for=""> &nbsp; Gender : </label> &nbsp;&nbsp;
                    <input type="radio" id="Male" value="M" name="">
                    <label for="Male">남자</label> &nbsp;&nbsp;
                    <input type="radio" id="Female" value="F" name="">
                    <label for="Female">여자</label> &nbsp;&nbsp;
                    <script>
                    	window.onload = () => {
                    		
                    		// 속성(type, id, value, name...) 선택자
                    		// SessionScope.loginUser.gender
                    		 document.querySelector('input[value=${sessionScope.loginUser.gender}]').checked = true;
                    	}
                    	
                    	//	$(() => {
                    	//		console.dir()
                    			
                    	//		$('input[value="${sessionScope.loginUser.gender}"]').attr('checked', true);
                    			
                    			
                    			
                    			//console.log(1 + 1); // 2
                    			//console.log(1 + '1'); // 11
                    			//console.log('1' - 1); // 0
                    			//console.log([] == 0); // true
                    			
                    	//	})
                    		
                    </script>
                </div> 
                <br>
                <div class="btns" align="center">
                    <button type="submit" class="btn btn-primary">수정하기</button>
                    <button type="button" class="btn btn-danger" data-toggle="modal" data-target="#deleteForm">회원탈퇴</button>
                </div>
            </form>
        </div>
        <br><br>
        
    </div>

    <!-- 회원탈퇴 버튼 클릭 시 보여질 Modal -->
    <div class="modal fade" id="deleteForm">
        <div class="modal-dialog modal-sm">
            <div class="modal-content">

                <!-- Modal Header -->
                <div class="modal-header">
                    <h4 class="modal-title">회원탈퇴</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>

                <form action="delete.do" method="post">
                <input type="hidden" value="${sessionScope.loginUser.userId }" name="userID" />
                    <!-- Modal body -->
                    <div class="modal-body">
                        <div align="center">
                            탈퇴 후 복구가 불가능합니다. <br>
                            정말로 탈퇴 하시겠습니까? <br>
                        </div>
                        <br>
                            <label for="userPwd" class="mr-sm-2">Password : </label>
                            <input type="text" class="form-control mb-2 mr-sm-2" placeholder="Enter Password" id="userPwd" name="UserPwd"> <br>
                    </div>
                    <!-- Modal footer -->
                    <div class="modal-footer" align="center">
                        <button type="button" class="btn btn-danger" onclick="return deletePrompt();">탈퇴하기</button>
                    </div>
                </form>
                
                <script>
                	// 회원 탈퇴하려면 확인창이 띄워지게
                	function deletePrompt() {
                				//prompt의 확인버튼 : 입력한 값을 return(아무것도 입력하지않으면 빈 문자열) / 취소버튼 : null값을 반환
                	// const value	= prompt('탈퇴를 하고 싶으시면 비밀번호를 다시 한번 입력해주세요.');
                		// console.log(value);
                		
                		// console.log('1' == 1) => true반환. 자바스크립트의 특징:속성과 값으로 이루어져있음. (★타입까지 비교하려면 ===)
                		// 즉, console.log(typeof('1') === typeof(1)); 이 올바른 방법.
                		//if(value === '어쩌고저쩌고') {
                			// submit요청(탈퇴)를 보냄.
                		//	return true;
                		//} else {
                			// submit요청을 안가게 함
                		//	return false;
                		//}
                		return prompt('탈퇴를 하고 싶으시면 비밀번호를 다시 한번 입력해주세요.') === '어쩌고저쩌고' ? true : false;
                	}	
                </script>
            </div>
        </div>
    </div>

    <jsp:include page="../common/footer.jsp" />

</body>
</html>