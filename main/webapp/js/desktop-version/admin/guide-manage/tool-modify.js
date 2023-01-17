/** 
 * tool-modify.js 
	admin - 컨텐츠관리 - 가이드관리 - 스마트워크Rule을 제외한 모든 컨텐츠 관련 js
	Zoom, Lineworks..
 */

//도구이름(zoom, lineworks...) 
var titleTemplate =  
	"<section>"+ 
        "<div class=\"inner\">"+ 
            "<h3 class=\"section-title\">{tool}</h3>"+ 
        "</div>"+ 
    "</section>"; 

//영상 기본정보와 섹션, 북마크 정보 출력
var formTemplate = 
	"<form action=\"#\" class=\"form\">"+
        "<div class=\"inner\">"+
        "</div>"+
    "</form>";

//기본정보(제목, url, 카테고리)
var template = 
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
                "<td><input type=\"text\" name=\"\" id=\"desc\" class=\"common\" value=\"{desc}\"></td>"+
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
                    "<input type=\"text\" name=\"\" id=\"url\" class=\"common btn-padding\" value=\"{url}\">"+
                    "<button type=\"button\" class=\"simple url\">확인</button>"+
                "</td>"+
            "</tr>"+
            "<tr>"+
                "<th scope=\"row\"><span class=\"sr-only\">미리보기</span></th>"+
                "<td colspan=\"3\">"+
                    "<div class=\"preview-area\">"+
                        "<p class=\"video\" data-src=\"{url}\"></p>"+
                    "</div>"+
                "</td>"+
            "</tr>"+
        "</tbody>"+
    "</table>"+
    "<input type=\"hidden\" id=\"idx\" value=\"{idx}\" />"+
"</fieldset>"+
"<div class=\"sectionList\"></div>";

//section template
var sectionTemplate = 
	"<fieldset class=\"border-type\">"+
	    "<legend class=\"sr-only\">섹션 추가</legend>"+
	    "<table>"+
	        "<colgroup>"+
	            "<col style=\"width:70px\">"+
	            "<col style=\"width:170px\">"+
	            "<col>"+
	        "</colgroup>"+
	        "<tbody>"+
	            "<tr>"+
	                "<th scope=\"row\"><label for=\"section1Title\">섹션 제목</label></th>"+
	                "<td colspan=\"2\"><input type=\"text\" name=\"\" id=\"section{sectionIdx}\" class=\"common section\" value=\"{section}\"></td>"+
	                 "<input type=\"hidden\" class=\"secIdx\" name=\"secIdx\" value=\"{sectionIdx}\" />"+
	            "</tr>"+
	        "</tbody>"+
	    "</table>"+
	"</fieldset>";

//section 삭제, 북마크추가 템플릿
var sectionTemplate2 = 
     "<tr>"+
        "<td colspan=\"3\" class=\"btn-wrapper\">"+
            "<button type=\"button\" class=\"simple deleteSection\">섹션 삭제</button>"+
            "<button type=\"button\" class=\"simple addbookmark\">북마크 추가</button>"+
        "</td>"+
    "</tr>";
	
//bookmark template
var bookmarkTemplate = 
	"<tr class=\"bookmark{bmkidx}\">"+
	    "<th scope=\"row\"><label for=\"section2Bookmark2\">북마크</label></th>"+
	    "<td class=\"timestamp-field\">"+
	        "<input type=\"text\" name=\"section2Bookmark2mm\"  class=\"common minute\" value=\"{minute}\"> 분 "+
	        "<input type=\"text\" name=\"section2Bookmark2ss\"  class=\"common second\" value=\"{second}\"> 초"+
	    "</td>"+
	    "<td>"+
	        "<input type=\"text\" name=\"section2Bookmark2title\" class=\"common btn-padding bmk\" value=\"{bookmark}\">"+
	        "<button type=\"button\" class=\"simple deleteBmkBtn\">삭제</button>"+
	        "<input type=\"hidden\" class=\"bmkidx\" name=\"bmkidx\" value=\"{bmkidx}\" /></p>"+
	    "</td>"+
	"</tr>";


 
//추가 북마크 템플릿(기존 섹션에 추가하는 경우)
var addBookmarkTemplate = 
		"<tr class=\"newbookmark\">"+
		    "<th scope=\"row\"><label for=\"section2Bookmark2\">북마크</label></th>"+
		    "<td class=\"timestamp-field\">"+
		        "<input type=\"text\" name=\"section2Bookmark2mm\"  class=\"common minute\"  placeholder=\"00\"> 분 "+
		        "<input type=\"text\" name=\"section2Bookmark2ss\"  class=\"common second\"  placeholder=\"00\"> 초"+
		    "</td>"+
		    "<td>"+
		        "<input type=\"text\" name=\"section2Bookmark2title\" class=\"common btn-padding bmk\" placeholder=\"북마크 제목을 입력하세요\">"+
		        "<button type=\"button\" class=\"simple deleteBmkBtn\">삭제</button>"+
		   		"<input type=\"hidden\" class=\"secidx\" name=\"secidx\" value=\"{secidx}\" /></p>"+
		     "</td>"+
		"</tr>";


	 
