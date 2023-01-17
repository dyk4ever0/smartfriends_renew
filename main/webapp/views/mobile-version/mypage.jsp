<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>대웅 SmartWork Friends</title>
<link rel="stylesheet" href="css/mobile/mypage.css?ver=1">
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
<!-- common import -->
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

<!-- js import -->
<script type="text/javascript"
	src="js/mobile-version/suggestion/suggestion-append.js"></script>
<script type="text/javascript"
	src="js/mobile-version/suggestion/suggestion-datacall.js"></script>
<script type="text/javascript"
	src="js/mobile-version/mypage/mypage-datacall.js"></script>
<script type="text/javascript"
	src="js/mobile-version/mypage/mypage-append.js"></script>
<!-- error message appending import -->
<script type="text/javascript" src="js/mobile-version/errorpage.js"></script>
<body>
	<!-- header -->
	<%@ include file="include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper">
		<section class="recommend transparent">
			<div class="inner">
				<div class="recommend-title">
					<p class="section-title">오답 노트</p>
					<!-- append incorrect answer show button -->
				</div>
				<div class="overflow-box x">
					<div class="guide-list" role="list">
						<!-- suggestion list append here -->
					</div>
				</div>
			</div>
		</section>
		<section class="statistics">
			<div class="inner">
				<h2 class="section-title">도구별 통계</h2>
				<ul class="tab-buttons">
					<li><a href="#total" id="tab-button" role="tab"
						aria-controls="total" aria-selected="true">전체</a></li>
					<!-- tool tab format append here -->
					<!-- <li><a href="#zoom" role="tab" aria-controls="zoom" aria-selected="false">Zoom</a></li> -->
				</ul>
				<ul class="tab-contents">
					<!-- basic userinfo format -->
					<li id="total" class="active" role="tabpanel">
						<div class="data-status">
							<p class="desc bigger">
								<strong id="username">NaN</strong>님은 스마트워크를 위한 <br>도구 숙련도가
								‘ <strong id="userskill">NaN</strong> ’ 수준이며 <br>전체 인원 중 ‘
								<strong id="userranking">NaN</strong> ’ 입니다.
							</p>
							<p class="range">(NaN명 중 NaN명 응시)</p>
							<div class="progress">
								<ul class="progress-text">
									<li class="verylow" value="매우 낮은">
										<div class="progress-box">매우 낮은</div>
										<div class="progress-icon">
											<span class="progress-check"></span>
										</div>
									</li>
									<li class="low" value="낮은">
										<div class="progress-box">낮은</div>
										<div class="progress-icon">
											<span class="progress-check"></span>
										</div>
									</li>
									<li class="normal" value="보통">
										<div class="progress-box">보통</div>
										<div class="progress-icon">
											<span class="progress-check"></span>
										</div>
									</li>
									<li class="high" value="높은">
										<div class="progress-box">높은</div>
										<div class="progress-icon">
											<span class="progress-check"></span>
										</div>
									</li>
									<li class="veryhigh" value="매우 높은">
										<div class="progress-box">매우 높은</div>
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
						<div class="board-box">
							<div class="recommend-title">
								<p class="title">정답 노트</p>
								<!-- correct answer show button append -->
							</div>
							<!-- <p class="title">오답 노트</p> -->
							<ul class="board-list" role="list">
							</ul>
						</div>
					</li>
					<!-- here 'll be appended the tool tab info -->
				</ul>
			</div>
		</section>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="include-pages/footer.jsp"%>
</body>
</html>

<script>
	//change header title text
	$(".header-title").text("내 정보");

	//append user universal info component
	appendUniversalInfo();
	//append tool tab component
	appendSurveyToolsTabs();
	//append survey offering tool result component
	appendSurveyOfferTools();
	//append correct/incorrect answer show button
	appendSuggestionButton();

	//append tab click event
	$(document).on(
			"click",
			"#tab-button",
			function() {
				$('.statistics .inner li a').attr('aria-selected', "false");
				$(this).attr("aria-selected", "true");
				//add active class to tool info
				$('.tab-contents li').removeClass("active");
				$(
						".tab-contents li[id=\""
								+ $(this).attr("aria-controls") + "\"]")
						.addClass("active");
				//need to addclass active
			})

	//append announce mention to click tool name button
	appendErrorMessage("상단의 도구이름 버튼을 눌러주세요.", "오답노트가 출력됩니다.",
			"버튼을 누르면, 각 도구별 오답노트가 등장합니다.", null, null, ".guide-list", false);

	//append announce mention to click tool name button
	appendErrorMessage("상단의 도구이름 버튼을 눌러주세요.", "정답노트가 출력됩니다.",
			"버튼을 누르면, 각 도구별 정답노트가 등장합니다.", null, null,
			"#total .board-box .board-list", false);
</script>