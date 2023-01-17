/**
 * append tab info into jsp page component
 * @author 박승수(21.02.05)
 */
function appendInfoTabs() {
    var tabListTemplate = 
        "<li><a href=\"tab-{idx}\" role=\"tab\" aria-controls=\"position\" aria-selected=\"{selection}\" data-tab=\"tab-{idx}\">{kinds}</a></li>"
    var kindsOfTab = ["직책별", "부서별", "계열사별"];
    var crewSelectionTemplate = 
        "<ul class=\"tab-toggle\">" +
            "<li><a href=\"#leader\" role=\"tab\" aria-controls=\"leader\" aria-selected=\"true\">직책자</a></li>" +
            "<li><a href=\"#member\" role=\"tab\" aria-controls=\"member\" aria-selected=\"false\">팀원</a></li>" +
        "</ul>";
    
    for(var i = 0; i < kindsOfTab.length; i++) {
        if(i == 0) {
            $('.tab-buttons').append(
                tabListTemplate
                    .replace(/{idx}/gi, i)
                    .replace("{kinds}", kindsOfTab[i])
                    .replace("{selection}", "true")
            );
        } else {
            $('.tab-buttons').append(
                tabListTemplate
                    .replace(/{idx}/gi, i)
                    .replace("{kinds}", kindsOfTab[i])
                    .replace("{selection}", "false")
            );
        }
        appendAnalysisTabFormat(i);
    }

    $('#tab-0').append(
        crewSelectionTemplate
    );
}

/**
 * append analysis tab format (tab which 'll contain charts and other data)
 * @author 박승수 (21.02.05)
 */
function appendAnalysisTabFormat(index) {
    var analysisTabFormat = 
        "<li id=\"tab-{idx}\" class=\"{activation}\" role=\"tabpanel\"></li>";
    $('.tab-contents').append(
        analysisTabFormat
            .replace("{idx}", index)
            .replace("{activation}", function() {
                if(index == 0) return "active";
                else return "";
            })
    );
}