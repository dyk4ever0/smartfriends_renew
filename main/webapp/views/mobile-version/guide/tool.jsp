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
	
	<script src="js/mobile-version/guide/tool.js"></script>
	<script>
	//header-title 변환
	$('.header-title').html("가이드");
	//category select box
	//가이드 페이지 상단의 왼쪽 select 박스를 생성하는 함수
	var categoryList = ${categoryList};
	categorySelect(categoryList);
	$('.sub-nav.category select').val("도구 가이드").attr('selected', 'selected')
	/*contents select box  */
	//가이드 페이지 상단의 오른쪽 select 박스를 생성하는 함수
	var contentsList = ${contentsList};
	contentsSelect(contentsList);
	
	/*초기에 도구가이드 정보를 페이지에 출력하는 함수
	 *  전체적인 틀 삽입  */
	var data = ${guideInfo};
	makeToolGuide(data);
	
	/*섹션 데이터 출력  */
	var sectionData = ${sectionList};
	var SectionNum = ${num};
	makeSection(sectionData, SectionNum);
	
	//bookmark print
	var bmkList = ${bmkList};
	//bmkidx list in favorite list
	var bmkidx = ${bmkidx};
	/*특정 도구(Lineworks..)와 관련된 북마크 정보 출력  */
	makeBookmark(bmkList, SectionNum, bmkidx);
	
	//category select box에서 현재 페이지에 해당하는 도구 selected
	$('.sub-nav.contents select').val($('.section-title').text()).prop("selected",true);
	
	//페이지 로드 시 특정시간부터 영상 재생 
	//publisher-folder/mobile/js/script.js에 위치
	var start = ${start};
	videoPlayTime(start);

	</script>
</body>
</html>