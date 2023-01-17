/**
 * ask user to contact directly to service team when icon chat is clicked.
 * @author 박승수 (21.01.15)
 */
$(function () {
  $(document).on("click", "header .util-menu .icon.chat", function (event) {
    alert(
      "궁금한 사항은 idsTrust IT자산관리팀으로 문의부탁드립니다.\n02-550-8088 "
    );
  });
});

/**
 * calls session data for showing user data on header-lnb
 * @author 박승수 (21.01.15)
 */
function appendUserInfo() {
  var result = null;
  $.ajax({
    type: "get",
    url: "/DWSWS/callSessionData",
    async: false,
    cache: false,

    success: function (data) {
      $('header .info .affiliation').text(data.usercompanyname + "/" + data.userorgname);
      $('header .info .name').text(data.username);
      $('header .portrait img').attr("src", "http://bearworld.co.kr" + data.userimage);
    },
    error: function (request, error) {
      console.log(request, error);
    },
  })
  return result;
}

/**
 * For calling badge
 * @author 김예린 (21.01.18)
 */
 function favotiteNum(){
 	$.ajax({
	type : "post",
 	url : "/DWSWS/favoriteNum",
    cache: false,
	success : function(data){
		$('.badge').html(data);
		},
	error : function(request, error){
		console.log(request, error);
	}
});
 }

favotiteNum();

/**
 * header의 알림버튼 눌렀을 때 나오는 창에서 즐겨찾기 삭제한 경우
 * @author 김예린 (21.01.18)
 */
 $(document).on("click", '#noticeLayer .btn-wrapper .circle.yellow', function(e){
	 //즐겨찾기 목록에서 제거
	$(this).parent().parent().remove();
	//도구가이드페이지에서도 즐겨찾기 해제 적용
    $('.icon.star.on').each(function(index, starComponent) {
   	if($(starComponent).parent().parent().find('#bmk').attr("value") == $(e.target).parent().parent().find('#bookmark').attr("value")) {
		$(starComponent).removeClass('on');
    }
});
	 $.ajax({//클릭한 북마크 번호 전송하여 삭제
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
