<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
<!-- <link rel = "stylesheet" type = "text/css" href = "publisher-folder/css/style.css"></link>
<link rel = "stylesheet" type = "text/css" href = "publisher-folder/css/slick.css"></link>

<script type = "text/javascript" src="publisher-folder/js/jquery-3.3.1.min.js"></script>
<script type = "text/javascript" src="publisher-folder/js/script.js"></script>
<script type = "text/javascript" src="publisher-folder/js/slick.min.js"></script> -->
</head>

<!-- #include virtual="/view/SVNProject/working/etc/smartwork/html/include/common.html" -->

<script type="text/javascript" src="js/desktop-version/diagnose/diagnose.js"></script>
<!--diagnose js functions -->
<body>
	<!-- GNB -->
	<!-- #include virtual="/view/SVNProject/working/etc/smartwork/html/include/header.html" -->
	<!-- <header id = "header"> -->
	<%@ include file="../include-pages/header.jsp"%>
	<!-- </header> -->

	<!-- CONTENTS [s] -->
	<!-- <div class = "column" id = "all-components">
      <div class = "column" id = "test-result">
        <h1>진단결과</h1>
        <h1 id = "result-score">NaN</h1><h3>/100</h3></br>
        <h3>정답 </h3><h3 id = "correct-num">NaN</h3><h3>개 문항</h3></br>
        <h1 id = "trialnum"></h1><h1>차 진단이 완료되었습니다.</h1>
      </div>
      <div class = "column" id = "suggestion-guide"> -->
	<!-- <button type="button" class="square-btn has-icon white" id = "{idx}">
          <span class="icon {toolname}">{toolname}</span>{bmkcomment}
        </button> -->
	<!-- </div>
      <div class = "column" id = "test-offer-tools">
      </div>
  </div> -->
	<!-- CONTENTS [e] -->

	<div class="contents-wrapper bg-blue">
		<div class="inner">
			<div class="contents">
				<section>
					<div class="inner">
						<div class="columns-wrapper cell-type">
							<div class="column has-title">
								<h2 class="section-title">진단결과</h2>
								<div class="text-box rounded-box status-box">
									<p class="progress">
										<strong id="result-score">NaN</strong>/100
									</p>
									<p class="info">
										<strong>정답 </strong> <strong id="correct-num">NaN</strong> <strong>문항</strong>
									</p>
									<p class="large" id="trialnum">NaN</p>
									<p class="btn-wrapper">
										<a href="myinfo" class="square-btn">내 정보 보러가기</a>
									</p>
								</div>
							</div>
							<div class="column has-title">
								<h2 class="section-title">추천 가이드</h2>
								<div class="text-box rounded-box">
									<ul class="board-list" role="list" id="suggestion-guide">
										<!-- Here 'll be added the suggestion guide-->
									</ul>
								</div>
							</div>
						</div>
					</div>
				</section>

				<section class="status">
					<div class="inner">
						<h2 class="section-title">진단 현황</h2>
						<div class="columns-wrapper" role="list" id="test-offer-tools">
							<!-- Here 'll be added the testOfferTools-->
						</div>
					</div>
				</section>
			</div>
		</div>
	</div>

	<!-- FOOTER -->
	<!-- #include virtual="/view/SVNProject/working/etc/smartwork/html/include/footer.html" -->
	<%@ include file="../include-pages/footer.jsp"%>
</body>
</html>

