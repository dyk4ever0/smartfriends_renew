<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="include-pages/common.jsp"%>

<!-- include application-chart.min.css -->
<link rel="stylesheet" type="text/css"
	href="tui-chart/bower_components/tui-chart/dist/tui-chart.min.css" />
<!-- include libraries -->
<script type="text/javascript"
	src="tui-chart/bower_components/tui-code-snippet/dist/tui-code-snippet.min.js"></script>
<script type="text/javascript"
	src="tui-chart/bower_components/raphael/raphael.min.js"></script>
<!-- include chart.min.js -->
<script type="text/javascript"
	src="tui-chart/bower_components/tui-chart/dist/tui-chart.min.js"></script>
<!-- include map data (only map chart) -->
<script type="text/javascript"
	src="tui-chart/bower_components/tui-chart/dist/maps/world.js"></script>
<!--toast UI for explorer  -->
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.js"></script>
<!--diagnose js functions -->
<script type="text/javascript" src="js/desktop-version/mypage/suggestion-guide.js"></script>
<script type="text/javascript" src="js/desktop-version/mypage/survey-result-tap.js"></script>
<script type="text/javascript" src="js/desktop-version/errorpages.js"></script>
<script type="text/javascript" src="js/desktop-version/diagnose/diagnose.js"></script>
<title>대웅 SmartWork Friends</title>
<link rel="stylesheet" href="css/mypage.css?ver=1">
<style>
  .graph-box::-webkit-scrollbar {
    width: 10px;
    height:10px;
  }
  .graph-box::-webkit-scrollbar-thumb {
    background-color: #d4d4d4;
    border-radius: 10px;
    background-clip: padding-box;
    border: 2px solid transparent;
  }
  .graph-box::-webkit-scrollbar-track {
    background-color: #f1f1f1;
    border-radius: 10px;
    box-shadow: inset 0px 0px 5px white;
  }
</style>
</head>
<body>
	<!-- <header> -->
	<%@ include file="include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper" style="padding-bottom: 0px;">
		<div class="inner">
			<div class="contents-area">
				<div class="content">
					<section class="mypage bgs">
						<div class="inner inner-info">
							<img class="data-img" src="images/main-character.png"></img>
							<div class="data-contents">
								<p class="data-text">
									<span class="imgname">NAN</span> 진단 결과, <span id="username">NaN</span>님은
									스마트워크를 위한<br> 도구 숙련도가 디지털 <span id="userskill">NaN</span>
									수준이며 전체 인원 중 <span id="userranking">NaN</span> 입니다.
								</p>
								<div>
									<span class="data-text-sub">(</span><span class="data-text-sub"
										id="allpeoplenum">NaN</span><span class="data-text-sub">명
										중 </span><span class="data-text-sub" id="attendentpeoplenum">NaN</span><span
										class="data-text-sub">명 응시)</span>
								</div>
								<div class="all-progress">
									<div class="progress">
										<ul class="progress-text">
											<li class="verylow" value="매우 낮은">
												<div class="progress-box">하얀종이</div>
												<div class="progress-icon">
													<span class="progress-check"></span>
												</div>
											</li>
											<li class="low" value="낮은">
												<div class="progress-box">견습생</div>
												<div class="progress-icon">
													<span class="progress-check"></span>
												</div>
											</li>
											<li class="normal" value="보통">
												<div class="progress-box">숙련자</div>
												<div class="progress-icon">
													<span class="progress-check"></span>
												</div>
											</li>
											<li class="high" value="높은">
												<div class="progress-box">전도사</div>
												<div class="progress-icon">
													<span class="progress-check"></span>
												</div>
											</li>
											<li class="veryhigh" value="매우 높은">
												<div class="progress-box">우주고수</div>
												<div class="progress-icon">
													<span class="progress-check"></span>
												</div>
											</li>
										</ul>
										<progress max="100" value="0" class="progress-main"
											aria-labelledby="Progress-id">
											<div class="Progress-bar" role="presentation">
												<span class="Progress-value" style="width: 80%;">&nbsp;</span>
											</div>
										</progress>
									</div>
								</div>
								<button class="retest-btn" onclick="location.href='diagnose-main'">재진단하기</button>
							</div>
						</div>
						<div class="inner">
							<h2 class="section-title">오답 노트</h2>

							<!-- 추천가이드 slide start -->
							<div class="slide-area">
								<div class="slide-guide" id="suggestion-page-1">
									<!-- 한 슬라이드 페이지 -->
									<!-- 한 추천 북마크 -->
								</div>
							</div>
							<div class="pagination"></div>
							<!-- 추천가이드 slide end -->
						</div>
					</section>


					<section class="mypage-tab">
						<div class="inner">
							<!-- 탭 start -->
							<div class="tab-area">
								<h2 class="section-title">도구별 통계</h2>
								<!-- survey offering tools tab append here-->
								<ul class="tab" id="tooltab">
								</ul>

								<!-- more study 탭 start -->
								<div id="tab-all" class="tab-content all">
									<!-- 전체 탭 -->
									<div class="tab-inner">
										<div class="columns-wrapper">

											<!-- 오른쪽 영역 start -->
											<div class="all-right">
												<h3 class="sub-title">정답 노트</h3>
												<div class="right-slide-area">
													<div class="right-slide">
														<div id="oneslide-1">
															<!-- one pagination -->
															<div class="right-slide-box" id="morestudy-1">
																<!-- one side list -->
																<ul id="sidelist-1">
																</ul>
															</div>
														</div>
													</div>
												</div>
												<div class="pagination2"></div>
											</div>
											<!--  오른쪽 영역 end -->
										</div>
									</div>
								</div>
								<!-- more study 탭 end-->
							</div>
							<!-- tab end -->
						</div>
					</section>
				</div>
			</div>
		</div>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="include-pages/footer.jsp"%>
</body>
</html>
<script>
	getUniversalMyinfo();
	showSuggestionData();
	showToolListForTooltabs();
	showMoreStudyData();
	showTooltabDataList();

	$(function() {

		/* 추천 가이드 슬라이드 start */
		function slideBox() {
			var $st = $('.pagination');
			var $slider = $('.slide-area');

			$slider.on('init reInit afterChange', function(event, slick,
					currentSlide, nextSlide) {
				var i = (currentSlide ? currentSlide : 0) + 1;
				$st.text(i + ' ' + '/' + ' ' + slick.slideCount);
			});

			$slider.slick({
				slidesToShow : 1,
				slidesToScroll : 1,
				variableWidth : true,
				autoplay : false
			});
		}

		slideBox();
		/* 추천 가이드 슬라이드 end */

		/* 도구별 통계 슬라이드 start */
		function rightSlide() {
			var $st = $('.pagination2');
			var $slider = $('.right-slide');

			$slider.on('init reInit afterChange', function(event, slick,
					currentSlide, nextSlide) {
				var i = (currentSlide ? currentSlide : 0) + 1;
				$st.text(i + ' ' + '/' + ' ' + slick.slideCount);
			});

			$slider.slick({
				slidesToShow : 1,
				slidesToScroll : 1,
				variableWidth : true,
				autoplay : false
			});
		}

		rightSlide();
		/* 도구별 통계 슬라이드 end */

		/* 탭 start */
		$('ul.tab li').click(function() {
			var tab_id = $(this).attr('data-tab');
			$('ul.tab li').removeClass('current');
			$('.tab-content').removeClass('current');
			$(this).addClass('current');
			$("#" + tab_id).addClass('current');
		})
		/* 탭 end */

	});
</script>