<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<aside>
	<div class="body">
		<ul class="link-menu">
			<li><a class="active"><span class="img-icon" style="background-image: url(publisher-folder/images/ico_link_editor.png);"></span>편집자 관리</a></li>
			<li><a><span class="img-icon" style="background-image: url(publisher-folder/images/ico_link_admin.png);"></span>데이터 조회 관리</a></li>
		</ul>
	</div>
</aside>

<script>
    /**
     * This code is for changing authtype by clicking aside components.
     */
    var authtype = "EDITAUTH";
    $(".link-menu li").click(function(event) {
        if($(this).find("a").text() == "편집자 관리") {
            authtype = "EDITAUTH";
            $(".link-menu").find(".active").removeClass();
            $(this).find("a").addClass("active");
            $("#tab-title").text($(this).find("a").text().split(" ")[0] + " 목록");
            $("#tab-mng-title").text($(this).find("a").text().split(" ")[0] + " 추가");
            appendRegisteredUserData(authtype);
            
        }
        else if($(this).find("a").text() == "데이터 조회 관리") {
            authtype = "DATAAUTH";
            $(".link-menu").find(".active").removeClass();
            $(this).find("a").addClass("active");
            $("#tab-title").text("데이터 조회 권한" + " 목록");
            $("#tab-mng-title").text("데이터 조회 권한" + " 추가");
            appendRegisteredUserData(authtype);
        }
        else alert("잘못된 타입이 입력되었습니다.")
    });
</script>