<!--가이드-메인페이지(스마트워크Rule)  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ include file="../include-pages/common.jsp"%>
<title>대웅 SmartWork Friends</title>
<style>
.thumbnail{
    display: table-cell;
    background: transparent;
    border: none;
}
</style>
</head>

<body>
	<!--header  -->
	<%@ include file="../include-pages/header.jsp"%>

	<!-- CONTENTS [s] -->
	<div class="contents-wrapper has-side-menu bg-blue">
		<div class="inner">
			<!-- LNB [s] -->
			<%@ include file="../include-pages/guide-lnb.jsp"%>
			<!-- LNB [e] -->

			<div class="contents">
				<section>
					<div class="inner">
						<h2 class="section-title">스마트워크 Rule</h2>
					</div>
				</section>
				<section class="description">
					<div class="inner">
						<div class="columns-wrapper" style="display: table;">
							<div class="column" style="display: table-cell;">
								<div class="text-box" style="padding-right: 80px;">
									<h3 class="title">${oneDesc}</h3><!--한줄설명  -->
									<p>${ruleDesc}</p><!--상세설명  -->
								</div>
							</div>
							<div class="column" style="display: contents;">
								<button type="button"
									class="thumbnail guide-video-play" data-task="full">
									<img src="images/rule-thumbnail.svg"></img>
								</button>
							</div>
						</div>
					</div>
				</section>
				<section class="flow-chart-wrapper">
					<div class="inner">
						<div class="start-line">
							<span class="rounded-square">Start</span>
						</div>
						<div class="flow-chart">
							<!--스마트워크 rule과 그에 해당하는 기능 출력  -->
						</div>
					</div>
				</section>
			</div>

			<!-- Video Player layer popup [s] -->
			<div class="layer-popup video-player" id="videoPopup"
				aria-modal="true" role="document">
				<div class="popup-inner">
					<!-- video carousel은 task별로 묶여 있습니다. -->
					<div class="video-carousel" data-task="full">
						<div class="item">
							<div class="header">
								<p class="title">${videoTitle}</p><!--영상제목  -->
							</div>
							<div class="body">
								<div class="video" data-src="${fullUrl}"></div><!--스마트워크Rule 동영상 URL  -->
							</div>
							<!--동영상 팝업 시 나타나는 즐겨찾기기능과 공유url기능 출력  -->
						</div>
					</div>
				</div>
				<button type="button" class="close">닫기</button>
			</div>
			<!-- Video Player layer popup [e] -->

		</div>
		<!-- CONTENTS [e] -->
	</div>
	<!-- FOOTER -->
	<%@ include file="../include-pages/footer.jsp"%>
