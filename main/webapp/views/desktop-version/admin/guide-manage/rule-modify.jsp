<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
</head>

<body>
	<!--header  -->
	<%@ include file="../../include-pages/header.jsp"%>
	<!-- CONTENTS [s] -->
	<div class="contents-wrapper contents-header bg-blue">
		<section class="header-tab">
			<div class="inner">
				<h2 class="section-title">컨텐츠 관리</h2>
				<ul class="tab">
					<li class="tab-link current"><a href="contents">가이드관리</a></li>
					<li class="tab-link"><a href="survey-modify">진단관리</a></li>
					<li class="tab-link digital"><a>디지털창</a></li>
					<li class="tab-link" ><a href="authManagement">권한관리</a></li>
				</ul>
			</div>
		</section>
	</div>
	<div class="contents-wrapper has-side-menu">
		<div class="inner">
			<!-- LNB [s] -->
			<%@ include file="../../include-pages/contents-admin-lnb.jsp"%>
			<!-- LNB [e] -->
			<div class="contents">
				<section>
					<div class="inner">
						<h3 class="section-title">스마트워크 Rule</h3>
					</div>
				</section>

				<form action="#" class="form">
					<div class="inner">
						<fieldset>
							<legend class="sr-only">기본 정보</legend>
							<table>
								<colgroup>
									<col style="width: 75px">
									<col style="width: 330px">
									<col>
									<col>
								</colgroup>
								<tbody class="contentsInfo">
									<!--한 줄 설명,상세 설명, 영상 URL과 같은 영상의 기본 정보 -->
									
								</tbody>
							</table>
						</fieldset>

						<div class="ruleList"></div>
							<!--룰 설명과 그에 해당하는 도구의 기능  -->

						<fieldset class="btn-wrapper">
							<button type="button" id="cancel4rule" class="square-btn gray">취소</button>
							<button type="button" id="submit4rule" class="square-btn">수정완료</button>
							<button type="button" class="square-btn border text-icon large addSection4rule">
								+<span class="sr-only">추가</span>
							</button>
						</fieldset>
					</div>
				</form>
			</div>
		</div>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%-- <%@ include file="../../include-pages/footer.jsp"%> --%>
</body>

<!-- error message append -->
<script type="text/javascript" src="js/desktop-version/errorpages.js"></script>
<script type="text/javascript" src="js/desktop-version/admin/auth-check.js"></script>
<!--스마트워크Rule 수정 관련 js  -->
<script type="text/javascript" src="js/desktop-version/admin/guide-manage/rule-modify.js"></script>
<!--디지텅창 lnb 관련 js  -->
<script type="text/javascript" src="js/desktop-version/admin/digital/digital_lnb.js"></script>
<!--디지텅창 main 관련 js  -->
<script type="text/javascript" src="js/desktop-version/admin/digital/digital_main.js"></script>
<script>

/*	@param
	toolList : Zoom, Lineworks 등 도구리스트
 	oneDesc : 한 줄 설명
 	ruleDesc : 상세 설명
 	fullUrl : 영상 URL
 	videoTitle : 영상제목
 	data : 스마트워크 Rule 데이터 ex) 업무계획수립 - 업무계획을 수립하여 일과를 계획합니다.
 	bmkMap : 도구별 북마크리스트 가져와서 select형태로 변환 ex) Zoom - 환경설정, 마이크/화면확인...
 	
 	@function initalTemplate
 	사용자 권한 확인 후 초기 데이터를 가져와서 기본 템플릿 출력하는 함수
 */
initialTemplate(${toolList}, ${oneDesc}, ${ruleDesc}, ${fullUrl},  ${videoTitle}, ${ruleList}, ${bmkMap});

</script>
</html>