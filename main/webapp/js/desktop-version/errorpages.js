var errorPageTemplate =
   "<div class=\"contents-wrapper bg-blue error\">"+
        "<div class=\"inner\">"+
            "<div class=\"contents\">"+
                "<section>"+
                    "<div class=\"inner\">"+
                        "<div class=\"error-box\">"+
                            "<h2 class=\"code\">{code}</h2>"+
                            "<p class=\"message\">{message}</p>"+
                            "<p class=\"description\">{description}</p>"+
                        "</div>"+
                        "<p class=\"button-wrapper\">"+
                            "<a href=\"{url}\" class=\"square-btn\">{buttonContents}</a>"+
                        "</p>"+
                    "</div>"+
                "</section>"+
            "</div>"+
        "</div>"+
    "</div>";

/**
 * used on suggestionGuide.js
 * handles error when no diagnose data exist but try to access to myinfo page.
 * @param status error code returned from occured function
 * @param message message want to vail
 * @param description solution that 'll give to user
 * @param url redirect url for description
 * @param buttonContents text shows on button
 */
function noneTestDataError(status, message, description, url, buttonContents, targetComponent) {
    $(targetComponent).html(
        errorPageTemplate
            .replace("{code}", status)
            .replace("{message}", message)
            .replace("{description}", description)
            .replace("{url}", url)
            .replace("{buttonContents}", buttonContents)
    );
}

/**
 * used on header.js
 * handles authentication check
 * if not have auth, error page shows up
 * @param status error code returned from occured function
 * @param message message want to vail
 * @param description solution that 'll give to user
 * @param url redirect url for description
 * @param buttonContents text shows on button
 */
function accessAuthentication(status, message, description, url, buttonContents) {
    $(".contents-wrapper").html(
        errorPageTemplate
            .replace("{code}", status)
            .replace("{message}", message)
            .replace("{description}", description)
            .replace("{url}", url)
            .replace("{buttonContents}", buttonContents)
    );
}