//추가 북마크 템플릿(새로 추가한 섹션에 추가하는 경우)
var addBookmarkTemplate2 = 
	"<tr class=\"newBmk4newSec\">"+
	    "<th scope=\"row\"><label for=\"section2Bookmark2\">북마크</label></th>"+
	    "<td class=\"timestamp-field\">"+
	        "<input type=\"text\" name=\"section2Bookmark2mm\"  class=\"common minute\" placeholder=\"00\" > 분 "+
	        "<input type=\"text\" name=\"section2Bookmark2ss\"  class=\"common second\" placeholder=\"00\" > 초"+
	    "</td>"+
	    "<td>"+
	        "<input type=\"text\" name=\"section2Bookmark2title\" class=\"common btn-padding bmk\" placeholder=\"북마크 제목을 입력하세요\" >"+
	        "<button type=\"button\" class=\"simple deleteBmkBtn\">삭제</button>"+
	    "</td>"+
	"</tr>";

//추가 섹션 템플릿
var addSectionTemplate = 
	"<fieldset class=\"border-type\">"+
	   "<legend class=\"sr-only\">섹션 추가</legend>"+
	    "<table>"+
	        "<colgroup>"+
	            "<col style=\"width:70px\">"+
	            "<col style=\"width:170px\">"+
	            "<col>"+
	        "</colgroup>"+
	        "<tbody class=\"newSection\">"+
	            "<tr>"+
	                "<th scope=\"row\"><label for=\"section1Title\">섹션 제목</label></th>"+
	                "<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common section\" placeholder=\"북마크 묶음인 섹션의 제목을 입력하세요.\"></td>"+
	            "</tr>"+
	          	"<tr>"+
		            "<td colspan=\"3\" class=\"btn-wrapper\">"+
		                "<button type=\"button\" class=\"simple deleteSection\">섹션 삭제</button>"+
		                "<button type=\"button\" class=\"simple addbookmark\">북마크 추가</button>"+
		            "</td>"+
	        	"</tr>"
	        "</tbody>"+
	    "</table>"+
	"</fieldset>";

//취소, 제출, 섹션추가 템플릿
var submitTemplate = 
    "<fieldset class=\"btn-wrapper\">"+
	    "<button type=\"button\" id=\"cancel\" class=\"square-btn gray\">취소</button>"+
	    "<button type=\"button\" id=\"submit\" class=\"square-btn\">수정완료</button>"+
	    "<button type=\"button\" class=\"square-btn border text-icon large addSection\">+<span class=\"sr-only\">추가</span></button>"+
	"</fieldset>";
	
	
	
//베어독 두번째 url 넣는 템플릿
var url2Template = 
	"<tr>"+
        "<th scope=\"row\"><label for=\"videoUrl\">영상 URL2</label></th>"+
        "<td colspan=\"3\">"+
            "<input type=\"text\" name=\"\" id=\"url2\" class=\"common btn-padding\" value=\"{url}\">"+
            "<button type=\"button\" class=\"simple url2\">확인</button>"+
        "</td>"+
    "</tr>"+
    "<tr>"+
        "<th scope=\"row\"><span class=\"sr-only\">미리보기</span></th>"+
        "<td colspan=\"3\">"+
            "<div class=\"preview-area\">"+
                "<p class=\"video2\" data-src=\"{url}\"></p>"+
            "</div>"+
        "</td>"+
    "</tr>";

/*	@param
	contentsInfo : 기본정보(url, 설명..)
	sectionData : 섹션데이터
	bmkList : 북마크데이터
 	
 	@function initialFuncion4Tool
 	contents-lnb에서 클릭한 컨텐츠(zoom, lineworks..) 데이터를 가져와서 기본 템플릿 출력하는 함수
 */
