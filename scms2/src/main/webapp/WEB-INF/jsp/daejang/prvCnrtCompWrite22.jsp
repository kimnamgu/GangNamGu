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
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_prvCnrtCompInsList();
			});
			
			$("#write").on("click", function(e){ //등록하기 버튼							
				e.preventDefault();				
				fn_serviceContractWrite();				
			});
			
			$("#juso").on("click", function(e){ //주소찾기					
				e.preventDefault();
				var pop = window.open("/scms/daejang/samplepop.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			
			$('#name_chk').click(function() {
				if($("#COMP_NM").val().length > 1)
				{			
					fn_name_chk();
				}
				else
				{
					alert('업체이름을 입력해 주세요!');
					$("#COMP_NM").focus();
				}
		    });
			
			$('#saup_chk').click(function() {
				if($("#COMP_SAUP_NO").val().length < 1)
				{
					alert('사업자 번호를 입력해 주세요.');	
					$("#COMP_SAUP_NO").focus();
				}	
				else if($("#COMP_SAUP_NO").val().length >= 10)
				{			
					fn_saup_chk();
				}
				else
				{
					alert('사업자 번호  10자리를 입력해 주세요!');
					$("#COMP_SAUP_NO").focus();
				}				
		    });
			
			
			$("#COMP_CAPITAL").bind('keyup keydown',function(){
		        inputNumberFormat(this);
		    });
			
			
			$("#COMP_SAUP_NO").bind('keyup keydown',function(){			
				licenseNum(this);
		    });
		    
			
			$("#addFile").on("click", function(e){ //파일 추가 버튼
				e.preventDefault();
				fn_addFile();
			});
			
			$("a[name='delete']").on("click", function(e){ //삭제 버튼
				e.preventDefault();
				fn_deleteFile($(this));
			});
			
			$("#COMP_EMPLOYEE_CNT").bind('keyup keydown',function(){
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
		
		
		function licenseNum(obj){	          
	          var str2 = obj.value;
	          str2 = str2.replace(/[^0-9]/g, '');
	          var tmp = '';
	          
	          if(str2.length < 4){
	              tmp = str2;
	          }else if(str2.length < 7){	        	  
	        	  tmp += str2.substring(0, 3);
	              tmp += '-';
	              tmp += str2.substring(3);	             
	          }else{ 
	        	  
	        	 
	              tmp += str2.substring(0, 3);
	              tmp += '-';
	              tmp += str2.substring(3, 5);
	              tmp += '-';
	              tmp += str2.substring(5);
	              
	              if(tmp.length > 12){
		  				tmp = tmp.substring(0, 12);	        	  
		  	      }
	          }
	          obj.value = tmp;
	    }
		
		function fn_prvCnrtCompInsList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/prvCnrtCompInsList.do' />");
			comSubmit.submit();
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
			var tmp = "";
			var result = "";
			var comAjax = new ComAjax();
			
			comAjax.setUrl("<c:url value='/daejang/chkSaup.do' />");
			tmp = $("#COMP_SAUP_NO").val();		
			result = tmp.replace(/-/g, '');		
			comAjax.addParam("COMP_SAUP_NO", result);			
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
		
				
		function fn_serviceContractWrite(){
			var comSubmit = new ComSubmit("form1");
			var tmp = "";
			var result = "";
			
			if($('#COMP_NM').val() == "")
			{
				alert('업체명을 입력하세요!!');
				$('#COMP_NM').focus();
				return false();
			}
			
			if (in_chk1 == 0)
			{
				alert('업체명 중복체크를 먼저하세요.');
				return false();
			}
			else if (in_chk1 == 2){
				alert('이미 등록된 업체로 입력이 불가합니다.');
				return false();
			}
			
			
			if (in_chk2 == 0)
			{
				alert('사업자 번호 중복체크를 먼저하세요.');
				return false();
			}
			else if (in_chk2 == 2){
				alert('이미 등록된 업체로 입력이 불가합니다.');
				return false();
			}
			
			if( $('#COMP_BUBIN_NO').val().length > 0 &&  $('#COMP_BUBIN_NO').val().length < 13)
			{
				alert('법인번호을 확인해 보세요.');
				$('#COMP_BUBIN_NO').focus();
				return false();
			}
			
			
			if($('#COMP_ZIP_CD').val() == "")
			{
				alert('주소를  검색하세요!!\n\n오른쪽 끝 주소찾기를 클릭하세요.');
				$('#COMP_ZIP_CD').focus();
				return false();
			}
			
			
			tmp = get_check_val();			
			$('#COMP_FIELD1_GB').val(tmp);	 //유형
			
			tmp = get_check_val2();			
			$('#COMP_FIELD2_GB').val(tmp);	 //유형2

			tmp = $('#COMP_SAUP_NO').val();  //사업자번호			
			result = tmp.replace(/-/g, '');
			$('#COMP_SAUP_NO').val(result);
						
			tmp = $('#COMP_BUBIN_NO').val();  //법인번호
			result = tmp.replace(/-/g, '');
			$('#COMP_BUBIN_NO').val(result);
						
			tmp = $('#COMP_CAPITAL').val();   //재무상태 (자본금)
			result = tmp.replace(/,/g, '');
			$('#COMP_CAPITAL').val(result);
			
			tmp = $('#COMP_EMPLOYEE_CNT').val();  //직원수
			result = tmp.replace(/,/g, '');
			$('#COMP_EMPLOYEE_CNT').val(result);
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{					
				comSubmit.setUrl("<c:url value='/daejang/insertPrvCnrtComp.do' />");									
				comSubmit.submit();
				alert('기술능력정보 탭(과업실적 및 기술자 보유현황)의 내용도 등록하시길 바랍니다.!!');
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
				document.form1.COMP_ZIP_CD.value = zipNo;
								
				$('#COMP_ADDR').val(jibunAddr + " " + addrDetail);
				$('#COMP_ADDR_ROAD').val(roadAddrPart1);		
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
		
		
		
		// 첨부파일 확장자 확인 
		function checkImg(obj, ext)
		{ 
			var check = false; 
			var extName = $(obj).val().substring($(obj).val().lastIndexOf(".")+1).toUpperCase(); 
			var str = ext.split(","); 
			for (var i=0;i<str.length;i++) 
			{ 
				if(extName == $.trim(str[i])) 
				{ 
					check = true; 
					break; 
				} else 
					check = false; 
			} 
			if(!check) 
			{ 
				alert(ext+" 파일만 업로드 가능합니다."); 
			}	
			return check; 
		} 
				
		// 첨부파일 용량 확인 
		function checkImgSize(obj, size) 
		{ 
			var check = false; 
			if(window.ActiveXObject) {//IE용인데 IE8이하는 안됨... 
				var fso = new ActiveXObject("Scripting.FileSystemObject"); 
				//var filepath = document.getElementById(obj).value; 
				var filepath = obj[0].value; 
				var thefile = fso.getFile(filepath); 
				sizeinbytes = thefile.size; 
			} else {//IE 외 
				//sizeinbytes = document.getElementById(obj).files[0].size; 
				sizeinbytes = obj[0].files[0].size; 
			} 

			var fSExt = new Array('Bytes', 'KB', 'MB', 'GB'); 
			var i = 0; 
			var checkSize = size; 

			while(checkSize>900) {
				checkSize/=1024; 
				i++; 
			} 

			checkSize = (Math.round(checkSize*100)/100)+' '+fSExt[i]; 

			var fSize = sizeinbytes; 

			if(fSize > size) { 
				alert("첨부파일은 "+ checkSize + " 이하로 등록가능합니다."); 
				check = false; 
			} else { 
				check = true; 
			} 
			return check; 
		}


		// 이미지 선택 
		document.getElementById("file").onchange = function () { 
			alert('kk');
			if(this.value != "") { 

				var extPlan = "JPG, PNG"; 
				var checkSize = 1024*1024*5; // 5MB 
				
				if(!checkImg($('#file'), extPlan) | !checkImgSize($('#file'), checkSize)) { 
					this.value = ""; 
					return; 
				} 
			} 
		};
		
		
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
<input type="hidden" id="COMP_FIELD1_GB" name="COMP_FIELD1_GB" value="">
<input type="hidden" id="COMP_FIELD2_GB" name="COMP_FIELD2_GB" value="">
<input type="hidden" id="COMP_ADDR" name="COMP_ADDR" value="">
<input type="hidden" id="COMP_ADDR_ROAD" name="COMP_ADDR_ROAD" value="">
<input type="hidden" id="COMP_CAPITAL_DETAIL" name="COMP_CAPITAL_DETAIL" value="">
<input type="hidden" id="BOARD_GB" name="BOARD_GB" value="5">
<input type="hidden" id="BOARD_GB2" name="BOARD_GB2" value="6">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/scms/images/popup_title.png" align="absmiddle">업체 신규등록</td>
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
	            <td width="130" class="tbl_field">업체명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_NM" name="COMP_NM" class="input" style="width:300;"> &nbsp;<a href="#this" class="btn" id="name_chk">중복체크</a></td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">대표자명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_HEAD_NM" name="COMP_HEAD_NM" class="input" style="width:300;"></td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">사업자번호</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_SAUP_NO" name="COMP_SAUP_NO" class="input" style="width:300;"> &nbsp;<a href="#this" class="btn" id="saup_chk">중복체크</a></td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">법인번호</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_BUBIN_NO" name="COMP_BUBIN_NO" class="input" style="width:200;"></td>									
	          </tr>                 				 	          
	          <tr>
	            <td width="130" class="tbl_field">주소</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_ZIP_CD" name="COMP_ZIP_CD" class="input" style="width:70;" readonly> &nbsp;<input type="text" id="jibunAddr" name="jibunAddr" class="input" style="width:300;" readonly>&nbsp; <input type="text" id="addrDetail" name="addrDetail" class="input" style="width:100;" readonly>	            
	            <a href="#this" class="btn" id="juso">주소찾기</a>
	            </td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">도로명</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="roadAddrPart1" name="roadAddrPart1" class="input" style="width:400;" readonly></td>									
	          </tr>
	           <tr>
	            <td width="130" class="tbl_field">전화번호 (핸드폰)</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_TEL" name="COMP_TEL" class="input" style="width:200;"> ex) 02-1111-1234</td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">업체규모</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_SIZE" name="COMP_SIZE" class="input" style="width:200;"> ex) 32㎡ 1층 사무실, 3층 건물</td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">재무상태 (자본금)</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_CAPITAL" name="COMP_CAPITAL" class="input" style="width:200;"> <b>원</b></td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">상근직원수</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="text" id="COMP_EMPLOYEE_CNT" name="COMP_EMPLOYEE_CNT" class="input" style="width:200;" onkeydown="return onlyNumber(event)" onkeyup="removeChar(event)" style="ime-mode:disabled;"> <b>명</b></td>									
	          </tr>	        
	          <tr>
	            <td width="130" class="tbl_field">업무유형</td>
	            <td width="607" colspan="3" class="tbl_list_left"><input type="checkbox" id="UH1" name="UH1" class="input"> 공사 &nbsp; <input type="checkbox" id="UH2" name="UH2" class="input"> 용역 &nbsp; <input type="checkbox" id="UH3" name="UH3" class="input"> 물품</td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">세부유형</td>
	            <td width="607" colspan="3" class="tbl_list_left">
	            <input type="checkbox" id="UH21" name="UH21" class="input"> 디자인 &nbsp; <input type="checkbox" id="UH22" name="UH2" class="input"> 인쇄물 &nbsp; <input type="checkbox" id="UH23" name="UH23" class="input"> 동영상
	            <input type="checkbox" id="UH24" name="UH24" class="input"> 행사기획 &nbsp; <input type="checkbox" id="UH25" name="UH25" class="input"> 종합홍보 &nbsp; <input type="checkbox" id="UH26" name="UH26" class="input"> 기타
	            <br /><br /><input type="text" id="COMP_FIELD2_DETAIL" name="COMP_FIELD2_DETAIL" class="input" style="width:200;"></td>									
	          </tr>	          
	          <tr>
	            <td width="130" class="tbl_field">주요과업사항</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="10" cols="76" id="COMP_MAIN_WORK" name="COMP_MAIN_WORK" class="input" style="width:557;"></textarea></td>									
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">회사소개서</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv">
						<p>
							<input type="file" id="file" name="file_0">
							<a href="#this" class="btn" id="delete" name="delete">삭제</a> (<b>10M이하</b>)
						</p>
					</div>
					<a href="#this" class="btn" id="addFile">파일 추가</a> (<b>10M이하</b>)
					<p><b>* 수행능력, 재무상태 등을 알 수 있는 회사소개서를 첨부하시기 바랍니다.</b></p>           
	            </td>
	          </tr>
	          <tr>
	            <td width="130" class="tbl_field">현장점검 출장복명서</td>
	            <td colspan="3" width="607" class="tbl_list_left">
	                <div id="fileDiv2">
						<p>
							<input type="file" id="file2" name="file2_0"> (<b>10M이하</b>)							
						</p>
					</div>										        
	            </td>
	          </tr>	         	          	         
	          <tr>
	            <td width="130" class="tbl_field">비고</td>
	            <td width="607" colspan="3" class="tbl_list_left"><textarea rows="2" cols="76" id="BIGO" name="BIGO" class="input" style="width:557;"></textarea></td>									
	          </tr>
	      </table>
	      
	     
	      
        <!-- -------------- 버튼 시작 --------------  -->
        <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0">
          <tr>
            <td><b>* 업체등록후 <u>기술능력정보 탭(과업실적 및 기술자 보유현황)</u>의 내용도 등록하시길 바랍니다.</b></td>
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
