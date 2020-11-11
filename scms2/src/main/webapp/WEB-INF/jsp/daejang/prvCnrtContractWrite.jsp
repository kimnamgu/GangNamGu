<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var tmp2 = "";
		var i = 1;
		var in_chk1 = 0; //이름
		var in_chk2 = 0; //사업자번호
		
		$(document).ready(function(){
			
			//fn_structList(); //구조콤보			
			
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtContractList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				if( $('#USER_RIGHT').val() == 'A')
				{
					fn_insertPrvCnrtContract();
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
			
			
			$("#juso").on("click", function(e){ //주소찾기					
				e.preventDefault();
				var pop = window.open("/scms/daejang/samplepop.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			
			$("#basic").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentBasicWrite();
			});
			
			$("#indsim").on("click", function(e){ //1,2,3심
				e.preventDefault();								
				fn_indTrialWrite();				
			});
			$('#close1').click(function() {
		        $('#pop').hide();
		    });
			
			$('#close2').click(function() {
		        $('#pop').hide();
		    });
			
			
			$('#name_chk').click(function() {
				if($("#COMP_NM").val().length > 1)
				{			
					fn_name_chk();
				}
				else
				{
					alert('업체이름을 입력해 주세요!');	
				}
		    });
			
			$('#saup_chk').click(function() {
				if($("#COMP_SAUP_NO").val().length == 10)
				{			
					fn_saup_chk();
				}
				else
				{
					alert('사업자 번호  10자리를 입력해 주세요!');	
				}
		    });
			
			
			$('#open').click(function() {
		        
				if($("#USER_NAME").val().length < 1){					
					alert('찾으시려는 이름을 입력하세요');
					$("#USER_NAME").focus();
					return;
				}
				
				$('#pop').show();
		        fn_jikwonlist();
		    });
			
		});
		
		function fn_prvCnrtContractList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/PrvCnrtContractList.do' />");
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
		
		
		
		
		
		
		function fn_name_chk(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/daejang/chkSaup.do' />");			
			comAjax.addParam("COMP_NM", $("#COMP_NM").val());			
			comAjax.setCallback("fn_name_chkCallback");			
			comAjax.ajax();		
		}
		
		
		function fn_name_chkCallback(data){
			data = $.parseJSON(data);
			
			var str = "";
			var i = 0;
				
			$.each(data.list, function(key, value){										
				//alert(value.COMP_SAUP_NO);					
				i++;
			});
			//alert('end');
			
			if(i == 0)
			{
				alert('등록 가능한  업체입니다!!');
				in_chk1 = 1;
			}	
			else{
				alert('이미 등록외어 있는 회사 입니다.');
				in_chk1 = 2;
			}
		}	
		
		
		
		function fn_saup_chk(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("<c:url value='/daejang/chkSaup.do' />");			
			comAjax.addParam("COMP_SAUP_NO", $("#COMP_SAUP_NO").val());			
			comAjax.setCallback("fn_saup_chkCallback");			
			comAjax.ajax();
		
		}
		
		
		function fn_saup_chkCallback(data){
			data = $.parseJSON(data);
			
			var str = "";
			var i = 0;
			var tmp = "";
				
			$.each(data.list, function(key, value){										
				//alert(value.COMP_SAUP_NO);					
				i++;
			});
			//alert('end');
			
			if(i == 0)
			{
				alert('등록 가능한  업체입니다!!');
				in_chk2 = 1;
			}	
			else{
				alert('이미 등록외어 있는 회사 입니다.');
				in_chk2 = 2;
			}
		}	
		
		
		
		
		function fn_insertPrvCnrtContract(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			if($('#COMP_ID').val() == "")
			{
				alert('엽체를 선택해 주세요!!');
				$('#COMP_ID').focus();
				return false();
			}
											
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{												
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtContract.do' />");									
				comSubmit.submit();							
			}
		}
		
		
		function trim(str) {
		    return str.replace(/(^\s*)|(\s*$)/gi, '');
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
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="CNRT_DEPT_CD" name="CNRT_DEPT_CD" value="">
<input type="hidden" id="CNRT_DEPT_NM" name="CNRT_DEPT_NM" value="">
<input type="hidden" id="CNRT_PERSON_ID" name="CNRT_PERSON_ID" value="">
<input type="hidden" id="CNRT_PERSON_NM" name="CNRT_PERSON_NM" value="">
<input type="hidden" id="CNRT_PERSON_TEL" name="CNRT_PERSON_TEL" value="">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">계약정보 등록</td>
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
                        
        <table width="100%" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td class="margin_btn" colspan="2"></td>
          </tr>
        </table>
        
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">
	      	  <tr>
	            <td width="120" class="tbl_field">업체선택</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_ID" name="COMP_ID" class="input" style="width:200;"> &nbsp;<a href="#this" class="btn" id="name_chk">선택</a></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">사업선택</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="SAUP_ID" name="SAUP_ID" class="input" style="width:200;"> &nbsp;<a href="#this" class="btn" id="saup_chk">선택</a></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">기관구분</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="radio" id="DATA_GB1" name="DATA_GB" value="1" class="input"> 과거실적등록 &nbsp; <input type="radio" id="DATA_GB2" name="DATA_GB" value="2" class="input"> 현재진행계약</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">기관명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_AGENCY_NM" name="CNRT_AGENCY_NM" class="input" style="width:200;"></td>									
	          </tr>                 				 	          
	          <tr>
	            <td width="120" class="tbl_field">계약년월</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_YEAR" name="CNRT_YEAR" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">계약명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_NM" name="CNRT_NM" class="input" style="width:200;"></td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">계약금액</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_AMT" name="CNRT_AMT" class="input" style="width:200;"></td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">계약내용</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_DETAIL" name="CNRT_DETAIL" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">선정사유</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_CHOOSE_REASON" name="CNRT_CHOOSE_REASON" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">평가정보</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_EVAL" name="CNRT_EVAL" class="input" style="width:200;"></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">상태값</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="radio" id="STATE_GB1" name="STATE_GB" value="1" class="input"> 선정사유 &nbsp; <input type="radio" id="STATE_GB2" name="STATE_GB" value="2" class="input"> 평가등록</td>									
	          </tr>	              	          	         
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="BIGO" name="BIGO" class="input" style="width:200;"></td>									
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
                    <td background="/scms/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
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
