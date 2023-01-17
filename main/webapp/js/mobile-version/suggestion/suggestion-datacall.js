/**
 * calls suggestion list data based on most recent test data
 * @author 박승수 (21.01.20)
 */
function callSuggestionList() {
    var result = null;
  
    $.ajax({
      async: false,
      type: "get",
      url: "/DWSWS/getSuggestionList",
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
 * calls survey offering tools
 * @author 박승수 (21.01.25)
 */
function getTestOfferTools() {
  var result = [];

  $.ajax({
    async: false,
    type: "get",
    url: "/DWSWS/getTestOfferTools",
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