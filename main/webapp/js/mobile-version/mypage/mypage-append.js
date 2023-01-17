/**
 * appends universal mypage info (tooltab/[내 정보])
 * @author 박승수 (21.01.21)
 */
function appendUniversalInfo() {
    var userdata = callSessionData();
    var universaldata = callUniversalUserinfo();

    $('#username').text(userdata.username);
    $('#userskill').text(universaldata.userskill);
    $('#userranking').text(universaldata.userrank + "등");
    $('.progress .progress-text').find("li[value=\"" + universaldata.userskill + "\"]").addClass('on');

    //append attendence number of all members
    $('.tab-contents #total .data-status .range').text("(" + universaldata.numOfAllPeople + "명 중 " + universaldata.participatedPeople + "명 응시)");
}

/**
 * appends correct answered contents data
 * @author 박승수 (21.01.22)
 */
function appendCorrectAnswerSuggestion(toolname, removeClass, appendClass) {
    //remove initial tool button click message
    $('#total #error-message').remove();
    //remove existing appended suggestion items
    $(removeClass).remove();
    var correctData = callCorrectAnswerSuggestion();

    var correctAnswerTemplate = 
        "<li role=\"listitem\">" +
            "<a href=\"{url}\"><span class=\"tag {toolname}\">{toolname}</span>{bmkcomment}</a>" +
            "<div class=\"btn-wrapper\">" +
                "<button type=\"button\" class=\"icon star\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>" +
                "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>" +
            "</div>" +
            "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
            "<input type=\"hidden\" id=\"url\" value=\"{url}\" />" +
        "</li>";

    for(var correctNum = 0; correctNum < correctData.length; correctNum++) {
        //append only clicked tool name
        if(toolname == correctData[correctNum].toolname) {
            //appending suggestion item
            var currentData =
            $(appendClass).append(
                correctAnswerTemplate
                    .replace(/{url}/gi, "toolguide?tool=" + correctData[correctNum].toolname + "&start=" + correctData[correctNum].bmktime)
                    .replace(/{toolname}/gi, correctData[correctNum].toolname)
                    .replace("{bmkcomment}", correctData[correctNum].bmkcomment)
                    .replace("{bmkidx}", correctData[correctNum].bmkindex)
            );

            //star-highlight function
            var favoriteData = callFavoriteList();
            favoriteData.forEach(function(element) {
                if(element.bookmark == correctData[correctNum].bmkindex) {
                    currentData.find("input[value=" + element.bookmark + "]").parent().find('.icon.star').addClass('on');
                    console.log(currentData.find("input[value=" + element.bookmark + "]").parent().find('.icon.star'));
                }
            });
        }
    }
}

/**
 * append survey offer tools tab
 * @author 박승수 (21.01.26)
 */
function appendSurveyToolsTabs() {
    var testOfferTools = getTestOfferTools();
    var testOfferTabTemplate = 
        "<li><a href=\"#tab-{idx}\" id=\"tab-button\" role=\"tab\" aria-controls=\"tab-{idx}\" aria-selected=\"false\">{toolname}</a></li>";
    
    //append survey offer tabs
    for(var tools = 0; tools < testOfferTools.length; tools++) {
        $('.tab-buttons').append(
            testOfferTabTemplate
                .replace(/{idx}/gi, parseInt(tools))
                .replace("{toolname}", testOfferTools[tools])
        );
    }
}

/**
 * append tooltap for survey providing tools
 * @author 박승수 (21.01.25)
 */
