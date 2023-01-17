<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
</head>

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
<!--analysis Tab attachment js functions -->
<script type="text/javascript" src="js/desktop-version/admin/Admin-sub/analysis-append.js"></script>
<script type="text/javascript" src="js/desktop-version/admin/Admin-sub/analysis-datacall.js"></script>
<script type="text/javascript" src="js/desktop-version/admin/auth-check.js"></script>
<script type="text/javascript" src="js/desktop-version/errorpages.js"></script>
<!--toast UI for explorer  -->
<script src="https://uicdn.toast.com/tui.chart/latest/tui-chart-all.js"></script>

<body>
	<!-- <heade> -->
	<%@ include file="../../include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper">
		<div class="inner">
			<div class="contents bgs">
				<section>
					<div class="inner">
						<h2 class="section-title">통계 데이터</h2>
					</div>
				</section>
			</div>

			<section class="data-area">
				<div class="inner">
					<div class="tab-area" style="position: relative;">
						<ul class="tab">
						</ul>
						<!-- 계열사별 start-->
						<!-- 계열사별 end-->
						</div>
					</div>
			</section>

		</div>
	</div>

	<!-- CONTENTS [e] -->

	<script>
	//add if else phase about return flag value of userAuthCheck function, so that can divide which 'll be appended (error or correct)
	if(userAuthCheck() == true) {
		appendTabs();
		appendCrewData();
		appendOrgData();
		appendCompanyData();
	} else {
		userAuthAppend(".contents-wrapper .inner");
	}

	
    $(function(){
        /* 탭 start */
        $('ul.tab li').click(function(){
            var tab_id = $(this).attr('data-tab');
            $('ul.tab li').removeClass('current');
            $('.tab-content').removeClass('current');
            $(this).addClass('current');
            $("#"+tab_id).addClass('current');
        })
        /* 탭 end */
    
        /*  통계 데이터 직책자/팀원 라디오버튼 start*/
        div_change();   
        $(".bt").click(function() { div_change(); }); 
    
        function div_change() {    
            var chk = $("input[name='bt_01']").filter(function() {if (this.checked) return this;}).val();  
            $(".tab_section").hide(); 
            $("#img_"+chk).show(); 
        }  
        /* 통계 데이터 직책자/팀원 라디오버튼 end */
    });
    </script>
	<!-- FOOTER -->
	<%@ include file="../../include-pages/footer.jsp"%>
</body>
</html>
