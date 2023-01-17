<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<title>대웅 SmartWork Friends</title>
<link rel="stylesheet" href="css/mobile/main.css">
<style>
.none-set {
	display: none!important;
}
</style>
</head>

<%@ include file="include-pages/common.jsp"%>
<body class="main">
	<!-- GNB -->
	<%@ include file="include-pages/header.jsp"%>
	<!-- </header> -->

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper">
		<section class="top-visual">
			<a href="https://www.youtube.com/watch?v=deohrjOdlI8"> <img
				src="images/main-video.gif" alt="Line Works Naver에서 제공하는 기업용 소통 플랫폼"
				class="thumbnails"> <span class="sr-only">영상 재생하기</span>

			</a>
			<p class="main-title">
				똑똑한 업무를<br> 도와주는 우리는<br> <span style="font-weight: 700;">스마트워크
					프렌즈</span>입니다
			</p>

		</section>

		<section class="about">
			<div class="inner">
				<ul class="columns-wrapper">
					<li>
						<h2 class = "section-title">
							디지털 트랜스포메이션을 실천하여<br>대웅인의 <span style="color: #bb9dd6;">일하는 수준</span>을 혁신하고자 합니다.
						</h2>
						<h3 class="title" style="margin-top : 10px;">Work for smart</h3>
					</li>
					<li><img class="main-img" src="images/main-character.png"></img></li>
				</ul>

				<div style="padding-bottom : 20px;">
					<div class="text-box" style="margin-top : 10px;">
						<p>
							대웅은 대웅way에 기반한 <b>‘자율’과 ‘책임’</b>이라는 모토 하에<br> <b>‘직원이
								성장하는 문화’,<br></b> <b>‘제대로 몰입하여 일하고 성과 내는 문화’</b>를<br> 만들어나가기 위해 항상 고민하고
							노력합니다.
						</p>
						<p>
							대웅은 대웅way가 내재화된 사람들이 <br>자율적으로 성과를 내도록<br> <b>‘스마트워크’</b>라는
							일하는 방식을 2010년부터 도입하였고,<br> 이를 위한 제도와 환경, 시스템을 고도화 해왔습니다.
						</p>
						<p>
							대웅 스마트워크 프렌즈는 <br> 스마트워크의 가치를 실현시키며<br> 변화하는 <b>업무환경에 빠르게
								적응할 수 있는 가이드</b>를 제공하고자 합니다.
						</p>
						<p class="hash-tag">#DigitalTransformation #스마트워크</p>
					</div>
				</div>
			</div>
		</section>

		<section class = "document">
			<div class = "inner">
				<div class = "text-box">
					<p class="page-title">가이드</p>
					<div class="page-text">스마트워크 Rule을 기준으로 주요 기능 제공</div>
					<p>
						스마트워크의 정의는 다양합니다. <br> 우리의 서비스는 그 기준을 스마트워크 Rule을 통해 명확히
						제시합니다.<br> <b>업무 일과를 타임라인 형식으로 구분</b>했고,<br> 해당 업무를
						수행하기 위해 숙지해야 할 <b>도구별 기능</b>을 함께 나타냈습니다
					</p>
				</div>
				<a href="guide" class="square-btn">가이드 바로가기 ></a>
				<img class="page-img-lf" src="images/rule.png"></img>
			</div>
		</section>

		<section class = "document">
			<div class = "inner">
				<div class = "text-box">
					<p class="page-title">가이드(영상)</p>
					<div class="page-text">영상 가이드를 통한 빠른 학습과 성장</div>
					<p>
						<b>'스마트워크에 자주 이용되는 도구',<br> '업무 중 필요한 사전 지식',<br> '업무의
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
				<img class="page-img-lf" src="images/lecture.png"></img>
			</div>
		</section>

		<section class = "document">
			<div class = "inner">
				<div class = "text-box">
					<p class="page-title">진단</p>
					<div class="page-text">자기 진단을 통해 숙련도 파악</div>
					<p>
						직책별, 부서별, 계열사별로 <b>시각화된 데이터</b>를 제공하여<br> 스마트워크 수준의 발전 및
						변화를<br>한 눈에 파악 할 수 있습니다.<br> <b>컨텐츠 관리 기능을 제공</b>하여<br>
						지속적으로 컨텐츠 및 진단 문항을 업데이트할 수 있습니다.<br>
					</p>
				</div>
				<a href="diagnose-main" class="square-btn">진단 바로가기 ></a>
				<img class="page-img-lf" src="images/test.png"></img>
			</div>
		</section>

		<section class = "document">
			<div class = "inner">
				<div class = "text-box">
					<p class="page-title">통계</p>
					<div class="page-text">관리자 도구 지원</div>
					<p>
						직책별, 부서별, 계열사별로 <b>시각화된 데이터</b>를 제공하여<br> 스마트워크 수준의 발전 및
						변화를 한 눈에 파악 할 수 있습니다.<br> <b>컨텐츠 관리 기능을 제공</b>하여<br>
						지속적으로 컨텐츠 및 진단 문항을 업데이트할 수 있습니다.<br>
					</p>
				</div>
				<img class="page-img-lf" src="images/chart.png"></img>
			</div>
		</section>
	</div>
	<!-- CONTENTS [e] -->
	<!-- FOOTER -->
	<%@ include file="include-pages/footer.jsp"%>
</body>
</html>