<script>
	var favoriteList_Data_Container = callFavoriteList();
	var toolname = localStorage.getItem("toolname");
	callRecentTestData();
	suggestionList();
	testOfferTools();

	//onefunction
	function callRecentTestData() {
		var recentData = null;
		var correctnum = null;

		recentData = callRecentTestResult(toolname);
		correctnum = callCorrectNum(toolname);

		$("#result-score").text(recentData.totalscore);
		$("#correct-num").text(correctnum);
		$("#trialnum").text(recentData.trialnum + "차 진단이 완료되었습니다.");
	}

	//onefunction
	function suggestionList() {
		var suggestionData = null;

		var suggestionTemplate = "<li role=\"listitem\" class=\"suggestion-list-items\">"
				+ "<a href=\"{url}\"><span class=\"tag {toolname}\">{toolname}</span>{bmkcomment}</a>"
				+ "<div class=\"btn-wrapper\">"
				+ "<button type=\"button\" class=\"icon star\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"
				+ "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"
				+ "</div>"
				+ "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"
				+ "<input type=\"hidden\" id=\"url\" value=\"{url}\" />"
				+ "</li>";

		suggestionData = callSuggestionList();
		for (var i = 0; i < suggestionData.length; i++) {
			var temp_suggestion = null;
			if(42<=suggestionData[i].secindex<45) {
				temp_suggestion = $("#suggestion-guide").append(
					suggestionTemplate.replace(
							/{url}/gi,
							localStorage.toolGuide.split('/DWSWS/')[1]
									+ suggestionData[i].toolname + "&start="
									+ suggestionData[i].bmktime + "&second=true").replace(
							"{idx}", suggestionData[i].idx).replace(
							/{toolname}/gi, suggestionData[i].toolname)
							.replace("{bmkcomment}",
									suggestionData[i].bmkcomment).replace(
									"{bmkidx}", suggestionData[i].bmkindex));
			favoriteList_Data_Container.forEach(function(element) {
				if (element.bookmark == suggestionData[i].bmkindex) {
					temp_suggestion.find(
							"input[value=" + element.bookmark + "]").parent()
							.find('.icon.star').addClass('on');
				}
			})
			} else {
				temp_suggestion = $("#suggestion-guide").append(
					suggestionTemplate.replace(
							/{url}/gi,
							localStorage.toolGuide.split('/DWSWS/')[1]
									+ suggestionData[i].toolname + "&start="
									+ suggestionData[i].bmktime).replace(
							"{idx}", suggestionData[i].idx).replace(
							/{toolname}/gi, suggestionData[i].toolname)
							.replace("{bmkcomment}",
									suggestionData[i].bmkcomment).replace(
									"{bmkidx}", suggestionData[i].bmkindex));
			favoriteList_Data_Container.forEach(function(element) {
				if (element.bookmark == suggestionData[i].bmkindex) {
					temp_suggestion.find(
							"input[value=" + element.bookmark + "]").parent()
							.find('.icon.star').addClass('on');
				}
			})
			}
		}
	}

	//onefunction
	function testOfferTools() {
		var data = null;
		var toolImageTemplate = "<div class=\"qna column\" role=\"listitem\">"
				+ "<div class=\"text-box status-box\">"
				+ "<div class=\"visual logo\">"
				+ "<img src=\"publisher-folder/images/logo_{toolname}.jpg\"  alt=\"\">"
				+ "</div>"
				+ "<p class=\"progress\">"
				+ "<strong>{totalscore}</strong>/100"
				+ "</p>"
				+ "<p class=\"info\"><strong>{trialnum-text}진단</strong> ({questionnum}문항)</p>"
				+ "</div>"
				+ "<div class=\"button-box\">"
				+ "<a href=\"{url}\" class=\"square-btn border\">가이드 보기</a>"
				+ "<a id=\"diagnose-access\" class=\"square-btn\" name=\"{idx}\" data-trial = \"{trialnum}\" data-score=\"{currentscore}\" data-toolname=\"{toolname}\" style=\"cursor:pointer\">진단시작</a>"
				+ "</div>" + "</div>";

		data = callMainpageDataList();
		for (var i = 0; i < data.length; i++) {
			if (data[i].updatedate == null) {
				$("#test-offer-tools")
						.append(
								toolImageTemplate.replace(/{toolname}/gi,
										data[i].toolname).replace(
										"{totalscore}", 0).replace(
										"{trialnum-text}", "미").replace(
										"{trialnum}", data[i].trialnum)
										.replace("{questionnum}",
												data[i].questionnum).replace(
												"{idx}", i).replace(
												"{currentscore}", 0).replace(
												"{url}",
												localStorage.toolGuide
														.split('/DWSWS/')[1]
														+ data[i].toolname));
			} else {
				$("#test-offer-tools")
						.append(
								toolImageTemplate
										.replace(/{toolname}/gi,
												data[i].toolname)
										.replace(/{totalscore}/gi,
												data[i].totalscore)
										.replace("{trialnum-text}",
												data[i].trialnum + "차")
										.replace("{trialnum}", data[i].trialnum)
										.replace("{questionnum}",
												data[i].questionnum).replace(
												"{idx}", i).replace(
												"{currentscore}",
												data[i].totalscore).replace(
												"{url}",
												localStorage.toolGuide
														.split('/DWSWS/')[1]
														+ data[i].toolname));
			}
		}
		$(document).on(
				"click",
				"#diagnose-access",
				function(event) {
					localStorage.setItem("toolname", $(this).attr(
							"data-toolname"));
					localStorage
							.setItem("trialnum", $(this).attr("data-trial"));
					localStorage.setItem("mycurrentscore", $(this).attr(
							"data-score"));
					localStorage.setItem("mycurrentTotalscore",
							callTotalscoreFromRankingTable());
					location.href = "diagnose-test";
				})
	}
</script>
