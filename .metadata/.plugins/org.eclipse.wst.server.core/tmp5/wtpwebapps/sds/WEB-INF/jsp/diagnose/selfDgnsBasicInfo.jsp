<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>자가진단시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		$(document).ready(function(){
			fn_deptList();
			fn_judgmentList();
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_slefDgnsMastList();
			});
			
			$("#update").on("click", function(e){ //수정하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_selfDgnsUpdate();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#delete").on("click", function(e){ //수정하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_selfDgnsDelete();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#SNUM").change(function(){
				var num=0;		
				
				num = $("#SNUM option:selected").val();
				
				$("#kkk").val("직접수행");

				
			});
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicWrite();
			});
			
			$("#indsim").on("click", function(e){ //1,2,3심
				e.preventDefault();								
				fn_indTrialWrite();				
			});
			
			
		});
		
		function fn_slefDgnsMastList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/diagnose/selfDgnsMastList.do' />");
			comSubmit.submit();
		}
		
		
		function fn_incidentBasicWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicWrite.do' />");
			comSubmit.submit();
		}
		
		
		
		
		function fn_deptList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/common/deptList.do' />");
			comAjax.setCallback("fn_deptListCallback");			
			comAjax.ajax();
		}
		
		function fn_deptListCallback(data){
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.DEPT_ID + "\">" + value.DEPT_NAME + ",</option>";
					i++;
			});
			
			$("#I_DEPT_CD").append(str);			
		}	
		
		
		
		function fn_selfDgnsUpdate(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			var c_gn_gb = "";
			
			
			if($('#TITLE').val() == "")
			{
				alert('제목을 입력하세요!!');
				$('#TITLE').focus();
				return false();
			}
			
			/*			
			if($(":input:radio[name=SBT_GB]:checked").val() == "" || $(":input:radio[name=SBT_GB]:checked").val() == null)
			{
				alert('구분을 선택하세요!!');
				$('#SBT_GB').focus();
				return false();
			}
			
			
			
			
			if($('#DFNDNT_NAME').val() == "")
			{
				alert('피고를 입력하세요!!');
				$('#DFNDNT_NAME').focus();
				return false();
			}
			
			
			tmp = $("#I_DEPT_CD > option:selected").text();
			tmp = tmp.substring(0, tmp.length-1);
			
			if(tmp == "" || tmp == null)
			{
				alert('소관부서를 선택하세요!!');
				$('#I_DEPT_CD').focus();
				return false();
			}
			
			
			if($("input:checkbox[id='C_GN_GB']").is(":checked")) 
				c_gn_gb = "1";
			*/
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/diagnose/insAndUpDgnsMast.do' />");				
				comSubmit.addParam("SFILE", $('#SFILE').val());
				comSubmit.addParam("excelFile", $('#excelFile').val());
				comSubmit.addParam("imgFile", $('#imgFile').val());
				comSubmit.addParam("GB", "U");//수정
											
				comSubmit.submit();
			}
		}
		
		
		
		function fn_selfDgnsDelete(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{							
				comSubmit.setUrl("<c:url value='/diagnose/deleteDgnsMast.do' />");							
				comSubmit.submit();
			}
		}
		
		function fn_judgmentList(){
			
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/common/selectJudgmentList.do' />");
			comAjax.setCallback("fn_selectJudgmentListCallback");			
			comAjax.addParam("GR_CD", "15");
			comAjax.ajax();
		}
		
		function fn_selectJudgmentListCallback(data){			
			var str = "";
			var i = 1;
			
			str = "<option value=\"\">----------</option>";
			
			$.each(data.list, function(key, value){										
				str += "<option value=\"" + value.CODE + "\">" + value.NAME + "</option>";
				i++;
			});
			
			$("#LAST_RSLT_GB").append(str);			
		}
		
		
		
		
		
		
	</script>
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_ID" name="DEL_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEL_DEPT" name="DEL_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="DGNS_NO" name="DGNS_NO" value="${map.DGNS_NO}">
<input type="hidden" id="DGNS_ID" name="DGNS_ID" value="${map.DGNS_NO}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sds/images/popup_title.gif" align="absmiddle">자가진단표 등록하기</td>
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
			<li class="Lcurrent" id='tab1'><span><a href="#this" class="btn" id="basic">기본정보</a></span></li>			
			<!-- <li id='tab2'><span><a href="#this" class="btn" id="indsim">1,2,3심</a></span></li>
			<li id='tab3'><span><a href="#this" class="btn" id="1sim-2">신청사건</a></span></li>	 		
	 		<li id='tab4'><span><a href="#this" class="btn" id="1sim-3">신청사건(즉시항고)</a></span></li>	 		
	 		<li id='tab5'><span><a href="#this" class="btn" id="3sim-2">환송후사건</a></span></li>	 		
	 		<li id='tab6'><span><a href="#this" class="btn" id="3sim-3">소송비용확정결정</a></span></li> -->	 		
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
	            <td width="120" class="tbl_field">제목</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="TITLE" name="TITLE" value="${map.TITLE}" class="input" style="width:500;"></td>										
	          </tr>	                				 
	          <tr>
	            <td width="120" class="tbl_field">상단글</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="HEADCONT" name="HEADCONT" class="input" style="width:500px;height:60px;" rows="4" cols="50">${map.HEADCONT}</textarea></td>											
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">하단글</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="TAILCONT" name="TAILCONT" class="input" style="width:500px;height:60px;" rows="4" cols="50">${map.TAILCONT}</textarea></td>											
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="BIGO" name="BIGO" value="${map.BIGO}" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">진단표파일</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="file" id="SFILE" name="SFILE" style="width:500;"> ${map.ORIGINAL_FILE_NAME}</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">엑셀양식</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="file" id="excelFile" name="excelFile" style="width:500;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">하단이미지</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="file" id="imgFile" name="imgFile" style="width:500;"> ${map.ORIGINAL_IMG_NAME}</td>									
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
                    <td><img src="/sds/images/btn_type0_head.gif"></td>
                    <td background="/sds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/sds/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
                  <tr>
                    <td><img src="/sds/images/btn_type0_head.gif"></td>
                    <td background="/sds/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="delete"><font color="white">삭제</font></a></td>
                    <td><img src="/sds/images/btn_type0_end.gif"></td>
                  </tr>
                </table>
                </td>                               	
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/sds/images/btn_type1_head.gif"></td>
                    <td background="/sds/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/sds/images/btn_type1_end.gif"></td>
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
