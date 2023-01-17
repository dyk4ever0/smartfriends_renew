/**
 * rule-modify.js
	admin-가이드관리탭- 스마트워크룰 수정 관련 js
 */
		//한 줄 설명, 상세 설명, 영상 url
		var contentsInfo = 
		"<tr>"+
			"<th scope=\"row\"><label for=\"videoTitle\">제목</label></th>"+
			"<td><input type=\"text\" size=\"10\"  id=\"title\"  class=\"common title\" value=\"{videoTitle}\" placeholder=\"영상의 제목을 입력해주세요.\"></td>"+
		"</tr>"+ 
		"<tr>"+
			"<th scope=\"row\"><label for=\"videoTitle\">한 줄 설명</label></th>"+
			"<td><input type=\"text\" size=\"10\"  id=\"oneDesc\"  class=\"common oneDesc\" value=\"{oneDesc}\" placeholder=\"영상의 한 줄 설명을 입력하세요.\"></td>"+
		"</tr>"+
		"<tr>"+
			"<th scope=\"row\"><label for=\"videoTitle\">상세 설명</label></th>"+
			"<td><input type=\"text\" size=\"10\" id=\"ruleDesc\" class=\"common ruleDesc\" value=\"{ruleDesc}\" placeholder=\"영상의 상세 설명을 입력하세요.\"></td>"+
		"</tr>"+
		"<tr>"+
			"<th scope=\"row\"><label for=\"videoUrl\">영상 URL</label></th>"+
			"<td colspan=\"3\">"+
				"<input type=\"text\" name=\"\" id=\"url\" class=\"common btn-padding\" value=\"{url}\" placeholder=\"영상 URL을 입력하세요.\">"+
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
		"</tr>";
		
		//스마트워크룰 EX)업무시작알림 : 업무시작을 알리고 금일 주요 업무사항을 공유합니다.
		var ruleTemplate = 
		"<fieldset class=\"border-type\">"+
			"<legend class=\"sr-only\">섹션 추가</legend>"+
			"<table>"+
				"<colgroup>"+
					"<col style=\"width:70px\">"+
					"<col style=\"width:170px\">"+
					"<col>"+
				"</colgroup>"+
				"<tbody class=\"rule_{num}\">"+
					"<tr>"+
						"<th scope=\"row\"><label for=\"section1Title\">규칙번호</label></th>"+
						"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common ruleNum\" placeholder=\"규칙번호를 입력해주세요.\" value=\"{num}\" disabled></td>"+
					"</tr>"+
					"<tr>"+
						"<th scope=\"row\"><label for=\"section1Title\">규칙명</label></th>"+
						"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common ruleName\" placeholder=\"규칙명을 입력해주세요.\" value=\"{title}\"></td>"+
					"</tr>"+
					"<tr>"+
						"<th scope=\"row\"><label for=\"section1Title\">규칙 설명</label></th>"+
						"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common ruleExplain\" placeholder=\"규칙 설명을 입력해주세요.\" value=\"{comments}\"></td>"+
					"</tr>"+
				"</tbody>"+
			"</table>"+
		"</fieldset>";
		
		//북마크 템플릿
		var bmkSelectTemplate = 
		"<tr class=\"bmkSelect\">"+
			"<th scope=\"row\"><label for=\"section1Bookmark1\">북마크</label></th>"+
			"<td class=\"timestamp-field\">"+
					"<div class=\"data-list-box front-select\">"+
						"<select name=\"main-list\">"+
						"</select>"+
					"</div>"+
			"</td>"+
			"<td>"+
				"<div class=\"data-list-box back-select\">"+
					"<select name=\"main-list\">"+
					"</select>"+
					"<button type=\"button\" class=\"simple deleteBmkBtn4rule\">삭제</button>"+
				"</div>"+
			"</td>"+
		"</tr>";
		
		//규칙삭제(섹션), 북마크 추가 템플릿
		var deleteAndAddTemplate = 
		"<tr>"+
			"<td colspan=\"3\" class=\"btn-wrapper\">"+
				"<button type=\"button\" class=\"simple deleteRule4rule\">규칙 삭제</button>"+
				"<button type=\"button\" class=\"simple addbookmark4rule\">북마크 추가</button>"+
			"</td>"+
		"</tr>";
		
		
		//도구별 북마크리스트 가져와서 select형태로 변환
		var selectBmkMap = new Map();
		
		//변경된 class를 탐지하여 저장하는 set
		var set = new Set();//contents info 변경내용 저장 set
		var ruleSet = new Set(); //규칙명, 규칙설명의 변경내용 저장 set
		var bmkSet = new Set(); //북마크 수정 저장 set
		var newBmkSet = new Set(); //새로 추가된 섹션(규칙)에 추가된 북마크 저장 set
		

	/*	@param
		toolList : Zoom, Lineworks 등 도구리스트
	 	oneDesc : 한 줄 설명
	 	ruleDesc : 상세 설명
	 	fullUrl : 영상 URL
	 	videoTitle : 영상제목
	 	data : 스마트워크 Rule 데이터 ex) 업무계획수립 - 업무계획을 수립하여 일과를 계획합니다.
	 	bmkMap : 도구별 북마크리스트 가져와서 select형태로 변환 ex) Zoom - 환경설정, 마이크/화면확인...
	 	
	 	@function initalTemplate
	 	사용자 권한 확인 후 초기 데이터를 가져와서 기본 템플릿 출력하는 함수
	 */

		//도구리스트 select box
		var toollHTML = "<option> 선택해주세요.</option>";
		
		function initialTemplate(toolList, oneDesc, ruleDesc, fullUrl, videoTitle,data, bmkMap ){
			if(userAuthCheck() == true) {
			//도구리스트 select box
			toolList.forEach(function(element){
				toollHTML +=  "<option value='" + element + "'>" + element + "</option>";
			});
			
			//도구별 북마크리스트 가져와서 select형태로 변환
			for(var key in bmkMap){					
				var tempHTML = "<option> 선택해주세요.</option>";
				for(var i=0; i <bmkMap[key].length; i++){
					tempHTML +="<option value='" +bmkMap[key][i].idx + "'>" + bmkMap[key][i].comments + "</option>";
				}
				selectBmkMap.set(key, tempHTML);
			}
			
			//스마트워크 rule
			for(var i=0; i<data.length; i++){
				if(data[i].length ==  0){
					continue;
				}
				//스마트워크룰 EX)업무시작알림 : 업무시작을 알리고 금일 주요 업무사항을 공유합니다.
				$('.form .ruleList').append(ruleTemplate.replace("{title}",data[i][0].title).replace("{comments}",data[i][0].comments).replace(/{num}/gi, i+1));
				
				
				for(var j=0; j<data[i].length; j++){
					//북마크 select 박스
					$('.ruleList tbody:eq('+i+')').append(bmkSelectTemplate.replace(/{selectedTool}/gi, data[i][j].tool)
																		.replace(/{selectedBmk}/gi, data[i][j].bkcomments)
																		.replace("{selectedBmkIdx}",data[i][j].bidx ));
					
					$('.ruleList tbody:eq('+i+') .data-list-box.front-select:eq('+j+') select').append(toollHTML);//도구
					$('.ruleList tbody:eq('+i+') .data-list-box.back-select:eq('+j+') select').append(selectBmkMap.get(data[i][j].tool));//북마크
					
					//기존 tool을 selected로 바꿔주기
					$('.ruleList tbody:eq('+i+') .data-list-box.front-select:eq('+j+') select option').each(function(){
						if(data[i][j].tool == $(this).val()){
							$(this).attr('selected', 'selected');
						}
					})
					
					
					//기존 북마크를 selected로 바꿔주기
					$('.ruleList tbody:eq('+i+') .data-list-box.back-select:eq('+j+') select option').each(function(){
						if(data[i][j].bidx == $(this).val()){
							$(this).attr('selected', 'selected');
						}
						
					});
					
				}
				$('.ruleList tbody:eq('+i+')').append(deleteAndAddTemplate);
			}

			$('.contentsInfo').append(contentsInfo.replace("{oneDesc}", oneDesc)
												.replace("{ruleDesc}", ruleDesc)
												.replace(/{url}/gi, fullUrl)
												.replace("{videoTitle}", videoTitle));
		
			var url = $('.video').attr('data-src'); //스마트워크 동영상 Url 가져오기
			$('.video').html(returnYoutubeSource(url)); //iframe으로 변환한 것 삽입 
			$('.preview-area .video iframe').css('width', '681px');//동영상 가로 길이 조절
			$('.preview-area .video iframe').css('height', '417px');//동영상 세로 길이 조절
			
		}

		// append non-authorized message
		else { 
			noneTestDataError("Unauthorized"
								, "[컨텐츠 관리 페이지]에 접근할 수 없습니다."
								, "현재 사용자는 해당 페이지의 접근 권한이 없습니다. <br> 관리자에게 접근권한을 요청해야합니다."
								, "main"
								, "메인으로 이동"
								, ".contents-wrapper.contents-header");
			$('.contents-wrapper.has-side-menu').html("");
		}
		}
		
		//영상 URL의 확인버튼 클릭했을때 해당 url의 영상 미리 재생
		$(document).on('click', '.simple.url', function(){
			var url = $('#url').val();
			$('.video').html(returnYoutubeSource(url));
			$('.preview-area .video iframe').css('width', '681px');//동영상 가로 길이 조절
			$('.preview-area .video iframe').css('height', '417px');//동영상 세로 길이 조절
		});
		
		
		//select박스 앞쪽의 도구가 바뀌었을 때, 뒤쪽의 select박스 변경
		$(document).on('change', '.data-list-box.front-select select', function(){
			$(this).parent().parent().parent().find('.data-list-box.back-select select').html( selectBmkMap.get(($(this).val())));
		});
		
		//select박스 변경 감지 이벤트 -> set 저장
		$(document).on('change', 'select', function(e){
			if($(this).parent().parent().parent().parent().attr('class') != 'newRule'){
				//기존 스마트워크룰과 관련된 select박스를 변경한 경우
				bmkSet.add($(this).parent().parent().parent().parent().attr('class'));
			}
		});
		
		
		//모든 input태그에 change event 감지
		$(document).on('change', "input", function(){
			if($(this).attr('class').indexOf("oneDesc") != -1 || $(this).attr('class').indexOf("ruleDesc") != -1 
				||  $(this).attr('class').indexOf("btn-padding") != -1 
				|| $(this).attr('class').indexOf("title") != -1){
				//한 줄 설명이 수정된 경우 or 상세 설명이 수정된 경우 or youtubeUrl이 수정된 경우
				set.add($(this).attr('id'));
				
			}else if($(this).attr('class').indexOf("new") === -1){
				//규칙명, 규칙설명의 변경내용 저장 
				//단, 사용자가 새로 생성한 규칙명과 규칙설명 제외
				ruleSet.add($(this).parent().parent().parent().attr('class'));
			}
		});
		
		//수정완료 버튼 눌렀을 경우
		//변경사항을 탐지해야하는 요소에 change이벤트나 click이벤트를 걸어놓고 경우에 따라 다른 set or array에 저장한다.
	    //제출버튼을 클릭 했을 때, set, array의 정보를 map에 담아서 컨트롤러로 보낸다.
		$(document).on('click', '#submit4rule', function(){
			
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
			
			//select박스에서 '선택해주세요'로 선택된 경우 return
			$(document).find("select option:selected").each(function(){
				
				if ($(this).val() == '선택해주세요.') {
					alert("값을 선택해주세요.");
					$(this).parent().css('border','solid');
					isRight = false;
					return false;
				}
			});
			
		
			
			//규칙번호 시간입력하는 칸에 숫자만 입력할 수 있도록 체크
	        var regex= /^[0-9]*$/;
	        $('.common.newRuleNum').each(function(){
	        	if(!regex.test($(this).val())){
	        		alert("숫자만 입력해주세요.");
	        		$(this).css('border','solid');
	        		isRight = false;
	        		return false;
	        	};
	        });
			
			
			//새로운 규칙을 추가할 때, 북마크를 추가했는지 확인
			//북마크를 추가하지 않고 규칙설명, 규칙번호만 입력한 경우 수정 반영되지 않음            
	        $('.newRule').each(function(){
	        	if($(this).find('.bmkSelect').length == 0){
	        		alert("북마크를 추가해주세요");
					$(this).parent().parent().css('border','solid');
					isRight = false;
	        	}
	        });

			//기존 섹션의 북마크를 모두 삭제하고 섹션설명만 남긴경우 탐지
			$("[class^=rule_]").each(function(){
	        	if($(this).find('.bmkSelect').length == 0){
	        		alert("북마크를 추가해주세요");
					$(this).parent().parent().css('border','solid');
					isRight = false;
	        	}
	        });


			//잘못된 정보가 있는지 check, 잘못된 정보가 있으면 수정하지 않음.
			if (!isRight) {
				return ;
			} 
			
			//-------------수정완료 클릭한 이후 여기까지는 잘못된 문자가 입력됐는지 확인하는 부분
			
			
			
			//모든 수정 정보를 저장할 map
			var map = {};
			
			//contentsInfo 중에서 변경된 내용
			set.forEach(function(value){
				map[value] = $('#'+value+'').val();
			});
		
		
		//규칙명, 규칙설명 변경된 내용
		ruleSet.forEach(function(value){
			var tempData = {};
			tempData.title = $('.'+value+'').find('.common.ruleName').val();
			tempData.comments = $('.'+value+'').find('.common.ruleExplain').val();
			map[value +'forRule'] = tempData;
			});
		
		
		//북마크가 수정된 경우
		bmkSet.forEach(function(value){
			var tempArr = new Array();
			
			$('.'+value+' .bmkSelect .data-list-box.back-select select option:selected').each(function(){
				tempArr.push( $(this).attr('value'));
			});
			map[value + 'forBmk'] = tempArr;
			
		});
		
		//규칙삭제 정보
		map.deleteRule = deleteRuleArr;
		
		//새로운 rule 추가
		$('.newRule').each(function(){
			var tempData = {};
			tempData.num = $(this).find('.common.newRuleNum').val();
			tempData.title = $(this).find('.common.newRuleName').val();
			tempData.comments = $(this).find('.common.newRuleExplain').val();
			map['addNewRule_'+tempData.num] = tempData;
		});
		
		//새로 추가된 섹션(규칙)에 북마크가 추가된 경우
		$('.newRule').each(function(){
			var tempArr = new Array();
			
			$(this).find('.bmkSelect .data-list-box.back-select select option:selected').each(function(){
				tempArr.push( $(this).attr('value'));
			});
			
			var num = $(this).find('.common.newRuleNum').val();
			map['newBmk4newRule_'+num] = tempArr ;
		})
		
		
		//규칙번호가 변경된 경우
		$("[class^='rule_']").each(function(index){
			if($(this).attr('class').split('_')[1] != $(this).find('.common.ruleNum').val()){
				var tempArr = new Array();
				//기존 규칙 번호
				tempArr.push($(this).attr('class').split('_')[1]);
				//바뀔 규칙 번호
				tempArr.push($(this).find('.common.ruleNum').val());
				
				map['changeNum_'+index] = tempArr;
			}
		});
		
			$.ajax({
				type : "post",
				url : "/DWSWS/submitSet4rule",
				cache: false,
				data : JSON.stringify(map),//변경정보를 담은 map 전송
				contentType:"application/json;charset=UTF-8",
				success : function(){
					alert('컨텐츠가 성공적으로 수정되었습니다.');
					window.location.reload();//컨텐츠 수정 이후 reload
					$('html').scrollTop(0); //top으로 이동
				},
				error : function(){
					alert('컨텐츠 수정에 실패했습니다.');
				}
			}); 
		
			
		});
		
	//bookmark를 삭제한 경우
	$(document).on('click', '.simple.deleteBmkBtn4rule', function(){
		//기존 북마크를 삭제한 경우
		if($(this).parent().parent().parent().parent().attr('class') != "newRule"){
			//bmkSet에 저장
			bmkSet.add($(this).parent().parent().parent().parent().attr('class'));
		}
		//새로운 룰에 대한 북마크를 삭제한 경우 + 기존북마크를 삭제한 경우 공통으로 html제거
		$(this).parent().parent().parent().remove();
	});
		
	//북마크추가 클릭 시
	//웹사이트 상에서 이미지 리스트와 '섹션 삭제/북마크 추가' 버튼이 같은 class안에 위치하기 때문에 append대신 before 함수 이용
	//따라서 children갯수를 구하고 맨 끝 요소의 앞에 삽입하도록 함
	$(document).on('click', '.simple.addbookmark4rule', function(){
		
		var childNum = $(this).parent().parent().parent().children().length; //chlidren 갯수
		$(this).parent().parent().parent().children(':eq('+(childNum-1)+')').before(bmkSelectTemplate);
		//tool list select box 삽입
		$(this).parent().parent().parent().children(':eq('+(childNum-1)+')').find('.data-list-box.front-select select').append(toollHTML);
		
		
		//기존 섹션(규칙)의 북마크를 추가하는 경우
		if($(this).parent().parent().parent().attr('class') != 'newRule'){
			//bmkSet 추가 : controller 전송 목적
			bmkSet.add($(this).parent().parent().parent().attr('class'));
		}
		
	});
	
		//+버튼 클릭 시 추가되는 템플릿(규칙추가)
		var newRuleTemplate = 
		"<fieldset class=\"border-type\">"+
			"<legend class=\"sr-only\">섹션 추가</legend>"+
			"<table>"+
				"<colgroup>"+
					"<col style=\"width:70px\">"+
					"<col style=\"width:170px\">"+
					"<col>"+
				"</colgroup>"+
				"<tbody class=\"newRule\">"+
					"<tr>"+
						"<th scope=\"row\"><label for=\"section1Title\">규칙번호</label></th>"+
						"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common newRuleNum\" placeholder=\"규칙번호를 입력해주세요.\"></td>"+
					"</tr>"+
					"<tr>"+
						"<th scope=\"row\"><label for=\"section1Title\">규칙명</label></th>"+
						"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common newRuleName\" placeholder=\"규칙명을 입력해주세요.\"></td>"+
					"</tr>"+
					"<tr>"+
						"<th scope=\"row\"><label for=\"section1Title\">규칙 설명</label></th>"+
						"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common newRuleExplain\" placeholder=\"규칙 설명을 입력해주세요.\"></td>"+
					"</tr>"+
				"</tbody>"+
			"</table>"+
		"</fieldset>";
		
	//+버튼 클릭 시(규칙추가)
	$(document).on('click', '.square-btn.border.text-icon.large.addSection4rule', function(e){
		//규칙번호, 규칙명, 규칙설명 템플릿 삽입
		$('.form .ruleList').append(newRuleTemplate);
		//규칙삭제, 북마크추가 템플릿 삽입
		$('.border-type:last-child tbody').append(deleteAndAddTemplate);
	});
	
	//rule 삭제정보를 저장할 배열
	var deleteRuleArr = new Array();
	//규칙삭제를 클릭한 경우
	$(document).on('click', '.simple.deleteRule4rule', function(){
		if($(this).parent().parent().parent().attr('class') != "newRule"){
			//기존 규칙을 삭제한 경우 : tbody에서 몇번째 규칙인지 정보 얻기
			deleteRuleArr.push(parseInt($(this).parent().parent().parent().attr('class').split('_')[1]));
		}
		//새로 추가한 규칙을 삭제한 경우
		$(this).parent().parent().parent().parent().parent().remove();
	});
	
	//취소버튼
	$(document).on('click', '#cancel4rule', function(){
		window.location.reload();//취소버튼 클릭 시 reload
	}); 