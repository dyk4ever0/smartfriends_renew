<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
<style>
.question .question-list .radio-wrapper .answer.selected+span {
	outline: none;
}
</style>
</head>

<script type="text/javascript" src="js/desktop-version/diagnose/diagnose.js"></script>
<!--diagnose js functions -->
<body>
	<!-- header -->
	<%@ include file="../include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper bg-blue limit-height">
		<div class="inner">
			<div class="contents">
				<section>
					<div class="inner" id="user-info">
						<!-- Here 'll be appended userinfo template -->
					</div>
				</section>

				<section class="question">
					<div class="inner">
						<ul class="question-list" role="list">
							<!-- Here 'll be appended question template -->
						</ul>

						<p class="button-wrapper">
							<button type="button" id="resultSubmit"
								onclick="saveTestResult(event)" class="square-btn">제출</button>
						</p>
					</div>
				</section>
			</div>
		</div>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>
</body>
</html>

<script>
	var data = null;

	var toolname = localStorage.getItem("toolname");
	var trialnum = localStorage.getItem("trialnum");
	var mycurrentscore = localStorage.getItem("mycurrentscore");
	var mycurrentTotalscore = localStorage.getItem("mycurrentTotalscore");
	var ansResult = [];

	var userInfoTemplate = "<h2 class=\"section-title\">{toolname}<span class=\"sub-title\">{trialnum}차 진단</span></h2>"
			+ "<div class=\"member-profile\">"
			+ "<div class=\"portrait\">"
			+ "<img src=\"{userimage}\" alt=\"\">"
			+ "</div>"
			+ "<div class=\"info\">"
			+ "<p class=\"affiliation\">{orgname}</p>"
			+ "<p class=\"name\">{username}</p>" + "</div>" + "</div>";

	var questionListTemplate = "<li role=\"listitem\">"
			+ "<p>{question-content}   </p>"
			+ "<div class=\"radio-wrapper\">"
			+ "<label>"
			+ "<input type=\"button\" class=\"answer\" name=\"{num}\" value=True title=\"네\">"
			+ "<span class=\"yes\">O</span>"
			+ "</label>"
			+ "<label>"
			+ "<input type=\"button\" class=\"answer\" name=\"{num}\" value=False title=\"아니오\">"
			+ "<span class=\"no\">X</span>" + "</label>" + "</div>" + "</li>";

	data = callQuestionList(toolname);
	userdata = callSessionData();

	$("#user-info").append(
			userInfoTemplate.replace("{toolname}", toolname).replace(
					"{trialnum}", parseInt(trialnum) + 1).replace("{orgname}",
					userdata.userorgname).replace("{username}",
					userdata.username).replace("{userimage}",
					"http://bearworld.co.kr" + userdata.userimage));

	for (var i = 0; i < data.length; i++) {
		$(".question-list").append(
				questionListTemplate.replace(/{num}/gi, i + 1).replace(
						"{question-content}", data[i].question));
	}
	$(document).on(
			"click",
			".question .question-list .radio-wrapper .answer",
			function(event) {
				if ($(this).val() == "True") {
					ansResult[($(this)[0].name) - 1] = 1;
					$(this).addClass('selected');
					$(
							"input[name=\'" + $(this)[0].name + "\']"
									+ "[value=\'False\']").removeClass(
							'selected');
				} else if ($(this).val() == "False") {
					ansResult[($(this)[0].name) - 1] = 0;
					$(this).addClass('selected');
					$(
							"input[name=\'" + $(this)[0].name + "\']"
									+ "[value=\'True\']").removeClass(
							'selected');
				} else {
					alert("결과를 저장하는데 에러가 발생했습니다.")
				}
			})
</script>
