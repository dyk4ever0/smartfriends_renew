/**
 * returns Suggestion List based on recent test data
 * @param HttpSession uses session data in server, so doesn't need to pass parameter
 */
function callSuggestionList() {
    var result = null;
    console.log("suggestion started");
  
    $.ajax({
      async: false,
      type: "get",
      url: "/DWSWS/getSuggestionList",
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
 * returns more study list based on recent test data
 * @param HttpSession uses session data in server, so doesn't need to pass parameter
 */
function callMoreStudyList() {
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
 * returns sessionStorage data from server so that can use session data which isn't able directly on frontend
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
 * calls Universal info for myinfo page
 * @throws error error message come from ajax
 */
function getUniversalMyinfo() {
    var result = null;
    var userdata = callSessionData();
    $.ajax({
        type: "get",
        url: "/DWSWS/getUniversalMypageInfo",
        async: false,
        cache: false,

        success: function(data) {
          //get imgname from other function
          var userSkillGrade = getImgnameByUserskill(data.userskill);
          //change text by returned data
          $('.inner.inner-info .data-img').attr('src', "images/lev/" + data.userskill + ".png");
          $('.inner.inner-info .imgname').text(userSkillGrade.imgtext);
          $("#username").text(userdata.username);
          $("#userskill").text("\'" + userSkillGrade.skill + "\'");
          $("#userranking").text("\'" + data.userrank + "등\'");
          $("#allpeoplenum").text(data.numOfAllPeople);
          $("#attendentpeoplenum").text(data.participatedPeople);
          $(".all-progress .progress .progress-text").find("li[value=\"" + data.userskill + "\"]").addClass("on");
        },
        error: function(request, error) {
            console.log(request, error);
            
            noneTestDataError("진단 결과가 없습니다"
                              , "[내 정보] 페이지에 접근할 수 없습니다."
                              , "진단 결과가 저장되어있지 않습니다. <br> 최소 1개의 진단 진행 후 다시 시도해 주시기 바랍니다."
                              , "diagnose-main"
                              , "진단으로 이동"
                              , ".contents-wrapper");
        }
    });
    return result;
}

/**
 * appending function for showing suggestion list in myinfo page
 * it uses data from other functions so that don't worry about error handling
 * Also uses asynchronized function.
 */
 function showSuggestionData() {
    var suggestionData = callSuggestionList();
    var favoriteList_Data_Container = callFavoriteList();

    var suggestionBookmarkTemplate = 
        "<div class=\"slide-box\">" +
            "<a href=\"{url}\">" +
            "<span class=\"result-icon {toolname}-icon\">{toolname}</span>" +
            "<p class=\"slide-text-top\">{sectionname}</p>" +
            "<p class=\"slide-text-bottom\">{bookmarkname}</p></a>" +
            "<div class=\"btn-wrapper\">" +
                "<button type=\"button\" class=\"icon star\" aria-pressed=\"false\"><span class=\"sr-only\">북마크</span></button>" +
                "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>" +
            "</div>" +
            "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
            "<input type=\"hidden\" id=\"url\" value=\"{url}\" />" +
        "</div>";
    var suggestionSlideTemplate = 
        "<div class=\"slide-guide\" id=\"suggestion-page-{slideCount}\">" +
        "</div>";

    var numberOfSlideElements = 0;
    var slideCount = 1;
//priorKnow?tool=BearDoc&start=187
    for(var i = 0; i < suggestionData.length; i++) {
      var fowardAppendUrl = (suggestionData[i].category == "도구 가이드") ? localStorage.toolGuide.split('/DWSWS/')[1] : localStorage.priorKnow.split('/DWSWS/')[1];
      var temp_suggestion = null;
        if(numberOfSlideElements < 5) {
          if(42<=suggestionData[i].secindex<45) { // BearDoc ( means, there is part1 and part2 )
            temp_suggestion =
            $("#suggestion-page-" + slideCount).append(
                suggestionBookmarkTemplate
                .replace(/{toolname}/gi, suggestionData[i].toolname)
                .replace("{sectionname}", suggestionData[i].secname)
                .replace("{bookmarkname}", suggestionData[i].bmkcomment)
                .replace(/{url}/gi, fowardAppendUrl +suggestionData[i].toolname+ "&start=" + suggestionData[i].bmktime + "&second=true")
                .replace("{bmkidx}", suggestionData[i].bmkindex)
                .replace("{url_share}", localStorage.toolGuide +suggestionData[i].toolname+ "&start=" + suggestionData[i].bmktime + "&second=true")
            );
          } else { // non - BearDoc (means, only single video exist)
            temp_suggestion =
            $("#suggestion-page-" + slideCount).append(
                suggestionBookmarkTemplate
                .replace(/{toolname}/gi, suggestionData[i].toolname)
                .replace("{sectionname}", suggestionData[i].secname)
                .replace("{bookmarkname}", suggestionData[i].bmkcomment)
                .replace(/{url}/gi, fowardAppendUrl +suggestionData[i].toolname+ "&start=" + suggestionData[i].bmktime)
                .replace("{bmkidx}", suggestionData[i].bmkindex)
                .replace("{url_share}", localStorage.toolGuide +suggestionData[i].toolname+ "&start=" + suggestionData[i].bmktime)
            );
          }
            favoriteList_Data_Container.forEach(function(element) {
                if(element.bookmark == suggestionData[i].bmkindex) {
                  temp_suggestion.find("input[value=" + element.bookmark + "]").parent().find('.icon.star').addClass('on');
                }
              });
            numberOfSlideElements++;
        }
        else {
          slideCount++;
            $(".slide-area").append(
                suggestionSlideTemplate
                .replace("{slideCount}", slideCount)
            );
            i--;
            numberOfSlideElements = 0;
        }
    }
}

/**
 * appending function for showing more studying list in myinfo page
 * it uses data from other functions so that don't worry about error handling
 * Also uses asynchronized function.
 */
 function showMoreStudyData() {
  var moreStudyData = callMoreStudyList();
  var favoriteList_Data_Container = callFavoriteList();

  var moreStudyBookmarkTemplate = 
    "<li>" +
      "<a href=\"{url}\"><span class=\"result-icon {toolname}-icon\">{toolname}</span>{bmkcomment}</a>" +
      "<div class=\"btn-wrapper\">" +
        "<button type=\"button\" class=\"icon star\" aria-pressed=\"false\"><span class=\"sr-only\">북마크</span></button>" +
        "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>" +
      "</div>" +
      "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
      "<input type=\"hidden\" id=\"url\" value=\"{url_share}\" />" +
    "</li>";
  var moreStudyOneSideTemplate = 
    "<div class=\"right-slide-box\" id=\"morestudy-{sidenum}\">" +
    "<ul id=\"sidelist-{sidenum}\"></ul>" +
    "</div>";
  var oneSlideTemplate = 
    "<div id=\"oneslide-{slidenum}\"></div>";

  var numberOfOnesideElements = 0;
  var sidecount = 1;
  var slideCount = 1;

  for(var i = 0; i < moreStudyData.length; i++) {
    var fowardAppendUrl = (moreStudyData[i].category == "도구 가이드") ? localStorage.toolGuide.split('/DWSWS/')[1] : localStorage.priorKnow.split('/DWSWS/')[1];
    var temp_morestudy = null;
    if(numberOfOnesideElements < 4) {
      if(42<=moreStudyData[i].secindex<45) {
        temp_morestudy =
        $("#sidelist-" + sidecount).append(
          moreStudyBookmarkTemplate
            .replace(/{toolname}/gi, moreStudyData[i].toolname)
            .replace("{bmkcomment}", moreStudyData[i].bmkcomment)
            .replace("{url}", fowardAppendUrl +moreStudyData[i].toolname+ "&start=" + moreStudyData[i].bmktime + "&second=true")
            .replace("{bmkidx}", moreStudyData[i].bmkindex)
            .replace("{url_share}", localStorage.toolGuide +moreStudyData[i].toolname+ "&start=" + moreStudyData[i].bmktime)
        );
      } else {
        temp_morestudy =
        $("#sidelist-" + sidecount).append(
          moreStudyBookmarkTemplate
            .replace(/{toolname}/gi, moreStudyData[i].toolname)
            .replace("{bmkcomment}", moreStudyData[i].bmkcomment)
            .replace("{url}", fowardAppendUrl + moreStudyData[i].toolname+ "&start=" + moreStudyData[i].bmktime)
            .replace("{bmkidx}", moreStudyData[i].bmkindex)
            .replace("{url_share}", localStorage.toolGuide +moreStudyData[i].toolname+ "&start=" + moreStudyData[i].bmktime)
        );
      }

      favoriteList_Data_Container.forEach(function(element) {
          if(element.bookmark == moreStudyData[i].bmkindex) {
            temp_morestudy.find("input[value=" + element.bookmark + "]").parent().find('.icon.star').addClass('on');
          }
        });
      numberOfOnesideElements++;
    }
    else if(numberOfOnesideElements >= 4) {
      if(sidecount % 2 == 0) {
        $(".right-slide").append(
          oneSlideTemplate
          .replace(/{slidenum}/gi, ++slideCount)
        );
      }

      $("#oneslide-" + slideCount).append(
        moreStudyOneSideTemplate
          .replace(/{sidenum}/gi, ++sidecount)
      );
      i--;
      numberOfOnesideElements = 0;
    }
  }
}

/**
 * returns test offer tool list
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

 function showToolListForTooltabs() {
  var testOfferTools = getTestOfferTools();
  
  var testOfferTabTemplate = 
    "<li class=\"tab-link\" data-tab=\"tab-{num}\">{toolname}</li>";
  
  for(var i = 0; i < testOfferTools.length; i++) {
    $("#tooltab").append(
      testOfferTabTemplate
      .replace("{num}", parseInt(i))
      .replace("{toolname}", testOfferTools[i])
    );
  }
  //append current class to first appended tab
  $('#tooltab').find("li[data-tab=\"tab-0\"]").addClass('current');
  //append more study tab component
  $('#tooltab').append(
    "<li class=\"tab-link\" data-tab=\"tab-all\">되짚어보기</li>" // need to adjust how to show tab-all for first
  );
}

/**
 * returns text by user skill
 * @param {String} userskill user skill get from universal info function
 * @author 박승수 (21.01.25)
 */
function getImgnameByUserskill(userSkill) {
  console.log("returned userskill : " + userSkill);
  var result = {};

  switch(userSkill) {
    case "매우 높은" :
      result = {
        imgtext: "미래에서 오신 스마트워커이신가요? 완벽해요!",
        skill: "우주 고수"
      };
      break;
    case "높은" :
      result = {
        imgtext: "당신은 스마트 워커계의 인-싸!",
        skill: "전도사"
      };
      break;
    case "보통" :
      result = {
        imgtext: "오~! 조금만 노력하면 당신도 스마트워커!",
        skill: "숙련자"
      };
      break;
    case "낮은" :
      result = {
        imgtext: "헉! 배울게 이렇게나 더 남았어요~!",
        skill: "견습생"
      };
      break;
    case "매우 낮은" :
      result = {
        imgtext: "삐빅- 지금부터 어서 가이드를 보러 갑시다!",
        skill: "하얀종이"
      };
      break;
  };

  return result;
}