<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>쇼핑 API</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
<script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
<style>
	a {
		text-decoration: none;
		color: white;
	}
	#kh-bg {
		width: 800px;
		height: 450px;
		background-image: url('https://upload.wikimedia.org/wikipedia/commons/b/b8/Seoul_Cheonggyecheon_night.jpg');
		margin-top: 30px;
		margin: auto;
		background-repeat: no-repeat;
		background-size: cover;
		border-radius: 5px;
	}
	.items {
		width: 1000px;
		margin: auto;
		display: flex;
		flex-wrap: wrap;
		gap: 15px;
	}
</style>
</head>
<body>
<nav class="navbar navbar-expand-lg bg-dark">
	<div class="container-fluid">
		<a class="navbar-brand" href="#">
			<img src="https://pbs.twimg.com/profile_images/1597451518/tw_logo_400x400.png" alt="Logo" width="30" height="24" class="d-inline-block align-text-top">
			KH몰
		</a>
	</div>
	<div class="container-fluid">
		<form class="d-flex" role="search">
			<input class="form-control me-2" id="keyword" type="search" placeholder="상품명을 입력해주세요." aria-label="Search">
			<button id="search-btn" style="width:300px; border:1px solid white; color:white;" class="btn btn-outline-success" type="button">검색</button>
		</form>
	</div>
</nav>

<div id="kh-bg">

</div>
<br/><br/>
<button class="button btn-lg" style="background:lightgreen; color:white; font-weight:bold;" onclick="nextPage();">다음상품</button>

<div class="items"></div>

<script>
var startNo = 1;

$('#search-btn').click(() => {
	searchItem();
});

function nextPage() {
	startNo += 9;
	searchItem();
}

function searchItem() {
	const keyword = $('#keyword').val();
	
	$.ajax({
		url: 'product',
		data: {
			keyword: keyword,
			start: startNo
		},
		type: 'get',
		success: product => {
			console.log(product);
			const items = product.items;
			
			let itemHtml = '';
			for (let i in items) {
				itemHtml += '<div class="card" style="width: 18rem;">'
					+ '<img src="' + items[i].image + '" class="card-img-top">'
					+ '<div class="card-body">'
					+ '<h5 class="card-title">' + items[i].brand + '</h5>'
					+ '</div>'
					+ '<ul class="list-group list-group-flush">'
					+ '<li class="list-group-item">가격</li>'
					+ '<li class="list-group-item">' + items[i].lprice + '원</li>'
					+ '</ul>'
					+ '<div class="card-body">'
					+ '<a href="' + items[i].link + '" class="btn btn-primary" target="_blank">구매하러 가기</a>'
					+ '</div>'
					+ '</div>';
			}
			
			$('.items').html(itemHtml);
		},
		error: (xhr, status, error) => {
			console.error('Error:', status, error);
		}
	});
}
</script>
</body>
</html>