/**
 * returns company list registered in User_info table
 * @returns company list in daewoong
 */
function getCompanyNameList() {
    var result = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/getCompanynameList",
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
 * returns org list by company name registered in User_info table
 * @returns org list in each company
 */
function getOrgNameList(companyname) {
    var result = null;

    $.ajax({
        type:"get",
        url: "/DWSWS/getOrgnameList",
        async: false,
        cache: false,
        data: {
            "companyname" : companyname
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
 * returns organization's skill grade and ranking
 * this function is used for org tab in analysis page
 * @returns selected org's skill and ranking
 */
function getOrgRanking(companyname, orgname) {
    var result = null;
    var dataFormat = (function() {
        if(orgname != null) return {"companyname" : companyname, "orgname" : orgname};
        else return {"companyname" : companyname};
    })();

    $.ajax({
        type: "get",
        url: "/DWSWS/getRanking",
        async: false,
        data: dataFormat,
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
 * returns org's people data (attended and all number)
 * @returns selected org people's attended number and all number
 */
function getOrgPeopleData(companyname, orgname) {
    var result = null;
    var dataFormat = (function() {
        if(orgname != null) return {"companyname" : companyname, "orgname" : orgname};
        else return {"companyname" : companyname};
    })();

    $.ajax({
        type: "get",
        url: "/DWSWS/getPeopleData",
        async: false,
        data: dataFormat,
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
 * returns org's pie chart data 
 * @returns pie chart reequired data
 */
function getOrgDataForPieChart(companyname, orgname) {
    var result = null;
    var dataFormat = (function() {
        if(orgname != null) return {"companyname" : companyname, "orgname" : orgname};
        else return {"companyname" : companyname};
    })();

    $.ajax({
        type: "get",
        url: "/DWSWS/getDataForPieChart",
        async: false,
        data: dataFormat,
        cache: false,

        success: function(data) {
            result = data
        },
        error: function(request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
    return result;
}

/**
 * returns org's bullet chart data 
 * @returns bullet chart reequired data
 */
function getOrgDataForBulletChart(companyname, orgname) {
    var result = null;
    var dataFormat = (function(){
        if(orgname != null) return {"companyname" : companyname, "orgname" : orgname};
        else return {"companyname" : companyname};
    })();

    $.ajax({
        type: "get",
        url: "/DWSWS/getDataForBulletChart",
        async: false,
        data: dataFormat,
        cache: false,

        success: function(data) {
            result = data
        },
        error: function(request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
    return result;
}

/**
 * returns non crew data
 * This returns object contains # of people by skill level and total average score by survey
 * @author 박승수(21.02.08)
 */
function getNoneCrewData() {
    var noneCrewSkill = null;
    var noneCrewScore = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/getNotCrewSkillNumber",
        async: false,
        cache: false,

        success: function(data) {
            noneCrewSkill = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });

    $.ajax({
        type: "get",
        url: "/DWSWS/getcrewscoreByTools",
        async: false,
        cache: false,
        data: {
            crewflag: "notcrew"
        },

        success: function(data) {
            noneCrewScore = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
    var noneCrewResult = {"notCrewSkill": noneCrewSkill,
                         "notCrewScore": noneCrewScore};
    return noneCrewResult;
}

/**
 * returns Crew data
 * This returns object contains # of people by skill level and total average score by survey
 * @author 
 */
function getCrewData() {
    var CrewSkill = null;
    var CrewScore = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/getCrewSkillNumber",
        async: false,
        cache: false,

        success: function(data) {
            CrewSkill = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });

    $.ajax({
        type: "get",
        url: "/DWSWS/getcrewscoreByTools",
        async: false,
        cache: false,
        data: {
            crewflag: "crew"
        },

        success: function(data) {
            console.log(data);
            CrewScore = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("서버에 접속하는 동안 오류가 발생했습니다.");
        }
    });
    var CrewResult = {"CrewSkill": CrewSkill,
                      "CrewScore": CrewScore};
    return CrewResult;
}