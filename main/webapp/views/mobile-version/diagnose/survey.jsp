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
   <!-- QNA 전용 header [s] -->
   <header>
        <div class="inner">
            <a href="diagnose-main" class="back"><span class="sr-only">뒤로가기</span></a>
            <h1 class="header-title">{toolname}</h1>
            <h2 class="sub-title">{trialnum}차진단</h2>
        </div>
    </header>
    <!-- QNA 전용 header [e] -->

    <!-- CONTENTS [s] -->
    <div class="contents-wrapper">
        <section class="question transparent">
            <div class="inner">
                <ul class="question-list" role="list">
                    <!-- one question item -->
                </ul>

                <p class="button-wrapper">
                    <button type="button" onclick="saveSurveyResult(event);" class="square-btn">제출하기</button>
                </p>
            </div>
        </section>
    </div>
    <!-- CONTENTS [e] -->

    <!-- FOOTER -->
    <%@ include file="../include-pages/footer.jsp" %>
</body>
</html>

<script>
    //Diagnose header text change
    $(".header-title").text("진단");
    //append question list
    appendToolQuestionList();
</script>

<!-- <script type="text/javascript">
    window.onbeforeunload = function() {
        return "preventing refreshing";
    }
</script> -->