function initialFuncion4Tool(contentsInfo, sectionData, bmkList){
	
	//contents_admin_lnb에서 클릭한 부분 active
	$('.link-menu li a').removeClass('active');
	$('.link-menu li').each(function(){
		if(contentsInfo.title === $(this).text().trim().substring(1)){
			$(this).find('a').attr('class', 'active');
		}
	});

	//contents 영역 제거 후 삽입
	$('.contents').empty().append(titleTemplate.replace("{tool}", contentsInfo.title)).append(formTemplate);
	
	//기본정보(url, 설명..)	
	$('form .inner').append(template.replace("{desc}", contentsInfo.comments)
									.replace("{idx}", contentsInfo.idx)
									.replace(/{url}/gi, contentsInfo.youtubeurl));
	
	//해당되는 카테고리 라디오버튼 활성화
	if(contentsInfo.section == "도구 가이드"){
		//$('#toolGuide').attr('checked', 'checked');
		$('input[name="videoType"]:radio[class="도구 가이드"]').prop('checked',true);
	}else if(contentsInfo.section == "보안"){
		$('input[name="videoType"]:radio[class="보안"]').prop('checked',true);
	}
	
	//동영상
	var url = $('.video').attr('data-src'); //영상Url 가져오기
	$('.video').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입 
	$('.preview-area .video iframe').css('width', '681px');//동영상 가로 길이 조절
	$('.preview-area .video iframe').css('height', '417px');//동영상 세로 길이 조절
	
	//section list
	for(var i=0; i<sectionData.length; i++){ //section print
		$('form .inner .sectionList').append(sectionTemplate.replace("{section}", sectionData[i].comments).replace(/{sectionIdx}/gi, sectionData[i].sectionIdx) );
	}	
	
	//bookmark list
		for(var i=0; i<sectionData.length; i++){//bookmark print
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
			//북마크 정보
			$('.border-type:eq('+i+') table tbody').append(bookmarkTemplate.replace("{minute}", ten).replace("{second}", one).replace("{bookmark}", bmkList[i][j].bookmark).replace(/{bmkidx}/gi,bmkList[i][j].bmkidx))
		}
		//section 삭제, 북마크추가 템플릿
		$('.border-type:eq('+i+') table tbody').append(sectionTemplate2);
		
	}
	
	//취소, 수정완료 버튼
	$('form .inner').append(submitTemplate);
	
	//베어독인경우 두번째 영상 url 추가
	if(contentsInfo.title === 'BearDoc'){
		$.ajax({
				type : "get",
				url : "/DWSWS/secondUrl",
				cache: false,
				data : {
			        "tool" : contentsInfo.title,//컨텐츠명
			    },
				contentType:"application/json;charset=UTF-8",
				success : function(data){
					$('.inner fieldset table tbody:eq(0)').append(url2Template.replace(/{url}/gi, data));
					//동영상
					var url = $('.video2').attr('data-src'); //zoomUrl 가져오기
					$('.video2').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입 
					$('.preview-area .video2 iframe').css('width', '681px');//동영상 가로 길이 조절
					$('.preview-area .video2 iframe').css('height', '417px');//동영상 세로 길이 조절
				},
				error : function(){
					alert('두번째 url을 불러오느데 실패했습니다.');
				}
			}); 
	}
	
}

	
	
	 //제출버튼 눌렀을 경우
 	$(document).on('click', '#submit', function(e){
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
		
		//contentsInfo 중에서 변경된 내용
		set.forEach(function(value){
	
			map[value] = $('#'+value+'').val();
		});
		
		
		//section 중에서 변경된 내용
		sectionSet.forEach(function(value){
			
			var tempData = {};
			tempData.comments = $('#'+value+'').val();
			tempData.secIdx = $('#'+value+'').parent().parent().find('.secIdx').attr('value');
			map[value] = tempData;
		});
		
		
		
		//bookmark 삭제 정보
		map.deleteBmk = deleteBmkArr;	
		
		
		//section 삭제 정보
		map.deleteSection = deleteSecArr;
		
		
		//bookmark 업데이트 정보
		bmkSet.forEach(function(value){
			var tempData = {};
			tempData.timemoment = Number($('.'+value+' .common.minute').val())*60 + Number($('.'+value+' .common.second').val());
			tempData.comments = $('.'+value+' .common.btn-padding.bmk').val();
			tempData.bmkidx = $('.'+value+'').parent().find('.bmkidx').attr('value');
			map["bmk"+value] = tempData;
		});
		
		
		//bookmark insert 정보
		$('.newbookmark').each(function(index, item){
			var tempData = {};
			tempData.timemoment = Number($(this).find('.common.minute').val())*60+Number($(this).find('.common.second').val());
			tempData.bmk = $(this).find('.common.btn-padding.bmk').val();
			tempData.secidx = $(this).find('.secidx').attr('value');
			map["newbookmark"+index] = tempData;
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
		
		console.log(map);
		
 		//ajax로 변경할 데이터 전송
  		$.ajax({
			type : "post",
			url : "/DWSWS/submitSet",
			data : JSON.stringify(map),
			cache: false,
			contentType:"application/json;charset=UTF-8",
			success : function(){
				alert('컨텐츠가 성공적으로 수정되었습니다.');
				//해당 페이지 reload
				$.ajax({
					type : "post",
					url : "/DWSWS/contentsTool",
					cache: false,
					data : $('.section-title:eq(1)').text(),
					contentType:"application/json;charset=UTF-8",
					success : function(json_map){
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
	
	
 	
	//bookmark 삭제정보를 저장할 배열
	var deleteBmkArr = new Array();
	
	$(document).on('click', '.simple.deleteBmkBtn', function(e){
		if($(this).parent().parent().attr('class')!=="newbookmark" && $(this).parent().parent().attr('class')!=="newBmk4newSec"  ){//기존 북마크를 삭제한 경우
			
			deleteBmkArr.push(parseInt($(this).parent().find('.bmkidx').attr('value')));
			
		}
		//북마크추가를 통해 생성된 북마크를 삭제한 경우
		$(this).parent().parent().remove();
	});

	
	//section 삭제정보를 저장할 배열
	var deleteSecArr = new Array();
	//섹션 삭제버튼을 클릭한 경우
	$(document).on('click', '.simple.deleteSection', function(e){
		if($(this).parent().parent().parent().find('.secIdx').length > 0){
			//기존 섹션을 삭제한 경우
			deleteSecArr.push(parseInt($(this).parent().parent().parent().find('.secIdx').attr('value')));
		}
		//새로 추가한 섹션을 삭제한 경우
		$(this).parent().parent().parent().parent().parent().remove();
	});
	
	
	//변경된 class를 탐지하여 저장하는 set
	var set = new Set();//contents info 변경내용 저장 set
	var sectionSet = new Set(); //section 변경내용 저장 set
	var bmkSet = new Set(); //bookmark update 저장 set
	
	
	//변화된 class명 저장(input태그에 모두 이벤트 리스너연결)
	$(document).on('change', "input", function(e){
		if($(this).attr('class').indexOf("section") != -1){
			//변경된 section에 대한 내용일 경우
			sectionSet.add($(this).attr('id'));
		
		}else if($(this).attr('class').indexOf("minute") != -1 || $(this).attr('class').indexOf("second") != -1 || $(this).attr('class').indexOf("bmk") != -1){
			//시간이나 북마크 내용인 경우
			if($(this).parent().parent().attr('class').indexOf('newbookmark') === -1 && $(this).parent().parent().attr('class').indexOf('newBmk4newSec') === -1){
				//bookmark insert가 아닌 bookmark update 내용일 경우 
				bmkSet.add($(this).parent().parent().attr('class'));
			}
			
			
		}else{
			//변경된 contentsInfo에 대한 내용일 경우
			set.add($(this).attr('id'));
		}
	});
	
	//북마크추가 클릭 시
	$(document).on('click', '.simple.addbookmark', function(e){
		if($(this).parent().parent().parent().find('.secIdx').length > 0){//기존 섹션의 북마크를 추가하는 경우
			
			var childNum = $(this).parent().parent().parent().children().length; //chlidren 갯수
			$(this).parent().parent().parent().children(':eq('+(childNum-1)+')').before(addBookmarkTemplate.replace("{secidx}", $(this).parent().parent().parent().find('.secIdx').attr('value')));
		
		}else{//새로 추가한 섹션에 북마크를 추가하는 경우
			
			var childNum = $(this).parent().parent().parent().children().length; //chlidren 갯수
			$(this).parent().parent().parent().children(':eq('+(childNum-1)+')').before(addBookmarkTemplate2);
			//$(this).parent().parent().append(addBookmarkTemplate2);
			
		}
	});
	

	//섹션추가
	$(document).on('click', '.square-btn.border.text-icon.large.addSection', function(e){
		$('form .inner .sectionList').append(addSectionTemplate);
	}); 
	
	//취소버튼
	$(document).on('click', '#cancel', function(e){
		window.location.reload();//취소버튼 클릭 시 reload
	}); 
	
	//두번째 영상 URL의 확인버튼 클릭했을때 해당 url의 영상 미리 재생(for BearDoc)
		$(document).on('click', '.simple.url2', function(){
			var url2 = $('#url2').val();
			$('.video2').html(returnYoutubeSource(url2));
			$('.preview-area .video2 iframe').css('width', '681px');//동영상 가로 길이 조절
			$('.preview-area .video2 iframe').css('height', '417px');//동영상 세로 길이 조절
		});
	
	