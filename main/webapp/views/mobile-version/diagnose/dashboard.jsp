<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>대웅 SmartWork Friends</title>
</head>
<!-- common import -->
<%@ include file="../include-pages/common.jsp" %>
<!-- js import -->
<script type="text/javascript" src="js/mobile-version/diagnose/diagnose-datacall.js"></script>
<script type="text/javascript" src="js/mobile-version/diagnose/diagnose-append.js"></script>
<body>
    <!-- header import -->
    <%@ include file="../include-pages/header.jsp" %>

    <!-- CONTENTS [s] -->
    <div class="contents-wrapper">
        <section class="qna-status transparent">
            <div class="inner">
                <h2 class="section-title">진단 현황</h2>
                <div class="overflow-box x">
                    <div class="status-list columns-wrapper" role="list">
                    </div>
                </div>
            </div>
        </section>
        <section class="document">
            <div class="inner">
                <h2 class="section-title">진단서비스의 특징</h2>
                <ul class="box-wrapper" role="list">
                    <li class="text-box" role="listitem">
                        <h3 class="title">도구들에 대한 개별 진단</h3>
                        <p>스마트워크 필수 도구들에 대해 각각 진단지가 존재합니다. 자신이 도구의 특징들을 잘 알고 있는지 테스트 해 보세요. </p>
                    </li>
                    <li class="text-box" role="listitem">
                        <h3 class="title">친절한 오답 가이드</h3>
                        <p>각각의 질문들에 대한 가이드가 존재합니다. 답을 적지 못한 질문은 제시되는 가이드를 통해 바로 학습이 가능합니다.</p>
                    </li>
                </ul>
            </div>
        </section>
    </div>
    <!-- CONTENTS [e] -->

    <!-- FOOTER -->
    <%@ include file="../include-pages/footer.jsp" %>
</body>
</html>

<script>
    appendDiagnoseDashboard();

    //Diagnose header text change
    $(".header-title").text("진단");

    /**
     * onclick function about diagnose-offer-test block's '진단시작' button
     * @author 박승수 (21.01.18)
     */
    $(document).on("click", ".square-btn", function(event) {
        localStorage.setItem("toolname", $(this).attr("data-toolname"));
        localStorage.setItem("trialnum", $(this).attr("data-trial"));
        localStorage.setItem("mycurrentscore", $(this).attr("data-score"));
        localStorage.setItem("mycurrentTotalscore", callTotalscoreFromRankingTable());
        location.href = "diagnose-test";
    })
</script>