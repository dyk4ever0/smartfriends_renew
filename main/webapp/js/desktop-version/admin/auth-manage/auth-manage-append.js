/**
 * appends registered auth users into page.
 */
function appendRegisteredUserData(authtype) {
    var registeredUserTemplate = 
        "<li id={index}>" +
            "<div class=\"editor-info\">" +
                "<div class=\"editor-img\">" +
                    "<img src=\"{userimage}\" alt=\"\">" +
                "</div>" +
                "<div class=\"editor-name\">" +
                    "<p class=\"member-name\">{username}</p>" +
                    "<p class=\"member-info\">{orgname}<span>|</span>{companyname}</p>" +
                "</div>" +
                "<button type=\"button\" class=\"simple\" data-userindex=\"{userindex}\">삭제</button>" +
            "</div>" +
        "</li>";
    $("#register-auth-user li").remove();
    
    var registeredUserData = callCurrentAuthRegisteredUser(authtype);

    for(var index = 0; index < registeredUserData.length; index++) {
        var newlyAddedComponent =
        $("#register-auth-user").append(
            registeredUserTemplate
                .replace("{index}", index)
                .replace("{userimage}", "http://bearworld.co.kr" + registeredUserData[index].avatar)
                .replace("{username}", registeredUserData[index].username)
                .replace("{orgname}", registeredUserData[index].orgname)
                .replace("{companyname}", registeredUserData[index].companyname)
                .replace("{userindex}", registeredUserData[index].userindex)
        )
        newlyAddedComponent.find(".simple").click(function() {
            deregisterSelectedUser(authtype, $(this).attr("data-userindex"));
            $(this).parent().parent().remove();
        })
    }
}

/**
 * append searched result user data into page
 */
function appendSearchedUserList(searchResult) {
    var searchedUserTemplate = 
        "<li value=\"{idx}\" data-userindex=\"{userindex}\"><div><span class=\"search-list-name\" data-username=\"{username}\">{username}</span>" + 
        "<span class=\"search-department\" data-orgname=\"{orgname}\" data-companyname=\"{companyname}\">" +
        "{orgname} | {companyname}</span></div></li>";

    $(".search-list #search-result li").remove();

    for(var index = 0; index < searchResult.length; index++) {
        var newlyAddedComponent =
            $("#search-result").append(
                searchedUserTemplate
                    .replace("{idx}", index)
                    .replace("{userindex}", searchResult[index].userindex)
                    .replace(/{username}/gi, searchResult[index].username)
                    .replace(/{orgname}/gi, searchResult[index].orgname)
                    .replace(/{companyname}/gi, searchResult[index].companyname)
            )
        
        //append onclick event to every single 'li' elements 
        newlyAddedComponent.find("li[value=" + index + "]").click(function(event) {
            appendSelectedUserInfo({"idx": $(this).attr("value"),
                                    "userindex": $(this).attr("data-userindex"),
                                    "username": $(this).find(".search-list-name").attr("data-username")});
        })
    }
}

/**
 * append selected searched user name into search box of page
 */
function appendSelectedUserInfo(userinfo) {
    var selectedUserTemplate = 
        "<span value=\"{idx}\" data-userindex=\"{userindex}\">{username}<button type=\"button\" class=\"icon delete\"></button></span>";
    var newlyAddedComponent =
        $(".search-name-ok").append(
        selectedUserTemplate
            .replace("{idx}", userinfo.idx)
            .replace("{username}", userinfo.username)
            .replace("{userindex}", userinfo.userindex)
    );
    $(".search-name-ok").fadeIn();

    newlyAddedComponent.find(".delete").click(function(event) {
        $(this).parent().remove();
    })
}