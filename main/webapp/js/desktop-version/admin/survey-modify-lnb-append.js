/**
 * append survey-modify-lnb components that shows survey-offer tools
 * @author 박승수 (21.02.10)
 */
function appendSurveyOfferingTools() {
    var surveyOfferToolsTemplate = 
        "<li>" +
            "<a class=\"{activation}\" onclick=\"appendSurveyQuestionList($(this).text().substring(1), $(this))\">{imgclass}{toolname}</a>" +
        "</li>";
    var spanTextIconTemplate =
        "<span class=\"text-icon\" area-hidden=\"true\">{firstletter}</span>";
    var testOfferTools = callSurveyOfferingTools();

    for(var index = 0; index < testOfferTools.length; index++) {
        $("aside .link-menu").append(
            surveyOfferToolsTemplate
                .replace("{activation}", function() {
                    if(index == 0) return "active";
                    else return "";
                }())
                .replace("{toolname}", testOfferTools[index])
                .replace("{imgclass}", function() { // erased image template (only alphabet exist)
                    return spanTextIconTemplate.replace("{firstletter}", testOfferTools[index].substring(0,1)); 
                }()
                )
        )
    }
}

/**
 * append delete button to erase survey offering tools on survey-modify-lnb
 * @author 박승수 (21.02.10)
 */
function appendSurveyOfferingToolsDeletion() {
    var deleteButtonTemplate = 
            "<button type=\"button\" class=\"icon delete survey\" id=\"survey-deletion\" onclick=\"deleteSurveyOfferingTools($(this).parent().text().substring(1), $(this).parent());\">" +
                "<span class=\"sr-only\"></span>" +
            "</button>";

    if($("aside .link-menu li").find("button[class=\"icon delete\"]").length > 0) { // when delete button appended
        $("aside .link-menu li").each(function (index, item) {
            $(this).find("button[class=\"icon delete\"]").remove();
        })
    } else { // when delete button not appended
        $("aside .link-menu li").each(function (index, item) {
                $(this).find("a").append(
                    deleteButtonTemplate
                );
        })
    }
}

/**
 * append survey create button on survey-modify-lnb
 * @author 박승수 (21.02.15)
 */
function appendSurveyOfferingToolsAddition() {
    var additionFormatTemplate = 
    "<li class=\"menu-field\">" +
        "<span class=\"text-icon plus\" aria-hidden=\"true\">+</span>" +
        "<input type=\"text\" class=\"common\" id=\"newSurvey\" name=\"\" placeholder=\"메뉴명\" value=\"\">" +
        "<button type=\"button\" class=\"icon check\" onclick=\"addSurveyOfferingTools($(this).parent());\">" +
            "<span class=\"sr-only\">확인</span>" +
        "</button>" +
    "</li>";

    if($("aside .link-menu").find("li[class=\"menu-field\"]").length > 0) { // when addition format appended
        $("aside .link-menu").find("li[class=\"menu-field\"]").remove();
    } else {
        $("aside .link-menu").append(
            additionFormatTemplate
        )
    }
}