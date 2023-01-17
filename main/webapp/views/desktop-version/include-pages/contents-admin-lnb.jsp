<!--admin - 컨텐츠관리 - 가이드관리탭에서 사용되는 contents-lnb  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
     <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<aside>
    <div class="body">
        <ul class="link-menu guide">
            <li>
                <a href="contents" class="active"><span class="img-icon" style="background-image:url(publisher-folder/images/dw_icon.png);"></span>스마트워크Rule</a>
            </li>
          
           <!--컨텐츠 목록 출력 : 스마트워크Rule, Zoom, Lineworks..  -->
            
        </ul>
    </div>
    <div class="bottom">
        <div class="btn-wrapper">
            <button type="button" class="square-btn border text-icon plus">+<span class="sr-only">추가</span></button>
            <button type="button" class="square-btn border text-icon minus">-<span class="sr-only">삭제</span></button>
        </div>
    </div>
</aside>
<!--contents_admin_lnb 관련 js  -->
<script type="text/javascript" src="js/desktop-version/admin/guide-manage/contents_admin_lnb.js"></script>
<!--tool-modify 관련 js  -->
<script type="text/javascript" src="js/desktop-version/admin/guide-manage/tool-modify.js"></script>
<!--add-contents 관련 js  -->
<script type="text/javascript" src="js/desktop-version/admin/guide-manage/add-contents.js"></script>