<!--가이드페이지의 lnb  -->
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<aside>
	<div class="header">
		<a href="guide" class="title active">스마트워크 Rule</a>
	</div>
	<div class="body">
		<ul class="accordion-menu" role="navigation">
			<li class="accordion-item expanded">
				<button type="button" aria-expanded="true">도구 가이드</button>
				<ul class="link-menu">
					<!--도구 가이드 목록 출력 ex)zoom, lineworks  -->
				</ul>
			</li>
			<li class="accordion-item expanded">
				<button type="button" aria-expanded="true">보안</button>
				<ul class="link-menu">
					<!--보안 목록출력 ex)VPN, DLP  -->
				</ul>
			</li>
			<li class="accordion-item expanded">
				<button type="button" aria-expanded="true">디지털 창</button>
				<ul class="link-menu">
					<!--디지털창 목록출력 ex)간편도구, 베아월드  -->
				</ul>
			</li>
		</ul>
	</div>
</aside>

<script>
	//도구가이드 목록 출력
	var toolGuideList = ${toolGuideList};

	var toolLiTemplate = "<li>"
			+ "<a href=\"toolguide?tool={tool}\" class=\"toolGuide\"><span class=\"text-icon\" aria-hidden=\"true\">{firstChar}</span>{tool}</a>"
			+ "</li>";

	for (var i = 0; i < toolGuideList.length; i++) {

		$('.link-menu:eq(0)').append(
				toolLiTemplate.replace(/{tool}/gi, toolGuideList[i])
							  .replace("{firstChar}",toolGuideList[i].charAt(0) ));
	}

	//보안 목록 출력
	var priorKnowList = ${priorKnowList};

	var priorKnowTemplate = "<li>"
			+ "<a href=\"priorKnow?tool={tool}\"><span class=\"text-icon\" aria-hidden=\"true\">{firstChar}</span>{priorKnow}</a>"
			+ "</li>";

	for (var i = 0; i < priorKnowList.length; i++) {
		$('.link-menu:eq(1)').append(priorKnowTemplate.replace("{firstChar}", priorKnowList[i].charAt(0))
														.replace("{priorKnow}",priorKnowList[i]).replace("{tool}", priorKnowList[i]));
	}
	
	//디지털창 목록 출력
	var digitalList = ${digitalList};
	
	//한글 -> 영어로 변환
	//익스플로러에서 한글이 포함된 url을 지원하지 않음으로 전환필요
	var digitalTitle = [];
	digitalList.forEach(function(element){
		if(element === "베아월드"){
			digitalTitle.push("BearWorld");
		}else if(element === "간편도구"){
			digitalTitle.push("SimpleTool");
		}else{
			digitalTitle.push(element);
		}
	});
	
	var digitalTemplate = "<li>"
		+ "<a href=\"digital?title={title}\"><span class=\"text-icon\" aria-hidden=\"true\">{firstChar}</span>{digital}</a>"
		+ "</li>";
		
 	for (var i = 0; i < digitalList.length; i++) {
 		
		$('.link-menu:eq(2)').append(
				digitalTemplate.replace("{firstChar}", digitalTitle[i].charAt(0))
								.replace("{digital}", digitalList[i]).replace("{title}", digitalTitle[i]));
	} 
	
</script>