function appendSurveyOfferTools() {
    var trialdataTemplate =
        "<tr id = \"trialnum-{idx}\">"+
            "<td>{trialnumOfTestdata}차</td>"+
            "<td>{trialdate}</td>"+
            "<td>{trialscore}</td>"+
            "<td>{trialskill}</td>"+
        "</tr>";

    var tooltabTemplate = 
    "<li id=\"tab-{idx}\" role=\"tabpanel\">"+
        "<div class=\"data-status\">"+
            "<div class=\"visual logo\">"+
                "<img src=\"publisher-folder/mobile/images/logo_{toolname}.jpg\" alt=\"\">"+
            "</div>"+
            "<p class=\"desc\">총 <strong>{trialnum}</strong>회 진단<br><strong>{ranking}</strong>등 <span class=\"range\">({numofallpeople}명 중 {numofparticipant}명 응시)</span></p>"+
            "<table class=\"data-table\">"+ // under here, problem start point
                "<caption class=\"sr-only\">진단 결과 테이블</caption>"+
                "<colgroup>"+
                    "<col style=\"width:20%;\">"+
                    "<col style=\"width:40%;\">"+
                    "<col style=\"width:15%;\">"+
                    "<col style=\"width:25%;\">"+
                "</colgroup>"+
                "<thead>"+
                    "<tr>"+
                        "<th scope=\"row\">차수</th>"+
                        "<th scope=\"row\">진단날짜</th>"+
                        "<th scope=\"row\">점수</th>"+
                        "<th scope=\"row\">숙련도</th>"+
                    "</tr>"+
                "</thead>"+
                "<tbody id=\"trialinfo-{idx}\">"+ // survey history data insertion
                "</tbody>"+
            "</table>"+
            //survey redirect button
            "<p class=\"btn-wrapper\" name=\"{idx}\" data-trial = \"{trialnum}\" data-score=\"{currentscore}\" data-toolname=\"{toolname}\">"+
                "<a id=\"survey-start\" class=\"square-btn\">진단 시작</a>"+
            "</p>"+
        "</div>"+
        "<div class=\"chart-box\">"+
            "<p class=\"caption\">항목별 점수</p>"+
            "<div class=\"chart\">"+
                "<div id=\"chart-area-{idx}\"></div>"+ // radial chart area
            "</div>"+
        "</div>"+
        "<div class=\"chart-box\">"+
            "<p class=\"caption\">점수비교</p>"+
            "<div class=\"chart\">"+
                "<div id=\"column-area-{idx}\"></div>"+ // column chart area
            "</div>"+
        "</div>"+
    "</li>";

    var testOfferTools = getTestOfferTools();
    for(var tools = 0; tools < testOfferTools.length; tools++) {
        //calls required data for survey history and graph which appends on tooltabTemplate
        var surveyTrialHistory = callSurveyHistory(testOfferTools[tools], 3);
        var sectionScore = getSectionalScore(testOfferTools[tools]);

        //if there is no recent test result, then append error message
        if(surveyTrialHistory.recentTestResults == null) {
            //append error message
            $('.tab-contents').append(
                "<li id=\"tab-{idx}\" role=\"tabpanel\"></li>"
                    .replace(/{idx}/gi, tools)
            );
            appendErrorMessage("No Data"
                                , "[도구별 통계]에 접근할 수 없습니다."
                                , testOfferTools[tools] + "에 대한 진단 결과가 저장되어있지 않습니다. <br> 해당 도구의 진단을 진행해야 합니다."
                                , "diagnose-main"
                                , "진단으로 이동"
                                , "#tab-" + tools
                                , true);
        }
        //if there is recent test result, append normal components
        else {
            $('.tab-contents').append(
                tooltabTemplate
                    .replace(/{idx}/gi, tools)
                    .replace(/{toolname}/gi, testOfferTools[tools])
                    .replace(/{trialnum}/gi, surveyTrialHistory.trialnum)
                    .replace("{ranking}", surveyTrialHistory.ranking)
                    .replace("{numofallpeople}", surveyTrialHistory.NumOfAllPeople)
                    .replace("{numofparticipant}", surveyTrialHistory.participant)
                    .replace("{currentscore}", surveyTrialHistory.recentTestResults[0].trialnum)
            );
            //set "survey start" button click event
            $(document).on("click", ".square-btn", function() {
                localStorage.setItem("toolname", $(this).attr("data-toolname"));
                localStorage.setItem("trialnum", $(this).attr("data-trial"));
                localStorage.setItem("mycurrentscore", $(this).attr("data-score"));
                localStorage.setItem("mycurrentTotalscore", callTotalscoreFromRankingTable());
                location.href = "diagnose-test";
            });
            //append survey history data
            for(var history = 0; history < surveyTrialHistory.recentTestResults.length; history++) {
                $("#trialinfo-" + tools).append(
                    trialdataTemplate
                        .replace("{idx}", history)
                        .replace("{trialnumOfTestdata}", surveyTrialHistory.recentTestResults[history].trialnum)
                        .replace("{trialdate}", surveyTrialHistory.recentTestResults[history].entrydate)
                        .replace("{trialscore}", surveyTrialHistory.recentTestResults[history].totalscore)
                        .replace("{trialskill}", surveyTrialHistory.recentTestResults[history].skill)
                );
            }
            //append radial graph data that shows personal sectional score
            appendRadialChart(sectionScore, tools);
            appendColumnChart(sectionScore, tools);
        }
    }
}

/**
 * appends radial chart that shows personal sectional score
 * @param {SectionalScoreInfo} sectionScoreInfo 
 * @param {integer} index
 * @author 박승수 (21.01.26)
 */
function appendRadialChart(sectionScoreInfo, index) {
    //component that chart 'll append
    var chartContainer = document.getElementById('chart-area-' + index);
    //actual data that chart 'll show
    var chartData = {
        categories: sectionScoreInfo.personal.sectionComment,
        series: [
          {
          name: '섹션별 점수',
          data: sectionScoreInfo.personal.sectionScore
          }
        ]
    };
    //chart's option value
    var chartOption = {
        plot: "circle",
        yAxis: {
          min: 0,
          max: 10
        }
    };
    tui.chart.radialChart(chartContainer, chartData, chartOption);
}

/**
 * appends column chart that compares average and personal score
 * @param {SectionalScoreInfo} sectionScoreInfo 
 * @param {integer} index 
 */
function appendColumnChart(sectionScoreInfo, index) {
    //component that chart 'll append
    var chartContainer = document.getElementById('column-area-' + index);
    //actual data that chart 'll show
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
    //chart's option data
    var chartOption = {
        yAxis: {
          title : '진단 제공 도구',
          min: 0,
          max: 10
        }
      }
    tui.chart.columnChart(chartContainer, chartData, chartOption);
}

/**
 * append correct/incorrect answer suggestion
 * @author 박승수 (21.02.04)
 */
function appendSuggestionButton() {
    var correctAnswerShowButtonTemplate = 
    "<span class=\"tag {toolname}\"" +
        "onclick=\"appendSuggestionBlockOnMypage('{toolname}', '.recommend .inner .overflow-box .guide-list .guide-box', '.guide-list');\">{toolname}</span>";

    var incorrectAnswerShowButtonTemplate = 
    "<span class=\"tag {toolname}\"" +
        "onclick=\"appendCorrectAnswerSuggestion('{toolname}', '.board-box .board-list li', '.board-box .board-list');\">{toolname}</span>";
    
    var testOfferTools = getTestOfferTools();

    //append incorrect answer note button
    for(var tools = 0; tools < testOfferTools.length; tools++) {
        $(".recommend .recommend-title").append(
            correctAnswerShowButtonTemplate
                .replace(/{toolname}/gi, testOfferTools[tools])
        );
    }

    //append correct answer note button
    for(var tools = 0; tools < testOfferTools.length; tools++) {
        $(".board-box .recommend-title").append(
            incorrectAnswerShowButtonTemplate
                .replace(/{toolname}/gi, testOfferTools[tools])
        );
    }
}