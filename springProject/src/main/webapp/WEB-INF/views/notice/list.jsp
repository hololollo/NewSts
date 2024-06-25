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

        #noticeList {text-align:center;}
        #noticeList>tbody>tr:hover {cursor:pointer;}

        #pagingArea {width:fit-content; margin:auto;}
        
        #searchForm {
            width:80%;
            margin:auto;
        }
        #searchForm>* {
            float:left;
            margin:5px;
        }
        .select {width:20%;}
        .text {width:53%;}
        .searchBtn {width:20%;}
    </style>
</head>
<body>
    
    <jsp:include page="../common/menubar.jsp" />

    <div class="content">
        <br><br>
        <div class="innerOuter" style="padding:5% 10%;">
            <h2>공지사항</h2>
            <br>
            <!-- 로그인 후 상태일 경우만 보여지는 글쓰기 버튼 -->
            <c:if test="${not empty sessionScope.loginUser}">
            	<a class="btn btn-secondary" style="float:right;" href="noticeForm.do">글쓰기</a>
            </c:if>
            <br>
            <br>
     <!-- 글 목록 -->
            <table id="noticeList" class="table table-hover" align="center">
                <thead>
                    <tr>
                        <th>글번호</th>
                        <th>제목</th>
                        <th>작성자</th>
                        <th>작성일</th>
                    </tr>
                </thead>
                 <tbody>
                	<c:choose>
                		<c:when test="${ list.size() == 0}"> <!-- 1이면 있을 때 -->
                			<tr>
                				<td colspan="6">조회된 결과가 존재하지 않습니다.</td>
                			</tr>
                		</c:when>
                		<c:otherwise> <!-- otherwise를 썼기 때문에 else같은 성격 -->
	                		<c:forEach var="notice" items="${list }" varStatus="status">
			                    <tr class="notice-detail">
			                        <td>${notice.noticeNo }</td>
			                        <td>${notice.noticeTitle }</td>
			                        <td>${notice.noticeWriter }</td>
			                        <td>${notice.createDate }</td>
			                        <!-- 
			                        <td>
			                        	<c:if test="${ not empty board.originName }">
			                        		🖼️
			                        	</c:if>
			                        </td>
			                         -->
			                    </tr>
	                    	</c:forEach>
                    	</c:otherwise>
                    </c:choose>
                </tbody>

            </table>
            <br>
            <script>
               $(() => {
                  
                  // 1번 어떤 친구들을 => eventTarget
                  // 2번 언제      => eventType
                  
                  // console.log($('.board-detail'));
                  
                  // 자바스크립트의 이벤트 처리
                  // .addEventListener() -> 권장사항(순수 자바스크립트로 이벤트를 사용할 때)
                  // on이벤트속성 = 
                  // 익명함수 대입~
                  // 인라인 방식
                  
                  // 하지만 인터넷 익스플로러 같은 곳에서는 동작하지 않을 수도 있기 때문에 다른 방법도 알아두는게 좋음.
                  
                  // jQuery의 강점기가 길었기 때문에 jQuery를 사용한 이벤트도 알고있는게 좋다.
                  
                  // jQuery의 이벤트 처리
                  // $
                  // .on()       ex) $('.board-detail').on('click', handler()) => 권장사항!
                  // 이벤트타입()      ex) $('.board-detail').click(e => {});
                  
                  console.dir('pagingArea');
                  
                  $('.notice-detail').click(e => {
                     // 클릭하면 컨트롤러에 요청을 보내게 하고싶음
                     // URL 변경
                     // console.log(window);
                     
                     // window.console.log('asdfg');
                     
                     // console.log(location);
                     
                     // location의 href 속성값을 변경하면 됨
                     
                     /*
                     const student = {
                        name : '홍길동',
                        grade : 1
                     };
                     */
                     
                     // console.log(student);
                     
                     // 자바스크립트에서 객체의 변수를 바꾸는법
                     // 1.객체명 2. 점 3. 바꿀 변수명 4. 값을 대입
                     // student.grade = 2;
                     
                     // e.target : 이벤트가 실제로 발생한 요소를 가리킨다. 사용자가 클릭하거나 입력한 가장 하위의 요소를 참조한다.
                     // e.currentTarget : 현재 이벤트를 처리하고 있는 요소를 가리킨다. 이벤트 핸들러가 바인딩된 요소를 참조한다.
                     
                     // console.log(e.target);
                     
                     // 우리가 알아야하는건 tr의 자식요소 중에서 첫번째 자식요소의 content값이 필요함
                     // console.log(e.currentTarget.id.split('-')[1]);
                     
                     // e.currentTarget은 자바스크립트 방식이고, children() 메서드는 jQuery 방식이라서 같이 쓸 수 없음
                     // jQuery 메서드를 사용하려면 jQuery 방식으로 선택한 요소를 사용해야함!
                     // console.log(e.currentTarget.children()); -> 그래서 에러발생
                     
                  // find('선택자') <-- 활용도가 가장 높음
                  // children() 
                  
                  // eq(x) : 요소들중에서 인덱스가 x인 요소를 선택함 
                  // text() : 선택한 요소의 content 값을 가져옴
                  location.href = 'notice-detail?noticeNo=' + $(e.currentTarget).children().eq(0).text();
                  
                  // location.href = '' + e.currentTarget.id.split('-')[1];
                  });
               });
            </script>
            
	<!-- 페이징 -->
            <div id="pagingArea">
                <ul class="pagination">
                
                <!-- 이전 -->
				<c:choose>
				    <c:when test="${ pageInfo.startPage eq pageInfo.currentPage }">
				        <li class="page-item disabled">
				            <a class="page-link" href="#">이전</a>
				        </li>
				    </c:when>
				    <c:when test="${ empty condition }">
				        <li>
				        	<a class="page-link" href="noticelist?page=${ pageInfo.currentPage - 1 }">이전</a>
			        	</li>
				    </c:when>
				    <c:otherwise>
				        <li>
				        	<a class="page-link" href="notice-search.do?page=${ pageInfo.currentPage - 1 }&condition=${condition}&keyword=${keyword }">이전</a>
			        	</li>
				    </c:otherwise>
				</c:choose>
                    
                    <c:forEach begin="${ pageInfo.startPage }" end="${ pageInfo.endPage }" var="p" >
	                    <c:choose>
	                    	<c:when test="${ empty condition }"> 
		                    	<li class="page-item">
		                    		<a class="page-link" href="noticelist?page=${ p }">${ p }</a>
		                    		<!-- 몇번 페이지로 요청하는지 -->
		                    	</li>
	                    	</c:when>
	                    	<c:otherwise>
	                    	 	<li class="page-item">
		                    		<a class="page-link" href="notice-search.do?page=${ p }&condition=${condition}&keyword=${keyword }">${ p }</a>
		                    	</li>
	                    	</c:otherwise>
	                    </c:choose>
                    </c:forEach>
                    
                    
                    <c:choose>
                    <c:when test="${ pageInfo.maxPage eq pageInfo.currentPage }">
                    	<li class="page-item disabled">
                    		<a class="page-link" href="#">다음</a>
                    	</li>
                    </c:when>
                    <c:when test="${ empty condition }">
                    <li class="page-item">
                    	<a class="page-link" href="noticelist?page=${ pageInfo.currentPage + 1 }">다음</a>
                    </li>
                    </c:when>
                    <c:otherwise>
                    	 <li>
				        	<a class="page-link" href="notice-search.do?page=${ pageInfo.currentPage + 1 }&condition=${condition}&keyword=${keyword }">다음</a>
			        	</li>
                    </c:otherwise>
                    </c:choose>
                </ul>
            </div>

            <br clear="both"><br>

            <form id="searchForm" action="notice-search.do" method="get" align="center">
                <div class="select">
                    <select class="custom-select" name="condition">
                    
                        <option value="writer">작성자</option>
                        <option value="title">제목</option>
                        <option value="content">내용</option>
                    </select>
                </div>
                <div class="text">
                    <input type="text" class="form-control" name="keyword" value="${ keyword }">
                </div>
                <button type="submit" class="searchBtn btn btn-secondary">검색</button>
            </form>
            
            <br><br>
            
            <!-- 검색된 검색어가 남아있게 -->
            <script>
            $(() => {
            	$('#searchForm option[value="${condition}"]').attr('selected', true);
            });
            </script>
            
        </div>
        <br><br>

    </div>

    <jsp:include page="../common/footer.jsp" />

</body>
</html>