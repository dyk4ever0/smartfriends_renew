<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<!DOCTYPE html>
<html>
<head>
<style>
.font-32 {
    font-size: 32px!important;
}
</style>
<title>대웅 SmartWork Friends</title>
</head>
<%@ include file="../include-pages/common.jsp" %>
<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp" %> 
	
    <!-- CONTENTS [s] -->
    <div class="contents-wrapper bg-blue error">
        <div class="inner">
            <div class="contents">
                <section>
                    <div class="inner">
                        <div class="error-box">
                            <h2 class="code font-32">{code} Error</h2>
                            <p class="message">[내 정보] 페이지에 접근 중 에러가 발생했습니다. <br>추가 워딩의 경우 두줄로 표기되며 텍스트박스 가로는 550입니다.</p>
                            <p class="description">‘박승수’님의 진단 데이터가 아직 없습니다. <br>[내 정보] 페이지에 접근을 원하시면 [진단]을 수행해주세요. <br>텍스트박스 가로 550입니다.</p>
                        </div>
                        <p class="button-wrapper">
                            <a href="#" class="square-btn">메인으로 이동</a>
                        </p>
                    </div>
                </section>
            </div>
        </div>
    </div>
    <!-- CONTENTS [e] -->

	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp" %>


</body>
</html>