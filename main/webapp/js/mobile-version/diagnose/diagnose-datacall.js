/**
 * calls data needs to append on diagnose-dashboard page
 * @author 박승수 (21.01.18)
 */
function callDashboardDataList() {
    var result = null;
  
    $.ajax({
      type: "get",
      url: "/DWSWS/getTestMainPageData",
      async: false,
      cache: false,
      
      success: function(data) {
        result = data;
      },
      error: function(request, error) {
        console.log(request, error);
        alert("서버에 접속하는 동안 오류가 발생했습니다.");
      },
    });
    return result;
}

/**
 * returns total score from user_ranking table so that can use 'rankingFlagGenerator' function
 * @author 박승수 (21.01.18)
 */
function callTotalscoreFromRankingTable() {
    var result = null;
    $.ajax({
      type: "get",
      url: "/DWSWS/getTotalscore",
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
 * calls question list data for diagnose-survey page
 * @author 박승수 (21.01.18)
 */
function callToolQuestionList() {
    var result = null;
  
    $.ajax({
      type: "get",
      url: "/DWSWS/callQuestionList",
      async: false,
      data: {
        "toolname": toolname,
      },
      cache: false,
  
      success: function (data) {
        result = data;
      },
      error: function (request, error) {
        console.log(request, error);
        alert("서버에 접속하는 동안 오류가 발생했습니다.");
      },
    });
    return result;
}

/**
 * sends survey result to server on diagnose-survey.jsp
 * @author 박승수 (21.01.19)
 */
function saveSurveyResult(event) {
  var compareData = callCompareData();
  console.log("compare Data get");
  var correctionList = ansResultCheck(compareData);
  console.log("correctionList data get");
  
  //check whether user submitted nullable answer
  if(correctionList == false) {
    event.preventDefault();
    return false;
  }
  console.log("nullable answer check");

  var mynewscore = newScoreCalculation(correctionList);
  console.log("mynewscore check");
  var rankingActivationFlag = rankingFlagGenerator(mycurrentTotalscore, mycurrentscore, mynewscore);
  console.log("flag check");

  var userBody = {
    "toolname" : toolname,
    "trialnum" : trialnum,
    "mynewscore" : mynewscore,
    "mycurrentscore" : mycurrentscore,
    "ansResult" : correctionList,
    "rankingActivationFlag" : rankingActivationFlag,
    "mycurrentTotalscore" : mycurrentTotalscore,
  }

  $.ajax({
    type: "post",
    url: "/DWSWS/saveTestResult",
    contentType: "application/json",
    data: JSON.stringify(userBody),
    cache: false,

    success: function(data) {
        if(data == "success") {
            alert("저장에 성공했습니다.");
            location.href = "diagnose-result";
        }
        else {
          alert("저장에 실패했습니다.")
        }
    }
  });
  console.log("ajax check");
}

/**
 * calls correct answer list so that can proceed comparement with survey result on diagnose-survey.jsp
 * @author 박승수 (21.01.19)
 */
function callCompareData() {
  var result = null;
  $.ajax({
    type: "get",
    url: "/DWSWS/compareTestDataList",
    async: false,
    data: {
      "toolname" : toolname,
    },
    cache: false,

    success: function(data) {
      result = data;
    },
    error: function (request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다. callCompareData");
    },
  })
  return result;
}

/**
 * compare survey result and compare data.
 * @returns array containing correct/incorrect
 * @author 박승수 (21.01.19)
 */
function ansResultCheck(compareData) {
  var correctionList = [];
  var answerNullable = false;
  for(var i = 0; i < compareData.length; i++) {
    // execute only when user submitted data and compare data from DB exist
    if(ansResult[i] != null && compareData[i].answer != null) {
      if(ansResult[i] == compareData[i].answer) {
        correctionList[i] = 1;
      }
      else if(ansResult[i] != compareData[i].answer) {
        correctionList[i] = 0;
      }
    }
    else {
      alert("모든 답에 응답해주세요.");
      answerNullable = true;
      break;
    }
  }
  if(answerNullable != true) return correctionList;
  else return false;
}

/**
 * calculates submitted survey result into score
 * @author 박승수 (21.01.19)
 */
function newScoreCalculation(correctionList) {
  var total = 0; var correct = 0;

  for(var i = 0; i < correctionList.length; i++) {
    total++;
    if(correctionList[i] == 1) correct++;
  }

  var calculatedResult = correct / total;

  return parseInt(calculatedResult * 100);
}

/**
 * generates ranking table modification act flag value
 * @author 박승수 (21.01.19)
 */
function rankingFlagGenerator(mycurrentTotalscore, mycurrentscore, mynewscore) {
  if(mycurrentTotalscore == (-1)) return "add new rank";
  else if(mycurrentscore == mynewscore) return "no activation";
  else if(mycurrentscore < mynewscore) return "between rank plus";
  else if(mycurrentscore > mynewscore) return "between rank minus";
  else return "no case exist";
}

/**
 * calls most recent survey data
 * @author 박승수 (21.01.20)
 */
function callRecentTestResult(toolname) {
  var result = null;

  $.ajax({
    async: false,
    type: "get",
    url: "/DWSWS/recentTestResult",
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
 * calls # of correct-answered survey on most recent survey result
 * @author 박승수 (21.01.20)
 */
function callCorrectNum(toolname) {
  var result = null;
  
  $.ajax({
    async: false,
    type: "get",
    url: "/DWSWS/getCorrectNum",
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
 * calls # of test offering tools list
 * @author 박승수 (21.01.20)
 */
function callTestOfferingTools() {
  var result = null;

  $.ajax({
    async: false,
    type: "get",
    url: "/DWSWS/testOfferingTools",
    cache: false,

    success: function(data) {
      result = data;
    },
    error: function(request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.")
    }
  });
  return result;
}