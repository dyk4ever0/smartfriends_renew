/**
 * [mobile] 가이드 - 보안 관련 js
 */

/*전체적인 틀  */
var toolGuideTemplate =
	"<h2 class=\"section-title\">{tool}</h2>" +
	"<div class=\"text-box\">" +
	"<p>{comments}</p>" +
	"<div class=\"video expanded\">" +
	"<div class=\"video\" data-src=\"{url}\"> </div>" +
	"</div>" +
	"<ul class=\"box-wrapper\" role=\"list\">" +
	"</ul>" +
	"</div>";


/**
 *  초기에 도구가이드 정보를 페이지에 출력하는 함수
 *  전체적인 틀 삽입
 * 
 * @param data : 컨트롤러에서 model객체에 toolGuideTemplate라는 이름으로 보낸 데이터
 * 				보안 컨텐츠 관련 기본정보 : 이름, 설명, url등
 * @returns
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
 * @param sectionData 특정 도구(DLP..)의 섹션리스트
 * @param SectionNum 특정 도구(DLP..)의 섹션 갯수
 * @return
 */
function makeSection(sectionData, SectionNum) {
	for (var  i= 0; i<  SectionNum; i++) {
		$('.box-wrapper').append(columnTemplate.replace("{comments}", sectionData[i]));
	}
}

/*bookmark list star off */
var liTemplate1 = 
	"<li>"+
		"<a href=\"#\"><span class=\"time\">{timemoment}</span>{bookmark}</a>"+
		"<div class=\"btn-wrapper\">"+
			"<button type=\"button\" class=\"icon star\"  style=\"display:none;\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
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
 * 특정 도구(Lineworks..)와 관련된 북마크 정보 출력
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
				$('.box-wrapper li .timestamp-list:eq('+i+')').append(liTemplate2.replace("{timemoment}", ten +  ":" + one)
																				.replace("{bookmark}", bmkList[i][j].bookmark)
																				.replace("{timemoment}", bmkList[i][j].timemoment)
																				.replace("{bmkidx}", bmkList[i][j].bmkidx)
																				.replace("{url}", priorKnow   + data.title + "&start=" + bmkList[i][j].timemoment));


			}else {//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
				$('.box-wrapper li .timestamp-list:eq('+i+')').append(liTemplate1.replace("{timemoment}", ten + ":" + one)
																				.replace("{bookmark}", bmkList[i][j].bookmark)
																				.replace("{timemoment}", bmkList[i][j].timemoment)
																				.replace("{bmkidx}", bmkList[i][j].bmkidx)
																				.replace("{url}", priorKnow + data.title + "&start=" + bmkList[i][j].timemoment));
			}
		}

	}
}
	 //북마크 클릭 시 영상에서 해당 기능을 설명하는 부분으로 이동
 	$(document).on("click", ".timestamp-list li a", function(e){
 		var url = $('iframe').attr('src');
 		var num = url.split('start=')[1].split('&')[0];
 		$('iframe').attr('src', url.replace("start="+num, "start="+$(this).parent().find('#time').attr('value'))) ;
 	}); 

	

