/**
 *admin-가이드관리탭의 contents-lnb 관련 js 
  contents_admin_lnb.js
 */
//컨텐츠 목록 템플릿
var contents4adminTemplate = 
	"<li>"+
        "<a style=\"cursor:pointer\"><span class=\"text-icon\" aria-hidden=\"true\">{firstChar}</span>{tool}</a>"+
    "</li>";

//삭제버튼 템플릿
var deleteTemplate = 
	"<button type=\"button\" class=\"icon delete\" id=\"guide-delete\">"+
	"<span class=\"sr-only\"></span></button>";


//contents list 호출 : lineworks, zoom, BearDoc..
function contents_lnb_call(){
	$.ajax({
        type : "post",
        url : "/DWSWS/callContentsList",
        async : false,

        success : function(data) {
        	//.link-menu.guide 영역 비우기 
        	//단, 첫번째 li는 제외(스마트워크rule)
        	var num = $('.link-menu.guide').children().length;
        	for(var i=1; i<=num; i++){
        		$('.link-menu.guide li:eq(1)').remove();
        	}
        	//link-menu.guide에 삽입	
           data.forEach(function(item){
				$('.link-menu.guide').append(contents4adminTemplate.replace("{firstChar}", item.charAt(0))
												  .replace("{tool}", item));
			});
        },
        error : function(request, error) {
            console.log(request, error);
        }
    });
}
//최초 페이지 로드 시 실행
contents_lnb_call();


	//contents-lnb의 특정 li를 클릭했을 때 해당 페이지로 이동
 	$(document).on('click', '.link-menu.guide li', function(){
 		if($(this).text().trim() == "스마트워크Rule" || $(this).attr('class') === "menu-field plus"){
 			//스마트워크Rule은 클릭 이벤트 제외 : 스마트워크룰은 /contents로 연결되도록 설정
			//새로 컨텐츠 추가하는 경우 제외
			return;
 		}
 		 $.ajax({
			type : "post",
			url : "/DWSWS/contentsTool",
			cache: false,
			data : $(this).text().trim().substring(1),//클릭한 컨텐츠명(Zoom, Lineworks..)
			contentType:"application/json;charset=UTF-8",
			success : function(json_map){
				//클릭한 컨텐츠 관련 데이터 리턴 : contentsInfo, 섹션리스트, 북마크리스트
				var map =  JSON.parse(json_map);
				//initialFuncion4Tool 함수는 tool-modify.js에 위치
				initialFuncion4Tool(map.contentsInfo, map.sectionList, map.bmkList);
			},
			error : function(){
				alert('데이터를 불러오는데 실패했습니다.');
			}
		});  
		
	});
	
//컨텐츠 추가 템플릿
var addBeforeContents =
	"<li class=\"menu-field plus\">"+
        "<label class=\"img-icon\">"+
        "</label>"+
        "<input type=\"text\" class=\"common\" placeholder=\"컨텐츠명\">"+
        "<button type=\"button\" class=\"icon check\" id=\"guide-addition\"><span class=\"sr-only\">확인</span></button>"+
    "</li>";
	
//체크 버튼 클릭 후 컨텐츠 추가 템플릿
var addAfterContents = 
	"<li class=\"menu-field plus\">"+
        "<span class=\"text-icon plus\" aria-hidden=\"true\">+</span>"+
        "<input type=\"text\" class=\"common\" placeholder=\"메뉴명\" value=\"{value} \" disabled>"+
    "</li>";
	
	

	//+버튼 클릭 시(컨텐츠 추가)
	$(document).on('click', '.square-btn.border.text-icon.plus', function(){
		$('aside .link-menu.guide').append(addBeforeContents);
	});
	
	//+버튼 클릭 후 체크버튼 클릭 시 : 컨텐츠 추가 확정
	$(document).on('click', '.link-menu.guide .icon.check', function(){
		//새로 입력한 컨텐츠명 
		var newContents = $(this).parent().find('input').val();
		
		//새로 입력한 컨텐츠명이 기존 컨텐츠명과 중복되는지 확인
		$('.link-menu.guide li').each(function(){
			if($(this).text().trim().substring(1) === newContents){
				alert("컨텐츠명이 중복됩니다.");
				return;
			}
		});
		//컨텐츠명을 입력하지 않은 경우
		if($(this).parent().find('input').val().trim() == ''){
			alert("공백을 채워주세요.");
			return;
		}
		
		//클릭된 li 제거
		$(this).parent().remove();
		
		//addAfterContents 템플릿 추가
		$('aside .link-menu.guide').append(addAfterContents.replace("{value}", newContents));
		
		//컨텐츠 정보를 입력받을 템플릿 append
		appendNewContents(newContents);
	});
	 
	//-버튼 클릭 시(컨텐츠 삭제)
	$(document).on('click', '.square-btn.border.text-icon.minus', function(){
		if($('.link-menu.guide').find('.icon.delete').length != 0){
			//이미 x버튼 있는경우.link-menu.guide의 li에 x 버튼 제거
			//단, 첫번째 li는 제외(스마트워크rule)
			$('.link-menu.guide .icon.delete').remove();
		}else {
			//.link-menu.guide의 li에 x 버튼 활성화
			//단, 첫번째 li는 제외(스마트워크rule)
			var num = $('.link-menu.guide').children().length;
	    	for(var i=1; i<=num; i++){
	    		$('.link-menu.guide li:eq('+i+') a').append(deleteTemplate);
	    	}
		}	
	});
	
	//-버튼 클릭 후 X버튼 클릭 시 (삭제확정)
	$(document).on('click', '.link-menu.guide .icon.delete', function(){
		var deleteConfirm = confirm("해당 컨텐츠를 삭제하시겠습니까?");
		if(deleteConfirm){
			$.ajax({
				type : "post",
				url : "/DWSWS/deleteContents",
				cache: false,
				data : $(this).parent().text().trim().substring(1),//삭제할 컨텐츠명
				contentType:"application/json;charset=UTF-8",
				success : function(json_map){
					alert("컨텐츠가 제거되었습니다.");
					window.location.reload();//컨텐츠 제거 이후 reload
				},
				error : function(){
					alert('데이터를 불러오는데 실패했습니다.');
				}
			}); 
		}else{
			alert("삭제가 취소되었습니다.");
		}
		 	
	});