/*
 * selection box data is managed as global variable so that can approach with only one call per clicking toolname on lnb.
 */
var selectionBoxData = null;
var selectedToolidx = null;

/**
 * append survey question contents on survey-modify.jsp
 * @author 박승수 (21.02.17)
 */
function appendSurveyQuestionList(toolname, targetComponent) {

    targetComponent.parent().parent().find("a[class=\"active\"]").removeClass('active');
    targetComponent.addClass('active');

    var surveyQuestionTemplate = 
        "<fieldset class=\"border-type\" role=\"listitem\" id=\"element-{idx}\">"+
            "<legend class=\"sr-only\">질문 추가</legend>"+
            "<table>"+
                "<tbody>"+
                    "<tr>"+
                        "<td><input type=\"text\" name=\"\" id=\"question\" class=\"common\" placeholder=\"문항을 입력하세요.\" value=\"{contents}\"></td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td>"+
                            "<select name=\"\" class=\"common bookmark-tools\" onchange=\"appendBookmarkBoxData($(this).val(), $(this));\">"+
                                //section append area
                            "</select>"+
                            "<select name=\"\" class=\"common bookmark-title\">"+
                                //bookmark append area
                            "</select>"+
                            "<select name=\"\" class=\"common question-answer\">"+
                                "<option value=1>O</option>"+
                                "<option value=0>X</option>"+
                            "</select>"+
                        "</td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td class=\"btn-wrapper\">"+
                            "<button type=\"button\" class=\"simple\" onclick=\"deleteOneQuestion($(this));\">삭제</button>"+ // this button erase $(this) fieldset.
                        "</td>"+
                    "</tr>"+
                "</tbody>"+
            "</table>"+
        "</fieldset>";

    var questionList = callSurveyQuestionData(toolname);
    selectedToolidx = callToolidxWithToolnameFromSurveytable(toolname);
    //initialize global selection box data with toolname
    selectionBoxData = callSelectionBoxDataForSurvey(toolname);
    $("#survey-tool-target").text(toolname);
    $("#questionList fieldset").remove();
    
    for(var index = 0; index < questionList.length; index++) {
        $("#questionList").append(
            surveyQuestionTemplate
                .replace(/{idx}/gi, index)
                .replace("{contents}", questionList[index].question)
        );
        appendSectionBoxData($("#element-"+index).find("select.common.bookmark-tools"));
        //set selected section data as question list's secidx
        $("#element-"+index).find("select.common.bookmark-tools").val(questionList[index].secidx);
        //append bookmark box data
        appendBookmarkBoxData(questionList[index].secidx, $("#element-"+index).find("select.common.bookmark-tools"));
        //set selected bookmark data as question list's bmkidx
        $("#element-"+index).find("select.common.bookmark-title").val(questionList[index].bmkidx);
        $("#element-"+index).find("select.common.question-answer").val(questionList[index].answer);
    }
}

/**
 * returns selection box data of section area
 * +. if can, need to apply json-groupby.
 * @author 박승수 (21.02.17)
 */
function appendSectionBoxData(targetComponent) {
    var selectboxTemplate = 
            "<option value=\"{secidx}\">{selectContents}</option>";
    var insertedSecidx = [];

    for(var index = 0; index < selectionBoxData.length; index++) {
        if(function(){ // find out which is already appended section data
            for(var inserted = 0; inserted < insertedSecidx.length; inserted++) {
                if(selectionBoxData[index].secidx == insertedSecidx[inserted]) return false;
            }
            return true;
        }() != false) {
            targetComponent.append(
                selectboxTemplate
                    .replace("{selectContents}", selectionBoxData[index].seccomment)
                    .replace("{secidx}", selectionBoxData[index].secidx)
            );
            insertedSecidx.push(selectionBoxData[index].secidx); // store appended section data
        }
    }
}

/**
 * append bookmark data to selection box by chosen section
 * @author 박승수 (21.02.17)
 */
function appendBookmarkBoxData(section, targetComponent) {
    var selectboxTemplate = 
            "<option value=\"{bmkidx}\">{selectContents}</option>";

    //erase existing option list
    targetComponent.parent().find("select.common.bookmark-title option").remove();

    for(var index = 0; index < selectionBoxData.length; index++) {
        if(section == selectionBoxData[index].secidx) {
            targetComponent.parent().find("select.common.bookmark-title").append(
                selectboxTemplate
                    .replace("{bmkidx}", selectionBoxData[index].bmkidx)
                    .replace("{selectContents}", selectionBoxData[index].bmkcomment)
            );
        }
    }
}

/**
 * delete one question ( = one fieldset component )
 * @author 박승수 (21.02.18)
 */
function deleteOneQuestion(targetComponent) {
    targetComponent.closest("fieldset.border-type").remove();
}

/**
 * collect updated question data which 'll send to server
 * @author 박승수 (21.02.18)
 */
function collectUpdateQuestionData() {
    var collectionUpdatedData = []; // array containing questionManagementListDO object
    
    $("#questionList fieldset").each(function(index, element) {
        var questionElement = {
            "question" : $(element).find("#question").val(),
            "answer" : $(element).find("select.common.question-answer").val(),
            "toolidx" : selectedToolidx,
            "bmkidx" : $(element).find("select.common.bookmark-title").val()
        };
        collectionUpdatedData.push(questionElement);
    });

    saveNewSurveyQuestions(collectionUpdatedData, $("#survey-tool-target").text());
}

/**
 * append new survey question format
 * @author 박승수 (21.02.18)
 */
function appendNewQuestionFormat() {
    var surveyQuestionTemplate = 
        "<fieldset class=\"border-type\" role=\"listitem\" id=\"element-{idx}\">"+
            "<legend class=\"sr-only\">질문 추가</legend>"+
            "<table>"+
                "<tbody>"+
                    "<tr>"+
                        "<td><input type=\"text\" name=\"\" id=\"question\" class=\"common\" placeholder=\"문항을 입력하세요.\" value=\"{contents}\"></td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td>"+
                            "<select name=\"\" class=\"common bookmark-tools\" onchange=\"appendBookmarkBoxData($(this).val(), $(this));\">"+
                                //section append area
                            "</select>"+
                            "<select name=\"\" class=\"common bookmark-title\">"+
                                //bookmark append area
                            "</select>"+
                            "<select name=\"\" class=\"common question-answer\">"+
                                "<option value=1>O</option>"+
                                "<option value=0>X</option>"+
                            "</select>"+
                        "</td>"+
                    "</tr>"+
                    "<tr>"+
                        "<td class=\"btn-wrapper\">"+
                            "<button type=\"button\" class=\"simple\" onclick=\"deleteOneQuestion($(this));\">삭제</button>"+ // this button erase $(this) fieldset.
                        "</td>"+
                    "</tr>"+
                "</tbody>"+
            "</table>"+
        "</fieldset>";

    //append new clean format
    var newAppendIndex = $("#questionList fieldset").length;
    $("#questionList").append(
        surveyQuestionTemplate
            .replace(/{idx}/gi, newAppendIndex)
            .replace("{contents}", "")
    );
    appendSectionBoxData($("#element-"+newAppendIndex).find("select.common.bookmark-tools"));
}