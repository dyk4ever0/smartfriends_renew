/**
 * appends diagnose-offer tools on dashboard.jsp
 * @author 박승수 (21.01.18)
 */
function appendDiagnoseDashboard() {
    var toolImageTemplate = 
        "<div class=\"status-wrapper column\" role=\"listitem\">" +
            "<div class=\"text-box status-box\">" +
                "<div class=\"visual logo\">" +
                    "<img src=\"publisher-folder/mobile/images/logo_{toolname}.jpg\" alt=\"\">" +
                "</div>" +
                "<p class=\"progress\">" +
                    "<strong>{totalscore}</strong>/100" +
                "</p>" +
                "<p class=\"info\"><strong>{trialnum-text}진단</strong> ({questionnum}문항)</p>" +
            "</div>" +
            "<div class=\"button-box\">" +
                "<a href=\"toolguide?tool={toolname}\" class=\"square-btn border\">가이드 보기</a>" +
                "<a href=\"#\" class=\"square-btn\" name=\"{idx}\" data-trial = \"{trialnum}\" data-score=\"{currentscore}\" data-toolname=\"{toolname}\">진단시작</a>" +
            "</div>" +
        "</div>";
    var data = callDashboardDataList();

    //append dashboard diagnose tool blocks
    for(var blockNum = 0; blockNum < data.length; blockNum++) {
        if(data[blockNum].updatedate == null) {
            $(".columns-wrapper").append(
                toolImageTemplate.replace(/{toolname}/gi, data[blockNum].toolname)
                .replace("{totalscore}", 0)
                .replace("{trialnum-text}", "미")
                .replace("{trialnum}", data[blockNum].trialnum)
                .replace("{questionnum}", data[blockNum].questionnum)
                .replace("{idx}", blockNum)
                .replace("{currentscore}", 0)
            );
            console.log("append none-test done");
        }
        else {
            $(".columns-wrapper").append(
                toolImageTemplate.replace(/{toolname}/gi, data[blockNum].toolname)
                .replace(/{totalscore}/gi, data[blockNum].totalscore)
                .replace("{trialnum-text}", data[blockNum].trialnum + "차")
                .replace("{trialnum}", data[blockNum].trialnum)
                .replace("{questionnum}", data[blockNum].questionnum)
                .replace("{idx}", blockNum)
                .replace("{currentscore}", data[blockNum].totalscore)
            );
            console.log("append exist-test done");
        }
    }
}

//variables uses all around survey.jsp
var toolname = localStorage.getItem("toolname");
var trialnum = localStorage.getItem("trialnum");
var mycurrentscore = localStorage.getItem("mycurrentscore");
var mycurrentTotalscore = localStorage.getItem("mycurrentTotalscore");

/**
 * appends diagnose-survey page question list on survey.jsp
 * @author 박승수 (21.01.18)
 */
function appendToolQuestionList() {
    var questionListTemplate =
        "<li role=\"listitem\">" +
            "<p>{question-content}   </p>" +
            "<div class=\"radio-wrapper\">" +
                "<label>" +
                    "<input type=\"radio\" name=\"{num}\" value=\"True\" title=\"네\">" +
                    "<span class=\"yes\">O</span>" +
                "</label>" +
                "<label>" +
                    "<input type=\"radio\" name=\"{num}\" value=\"False\" title=\"아니오\">" +
                    "<span class=\"no\">X</span>" +
                "</label>" +
            "</div>" +
        "</li>";

    var data = callToolQuestionList(toolname);
    //survey target tool name append
    $(".header-title").text(toolname);
    $(".sub-title").text((parseInt(trialnum) + 1) + "차 진단")

    //actual append question list
    for(var questionNum = 0; questionNum < data.length; questionNum++) {
        $(".question-list").append(
            questionListTemplate
                .replace(/{num}/gi, questionNum + 1)
                .replace("{question-content}", data[questionNum].question)
        );
    }
}

//survey answer containing array
var ansResult = [];

/**
 * handles yes or no button on survey.jsp
 * @author 박승수 (21.01.19)
 */
$(document).on("click", ".question .question-list .radio-wrapper .yes", function(event) {
    ansResult[$(this).parent().find('input[value=\"True\"]').attr('name') - 1] = 1;
    console.log(ansResult);
});
$(document).on("click", ".question .question-list .radio-wrapper .no", function(event) {
    ansResult[$(this).parent().find('input[value=\"False\"]').attr('name') - 1] = 0;
    console.log(ansResult);
});

/**
 * append survey result
 * @author 박승수 (21.01.20)
 */
function appendSurveyResult() {
    var recentData = callRecentTestResult(toolname);
    var correctNum = callCorrectNum(toolname);

    $('#result-score').text(recentData.totalscore);
    $('#correct-num').text(correctNum);
    $('#trial-num').text(recentData.trialnum + "차 진단이 완료되었습니다. ");
}

/**
 * appends test offering tool's blocks on diagnose/result.jsp
 * @author 박승수 (21.01.20)
 */
function appendingResultDashboard() {
    var resultDashboardTemplate = 
        "<div class=\"status-wrapper column\" role=\"listitem\">"+
            "<div class=\"text-box status-box\">"+
                "<div class=\"visual logo\">"+
                    "<img src=\"publisher-folder/mobile/images/logo_{toolname}.jpg\" alt=\"\">"+
                "</div>"+
                "<p class=\"progress\">"+
                    "<strong>{totalscore}</strong>/100"+
                "</p>"+
                "<p class=\"info\"><strong>{trialnum-text} 진단</strong> ({questionnum}문항)</p>"+
            "</div>"+
            "<div class=\"button-box\">"+
                "<a href=\"{url}\" class=\"square-btn border\">가이드 보기</a>"+
                "<a id=\"diagnose-access\" class=\"square-btn\" name=\"{idx}\" data-trial = \"{trialnum}\" data-score=\"{currentscore}\" data-toolname=\"{toolname}\" style=\"cursor:pointer\">진단시작</a>" +
            "</div>"+
        "</div>";

    var data = callDashboardDataList();

    //appends test offering tools block into result.jsp
    for(var toolNum = 0; toolNum < data.length; toolNum++) {
        //if there's no initial trial info, append none-trial format
        if(data[toolNum].updatedate == null) {
            $('.qna-status .status-list').append(
                resultDashboardTemplate
                    .replace(/{toolname}/gi, data[toolNum].toolname)
                    .replace("{totalscore}", 0)
                    .replace("{trialnum-text}", "미")
                    .replace("{questionnum}", data[toolNum].questionnum)
                    .replace("{idx}", toolNum)
                    .replace("{currentscore}", 0)
                    .replace("{url}", "toolguide?tool=" + data[toolNum].toolname)
                    .replace("{trialnum}", data[toolNum].trialnum)
            );
        }
        //if there exists at least one trial info, append regular trial format
        else {
            $('.qna-status .status-list').append(
                resultDashboardTemplate
                    .replace(/{toolname}/gi, data[toolNum].toolname)
                    .replace("{totalscore}", data[toolNum].totalscore)
                    .replace("{trialnum-text}", data[toolNum].trialnum + "차")
                    .replace("{questionnum}", data[toolNum].questionnum)
                    .replace("{idx}", toolNum)
                    .replace("{currentscore}", data[toolNum])
                    .replace("{trialnum}", data[toolNum].trialnum)
                    .replace("{url}", "toolguide?tool=" + data[toolNum].toolname)
            );
        }
    }
}