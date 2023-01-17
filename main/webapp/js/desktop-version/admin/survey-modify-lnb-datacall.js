/**
 * calls survey offering tools list for appending survey-mng-lnb
 * @author 박승수 (21.02.10)
 */
function callSurveyOfferingTools() {
        var result = [];
      
        $.ajax({
          async: false,
          type: "get",
          url: "/DWSWS/getTestOfferTools",
          cache: false,
      
          success: function(data) {
              result = data;
          },
          error: function(request, error) {
              console.log(request, error);
              alert("서버에 접속하는 동안 오류가 발생했습니다.");
          }
        });
        return result;
}

/**
 * delete survey offering tools
 * @author 박승수 (21.02.15)
 */
function deleteSurveyOfferingTools(toolname, targetComponent) {
  $.ajax({
    async: false,
    type: "post",
    url: "/DWSWS/deleteSurvey",
    cache: false,
    data: {
      "toolname" : toolname
    },

    success: function(data) {
      if(data == true) {
        targetComponent.remove();
        alert(toolname + " 이 진단목록에서 삭제되었습니다.");
      } else {
        alert(toolname + " 을 삭제하는데 실패했습니다.");
      }
    },
    error : function(request, error) {
      console.log(request, error);
      alert("진단문항 삭제 도중 서버 오류가 발생했습니다.");
    }
  });
  location.reload();
}

/**
 * add survey offering tools
 * @author 박승수 (21.02.15)
 */
function addSurveyOfferingTools(targetComponent) {
  var toolname = targetComponent.find("#newSurvey").val();

  // non-input or white space are prohibited for naming new survey due to the sql qurey problem.
  if(targetComponent.find("#newSurvey").val() === null || targetComponent.find("#newSurvey").val().split(" ").length > 1) {
    alert("공백 없는 이름으로 정해주세요.");
    return ;
  }

  //send request to server
  $.ajax({
    async: false,
    type: "post",
    url: "/DWSWS/createNewSurvey",
    cache: false,
    data: {
      "toolname" : toolname
    },

    success: function(data) {
      if(data == true) {
        //newly append lnb list
        $("aside .link-menu li").remove();
        appendSurveyOfferingTools();
        alert(toolname + " 이 진단목록에 추가되었습니다.");
      } else {
        alert(toolname + " 을 추가하는데 실패했습니다.")
      }
    },
    error: function(request, error) {
      console.log(request, error);
      alert("진단문항 추가 도중 서버 오류가 발생했습니다.");
    }
  });
}