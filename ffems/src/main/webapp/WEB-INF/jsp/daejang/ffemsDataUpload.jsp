<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>신속재정 관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<style type="text/css">
 #pop{
  width:300px; height:250px; background:#3d3d3d; color:#fff; 
  position:absolute; top:10px; left:100px; text-align:center; 
  border:1px solid #000;
   }
</style>
<script type="text/javascript">
		var gfv_count = 1;
		
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				//alert('aa');
				e.preventDefault();				
				fn_ffemsDataList();
			});
			
			$("#write").on("click", function(e){ //작성하기 버튼
				
				
				if( $('#USER_RIGHT').val() == 'A' || $('#SAUP_DEPT_NM').val() == $('#USER_NAME').val() )
				{				
					e.preventDefault();
					fn_insertFfemsData();
				}
				else{
					alert('권한이 없습니다.');
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
		
		
		
		function fn_ffemsDataList(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/daejang/ffemsDataList.do' />");
			comSubmit.submit();
		}
		
		function fn_insertFfemsData(){
			var comSubmit = new ComSubmit("form1");
						
			//alert($('#SAUP_DETAIL').val().length);			
			
			if(confirm('데이터를 등록하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/insertFfemsData.do' />");
				comSubmit.submit();
			}
		}
		
		/*
		function fn_insertMainSaupData(){
	    	
	    	var jsnobj = new Object();
	    	
	    	jsnobj.uploadFile = $('#uploadFile').val();
	    	jsnobj.GIJUN_DATE = $('#GIJUN_DATE').val();
	    	jsnobj.OUT_COMMENT1 = $('#OUT_COMMENT1').val();
	    	
			var jsonData = JSON.stringify(jsnobj);
			alert(jsonData);
			
			$.ajax({
			        type:"POST",
			        url:"/ffems/daejang/insertMainSaup.do",
			        data : jsonData,
			        dataType : "json",
			        contentType : "application/json; charset=UTF-8",			        
			        success : function(data, stat, xhr) {
						alert("success " + "data=[" + JSON.stringify(data) + "]" );
						//alert("success!!");
						
					},
			        error: function(xhr, status, error) {
			            alert("err: " + error);
			        }  
    		});

	    }
		
		*/
		
		
	</script>
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="INS_DEPT" name="INS_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">
<input type="hidden" id="BIGO" name="BIGO" value="">


<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/ffems/images/popup_title.gif" align="absmiddle">신속재정  데이터 등록</td>
      </tr>
      <tr>
        <td class="margin_title"></td>
      </tr>
    </table>
    <!--페이지 타이틀 끝 -->
    
     <table width="100%" border="0" cellspacing="0" cellpadding="0">
	 <tr>
		<td width="100%">
		<table border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td class="title"><img src="/ffems/images/title_ico.gif" align="absmiddle">신속재정 기본데이터 등록</td>
			</tr>
		</table>
		</td>				
	</tr>
	</table>
	
    </td>
  </tr>
  
  <tr>
    <td class="pupup_frame" style="padding-right:12px">

    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td height="15">
                
        <!--리스트 시작 -->
	      <table width="100%" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody" style="display:block;">	         
	          <tr>
	            <td width="120" class="tbl_field">업로드파일</td>
	            <td width="612" colspan="3" class="tbl_list_left">
	                <input type="file" id="uploadFile" name="uploadFile" style="width:500;">	                
	            </td>
	          </tr>
	                  
	          <tr>
	            <td width="120" class="tbl_field">기준일자</td>
	            <td width="612" colspan="3" class="tbl_list_left">	                
	                <input type="text" id="GIJUN_DATE" name="GIJUN_DATE" class="input" style="width:174;">	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">코멘트1</td>
	            <td width="612" colspan="3" class="tbl_list_left">	                
	                <input type="text" id="OUT_COMMENT1" name="OUT_COMMENT1" class="input" style="width:174;">	                
	            </td>
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
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/ffems/images/btn_type0_head.gif"></td>
                    <td background="/ffems/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="write"><font color="white">등록</font></a></td>
                    <td><img src="/ffems/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				               
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/ffems/images/btn_type1_head.gif"></td>
                    <td background="/ffems/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/ffems/images/btn_type1_end.gif"></td>
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


<!-- POPUP --> 
<div id="pop" style="position:absolute;left:395px;top:90;z-index:200;display:none;"> 
<table width=560 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close1"><B>[닫기]</B></a> 
    </td> 
</tr>
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="80"></td>
				<td class="list_tit_line_s" width="100%"></td>
				<td class="list_tit_line_s" width="60"></td>
				<td class="list_tit_line_s" width="100"></td>			
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">이름</td>
				<td nowrap class="list_tit_bar">부서</td>
				<td nowrap class="list_tit_bar">직급</td>
				<td nowrap class="list_tit_bar">직위</td>
				<td nowrap class="list_tit_bar">내선</td>							
			</tr>
			<tr>
				<td colspan="6" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable'>			
			</tbody>
			<tr>
				<td colspan="6" height="20"></td>
			</tr>
			<tr>
				<td colspan="6">
				<div id="PAGE_NAVI" align='center'></div>
				<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
				<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="20"/>
				<%@ include file="/WEB-INF/include/include-body.jspf" %>
				</td>
			</tr>
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close2"><B>[닫기]</B></a> 
    </td> 
</tr> 
      
</table> 
</div>  



<!-- POPUP2 --> 
<div id="pop2" style="position:absolute;left:395px;top:50;z-index:200;display:none;"> 
<table width=500 height=250 cellpadding=2 cellspacing=0>
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close3"><B>[닫기]</B></a> 
    </td> 
</tr>
<tr> 
    <td style="border:1px #666666 solid" height=230 align=center bgcolor=white> 
     <table width="100%" border="0" cellspacing="0" cellpadding="0" style="TABLE-layout: fixed">
			<tr>
				<td class="list_tit_line_s" width="40"></td>
				<td class="list_tit_line_s" width="80"></td>				
				<td class="list_tit_line_s" width="100%"></td>							
			</tr>
			<tr class="list_tit_tr">
				<td nowrap class="list_tit"></td>
				<td nowrap class="list_tit_bar">추진부서</td>
				<td nowrap class="list_tit_bar">사업명</td>											
			</tr>
			<tr>
				<td colspan="3" class="list_tit_line_e"></td>
			</tr>
			<tbody id='mytable2'>			
			</tbody>			
			<tr>
				<td colspan="3" height="20"></td>
			</tr>
			<tr>
				<td colspan="3">
				<div id="PAGE_NAVI2" align='center'></div>
				<input type="hidden" id="PAGE_INDEX2" name="PAGE_INDEX2"/>
				<input type="hidden" id="RECORD_COUNT2" name="RECORD_COUNT2" value="20"/>
				<%@ include file="/WEB-INF/include/include-body.jspf" %>
				</td>
			</tr>			
			</table>
    </td> 
</tr> 
<tr>        
    <td align=right bgcolor=white>      
        <a href="#" id="close4"><B>[닫기]</B></a> 
    </td> 
</tr> 
      
</table> 
</div>  
</body>
</html>
