/**
 * [mobile] 가이드 - 스마트워크룰 관련 js
 * rule.js
 */
//스마트워크룰 EX)업무시작알림 : 업무시작을 알리고 금일 주요 업무사항을 공유합니다.
var taskDivTemplate =
	"<li class=\"text-box task-item\" role=\"listitem\" data-task=\"task{num}\">" +
	"<h3 class=\"title\">{title}</h3>" +
	"<p>{comments}</p>" +
	"<div class=\"video-wrapper\">" +
	"</div>" +
	"</li>";

//task별 video-carousel 마련
var carouselTemplate =
	"<div class=\"video-carousel\" data-task=\"task{num}\">" +
	"</div>";

//각 룰에 해당하는 기능들 EX)회의개설, 회의초대..
var taskTemplate =
	"<button type=\"button\" class=\"square-btn has-icon white\">" +
	"<span class=\"icon {tool}\">{tool}</span>{bkcomments}" +
	"<span class=\"icon play\">video play</span>" +
	"</button>";

/*bookmark list star on */
var item1 =
	"<div class=\"item\">" +
	"<div class=\"header\">" +
	"<p class=\"eyebrow\">{title}</p>" +
	"<p class=\"title\">{bkcomments}</p>" +
	"</div>" +
	"<div class=\"body\">" +
	"<div class=\"video\" data-src=\"{youtubeurl}&t={timemoment}\"></div>" +
	"</div>" +
	"<div class=\"footer\">" +
	"<div class=\"btn-wrapper\">" +
	"<span class=\"btn\" style=\"width:45px; display:none;\">" +
	"<button type=\"button\" class=\"circle yellow\">" +
	"<span class=\"icon star white\">즐겨찾기</span>" +
	"</button> 즐겨찾기" +
	"</span>" +
	"<span class=\"btn\">" +
	"<button type=\"button\" class=\"circle url\">" +
	"<div class=\"icon link white\">페이지 이동</div>" +
	"</button> URL" +
	"<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
	"<input type=\"hidden\" id=\"url\" value=\"{url}\" />" +
	"</span>" +
	"</div>" +
	"</div>" +
	"</div>";

/*bookmark list star off */
var item2 =
	"<div class=\"item\">" +
	"<div class=\"header\">" +
	"<p class=\"eyebrow\">{title}</p>" +
	"<p class=\"title\">{bkcomments}</p>" +
	"</div>" +
	"<div class=\"body\">" +
	"<div class=\"video\" data-src=\"{youtubeurl}&t={timemoment}\"></div>" +
	"</div>" +
	"<div class=\"footer\">" +
	"<div class=\"btn-wrapper\">" +
	"<span class=\"btn\" style=\"width:45px; display:none;\">" +
	"<button type=\"button\" class=\"circle yellow\" style=\"background: #c2c2c2;\">" +
	"<span class=\"icon star white\">즐겨찾기</span>" +
	"</button> 즐겨찾기" +
	"</span>" +
	"<span class=\"btn\">" +
	"<button type=\"button\" class=\"circle url\">" +
	"<div class=\"icon link white\">페이지 이동</div>" +
	"</button> URL" +
	"<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
	"<input type=\"hidden\" id=\"url\" value=\"{url}\" />" +
	"</span>" +
	"</div>" +
	"</div>" +
	"</div>";

/**
 * 초기에 Rule 정보를 페이지에 출력하는 함수
 * 
 * @param data : 스마트워크Rule 관련 데이터 ex) 업무시작 알림 - ...
 * @param bmkidx : 사용자별 즐겨찾기에 포함된 북마크 idx 
 *                 북마클 출력 시 즐겨찾기 포함 여부에 따라 색칠된 별표/빈 별표를 출력할 것인지 달라지기 때문
 *                 모바일버전에서는 로그인없이도 가이드를 확인할 수 있도록 즐겨찾기 기능(별표)를 프론트단에서 hidden처리해놓음
 * @return
 */
