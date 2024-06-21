<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        .content {
            background-color:rgb(247, 245, 245); 
            width:80%;
            margin:auto;
        }
        .innerOuter {
            border:1px solid lightgray;
            width:80%;
            margin:auto;
            padding:5% 10%;
            background-color:white;
        }

        #enrollForm>table {width:100%;}
        #enrollForm>table * {margin:5px;}
        #img-area{
            width : 100%;
            margin : auto;
            text-align: center;
        }
        #img-area > img{
            width : 80%;
        }
    </style>
</head>
<body>
        
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 작성하기</h2>
            <br>

            <form id="enrollForm" method="post" action="insert.do" enctype="multipart/form-data">
                <table algin="center">
                    <tr>
                        <th><label for="title">제목</label></th>
                        <td><input type="text" id="title" class="form-control" name="boardTitle" required></td>
                    </tr>
                    <tr>
                        <th><label for="writer">작성자</label></th>
                        <td><input type="text" id="writer" class="form-control" value="${sessionScope.loginUser.userId }" name="boardWriter" readonly></td>
                    </tr>
                    <tr>
                        <th><label for="upfile">첨부파일</label></th>
                        <td><input type="file" id="upfile" class="form-control-file border" name="upfile" onchange="loadImg(this);"></td>
                    </tr>
                    <tr>
                        <th><label for="content">내용</label></th>
                        <td><textarea id="content" class="form-control" rows="10" style="resize:none;" name="boardContent" required></textarea></td>
                    </tr>
                    <tr>
                        <th colspan="2">
                            <div id="img-area">
                                <img id="kakao" src="https://t1.kakaocdn.net/friends/www/talk/kakaofriends_talk_2018.png" alt="카카오프렌즈">
                            </div>
                        </th>
                    </tr>
                </table>
                <br>
				<script>
					function loadImg(inputFile) {
						// inputFile : 현재 change가 일어난 <input type="file"> 요소 객체
						// console.log(inputFile);
						
						// console.log(inputFile.files);
						// files 속성 : 업로드 된 파일의 정보가 들어있음.
						
						// inputFile.files.length : 1 == 파일이 첨부, 0 선택 취소(콘솔창에 찍혔던)
						// => 파일의 존재 유무를 알 수 있음.
						// 파일 첨부시 inputFile.files[0]에 선택된 파일의 정보가 있음.!!
						
						   if(inputFile.files.length){  // 파일이 첨부
							
							//선택된 파일을 읽어서 영역에 미리보기 
							// 파일을 읽어들일 FileReader객체 생성
							
							const reader = new FileReader();
						// reader객체를 가지고 파일을 읽어들이는 메서드를 호출
							reader.readAsDataURL(inputFile.files[0]);
						// 해당 파일을 read하는 순간 파일만의 고유한 겁나 긴 url이 만들어짐.
						// url을 src속성의 값으로 부여할 것
						
		                     //파일 읽기가 완료되면 실행할 핸들러 정의
		                     reader.onload = e=>{
		                        
		                        //e.target ==> eventTarget
		                        //console.log(e.target);
		                    	  document.getElementById("kakao").src= e.target.result;
		                     };

		                  }else{
		                     document.getElementById('kakao').src="https://t1.kakaocdn.net/friends/www/talk/kakofriends_talk_2018.png";                  
		                  }
		            }

				</script>
                <div align="center">
                    <button type="submit" class="btn btn-primary">등록하기</button>
                    <button type="reset" class="btn btn-danger">취소하기</button>
                </div>
            </form>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
    
</body>
</html>