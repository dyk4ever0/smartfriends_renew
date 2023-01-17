<!--[mobile] 가이드 - 스마트워크룰(메인페이지) 관련 jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<style>
.footer .btn-wrapper .circle.url{
	background: #c2c2c2;
}
</style>
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
			</div>
		</nav>
		<section class="document">
			<div class="inner">
				<h2 class="section-title">
					스마트워크는 <br>원격근무입니다.
				</h2>
				<div class="text-box">
					<p>출퇴근 시간은 명확하게 지키며 신뢰를 바탕으로 효율적으로 일할 수 있는 팀별 업무규칙을 수립하고 육성형
						피드백을 통해 성과관리를 진행합니다. 스마트워크에 필요한 필수 도구들의 사용법을 소개합니다.</p>
					<p>
						<button type="button" class="square-btn has-icon guide-video-play"
							data-task="full">
							스마트워커의 하루 보러가기<span class="icon youtube">youtube video</span>
						</button>
					</p>
				</div>
			</div>
		</section>
		<section class="workflow transparent">
			<div class="inner">
				<h2 class="rounded-square">Start</h2>
				<ul class="workflow-list box-wrapper" role="list">
				</ul>
			</div>
		</section>
		<!-- modal [s] -->
		<div class="common-layer video-player modal" id="videoPopup"
			role="document" aria-modal="true">
			<div class="layer">
				<div class="layer-header">
					<h2 class="title">스마트워크 Rule</h2>
					<button type="button" class="close">닫기</button>
				</div>
				<div class="layer-body">
					<!-- video carousel은 task별로 묶여 있습니다. -->
					<div class="video-carousel" data-task="full">
						<div class="item">
							<div class="header">
								<p class="title">스마트워커 대웅인의 하루</p>
							</div>
							<div class="body">
								<div class="video"
									data-src="${fullUrl}"></div>
							</div>
						</div>
					</div>
					<!-- video carousel은 task별로 묶여 있습니다. -->
					</div>
				</div>
			</div>
		</div>
		<!-- modal [e] -->
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>
	<script type="text/javascript" src="js/mobile-version/guide/rule.js"></script>
	<script>
	//header-title 변환
	$('.header-title').html("가이드");
	//rule 정보
	var data = ${ruleList};
 	//bmkidx list in favorite list
	var bmkidx = ${bmkidx};
	//스마트워크Rule 및 해당 기능 출력
	makeRule(data, bmkidx);
	//category select box
	var categoryList = ${categoryList};
	categorySelect(categoryList);
	$('.sub-nav.category select').val("스마트워크룰").attr('selected', 'selected');
	</script>
</body>
</html>