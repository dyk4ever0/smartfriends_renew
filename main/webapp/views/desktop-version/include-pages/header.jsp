<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<style>
header .gnb-menu li a:hover{
color: #bb9dd6;
}
</style>
<header style="background: #1b1b1b">
	<!--sessopn input 추가  -->
	<input type="hidden" name="userindex" value="${sessionScope.userindex}" />
	<div class="inner">
		<h1 class="logo">
			<a href="/DWSWS/main"><img
				src="publisher-folder/images/logo_header.png" alt="대웅 Smart Work" /></a>
		</h1>

		<ul class="gnb-menu" role="navigation">
			<li><a href="main#about" class="scroll">소개</a></li>
			<li><a href="guide">가이드</a></li>
			<li><a href="diagnose-main">진단</a></li>
			<li><a href="myinfo">내 정보</a></li>
		</ul>

		<ul class="util-menu" role="navigation">
			<li><a href="#" class="icon chat"><span class="sr-only">대화</span></a></li>
			<li><a href="#" class="icon notice" aria-expanded="false"
				aria-controls="noticeLayer"><span class="sr-only">알림</span><span
					class="badge" style="background: #bb9dd6;"></span></a></li>
			<li><a href="#" class="icon stats"><span class="sr-only">통계</span></a></li>
			<li><a href="#" class="icon setup"><span class="sr-only">컨텐츠관리</span></a></li>
		</ul>

		<div class="notice-layer-wrapper" id="noticeLayer" role="document"
			aria-modal="true">
			<div class="layer">
				<div class="panel">
					<p class="title">북마크 즐겨찾기</p>
					<ul class="notice-list" role="list">

					</ul>
				</div>
			</div>
		</div>
	</div>
</header>