<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>


<script type="text/javascript">
		var chk_num = 0;
		
		$(document).ready(function(){
			fn_incidentStatisticsList2(1);
			fn_initial_setting();
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_incidentStatisticsList2();
			});
					
			
			$("#inquery").on("click", function(e){ //기본정보
				e.preventDefault();
				fn_incidentStatisticsList2();
			});
			
			
			$("input:radio[name=INCDNT_GB]").click(function(){			   
				$("#PAGE_INDEX").val("1");
				fn_incidentStatisticsList2();
			});
			
			$("#print").on("click", function(e){ //프린트 버튼
				e.preventDefault();
				fn_print();							
			});	
			
			$("#excel").on("click", function(e){ //엑셀다운 버튼
				e.preventDefault();
				fn_exceldown();
			});
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();				
				$("#form1").each(function() {				 
			       this.reset();  
			    });						
				fn_initial_setting();
			});
			
		});
		
		function fn_initial_setting(){			
			$('input:radio[id=INCDNT_GB4]').prop("checked", true);
		}
		
		function fn_print(){     
					
			$('#divMemoMainBody1').css("display","none");
			$('#divMemoMainBody2').css("display","none");
			 
			factory.printing.portrait = false;                    //출력방향 설정: true-세로, false-가로
			factory.printing.leftMargin = 0.0;                //왼쪽 여백 설정 
			factory.printing.topMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.rightMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.bottomMargin = 0.0;            //왼쪽 여백 설정			
			factory.printing.Print(false, window);			
		}
		
	    function fn_incidentStatisticsList2(pageNo){
			
			var comAjax = new ComAjax();
			var user_right =  $("#USER_RIGHT").val();
			var tmp = "";
			
			comAjax.setUrl("<c:url value='/incident/selectIncidentStatisticsList2.do' />");
			comAjax.setCallback("fn_selectIncidentStatisticsList2Callback");
			
			if( $("input:radio[name='INCDNT_GB']").is(":checked") == true){				
				comAjax.addParam("INCDNT_GB", $("input:radio[name='INCDNT_GB']:checked").val());
			}	
			comAjax.addParam("DEPT_NAME",$("#DEPT_NAME").val());	
			comAjax.addParam("STDATE", $("#STDATE").val());
			
			if($("#EDDATE").val() == "" || $("#EDDATE").val() == null)
				comAjax.addParam("EDDATE", $.datepicker.formatDate($.datepicker.ATOM, new Date())); //오늘일자
			else
				comAjax.addParam("EDDATE", $("#EDDATE").val());
						
			var sel_layer = $("#SEL_LAWYER > option");
		    var len = sel_layer.length;
		    var len2 = $("#SEL_LAWYER > option:selected").length;
		     
	        for (var i = 0, j=0; i < len; i++){
	            if(sel_layer[i].selected == true){
	                tmp += "'" + sel_layer[i].value+ "'";
	              
	                if(j != len2-1)
	                	tmp = tmp + ", ";
	              j++;
	            }
	        }
	        
			if(tmp.length > 0)
			{				
				comAjax.addParam("CHK_LAWYER", "11");				
				comAjax.addParam("LAWYER_LIST", tmp);
			}
			else{
				comAjax.addParam("CHK_LAWYER", "");
			}
			
			comAjax.ajax();
		}
		
		function fn_selectIncidentStatisticsList2Callback(data){			
			var total = data.TOTAL;
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			var str = "";
			var str2 = "";
						
			body.empty();
			
			if(total == 0){
					str = "<tr>" + 
								"<td colspan=\"4\">조회된 결과가 없습니다.</td>" +
						  "</tr>";
						
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectIncidentStatisticsList2Callback"
				};
				gfn_renderPaging(params);
				
				str = "";
				str2 = "";
			
				$.each(data.list, function(key, value){
					
					str += "<tr>" +
					   "<td>" + NvlStr(value.CHRG_LAWYER) +  "</td>" +
					   "<td>" + comma(NvlStr(value.SUMN)) +  "</td>" +
					   "<td>" + comma(NvlStr(value.NUM1)) +  "</td>" +
					   "<td>" + comma(NvlStr(value.NUM2)) + "</td>" +								   
				   "</tr>";						
							
					if(NvlStr(value.CHRG_LAWYER) != "계" && NvlStr(value.CHRG_LAWYER) != "기타변호사"){
						str2 += "<option value=\"" + NvlStr(value.CHRG_LAWYER) + "\">" + NvlStr(value.CHRG_LAWYER) + "</option>";
					}
				   
				});				
				body.append(str);
				if(chk_num == 0){
					$("#SEL_LAWYER").append(str2);
					chk_num++;
				}
			}
		}
		
		function fn_exceldown(){			
			var comSubmit = new ComSubmit();			
			var tmp = "";
			
			comSubmit.setUrl("<c:url value='/incident/excelIncidentStatistics2.do' />");		
			comSubmit.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			comSubmit.addParam("STDATE", $("#STDATE").val());

			if($("#EDDATE").val() == "" || $("#EDDATE").val() == null)
				comSubmit.addParam("EDDATE", $.datepicker.formatDate($.datepicker.ATOM, new Date())); //오늘일자
			else
				comSubmit.addParam("EDDATE", $("#EDDATE").val());
			
			if( $("input:radio[name='INCDNT_GB']").is(":checked") == true){				
				comSubmit.addParam("INCDNT_GB", $("input:radio[name='INCDNT_GB']:checked").val());			
			}		
			
			var sel_layer = $("#SEL_LAWYER > option");
		    var len = sel_layer.length;
		    var len2 = $("#SEL_LAWYER > option:selected").length;
		     
	        for (var i = 0, j=0; i < len; i++){
	            if(sel_layer[i].selected == true){
	                tmp += "'" + sel_layer[i].value+ "'";
	              
	                if(j != len2-1)
	                	tmp = tmp + ", ";
	              j++;
	            }
	        }
	        
			if(tmp.length > 0)
			{				
				//comSubmit.addParam("CHK_LAWYER", "11");				
				comSubmit.addParam("LAWYER_LIST", tmp);
			}
			else{
				comSubmit.addParam("CHK_LAWYER", "");
			}
			
			comSubmit.submit();
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<object id="factory" style="display:none" classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814" codebase="http://98.23.7.110:8099/sosong/common/smsx.cab#Version=7,7,0,20"></object>
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">

 

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/sosong/images/popup_title.gif" align="absmiddle">변호사 수임료 지급현황</td>
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
    
   
      <table width="720" border="0" cellspacing="1" cellpadding="0" class="tbl" id="divMemoMainBody1">
      <tr>
           <td width="100" class="tbl_field">소송구분</td>
           <td colspan="3" width="660" class="tbl_list_left">           	                 
               <input type="radio" id="INCDNT_GB4" name="INCDNT_GB" value="" class="input" onfocus="this.blur()"> 전체&nbsp;<input type="radio" id="INCDNT_GB1" name="INCDNT_GB" value="1" class="input" onfocus="this.blur()"> 행정소송 &nbsp;<input type="radio" id="INCDNT_GB2" name="INCDNT_GB" value="2" class="input" onfocus="this.blur()"> 민사소송 &nbsp;<input type="radio" id="INCDNT_GB3" name="INCDNT_GB" value="3" class="input" onfocus="this.blur()"> 국가소송                 	                	               
           </td>
      </tr>		 
      <tr>
        <td width="100" class="tbl_field">기간</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="STDATE" name="STDATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, STDATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a> ~
            <input type="text" id="EDDATE" name="EDDATE" class="input" style="width:100;"> <a href="#a" onclick="popUpCalendar(this, EDDATE, 'yyyy-mm-dd');"><img class="Lbtn" src="/sosong/images/ic_cal.gif" alt="달력" /></a>	                          
        </td>
      </tr>
	  <tr>
        <td width="100" class="tbl_field">담당부서</td>
        <td colspan="3" width="660" class="tbl_list_left">	               
            <input type="text" id="DEPT_NAME" name="DEPT_NAME" class="input" style="width:300;">	                          
        </td>
      </tr>
      <tr>
        <td width="100" class="tbl_field">조회 변호사</td>
        <td colspan="3" width="660" class="tbl_list_left">
	      <select id="SEL_LAWYER" name="SEL_LAWYER" class="input" size="7" multiple>    	                		                    		              
		  </select>		
	   </td>
      </tr>
      </table>
    
	   <!-- ----------------------------------- 버튼 시작 ------------------------------------------>
	   <table width="100%" height="40" border="0" cellspacing="0" cellpadding="0" id="divMemoMainBody2">
	     <tr>
	       <td></td>
	       <td align="right">
	       <table border="0" cellspacing="0" cellpadding="0">
	         <tr>
	          <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
	             </tr>
	           </table>
	           </td>
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
	             <tr>
	               <td><img src="/sosong/images/btn_type0_head.gif"></td>
	               <td background="/sosong/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="inquery"><font color="white">조회</font></a></td>
	               <td><img src="/sosong/images/btn_type0_end.gif"></td>
	             </tr>
	           </table>                
	           </td>		
	         </tr>
	       </table>
	       </td>
	     </tr>
	     <tr>
	       <td align="left"></td>
	       <td align="right">
	       <table border="0" cellspacing="0" cellpadding="0">
	         <tr>
	         <!-- 
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="initial">검색초기화</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
	             </tr>
	           </table>
	           </td>
	           -->				
	           <td>	           
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="changebtn2" style="display:block;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="print">프린트</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
	             </tr>
	           </table>	          
	           </td>	           					
	           <td>
	           <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
	             <tr>
	               <td><img src="/sosong/images/btn_type1_head.gif"></td>
	               <td background="/sosong/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="excel">엑셀다운로드</a></td>
	               <td><img src="/sosong/images/btn_type1_end.gif"></td>
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
       <!-- ----------------------------------- 버튼 끝 ------------------------------------------>
    
		<table class="tbl2">
			<tr>
	            <th width="250">구분</th>
	            <th width="200">계</th>
				<th width="200">착수금</th>
				<th width="200">승소사례금</th>	            						
          	</tr>
			<tbody id='mytable'>				
			</tbody>
		</table>
	</td>
	</tr>
</table>
</form>
	<div id="PAGE_NAVI" align='center'></div>
	<input type="hidden" id="PAGE_INDEX" name="PAGE_INDEX"/>
	<input type="hidden" id="RECORD_COUNT" name="RECORD_COUNT" value="10"/>	
	<%@ include file="/WEB-INF/include/include-body.jspf" %>
		
</body>
</html>