var errorPageTemplate =
    "<div id=\"error-message\" class=\"contents-wrapper\">"+
        "<section class=\"error transparent\">"+
            "<div class=\"inner\">"+
                "<div class=\"error-box\">"+
                    "<div class=\"visual\">"+
                        // "<img src=\"publisher-folder/images/error_bear.gif\" alt=\"\">"+
                    "</div>"+

                    "<h2 class=\"code\">{code}</h2>"+
                    "<p class=\"message\">{message}</p>"+
                    "<p class=\"description\">{description}</p>"+
                "</div>"+

                // "<p class=\"btn-wrapper\"><a href=\"{url}\" class=\"square-btn\">{buttonContents}</a></p>"+
            "</div>"+
        "</section>"+
    "</div>";

/**
 * handles error case and appends error message to specific component
 * @param {String} status 
 * @param {String} message 
 * @param {String} description 
 * @param {String} url 
 * @param {String} buttonContents 
 * @param {String} targetComponent
 * @param {Flag} imgflag
 * @author 박승수 (21.01.26)
 */
function appendErrorMessage(status, message, description, url, buttonContents, targetComponent, imgflag) {
    var imgTemplate =
        "<img src=\"publisher-folder/images/error_bear.png\" alt=\"\">";
    var buttonTemplate = 
        "<p class=\"btn-wrapper\"><a href=\"{url}\" class=\"square-btn\">{buttonContents}</a></p>";
    var currentComponent =
        $(targetComponent).html(
        errorPageTemplate
            .replace("{code}", status)
            .replace("{message}", message)
            .replace("{description}", description)
    );

    if(imgflag == true) {
        currentComponent.find(".error-box .visual").append(
            imgTemplate
        );
        currentComponent.find(".error .inner").append(
            buttonTemplate
                .replace("{url}", url)
                .replace("{buttonContents}", buttonContents)
        );
    }
}