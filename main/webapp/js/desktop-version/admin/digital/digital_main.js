/**
 * digital_main.js
   디지털창 클릭 나타나는 메인화면 관련 js
 */

//디지털창 메인 템플릿 : 컨텐츠명
var digitalContentsTitle = 
"<fieldset class=\"border-type\">"+
	"<legend class=\"sr-only\">섹션 추가</legend>"+
	"<table>"+
		"<colgroup>"+
			"<col style=\"width:70px\">"+
			"<col style=\"width:170px\">"+
			"<col>"+
		"</colgroup>"+
		"<tbody class=\"{contentsIdx}\">"+
			"<tr>"+
				"<th scope=\"row\"><label for=\"contentsTitle\">컨텐츠</label></th>"+
				"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common contentsName\" placeholder=\"컨텐츠명을 입력해주세요.\" value=\"{contentsTitle}\"></td>"+
			"</tr>"+	
		"</tbody>"+
	"</table>"+
"</fieldset>";

//디지털창 메인 템플릿 : 이미지
var digitalImg = 
		"<tr>"+
			"<th scope=\"row\"><label for=\"imgs\">이미지</label></th>"+
			"<td>"+
		        "<input type=\"text\" name=\"file\" class=\"common btn-padding\" value=\"{route}\" disabled style=\"width: 675px;\"/>"+
				"<button type=\"button\" class=\"simple deleteImgBtn\">삭제</button>"+
		    "</td>"+
		"</tr>";
		
//디지털창 이미지 추가 템플릿 : 이미지추가 클릭 시 추가되는 템플릿
var addImg = 
	"<tr>"+
		"<th scope=\"row\"><label for=\"imgs\">이미지</label></th>"+
		"<td>"+
	        "<input type=\"file\" name=\"img\" class=\"common btn-padding\" accept=\"image/png\"/>"+
			"<button type=\"button\" class=\"simple deleteNewImgBtn\">삭제</button>"+
	    "</td>"+
	"</tr>";
		
		
		
//취소, 수정완료, 컨텐츠추가 템플릿
var submitTemplate4Digital = 
    "<fieldset class=\"btn-wrapper\">"+
	    "<button type=\"button\" id=\"cancel4Digital\" class=\"square-btn gray\">취소</button>"+
	    "<button type=\"button\" id=\"submit4Digital\" class=\"square-btn\">수정완료</button>"+
	    "<button type=\"button\" class=\"square-btn border text-icon large addContents\">+<span class=\"sr-only\">추가</span></button>"+
	"</fieldset>";
	
	
//컨텐츠삭제(섹션), 이미지 추가 템플릿
var deleteAndAddTemplate4Digital = 
"<tr>"+
	"<td colspan=\"3\" class=\"btn-wrapper\">"+
		"<button type=\"button\" class=\"simple deleteContents\">컨텐츠 삭제</button>"+
		"<button type=\"button\" class=\"simple addImg\">이미지 추가</button>"+
	"</td>"+
"</tr>";
	
	
	
	//디지털창 탭 메뉴 클릭 시
	$(document).on('click', '.tab-link.digital, #cancel4Digital', function(){
		//기존 탭 비활성화
		$('.header-tab .tab li').removeClass('current');
		//디지털창 탭 활성화
		$('.tab-link.digital').addClass('current');
		//contents-lnb 비우기
		$('aside .body .link-menu').remove();
		$('aside .body').append("<ul class=\"link-menu digital\"></ul>");
		//lnb에 들어갈 디지털창 목록 호출
		$.ajax({
				type : "post",
				url : "/DWSWS/digitalLnb",
				cache: false,
				contentType:"application/json;charset=UTF-8",
				success : function(data){
					//contents-lnb의 .link-menu.digital에 삽입	
		           data.forEach(function(item){
						$('.link-menu.digital').append(contents4adminTemplate.replace("{firstChar}", item.charAt(0))
														  .replace("{tool}", item));
					});
					//첫번째 li active
					$('.link-menu.digital li:eq(0) a').attr('class', 'active');
					//첫번째 데이터 호출
					$.ajax({
						type : "post",
						url : "/DWSWS/digitalContents",
						cache: false,
						data : $('.link-menu.digital li:eq(0)').text().trim().substring(1),//lnb에서 첫번재 li 데이터 전송 ex) 베아월드
						contentType:"application/json;charset=UTF-8",
						success : function(json_map){
							//특정 컨텐츠에 대한 이미지리스트
							var imgData =  JSON.parse(json_map);
							//첫번째 컨텐츠명 ex)베아월드, 간편도구
							var tool = $('.link-menu.digital li:eq(0)').text().trim().substring(1);
							//기존 데이터 호출
							appendData4Digital(tool,imgData );
						},
						error : function(){
							alert('데이터를 불러오는데 실패했습니다.');
						}
					}); 
					},
				error : function(){
					alert('디지털창 목록을 호출하는데 실패했습니다.');
				}
			}); 
		$("h2.section-title").text("디지털창관리");
	});
	
