/**
	script.js for PC
	
	퍼블리셔분께서 작성해주신 js 파일
	필요한 경우 기능을 추가 작성함
	여러 페이지에서 공통적으로 작용되는 기능인 경우 script.js에 코드를 작성함
 */
	/*가이드 - 도구가이드 url for 공유url*/
	localStorage.toolGuide = "http://smartwork.daewoong.co.kr/DWSWS/toolguide?tool=";
	
	/*가이드 - 보안 for 공유url*/
	localStorage.priorKnow = "http://smartwork.daewoong.co.kr/DWSWS/priorKnow?tool=";

/*
	가이드 페이지의 영상관련 함수
	기존 youtube url을 iframe 형식으로 변환해주는 역할
 */
var returnYoutubeSource = function(src, controls){
    var time = '0';
    controls = controls ? 0 : 1;
    if(src.indexOf('=') > 0) {
        time = src.split('=')[2];
        src = src.split('=')[1].replace('&t', '');
    }else if(src.indexOf('/embed/') > 0) {
        src = src.split('/embed/')[1];
    }
    return '<iframe src="https://www.youtube.com/embed/'+src+'?start='+time+'&autoplay=1&controls='+controls+'&rel=0&color=white" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen></iframe>';
}
/*
	메인 페이지의 영상관련 함수
	기존 youtube url을 iframe 형식으로 변환해주는 역할
	무한재생 기능 존재
 */
var returnYoutubeSourceForMain = function(src){
    var time = '0';
    if(src.indexOf('=') > 0) {
        time = src.split('=')[2];
        src = src.split('=')[1].replace('&t', '');
        // src = src.split('=')[1];
    }else if(src.indexOf('/embed/') > 0) {
        src = src.split('/embed/')[1];
    }
    return '<iframe src="https://www.youtube.com/embed/'+src+'?start='+time+'&autoplay=1&controls=0&rel=0&color=white&disablekb=1&loop=1&playlist='+src+'" frameborder="0" allow="accelerometer; autoplay; clipboard-write; encrypted-media; gyroscope; picture-in-picture" allowfullscreen style="position:absolute; width:100%; height:100%"></iframe>';
}

   /*즐겨찾기 목록 template*/
	var template = "<li role=\"listitem\">"+
	"<a href=\"{url}\" style=\"text-decoration:none; color:#646464;\">[{title}] {comments}</a>"+
	"<div class=\"btn-wrapper\">"+
	     "<button type=\"button\" class=\"icon star on\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
	     "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
	     "<input type=\"hidden\" id=\"bookmark\" name=\"bookmark\" value=\"{bookmark}\" />"+
	     "<input type=\"hidden\" id=\"url\" value=\"{hiddenUrl}\" />"+
	"</div>"+
	"</li>";

var lteIe9 = navigator.userAgent.match(/msie\s[5-9]/i) !== null ? true : false;

/**
	사용자별 즐겨찾기 목록을 호출하는 함수
 */
