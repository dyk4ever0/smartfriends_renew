/**
 * [mobile] 가이드 - BearDoc.js(보안)
	베어독은 영상이 두개여서 따로 js 파일 생성
 */

/*전체적인 틀  */
var toolGuideTemplate =
	"<h2 class=\"section-title\">{tool}</h2>" +
	"<div class=\"text-box\">" +
	"<p>{comments}</p>" +
	"<div class=\"video expanded\">" +
	"<div class=\"video\" data-src=\"{url}\"> </div>" +
	"</div>" +
	"<p class=\"partLetter\">Part 1</p>"+
	"<ul class=\"box-wrapper part1\" role=\"list\">" +
	"</ul>" +
	"<p class=\"partLetter\">Part 2</p>"+
	"<ul class=\"box-wrapper part2\" role=\"list\">" +
	"</ul>" +
	"</div>";


/*전체적인 틀 삽입*/
/**
   초기에 BearDoc 페이지를 위한 전체적인 틀을 페이지에 출력하는 함수
	전체적인 틀 삽입 : toolGuideTemplate
 */
function makeToolGuide(data) {
	$('.document .inner').append(toolGuideTemplate.replace("{tool}", data.title).replace("{comments}", data.comments).replace("{url}", data.youtubeurl));

	var url = $('.video .video').attr('data-src'); //zoomUrl 가져오기
	$('.video').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입 


}
/*section list  */
var columnTemplate =
	"<li class=\"text-box\" role=\"listitem\">" +
	"<h3 class=\"title\">{comments}</h3>" +
	"<ul class=\"timestamp-list\">"  +
	"</ul>" 
	"</li>";
/**
 * 섹션 데이터 출력
 * 
 * @param sectionData 특정 도구(BeaDoc)의 섹션리스트
 * @param SectionNum 특정 도구(BeaDoc)의 섹션 갯수
 * @return
 */
function makeSection(sectionData, SectionNum) {
	for (var  i= 0; i<  SectionNum; i++) {
		if(i<4){
			$('.box-wrapper:eq(0)').append(columnTemplate.replace("{comments}", sectionData[i]));
		}else{
			$('.box-wrapper:eq(1)').append(columnTemplate.replace("{comments}", sectionData[i]));
		}
		
	}
}

/*bookmark list star off */
var liTemplate1 = 
	"<li>"+
		"<a href=\"#\"><span class=\"time\">{timemoment}</span>{bookmark}</a>"+
		"<div class=\"btn-wrapper\">"+
			"<button type=\"button\" class=\"icon star\" style=\"display:none;\"  aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
			"<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
		"</div>"+
		"<input type=\"hidden\" id=\"time\" name=\"timemoment\" value=\"{timemoment}\" />"+
		"<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"+
		"<input type=\"hidden\" id=\"url\" value=\"{url}\" />"+
	"</li>";
	
/*bookmark list star on */
var liTemplate2 = 
	"<li>"+
		"<a href=\"#\"><span class=\"time\">{timemoment}</span>{bookmark}</a>"+
		"<div class=\"btn-wrapper\">"+
			"<button type=\"button\" class=\"icon star on\" style=\"display:none;\"  aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
			"<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
		"</div>"+
		"<input type=\"hidden\" id=\"time\" name=\"timemoment\" value=\"{timemoment}\" />"+
		"<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />"+
		"<input type=\"hidden\" id=\"url\" value=\"{url}\" />"+
	"</li>";


/**
 * 특정 도구(BeaDoc)와 관련된 북마크 정보 출력
 * 
 * @param bmkList 특정 도구의 북마크 리스트
 * @param SectionNum : 특정 도구의 섹션 갯수
 * @param bmkidx : 사용자의 즐겨찾기에 포함된 북마크 index
 * @returns
 */
