<!--[mobile] 가이드 - 보안 관련 jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>

<body>
	<!-- GNB -->
	<%@ include file="../include-pages/header.jsp"%>
	<!-- CONTENTS [s] -->
	<div class="contents-wrapper">
		<nav class="navigation">
			<div class="nav-wrapper">
				<div class="sub-nav category">
					<select name="category">
						<!--스마트워크룰, 도구가이드, 보안  -->
					</select>
				</div>
				<div class="sub-nav contents">
					<select name="contents">
						<!--Zoom, Linworks  -->
					</select>
				</div>
			</div>
		</nav>
		<section class="document">
			<div class="inner">
			</div>
		</section>
	</div>
	<!-- CONTENTS [e] -->
	
	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>
	
	<script src="js/mobile-version/guide/etc.js"></script>
	<script>
	//header-title 변환
	$('.header-title').html("가이드");
	//category select box
	var categoryList = ${categoryList};
	categorySelect(categoryList);
	$('.sub-nav.category select').val("보안").attr('selected', 'selected')
	/*contents select box  */
	var contentsList = ${contentsList};
	contentsSelect(contentsList);
	
	/*전체적인 틀  */
	var data = ${guideInfo};
	makeToolGuide(data);
	/*section list  */
	var sectionData = ${sectionList};
	var SectionNum = ${num};
	makeSection(sectionData, SectionNum);
	//bookmark print
	var bmkList = ${bmkList};
	//bmkidx list in favorite list
	var bmkidx = ${bmkidx};
	/*bookmark list  */
	makeBookmark(bmkList, SectionNum, bmkidx);
	//category select box에서 현재 페이지에 해당하는 도구 selected
	$('.sub-nav.contents select').val($('.section-title').text()).prop("selected",true);
	
	//페이지 로드 시 특정시간부터 영상 재생 
	var start = ${start};
	videoPlayTime(start);
	</script>
</body>
</html>