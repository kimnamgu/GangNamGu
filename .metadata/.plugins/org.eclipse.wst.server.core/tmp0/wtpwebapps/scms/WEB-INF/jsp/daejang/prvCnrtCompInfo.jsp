<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
pageContext.setAttribute("cr", "\r"); //Space
pageContext.setAttribute("cn", "\n"); //Enter
pageContext.setAttribute("crcn", "\r\n"); //Space, Enter
pageContext.setAttribute("br", "<br/>"); //br 태그
%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtCompInsList();
			});
			
			$("#update").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				/*if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{*/
					fn_prvCnrtCompUpdate();
				/*}
				else{
					alert('권한이 없습니다.');
				}*/
			});
			
			
			$("#del").on("click", function(e){ //삭제 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{
					fn_deletePrvCnrtComp();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#record").on("click", function(e){ //
				e.preventDefault();								
				fn_recordList();				
			});
			
			$("#reason").on("click", function(e){ //
				e.preventDefault();								
				fn_reasonList();				
			});
			
			$("#eval").on("click", function(e){ //
				e.preventDefault();								
				fn_evalList();				
			});
			
			
			$("a[name='file']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});
			
		});
		
		function fn_prvCnrtCompInsList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompInsList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_recordList(){			
			var comSubmit = new ComSubmit();
			comSubmit.addParam("ID", "${map.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompRecordList.do' />");
			comSubmit.submit();
			
		}
		
		
		function fn_reasonList(){			
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${map.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtReasonList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_evalList(){			
			var comSubmit = new ComSubmit();			
			comSubmit.addParam("ID", "${map.ID}");			
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtEvalList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_stateList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/stateList.do' />");
			comAjax.setCallback("fn_stateListCallback");			
			comAjax.ajax();
			
		}
		
		function fn_stateListCallback(data){
			data = $.parseJSON(data);
			
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.CODE_ID + "\">" + value.CODE_NAME + "</option>";
					i++;
			});
			//alert(str);
			$("#STATE").append(str);			
		}	
		
		
		
		
		
		function fn_prvCnrtCompUpdate(){		
			var comSubmit = new ComSubmit("form1");			
			
			comSubmit.addParam("ID","${map.ID}");
			comSubmit.addParam("PARENT_ID", "${map.ID}");
			comSubmit.addParam("BOARD_GB", "5");  //실적 1, 평가 2 발주계획 3 기술자보유 4 업체등록 5
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompUpdate.do' />");
			comSubmit.submit();
		}
		
				
		function fn_deletePrvCnrtComp(){		
			var comSubmit = new ComSubmit("form1");
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{	
				comSubmit.addParam("ID","${map.ID}");
				comSubmit.setUrl("<c:url value='/daejang/deletePrvCnrtComp.do' />");
				comSubmit.submit();
			}
		}
		
		function trim(str) {
		    return str.replace(/(^\s*)|(\s*$)/gi, '');
		}	

		function jusoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
				
			    
				// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
				//document.form1.roadFullAddr.value = roadFullAddr;
				document.form1.roadAddrPart1.value = roadAddrPart1;
				//document.form.roadAddrPart2.value = roadAddrPart2;
				document.form1.addrDetail.value = addrDetail;
				//document.form.engAddr.value = engAddr;
				document.form1.jibunAddr.value = jibunAddr;				
				document.form1.ZIP_NO.value = zipNo;
				
				
				$('#COMP_ADDR').val(jibunAddr + " " + addrDetail);
				$('#COMP_ADDR_ROAD').val(roadAddrPart1);
		
		}
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}	
	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form name="form1" id="form1">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="COMP_ADDR" name="COMP_ADDR" value="">
<input type="hidden" id="COMP_ADDR_ROAD" name="COMP_ADDR_ROAD" value="">
<input type="hidden" id="COMP_CAPITAL_DETAIL" name="COMP_CAPITAL_DETAIL" value="">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>
<c:set var="w_id" value="${map.INS_ID}"/> 
<c:set var="l_id" value="${sessionScope.userinfo.userId}"/>

