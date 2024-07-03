<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="ko">
<head>
    <meta charset="UTF-8">
    <title>Document</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
	<style>
		.portfolio-item{
			text-align: center;
			margin-bottom: 20px;
		}

		.portfolio-caption{
			padding : 10px;
		}

		.img-thumbnail:hover{
			opacity: 0.8;
			transform: scale(1.1);
			transition: all 1s ease-in-out;
		}

		h1{
			text-align: center;
			padding: 50px;
		}
	
	</style>
</head>
<body>

	<jsp:include page="../common/menubar.jsp" />

	<script>
		
		window.onload = () =>{
			
		
		}
			/*
			const imgSrc = 'data:image/jpeg;base64,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';
			const imgs = [...document.getElementsByClassName('img-fluid')];

			imgs.map(e => {
				e.src = imgSrc; 
			})
			*/
			
			// $('portfolio-link').click(e => {
			// 	console.log(e.currentTarget);
			// });
			
			function detail(e) {
				/*
				console.log($(e).find('.thumbnail-date').val());
				console.log($(e).find('.thumbnail-content').val());
				console.log($(e).find('.thumbnail-img').attr('src'));
				console.log($(e).find('.thumbnail-text').text());
				console.log($(e).find('.thumbnail-writer').text());
				*/
				$('.#img-modal').html ($(e).find('.thumbnail-img').attr('src'));
				$('.modal-title').html ($(e).find('.modal-title').text());
				$('#modal-writer').html ($(e).find('.thfind('.thumbnail-writer').text());
				$('#modal-content').html ($(e).find('.thumbnail-content').val());
				$('#modal-date').html ($(e).find('.thumbnail-date').val());
			}
			// val() => value값 가져오기, html()=> html값 가져오기, attr(), css()
		
	</script>
	<!-- 
	<div>
		<input type="text" id="j-input">
		<button id="j-btn">버튼이당</button>
	</div>
	 -->
	<script>
		$('#j-btn').click(() => {
			//요소객체속성에 접근하는 방법
			
			// 자바스크립트
			// document.getElementById("j-input").value = '하하';
			// alert(document.getElementById("j-input").value);
		
			// jquery : val() 하나의 메서드가 getter, setter역할을 다 한다. 
			// $('#j-input').val('인자값으로 전달'); 
			// alert($('#j-input').val());
			
			// document.getElementById('j-btn').innerHTML = 'asd'; // getter
			// alert(($('j-btn').html()); // 또는 .text()
			// $('#j-btn').html('이게 버튼이야??');
			
			style = {
					'color : white',
					'font-size : 50px'
			}
			
			$('#j-btn').css(style);
		})
	</script>

	<div class="container">

		<h1>사진게시판이당</h1>

		<section class="bg-light" id="portfolio" style="padding-top: 90px; padding-bottom: 0;">
			<div class="container">
				<div class="row">
				
				<c:forEach items="${ board }" var="img">
					
					<div class="col-md-4 col-sm-6 portfolio-item">
					<input type="hidden" class="detail-date" value="${ img.createDate}"/>
					<input type="hidden" class="detail-content" value="${ img.boardContent}"/>
						<a class="portfolio-link" data-toggle="modal" href="#MyModal">
							<div class="portfolio-hover">
								<div class="portfolio-hover-content">
								</div>
							</div>
							<img style = "height:70%;" 
								class="img-thumbnail img-fluid" 
								src="${img.changeName }" 
								alt="이미지" 
								class="thumbnail-img">
						</a>
						<div class="portfolio-caption">
							<h4 class="thumbnail-title">${ img.boardTitle }</h4>
							<p class="text-muted" class="thumbnail-writer">${ img.boardWriter }</p>
						</div>
					</div>
					
				</c:forEach>
			
				</div>
			</div>
		</section>
	
	</div> 
	
    <jsp:include page="../common/footer.jsp" />


    <div class="modal fade" id="MyModal">
	    <div class="modal-dialog">
		    <div class="modal-content">
		
		        <!-- Modal Header -->
		        <div class="modal-header">
			        <h4 class="modal-title">게시글 제목</h4>
			        <button type="button" class="close" data-dismiss="modal">&times;</button>
		        </div>
		
		        <!-- Modal body -->
		        <div class="modal-body">
			        <div class="table-center">
			            <table class="intro-table">
			                <tr class="intro-tr">
			                    <td colspan="2">
			                        <img id="img-modal" class="img-fluid" src="" alt="" width="100%">
			                    </td>
			                </tr>
			                <tr class="intro-tr">
			                    <td style="background-color:#23C293; width:100px; text-align:center;">
			                        <strong><span style="color: white;">폼폼푸린</span></strong>
			                    </td>
			                    <td style="text-align:left;">
			                        <span id="modal-writer">폼폼푸린</span>
			                    </td>
			                </tr>
			                <tr class="intro-tr">
			                    <td style="background-color:#23C293; width:100px; text-align:center;">
			                        <strong><span style="color: white;">본문</span></strong>
			                    </td>
			                    <td style="text-align:left;">
			                        <span id="modal-content">어쩌고저쩌고</span><br>
			                    </td>
			                </tr>
			                <tr class="intro-tr">
			                    <td style="background-color:#23C293; width:100px; text-align:center;">
			                        <strong><span style="color: white;">작성일</span></strong>
			                    </td>
			                    <td style="text-align:left;">
			                        <span id="modal-date">2024-06-06</span>
			                    </td>
			                </tr>
			            </table>
			        </div>
		        </div>
		
		        <!-- Modal footer -->
		        <div class="modal-footer">
			        <button type="button" class="btn btn-danger" data-dismiss="modal">닫기</button>
		        </div>
	        </div>
	    </div>
    </div>
</body>
</html>