function makeRule(data, bmkidx) {
	for (var i = 0; i < data.length; i++) {

		//스마트워크룰 EX)업무시작알림 : 업무시작을 알리고 금일 주요 업무사항을 공유합니다.
		$('.workflow-list.box-wrapper').append(taskDivTemplate.replace("{num}", i + 1).replace("{title}", data[i][0].title).replace("{comments}", data[i][0].comments));
		//task별 video-carousel 마련 
		$('.layer-body').append(carouselTemplate.replace("{num}", i + 1));

		for (var j = 0; j < data[i].length; j++) {

			//각 룰에 해당하는 기능들 EX)회의개설, 회의초대..
			$('.workflow.transparent .workflow-list.box-wrapper .text-box:nth-child(' + (i + 1) + ') .video-wrapper').append(taskTemplate.replace(/{tool}/gi, data[i][j].tool).replace("{bkcomments}", data[i][j].bkcomments));

			//task별 video매칭
			if (bmkidx.indexOf(data[i][j].bidx) != -1) {//즐겨찾기 목록에 해당되는 bookmark인 경우
				$('#videoPopup .layer-body .video-carousel:nth-child(' + (i + 2) + ')').append(item1.replace("{title}", data[i][j].title)
					.replace("{bkcomments}", data[i][j].bkcomments)
					.replace("{youtubeurl}", data[i][j].youtubeurl)
					.replace("{timemoment}", data[i][j].timemoment)
					.replace("{url}", localStorage.toolGuide + data[i][j].tool + "&start=" + data[i][j].timemoment)
					.replace("{bmkidx}", data[i][j].bidx));
			} else {//즐겨찾기 목록에 해당하지 않는 bookmark인 경우
				$('#videoPopup .layer-body .video-carousel:nth-child(' + (i + 2) + ')').append(item2.replace("{title}", data[i][j].title)
					.replace("{bkcomments}", data[i][j].bkcomments)
					.replace("{youtubeurl}", data[i][j].youtubeurl)
					.replace("{timemoment}", data[i][j].timemoment)
					.replace("{url}", localStorage.toolGuide + data[i][j].tool + "&start=" + data[i][j].timemoment)
					.replace("{bmkidx}", data[i][j].bidx));

			}

		}
	}
}


//스마트워크 Rule 동영상 팝업에서 즐겨찾기 추가 or 제거
$(document).on("click", '#videoPopup .layer-body .video-carousel .slick-list .slick-track .footer .circle.yellow', function(e) {
	if ($(this).attr('style') != undefined) {//star가 off인 상태였을경우 추가

		$(this).removeAttr('style');

		$.ajax({//클릭한 북마크 번호 전송
			type: "post",
			url: "/DWSWS/insertFavorite",
			data: {
				"num": $(this).parent().parent().find('#bmk').attr('value'),
			},
			success: function() {
				favotiteNum(); // header의 즐겨찾기 갯수 update
			},
			error: function() {
				alert("failed");
			}
		});
	} else {//star가 on인 상태였을경우 삭제

		$(this).attr('style', 'background:#c2c2c2');

		$.ajax({//클릭한 북마크 번호 전송
			type: "post",
			url: "/DWSWS/deleteFavorite",
			data: {
				"num": $(this).parent().parent().find('#bmk').attr('value'),
			},
			success: function() {
				favotiteNum(); // header의 즐겨찾기 갯수 update
			},
			error: function() {
				alert("failed");
			}
		});
	}
}); 
	
	//공유 url(스마트워크 룰 팝업)
   //화살표 모양의 공유버튼 클릭 시 url을 공유하는 기능 
	$(document).on("click", '#videoPopup .layer-body .video-carousel .slick-list .slick-track .footer .circle.url', function(e) {
		$(this).parent().find('#url').attr('type', 'display').select();
		document.execCommand("Copy");
		$(this).parent().parent().find('#url').attr('type', 'hidden');
		alert("URL이 성공적으로 복사되었습니다.");
		
	});

	
	
	
	
	
	
	
	
	
	
	
	