<!--가이드 - 보안관련 jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
</head>

<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper has-side-menu">
		<div class="inner">
			<!-- LNB [s] -->
			<%@ include file="../include-pages/guide-lnb.jsp"%>
			<!-- LNB [e] -->
			<div class="start"></div>

		</div>
	</div>
	<!-- CONTENTS [e] -->

	<!-- FOOTER -->
<%-- 	<%@ include file="../include-pages/footer.jsp"%>
 --%>
 </body>

<script type="text/javascript">
	/*전체적인 틀  */
	 var priorKnowTemplate =
	"<div class=\"contents\">"+
	"<section class=\"guide-header\">"+
	    "<div class=\"inner\">"+
	        "<h2 class=\"title\">보안-{tool}</h2>"+
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
	//보안관련 컨텐츠의 기본정보 : 설명, url 등
	var data = ${guideInfo};
	
	
	$('.start').append(priorKnowTemplate.replace("{tool}", data.title).replace("{comments}", data.comments).replace("{url}",data.youtubeurl));
	
	var url = $('.video').attr('data-src'); //영상 Url 가져오기
	$('.video').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입 
	
	
 	  //클릭한 보안의 특정도구 active(guide-lnb)
	  for(var i=0; i<priorKnowList.length; i++){
		   if(data.title == $('.link-menu:eq(1) li:nth-child('+(i+1)+')').text().substring(1)){
			  $('aside .header a').attr('class','title'); //스마트워크 룰  active 지우기
			  $('.link-menu:eq(1) li:eq('+i+') a').attr('class', 'active'); // 클릭된 도구 active
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
		
		//priorKnow url
		var priorKnoweUrl = localStorage.priorKnow;
		
		
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
			
			if(bmkidx.indexOf(bmkList[i][j].bmkidx) != -1){//즐겨찾기 목록에 해당되는 bookmark인 경우
				$('.columns-wrapper .column:nth-child('+(i+1)+') .timestamp-list').append(liTemplate2.replace("{timemoment}", ten+":"+one)
																									.replace("{bookmark}", bmkList[i][j].bookmark)
																									.replace("{timemoment}", bmkList[i][j].timemoment)
																									.replace("{bmkidx}", bmkList[i][j].bmkidx)
																									.replace("{url}", priorKnoweUrl+data.title+"&start="+bmkList[i][j].timemoment));
				
				
			}else{//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
				$('.columns-wrapper .column:nth-child('+(i+1)+') .timestamp-list').append(liTemplate1.replace("{timemoment}", ten+":"+one)
																									.replace("{bookmark}", bmkList[i][j].bookmark)
																									.replace("{timemoment}", bmkList[i][j].timemoment)
																									.replace("{bmkidx}", bmkList[i][j].bmkidx)
																									.replace("{url}", priorKnoweUrl+data.title+"&start="+bmkList[i][j].timemoment));
			}
		}  
		
	}    
		
 		 //북마크 클릭 시 특정시간으로 이동
	 	$(document).on("click", ".timestamp .timestamp-list li a", function(e){
	 		
	 		var url = $('iframe').attr('src');
	 		var num = url.split('start=')[1].split('&')[0];
	 		//console.log(url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value')));
	 		$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value'))) ;
	 	});
		  
		  
	 	//페이지 로드 시 특정시간부터 영상 재생 
		var start = ${start};
		var url = $('iframe').attr('src');
		var num = url.split('start=')[1].split('&')[0];
		$('iframe').attr('src', url.replace(num, start));

	    
</script>
</html>