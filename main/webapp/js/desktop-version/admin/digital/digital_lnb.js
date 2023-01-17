/**
 * digital_lnb.js
	디지털창 클릭 시 나타나는 lnb관련 js
 */
	//클릭했을 때 해당 페이지로 이동
	//베아월드, 간편도구..
 	$(document).on('click', '.link-menu.digital li', function(){
	
		if($(this).attr('class') === "menu-field plus digital"){
			//새로 컨텐츠 추가하는 경우 제외
			return;
 		}
	
		//contents_admin_lnb에서 클릭한 부분 active
		$('.link-menu li a').removeClass('active');
		$(this).find('a').attr('class', 'active');
			
 		 $.ajax({
			type : "post",
			url : "/DWSWS/digitalContents",
			cache: false,
			data : $(this).text().trim().substring(1),//클릭한 컨텐츠명 ex)베아월드, 간편도구
			contentType:"application/json;charset=UTF-8",
			success : function(json_map){
				//특정 컨텐츠에 대한 이미지리스트
				var imgData =  JSON.parse(json_map);
				//컨텐츠명 ex)베아월드, 간편도구
				var tool = $('.link-menu.digital li .active').text().substring(1) ;
				//기존 데이터 호출
				appendData4Digital(tool,imgData );
			},
			error : function(){
				alert('데이터를 불러오는데 실패했습니다.');
			}
		});  
		
	});
	
//체크 버튼 클릭 후 컨텐츠 추가 템플릿
var addAfterContents = 
	"<li class=\"menu-field plus\">"+
        "<span class=\"text-icon plus\" aria-hidden=\"true\">+</span>"+
        "<input type=\"text\" class=\"common\" placeholder=\"메뉴명\" value=\"{value} \" disabled>"+
    "</li>";
	
	//+버튼 클릭 시(컨텐츠 추가)
	$(document).on('click', '.square-btn.border.text-icon.plus', function(){
		$('aside .link-menu.digital').append(addBeforeContents4Digital);
	});
	
	//-버튼 클릭 시(컨텐츠 삭제)
	$(document).on('click', '.square-btn.border.text-icon.minus', function(){
		if($('.link-menu.digital').find('.icon.delete').length != 0){
			//이미 x버튼 있는경우.link-menu.digital의 li에 x 버튼 제거
			$('.link-menu.digital .icon.delete').remove();
		}else {
			//.link-menu.guide의 li에 x 버튼 활성화
	    	$('.link-menu.digital li a').append(deleteTemplate);
	    
		}	
	});
	
//컨텐츠 추가 템플릿
var addBeforeContents4Digital =
	"<li class=\"menu-field plus digital\">"+
        "<label class=\"img-icon\">"+
        "</label>"+
        "<input type=\"text\" class=\"common\" placeholder=\"컨텐츠명\">"+
        "<button type=\"button\" class=\"icon check\"><span class=\"sr-only\">확인</span></button>"+
    "</li>";
	
	//+버튼 클릭 후 체크버튼 클릭 시 : 컨텐츠 추가 확정
	$(document).on('click', '.link-menu.digital .icon.check', function(){
		//새로 입력한 컨텐츠명 
		var newContents = $(this).parent().find('input').val();
		
		//새로 입력한 컨텐츠명이 기존 컨텐츠명과 중복되는지 확인
		$('.link-menu.digital li').each(function(){
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
		$('aside .link-menu.digital').append(addAfterContents.replace("{value}", newContents));
		
		//컨텐츠 정보를 입력받을 템플릿 append
		appendNewContents4digital(newContents);
	});
	
//취소, 수정완료, 컨텐츠추가 템플릿
var submitTemplate4DigitalAdd = 
    "<fieldset class=\"btn-wrapper\">"+
	    "<button type=\"button\" id=\"cancel4Digital\" class=\"square-btn gray\">취소</button>"+
	    "<button type=\"button\" id=\"submit4Digital\" class=\"square-btn\">수정완료</button>"+
	    "<button type=\"button\" class=\"square-btn border text-icon large addContents\">+<span class=\"sr-only\">추가</span></button>"+
	"</fieldset>";

//+버튼 누른 후 체크버튼 눌렀을 때 추가되는 템플릿	
function appendNewContents4digital(newContentsName){
	//contents 영역 제거 후 삽입
	$('.contents').empty().append(titleTemplate.replace("{tool}", newContentsName)).append(formTemplate4Digital);
	//컨텐츠 템플릿 삽입
	$('.form .inner').append(newContents);
	//규칙삭제, 북마크추가 템플릿 삽입
	$('.border-type tbody').last().append(deleteAndAddTemplate4Digital);
	//취소, 수정완료 버튼 for 디지털창
	$('form .inner').append(submitTemplate4DigitalAdd);
}
	
	//-버튼 클릭 후 X버튼 클릭 시 (삭제확정)
	$(document).on('click', '.link-menu.digital .icon.delete', function(){
		var deleteConfirm = confirm("해당 컨텐츠를 삭제하시겠습니까?");
		if(deleteConfirm){
			$.ajax({
				type : "post",
				url : "/DWSWS/deleteContents4Digital",
				cache: false,
				data : $(this).parent().text().trim().substring(1),//삭제할 컨텐츠명
				contentType:"application/json;charset=UTF-8",
				success : function(){
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
	
	