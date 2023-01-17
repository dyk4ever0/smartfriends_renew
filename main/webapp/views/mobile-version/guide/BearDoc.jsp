<!--[mobile] 가이드 - 보안 - 베어독관련 jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<style>
/*part1, part2 버튼  */
.text-box .part .partBtn{
	width: 100px;
    height: 40px;
    border-radius: 35px;
    margin-left: 10px;
    margin-top: 20px;
    font-size: 18px;
    box-shadow: 1px 2px 3px #dfdfdf;
    border: 2px solid #bb9dd6;
    background: #fff;
    color: #bb9dd6;
    outline: 0;
}

.text-box .part .partBtn.on{
	background: #bb9dd6;
    color: #fff;
}
div .part{
	padding-bottom: 25px;
}
/*part1, part2 p태그 글씨  */
.partLetter{
	color: #bb9dd6;
    padding-left: 18px;
    border-left: 5px solid #bb9dd6;
    font-size: 23px;
    margin-top: 23px;
}
</style>
<body>
	<!-- GNB -->
	<%@ include file="../include-pages/header.jsp"%>
	<!-- CONTENTS [s] -->
	<div class="contents-wrapper">
		<nav class="navigation">
			<div class="nav-wrapper">
				<div class="sub-nav category">
					<select name="category">
						<!--스마트워크룰, 도구가이드, 보안  -->
					</select>
				</div>
				<div class="sub-nav contents">
					<select name="contents">
						<!--Zoom, Linworks  -->
					</select>
				</div>
			</div>
		</nav>
		<section class="document">
			<div class="inner">
			</div>
		</section>
	</div>
	<!-- CONTENTS [e] -->
	
	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>
	
	<script src="js/mobile-version/guide/BearDoc.js"></script>
	<script>
	//header-title 변환
	$('.header-title').html("가이드");
	//category select box
	var categoryList = ${categoryList};
	categorySelect(categoryList);
	$('.sub-nav.category select').val("보안").attr('selected', 'selected')
	/*contents select box  */
	var contentsList = ${contentsList};
	contentsSelect(contentsList);
	
	/*전체적인 틀  */
	var data = ${guideInfo};
	makeToolGuide(data);
	/*section list  */
	var sectionData = ${sectionList};
	var SectionNum = ${num};
	makeSection(sectionData, SectionNum );
	//bookmark print
	var bmkList = ${bmkList};
	//bmkidx list in favorite list
	var bmkidx = ${bmkidx};
	/*bookmark list  */
	makeBookmark(bmkList, SectionNum, bmkidx);
	//category select box에서 현재 페이지에 해당하는 도구 selected
	$('.sub-nav.contents select').val($('.section-title').text()).prop("selected",true);
	
	
	 //PART1, PART2 버튼 템플릿
	var buttonTemplate = 
		"<div class=\"part\">"+
			"<button class=\"partBtn on\" data-src=\"{url1}\">Part 1</button>"+
			"<button class=\"partBtn\" data-src=\"{url2}\">Part 2</button>"+
		"</div>";
		
	//part1의 url 수정본
	var buttonTemplate2 = buttonTemplate.replace('{url1}', data.youtubeurl);
	
	var secondUrl = "";

	 //second url 가져오기
	  $.ajax({//해당 페이지의 도구 이름 전송 ex)BearDoc
		    type : "get",
		    url : "/DWSWS/secondUrl",
			async : false,
			cache: false,
		    data : {
		        "tool" : $('h2.section-title').text(),
		    },
		    success : function(data){
				
				secondUrl=data;
				
				$('.text-box p:eq(0)').after(buttonTemplate2.replace('{url2}', secondUrl));
				
				//두번째 영상을 재생시켜야하는 경우
				var second=${second};
				
				if(second == true){
				//두번째 동영상 로드
				 $('.video').html(returnYoutubeSource(secondUrl)); 
				 $('.text-box .part .partBtn').removeClass('on');
				$('.text-box .part .partBtn:eq(1)').addClass('on');
				
				//페이지 로드 시 특정시간부터 영상 재생 
				var start = ${start};
				var url = $('iframe').attr('src');
				var num = url.split('start=')[1].split('&')[0];
				$('iframe').attr('src', url.replace(num, start));
				
		}
		    },
		    error : function(){
		        alert("failed");
		    }
		}); 
		
		//페이지 로드 시 특정시간부터 영상 재생 
		var start = ${start};
		videoPlayTime(start);
	</script>
</body>
</html>