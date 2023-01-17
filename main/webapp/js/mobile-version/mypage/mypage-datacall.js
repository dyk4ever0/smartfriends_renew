/**
 * calls session storage data
 * @author 박승수 (21.01.21)
 */
function callSessionData() {
    var result = null;
    $.ajax({
        type: "get",
        url : "/DWSWS/callSessionData",
        async: false,
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
 * calls universal user diagnose result on mypage.jsp
 * @author 박승수 (21.01.21)
 */
function callUniversalUserinfo() {
    var result = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/getUniversalMypageInfo",
        async: false,
        cache: false,

        success: function(data) {
            result = data;
        },
        //if there is error, append error message component
        error: function(request, error) {
            console.log(request, error);
            appendErrorMessage("No Data"
                                ,"[내 정보] 페이지에 접근할 수 없습니다."
                                ,"진단 결과가 저장되어있지 않습니다. <br> 최소 1개의 진단을 진행 후 다시 시도해 주시기 바랍니다."
                                ,"diagnose-main"
                                ,"진단으로 이동"
                                ,".contents-wrapper"
                                ,true);
        }
    });
    return result;
}

/**
 * calls correct answered contents data
 * @author 박승수 (21.01.22)
 */
function callCorrectAnswerSuggestion() {
    var result = null;
    
      $.ajax({
        async: false,
        type: "get",
        url: "/DWSWS/getMoreStudy",
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
 * calls accumulated survey data by toolname
 * @param {String} toolname tool's name want to call
 * @param {int} limitation # of survey results want to call
 */
function callSurveyHistory(toolname, limitation) {
  var result = null;

  $.ajax({
    type: "get",
    url: "/DWSWS/getDataOfAnalysisByToolForTestData",
    async : false,
    cache: false,
    data: {
      "toolname" : toolname,
      "limitation" : limitation,
    },

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
 * calls section score that connected to section table based on recent survey result.
 * @param {String} toolname 
 */
function getSectionalScore(toolname) {
  var result = null;

  $.ajax({
    type: "get",
    url: "/DWSWS/getDataOfAnalysisByToolForGraph",
    async : false,
    data: {
      "toolname" : toolname
    },
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
 * calls survey offering tools
 * @author 박승수 (21.01.25)
 */
function getTestOfferTools() {
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