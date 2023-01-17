<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
</head>

<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp"%>
	<script type="text/javascript" src="js/desktop-version/admin/auth-manage/auth-manage-datacall.js"></script>
	<script type="text/javascript" src="js/desktop-version/admin/auth-manage/auth-manage-append.js"></script>
	<script type="text/javascript" src="js/desktop-version/admin/auth-check.js"></script>
	<script type="text/javascript" src="js/desktop-version/errorpages.js"></script>
	<!-- digital 창 전환 import -->
	<script type="text/javascript" src="js/desktop-version/admin/digital/digital_main.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/digital/digital_lnb.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/guide-manage/contents_admin_lnb.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/guide-manage/tool-modify.js"></script>
    <script type="text/javascript" src="js/desktop-version/admin/guide-manage/add-contents.js"></script>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper contents-header bg-blue">
		<section class="header-tab">
			<div class="inner">
                <h2 class="section-title">계정권한관리</h2>
                <ul class="tab">
                    <li class="tab-link" ><a href="contents">가이드관리</a></li>
					<li class="tab-link"><a href="survey-modify">진단관리</a></li>
					<li class="tab-link digital"><a>디지털창</a></li>
                    <li class="tab-link current" ><a href="authManagement">권한관리</a></li>
                </ul>
            </div>
		</section>
	</div>
	<div class="contents-wrapper has-side-menu">
		<div class="inner">
			<!-- LNB [s] -->
			<%@ include file="../include-pages/authManage-lnb.jsp"%>
			<!-- LNB [e] -->
			<div class="contents">
				<section>
					<div class="inner">
						<h3 class="section-title" id="tab-title">편집자 목록</h3>
					</div>
				</section>

				<section>
					<!--  편집자 start -->
					<div class="inner">
						<div class="editor-area">
							<ul id="register-auth-user">
							</ul>
						</div>
					</div>
				</section>
				<!--  편집자 end -->



				<!-- 편집자 추가 start -->
				<section class="editor-add">
					<div class="inner">
						<h3 class="section-title" id="tab-mng-title">편집자 추가</h3>
					</div>
				</section>

				<section>
					<div class="inner">

						<div class="search">
							<input type="text" class="search-area"
								placeholder="검색할 이름을 입력하세요">
							<button type="submit" class="search-button"></button>

							<!-- 이름 입력 확정 start  : this area is hidden area-->
							<div class="search-name-ok" style="display: none;"></div>
							<!-- 이름 입력 확정 end -->


							<!--  이름 검색시 박스 start -->
							<div class="search-name-box" style="display: none;">
								<!-- need to find trigger of this box -->
								<div class="search-list">
									<ul id="search-result">
									</ul>
								</div>
							</div>
							<!--  이름 검색시 박스 end -->

							<!-- 버튼영역 start -->
							<div class="btn-wrapper">
								<button type="button" class="square-btn gray">취소</button>
								<button type="button" class="square-btn" id="register">수정완료</button>
							</div>
							<!-- 버튼영역 end-->
						</div>
					</div>
				</section>
				<!-- 편집자 추가 end -->
			</div>
		</div>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>


</body>
</html>

<script>
	if(userAuthCheck() == true) {
		appendRegisteredUserData(authtype); // parameter for testing. (should be implemented into variable about left side tab.)
	} else {
		$(".contents-wrapper.contents-header").remove();
		noneTestDataError("Unauthorized"
							, "[자격 관리 페이지]에 접근할 수 없습니다."
							, "현재 사용자는 해당 페이지의 접근 권한이 없습니다. <br> 관리자에게 접근권한을 요청해야합니다."
							, "main"
							, "메인으로 이동"
							, ".contents-wrapper.has-side-menu .inner");
	}
	

	/**
	 * This part is for handling click event of search submit button about newly issuing auth
	 */
	var searchResult = null;
	$(document).on("click", ".search-button", function(event) {
		searchResult = searchedUserList($(".search-area").val());
		appendSearchedUserList(searchResult);
		$('.search-name-box').fadeIn();
	})

	/**
	 * this part is for hiding 'search-name-box' when other place clicked.
	 */
	$(document).mouseup(function(event) {
		if($(event.target).closest(".search-name-box").length == 0) {
			$(".search-name-box").fadeOut();
		}
	});

	/**
	 * This part is for registering new 
	 */
	 $(document).on("click", "#register", function(event) {
		var registerData = {
			"authtype" : authtype,
			"newUserList": 
				(function(){
					var result = [];
					$('.search-name-ok').children('span').each(function(index) {
						result[index] = {
							"userindex": $(this).attr("data-userindex")
						};
					});
					return result;
				})()
		};
		registerNewAuth(registerData);
	})
</script>