<table border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">업체등록</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                                               
        <div class="LblockTab" style="position: relative;">
		<ul class="Lclear">
			<li class="Lcurrent" id='property'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li>
			<li id='tab2'><span><a href="#this" class="btn" id="record">기술능력정보</a></span></li>			
			<li id='tab3'><span><a href="#this" class="btn" id="reason">선정사유</a></span></li>
			<li id='tab4'><span><a href="#this" class="btn" id="eval">부서평가정보</a></span></li>						
		</ul>
		</div>
		
		                
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	      	  <tr>
	            <td width="130" class="tbl_field">업체명</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_NM}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">대표자명</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_HEAD_NM}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">사업자번호</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	            <c:out value="${fn:substring(map.COMP_SAUP_NO,0,3)}"/>-<c:out value="${fn:substring(map.COMP_SAUP_NO,3,5)}"/>-<c:out value="${fn:substring(map.COMP_SAUP_NO,5,10)}"/>	            	           
	            </td>
	          </tr>                 				 	          
	          <tr>
	            <td width="130" class="tbl_field">주소</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_ADDR}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">도로명</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_ADDR_ROAD}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">전화번호</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_TEL}</td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">업체규모</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_SIZE}</td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">재무상태</td>
	            <td width="607" colspan="3" class="tbl_list_left"><fmt:formatNumber value="${map.COMP_CAPITAL}" pattern="###,###,###" /> 원</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">상근직원수</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.COMP_EMPLOYEE_CNT} 명</td>									
	          </tr>	        
	          <tr>
	            <td width="130" class="tbl_field">업무유형</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	            <c:choose> 
				    <c:when test="${map.COMP_FIELD1_GB eq '100'}">
				         공사
				    </c:when> 
				    <c:when test="${map.COMP_FIELD1_GB eq '010'}">
				         용역 
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '001'}">
				         물품
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '110'}">
				         공사,용역 
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '101'}">
				         공사,물품
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '011'}">
				         용역,물품
				    </c:when>
				    <c:when test="${map.COMP_FIELD1_GB eq '111'}">
				         공사,용역,물품
				    </c:when>			 
				</c:choose>
	            </td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">주요과업사항</td>
	            <td width="607" colspan="3" class="tbl_list_left">${fn:replace(map.COMP_MAIN_WORK, crcn, br)}</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">회사소개서</td>
	            <td colspan="3" width="607" class="tbl_list_left">
					<c:forEach var="row" items="${list }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>
				</td>
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">현장점검 출장복명서</td>
	            <td colspan="3" width="607" class="tbl_list_left">
					<c:forEach var="row" items="${list6 }">
						<p>
							<input type="hidden" id="FID" value="${row.ID }">
							<a href="#this" name="file">${row.ORIGINAL_FILE_NAME }</a> 
							(${row.FILE_SIZE }kb)
						</p>
					</c:forEach>
				</td>
	          </tr>	         	          	         
	          <tr>
	            <td width="130" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left">${fn:replace(map.BIGO, crcn, br)}</td>									
	          </tr>
	      </table>
	      
	     
	      
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td></td>
            <td align="right">
            <table border="0" cellspacing="0" cellpadding="0">
              <tr>              
               <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/scms/images/btn_type0_head.gif"></td>
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/scms/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                                                  
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/scms/images/btn_type1_head.gif"></td>
                    <td background="/scms/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/scms/images/btn_type1_end.gif"></td>
                  </tr>
                </table>                
                </td>                               
              </tr>
            </table>
            </td>
          </tr>
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        <!-- -------------- 버튼 끝 --------------  -->
        </td>
      </tr>
    </table>
    </td>    
  </tr>
</table>
</form>
<%@ include file="/WEB-INF/include/include-body.jspf" %>

</body>
</html>