function makeBookmark(bmkList, SectionNum, bmkidx) {
	//priorKnow url
	var priorKnow = localStorage.priorKnow;
	
	for (var i = 0; i<SectionNum; i++){

		for (var j = 0; j<bmkList[i].length; j++){

			//시간 00:00 형태로 바꿔주기
			var time = parseInt(bmkList[i][j].timemoment);
			var ten = "00";
			var one = "00";

			if ( (time /60) < 1) {
				if ((time%60) < 10){  
					one = "0" + time.toString();
				}else if ((time%60) >= 10) {
					one = time.toString();
				}

			} else if ((time/60) >= 10) {
				ten = parseInt((time / 60)).toString();
				if ((time % 60) < 10) {
					one = "0" + parseInt(time % 60).toString();
				} else if ((time%60) >= 10) {
					one = (time%60).toString();
				}
			} else {
				ten = "0"+ parseInt((time / 60)).toString();
				if ((time % 60) <  10){  
					one = "0" +(time % 60).toString();
				}  else if((time % 60) >= 10) {
					one = (time% 60).toString();
				}
			}

			if  (bmkidx.indexOf(bmkList[i][j].bmkidx) != -1) {//즐겨찾기 목록에 해당되는 bookmark인 경우
				if((i+1)<5){
					$('.box-wrapper li .timestamp-list:eq('+i+')').append(liTemplate2.replace("{timemoment}", ten +  ":" + one)
																				.replace("{bookmark}", bmkList[i][j].bookmark)
																				.replace("{timemoment}", bmkList[i][j].timemoment)
																				.replace("{bmkidx}", bmkList[i][j].bmkidx)
																				.replace("{url}", priorKnow   + data.title + "&start=" + bmkList[i][j].timemoment));
				}else{
					$('.box-wrapper li .timestamp-list:eq('+i+')').append(liTemplate2.replace("{timemoment}", ten +  ":" + one)
																				.replace("{bookmark}", bmkList[i][j].bookmark)
																				.replace("{timemoment}", bmkList[i][j].timemoment)
																				.replace("{bmkidx}", bmkList[i][j].bmkidx)
																				.replace("{url}", priorKnow   + data.title + "&start=" + bmkList[i][j].timemoment+"&second=true"));
				}
				

			}else {//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
				if((i+1)<5){
					$('.box-wrapper li .timestamp-list:eq('+i+')').append(liTemplate1.replace("{timemoment}", ten + ":" + one)
																				.replace("{bookmark}", bmkList[i][j].bookmark)
																				.replace("{timemoment}", bmkList[i][j].timemoment)
																				.replace("{bmkidx}", bmkList[i][j].bmkidx)
																				.replace("{url}", priorKnow + data.title + "&start=" + bmkList[i][j].timemoment));
				}else{
					$('.box-wrapper li .timestamp-list:eq('+i+')').append(liTemplate1.replace("{timemoment}", ten + ":" + one)
																				.replace("{bookmark}", bmkList[i][j].bookmark)
																				.replace("{timemoment}", bmkList[i][j].timemoment)
																				.replace("{bmkidx}", bmkList[i][j].bmkidx)
																				.replace("{url}", priorKnow + data.title + "&start=" + bmkList[i][j].timemoment+"&second=true"));
				}
				
			}
		}

	}
}
	 //북마크 클릭 시 영상에서 해당 기능을 설명하는 부분으로 이동
 	$(document).on("click", ".timestamp-list li a", function(e){
		//part1과 관련된 기능을 클릭했을 경우
		if($(this).parent().parent().parent().parent().attr('class') == 'box-wrapper part1'){
			//해당 파트 동영상 로드
			$('.video').html(returnYoutubeSource(data.youtubeurl)); 
			var url = $('iframe').attr('src');
 			var num = url.split('start=')[1].split('&')[0];
			$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value'))) ;
		
			//part1 버튼 색 변경
			$('.text-box .part .partBtn').removeClass('on');
			$('.text-box .part .partBtn:eq(0)').addClass('on');
		}else{
			//해당 파트 동영상 로드
			//part2과 관련된 기능을 클릭했을 경우
			$('.video').html(returnYoutubeSource(secondUrl)); 
			var url = $('iframe').attr('src');
 			var num = url.split('start=')[1].split('&')[0];
			$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value'))) ;
			
			//part2 버튼 색 변경
			$('.text-box .part .partBtn').removeClass('on');
			$('.text-box .part .partBtn:eq(1)').addClass('on');
		}
 		
 		
 	}); 

	//part1 or part2버튼 클릭했을 때
	$(document).on("click", ".text-box .part .partBtn", function(e){
		//button on/off
		$('.text-box .part .partBtn').removeClass('on');
		$(this).addClass('on');
		//해당 파트 동영상 로드
		 $('.video').html(returnYoutubeSource($(this).attr('data-src'))); 
		});

	
	