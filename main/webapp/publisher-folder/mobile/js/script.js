/**
	script.js for mobile
	
	퍼블리셔분께서 작성해주신 js 파일
	필요한 경우 기능을 추가 작성함
	여러 페이지에서 공통적으로 작용되는 기능인 경우 script.js에 코드를 작성함
	
	대체적으로 PC버전의 script.js와 유사함
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

var lteIe9 = navigator.userAgent.match(/msie\s[5-9]/i) !== null ? true : false;
/*
	메인 페이지의 영상관련 함수
	기존 youtube url을 iframe 형식으로 변환해주는 역할
	무한재생 기능 존재
 */
$(function () {
    $('a[aria-haspopup="true"]').on({
        click:function(e){
            e.preventDefault();
            var target = $(document.getElementById(e.currentTarget.getAttribute('aria-controls')));
            if(target.is('.common-layer')) {
                target.fadeIn(300, function(){
                    target.removeAttr('style').addClass('active');
                    
                    //사용자별 즐겨찾기 목록 호출
                    var favoriteList = callFavoriteList();
                    $('.layer-body .board-list').empty();

            if(favoriteList != null) {
                $('.layer-body .board-list').empty();
                favoriteList.forEach(function(el){
                    if(el.category == "도구 가이드"){
                        $('.layer-body .board-list').append(favTemplate.replace("{title}", el.title)
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
						$('.layer-body .board-list').append(favTemplate.replace(/{title}/gi, el.title)
                								 .replace(/{comments}/gi, el.comments)
                								 .replace(/{bookmark}/gi, el.bookmark)
                								 .replace(/{hiddenUrl}/gi,localStorage.priorKnow+ el.title + "&start=" + el.timemoment )
                								 .replace(/{url}/gi, localStorage.priorKnow.split('/DWSWS/')[1] +el.title+ "&start=" + el.timemoment+"&second=true"));
					}else{
						//베어독 part1과 나머지 보안 컨텐츠에 해당하는 기능인 경우
						//section 42~45는 베어독 part2과 관련된 기능이기 때문에 임의로 section을 기준으로 나눔
						//임의로 이렇게 한 것이기 때문에 나중에 베어독의 섹션이나 북마크를 수정하는 경우 영향이 있음
						$('.layer-body .board-list').append(favTemplate.replace(/{title}/gi, el.title)
                								 .replace(/{comments}/gi, el.comments)
                								 .replace(/{bookmark}/gi, el.bookmark)
                								 .replace(/{hiddenUrl}/gi,localStorage.priorKnow+ el.title + "&start=" + el.timemoment )
                								 .replace(/{url}/gi, localStorage.priorKnow.split('/DWSWS/')[1] +el.title+ "&start=" + el.timemoment));
					}
             		
                    }
                 });
            }
                    
                });
            }else {//header에서 맨 오른쪽 버튼을 클릭했을 경우 : 목차출력
                target.toggleClass('active');
				$.ajax({
			        type : "post",
			        url : "/DWSWS/ContentsList",
			        async : false,
			
			        success : function(data) {//출력할 목차 data 리턴
			            data = JSON.parse(data);
						HeaderList(data);//목차를 생성하는 함수
			        },
			        error : function(request, error) {
			            console.log(request, error);
			            alert("no info for contentsList");
		        }
    });
            }
            $('body').toggleClass('active-modal');
            $(this).attr('aria-expanded', function(index, attr){
                return attr == 'true' ? 'false' : 'true';
            });
        }
    });
    $('.modal').on({
        click:function(e){
            if(e.target.id == e.currentTarget.id || $(e.target).is('.close')){
                if($(e.currentTarget).is('.common-layer')) {
                    $(e.currentTarget).fadeOut(200, function(){
                        $(e.currentTarget).removeAttr('style').removeClass('active');
                    });
                }else {
                    $(this).removeClass('active');
                }

                $('body').removeClass('active-modal');
            }
        }
    });
    $('.accordion-menu .accordion-item').on({
        click:function(e){
            e.stopPropagation();
            $(e.delegateTarget).toggleClass('expanded');
            $(e.delegateTarget).find('> ul').slideToggle(500,function(){
                $(e.currentTarget).attr('aria-expanded', function(index, attr){
                    return attr == 'true' ? 'false' : 'true';
                });
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
                index = $(e.currentTarget).index(),
                targetCarousel = $('#videoPopup .video-carousel[data-task="'+target+'"]');
            $('body').toggleClass('active-modal');
            $('#videoPopup').fadeIn(300, function(){
                $(this).addClass('active');
                targetCarousel.addClass('active').siblings().removeClass('active');
                targetCarousel.slick('setPosition').slick('slickGoTo',index);
            });
            
        }
    }, 'button');
    $('.guide-video-play').on({
        click:function(e){
            var target = e.currentTarget.getAttribute('data-task'),
                index = $(e.currentTarget).index(),
                targetCarousel = $('#videoPopup .video-carousel[data-task="'+target+'"]');
            $('body').toggleClass('active-modal');
            $('#videoPopup').fadeIn(300, function(){
                $(this).addClass('active');
                targetCarousel.addClass('active').siblings().removeClass('active');
                targetCarousel.slick('setPosition').slick('slickGoTo',index);
            });
            
        }
    });
    $('#videoPopup').on({
        click:function(e){
            $(e.delegateTarget).removeClass('active').find('.video-carousel').removeClass('active');
            $(e.delegateTarget).find('.video').html('');
        }
    }, '.close');
});

 /*즐겨찾기 목록 template*/
var favTemplate = 
    "<li role=\"listitem\">"+
        "<a href=\"{url}\"><p>[{title}] {comments}</p></a>"+
        "<div class=\"btn-wrapper\">"+
            "<button type=\"button\" class=\"circle yellow\">"+
                "<span class=\"icon star white\">북마크</span>"+
            "</button>"+
            "<button type=\"button\" class=\"circle\">"+
                "<div class=\"icon link white\">페이지 이동</div>"+
            "</button>"+
            "<input type=\"hidden\" id=\"bookmark\" name=\"bookmark\" value=\"{bookmark}\" />"+
	        "<input type=\"hidden\" id=\"url\" value=\"{hiddenUrl}\" />"+
        "</div>"+
    "</li>";

/*사용자별 즐겨찾기 목록 DB에서 불러오는 함수*/    
function callFavoriteList() {
    var result = null;

    $.ajax({
        type : "post",
        url : "/DWSWS/favorite",
        async : false,

        success : function(data) {
            data = JSON.parse(data);
            result = data;
        },
        error : function(request, error) {
            console.log(request, error);
            alert("no info for favorite list");
        }
    });
    return result;
}

	//가이드 페이지 상단의 왼쪽 select 박스
	//'도구가이드' 선택 시 초기에는 zoom 페이지로 이동
	//'보안' 선택 시 초기에는 vpn 페이지로 이동
	//'디지털창' 선택 시 초기에는 bearworld 페이지로 이동
	$(document).on('change', '.sub-nav.category select', function(){
		if($(this).val() == "도구 가이드"){
				location.href = 'toolguide?tool=Zoom';
			
			}else if($(this).val() == "보안"){
				location.href = 'priorKnow?tool=VPN';	
			}else if($(this).val() == "스마트워크룰"){
				location.href = 'guide';
			}else if($(this).val() == "디지털창"){
				location.href = 'digital?title=BearWorld';
			}
		
	});
	
	//contents select box : category select box의 카테고리가 바뀌었을 때, 뒤쪽의 select-box 변경
	$(document).on('change', '.sub-nav.contents select', function(){
		if($('.sub-nav.category select').val() == "도구 가이드"){
			location.href = 'toolguide?tool='+$('.sub-nav.contents select').val();
		}else if($('.sub-nav.category select').val() == "보안"){
			location.href = 'priorKnow?tool='+$('.sub-nav.contents select').val();
		}
		
	});
	
	//category select box
	//가이드 페이지 상단의 왼쪽 select 박스를 생성하는 함수
	//@param categoryList :  도구가이드, 보안..
	function categorySelect(categoryList){
		var categoryHTML = "";
		categoryList.forEach(function(element){
		categoryHTML +=  "<option value='" + element + "'>" + element + "</option>";
		});	
		$('.sub-nav.category select').append(categoryHTML);
		$('.nav-wrapper .sub-nav select option[value="디지털창"]').css("display", 'none');
	}
	
	/*contents select box  */
	//가이드 페이지 상단의 오른쪽 select 박스를 생성하는 함수
	//@param contentsList :  특정 카테고리에 속하는 컨텐츠 목록 ex) 도구가이드인 경우 Lineworks, Zoom..
	function contentsSelect(contentsList){
		var contentsHTML = "";
		contentsList.forEach(function(element){
		contentsHTML +=  "<option value='" + element + "'>" + element + "</option>";
		});	
		$('.sub-nav.contents select').append(contentsHTML);
	}
	
	//즐겨찾기 추가 or 제거(가이드페이지)
	$(document).on("click", ".timestamp-list .btn-wrapper .icon.star, .guide-box .icon.star, .board-box .btn-wrapper .icon.star", function(e){ //.timestamp-list .btn-wrapper 
	if($(this).attr('class') == "icon star"){//star가 off인 상태였을경우 추가
	
	    $(e.target).addClass('on');
	
	
	$.ajax({//클릭한 북마크 번호 전송
	    type : "post",
	    url : "/DWSWS/insertFavorite",
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
	
	//공유 url(가이드페이지)
	$(document).on("click", '.icon.link', function() {
		$(this).parent().parent().find('#url').attr('type', 'display').select();
		document.execCommand("Copy");
		$(this).parent().parent().find('#url').attr('type', 'hidden');
		alert("URL이 성공적으로 복사되었습니다.");
		
	});
	
	//header에서 맨 오른쪽 버튼을 클릭했을 경우(목차)
	function HeaderList(data){
		$('.link-menu').empty();
		
		//도구가이드 목록 출력
		var toolGuideList = JSON.parse(data.toolGuideList);
		var toolLiTemplate = "<li><a href=\"toolguide?tool={tool}\">{tool}</a></li>";
		for (var i = 0; i < toolGuideList.length; i++) {
			$('.link-menu:eq(0)').append(toolLiTemplate.replace(/{tool}/gi, toolGuideList[i]));
		}
		//보안 목록 출력
		var priorKnowList = JSON.parse(data.priorKnowList);
		var priorKnowTemplate = "<li><a href=\"priorKnow?tool={tool}\">{priorKnow}</a></li>"
		for (var i = 0; i < priorKnowList.length; i++) {
			$('.link-menu:eq(1)').append(priorKnowTemplate.replace("{priorKnow}",priorKnowList[i]).replace("{tool}", priorKnowList[i]));
		}
		//디지털창 목록 출력
		var digitalList = JSON.parse(data.digitalList);
		//한글 -> 영어변환
		var digitalTitle = [];
		digitalList.forEach(function(element){
			if(element === "베아월드"){
				digitalTitle.push("BearWorld");
			}else if(element === "간편도구"){
				digitalTitle.push("SimpleTool");
			}else{
				digitalTitle.push(element);
			}
		});
		
		var digitalTemplate = "<li><a href=\"digital?title={tool}\">{digital}</a></li>"
		for (var i = 0; i < digitalList.length; i++) {
			$('.link-menu:eq(2)').append(
					digitalTemplate.replace("{digital}", digitalList[i]).replace("{tool}", digitalTitle[i]));
		}
	}
	
	//페이지 로드 시 특정시간부터 영상 재생 
	function videoPlayTime(start){
		var url = $('iframe').attr('src');
		var num = url.split('start=')[1].split('&')[0];
		$('iframe').attr('src', url.replace(num, start));
	}
	
	
	