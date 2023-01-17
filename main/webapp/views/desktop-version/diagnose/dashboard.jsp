<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags"%>
  
  <%@ include file="../include-pages/common.jsp" %>
    <title>대웅 SmartWork Friends</title>
  <style>
.text-box .info strong {
    font-weight: 500;
    color: #bb9dd6;
}
</style>

  <script type="text/javascript" src="js/desktop-version/diagnose/diagnose.js"></script>
  <!--diagnose js functions -->
  <body>
    <!-- GNB -->
    <%@ include file="../include-pages/header.jsp" %>
    <!-- </header> -->
    
    <!-- CONTENTS [s] -->
    <div class="contents-wrapper bg-blue">
      <div class="inner">
          <div class="contents">
              <section class="status">
                  <div class="inner">
                      <h2 class="section-title">진단 현황</h2>
                      <div id="upper-columns-wrapper" class="columns-wrapper" role="list">

                      </div>
                  </div>
              </section>
          </div>
      </div>
  </div>
  <div class="contents-wrapper">
      <div class="document">
          <section>
              <div class="inner">
                  <h2 class="section-title">진단 서비스의 <span style="color: #bb9dd6;">특징</span></h2>
                  <ul class="columns-wrapper" role="list">
                      <li class="column" role="listitem">
                          <div class="text-box">
                              <h3 class="title">도구들에 대한 <span style="color: #bb9dd6;">개별 진단</span></h3>
                              <p>스마트워크 필수 도구들에 대해 각각 진단지가 존재합니다. 자신이 도구의 특징들을 잘 알고 있는지 테스트 해 보세요. </p>
                          </div>
                      </li>
                      <li class="column" role="listitem">
                          <div class="text-box">
                              <h3 class="title">친절한 <span style="color: #bb9dd6;">오답 가이드</span></h3>
                              <p>각각의 질문들에 대한 가이드가 존재합니다. 답을 적지 못한 질문은 제시되는 가이드를 통해 바로 학습이 가능합니다.</p>
                          </div>
                      </li>
                  </ul>
              </div>
          </section>
      </div>
  </div>
  <!-- CONTENTS [e] -->
    <!-- FOOTER -->
<%--     <%@ include file="../include-pages/footer.jsp" %> --%>
  </body>
</html>

<script>
    var data = null;
    var toolImageTemplate = 
      "<div class=\"qna column\" role=\"listitem\">" +
        "<div class=\"text-box status-box\">" +
            "<div class=\"visual logo\">" +
                "<img src=\"publisher-folder/images/logo_{toolname}.jpg\"  alt=\"\">" +
            "</div>" +
            "<p class=\"progress\">" +
                "<strong>{totalscore}</strong>/100" +
            "</p>" +
            "<p class=\"info\"><strong>{trialnum-text}진단</strong> ({questionnum}문항)</p>" +
        "</div>" +
        "<div class=\"button-box\">" +
            "<a href=\"toolguide?tool={toolname}\" class=\"square-btn border\">가이드 보기</a>" +
            "<a style=\"cursor:pointer\" class=\"square-btn\" name=\"{idx}\" data-trial = \"{trialnum}\" data-score=\"{currentscore}\" data-toolname=\"{toolname}\">진단시작</a>" +
        "</div>" +
      "</div>";
    
    data = callMainpageDataList();
    for(var i = 0; i < data.length; i++) {
      if(data[i].updatedate == null) {
        $("#upper-columns-wrapper").append(
          toolImageTemplate.replace(/{toolname}/gi, data[i].toolname)
          .replace("{totalscore}", 0)
          .replace("{trialnum-text}", "미")
          .replace("{trialnum}", data[i].trialnum)
          .replace("{questionnum}", data[i].questionnum)
          .replace("{idx}", i)
          .replace("{currentscore}", 0)
        );
      }
      else {
        $("#upper-columns-wrapper").append(
          toolImageTemplate.replace(/{toolname}/gi, data[i].toolname)
          .replace(/{totalscore}/gi, data[i].totalscore)
          .replace("{trialnum-text}", data[i].trialnum + "차")
          .replace("{trialnum}", data[i].trialnum)
          .replace("{questionnum}", data[i].questionnum)
          .replace("{idx}", i)
          .replace("{currentscore}", data[i].totalscore)
        );
      }
    }
    $(document).on("click", ".square-btn", function(event) {
      localStorage.setItem("toolname", $(this).attr("data-toolname"));
      localStorage.setItem("trialnum", $(this).attr("data-trial"));
      localStorage.setItem("mycurrentscore", $(this).attr("data-score"));
      localStorage.setItem("mycurrentTotalscore", callTotalscoreFromRankingTable());
      location.href = "diagnose-test";
    })
</script>
