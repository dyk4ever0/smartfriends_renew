let urlSeparator = "DWSWS/";

/**
 * handles authorization check for accessing admin pages (analysis & contents modification)
 * @author 박승수 (21.01.18)
 */
function userAuthCheck() {
    var calledResult = userAuthDataCall();
    try {
        if(location.href.split(urlSeparator)[1] == "analysis" && calledResult.dataauth == 1) {
            console.log("analysis authorized");
            return true;
        }
        else if(location.href.split(urlSeparator)[1] == "contents" && calledResult.editauth == 1) {
            console.log("contents authorized");
            return true;
        }
        else if(location.href.split(urlSeparator)[1] == "authManagement" && calledResult.editauth == 1) {
            console.log("authentication authorized");
            return true;
        }
        else if(location.href.split(urlSeparator)[1] == "survey-modify" && calledResult.editauth == 1) {
            console.log("survey-modify authorized");
            return true;
        }
        else {
            console.log("else authorized");
            return false;
        }
    } catch(Exception) {
        console.log("exception authorized");
        return false;
    }
}

 /**
 * returns user's auth flag value for authorizing to access to analysis & contents modification
 * @author 박승수 (21.01.18)
 */
function userAuthDataCall() {
    var result = null;
    $.ajax({
        type: "get",
        url: "/DWSWS/authCheck",
        async: false,

        success: function(data) {
            console.log(data);
            result = JSON.parse(data);
        },
        error: function (request, error) {
            console.log(request, error);
            alert("서버 통신 중 오류가 발생했습니다.");
        },
    });
    return result;
}

/**
 * append error page when doesn't have auth
 * @author 박승수 (21.01.18)
 */
function userAuthAppend(appendComponent) {
    noneTestDataError("Unauthorized"
                        , "[통계 페이지]에 접근할 수 없습니다."
                        , "현재 사용자는 해당 페이지의 접근 권한이 없습니다. <br> 관리자에게 접근권한을 요청해야합니다."
                        , "main"
                        , "메인으로 이동"
                        , appendComponent);
}