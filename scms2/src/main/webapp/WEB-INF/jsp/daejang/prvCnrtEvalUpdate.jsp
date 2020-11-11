<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>수의계약 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
		var gfv_count = '${fn:length(list)+1}';
		var tmp2 = "";
		var i = 1;
		var in_chk1 = 0; //이름
		var in_chk2 = 0; //사업자번호
		var fchk = 0;  //첨부파일 사이즈 체크
		
		$(document).ready(function(){
			
			$("#CNRT_EVAL").val("${map.CNRT_EVAL}");
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtEvalList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();
			/*	if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_ID').val() == $('#LOGIN_ID').val())
				{*/
					fn_updatePrvCnrtEval();
				/*)}
				else{
					alert('권한이 없습니다.');
				}*/
			});
			
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name^='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			
			$("a[name^='name']").on("click", function(e){ //파일 이름
				e.preventDefault();
				fn_downloadFile($(this));
			});

			
		});
		
		function fn_prvCnrtEvalList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtEvalList.do' />");
			comSubmit.addParam("ID","${param.ID}");
			comSubmit.submit();
		}
		
		
		function fn_updatePrvCnrtEval(){
			var comSubmit = new ComSubmit("form1");
		
			if($('#CNRT_EVAL').val() == "")
			{
				alert('만족도 평가를 선택해 주세요!!');
				$('#CNRT_EVAL').focus();
				return false();
			}
			
			if(fchk)
			{
				alert("첨부파일 사이즈는  한화면에 쵀대 20MB 이내로 등록 가능합니다.");
				return false;
			}	
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{						
				comSubmit.addParam("PARENT_ID", $("#CON_ID").val());
				comSubmit.setUrl("<c:url value='/daejang/updatePrvCnrtEval.do' />");									
				comSubmit.submit();							
			}
		}
		
	
		function fn_addFile(){
						
			var str = "<p>" +
			"<input type='file' id='file_"+(gfv_count)+"' name='file_"+(gfv_count)+"' onchange='fileCheck(this)'>"+
			" <a href='#this' class='btn' id='delete_"+(gfv_count)+"' name='delete_"+(gfv_count)+"'> 삭제</a>" +
			"</p>";
			$("#fileDiv").append(str);
			$("#delete_"+(gfv_count++)).on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
		}
		
		function fn_deleteFile(obj){
			obj.parent().remove();
		}
		
		function fn_downloadFile(obj){
			var idx = obj.parent().find("#FID").val();
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/common/downloadFile.do' />");
			comSubmit.addParam("ID", idx);
			comSubmit.submit();
		}
		
		function fileCheck(input){
			var word;
            var version = "N/A";
            var agent = navigator.userAgent.toLowerCase();
            var name = navigator.appName;

		    if(input.value!=""){				
                // IE old version ( IE 10 or Lower )
                if ( name == "Microsoft Internet Explorer" ) word = "msie ";
                else {
                    // IE 11
                    if ( agent.search("trident") > -1 ) word = "trident/.*rv:";
                    // IE 12  ( Microsoft Edge )
                    else if ( agent.search("edge/") > -1 ) word = "edge/";
                }

                var reg = new RegExp( word + "([0-9]{1,})(\\.{0,}[0-9]{0,1})" );

                if (  reg.exec( agent ) != null  )
                    version = RegExp.$1 + RegExp.$2;

				if( version >= 10 ){
						
						fileSize = input.files[0].size;
					    var maxSize = 10 * 1024 * 1024;//10MB				 
					    if(fileSize > maxSize){					    
					    	alert("파일 사이즈가 10MB를 넘습니다!! [" + comma(fileSize) + " Byte]");
					    }
					    else{
					    	fchk = 0;
					    }
			   	}						
		   }
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
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="WRITE_ID" name="WRITE_ID" value="${map.INS_ID}">
<input type="hidden" id="LOGIN_ID" name="LOGIN_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="CNRT_DEPT_CD" name="CNRT_DEPT_CD" value="">
<input type="hidden" id="CNRT_DEPT_NM" name="CNRT_DEPT_NM" value="">
<input type="hidden" id="CNRT_PERSON_ID" name="CNRT_PERSON_ID" value="">
<input type="hidden" id="CNRT_PERSON_NM" name="CNRT_PERSON_NM" value="">
<input type="hidden" id="CNRT_PERSON_TEL" name="CNRT_PERSON_TEL" value="">
<input type="hidden" id="SAUP_ID" name="SAUP_ID" value="${map.SAUP_ID}">
<input type="hidden" id="DATA_GB" name="DATA_GB" value="2">
<input type="hidden" id="STATE_GB" name="STATE_GB" value="2">
<input type="hidden" id="ID" name="ID" value="${param.ID}">
<input type="hidden" id="CON_ID" name="CON_ID" value="${param.CON_ID}">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="2">
<input type="hidden" id="CNRT_DETAIL" name="CNRT_DETAIL" value="">
<c:set var="u_rt" value="${sessionScope.userinfo.userright}"/>
<c:set var="w_id" value="${map.INS_ID}"/> 
<c:set var="l_id" value="${sessionScope.userinfo.userId}"/>

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">만족도 등록</td>
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
	            <td width="120" class="tbl_field">기관명</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.CNRT_AGENCY_NM}</td>									
	          </tr>                 				 	          
	          <tr>
	            <td width="120" class="tbl_field">계약년월</td>
	            <td width="607" colspan="3" class="tbl_list_left">	            
	            <fmt:parseDate value="${map.CNRT_YEAR}" var="dateFmt1" pattern="yyyyMM"/>
			    <fmt:formatDate value="${dateFmt1}" pattern="yyyy.MM"/>
	            </td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">계약명</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.CNRT_NM}</td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">계약금액</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	            <fmt:formatNumber value="${map.CNRT_AMT}" pattern="###,###,###" /> 원</td>
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">선정사유</td>
	            <td width="607" colspan="3" class="tbl_list_left">${map.CNRT_CHOOSE_REASON}</td>									
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">만족도 평가</td>
	            <td width="607" colspan="3" class="tbl_list_left">	            
	            <select id="CNRT_EVAL" name="CNRT_EVAL" class="input">
	            <option value="">--------</option>	           
	            <option value="S">매우만족</option>
	            <option value="A">만족</option>
	            <option value="B">보통</option>
	            <option value="C">불만족</option>
	            </select> 
	            </td>									
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">첨부파일</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">				
						<c:forEach var="row" items="${list }" varStatus="var">
							<p>
								<input type="hidden" id="FID" name="FID_${var.index }" value="${row.ID }">
								<a href="#this" id="name_${var.index }" name="name_${var.index }">${row.ORIGINAL_FILE_NAME }</a>
								<input type="file" id="file_${var.index }" name="file_${var.index }" onchange="fileCheck(this)"> 
								(${row.FILE_SIZE }kb)
								<a href="#this" class="btn" id="delete_${var.index }" name="delete_${var.index }">삭제</a> (<b>10M이하</b>)
							</p>
						</c:forEach>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a> (<b>10M이하</b>)           
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;">${map.BIGO}</textarea></td>									
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
