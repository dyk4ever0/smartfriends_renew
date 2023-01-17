<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
<script type="text/javascript" src="js/slick/slick.min.js"></script>
<link rel="stylesheet" href="css/digital.css">
<style>
  .layer-popup.img-player.active::-webkit-scrollbar {
    width: 10px;
  }
  .layer-popup.img-player.active::-webkit-scrollbar-thumb {
    background-color: #2f3542;
    border-radius: 10px;
    background-clip: padding-box;
    border: 2px solid transparent;
  }
  .layer-popup.img-player.active::-webkit-scrollbar-track {
    background-color: #fff;
    border-radius: 10px;
    box-shadow: inset 0px 0px 5px white;
  }
</style>
</head>
<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper has-side-menu bg-blue contents-bottom">
		<div class="inner">
			<!-- LNB [s] -->
			<%@ include file="../include-pages/guide-lnb.jsp"%>
			<!-- LNB [e] -->

			<div class="contents">
				<div class="inner">
					<section>
						<h2 class="section-title"></h2>
					</section>
					<section class="digital-lists">
						<div class="inner">
							<ul id="li-list">
								<!-- digital list -->
							</ul>
						</div>
					</section>

					<div class="digital-img-lists"></div>
						<!-- modal images -->
				</div>
			</div>
		</div>
	</div>

	<!-- CONTENTS [e] -->

	<!-- FOOTER -->

<%--  	<%@ include file="../include-pages/footer.jsp"%> 
 --%>

	<script type="text/javascript">
	//digital list
	var contentsList = "<li><a type=\"button\" class=\"task-item\" data-task=\"{num}\">"
			+ "<span><img src=\"images/digital/{route}\" alt=\"디지털창\">"
			+ "<em>바로보기</em>"
			+ "</span>"
			+ "<strong>{comment}</strong>"
			+ "</a></li>";
	
	//image list template
	var imgTemplate1 = "<div class=\"layer-popup img-player\" id=\"imgPopup\" aria-modal=\"true\" role=\"document\" data-task=\"{num}\">"
			+ "<h2>{comment}</h2>"
			+ "<div class=\"slick-area-img\" id=\"slide-banner\">"
			+ "</div>";
	
	//img div
	var imgdiv = "<div class=\"popup-img\">";

	//image list
	var imgList = "<div class=\"popup-img\">" + "<img src=\"images/digital/{route}\" alt=\"디지털창\">" + "</div>";

	//close button
	var imgTemplate2 =  "<button type=\"button\" class=\"close\">닫기</button>";

	//디지털창에 해당하는 리스트 : 베아월드, 간편도구..
	var data = ${digitalContentsList};
	
	$('.section-title').append("디지털 창 - ", data[0].title);

	//image list template
	for (var i = 0; i < data.length; i++) {
		$("#li-list").append(
				contentsList.replace("{num}", i + 1).replace("{route}",data[i].route[0])
													.replace("{comment}", data[i].comment));
		
		$('.digital-img-lists').append(imgTemplate1.replace("{num}", i + 1).replace("{comment}", data[i].comment));
		
	}
	
	
	//X 닫기 버튼  
	$('.layer-popup').append(imgTemplate2);
	
		
	
	$(document).ready(function() {

		$('.task-item').click(function() {
				
			// 목록의 data-task 넘버 가져오기 
			var taskNum = $(this).attr('data-task');
				
			$('.digital-img-lists').find('.layer-popup[data-task='+taskNum+']').addClass('active');
				
				
			for (var j = 0; j < data[taskNum-1].route.length; j++) {
					// 목록의 data-task애 해당하는 layer-popup 하위의 .slick-area-img에 이미지 넣기
					$('.digital-img-lists').find(
							'.layer-popup[data-task='+taskNum+'] .slick-area-img').append(
									imgList.replace("{comment}", data[taskNum-1].comment).replace("{route}", data[taskNum-1].route[j]));
			}
				
		});


		$('.close').click(function() {
				
		$('.layer-popup').removeClass('active');		
			});
		
		});
	
	  //클릭한 도구가이드 툴 active
	   for(var i=0; i<digitalList.length; i++){
		   if(data[0].title == $('.link-menu:eq(2) li:nth-child('+(i+1)+')').text().substring(1)){
			  $('aside .header a').attr('class','title'); //스마트워크 룰  active 지우기
			  $('.link-menu:eq(2) li:eq('+i+') a').attr('class', 'active'); // 클릭된 도구 active
		  } 
		  
	  }  
	</script>
</body>
</html>