function callFavoriteList() {
    var result = null;

    $.ajax({
        type : "post",
        url : "/DWSWS/favorite",
        async : false,

        success : function(data) {//사용자별 즐겨찾기 목록 리턴
            data = JSON.parse(data);
            result = data;
        },
        error : function(request, error) {
            console.log(request, error);
        }
    });
    return result;
}
//헤더의 즐겨찾기 버튼(종모양)클릭 시 나오는 슬라이드 관련 함수
//즐겨찾기 버튼 클릭 시 사용자별 즐겨찾기 목록을 호출하고
// 북마크별로 hidden으로 연결 url을 저장해놓음
//특정 기능을 클릭 시 바로 해당 기능에 대해 설명하는 페이지로 이돟할 수 있고
// 공유버튼 클릭 시 url 복사 가능
$(function () {
    $('header .util-menu .notice').on({
        click:function(e){
            e.preventDefault();
			//사용자별 즐겨찾기 목록 호출
            var favoriteList = callFavoriteList();
            if(favoriteList != null) {
                $('.notice-list').empty();
				
                favoriteList.forEach(function(el){
                    if(el.category == "도구 가이드"){
                        $('.notice-list').append(template.replace("{title}", el.title)
                                                    .replace("{comments}", el.comments)
                                                    .replace("{bookmark}", el.bookmark)
                                                    .replace("{hiddenUrl}",localStorage.toolGuide+ el.title + "&start=" + el.timemoment )
                                                    .replace("{url}", localStorage.toolGuide.split('/DWSWS/')[1] +el.title+ "&start=" + el.timemoment));
                    }else if(el.category == "보안"){
	                	//보안을 즐겨찾기 목록에 추가하고자 하는 경우
	             		//베어독은 part1, part2로 영상이 나뉘어져있기 때문에 보안 카테고리는 if문을 한번 더 거침
						//section 42~45는 베어독 part2과 관련된 기능이기 때문에 임의로 section을 기준으로 나눔
						//임의로 이렇게 한 것이기 때문에 나중에 베어독의 섹션이나 북마크를 수정하는 경우 영향이 있음
						if(42<=el.section && el.section<45){
							//베어독 par2에 해당하는 기능인 경우
							//url에 second=true 추가
							$('.notice-list').append(template.replace(/{title}/gi, el.title)
	                								 .replace(/{comments}/gi, el.comments)
	                								 .replace(/{bookmark}/gi, el.bookmark)
	                								 .replace(/{hiddenUrl}/gi,localStorage.priorKnow+ el.title + "&start=" + el.timemoment )
	                								 .replace(/{url}/gi, localStorage.priorKnow.split('/DWSWS/')[1] +el.title+ "&start=" + el.timemoment+"&second=true"));
						}else{
							//베어독 part1과 나머지 보안 컨텐츠에 해당하는 기능인 경우
							//section 42~45는 베어독 part2과 관련된 기능이기 때문에 임의로 section을 기준으로 나눔
							//임의로 이렇게 한 것이기 때문에 나중에 베어독의 섹션이나 북마크를 수정하는 경우 영향이 있음
							$('.notice-list').append(template.replace(/{title}/gi, el.title)
	                								 .replace(/{comments}/gi, el.comments)
	                								 .replace(/{bookmark}/gi, el.bookmark)
	                								 .replace(/{hiddenUrl}/gi,localStorage.priorKnow+ el.title + "&start=" + el.timemoment )
	                								 .replace(/{url}/gi, localStorage.priorKnow.split('/DWSWS/')[1] +el.title+ "&start=" + el.timemoment));
						}
             		
                    }
                 });
            }
            
            $('#noticeLayer').toggleClass('active');
            $(this).attr('aria-expanded', function(attr){
                return attr == 'true' ? 'false' : 'true';
            });
        }
    });
    $('#noticeLayer').on({
        click:function(e){
            if(e.target.id == this.id){
                $(this).removeClass('active');
            }
        }
    });
    $('.accordion-menu .accordion-item').on({
        click:function(e){
            $(e.delegateTarget).toggleClass('expanded');
            $(e.delegateTarget).find('.link-menu').slideToggle();
            $(this).attr('aria-expanded', function(attr){
                return attr == 'true' ? 'false' : 'true';
            });
        }
    }, 'button');

    $('.youtube-area').on({
        click: function(e){
            e.preventDefault();
            var src = e.currentTarget.href;
            $(e.delegateTarget).append(returnYoutubeSource(src, 1));
        }
    }, '.play-link');

    $('.video-carousel').slick({
        dots:false,
        draggable:false
    }).on({
        afterChange:function(e, slick, currentSlide){
            slick.$slides.find('.video').html('');
            var player = slick.$slides.get(currentSlide).querySelector('.video');
            var src = player.getAttribute('data-src');
            $(player).html(returnYoutubeSource(src));
        }
    });
    $('.task-item').on({
        click:function(e){
            var target = e.delegateTarget.getAttribute('data-task'),
                index = $(e.currentTarget).index();
            $('#videoPopup').addClass('active')
                .find('.video-carousel[data-task="'+target+'"]')
                .addClass('active').siblings().removeClass('active');
            $('.layer-popup .video-carousel[data-task="'+target+'"]')
                .slick('setPosition')
                .slick('slickGoTo',index);
        }
    }, 'button');
    $('.guide-video-play').on({
        click:function(e){
            var target = e.currentTarget.getAttribute('data-task'),
                index = $(e.currentTarget).index();
            $('#videoPopup').addClass('active')
                .find('.video-carousel[data-task="'+target+'"]')
                .addClass('active').siblings().removeClass('active');
            $('.layer-popup .video-carousel[data-task="'+target+'"]')
                .slick('setPosition')
                .slick('slickGoTo',index);
        }
    });
    $('#videoPopup').on({
        click:function(e){
            $(e.delegateTarget).removeClass('active').find('.video-carousel').removeClass('active');
            $(e.delegateTarget).find('.video').html('');
        }
    }, '.close');
    $('#videoPopup').on({
        click:function(e){
            if(e.target.id == this.id){
                $(this).removeClass('active').find('.video-carousel').removeClass('active');
                $(e.delegateTarget).find('.video').html('');
            }
        }
    });
});

