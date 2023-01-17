/**
 * add-contents.js :  contents-lnb에서 +버튼을 클릭하여 컨텐츠 추가 시 실행되는 js
 */

//기본정보(제목, url, 카테고리) : 추가한 컨텐츠관련
var template4add = 
    "<fieldset>" +
    "<legend class=\"sr-only\">기본 정보</legend>"+
    "<table>"+
        "<colgroup>"+
            "<col style=\"width:75px\">"+
            "<col style=\"width:330px\">"+
            "<col>"+
            "<col>"+
        "</colgroup>"+
        "<tbody>"+
            "<tr>"+
                "<th scope=\"row\"><label for=\"videoTitle\">설명</label></th>"+
                "<td><input type=\"text\" name=\"\" id=\"desc\" class=\"common\" placeholder=\"설명을 입력해주세요.\"></td>"+
              "</tr>"+
              "<tr>"+
                "<th scope=\"row\">카테고리</th>"+
                "<td>"+
                    "<label class=\"radio\">"+
                        "<input type=\"radio\" class=\"도구 가이드\" name=\"videoType\" checked><span class=\"mark\"></span>"+
                        "도구 가이드"+
                    "</label>"+
                    "<label class=\"radio\">"+
                        "<input type=\"radio\" class=\"보안\" name=\"videoType\"><span class=\"mark\"></span>"+
                        "보안"+
                    "</label>"+
                "</td>"+
            "</tr>"+
            "<tr>"+
                "<th scope=\"row\"><label for=\"videoUrl\">영상 URL</label></th>"+
                "<td colspan=\"3\">"+
                    "<input type=\"text\" name=\"\" id=\"url\" class=\"common btn-padding\"  placeholder=\"영상 URL을 입력해주세요.\">"+
                    "<button type=\"button\" class=\"simple url\">확인</button>"+
                "</td>"+
            "</tr>"+
            "<tr>"+
                "<th scope=\"row\"><span class=\"sr-only\">미리보기</span></th>"+
                "<td colspan=\"3\">"+
                    "<div class=\"preview-area\">"+
                        "<p class=\"video\" data-src=\"\"></p>"+
                    "</div>"+
                "</td>"+
            "</tr>"+
        "</tbody>"+
    "</table>"+
"</fieldset>"+
"<div class=\"sectionList\"></div>";

//취소, 제출, 섹션추가 템플릿 : 가이드관리에서 컨텐츠 추가 시
var submitTemplate4add = 
    "<fieldset class=\"btn-wrapper\">"+
	    "<button type=\"button\" id=\"cancel4add\" class=\"square-btn gray\">취소</button>"+
	    "<button type=\"button\" id=\"submit4add\" class=\"square-btn\">수정완료</button>"+
	    "<button type=\"button\" class=\"square-btn border text-icon large addSection\">+<span class=\"sr-only\">추가</span></button>"+
	"</fieldset>";

/*
	@param
	newContnets : 추가하는 컨텐츠명
	@function appendNewContents : 새 컨텐츠 정보를 입력할 템플릿 삽입하는 함수
*/
function appendNewContents(newContents){
	//contents 영역 제거 후 삽입
	$('.contents').empty().append(titleTemplate.replace("{tool}", newContents)).append(formTemplate);
	
	//기본정보(url, 설명..)	
	$('form .inner').append(template4add);
	
	//취소, 수정완료 버튼
	$('form .inner').append(submitTemplate4add);
}

//제출버튼 눌렀을 경우 : 가이드관리의 lnb에서 +버튼 클릭하여 생성된 페이지에서 제출버튼 눌렀을 경우 
//이때는 모든 것이 새로운 데이터이기 때문에 chnage이벤트를 걸지않고 모든 데이터를 삽입한다.
//삽입할 데이터는 map에 담아서 컨트롤러로 보낸다.
	$(document).on('click', '#submit4add', function(){
		//비어있는 input text있는지 확인
		var isRight = true;
		$(document).find("input[type=text]").each(function(){
        // 아무값없이 띄어쓰기만 있을 때도 빈 값으로 체크되도록 trim() 함수 호출
        if ($(this).val().trim() == '') {
            alert("공백을 채워주세요.");
            $(this).css('border','solid');
            isRight = false;
            return false;
        }
    });

	//북마크 시간입력하는 칸에 숫자만 입력할 수 있도록 체크
    var regex= /^[0-9]*$/;
    $('.common.minute, .common.second').each(function(){
    	if(!regex.test($(this).val())){
    		alert("숫자만 입력해주세요.");
    		$(this).css('border','solid');
    		isRight = false;
    		return false;
    	};
    });

     //비어있는 input text가 있다면 return
    if (!isRight) {
        return;
    }
     
    //-------------수정완료 클릭한 이후 여기까지는 잘못된 문자가 입력됐는지 확인하는 부분
    
    
	//controller에 보낼 정보를 map에 담아서 전송
		var tool = $('.section-title:eq(1)').text();
		var category = $('input[name="videoType"]:checked').attr('class');
		var map = {'innerdata' : {'tool': tool, 'category':category}}; 
	
	//contentsInfo(설명, 카테고리, 영상url)관련 데이터 map에 저장
	set.forEach(function(value){

		map[value] = $('#'+value+'').val();
	});
	

	//새로운 section 추가
	$('.newSection').each(function(index){
		map['newSection'+index] = $(this).find('.common.section').val();
	});
	

	//새로운 section에 대한 북마크 추가
	$('.newBmk4newSec').each(function(index){
		var tempData = {};
		tempData.section = $(this).parent().find('.common.section').val();
		tempData.timemoment = Number($(this).find('.minute').val())*60+Number($(this).find('.second').val());
		tempData.bmk = $(this).find('.bmk').val();
		map['newBmk4newSec'+index] = tempData;
	});
	
	
		//ajax로 변경할 데이터 전송
		$.ajax({
		type : "post",
		url : "/DWSWS/submitSet4add",
		data : JSON.stringify(map),
		cache: false,
		contentType:"application/json;charset=UTF-8",
		success : function(){
			alert('컨텐츠가 성공적으로 추가되었습니다.');
			//해당 페이지 reload
			$.ajax({
				type : "post",
				url : "/DWSWS/contentsTool",
				cache: false,
				data : $('.section-title:eq(1)').text(),//추가할 도구명
				contentType:"application/json;charset=UTF-8",
				success : function(json_map){
					//contents_lnb 호출
					contents_lnb_call();
					var map =  JSON.parse(json_map);
					//initialFuncion4Tool 함수는 tool-modify.js에 위치
					initialFuncion4Tool(map.contentsInfo, map.sectionList, map.bmkList);
					//top으로 이동
					$('html').scrollTop(0);
				},
				error : function(){
					alert('데이터를 불러오는데 실패했습니다.');
				}
			}); 
			
		},
		error : function(){
			alert('컨텐츠 수정에 실패했습니다.');
		}
	});   
});
	
	//취소버튼 눌렀을 경우 : 컨텐츠 추가하는 페이지에서
	$(document).on('click', '#cancel4add', function(e){
		window.location.reload();
	});