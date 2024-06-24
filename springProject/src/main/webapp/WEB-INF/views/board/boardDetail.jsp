<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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

        table * {margin:5px;}
        table {width:100%;}
    </style>
</head>
<body>
        
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter">
            <h2>게시글 상세보기</h2>
            <br>

            <a class="btn btn-secondary" style="float:right;" href="">목록으로</a>
            <br><br>

            <table id="contentArea" algin="center" class="table">
                <tr>
                    <th width="100">제목</th>
                    <td colspan="3">${board.boardTitle }</td>
                </tr>
                <tr>
                    <th>작성자</th>
                    <td>${board.boardWriter }</td>
                    <th>작성일</th>
                    <td>${board.createDate }</td>
                </tr>
                <tr>
                    <th>첨부파일</th>
                    
                    <c:choose>
                    <c:when test="${empty board.originName }">	
                    <td colspan="3">
                    	파일이 존재하지 않습니다.
                        
                    </td>
                    </c:when>
                    <c:otherwise>
                   		<td colspan="3">
                    		<a href="${board.changeName }" download="${board.originName }">${board.originName }</a>
                    </c:otherwise>
                    </c:choose>
                    
                </tr>
                <tr>
                    <th>내용</th>
                    <td colspan="3"></td>
                </tr>
                <tr>
                    <td colspan="4"><p style="height:150px;">${board.boardContent }</p> 
                    <img src="${board.changeName}" alt=" " style="max-width: 50%; height: 50%;"></td>
                </tr>
            </table>
            <br>

            <div align="center">
                <!-- 수정하기, 삭제하기 버튼은 이 글이 본인이 작성한 글일 경우에만 보여져야 함 -->
                <c:if test="${ sessionScope.loginUser.userId eq requestScope.board.boardWriter }">
               <!--   <form action="boardupdate.do" method="post">
	                <input type="hidden" name="boardNo" value="${board.boardNo }" />--> <!-- 요청시 전달값 안보이게 -->
	                <a class="btn btn-primary" onclick="postSubmit(this.innerHTML);">수정하기</a>
              
                <!--  <form test="boarddelete.do" method="post">
                	<input type="hidden" name="boardNo" value="${board.boardNo }" />--> <!-- 요청시 전달값 안보이게 -->
	                <a class="btn btn-danger" onclick="postSubmit(this.innerHTML);">삭제하기</a>
            	</c:if>
            	<!-- 코드가 같고 action속성만 변경되면 될것같다. -->
            	<form method="post" action="" id="postForm">
            		<input type="hidden" name="boardNo" value="${board.boardNo }" />
            		<input type="hidden" name="filePath" value="${board.changeName }" />
            		
            	</form>
            	<script>
            		function postSubmit(el) {
            			// console.log('날 불렀니?')
            		/*
            		if('수정하기' === el) {
            			$('#postForm').attr('action', 'boardUpdateForm.do').submit();
            		} else {
            			$('#postForm').attr('action', 'boardDelete.do').submit();
            		}
            		*/	
            			const attrValue = '수정하기' === el ? 'boardUpdateForm.do' : 'boardDelete.do';
            			
            			$('#postForm').attr('action', attrValue).submit();
            		
            		}
            	</script>
            </div>
            
            <br><br>
			
            <!-- 댓글 기능은 나중에 ajax 배우고 나서 구현할 예정! 우선은 화면구현만 해놓음 -->
            <table id="replyArea" class="table" align="center">
                <thead>
                    <tr>
                        <th colspan="2">
                            <textarea class="form-control" name="" id="content" cols="55" rows="2" style="resize:none; width:100%;"></textarea>
                        </th>
                        <th style="vertical-align:middle"><button class="btn btn-secondary">등록하기</button></th> 
                    </tr>
                    <tr>
                        <td colspan="3">댓글(<span id="rcount">3</span>)</td>
                    </tr>
                </thead>
                <tbody>
                    <tr>
                        <th>user02</th>
                        <td>ㅋㅋㅋㅋㅋㅋㅋㅋㅋㅋ꿀잼</td>
                        <td>2023-03-12</td>
                    </tr>
                    <tr>
                        <th>user01</th>
                        <td>재밌어요</td>
                        <td>2023-03-11</td>
                    </tr>
                    <tr>
                        <th>admin</th>
                        <td>댓글입니다!!</td>
                        <td>2023-03-10</td>
                    </tr>
                </tbody>
            </table>
        </div>
        <br><br>

    </div>
    
    <jsp:include page="../common/footer.jsp" />
</body>
</html>