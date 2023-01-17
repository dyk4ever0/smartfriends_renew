/**
 * append suggestion list on diagnose/result.jsp
 * @author 박승수 (21.01.20)
 */
function appendSuggestionGuide(toolname, removeClass, appendClass) {
    //remove initial tool button click message
    $('#error-message').remove();
    //remove existing appended suggestion items
    $(removeClass).remove();
    var suggestionData = callSuggestionList();
    var resultSuggestionTemplate = 
        "<li role=\"listitem\">" +
            "<a href=\"{url}\"><span class=\"tag {toolname}\">{toolname}</span>{bmkcomment}</a>" +
            "<div class=\"btn-wrapper\">" +
                "<button type=\"button\" class=\"icon star\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>" +
                "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>" +
            "</div>" +
            "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
            "<input type=\"hidden\" id=\"url\" value=\"{url}\" />" +
        "</li>";
    
    //append suggestion list into result.jsp through iteration
    for(var suggestionItems = 0; suggestionItems < suggestionData.length; suggestionItems++) {
        //only append when clicked toolname is same as returned suggestion toolname
        if(toolname == suggestionData[suggestionItems].toolname) {
            //appending suggestion item
            var currentSuggestion =
            $(appendClass).append(
                resultSuggestionTemplate
                    .replace(/{url}/gi, "toolguide?tool=" + suggestionData[suggestionItems].toolname + "&start=" + suggestionData[suggestionItems].bmktime)
                    .replace(/{toolname}/gi, suggestionData[suggestionItems].toolname)
                    .replace("{bmkcomment}", suggestionData[suggestionItems].bmkcomment)
                    .replace("{bmkidx}", suggestionData[suggestionItems].bmkindex)
            );

            //star-highlight function
            var favoriteData = callFavoriteList();
            favoriteData.forEach(function(element) {
                if(element.bookmark == suggestionData[suggestionItems].bmkindex) {
                    currentSuggestion.find("input[value=" + element.bookmark + "]").parent().find('.icon.star').addClass('on');
                }
            });
        }
    }
}

/**
 * append suggestion list on mypage.jsp
 * @author 박승수 (21.01.20)
 */
function appendSuggestionBlockOnMypage(toolname, removeClass, appendClass) {
    //remove initial tool button click message
    $('.recommend #error-message').remove();
    //remove existing appended suggestion items
    $(removeClass).remove();
    var suggestionData = callSuggestionList();

    var mypageSuggestionTemplate = 
        "<div class=\"guide-box\" role=\"listitem\">"+
            "<div class=\"text-box\">"+
                "<span class=\"tag {toolname}\">{toolname}</span>"+
                "<p class=\"sub-name\">{sectionname}</p>"+
                "<p class=\"name\">{bookmarkname}</p>"+
                "<div class=\"btn-wrapper\">"+
                    "<button type=\"button\" class=\"icon star\" aria-pressed=\"true\"><span class=\"sr-only\">북마크</span></button>"+
                    "<button type=\"button\" class=\"icon link\"><div class=\"sr-only\">페이지 이동</div></button>"+
                "</div>"+
                "<input type=\"hidden\" id=\"bmk\" name=\"bmkidx\" value=\"{bmkidx}\" />" +
                "<input type=\"hidden\" id=\"url\" value=\"{url}\" />" +
            "</div>"+
        "</div>";

    //append suggestion list into mypage.jsp through iteration
    for(var suggestionItems = 0; suggestionItems < suggestionData.length; suggestionItems++) {
        var fowardAppendUrl = (suggestionData[i].category == "도구 가이드") ? localStorage.toolGuide.split('/DWSWS/')[1] : localStorage.priorKnow.split('/DWSWS/')[1];
        //only append when clicked toolname is same as returned suggestion toolname
        if(toolname == suggestionData[suggestionItems].toolname) {
            //append suggestion items
            var currentSuggestion = 
            $(appendClass).append(
                mypageSuggestionTemplate
                    .replace(/{url}/gi, fowardAppendUrl + suggestionData[suggestionItems].toolname + "&start=" + suggestionData[suggestionItems].bmktime)
                    .replace(/{toolname}/gi, suggestionData[suggestionItems].toolname)
                    .replace("{sectionname}", suggestionData[suggestionItems].secname)
                    .replace("{bookmarkname}", suggestionData[suggestionItems].bmkcomment)
                    .replace("{bmkidx}", suggestionData[suggestionItems].bmkindex)
            );
            //star-highlight function
            var favoriteData = callFavoriteList();
            favoriteData.forEach(function(element) {
                if(element.bookmark == suggestionData[suggestionItems].bmkindex) {
                    currentSuggestion.find("input[value=" + element.bookmark + "]").parent().find('.btn-wrapper .icon.star').addClass('on');
                }
            });
        }
    }
}

/**
 * append incorrect answer suggestion button
 * @author 박승수 (21.02.04)
 */
function appendIncorrectShowButton() {
    var testOfferTools = getTestOfferTools();
    var correctAnswerShowButtonTemplate =
    "<span class=\"tag {toolname}\" onclick=\"appendSuggestionGuide('{toolname}', '.recommend-guide .inner .board-list li', '.board-list');\">{toolname}</span>";
    //append incorrect answer note button
    for(var tools = 0; tools < testOfferTools.length; tools++) {
        $(".recommend-guide .recommend-title").append(
            correctAnswerShowButtonTemplate
                .replace(/{toolname}/gi, testOfferTools[tools])
        );
    }
}