//Suggestion Guide on diagnose-result _ star 'on' attribute erasement code when favorite unregistered on .notice
$(document).on("click", "header .notice-layer-wrapper .panel .notice-list .btn-wrapper .icon.star.on", function(event) {
    console.log("event : ");
    console.log($(event.target).parent().find('#bookmark').attr("value"));
    $('.icon.star.on').each(function(index, starComponent) {
        console.log("jquery index : " + index);
        if($(starComponent).parent().parent().find('#bmk').attr("value") == $(event.target).parent().find('#bookmark').attr("value")) {
            console.log("icon star on erased");
            $(starComponent).parent().parent().find("input[value=" + $(event.target).parent().find('#bookmark').attr("value") + "]").parent().find('.icon.star').removeClass('on');
        }
    });
})

//즐겨찾기 추가 or 제거
//가이드 - 스마트워크 rule페이지에서 특정 기능을 클릭하여 팝업된 동영상에서 즐겨찾기 버튼(별표 모양) 클릭했을 때 작용
//가이드 -  도구가이드, 보안페이지의 특정 기능 옆의 즐겨찾기 버튼(별표 모양)을 클릭했을 때 작용
$(document).on("click", ".video-carousel .btn-wrapper .icon.star, .timestamp .timestamp-list li div .icon.star, .column.has-title .text-box .board-list .suggestion-list-items .icon.star, .slide-box .icon.star, .right-slide-box .btn-wrapper .icon.star", function(e){
if($(this).attr('class') == "icon star"){//star가 off인 상태였을경우 추가

    $(e.target).addClass('on');


	$.ajax({//클릭한 북마크 번호 전송
	    type : "post",
	    url : "/DWSWS/insertFavorite",
		async : false,
	    data : {
	        "num" :  $(this).parent().parent().find('#bmk').attr('value'),
	    },
	    success : function(){
	        favotiteNum(); // header의 즐겨찾기 갯수 update
	    },
	    error : function(){
	        alert("failed");
	    }
});
} else{//star가 on인 상태였을경우 삭제
    $(e.target).removeClass('on');
    $.ajax({//클릭한 북마크 번호 전송
        type : "post",
        url : "/DWSWS/deleteFavorite",
		async : false,
        data : {
            "num" :  $(this).parent().parent().find('#bmk').attr('value'),
        },
        success : function(){
            favotiteNum(); // header의 즐겨찾기 갯수 update
        },
        error : function(){
            alert("failed");
        }
    });
}
}); 


//공유 url 기능
//가이드 - 스마트워크 rule페이지에서 특정 기능을 클릭하여 팝업된 동영상에서 공유버튼을(화살표모양) 클릭했을 때 작용
//가이드 -  도구가이드, 보안페이지의 특정 기능 옆의 공유버튼(화살표모양)을 클릭했을 때 작용
$(document).on("click", ".timestamp .timestamp-list li div .icon.link, .video-carousel .btn-wrapper .icon.link, .column.has-title .text-box .board-list .suggestion-list-items .icon.link, .slide-guide .slide-box .btn-wrapper .icon.link, .right-slide-box .icon.link", function(e){
$(this).parent().parent().find('#url').attr('type', 'display').select();
document.execCommand("Copy");
$(this).parent().parent().find('#url').attr('type', 'hidden');
alert("successfully copied");
});
