/**
 * returns tool tab data with toolname
 * @param toolname the name of tool want to get data about
 * @param limitation number of recent test result to get
 */
function getTooltabInfo(toolname, limitation) {
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
 * returns section score with 2 category - average, personal -
 * @param toolname tool's name want to get section score
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
   * appending function for showing tool tabs in myinfo page
   * it uses data from other functions so that don't worry about error handling
   * Also uses asynchronized function.
   */
   function showTooltabDataList() {
    var trialdataTemplate =
      "<tr id=\"trialnum-{idx}\">" +
        "<td class=\"td-center\">{trialnumOfTestdata}차</td>" +
        "<td>{trialdate}</td>" +
        "<td></td> " +
        "<td class=\"pl-4\">{trialscore}</td>" +
        "<td class=\"pl-7\">{trialskill}</td>" +      
      "</tr>";
    var tooltabTemplate = 
    "<!--  tool start -->" +
                                      "<div id=\"tab-{idx}\" class=\"tab-content\">" + // tooltab start
                                          "<div class=\"tab-inner mb-85\">" +
                                              "<div class=\"columns-wrapper\">" +
  
                                                  "<div class=\"zoom-left\">" + //tool tab left side
                                                      "<div class=\"zoom-left-box\">" +
                                                              "<span><img src=\"publisher-folder/images/logo_{toolname}.jpg\" alt=\"로고이미지\"></span>" +
                                                              "<p>총 {trialnum}회 진단<br>{ranking}등<span> ({numofallpeople}명 중 {numofparticipant}명 응시)</span></p>" +
                                                              "<button type=\"button\" class=\"zoom-btn\" name=\"{idx}\" data-trial = \"{trialnum}\" data-score=\"{currentscore}\" data-toolname=\"{toolname}\">진단시작</button>" +
                                                      "</div>" +
                                                  "</div>" +
  
                                                  "<div class=\"zoom-right\">" + // tool tab right side
  
                                                      "<table class=\"table-box\">" +
                                                        "<thead>" +
                                                          "<tr>" +
                                                            "<th class=\"th-title-01\">차수</th>" +
                                                            "<th class=\"th-title-02\">진단날짜</th>" +
                                                            "<th class=\"th-title-03\"></th>" +
                                                            "<th class=\"th-title-04\">점수</th>" +
                                                            "<th class=\"th-title-05\">숙련도</th>" +
                                                          "</tr>" +
                                                        "</thead>" +
                                                        "<tbody id=\"trialinfo-{idx}\">" + //trial info data insertion
                                                        "</tbody>" +
                                                      "</table>" +
  
                                                  "</div>" +
                                              "</div>" +
                                          "</div>" +
                                          
                                          "<!--  항목별 점수 & 점수비교 영역 start-->" +
                                          "<div class=\"tab-inner item-score\">" +
                                                  "<div class=\"columns-wrapper\">" +
                                                      "<div class=\"column\">" +
                                                          "<h3 class=\"title\">항목별 점수</h3>" +
                                                          "<div class=\"graph-box\">" +
                                                              "<div class=\"graph\">" +
                                                                "<div id=\"chart-area-{idx}\"></div>" +
                                                              "</div>" +
                                                          "</div>" +
                                                      "</div>" +
                                                      "<div class=\"column\">" +
                                                          "<h3 class=\"title\">점수 비교</h3>" +
                                                          "<div class=\"graph-box\">" +
                                                              "<div class=\"graph\">" +
                                                              "<div id=\"column-area-{idx}\"></div>" +
                                                              "</div>" +
                                                          "</div>" +
                                                      "</div>" +
                                                  "</div>" +
                                          "</div>" +
                                          "<!--  항목별 점수 & 점수비교 영역 end -->" +
  
                                      "</div>" +
                    "<!--  tool end-->";
    
    var testOfferTools = getTestOfferTools();
  
    for(var i = 0; i < testOfferTools.length; i++) {
      var tooltabInfo = getTooltabInfo(testOfferTools[i], 3);
      var sectionScoreInfo = getSectionalScore(testOfferTools[i]);

      //if recentTestResults = null (there's no trial about i th tool), append error form.
      if(tooltabInfo.recentTestResults == null) {
        //append frame contains error or data analysis.
        $(".tab-area").append(
          "<div id=\"tab-{idx}\" class=\"tab-content\"></div>"
          .replace(/{idx}/gi, i)
        );
        noneTestDataError(testOfferTools[i] + "에 대한<br> 진단 결과가 저장되어있지 않습니다."
                          , "해당 도구의 진단을 진행해야 합니다."
                          , "No Data <br> [도구별 통계]에 접근할 수 없습니다."
                          , "diagnose-main"
                          , "진단으로 이동"
                          , "#tab-" + i);
      }
      //if recentTestResult not have error, then append normal components
      else {
        $(".tab-area").append(
          tooltabTemplate
          .replace(/{idx}/gi, i)
          .replace(/{toolname}/gi, testOfferTools[i])
          .replace(/{trialnum}/gi, tooltabInfo.trialnum)
          .replace("{ranking}", tooltabInfo.ranking)
          .replace("{numofallpeople}", tooltabInfo.NumOfAllPeople)
          .replace("{numofparticipant}", tooltabInfo.participant)
          .replace("{currentscore}", tooltabInfo.recentTestResults[0].trialnum)
        );
        //set the "diagnose start" button event
        $(document).on("click", ".zoom-btn", function() {
          localStorage.setItem("toolname", $(this).attr("data-toolname"));
          localStorage.setItem("trialnum", $(this).attr("data-trial"));
          localStorage.setItem("mycurrentscore", $(this).attr("data-score"));
          localStorage.setItem("mycurrentTotalscore", callTotalscoreFromRankingTable());
          location.href = "diagnose-test";
        });
        for(var j = 0; j < tooltabInfo.recentTestResults.length; j++) {
          $("#trialinfo-" + i).append(
            trialdataTemplate
            .replace("{idx}", j)
            .replace("{trialnumOfTestdata}", tooltabInfo.recentTestResults[j].trialnum)
            .replace("{trialdate}", tooltabInfo.recentTestResults[j].entrydate)
            .replace("{trialscore}", tooltabInfo.recentTestResults[j].totalscore)
            .replace("{trialskill}", tooltabInfo.recentTestResults[j].skill)
          );
        }
        showRadialGraphData(sectionScoreInfo, i);
        showColumnGraphData(sectionScoreInfo, i);
      }
    }
    //append current class to first appended data component
    $(".tab-area").find("#tab-0").addClass('current');
} 

/**
 * shows radial chart data with sectional info by toolname for personal section score
 * @param sectionScoreInfo the sectional data about toolname, comes from its caller function 'showTooltabDataList'
 * @param index the index number that distinguish tool tab
 */
function showRadialGraphData(sectionScoreInfo, index) {
  var chartContainer = document.getElementById('chart-area-'+index);

  var chartData = {
    categories: sectionScoreInfo.personal.sectionComment,
    series: [
      {
      name: '섹션별 점수',
      data: sectionScoreInfo.personal.sectionScore
      }
    ]
  };
  var option = {
    plot: "circle",
    yAxis: {
      min: 0,
      max: 10
    }
  };
  tui.chart.radialChart(chartContainer, chartData, option);
}

/**
 * shows comparement-convenience column chart data with personal and sectional data
 * @param sectionScoreInfo the sectional data about toolname, comes from its caller function 'showTooltabDataList'
 * @param index the index number that distinguish tool tab
 */
function showColumnGraphData(sectionScoreInfo, index) {
  console.log("sectionScoreInfo : ");
  console.log(sectionScoreInfo);
  var chartContainer = document.getElementById('column-area-'+index);

  var chartData = {
    categories: sectionScoreInfo.personal.sectionComment,
    series: [
      {
      name: '개인 점수',
      data: sectionScoreInfo.personal.sectionScore
      },
      {
        name: '평균 점수',
        data: sectionScoreInfo.average.sectionScore
      }
    ]
  };

  var chartOption = {
    yAxis: {
      title : '진단 제공 도구',
      min: 0,
      max: 10
    }
  }
  tui.chart.columnChart(chartContainer, chartData, chartOption);
}