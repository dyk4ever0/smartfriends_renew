<!--가이드-도구가이드 페이지  -->
 <%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
 <%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp" %>
<title>대웅 SmartWork Friends</title>
</head>

<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp" %> 
	 
	    <!-- CONTENTS [s] -->
    <div class="contents-wrapper has-side-menu">
        <div class="inner">
            <!-- LNB [s] -->
            <!--#include virtual="/view/SVNProject/working/etc/smartwork/html/include/guide-lnb.html" -->
            <%@ include file="../include-pages/guide-lnb.jsp" %>
            <!-- LNB [e] -->
            <div class="start">
            
            </div>
         
    <!-- FOOTER -->
</body>

<script>
/*전체적인 틀  */
var toolGuideTemplate =
	"<div class=\"contents\">"+
	"<section class=\"guide-header\">"+
	    "<div class=\"inner\">"+
	        "<h2 class=\"title\">도구 가이드-{tool}</h2>"+
	        "<p>{comments}</p>"+
	    "</div>"+
	"</section>"+
	"<section class=\"full-video\">"+
	    "<div class=\"inner\">"+
	        "<div class=\"video-player\">"+
	             "<div class=\"video\" data-src=\"{url}\"> </div>"+
	        "</div>"+
	    "</div>"+
	"</section>"+
	"<section class=\"timestamp\">"+
	    "<div class=\"inner\">"+
	        "<div class=\"columns-wrapper\">"+
	        "</div>"+
	    "</div>"+
	"</section>"+
	"</div>"; 

	
	/*section list  */
 	var columnTemplate = 
		"<div class=\"column\">"+
		"<h3 class=\"title\">{comments}</h3>"+
		"<ul class=\"timestamp-list\">"+
		"</ul>"+
		"</div>";
		
	// 북마크를 출력할 때 사용자의 즐겨찾기에 이미 포함된 경우, 즐겨찾기의 별표가 색칠되어서 출력되어야하기 때문에 사용자별 즐겨찾기 목록 필요
	// 사용자의 즐겨찾기 idx 리스트와 북마크 idx를 비교하는 방식
	// 즐겨찾기 별표가 off 인 템플릿
	/*bookmark list star off */
	 var liTemplate1 = 
		"<li>"+
		"<a style='cursor : pointer'><span class=\"time\">{timemoment}</span>{bookmark}</a>"+
		"<div class=\"btn-wrapper\">"+
		    "<button type=\"button\" class=\"icon star\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
		    "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
		"</div>"+
		"<input type=\"hidden\" id=\"time\" name=\"timemoment\" value=\"{timemoment}\" />"+
		"<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"+
		"<input type=\"hidden\" id=\"url\" value=\"{url}\" />"+
		"</li>";
		
		// 북마크를 출력할 때 사용자의 즐겨찾기에 이미 포함된 경우, 즐겨찾기의 별표가 색칠되어서 출력되어야하기 때문에 사용자별 즐겨찾기 목록 필요
		// 사용자의 즐겨찾기 idx 리스트와 북마크 idx를 비교하는 방식
		// 즐겨찾기 별표가 on 인 템플릿
		/*bookmark list star on */
		var liTemplate2 = 
			"<li>"+
			"<a style='cursor : pointer'><span class=\"time\">{timemoment}</span>{bookmark}</a>"+
			"<div class=\"btn-wrapper\">"+
			    "<button type=\"button\" class=\"icon star on\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
			    "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
			"</div>"+
			"<input type=\"hidden\" id=\"time\" name=\"timemoment\" value=\"{timemoment}\" />"+
			"<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"+
			"<input type=\"hidden\" id=\"url\" value=\"{url}\" />"+
			"</li>"; 
 			
			//제목, 설명, url정보
			var data = ${guideInfo};


			 $('.start').append(toolGuideTemplate.replace("{tool}", data.title).replace("{comments}", data.comments).replace("{url}",data.youtubeurl));
			
			var url = $('.video').attr('data-src'); //zoomUrl 가져오기
			$('.video').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입  
			 
			
			  //클릭한 도구가이드 툴 active
			  for(var i=0; i<toolGuideList.length; i++){
				   if(data.title == $('.link-menu:eq(0) li:nth-child('+(i+1)+')').text().substring(1)){
					  $('aside .header a').attr('class','title'); //스마트워크 룰  active 지우기
					  $('.link-menu:eq(0) li:eq('+i+') a').attr('class', 'active'); // 클릭된 도구 active
				  } 
				  
			  } 
			
			//section print
			var sectionData = ${sectionList};

			for(var i=0; i<${num}; i++){
				$('.columns-wrapper').append(columnTemplate.replace("{comments}", sectionData[i]));
			}
			
			//bookmark print
			var bmkList = ${bmkList};
			
			//bmkidx list in favorite list
			var bmkidx = ${bmkidx};
			
			//toolguide url
			var toolguideUrl = localStorage.toolGuide;
		
				for(var i=0; i<${num}; i++){
					
				
				  for(var j=0; j<bmkList[i].length; j++){
					  
					//시간 00:00 형태로 바꿔주기
					var time = parseInt(bmkList[i][j].timemoment);
					var ten = "00";
					var one = "00";
					
					if((time/60) < 1){
						if((time%60) <10 ){
							one = "0"+time.toString();
						}else if((time%60) >=10 ){
							one = time.toString();
						}
						
					} else if((time/60) >= 10){
						ten = parseInt((time/60)).toString();
						if((time%60) <10 ){
							one = "0"+parseInt(time%60).toString();
						}else if((time%60) >=10 ){
							one = (time%60).toString();
						}
					} else{
						ten = "0"+parseInt((time/60)).toString();
						if((time%60) <10 ){
							one = "0"+(time%60).toString();
						}else if((time%60) >=10 ){
							one = (time%60).toString();
						}
					}
					//console.log(ten+":"+one);
					
					if(bmkidx.indexOf(bmkList[i][j].bmkidx) != -1){//즐겨찾기 목록에 해당되는 bookmark인 경우
						$('.columns-wrapper .column:nth-child('+(i+1)+') .timestamp-list').append(liTemplate2.replace("{timemoment}", ten+":"+one)
																											.replace("{bookmark}", bmkList[i][j].bookmark)
																											.replace("{timemoment}", bmkList[i][j].timemoment)
																											.replace("{bmkidx}", bmkList[i][j].bmkidx)
																											.replace("{url}", toolguideUrl+data.title+"&start="+bmkList[i][j].timemoment));
						
						
					}else{//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
						$('.columns-wrapper .column:nth-child('+(i+1)+') .timestamp-list').append(liTemplate1.replace("{timemoment}", ten+":"+one)
																											.replace("{bookmark}", bmkList[i][j].bookmark)
																											.replace("{timemoment}", bmkList[i][j].timemoment)
																											.replace("{bmkidx}", bmkList[i][j].bmkidx)
																											.replace("{url}", toolguideUrl+data.title+"&start="+bmkList[i][j].timemoment));
					}
				}  
				
			}     
			 
		 //북마크 클릭 시 특정시간으로 이동
	 	$(document).on("click", ".timestamp .timestamp-list li a", function(e){
	 		var url = $('iframe').attr('src');
	 		var num = url.split('start=')[1].split('&')[0];
	 		$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value'))) ;
	 	}); 
	 	
		 
	 	
	 	//페이지 로드 시 특정시간부터 영상 재생 
		var start = ${start};
		console.log("start time : " + start);
 		var url = $('iframe').attr('src');
		var num = url.split('start=')[1].split('&')[0];
		$('iframe').attr('src', url.replace(num, start));
 
</script>
</html> 