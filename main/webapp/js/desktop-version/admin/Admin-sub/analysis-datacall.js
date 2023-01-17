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

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
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
        data: {
            "companyname" : companyname
        },

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
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

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
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
    var dataFormat = (function(){
        if(orgname != null) return {"companyname" : companyname, "orgname" : orgname};
        else return {"companyname" : companyname};
    })();

    $.ajax({
        type: "get",
        url: "/DWSWS/getPeopleData",
        async: false,
        data: dataFormat,

        success: function(data) {
            result = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
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

        success: function(data) {
            result = data
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
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
    var dataFormat = (function() {
        if(orgname != null) return {"companyname" : companyname, "orgname" : orgname};
        else return {"companyname" : companyname};
    })();

    $.ajax({
        type: "get",
        url: "/DWSWS/getDataForBulletChart",
        async: false,
        data: dataFormat,

        success: function(data) {
            result = data
            console.log(result);
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
        }
    });
    return result;
}