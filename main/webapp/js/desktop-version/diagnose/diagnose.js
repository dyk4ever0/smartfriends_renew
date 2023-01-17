/**
 * returns total score from user_ranking table so that can use 'rankingActivationResult' function
 */
function callTotalscoreFromRankingTable() {
  var result = null;
  $.ajax({
    type: "get",
    url: "/DWSWS/getTotalscore",
    async: false,
    cache: false,

    success: function(data) {
      console.log("return currentTotalscore : ");
      console.log(data);
      result = data;
    },
    error: function(request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    }
  });
  return result;
}

//need to add async annotation to functions need.
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
      console.log("data : ");
      console.log(data);
      result = data;
      console.log("result : ");
      console.log(result);
    },
    error: function(request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    }
  });
  return result;
}

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
      console.log("data : ");
      console.log(data);
      result = data;
      console.log("result : ");
      console.log(result);
    },
    error: function(request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    }
  });
  return result;
}

function callSuggestionList() {
  var result = null;

  $.ajax({
    async: false,
    type: "get",
    url: "/DWSWS/getSuggestionList",
    cache: false,
    
    success: function(data) {
      console.log("suggestion guide function on diagnose called");
      result = data;
    },
    error: function(request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    }
  });
  
  return result;
}

function callMainpageDataList() {
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

function callSessionData() {
  var result = null;
  $.ajax({
    type: "get",
    url : "/DWSWS/callSessionData",
    async: false,
    cache: false,
    
    success: function(data) {
      console.log(data);
      result = data;
      console.log(result);
    },
    error: function(request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    }
  });
  return result;
}

function callQuestionList(toolname) {
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
      console.log(data);
      result = data;
      console.log(result);
    },
    error: function (request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    },
  });
  return result;
}

function saveTestResult(event) {
  var compareData = callCompareData();
  var correctionList = ansResultCheck(compareData);
  //check whether user submitted nullable answer
  if(correctionList == false) {
    return ;
  }
  var mynewscore = calculateNewScore(correctionList);
  var rankingActivationFlag = rankingActivationResult(mycurrentTotalscore, mycurrentscore, mynewscore);
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
    }
  })
}

function calculateNewScore(correctionList) {
  var mynewscore = newScoreCalculation(correctionList);

  return mynewscore;
}

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
      console.log(data);
      result = data;
    },
    error: function (request, error) {
      console.log(request, error);
      alert("서버에 접속하는 동안 오류가 발생했습니다.");
    },
  })
  return result;
}

function newScoreCalculation(correctionList) {
  var total = 0; var correct = 0;

  for(var i = 0; i < correctionList.length; i++) {
    total++;
    if(correctionList[i] == 1) correct++;
  }

  var calculatedResult = correct / total;

  console.log("parseint num : ")
  console.log(parseInt(calculatedResult * 100))
  return parseInt(calculatedResult * 100);
}

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

function rankingActivationResult(mycurrentTotalscore, mycurrentscore, mynewscore) {
  console.log("mycurrentscore in activation func : " + mycurrentscore);
  console.log("mynewscore in activation func : " + mynewscore);
  console.log("mycurrentTotalscore in activation func : " + mycurrentTotalscore);
  if(mycurrentTotalscore == (-1)) return "add new rank";
  else if(mycurrentscore == mynewscore) return "no activation";
  else if(mycurrentscore < mynewscore) return "between rank plus";
  else if(mycurrentscore > mynewscore) return "between rank minus";
  else return "no case exist";
}