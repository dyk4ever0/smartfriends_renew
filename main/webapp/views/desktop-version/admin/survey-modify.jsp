<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
</head>

<body>
	<!-- header  -->
	<%@ include file="../include-pages/header.jsp"%>
	<script type="text/javascript" src="js/desktop-version/admin/survey-modify-lnb-append.js"></script>
	<script type="text/javascript" src="js/desktop-version/admin/survey-modify-lnb-datacall.js"></script>
	<script type="text/javascript" src="js/desktop-version/admin/auth-check.js"></script>
	<script type="text/javascript" src="js/desktop-version/errorpages.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/survey-modify-datacall.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/survey-modify-append.js"></script>
    
    <script type="text/javascript" src="js/desktop-version/admin/digital/digital_main.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/digital/digital_lnb.js"></script>
    <!-- contents admin lnb cause double deletion error (both survey & contents )-->
    <script type="text/javascript" src="js/desktop-version/admin/guide-manage/contents_admin_lnb.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/guide-manage/tool-modify.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/guide-manage/add-contents.js"></script>

	<!-- CONTENTS [s] -->
    <div class="contents-wrapper contents-header bg-blue">
        <section class="header-tab">
            <div class="inner">
                <h2 class="section-title">진단문항관리</h2>
                <ul class="tab">
                    <li class="tab-link" ><a href="contents">가이드관리</a></li>
					<li class="tab-link current"><a href="survey-modify">진단관리</a></li>
					<li class="tab-link digital"><a>디지털창</a></li>
                    <li class="tab-link" ><a href="authManagement">권한관리</a></li>
                </ul>
            </div>
        </section>
    </div>
    <div class="contents-wrapper has-side-menu">
        <div class="inner">
            <!-- LNB [s] -->
            <%@ include file="../include-pages/survey-modify-lnb.jsp"%>
            <!-- LNB [e] -->
            <div class="contents">
                <section>
                    <div class="inner">
                        <h3 class="section-title" id="survey-tool-target">
                            {survey name}
                        </h3>
                    </div>
                </section>
                
                <form action="#" class="form">
                    <div class="inner">
                        <div class="order-fields" role="list" id="questionList">
                            <!-- question elements append here (fieldset components) -->
                        </div>
                        <fieldset class="btn-wrapper">
                            <button type="button" class="square-btn gray" onclick="location.reload();">취소</button>
                            <button type="button" class="square-btn" onclick="collectUpdateQuestionData();">수정완료</button>
                            <button type="button" class="square-btn border text-icon large" onclick="appendNewQuestionFormat();">+<span class="sr-only">추가</span></button>
                        </fieldset>
                    </div>
                </form>
            </div>
        </div>
    </div>
    <!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>
</body>
</html>

<script>
    //check authorization about survey-modification
    if(userAuthCheck() == true) {
        appendSurveyOfferingTools();
        $($(".link-menu li")[0]).find("a").trigger("click");
    } else {
        $(".contents-wrapper.has-side-menu").remove();
        noneTestDataError("Unauthorized"
                            , "[문항 수정 페이지]에 접근할 수 없습니다."
                            , "현재 사용자는 해당 페이지의 접근 권한이 없습니다. <br> 관리자에게 접근권한을 요청해야합니다."
                            , "main"
                            , "로그인 하기"
                            , ".contents-wrapper.contents-header .inner");
    }
</script>