<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script type="text/javascript" src="//dapi.kakao.com/v2/maps/sdk.js?appkey=589f8d6a78d9e7604a24a3c4ae93da99"></script>
</head>
<div id="map" style="width:750px;height:350px;"></div>

	<script>
		var mapContainer = document.getElementById('map'), // 지도를 표시할 div 
		    mapOption = {
		        center: new kakao.maps.LatLng(37.56790, 126.98305), // 지도의 중심좌표
		        level: 1, // 지도의 확대 레벨
		        mapTypeId : kakao.maps.MapTypeId.HYBRID // 지도종류
		    }; 

		// 지도를 생성한다 
		var map = new kakao.maps.Map(mapContainer, mapOption); 

		// 지형도 타일 이미지 추가
		map.addOverlayMapTypeId(kakao.maps.MapTypeId.TERRAIN); 

		// 로드뷰 타일 이미지 추가
		map.addOverlayMapTypeId(kakao.maps.MapTypeId.ROADVIEW); 

		// 지도 타입 변경 컨트롤을 생성한다
		var mapTypeControl = new kakao.maps.MapTypeControl();

		// 지도의 상단 우측에 지도 타입 변경 컨트롤을 추가한다
		map.addControl(mapTypeControl, kakao.maps.ControlPosition.TOPRIGHT);	

		// 지도에 마커를 생성하고 표시한다
		var marker = new kakao.maps.Marker({
		    position: new kakao.maps.LatLng(37.56790, 126.98305), // 마커의 좌표
		    map: map // 마커를 표시할 지도 객체
		});

		// 커스텀 오버레이를 생성하고 지도에 표시한다
		var customOverlay = new kakao.maps.CustomOverlay({
			map: map,
			content: '<div style="padding:0 5px;background:#fff;">고통 :(</div>', 
			position: new kakao.maps.LatLng(37.56790, 126.98305), // 커스텀 오버레이를 표시할 좌표
			xAnchor: 0.5, // 컨텐츠의 x 위치
			yAnchor: 0 // 컨텐츠의 y 위치
		});

	</script>
</body>
</html>