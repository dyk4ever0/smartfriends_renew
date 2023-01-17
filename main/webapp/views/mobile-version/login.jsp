<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ page session="true"%>

<%@ include file="./include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
<link rel="stylesheet" href="css/mobile/login.css">
</head>
<body>
<div class="login-header-area">
<button class="back-btn" id="back"><i><img src="images/icons/arrow-back.svg"></i></button>
</div>
	<!-- header 영역 -->
	<div class="contents-wrapper">
		<section class="document" id="login">
			<div class="inner">
				<h2 class="login-title">
					베어월드 ID로<br /> <b>디지털 수준을 진단</b>하고<br /> <b>나의 캐릭터를 확인</b>해보세요!
				</h2>
				<ul class="login-wrapper">
					<li class="login-text">
						<i><img src="images/icons/user.svg"></i>
						<input type="text" name="id" id="id" placeholder="BearWorld ID">
					</li>
					<li class="login-btn">
						<button type="submit" id="signin" disabled>LOGIN</button>
					</li>
					<li class="main-btn"><button onclick="location.href='main'"><i><img src="images/icons/home.svg"></i>메인으로 이동</button></li>
				</ul>
			</div>
		</section>
	</div>
	<!-- footer 영역 -->
	<%@ include file="include-pages/footer.jsp"%>
</body>

<script type='text/javascript'>
var urlParams = window.location.search.substring(5);
	$("#signin").click(
			function(event) {
				$.ajax({
					type : "post",
					url : "/DWSWS/loginAuth",
					cache : false,
					data : {
						"id" : $("#id").val()
					},
					success : function(data) {
						if (data == "") {
							alert("존재하지 않는 ID 입니다.");
						} else {
							var urlParams = window.location.search.substring(5);
							if(urlParams == "") { // redirect to main page after login
								location.href = 'main';
							} else {
								location.href = urlParams; // redirect to foward page after login
							}
						}
					},
					error : function(request, error) {
						console.log(request, error);
						alert(
								"서버와의 통신 중 오류가 발생했습니다.");
					}
				})
			});
	$(document).ready(function() {
		$("#back").click(function(){
			window.history.back();
		});

		$("#id").keyup(function(e) {
			
			var id = $(this).val();
			
			if (id.length > 0) {
				$("#id").addClass('login-text-active');
				$("#signin").attr("disabled", false);
			} else if (id.length == 0) {
				$("#id").removeClass('login-text-active');
				$("#signin").attr("disabled", true);
			}
		});
	})
</script>
</html>