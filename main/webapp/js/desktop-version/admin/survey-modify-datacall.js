/**
 * calls survey question list data with toolname
 * @author 박승수 (21.02.17)
 */
function callSurveyQuestionData(toolname) {
    var result = null;

    $.ajax({
        async: false,
        cache: false,
        type: "get",
        url: "/DWSWS/callQuestionListForManagement",
        data: {
            "toolname" : toolname
        },

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("진단문항을 불러오는 도중, 서버 문제가 발생했습니다.");
        }
    });
    return result;
}

/**
 * calls section and bookmark related to given toolname
 * @author 박승수 (21.02.17)
 */
function callSelectionBoxDataForSurvey(toolname) {
    var result = null;

    $.ajax({
        async: false,
        cache: false,
        type: "get",
        url: "/DWSWS/callSelectionBoxDataByToolname",
        data: {
            "toolname" : toolname
        },

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("컨텐츠 정보를 불러오는 도중, 서버 문제가 발생했습니다.");
        }
    });
    return result;
}

/**
 * send update request of survey questions to server
 * @author 박승수 (21.02.18)
 */
function saveNewSurveyQuestions(updateQuestionList, toolname) {
    var sendingData = {
        "toolname": toolname,
        "inputList": updateQuestionList
    };
    $.ajax({
        async: false,
        cache: false,
        contentType: "application/json",
        type: "post",
        url: "/DWSWS/applyModificationOnSurvey",
        data: JSON.stringify(sendingData),

        success: function(data) {
            if(data == true) {
                alert(toolname + " 의 문항 수정이 완료되었습니다.");
            } else {
                alert(toolname + "의 문항 수정 중 오류가 발생하였습니다.")
            }
        },
        error: function(request, error) {
            console.log(request, error);
            alert("진단문항 수정 도중 오류가 발생하였습니다.");
        }
    });
}

/**
 * returns tool's index by given toolname
 * @param {int} toolname 
 * @author 박승수 (21.02.19)
 */
function callToolidxWithToolnameFromSurveytable(toolname) {
    var result = null;

    $.ajax({
        async: false,
        cache: false,
        type: "get",
        url: "/DWSWS/callToolidxFromSurveytable",
        data: {
            "toolname" : toolname
        },

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("진단 정보를 가져오는 중, 서버 오류가 발생했습니다.");
        }
    });
    return result;
}