<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%> <%@ taglib prefix="c"
uri="http://java.sun.com/jsp/jstl/core"%> <%@ taglib prefix="spring"
uri="http://www.springframework.org/tags"%>
  
  <%@ include file="../../include-pages/common.jsp" %>
    <title>대웅 SmartWork Friends</title>

  <!--diagnose js functions -->
  <body>
    <!-- GNB -->
    <%@ include file="../../include-pages/header.jsp" %>
    <!-- </header> -->
    <script type="text/javascript" src="js/mobile-version/admin/Admin-sub/analysis-append.js"></script>
    <script type="text/javascript" src="js/mobile-version/admin/Admin-sub/analysis-datacall.js"></script>
    <!-- CONTENTS [s] -->
    <div class="contents-wrapper">
        <section class="data-statistics">
            <div class="inner">
                <ul class="tab-buttons">
                    <li><a href="#position" role="tab" aria-controls="position" aria-selected="true">직책별</a></li>
                    <li><a href="#department" role="tab" aria-controls="department" aria-selected="false">부서별</a></li>
                    <li><a href="#affiliate" role="tab" aria-controls="affiliate" aria-selected="false">계열사별</a></li>
                </ul>
                <ul class="tab-contents">
                    <li id="position" class="active" role="tabpanel"> <!-- analysis tab format append target-->
                        <ul class="tab-toggle">
                            <li><a href="#leader" role="tab" aria-controls="leader" aria-selected="true">직책자</a></li>
                            <li><a href="#member" role="tab" aria-controls="member" aria-selected="false">팀원</a></li>
                        </ul>
                        <ul class="tab-contents">
                            <li id="leader" class="active" role="tabpanel">
                                <h2 class="section-title">직책자 데이터</h2>
                                <div class="chart-box">
                                    <div class="chart">
                                        <p class="caption">숙련도 분포도</p>
                                        <!-- 차트 영역 -->
                                        <div class="placeholder-chart">임시 차트 영역</div>
                                    </div>
                                    <div class="chart">
                                        <p class="caption">숙련도 분포도</p>
                                        <!-- 차트 영역 -->
                                        <div class="placeholder-chart">임시 차트 영역</div>
                                    </div>
                                </div>
                            </li>
                            <li id="member" class="" role="tabpanel">
                                <h2 class="section-title">팀원 데이터</h2>
                                <div class="chart-box">
                                    <div class="chart">
                                        <p class="caption">숙련도 분포도</p>
                                        <!-- 차트 영역 -->
                                        <div class="placeholder-chart">임시 차트 영역</div>
                                    </div>
                                    <div class="chart">
                                        <p class="caption">숙련도 분포도</p>
                                        <!-- 차트 영역 -->
                                        <div class="placeholder-chart">임시 차트 영역</div>
                                    </div>
                                </div>
                            </li>
                        </ul>
                    </li>
                    <li id="department" role="tabpanel">
                        <div class="filter-selector">
                            <div class="select-box">
                                <select name="">
                                    <option value="">대웅제약</option>
                                    <option value="">대웅제약</option>
                                    <option value="">대웅제약</option>
                                </select>
                            </div>
                            <div class="select-box">
                                <select name="">
                                    <option value="">인사팀</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="data-status">
                            <p class="desc bigger"><strong>인사팀</strong>은 스마트워크 진행을 위한 <br>도구 숙련도가 ‘<strong>높은</strong>’ 수준이며 <br>전체 부서 중 ‘<strong>1등</strong>’ 입니다.</p>
                            <ul class="grade-info">
                                <li>참여도 <strong>25</strong>% <span class="range">(155명 중 100명 응시)</span></li>
                                <li><strong>1/52</strong> <span class="range">(그룹부서 전체)</span></li>
                            </ul>
                            <div class="progress">
                                <ul class="progress-text">
                                    <li class="verylow">
                                        <div class="progress-box">매우낮은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>
                                    </li>
                                    <li class="low">
                                        <div class="progress-box">낮은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>	
                                    </li>
                                    <li class="normal">
                                        <div class="progress-box">보통</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>
                                    </li>
                                    <li class="high on">
                                        <div class="progress-box">높은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>
                                    </li>
                                    <li class="veryhigh">
                                        <div class="progress-box">매우높은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>	
                                    </li>
                                </ul>
                                <progress max="100" value="0" class="progress-main" aria-labelledby="Progress-id">
                                    <div class="Progress-bar" role="presentation">
                                        <span class="Progress-value" style="width: 80%;">&nbsp;</span>
                                    </div>
                                </progress>
                            </div>
                        </div>
                        <div class="chart-box">
                            <p class="title">부서별 데이터</p>
                            <div class="chart">
                                <p class="caption">숙련도 분포도</p>
                                <!-- 차트 영역 -->
                                <div class="placeholder-chart">임시 차트 영역</div>
                            </div>
                            <div class="chart">
                                <p class="caption">소속부서의 도구별 위치</p>
                                <!-- 차트 영역 -->
                                <div class="placeholder-chart">임시 차트 영역</div>
                                <p class="description">가이드 접속 비율과 점수의 상관관계를 나타내는 그래프입니다. <br>Zoom 도구 점수가 가장 높고 LineWorks 도구 점수가 가장 낮으며 점수 차이는 15점입니다.</p>
                            </div>
                            <p class="legend"> x : 점수  /  y : 도구  /  마커 : 최고점수값, 중간점수값 <br>배경 색 : 하위30%, 중위60%, 상위10%  /  바 : 소속 부서 점수</p>
                        </div>
                    </li>
                    <li id="affiliate" role="tabpanel">
                        <div class="filter-selector">
                            <div class="select-box">
                                <select name="">
                                    <option value="">대웅제약</option>
                                    <option value="">대웅제약</option>
                                    <option value="">대웅제약</option>
                                </select>
                            </div>
                            <div class="select-box">
                                <select name="">
                                    <option value="">인사팀</option>
                                </select>
                            </div>
                        </div>
                        
                        <div class="data-status">
                            <p class="desc bigger"><strong>인사팀</strong>은 스마트워크 진행을 위한 <br>도구 숙련도가 ‘<strong>높은</strong>’ 수준이며 <br>전체 부서 중 ‘<strong>1등</strong>’ 입니다.</p>
                            <ul class="grade-info">
                                <li>참여도 <strong>25</strong>% <span class="range">(155명 중 100명 응시)</span></li>
                                <li><strong>1/52</strong> <span class="range">(그룹부서 전체)</span></li>
                            </ul>
                            <div class="progress">
                                <ul class="progress-text">
                                    <li class="verylow">
                                        <div class="progress-box">매우낮은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>
                                    </li>
                                    <li class="low">
                                        <div class="progress-box">낮은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>	
                                    </li>
                                    <li class="normal">
                                        <div class="progress-box">보통</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>
                                    </li>
                                    <li class="high on">
                                        <div class="progress-box">높은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>
                                    </li>
                                    <li class="veryhigh">
                                        <div class="progress-box">매우높은</div>
                                        <div class="progress-icon"><span class="progress-check"></span></div>	
                                    </li>
                                </ul>
                                <progress max="100" value="0" class="progress-main" aria-labelledby="Progress-id">
                                    <div class="Progress-bar" role="presentation">
                                        <span class="Progress-value" style="width: 80%;">&nbsp;</span>
                                    </div>
                                </progress>
                            </div>
                        </div>
                        <div class="chart-box">
                            <p class="title">계열사별 데이터</p>
                            <div class="chart">
                                <p class="caption">숙련도 분포도</p>
                                <!-- 차트 영역 -->
                                <div class="placeholder-chart">임시 차트 영역</div>
                            </div>
                            <div class="chart">
                                <p class="caption">소속부서의 도구별 위치</p>
                                <!-- 차트 영역 -->
                                <div class="placeholder-chart">임시 차트 영역</div>
                                <p class="description">가이드 접속 비율과 점수의 상관관계를 나타내는 그래프입니다. <br>Zoom 도구 점수가 가장 높고 LineWorks 도구 점수가 가장 낮으며 점수 차이는 15점입니다.</p>
                            </div>
                            <p class="legend"> x : 점수  /  y : 도구  /  마커 : 최고점수값, 중간점수값 <br>배경 색 : 하위30%, 중위60%, 상위10%  /  바 : 소속 부서 점수</p>
                        </div>
                    </li>
                </ul>
            </div>
        </section>
    </div>
    <!-- CONTENTS [e] -->
    <!-- FOOTER -->
<%--     <%@ include file="../../include-pages/footer.jsp" %> --%>
  </body>
</html>

<script>
    //change header text
    $(".header-title").text("통계 페이지");
</script>