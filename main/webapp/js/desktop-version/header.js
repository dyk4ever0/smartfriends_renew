
// Get result values about authentication
function userAuthCheck() {
  var result = null;
  $.ajax({
    type: "get",
    url: "/DWSWS/authCheck",
    async: false,

    success: function (data) {
      result = JSON.parse(data);
    },
    error: function (request, error) {
      console.log(request, error);
    },
  });
  return result;
}

/**
 * This function used for checking authentication for management access icon on right-top of page
 * This function is activated for specific icons written as jQuery function parameter. (.stats, .setup)
 * @returns authflag and redirect to each page.
 */
$(function () {
  $("header .util-menu .chat").on({
    click: function(event) {
      event.preventDefault();
      alert("궁금한 사항은 idsTrust IT자산관리팀으로 문의부탁드립니다.\n02-550-8088");
    }
  })

  $("header .util-menu .stats").on({
    click: function (event) {
      event.preventDefault();
      location.href="analysis";
    },
  });

  $("header .util-menu .setup").on({
    click: function (event) {
      event.preventDefault();
      location.href="contents";
    },
  });
});


//For calling badge 
//header에서 즐겨찾기 버튼(종모양)위의 즐겨찾기 갯수를 출력하기 위한 함수
 function favotiteNum(){
 	$.ajax({
	type : "post",
    url : "/DWSWS/favoriteNum",
    cache: false,
	success : function(data){//리턴된 data는 사용자별 즐겨찾기 갯수
		$('.badge').html(data);
		},
	error : function(request, error){
    console.log(request,error);
	}
});
 }

favotiteNum();


// For highlighting menu tab
$(function () {
  $('header .gnb-menu li a').each(function() {
    var isActive = (function() {
      if(this.href === location.href) {
        return true;
      }
      //on the href of 'toolguide', also shows '가이드'menu button as class'on'.
      else if((location.href.indexOf("toolguide") != (-1) || location.href.indexOf("priorKnow") != (-1)) && this.href.indexOf("guide") != (-1)) {
        return true;
      }
      //on the href of 'diagnose', also shows '진단'menu button as class'on'.
      else if(location.href.indexOf("diagnose") != (-1) && this.href.indexOf("diagnose-main") != (-1)) {
        return true;
      }
      else {
        return false;
      }
    })();
    $(this).parent().toggleClass('active', isActive);
  })
}); 

//header의 알림버튼 눌렀을 때 나오는 창에서 즐겨찾기 삭제한 경우
 $(document).on("click", ".notice-list li div .icon.star", function(e){
	 $(this).parent().parent().remove();
	 $.ajax({//클릭한 북마크 번호를 컨트롤러에 전송하여 삭제
		 			type : "post",
		 			url : "/DWSWS/deleteFavorite",
		 			data : {
		 				"num" :  $(this).parent().parent().find('#bookmark').attr('value'),
		 			},
		 			cache: false,
		 			success : function(){
		 				favotiteNum(); // header의 즐겨찾기 갯수 update
		 			},
		 			error : function(){
		 				alert("즐겨찾기 삭제에 실패했습니다.");
		 			}
		 		});
 });
 
 //header의 알림버튼 놀렀을 때 나오는 창에서 공유버튼 눌렀을 때
 //url을 복사하는 기능
 $(document).on("click",".notice-list li div .icon.link", function(e){
 	$(this).parent().parent().find('#url').attr('type', 'display').select();
 	document.execCommand("Copy");
 	$(this).parent().parent().find('#url').attr('type', 'hidden');
	alert("URL이 성공적으로 복사되었습니다.");
 } )
