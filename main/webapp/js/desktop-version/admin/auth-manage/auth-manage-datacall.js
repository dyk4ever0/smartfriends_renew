/**
 * calls current auth registered user list about auth type as parameter
 * @param authtype authentication type that want to call list about. (1. EDITAUTH or 2. DATAAUTH)
 * @return List<authorizationListDO> Json stringified list of user_auth table data
 */
function callCurrentAuthRegisteredUser(authtype) {
    var result = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/authList",
        async : false,
        cache: false,
        data : {
            "authtype" : authtype
        },

        success: function(data) {
            result = data;
        },
        error: function (request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
          }
    });
    return result;
}

/**
 * issues selected user's new auth about auth type
 * @param List list containing newly added user's data
 * @return String "success" or "fail" about result of insertion
 */
function registerNewAuth(newAddedUsers) {
    console.log(newAddedUsers);
    $.ajax({
        type: "post",
        url: "/DWSWS/newAuthInsert",
        contentType : "application/json",
        data: JSON.stringify(newAddedUsers),
        cache: false,

        success: function(data) {
            if(data == "success") {
                alert("제출에 성공했습니다.");
                appendRegisteredUserData(authtype);
            } else if(data == "fail") {
                alert("데이터를 저장하는 동안 오류가 발생했습니다.");
            }
        },
        error: function (request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
}

/**
 * calls searched result
 * @param username a string that name which want to find including
 * @return List
 */
function searchedUserList(username) {
    var result = null;
    $.ajax({
        type: "get",
        async: false,
        url: "/DWSWS/searchUser",
        data: {
            "username" : $(".search-area").val()
        },
        cache: false,

        success: function(data) {
            result = data;
        },
        error: function (request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
    return result;
}

/**
 * deregister selected user on 'authtype' auth
 * @param authtype which authentication want to delete (1. EDITAUTH, 2. DATAAUTH)
 * @param userindex selected user's index
 * @return String "success" or "fail"
 */
function deregisterSelectedUser(authtype, userindex) {
    var sendData = {
        "authtype" : authtype,
        "newUserList" : [
            {
                "userindex" : userindex
            }
        ]
    }
    $.ajax({
        type: "post",
        url: "/DWSWS/authDelete",
        contentType : "application/json",
        data: JSON.stringify(sendData),
        cache: false,

        success: function(data) {
            if(data == "success") {
                alert("사용자를 삭제하는데 성공했습니다.");
                appendRegisteredUserData(authtype);
            } else if(data == "fail") {
                alert("오류가 발생하여 데이터 저장에 실패했습니다.");
            }
        },
        error: function (request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
}