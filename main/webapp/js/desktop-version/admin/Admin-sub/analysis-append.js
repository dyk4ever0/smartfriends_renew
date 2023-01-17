/**
 * append tab info into jsp page component
 */
function appendTabs() {
    var tabListTemplate = 
        "<li class=\"tab-link {current}\" data-tab=\"tab-{idx}\">{kinds}</li>";
    var kindsOfTab = ["직책별", "부서별", "계열사별"];
    var crewSelectionTemplate = 
        "<div class=\"data-choice\">" +
            "<label class=\"radio mr-25\">" +
                "<input type=\"radio\" name=\"bt_01\" value=\"01\"  class=\"bt\" checked><span class=\"mark\"></span>직책자" +
            "</label>" +
            "<label class=\"radio\">" +
                "<input type=\"radio\" name=\"bt_01\" value=\"02\" class=\"bt\"><span class=\"mark\"></span>팀원" +
            "</label>" +
        "</div>";
    for(var i = 0; i < kindsOfTab.length; i++) {
        if(i == 0) {
            $('.tab').append(
                tabListTemplate
                    .replace("{idx}", i)
                    .replace("{kinds}", kindsOfTab[i])
                    .replace("{current}", "current")
            );
        }
        else {
            $('.tab').append(
                tabListTemplate
                    .replace("{idx}", i)
                    .replace("{kinds}", kindsOfTab[i])
                    .replace("{current}", "")
            );
        }
        appendAnalysisTabContentsFormat(i);
    }
    
    
    $("#tab-0").append(
        crewSelectionTemplate
    );
}

/**
 * appends tab contents format into analysis page
 */
function appendAnalysisTabContentsFormat(index) {
    var analysisTabDataContainerTemplate = 
        "<div id=\"tab-{index}\" class=\"tab-content {current}\">" +
        "</div>";
    $('.tab-area').append(
        analysisTabDataContainerTemplate
            .replace("{index}", index)
            .replace("{current}", function() {
                if(index == 0)
                    return "current";
                else return "";
            }())
    );
}

/**
 * returns NonCrew data
 * This return object contains "# of people by skill level" and "total average score by diagnose offering toolname"
 * @returns JsonTypeMapObject two data as map object stringifyed
 */
function getNonCrewData() {
    var notCrewSkill = null;
    var notCrewScore = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/getNotCrewSkillNumber",
        async: false,

        success: function(data) {
            notCrewSkill = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
        }
    });

    $.ajax({
        type: "get",
        url: "/DWSWS/getcrewscoreByTools",
        async: false,
        data: {
            crewflag: "notcrew"
        },

        success: function(data) {
            notCrewScore = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
        }
    });
    var notCrewResult = {"notCrewSkill": notCrewSkill,
                         "notCrewScore": notCrewScore};
    return notCrewResult;
}

/**
 * returns Crew data
 * This return object contains "# of people by skill level" and "total average score by diagnose offering toolname"
 * @returns JsonTypeMapObject two data as map object stringifyed
 */
function getCrewData() {
    var CrewSkill = null;
    var CrewScore = null;

    $.ajax({
        type: "get",
        url: "/DWSWS/getCrewSkillNumber",
        async: false,

        success: function(data) {
            console.log(data);
            CrewSkill = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
        }
    });

    $.ajax({
        type: "get",
        url: "/DWSWS/getcrewscoreByTools",
        async: false,
        data: {
            crewflag: "crew"
        },

        success: function(data) {
            console.log(data);
            CrewScore = data;
        },
        error: function(request, error) {
            console.log(request, error);
            alert("Error occured while accessing to server");
        }
    });
    var CrewResult = {"CrewSkill": CrewSkill,
                      "CrewScore": CrewScore};
    return CrewResult;
}

/**
 * returns average score by tools
 * @returns average score list about diagnose registered tools
 */
