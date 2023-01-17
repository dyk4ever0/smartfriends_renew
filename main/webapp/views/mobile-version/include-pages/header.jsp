<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<style>
.main header {
	background: black;
	color: #1b1b1b;
}
  
header nav>.accordion-menu>.accordion-item>.anchor>span {
	float: right;
}

.none-set {
	display: none!important;
}
</style>
<header>
	<div class="inner">
		<!-- 해당 카테고리에 맞게 개발에서 변경 -->
		<!-- 상단 로고 아이콘 -->
		<h1 class="header-title"></h1>
		<h1 class="logo">
			<a href="main"><img
				src="publisher-folder/mobile/images/logo_white.png"
				alt="대웅 Smart Work"></a>
		</h1>
		<!-- 우상단 아이콘들 -->
		<ul class="util-menu" role="navigation">
			<li><a href="#"> <span class="icon chat">대화</span>
			</a></li>
			<li class="none-set">
				<!-- 즐겨찾기 badge --> <a href="#" aria-haspopup="true"
				aria-controls="noticeLayer"> <span class="icon notice none-set">알림</span>
					<span class="badge none-set"></span>
			</a>
			</li>
			<li><a href="#" aria-haspopup="true"
				aria-controls="gnbMenuLayer"> <span class="icon menu">메뉴</span>
			</a></li>
		</ul>
	</div>
	<!-- 메뉴 버튼 클릭시 등장하는 창 -->
	<div class="gnb-menu-layer modal" id="gnbMenuLayer" role="navigation"
		aria-modal="true">
		<div class="layer">
			<div class="panel">
				<div class="status none-set">
					<div class="portrait">
						<img src="{userimage}" alt="">
					</div>
					<div class="info">
						<p class="affiliation">사용자 정보가 없습니다</p>
						<p class="name">로그인이 필요합니다</p>
					</div>
				</div>
				<!-- on jquery, onclick event handles redirect : leave 'href' as '#' -->
				<ul class="columns-menu none-set">
					<li><a href="#"><i class="icon chart"></i>통계 데이터</a></li>
					<li><a href="#"><i class="icon document"></i>컨텐츠 관리</a></li>
				</ul>
				<nav>
					<ul class="accordion-menu">
						<li class="accordion-item"><a href="main" class="anchor">메인<span>></span></a>
						</li>
						<li class="accordion-item">
							<button type="button" class="anchor" aria-expanded="true">가이드</button>
							<ul class="accordion-menu">
								<li class="accordion-item"><a href="guide" class="anchor">스마트워크
										Rule</a></li>
								<li class="accordion-item">
									<!-- Contents from DB -->
									<button type="button" class="anchor" aria-expanded="true">도구
										가이드</button>
									<ul class="link-menu">
										<!--Zoom, Lineworks  -->
									</ul>
								</li>
								<li class="accordion-item">
									<!-- Contents from DB -->
									<button type="button" class="anchor" aria-expanded="true">보안</button>
									<ul class="link-menu">
										<!--vpn, DLP  -->
									</ul>
								</li>
								<li class="accordion-item">
									<!-- Contents from DB -->
									<button type="button" class="anchor" aria-expanded="true">디지털창</button>
									<ul class="link-menu">
									<!-- 베아월드, 간편도구, FIORI  -->
									</ul>
								</li>
							</ul>
						</li>
						<li class="accordion-item none-set">
							<!-- mobile page 'll be used by controller's interceptor that -->
							<a href="diagnose-main" class="anchor">진단</a>
						</li>
						<li class="accordion-item none-set"><a href="myinfo" class="anchor">내정보</a>
						</li>
					</ul>
				</nav>
			</div>
			<button type="button" class="close">닫기</button>
		</div>
	</div>
	<!-- 즐겨찾긱 클릭시 등장하는 창 -->
	<div class="common-layer modal" id="noticeLayer" role="document"
		aria-modal="true">
		<div class="layer">
			<div class="layer-header">
				<h2 class="title">알람</h2>
				<button type="button" class="close">닫기</button>
			</div>
			<div class="layer-body">
				<p class="title">북마크 즐겨찾기</p>
				<ul class="board-list" role="list">
					<!-- listitem 개당 한개의 즐겨찾기 -->
				</ul>
			</div>
		</div>
	</div>
</header>

<script>
	//append user potrait and other info
	appendUserInfo();
</script>