<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ include file="include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
<link rel="stylesheet" href="css/main.css?ver=2">
<style>
.slick-list, .slick-list .slick-track {
	transform: none !important;
}
.active {
	visibility: visible;
}
</style>
</head>

<body style="padding-top: 0px;">
	<!--header  -->
	<%@ include file="include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper" id=top>
		<!-- Video Player layer popup [s] -->
		<div class="layer-popup video-player" id="videoPopup"
			aria-modal="true" role="document">
			<div class="popup-inner">
				<!-- video carousel은 task별로 묶여 있습니다. -->
				<div class="video-carousel" data-task="full">
					<div class="item">
						<div class="header">
							<p class="title">${videoTitle}</p>
						</div>
						<div class="body">
							<div class="video" data-src="${fullUrl}"></div>
						</div>
						<!--동영상 팝업 시 나타나는 즐겨찾기기능과 공유url기능 출력  -->
					</div>
				</div>
			</div>
			<button type="button" class="close">닫기</button>
		</div>

		<button type="button" class="banner guide-video-play" data-task="full">
			<img class="bannerImg tv" src="images/tv-banner.svg"></img>
		</button>
		<!-- Video Player layer popup [e] -->
		<span class="main-title">똑똑한 업무를<br> 도와주는 우리는<br> <span
			style="font-weight: 700;">스마트워크 프렌즈</span>입니다
		</span>
		<section class="top-visual" style="position: relative;">
			<div class="inner" style="width: 100%;">
				<div class="inner-cover"></div>
				<div class="youtube-area"
					style="height: 100%; width: 100%; position: relative; width: 100%; height: 0; padding-bottom: 56.25%;">
					<a href="https://www.youtube.com/watch?v=NaWnBpfwqEw"
						class="play-link"> <span class="sr-only">영상 재생하기</span>
					</a>
				</div>
			</div>
		</section>


		<div id="about">
			<section class="about">
				<div class="inner">
					<h2 class="section-title">
						디지털 트랜스포메이션을 실천하여<br>대웅인의 <span style="color: #bb9dd6;">일하는
							수준</span>을 혁신하고자 합니다.
					</h2>
					<ul class="columns-wrapper">
						<li class="column">
							<div class="text-box" style="margin-right: 80px;">
								<h3 class="title">Work for smart</h3>
								<p>
									대웅은 대웅way에 기반한 <b>‘자율’과 ‘책임’</b>이라는 모토 하에<br> <b>‘직원이
										성장하는 문화’, ‘제대로 몰입하여 일하고 성과 내는 문화’</b>를<br> 만들어나가기 위해 항상 고민하고
									노력합니다.
								</p>
								<p>
									대웅은 대웅way가 내재화된 사람들이 자율적으로 성과를 내도록<br> <b>‘스마트워크’</b>라는
									일하는 방식을 2010년부터 도입하였고,<br> 이를 위한 제도와 환경, 시스템을 고도화 해왔습니다.
								</p>
								<p>
									대웅 스마트워크 프렌즈는 스마트워크의 가치를 실현시키며<br> 변화하는 <b>업무환경에 빠르게
										적응할 수 있는 가이드</b>를 제공하고자 합니다.
								</p>
								<p class="hash-tag">#DigitalTransformation #스마트워크</p>
							</div>
						</li>
						<li><img class="main-img" src="images/main-character.png"></img>
						</li>
					</ul>
				</div>
			</section>

			<section>
				<div class="inner about-content">
					<ul class="columns-wrapper">
						<li><img class="page-img-lf" src="images/rule.png"></img></li>
						<li class="column">
							<div class="text-box">
								<h3 class="page-title">가이드</h3>
								<div class="page-text">스마트워크Rule을 기준으로 주요 기능 제공</div>
								<p>
									스마트워크의 정의는 다양합니다. <br> 우리의 서비스는 그 기준을 스마트워크 Rule을 통해 명확히
									제시합니다.<br> <b>업무 일과를 타임라인 형식으로 구분</b>했고,<br> 해당 업무를
									수행하기 위해 숙지해야 할 <b>도구별 기능</b>을 함께 나타냈습니다
								</p>
							</div>
							<button class="page-btn" onclick="location.href='guide'">가이드
								바로가기</button>
						</li>
					</ul>
				</div>
			</section>

			<section>
				<div class="inner about-content">
					<ul class="columns-wrapper">

						<li class="column">
							<div class="text-box text-rg">
								<h3 class="page-title">가이드(영상)</h3>
								<div class="page-text">영상 가이드를 통한 빠른 학습과 성장</div>
								<p>
									<b>'스마트워크에 자주 이용되는 도구', '업무 중 필요한 사전 지식',<br> '업무의
										효율성을 증대할 수 있는 팁'
									</b> 에 관한 영상 가이드를 제공합니다.<br> 따라만 하면 바로 활용할 수 있는 영상 가이드를 통해<br>
									<b>스스로 학습할 기회</b>를 가지며 성장할 수 있습니다.

								</p>
							</div>
							<ul class="icon-list">
								<li><a href="toolguide?tool=Zoom"><img
										src="publisher-folder/images/icon_Zoom.jpg" alt="Zoom"></a></li>
								<li><a href="toolguide?tool=Lineworks"><img
										src="publisher-folder/images/icon_Lineworks.jpg"
										alt="Lineworks"></a></li>
							</ul>
						<li><img class="page-img-rg" src="images/lecture.png"></img></li>
					</ul>
				</div>
			</section>

			<section>
				<div class="inner about-content">
					<ul class="columns-wrapper">
						<li><img class="page-img-lf" src="images/test.png"></img></li>
						<li class="column">
							<div class="text-box">
								<h3 class="page-title">진단</h3>
								<div class="page-text">자가 진단을 통해 숙련도 파악</div>
								<p>
									가이드로 제공된 도구의 숙련도를 진단하여<br> <b>현재 수준을 확인</b>할 수 있습니다.<br>
									진단 결과를 캐릭터 형식으로 나타내어 즐겁게 자가진단에 참여할 수 있도록 구성했습니다.<br> 또한
									시행한 진단들의 <b>결과를 누적하여 숙련도 변화를 파악</b>할 수 있습니다.
								</p>
							</div>
							<button class="page-btn" onclick="location.href='diagnose-main'">진단
								하러가기</button>
						</li>
					</ul>
				</div>
			</section>

			<section>
				<div class="inner about-content">
					<ul class="columns-wrapper">

						<li class="column">
							<div class="text-box text-rg">
								<h3 class="page-title">통계</h3>
								<div class="page-text">관리자 도구 지원</div>
								<p>
									직책별, 부서별, 계열사별로 <b>시각화된 데이터</b>를 제공하여<br> 스마트워크 수준의 발전 및
									변화를 한 눈에 파악 할 수 있습니다.<br> <b>컨텐츠 관리 기능을 제공</b>하여<br>
									지속적으로 컨텐츠 및 진단 문항을 업데이트할 수 있습니다.<br>

								</p>
							</div>
						</li>
						<li><img class="page-img-rg" src="images/chart.png"></img></li>
					</ul>
				</div>
			</section>
		</div>
		<div class="banner" id=tooltip-btn>
			<img class="bannerImg" src="images/mobile-banner.png"></img>
		</div>

		<div class="banner-tooltip" id=tooltip-img>
			<img class="mobile-info" src="images/mobile-info.png"></img>
		</div>

		<div class="topBtn">
			<a href="#top"><img class="topImg" src="images/icons/top-btn.svg"></img></a>
		</div>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="include-pages/footer.jsp"%>

</body>

<script type="text/javascript">
	$(document).ready(
			function() {
				$('.youtube-area')
						.html(
								returnYoutubeSourceForMain($('.play-link')
										.attr('href')));

				$('#tooltip-btn').hover(function(){
						$('#tooltip-img').addClass('active')},
						function(){$('#tooltip-img').removeClass('active')});
			});
</script>
</html>