function getAverageScoreByTools() {
    var result = null;
    $.ajax({
        type: "get",
        url: "/DWSWS/getAveragescoreByTools",
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
 * appends data of people by crew level into analysisPage.jsp
 */
function appendCrewData() {
    var getNotCrewData = getNonCrewData();
    var crewDataTemplate = 
            "<div id=\"img_01\" class=\"tab_section\">" + 
                "<div class=\"tab-inner item-score\">" +
                        "<div class=\"data-title\">" +
                            "<h2 class=\"section-title\">직책자 데이터</h2>" +
                        "</div>" +

                        "<div class=\"columns-wrapper\">" +
                            "<div class=\"column\">" +
                                "<div class=\"graph-box\">" +
                                    "<div class=\"graph\">" +
                                        "<h3 class=\"title\">숙련도 분포도</h3>" +
                                        "<div id=\"noncrew-skill\">" +
                                            
                                        "</div>" +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                            "<div class=\"column\">" +
                                "<div class=\"graph-box\">" +
                                    "<div class=\"graph\">" +
                                        "<h3 class=\"title\">도구별 점수 비교</h3>" +
                                        "<div id=\"noncrew-score\">" +
                                        "</div>" +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                        "</div>" +
                "</div>" +
            "</div>" +  

            "<div id=\"img_02\" class=\"tab_section\">" +
                "<div class=\"tab-inner item-score\">" +
                        "<div class=\"data-title\">" +
                            "<h2 class=\"section-title\">팀원 데이터</h2>" +
                        "</div>" +

                        "<div class=\"columns-wrapper\">" +
                            "<div class=\"column\">" +
                                "<div class=\"graph-box\">" +
                                    "<div class=\"graph\">" +
                                        "<h3 class=\"title\">숙련도 분포도</h3>" +
                                        "<div id=\"crew-skill\"></div>" +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                            "<div class=\"column\">" +
                                "<div class=\"graph-box\">" +
                                    "<div class=\"graph\">" +
                                        "<h3 class=\"title\">도구별 점수 비교</h3>" +
                                        "<div id=\"crew-score\"></div>" +
                                    "</div>" +
                                "</div>" +
                            "</div>" +
                        "</div>" +
                "</div>" +
            "</div> ";

    $("#tab-0").append(
        crewDataTemplate
    );
    
    /*
    average tool score for column chart
    */
    var averageData = getAverageScoreByTools();
    /*
    appending nonCrew data into two types of graph.
    */
    var noncrewSkillChartContainer = document.getElementById('noncrew-skill');
    var noncrewScoreChartContainer = document.getElementById('noncrew-score');
    var noncrewSkillDataSeries = (function() {
        var result = [];
        for(var i = 0; i < Object.keys(getNotCrewData.notCrewSkill).length; i++) {
            result[i] = {
                name: Object.keys(getNotCrewData.notCrewSkill)[i],
                data: getNotCrewData.notCrewSkill[Object.keys(getNotCrewData.notCrewSkill)[i]]
            };
        }
        return result;
    })();
    var noncrewScoreDataSeries = (function() {
        var result = [];
            result[0] = {
                name: "도구 점수",
                data: (function() {
                    var tempResult = [];
                    for(var i = 0; i < Object.keys(getNotCrewData.notCrewScore).length; i++) {
                        tempResult[i] = getNotCrewData.notCrewScore[Object.keys(getNotCrewData.notCrewScore)[i]];
                    }
                    console.log("도구점수 : ");
                    console.log(tempResult);
                    return tempResult;
                })()
            };
            result[1] = {
                name: "평균 점수",
                data: (function() {
                    var tempResult = [];
                    for(var i = 0; i < Object.keys(averageData).length; i++) {
                        tempResult[i] = averageData[Object.keys(averageData)[i]];
                    }
                    console.log("평균점수 : ");
                    console.log(tempResult);
                    return tempResult;
                })()
            };
        console.log(result);
        return result;
    })();

    var noncrewSkillData = {
        categories: ['숙련도'],
        series: noncrewSkillDataSeries
    }
    var noncrewScoreData = {
        categories: Object.keys(averageData),
        series: noncrewScoreDataSeries
    }

    var option = {
        chart: {
            width: 440,
            height: 350
        }
    }
    var columnOption = {
        yAxis: {
            min :0,
            max :100
        },
        chart: {
            width: 440,
            height: 350
        }
    }
    tui.chart.pieChart(noncrewSkillChartContainer, noncrewSkillData, option);
    tui.chart.columnChart(noncrewScoreChartContainer, noncrewScoreData, columnOption);

    /*
    appending crew data into two types of graph
    */
    var crewData = getCrewData();
    var crewSkillChartContainer = document.getElementById('crew-skill');
    var crewScoreChartContainer = document.getElementById('crew-score');
    var crewSkillDataSeries = (function() {
        var result = [];
        for(var i = 0; i < Object.keys(crewData.CrewSkill).length; i++) {
            result[i] = {
                name: Object.keys(crewData.CrewSkill)[i],
                data: crewData.CrewSkill[Object.keys(crewData.CrewSkill)[i]]
            };
        }
        return result;
    })();
    var crewScoreDataSeries = (function() {
        var result = [];
            result[0] = {
                name: "도구 점수",
                data: (function() {
                    var tempResult = [];
                    for(var i = 0; i < Object.keys(crewData.CrewScore).length; i++) {
                        tempResult[i] = crewData.CrewScore[Object.keys(crewData.CrewScore)[i]];
                    }
                    console.log("도구점수 : ");
                    console.log(tempResult);
                    return tempResult;
                })()
            };
            result[1] = {
                name: "평균 점수",
                data: (function() {
                    var tempResult = [];
                    for(var i = 0; i < Object.keys(averageData).length; i++) {
                        tempResult[i] = averageData[Object.keys(averageData)[i]];
                    }
                    console.log("평균점수 : ");
                    console.log(tempResult);
                    return tempResult;
                })()
            };
        console.log(result);
        return result;
    })();

    var crewSkillData = {
        categories: ['숙련도'],
        series: crewSkillDataSeries
    }
    var crewScoreData = {
        categories: Object.keys(crewData.CrewScore),
        series: crewScoreDataSeries
    }
    tui.chart.pieChart(crewSkillChartContainer, crewSkillData, option);
    tui.chart.columnChart(crewScoreChartContainer, crewScoreData, columnOption);
}

/**
 * append second data tab into analysis page
 */
function appendOrgData() {
    var orgDataTemplate = 
        "<div class=\"tab-inner item-score\">" +
                "<div class=\"data-title\">" +
                    "<h2 class=\"section-title\">부서별 데이터</h2>" +
                    "<div class=\"data-list-box\">" +
                        "<select id=\"org-main-list\" onchange=\"onChangeOfMainList()\">"+ //here, company list should be entered.
                        "</select>"+

                        "<select id=\"org-sub-list\" onchange=\"onChangeOfSubList()\">"+ //here, company's org list should be entered.
                        "</select>"+
                    "</div>"+
                "</div>"+
        "</div>";
    $('#tab-1').append(
        orgDataTemplate
    );
    appendCompanyListForOrgList(1);
    
}

/**
 * append select list tags for org-main-list
 */
function appendCompanyListForOrgList(tabNumber) {
    var companyList = getCompanyNameList();
    var companyNameTemplate = 
        "<option value=\"{companyname}\">{companyname}</option>";

    $('#tab-' + tabNumber + ' #org-main-list').append("<option disabled selected value>계열사를 선택하세요.</option>");
    for(var i = 0; i < companyList.length; i++) {
        $('#tab-' + tabNumber + ' #org-main-list').append(
            companyNameTemplate
                .replace(/{companyname}/gi, companyList[i])
        );
    }
}

/**
 * append org name list by company name
 */
function appendOrgList(companyname) {
    var orgList = getOrgNameList(companyname);
    var orgNameTemplate = 
        "<option value=\"{orgname}\">{orgname}</option>";
    
    for(var i = 0; i < orgList.length; i++) {
        $('#org-sub-list').append(
            orgNameTemplate
                .replace(/{orgname}/gi, orgList[i])
        );
    }
}

/**
 * when org-main-list has been changed to other value, this function triggered.
 * This function changes sub-list contents so that can show new org list about chosen company
 */
function onChangeOfMainList() {
    var selectedCompany = $('#org-main-list').val();
    try{
        $("#org-sub-list option").remove();
        $("#org-sub-list").append("<option disabled selected value>부서를 선택하세요.</option>");
        appendOrgList(selectedCompany);
    } catch(error) {
        alert("there is error on calling org list with comp name.")
    }
}

/**
 * when org-main-list has been changed to other value, this function triggered.
 * This function appends template of specific data about chosen company and org.
 */
function onChangeOfSubList() {
    var selectedCompany = $('#tab-1 #org-main-list').val();
    var selectedOrg = $('#org-sub-list').val();

    var orgRankingData = getOrgRanking(selectedCompany, selectedOrg);
    var orgPeopleData = getOrgPeopleData(selectedCompany, selectedOrg);
    var pieChartData = getOrgDataForPieChart(selectedCompany, selectedOrg);
    var bulletChartData = getOrgDataForBulletChart(selectedCompany, selectedOrg);
    console.log("bulletchartdata: ");
    console.log(bulletChartData);

    var orgSpceificDataTemplate = 
    "<div class=\"department\">"+
        "<div class=\"tab-1-left\">"+
            "<p class=\"data-text\">"+
            "<span>{orgname}</span>은 스마트워크 진행을 위한<br>평균 도구 숙련도가 <span>'{orgSkillGrade}'</span> 수준이며<br>전체 부서 중 <span>'{orgRanking}등'</span> 입니다."+
            "</p>"+
            
            "<div class=\"join-area\">"+
                "<p>참여도 {attendenceRate}% <span class=\"data-text-sub\">({orgpeople}명 중 {attendencepeople}명 응시)</span></p>"+
                "<p>{orgRanking} / {totalorgnumber}<span class=\"data-text-sub\">(그룹부서 전체)</span></p>"+
            "</div>"+

            "<div class=\"progress\">"+
                "<ul class=\"progress-text org\">"+
                    "<li class=\"verylow\" value=\"매우 낮은\">"+
                        "<div class=\"progress-box\">매우 낮은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"+
                    "</li>"+
                    "<li class=\"low\" value=\"낮은\">"+
                        "<div class=\"progress-box\">낮은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"	+
                    "</li>"+
                    "<li class=\"normal\" value=\"보통\">"+
                        "<div class=\"progress-box\">보통</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"+
                    "</li>"+
                    "<li class=\"high\" value=\"높은\">"+
                        "<div class=\"progress-box\">높은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"+
                    "</li>"+
                    "<li class=\"veryhigh\" value=\"매우 높은\">"+
                        "<div class=\"progress-box\">매우 높은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"	+
                    "</li>"+
                "</ul>"+
                "<progress max=\"100\" value=\"0\" class=\"progress-main\" aria-labelledby=\"Progress-id\">"+
                    "<div class=\"Progress-bar\" role=\"presentation\">"+
                        "<span class=\"Progress-value\" style=\"width: 80%;\">&nbsp;</span>"+
                    "</div>"+
                "</progress>"+
            "</div>"+

        "</div>"+
        "<div class=\"tab-1-right\">"+
            "<div class=\"graph-box\">"+
                "<div class=\"graph\">"+
                    "<h3 class=\"title\">숙련도 분포도</h3>"+
                    "<div id=\"skill-area\"></div>"+
                "</div>"+
            "</div>"+
            "<div class=\"graph-box graph-stick\">"+
                "<div class=\"graph\">"+
                    "<h3 class=\"title\">소속부서의 도구별 위치</h3>"+
                    "<div id=\"org-tool-position\">"+
                "</div>"+
                
                "</div>"+
                "<div class=\"graph-stick-text\">x : 점수 / y : 도구 / 마커 : 최고점수값, 중간점수값 / 배경 색 : 하위40%, 중위 40%, 상위20% / 바 : 소속부서 점수</div>"+
            "</div>"+
        "</div>"+
    "</div>";

    $('#tab-1 .tab-inner .department').remove();
    if(orgRankingData.orgRanking == (-1)) {
        alert("해당 부서는 시행된 진단결과가 없습니다.");
    }
    else {
        $('#tab-1 .tab-inner').append(
            orgSpceificDataTemplate
                .replace(/{orgname}/gi, selectedOrg)
                .replace(/{orgSkillGrade}/gi, orgRankingData.orgSkillGrade)
                .replace(/{orgRanking}/gi, orgRankingData.orgRanking)
                .replace(/{orgpeople}/gi, orgPeopleData.allpeople)
                .replace(/{attendencepeople}/gi, orgPeopleData.attendedpeople)
                .replace(/{totalorgnumber}/gi, orgRankingData.orgNumbers.allorgnumber)
                .replace(/{attendenceRate}/gi, ((orgPeopleData.attendedpeople/orgPeopleData.allpeople) * 100).toFixed(3))
        );
        $('.progress .progress-text.org').find("li[value=\"" + orgRankingData.orgSkillGrade + "\"]").addClass("on");

        var orgSkillGraph = document.getElementById('skill-area');
        var orgToolPositionGraph = document.getElementById('org-tool-position');
        var orgSkillData = {
            categories: ["숙련도"],
            series: (function() {
                var result = [];
                for(var i = 0; i < Object.keys(pieChartData).length; i++) {
                    result[i] = {
                        name: Object.keys(pieChartData)[i],
                        data: pieChartData[Object.keys(pieChartData)[i]]
                    };
                }
                return result;
            })()
        };
        var pieOption = {
            chart: {
                width: 546,
                height: 350
            }
        };
        var orgToolPositionData = {
            categories: ['점수'],
            series: (function() {
                var result = [];
                try {
                    for(var i = 0; i < Object.keys(bulletChartData).length; i++) {
                        console.log(bulletChartData[Object.keys(bulletChartData)[i]].toolname);
                        console.log(bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore);
                        result[i] = {
                            name: bulletChartData[Object.keys(bulletChartData)[i]].toolname,
                            data: bulletChartData[Object.keys(bulletChartData)[i]].orgToolscore,
                            markers: bulletChartData[Object.keys(bulletChartData)[i]].medianAndMaximum,
                            ranges: [[0, bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[2]],
                                    [bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[2], bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[1]],
                                    [bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[1], bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[0]]]
                        };
                    }
                } catch(Exception) {
                    console.log(Exception);
                }
                console.log("bullet chart data :")
                console.log(result);
                return result;
            })()
        }
        var bulletOption = {
            chart: {
                width: 546,
                height: 350
            },
            series: {
                showLabel : true,
                vertical : false
            }
        }
        tui.chart.pieChart(orgSkillGraph, orgSkillData, pieOption);
        tui.chart.bulletChart(orgToolPositionGraph, orgToolPositionData, bulletOption);
    }
}

/**
 * append third data tab into analysis page
 */
function appendCompanyData() {
    var orgDataTemplate = 
        "<div class=\"tab-inner item-score\">" +
                "<div class=\"data-title\">" +
                    "<h2 class=\"section-title\">계열사별 데이터</h2>" +
                    "<div class=\"data-list-box\">" +
                        "<select id=\"org-main-list\" onchange=\"onChangeOfMainListForCompany()\">"+ //here, company list should be entered.
                        "</select>"+
                    "</div>"+
                "</div>"+
        "</div>";
    $('#tab-2').append(
        orgDataTemplate
    );
    appendCompanyListForOrgList(2);
}

/**
 * when org-main-list has been changed to other value, this function triggered.
 * This function appends template of specific data about chosen company and org.
 */
function onChangeOfMainListForCompany() {
    var selectedCompany = $('#tab-2 #org-main-list').val();

    /*
    function names should be changed into generalized names that dose not imply 'only for org'.
    */
    var orgRankingData = getOrgRanking(selectedCompany, null);
    var orgPeopleData = getOrgPeopleData(selectedCompany, null);
    var pieChartData = getOrgDataForPieChart(selectedCompany, null);
    var bulletChartData = getOrgDataForBulletChart(selectedCompany, null);
    console.log("bulletchartdata: ");
    console.log(bulletChartData);

    var companySpceificDataTemplate = 
    "<div class=\"department\">"+
        "<div class=\"tab-2-left\">"+
            "<p class=\"data-text\">"+
            "<span>{companyname}</span>은 스마트워크 진행을 위한<br>평균 도구 숙련도가 <span>'{companySkillGrade}'</span> 수준이며<br>전체 부서 중 <span>'{companyRanking}등'</span> 입니다."+
            "</p>"+
            
            "<div class=\"join-area\">"+
                "<p>참여도 {attendenceRate}% <span class=\"data-text-sub\">({companypeople}명 중 {attendencepeople}명 응시)</span></p>"+
                "<p>{companyRanking} / {totalcompanynumber}<span class=\"data-text-sub\">(회사 전체)</span></p>"+
            "</div>"+

            "<div class=\"progress\">"+
                "<ul class=\"progress-text org\">"+
                    "<li class=\"verylow\" value=\"매우 낮은\">"+
                        "<div class=\"progress-box\">매우 낮은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"+
                    "</li>"+
                    "<li class=\"low\" value=\"낮은\">"+
                        "<div class=\"progress-box\">낮은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"	+
                    "</li>"+
                    "<li class=\"normal\" value=\"보통\">"+
                        "<div class=\"progress-box\">보통</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"+
                    "</li>"+
                    "<li class=\"high\" value=\"높은\">"+
                        "<div class=\"progress-box\">높은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"+
                    "</li>"+
                    "<li class=\"veryhigh\" value=\"매우 높은\">"+
                        "<div class=\"progress-box\">매우 높은</div>"+
                        "<div class=\"progress-icon\"><span class=\"progress-check\"></span></div>"	+
                    "</li>"+
                "</ul>"+
                "<progress max=\"100\" value=\"0\" class=\"progress-main\" aria-labelledby=\"Progress-id\">"+
                    "<div class=\"Progress-bar\" role=\"presentation\">"+
                        "<span class=\"Progress-value\" style=\"width: 80%;\">&nbsp;</span>"+
                    "</div>"+
                "</progress>"+
            "</div>"+

        "</div>"+
        "<div class=\"tab-2-right\">"+
            "<div class=\"graph-box\">"+
                "<div class=\"graph\">"+
                    "<h3 class=\"title\">숙련도 분포도</h3>"+
                    "<div id=\"comp-skill-area\"></div>"+
                "</div>"+
            "</div>"+
            "<div class=\"graph-box graph-stick\">"+
                "<div class=\"graph\">"+
                    "<h3 class=\"title\">소속회사의 도구별 위치</h3>"+
                    "<div id=\"comp-tool-position\">"+
                "</div>"+
                
                "</div>"+
                "<div class=\"graph-stick-text\">x : 점수 / y : 도구 / 마커 : 최고점수값, 중간점수값 / 배경 색 : 하위40%, 중위 40%, 상위20% / 바 : 소속부서 점수</div>"+
            "</div>"+
        "</div>"+
    "</div>";

    $('#tab-2 .tab-inner .department').remove();
    if(orgRankingData.orgRanking == (-1)) {
        alert("This Company does not have data.")
    }
    else {
        $('#tab-2 .tab-inner').append(
            companySpceificDataTemplate
                .replace(/{companyname}/gi, selectedCompany)
                .replace(/{companySkillGrade}/gi, orgRankingData.orgSkillGrade)
                .replace(/{companyRanking}/gi, orgRankingData.orgRanking)
                .replace(/{companypeople}/gi, orgPeopleData.allpeople)
                .replace(/{attendencepeople}/gi, orgPeopleData.attendedpeople)
                .replace(/{totalcompanynumber}/gi, orgRankingData.orgNumbers.allorgnumber)
                .replace(/{attendenceRate}/gi, ((orgPeopleData.attendedpeople/orgPeopleData.allpeople) * 100).toFixed(3))
        );
        $('.progress .progress-text.org').find("li[value=\"" + orgRankingData.orgSkillGrade + "\"]").addClass("on");

        var orgSkillGraph = document.getElementById('comp-skill-area');
        var orgToolPositionGraph = document.getElementById('comp-tool-position');
        var orgSkillData = {
            categories: ["숙련도"],
            series: (function() {
                var result = [];
                for(var i = 0; i < Object.keys(pieChartData).length; i++) {
                    result[i] = {
                        name: Object.keys(pieChartData)[i],
                        data: pieChartData[Object.keys(pieChartData)[i]]
                    };
                }
                return result;
            })()
        };
        var pieOption = {
            chart: {
                width: 546,
                height: 350
            }
        };
        var orgToolPositionData = {
            categories: ['점수'],
            series: (function() {
                var result = [];
                try {
                    for(var i = 0; i < Object.keys(bulletChartData).length; i++) {
                        console.log(bulletChartData[Object.keys(bulletChartData)[i]].toolname);
                        console.log(bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore);
                        result[i] = {
                            name: bulletChartData[Object.keys(bulletChartData)[i]].toolname,
                            data: bulletChartData[Object.keys(bulletChartData)[i]].orgToolscore,
                            markers: bulletChartData[Object.keys(bulletChartData)[i]].medianAndMaximum,
                            ranges: [[0, bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[2]],
                                    [bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[2], bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[1]],
                                    [bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[1], bulletChartData[Object.keys(bulletChartData)[i]].rankingRangeScore[0]]]
                        };
                    }
                } catch(Exception) {
                    console.log(Exception);
                }
                return result;
            })()
        }
        var bulletOption = {
            chart: {
                width: 546,
                height: 350
            },
            series: {
                showLabel : true,
                vertical : false
            }
        }
        tui.chart.pieChart(orgSkillGraph, orgSkillData, pieOption);
        tui.chart.bulletChart(orgToolPositionGraph, orgToolPositionData, bulletOption);
    }
}