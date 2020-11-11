<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>

<script type="text/javascript">

$(document).ready(function(){
	/* 로그아웃 */
	$("#logout").on("click", function(e){
		e.preventDefault();				
		fn_logout();
	});
});

//로그아웃
function fn_logout(){
	var comSubmit = new ComSubmit();			
	comSubmit.setUrl("<c:url value='/logoutVisitor.do' />");
	comSubmit.submit();
}
</script>


</head>
<body bgcolor="#FFFFFF">

<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>

<!-- 타이틀 -->
<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/service/images/popup_title.gif" align="absmiddle">
	</a>일자리 컨텐츠 조회
</div>

<br>
	
<div class="card" style="width:700px;margin-left:20px;">
    <div class="card-header">
	    <div style="float:left;">
	        <strong>공공사업참여자 응시자 정보 상세조회</strong>
	    </div>
       	<div style="float:right;">
			<a href="javascript:history.back()" class="btn btn-success"><i class="fa fa-reply"></i> 목록으로</a>
		</div> 
    </div>
    <div class="card-body card-block">
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">고시공고명</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.notification_nm}" style="background-color:white!important;width:500px!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">uuid</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.uuid}" style="background-color:white!important;width:500px!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">성명</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.name}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">주민번호</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.jumin_no}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">주소</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.address}" style="background-color:white!important;width:500px!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">상세주소</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.address_detail}" style="background-color:white!important;width:500px!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">우편번호</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.post_no}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">전화번호</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.phone}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">참여희망사업</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.hope_business}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">기타사항</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.etc}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">장애인(본인,가족여부)</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.obstacle}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">장애종류(장애정도)</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.obstacle_degree}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">장애종류(장애종류)</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.obstacle_class}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">취업보호.지원대상자(국가유공자)</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.support_yn}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">한부모가정</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.one_parrent_yn}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">노숙자</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.homeless}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">결혼이주여성</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.marriage_mig_yn}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">북한탈주민</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.north_escape_yn}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">자격증정보</label>
                </div>
                
                
                <c:forEach var="list" items="${fn:split(map.license_info, '|')}" varStatus="status">
				    <c:forEach var="map" items="${list}" varStatus="status">
				    	<c:out value="${map}" /><br>
					</c:forEach>
				</c:forEach>

                
                
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.license_info}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">응시분야</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.apply_class}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">응시날짜</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.apply_date}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">응시상태</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.apply_state}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">담당자</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.master_nm}" style="background-color:white!important;" readonly>
                </div>
            </div>
            <div class="row form-group">
                <div class="col col-md-3">
                    <label for="text-input" class=" form-control-label">담당자코멘트</label>
                </div>
                <div class="col-12 col-md-9">
                    <input type="text" id="text-input" name="text-input" class="form-control" value="${map.master_comment}" style="background-color:white!important;" readonly>
                </div>
            </div>
    </div>
    <div class="card-footer">

    </div>
</div>

	<%@ include file="/WEB-INF/include/include-body.jspf" %>
	
</body>
</html>