//영상 기본정보와 섹션, 북마크 정보 출력
var formTemplate4Digital = 
	"<form class=\"form\" enctype=\"multipart/form-data\" name=\"img\">"+
        "<div class=\"inner\">"+
        "</div>"+
    "</form>";
	
	//기존 데이터 호출
	function appendData4Digital(tool,imgData){
		//contents 영역 제거 후 삽입
		$('.contents').empty().append(titleTemplate.replace("{tool}",tool)).append(formTemplate4Digital);
		//digitalTemplate 삽입 : 컨텐츠명, 이미지
		for (var i = 0; i < imgData.length; i++) {
			//컨텐츠명
			$('.contents .form .inner').append(digitalContentsTitle.replace("{contentsTitle}", imgData[i].comment)
																	.replace("{contentsIdx}", imgData[i].idx));
			
			//특정 컨텐츠와 관련된 이미지 parse
			var route = JSON.parse(imgData[i].route);
			
			for(var j = 0; j < route.length; j++){
				//특정 컨텐츠와 관련된 이미지 삽입
				$('.contents .form .inner .border-type tbody:eq('+i+')').append(digitalImg.replace("{route}", route[j] ));
			}
			$('.contents .form .inner .border-type tbody:eq('+i+')').append(deleteAndAddTemplate4Digital);
		}
		//취소, 수정완료 버튼 for 디지털창
		$('form .inner').append(submitTemplate4Digital);
	}
	
	
	//삭제할 이미지 파일명을 저장하는 배열
	var deleteImg = [];
	//기존 이미지파일을 삭제하는 경우
	$(document).on('click', '.simple.deleteImgBtn', function(){
		
		//이미지 파일명을 배열에 저장
		deleteImg.push($(this).parent().find('.common.btn-padding').val());
		
		//북마크추가를 통해 생성된 북마크를 삭제한 경우
		$(this).parent().parent().remove();
	});
	
	//이미지추가를 통해 생성된 이미지를 삭제한 경우
	$(document).on('click', '.simple.deleteNewImgBtn', function(){
		
		$(this).parent().parent().remove();
	});
	
	//제출버튼 눌렀을 경우 for digital
	//변경사항을 탐지해야하는 요소에 change이벤트나 click이벤트를 걸어놓고 경우에 따라 다른 set or array에 저장한다.
	//제출버튼을 클릭 했을 때, set, array의 정보를 map에 담아서 컨트롤러로 보낸다.
 	$(document).on('click', '#submit4Digital', function(){
		//비어있는 input file 있는지 확인
 		var isRight = true;
 		$(document).find("input[type=file]").each(function(){
            // 아무값없이 띄어쓰기만 있을 때도 빈 값으로 체크되도록 trim() 함수 호출
            if ($(this).val().trim() == '') {
                alert("파일을 첨부해주세요.");
                $(this).css('border','solid');
                isRight = false;
                return false;
            }
        });

		//비어있는 input text있는지 확인
		$(document).find("input[type=text]").each(function(){
            // 아무값없이 띄어쓰기만 있을 때도 빈 값으로 체크되도록 trim() 함수 호출
            if ($(this).val().trim() == '') {
                alert("공백을 채워주세요.");
                $(this).css('border','solid');
                isRight = false;
                return false;
            }
        });

		//새로 디지털창의 컨텐츠를 추가하는데 관련 이미지를 첨부하지 않은 경우
		if($('.border-type').length == 0){
			alert("관련된 컨텐츠를 추가해주세요.");
			isRight = false;
            return false;
		}

	    //비어있는 곳이 있다면 return
        if (!isRight) {
            return;
        }

		 //-------------수정완료 클릭한 이후 여기까지는 잘못된 문자가 입력됐는지 확인하는 부분
		
		//tool이름 : 베아월드, FIORI..
		var tool = $('.section-title:eq(1)').text();
		
		//수정할 모든 정보를 저장할 map
		//변경 사항을 map에 담아서 map을 백으로 보낸다.
		
		var map = {'tool' : tool};
		
		//추가할 컨텐츠명을 저장하는 배열
		var addContentsArr = new Array();
		$('.newContents').each(function(){
			addContentsArr.push($(this).find('.common.contentsName').val());
		});
		//map에 추가할 컨텐츠명을 담은 배열 삽입
		map.addContents = addContentsArr;
		
		//map에 이미지 삭제정보 삽입 : 이미지 파일명 배열
		map.deleteImg = deleteImg;
		
		//map에 컨텐츠 삭제 정보 삽입 : 삭제할 컨텐츠명을 저장하는 배열
		map.deleteContents = deleteContents;
		
		//컨텐츠명 수정 정보
		//컨텐츠명이 변경된 경우 set에 담아두고, for문을 돌면셔 컨텐츠의 idx와 변경 내용을 배열에 담아서 map에 저장
		contentsNameSet.forEach(function(value){
			var tempData = {};
			tempData.idx = Number(value);
			tempData.name = $('.'+value+' .common.contentsName').val();
			map["nameChange"+value] = tempData;
		});
		
		
		$.ajax({
				type : "post",
				url : "/DWSWS/digitalModify",
				cache: false,
				data : JSON.stringify(map),//변경사항을 담은 map을 백으로 보냄
				contentType:"application/json;charset=UTF-8",
				success : function(){
					//컨텐츠명 삽입 이후
					//이미지 업로드 함수 실행 : 이미지파일과 컨텐츠명 formData를 서버에 전송
					if($('form input[type="file"]').length != 0){//이미지 파일이 있는 경우에만 실행
						imgupload();
					}else{
						window.location.reload();// reload
						$('html').scrollTop(0);//top으로 이동
					}
					
				},
				error : function(){
					alert('컨텐츠 수정에 실패했습니다.');
				}
			}); 


	});
	
	//이미지 업로드 함수
	//이미지배열와 이미지와 관련된 컨텐츠명을 저장하는 배열을 전송
	//컨트롤러에서 이미지와 컨텐츠명을 매칭시켜준 후 service계층으로 전달 
	function imgupload(){
		var formData = new FormData(); //이미지파일 배열과 컨텐츠명을 저장하는 배열을 포함하는 formData
		var contentsArray = []; //컨텐츠명을 저장하는 배열
			
		for(var i=0; i<$('form input[type="file"]').length; i++){
			//이미지파일과 관련된 컨텐츠 이름를 저장
			formData.append('imgArray',$('form input[type="file"]')[i].files[0] ); // 이미지파일 저장
			contentsArray.push(JSON.stringify($('form input[type="file"]').eq(i).parent().parent().parent().find('.common.contentsName').val())); //컨텐츠명 저장
			
		}
			
			
		formData.append("contentsArray", contentsArray); // 컨텐츠명 배열을 formData에 삽입
		
		$.ajax({
				type : "post",
				url : "/DWSWS/imgUpload",
				cache: false,
				contentType: false,
				processData: false,
				data : formData,//formData 전송
				traditional : true,
				enctype : 'multipart/form-data',
				success : function(){
					window.location.reload();// reload
					$('html').scrollTop(0);//top으로 이동
				},
				
				
				error : function(){
					alert('데이터를 불러오는데 실패했습니다.');
				}
			});
	}
	
	
	
	//이미지추가 클릭 시 이미지 파일 업로드 템플릿 삽입(input file을 포함한 템플릿 추가)
	//웹사이트 상에서 이미지 리스트와 컨텐츠 삭제/이미지추가 버튼이 같은 class안에 위치하기 때문에 append대신 before 함수 이용
	//따라서 children갯수를 구하고 맨 끝 요소의 앞에 삽입하도록 함
	$(document).on('click', '.simple.addImg', function(){
		var childNum = $(this).parent().parent().parent().children().length; //chlidren 갯수
		$(this).parent().parent().parent().children(':eq('+(childNum-1)+')').before(addImg);
		
	});
	