</body>
<script>
   //스마트워크룰 EX)업무시작알림 : 업무시작을 알리고 금일 주요 업무사항을 공유합니다.
 	var taskDivTemplate = "<div class=\"task-item columns-wrapper\" data-task=\"task{num}\">"+
								"<!-- description -->"+
								"<div class=\"column\">"+
								    "<div class=\"text-box\">"+
								        "<h3 class=\"title\">{title}</h3>"+
								        "<p>{comments}</p>"+
								    "</div>"+
								"</div>"+
								"<!-- video list -->"+
								"<div class=\"column\">"+
								"</div>"
						  "</div>";
	//각 룰에 해당하는 기능들 EX)회의개설, 회의초대..				
	var taskTemplate = "<button type=\"button\" class=\"square-btn has-icon white\" style=\"width:52%;\"><span class=\"icon {tool}\">{tool}</span>{bkcomments}<span class=\"icon play\">video play</span></button>";
	
	//task별 video-carousel 마련 				
	var carouselTemplate = "<div class=\"video-carousel\" data-task=\"task{num}\"> </div>";
 
	// 북마크를 출력할 때 사용자의 즐겨찾기에 이미 포함된 경우, 즐겨찾기의 별표가 색칠되어서 출력되어야하기 때문에 사용자별 즐겨찾기 목록 필요
	// 사용자의 즐겨찾기 idx 리스트와 북마크 idx를 비교하는 방식
	// 즐겨찾기 별표가 off 인 템플릿
	/*bookmark list star off */
    var item1 = "<div class=\"item\">"+
					    "<div class=\"header\">"+
					    "<p class=\"eyebrow\"><em>{title}</em></p>"+
					    "<p class=\"title\">{bkcomments}</p>"+
					"</div>"+
					"<div class=\"body\">"+
					    "<div class=\"video\" data-src=\"{youtubeurl}&t={timemoment}\"></div>"+
					"</div>"+
					"<div class=\"footer\">"+
						"<div class=\"time\">{time}</div>"+
					    "<div class=\"btn-wrapper\">"+
					        "<button type=\"button\" class=\"icon star\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
					        "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
					    "</div>"+
					    "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"+
					    "<input type=\"hidden\" id=\"url\" value=\"{url}\" />"+
					"</div>"+
				   "</div>"; 
				   
	// 북마크를 출력할 때 사용자의 즐겨찾기에 이미 포함된 경우, 즐겨찾기의 별표가 색칠되어서 출력되어야하기 때문에 사용자별 즐겨찾기 목록 필요
	// 사용자의 즐겨찾기 idx 리스트와 북마크 idx를 비교하는 방식
	// 즐겨찾기 별표가 on 인 템플릿
   /*bookmark list star on */
     var item2 = "<div class=\"item\">"+
					    "<div class=\"header\">"+
					    "<p class=\"eyebrow\"><em>{title}</em></p>"+
					    "<p class=\"title\">{bkcomments}</p>"+
					"</div>"+
					"<div class=\"body\">"+
					    "<div class=\"video\" data-src=\"{youtubeurl}&t={timemoment}\"></div>"+
					"</div>"+
					"<div class=\"footer\">"+
						"<div class=\"time\">{time}</div>"+
					    "<div class=\"btn-wrapper\">"+
					        "<button type=\"button\" class=\"icon star on\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
					        "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
					    "</div>"+
					    "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"+
					    "<input type=\"hidden\" id=\"url\" value=\"{url}\" />"+
					"</div>"+
				   "</div>"; 
				   
	//스마트워크룰 관련 데이터
	 var data = ${ruleList};

 	//bmkidx list in favorite list
	var bmkidx = ${bmkidx};
	
	
	for(var i=0; i<data.length; i++){
		
		//스마트워크룰 EX)업무시작알림 : 업무시작을 알리고 금일 주요 업무사항을 공유합니다.
		$('.flow-chart').append(taskDivTemplate.replace("{num}", i+1).replace("{title}", data[i][0].title).replace("{comments}", data[i][0].comments));
		//task별 video-carousel 마련 
		$('.popup-inner').append(carouselTemplate.replace("{num}", i+1));

		for(var j=0; j<data[i].length; j++){
			//각 룰에 해당하는 기능들 EX)회의개설, 회의초대..
			$('.flow-chart-wrapper  .flow-chart  .task-item:nth-child('+(i+1)+') .column:nth-child(2)').append(taskTemplate.replace(/{tool}/gi, data[i][j].tool).replace("{bkcomments}", data[i][j].bkcomments));
			
			//task별 video매칭
			
			//시간 00:00 형태로 바꿔주기
			var time = parseInt(data[i][j].timemoment);
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
			
			if(bmkidx.indexOf(data[i][j].bidx) != -1 ){//즐겨찾기 목록에 해당되는 bookmark인 경우
				$('.video-carousel:nth-child('+(i+2)+')').append(item2.replace("{title}", data[i][j].title)
																		.replace("{bkcomments}", data[i][j].bkcomments)
																		.replace("{youtubeurl}", data[i][j].youtubeurl)
																		.replace("{timemoment}", data[i][j].timemoment)
																		.replace("{time}", ten+":"+one)
																		.replace("{url}", localStorage.toolGuide + data[i][j].tool + "&start=" + data[i][j].timemoment)
																		.replace("{bmkidx}", data[i][j].bidx));
			}else{//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
					$('.video-carousel:nth-child('+(i+2)+')').append(item1.replace("{title}", data[i][j].title)
																.replace("{bkcomments}", data[i][j].bkcomments)
																.replace("{youtubeurl}", data[i][j].youtubeurl)
																.replace("{timemoment}", data[i][j].timemoment)
																.replace("{time}", ten+":"+one)
																.replace("{url}", localStorage.toolGuide + data[i][j].tool + "&start=" + data[i][j].timemoment)
																.replace("{bmkidx}", data[i][j].bidx)); 
				
			}
			
		}
	} 
	
</script>
</html>