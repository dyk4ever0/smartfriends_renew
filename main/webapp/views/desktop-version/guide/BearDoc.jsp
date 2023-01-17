<!--가이드 - BearDoc 관련 jsp  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
</head>
<style>
.contents .timestamp .inner{
    margin-top: 19px;
    border-radius: 16px;
    border: 1px solid #af8dce45;
}
.partLetter{
	color: #bb9dd6;
    padding-left: 18px;
    border-left: 5px solid #bb9dd6;
    font-size: 23px;
}
.start .contents .timestamp{
	padding-top: 60px;
}
</style>
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
<%-- 	<%@ include file="../include-pages/footer.jsp"%> --%>
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
		"<p class=\"partLetter\">Part 1</p>"+
	    "<div class=\"inner part1\">"+
	    	
	        "<div class=\"columns-wrapper\">"+
	        "</div>"+
	    "</div>"+
	    "<p class=\"partLetter\" style=\"margin-top:22px\">Part 2</p>"+
	    "<div class=\"inner part2\">"+
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
	
	//베어독관련 기본정보 : 설명, url 등
	var data = ${guideInfo};
	
	
	$('.start').append(priorKnowTemplate.replace("{tool}", data.title).replace("{comments}", data.comments).replace("{url}",data.youtubeurl));
	
	var url = $('.video').attr('data-src'); //BearDoc Url 가져오기
	$('.video').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입 
	
	
 	  //클릭한 보안의 특정도구 active(guide-lnb)
	  for(var i=0; i<priorKnowList.length; i++){
		   if(data.title.charAt(0)+data.title == $('.link-menu:eq(1) li:nth-child('+(i+1)+')').text()){
			  $('aside .header a').attr('class','title'); //스마트워크 룰  active 지우기
			  $('.link-menu:eq(1) li:eq('+i+') a').attr('class', 'active'); // 클릭된 도구 active
		  } 
		  
	  } 
	
		//section print
		var sectionData = ${sectionList};
		
 		for(var i=0; i<${num}; i++){
 			if(i<4){
 				$('.inner.part1 .columns-wrapper').append(columnTemplate.replace("{comments}", sectionData[i]));
 			}else{
 				$('.inner.part2 .columns-wrapper').append(columnTemplate.replace("{comments}", sectionData[i]));
 			}
			
		}
		 
		 
		//bookmark print
		var bmkList = ${bmkList};
		
		//bmkidx list in favorite list
		var bmkidx = ${bmkidx};
		
		//priorKnow url
		var priorKnoweUrl = localStorage.priorKnow;
		
		
 		  
		
 		 //북마크 클릭 시 특정시간으로 이동
	 	$(document).on("click", ".timestamp .timestamp-list li a", function(e){
	 		//part1과 관련된 기능을 클릭했을 경우
	 		if($(this).parent().parent().parent().parent().parent().attr('class') == 'inner part1'){
	 			//동영상 로드
	 			$('.video').html(returnYoutubeSource(data.youtubeurl)); 
	 			var url = $('iframe').attr('src');
		 		var num = url.split('start=')[1].split('&')[0];
		 		$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value')));
		 		
		 		//버튼 색 변경
		 		$('.guide-header .inner .part .partBtn').removeClass('on');
		 		$('.guide-header .inner .part .partBtn:eq(0)').addClass('on');
		 		
	 		}else{//part2와 관련된 기능을 클릭했을 경우
	 			//해당 파트 동영상 로드
				$('.video').html(returnYoutubeSource(secondUrl)); 
				var url = $('iframe').attr('src');
		 		var num = url.split('start=')[1].split('&')[0];
		 		$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value'))) ;
		 		
		 		//버튼 색 변경
		 		$('.guide-header .inner .part .partBtn').removeClass('on');
		 		$('.guide-header .inner .part .partBtn:eq(1)').addClass('on');
	 		}
	 		
	 	});
		  
		  
	 	//페이지 로드 시 특정시간부터 영상 재생 
		var start = ${start};
		var url = $('iframe').attr('src');
		var num = url.split('start=')[1].split('&')[0];
		$('iframe').attr('src', url.replace(num, start));
		
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
			        "tool" : $('h2.title').text().split("-")[1],
			    },
			    success : function(data){
					
					secondUrl=data;
					
					$('.guide-header .inner').append(buttonTemplate2.replace('{url2}', secondUrl));
					
					//두번째 영상을 재생시켜야하는 경우
					var second=${second};
					
					if(second == true){
					//두번째 동영상 로드
					 $('.video').html(returnYoutubeSource(secondUrl)); 
					 $('.guide-header .inner .part .partBtn').removeClass('on');
					$('.guide-header .inner .part .partBtn:eq(1)').addClass('on');
					
					//페이지 로드 시 특정시간부터 영상 재생 
					var start = ${start};
					
					var url = $('iframe').attr('src');
					var num = url.split('start=')[1].split('&')[0];
					$('iframe').attr('src', url.replace(num, start));
					
			}
			console.log("end of if phrase");
			    },
			    error : function(){
			        alert("failed");
			    }
			});
		
		
		//part1 or part2버튼 클릭했을 때
 		$(document).on("click", ".guide-header .inner .part .partBtn", function(e){
 			//button on/off
 			$('.guide-header .inner .part .partBtn').removeClass('on');
 			$(this).addClass('on');
 			//해당 파트 동영상 로드
			 $('.video').html(returnYoutubeSource($(this).attr('data-src'))); 
 			});
		
		//bookmark
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
 					//part2에 해당하는 기능이 존재해서 분기
 					//임의로 이렇게 한 것이기 때문에 나중에 베어독의 섹션이나 북마크를 수정하는 경우 영향이 있음
 					if((i+1)<5){
 						//part1에 해당하는 기능인 경우
						$('.inner.part1 .columns-wrapper .column:nth-child('+(i+1)+') .timestamp-list').append(liTemplate2.replace("{timemoment}", ten+":"+one)
						 																										.replace("{bookmark}", bmkList[i][j].bookmark)
						 																										.replace("{timemoment}", bmkList[i][j].timemoment)
						 																										.replace("{bmkidx}", bmkList[i][j].bmkidx)
						 																										.replace("{url}", priorKnoweUrl+data.title+"&start="+bmkList[i][j].timemoment));
 					}else{//part2에 해당하는 기능인 경우 -> second=true 추가
 						$('.inner.part2 .columns-wrapper .column:nth-child('+(i-3)+') .timestamp-list').append(liTemplate2.replace("{timemoment}", ten+":"+one)
																											.replace("{bookmark}", bmkList[i][j].bookmark)
																											.replace("{timemoment}", bmkList[i][j].timemoment)
																											.replace("{bmkidx}", bmkList[i][j].bmkidx)
																											.replace("{url}", priorKnoweUrl+data.title+"&start="+bmkList[i][j].timemoment+"&second=true"));
																		 					}
 					
 					
 				//part2에 해당하는 기능이 존재해서 분기
 				//임의로 이렇게 한 것이기 때문에 나중에 베어독의 섹션이나 북마크를 수정하는 경우 영향이 있음
 				}else{//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
 					if((i+1)<5){
 						//part1에 해당하는 기능인 경우
 						$('.inner.part1 .columns-wrapper .column:nth-child('+(i+1)+') .timestamp-list').append(liTemplate1.replace("{timemoment}", ten+":"+one)
									.replace("{bookmark}", bmkList[i][j].bookmark)
									.replace("{timemoment}", bmkList[i][j].timemoment)
									.replace("{bmkidx}", bmkList[i][j].bmkidx)
									.replace("{url}", priorKnoweUrl+data.title+"&start="+bmkList[i][j].timemoment));
 					}else{
 						//part2에 해당하는 기능인 경우 -> second=true 추가
 						$('.inner.part2 .columns-wrapper .column:nth-child('+(i-3)+') .timestamp-list').append(liTemplate1.replace("{timemoment}", ten+":"+one)
									.replace("{bookmark}", bmkList[i][j].bookmark)
									.replace("{timemoment}", bmkList[i][j].timemoment)
									.replace("{bmkidx}", bmkList[i][j].bmkidx)
									.replace("{url}", priorKnoweUrl+data.title+"&start="+bmkList[i][j].timemoment+"&second=true"));
 					}
 					
 				}
 			}  
 			
 		}  
	    
	    
</script>
</html>