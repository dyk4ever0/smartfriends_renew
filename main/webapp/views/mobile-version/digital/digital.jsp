<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>대웅 SmartWork Friends</title>
<%@ include file="../include-pages/common.jsp"%>
<link rel="stylesheet" href="css/mobile/digital.css">
</head>

<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper">
		<section class="document">
			<div class="inner" id=dgtArea>
				<div class="dgt-list" id=dgtList>
					<!-- digital-list start -->
					<!-- content start -->
				</div>
			</div>
		</section>
	</div>
	<!-- CONTENTS [e] -->
	
	<!-- footer  -->
	<%@ include file="../include-pages/footer.jsp"%>

<script type="text/javascript">
		var data = ${digitalContentsList};
 
		//Insert header-title
		$('.header-title').append("디지털 창 - ", data[0].title);
		
		//list Template
		var dgtList = "<div class=\"dgt-wrapper\" id=\"digitalList\" data-task=\"{num}\">"
		+"<div class=\"dgt-inner\">"
		+"<img class=\"dgt-img\" src=\"images/digital/{route}\">"
		+"</div>"
		+"<div class=\"dgt-title\">"
		+"<p>{comment}</p>"
		+"</div>"
		+"<div class=\"dgt-btn\">"
		+"<button class=\"open-btn\">"
		+"<i><img src=\"images/icons/dropdown-circle.svg\" class=\"toggle-btn\"></i>"
		+"</button>"
		+"</div></div>"
		+"<div class=\"contents-area\" data-task=\"{num}\">"
		+"<ul class=\"contents-list\">"
		+"</ul>"
		+"</div>"

		//img Template
		var contentImg = "<li><img src=\"images/digital/{route}\"></li>"

		//Load list 
		for (var i = 0; i < data.length; i++) {
			$("#dgtList").append(
					dgtList.replace("{num}", i + 1).replace("{route}",data[i].route[0])
					.replace("{comment}", data[i].comment).replace("{num}", i + 1)
			);
			
						
			//Load img
			for (var j = 0; j < data[i].route.length; j++) {
				$('.contents-list:eq('+i+')').append(contentImg.replace("{route}", data[i].route[j])); }
		}
		
		//Open contents by toggle
		$('.dgt-wrapper').click(function() {
			$(this).next().slideToggle("slow");
			$(this).find('.toggle-btn').toggleClass('close-btn');
			});
</script>
</body>
</html>
