<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = 1;
		var tmp2 = "";
		var i = 1;
		var in_chk1 = 0; //이름
		var in_chk2 = 0; //사업자번호
		
		$(document).ready(function(){
					
			fn_yearList();
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtCompRecordList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
				fn_insertPrvCnrtDuty();				
			});										
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			$("#STATE_GB1").on("click", function(e){ //				
				$("#CNRT_AGENCY_NM").val("강남구");
				$("#CNRT_AGENCY_NM").attr("readonly", true);
				
			});
			
			$("#STATE_GB2").on("click", function(e){ //
				if($("#CNRT_AGENCY_NM").val() == "강남구"){
					$("#CNRT_AGENCY_NM").val("");
				}
				$("#CNRT_AGENCY_NM").attr("readonly", false);
				$("#CNRT_AGENCY_NM").focus();
			});
			
			$("#CNRT_AMT").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
			
			
			$(document).keydown(function(e){
				if(e.target.nodeName != "INPUT" && e.target.nodeName != "TEXTAREA"){
			        if(e.keyCode == 8){		        
			        	return false;
			        }
			    }
				
				if(e.target.readOnly){ // readonly일 경우 true
		            if(e.keyCode == 8){
		                return false;
		           }
				}
			});
			
		});
		
		
		
		
		//입력한 문자열 전달
	    function inputNumberFormat(obj) {
	        obj.value = comma(uncomma(obj.value));
	    }
	       
	    //콤마찍기
	    function comma(str) {
	        str = String(str);
	        return str.replace(/(\d)(?=(?:\d{3})+(?!\d))/g, '$1,');
	    }
	 
	    //콤마풀기
	    function uncomma(str) {
	        str = String(str);
	        return str.replace(/[^\d]+/g, '');
	    }
		
		
		function fn_prvCnrtCompRecordList(){
			var comSubmit = new ComSubmit();
			
			comSubmit.addParam("ID", "${param.ID}");
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompRecordList.do' />");
			comSubmit.submit();
		}
			
		
		
		function fn_yearList(){
			
			var comAjax = new ComAjax();
			comAjax.setUrl("/scms/common/yearList.do");
			comAjax.setCallback("fn_yearListCallback");			
			comAjax.ajax();
		}
		
		function fn_yearListCallback(data){
			data = $.parseJSON(data);
			var str = "";
			var i = 1;
					
			$.each(data.list, function(key, value){										
					str += "<option value=\"" + value.YEAR + "\">" + value.YEAR + "년</option>";
					i++;
			});
			
			$("#CNRT_YEAR1").append(str);			
		}	
		
		
		function fn_insertPrvCnrtDuty(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			
			if($('#CNRT_AGENCY_NM').val() == "")
			{
				alert('발주처를 입력해 주세요!!');
				$('#CNRT_AGENCY_NM').focus();
				return false();
			}
			
			if($('#CNRT_YEAR1').val() == "")
			{
				alert('년도를 선택해 주세요!!');
				$('#CNRT_YEAR1').focus();
				return false();
			}
			
			if($('#CNRT_MONTH').val() == "")
			{
				alert('월을 선택해 주세요!!');
				$('#CNRT_MONTH').focus();
				return false();
			}
											
			tmp = $('#CNRT_YEAR1').val() + $('#CNRT_MONTH').val(); 
			$('#CNRT_YEAR').val(tmp);
			
			
			if($('#CNRT_NM').val() == "")
			{
				alert('계약명을 입력해 주세요!!');
				$('#CNRT_NM').focus();
				return false();
			}
			
			if($('#CNRT_AMT').val() == "")
			{
				alert('계약금액을 입력해 주세요!!');
				$('#CNRT_AMT').focus();
				return false();
			}
			
			tmp = $('#CNRT_AMT').val();
			result = tmp.replace(/,/g, '');
			$('#CNRT_AMT').val(result);
			
			if($('#CNRT_DETAIL').val() == "")
			{
				alert('계약내용을 입력해 주세요!!');
				$('#CNRT_DETAIL').focus();
				return false();
			}
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{												
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtDuty.do' />");									
				comSubmit.submit();							
			}
		}
		
		
		function fn_addFile(){
			var str = "<p><input type='file' name='file_"+(gfv_count++)+"'> <a href='#this' class='btn' name='delete'>삭제</a></p>";
			$("#fileDiv").append(str);
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
					
	</script>
	
	
<style>

input:read-only { 
	background-color:blue;
	}
</style>	
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
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
<input type="hidden" id="COMP_ID" name="COMP_ID" value="${param.ID}">
<input type="hidden" id="DATA_GB" name="DATA_GB" value="1">
<input type="hidden" id="CNRT_CHOOSE_REASON" name="CNRT_CHOOSE_REASON" value="">
<input type="hidden" id="CNRT_EVAL" name="CNRT_EVAL" value="">
<input type="hidden" id="SAUP_ID" name="SAUP_ID" value="">
<input type="hidden" id="CNRT_YEAR" name="CNRT_YEAR" value="">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="1">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">과업실적 등록</td>
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
	            <td width="120" class="tbl_field">발주처구분</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="radio" id="STATE_GB1" name="STATE_GB" value="2" class="input" onfocus="this.blur()"> 강남구 &nbsp; <input type="radio" id="STATE_GB2" name="STATE_GB" value="1" class="input" onfocus="this.blur()"> 타시군구 &nbsp;</td>									
	          </tr>	      	  	                   
	          <tr>
	            <td width="120" class="tbl_field">발주처</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_AGENCY_NM" name="CNRT_AGENCY_NM" class="input" style="width:300;"> ex) 서울시, 성남시 분당구</td>									
	          </tr>                 				 	          
	          <tr>
	            <td width="120" class="tbl_field">계약년월</td>
	            <td width="607" colspan="3" class="tbl_list_left">	            	           	            
	            <select id="CNRT_YEAR1" name="CNRT_YEAR1" class="input">
	            <option value="">--------</option>
	            </select> &nbsp;
	            <select id="CNRT_MONTH" name="CNRT_MONTH" class="input">
	            <option value="">--------</option>
	            <option value="01">&nbsp;1월</option>
	            <option value="02">&nbsp;2월</option>
	            <option value="03">&nbsp;3월</option>
	            <option value="04">&nbsp;4월</option>
	            <option value="05">&nbsp;5월</option>
	            <option value="06">&nbsp;6월</option>
	            <option value="07">&nbsp;7월</option>
	            <option value="08">&nbsp;8월</option>
	            <option value="09">&nbsp;9월</option>
	            <option value="10">10월</option>
	            <option value="11">11월</option>
	            <option value="12">12월</option>
	            </select> 
	            
	            </td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">사업명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_NM" name="CNRT_NM" class="input" style="width:300;"></td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">계약금액</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="CNRT_AMT" name="CNRT_AMT" class="input" style="width:300;"> <b>원</b></td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">과업내용</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="10" cols="76" id="CNRT_DETAIL" name="CNRT_DETAIL" class="input" style="width:557;"></textarea></td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0">
							<a href="#this" class="btn" id="delete" name="delete">삭제</a> (<b>10M이하</b>)
						</p>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a> (<b>10M이하</b>)           
	            </td>
	          </tr>	          	          	        
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;"></textarea></td>									
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
