<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8" />
    <title>대웅 SmartWork Friends</title>

    <style>
        .recommend-title {
            display: flex;
            flex-direction: row;
            justify-content: left;
            align-items: center;
            overflow: scroll;
        }
        .recommend-title .btn-wrapper {
            padding-left : 1vh;
        }
        .recommend-title .btn-wrapper .Zoom {
            display: block;
            width: 100%;
            padding: 9px;
            background: #489afd;
            border: 1px solid #489afd;
            color: #fff;
            text-decoration: none;
            text-align: center;
            font-size: 15px;
            font-weight: 700;
            letter-spacing: -0.005em;
            line-height: 17px;
            border-radius: 5px;
        }

        .recommend-title .btn-wrapper .Lineworks {
            display: block;
            width: 100%;
            padding: 9px;
            background: #7ac523;
            border: 1px solid #7ac523;
            color: #fff;
            text-decoration: none;
            text-align: center;
            font-size: 15px;
            font-weight: 700;
            letter-spacing: -0.005em;
            line-height: 17px;
            border-radius: 5px;
        }

        .recommend-title .tag {
            margin-left : 1vh;
        }
        .recommend-title .tag .Zoom {
            display: block;
            width: 100%;
            background: #489afd;
            border: 1px solid #489afd;
            color: #fff;
            text-decoration: none;
            text-align: center;
            font-size: 15px;
            font-weight: 700;
            letter-spacing: -0.005em;
            line-height: 17px;
            border-radius: 5px;
        }

        .recommend-title .tag .Lineworks {
            display: block;
            width: 100%;
            padding: 9px;
            background: #7ac523;
            border: 1px solid #7ac523;
            color: #fff;
            text-decoration: none;
            text-align: center;
            font-size: 15px;
            font-weight: 700;
            letter-spacing: -0.005em;
            line-height: 17px;
            border-radius: 5px;
        }
    </style>
</head>
<!-- common import -->
<%@ include file="../include-pages/common.jsp" %>
<!-- js import -->
<script type="text/javascript" src="js/mobile-version/suggestion/suggestion-append.js"></script>
<script type="text/javascript" src="js/mobile-version/suggestion/suggestion-datacall.js"></script>
<script type="text/javascript" src="js/mobile-version/diagnose/diagnose-datacall.js"></script>
<script type="text/javascript" src="js/mobile-version/diagnose/diagnose-append.js"></script>
<script type="text/javascript" src="js/mobile-version/header.js"></script>
<!-- error message appending import -->
<script type="text/javascript" src="js/mobile-version/errorpage.js"></script>
<body>
    <!-- header -->
    <%@ include file="../include-pages/header.jsp" %>

    <!-- CONTENTS [s] -->
    <div class="contents-wrapper">
        <section class="qna-result">
            <div class="inner">
                <h2 class="section-title">진단결과</h2>
                <div class="text-box">
                    <p class="progress">
                        <strong id="result-score">NaN</strong>/100
                    </p>
                    <p class="info"><strong>정답 </strong> <strong id="correct-num">NaN</strong> <strong>문항</strong></p>
                    <p class="large" id="trial-num">NaN차 진단이 완료되었습니다. </p>
                    <p class="btn-wrapper"><a href="myinfo" class="square-btn">내 정보 보러가기</a></p>
                </div>
            </div>
        </section>
        <section class="recommend-guide">
            <div class="inner">
                <div class="recommend-title">
                    <h2 class="section-title">오답 노트</h2>
                    <!-- appends incorrect answer suggestion show button -->
                </div>
                <ul class="board-list" role="list">
                    <!-- here, suggestion list places -->
                </ul>
            </div>
        </section>
        <section class="qna-status transparent">
            <div class="inner">
                <h2 class="section-title">진단 현황</h2>
                <div class="overflow-box x">
                    <div class="status-list columns-wrapper" role="list">
                        <!-- here, test offering tool's block 'll be appended -->
                    </div>
                </div>
            </div>
        </section>
    </div>
    <!-- CONTENTS [e] -->

    <!-- FOOTER -->
    <%@ include file="../include-pages/footer.jsp" %>
</body>
</html>

<script>
    //change header text change
    $(".header-title").text("진단 결과");
    //initial activating functions
    appendSurveyResult();
    appendingResultDashboard();
    //append suggestion show button
    appendIncorrectShowButton();

    //append announce mention to click tool name button
    // $('.recommend-guide .inner .board-list')
    appendErrorMessage("상단의 도구이름 버튼을 눌러주세요."
                        , "오답노트가 출력됩니다."
                        , "버튼을 누르면, 각 도구별 오답노트가 등장합니다."
                        , null
                        , null
                        , ".recommend-guide .inner .board-list"
                        , false);

    //click event handler for test offering tools block's [진단시작] button
    $(document).on("click", "#diagnose-access", function(event) {
        localStorage.setItem("toolname", $(this).attr("data-toolname"));
        localStorage.setItem("trialnum", $(this).attr("data-trial"));
        localStorage.setItem("mycurrentscore", $(this).attr("data-score"));
        localStorage.setItem("mycurrentTotalscore", callTotalscoreFromRankingTable());
        location.href = "diagnose-test";
    })
</script>