//+버튼 클릭 시 추가되는 템플릿(컨텐츠 추가)
var newContents = 
"<fieldset class=\"border-type\">"+
	"<legend class=\"sr-only\">섹션 추가</legend>"+
	"<table>"+
		"<colgroup>"+
			"<col style=\"width:70px\">"+
			"<col style=\"width:170px\">"+
			"<col>"+
		"</colgroup>"+
		"<tbody class=\"newContents\">"+
			"<tr>"+
				"<th scope=\"row\"><label for=\"contentsTitle\">컨텐츠</label></th>"+
				"<td colspan=\"2\"><input type=\"text\" name=\"\" class=\"common contentsName\" placeholder=\"컨텐츠명을 입력해주세요.\"></td>"+
			"</tr>"+	
		"</tbody>"+
	"</table>"+
"</fieldset>";

//+버튼 클릭 시 (컨텐츠 추가)
//웹사이트 상에서 컨텐츠 리스트와 '취소, 수정완료, +' 버튼이 같은 class안에 위치하기 때문에 append대신 before 함수 이용
//따라서 children갯수를 구하고 맨 끝 요소의 앞에 삽입하도록 함
	$(document).on('click', '.square-btn.border.text-icon.large.addContents', function(){
		//규칙번호, 규칙명, 규칙설명 템플릿 삽입
		var childNum = $('.form .inner').children().length; //chlidren 갯수
		$('.form .inner fieldset:eq('+(childNum-1)+')').before(newContents);
		
		//규칙삭제, 북마크추가 템플릿 삽입
		$('.border-type tbody').last().append(deleteAndAddTemplate4Digital);
	});
	
	//삭제할 컨텐츠명을 저장하는 배열
	var deleteContents = [];
	// 컨텐츠 삭제 클릭 시
	$(document).on('click', '.simple.deleteContents', function(){
			if($(this).parent().parent().parent().attr('class') != 'newContents'){
				//기존 컨텐츠 삭제 클릭 시
				deleteContents.push($(this).parent().parent().parent().attr('class'));
			}
			//기존 컨텐츠 삭제 클릭 시 + 사용자가 직접 추가한 컨텐츠 삭제 클릭 시
			$(this).parent().parent().parent().parent().parent().remove();
		});

	//디지털창 페이지에서 취소를 클릭한 경우
	$(document).on('click', '#cancel4Digital', function(){
		window.location.reload();// reload
		$('html').scrollTop(0);//top으로 이동
	});
	
	//변경된 컨텐츠명을 저장할 set - 기존 컨텐츠명 변경 시에만 적용
	var contentsNameSet = new Set();
	//변경된 컨텐츠명 저장(input태그에 모두 이벤트 리스너연결)
	$(document).on('change', ".contents input[type=text]", function(){
		if($(this).parent().parent().parent().attr('class') != "newContents"){
			//기존 컨텐츠명을 변경했을 경우에만 set에 add
			//사용자가 추가한 컨텐츠명은 set에 넣지 않음
			contentsNameSet.add($(this).parent().parent().parent().attr('class'));
		}
		
	});