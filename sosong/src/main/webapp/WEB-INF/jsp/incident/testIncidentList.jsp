<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript">
alert('bb');
		var user_right = "${sessionScope.userinfo.userright}";
		alert('aa');
		
		$(document).ready(function(){
			alert('zz');
			
			fn_selectAllIncidentList(1);
			
			fn_initial_setting();
			
			$("#write").on("click", function(e){ //글쓰기 버튼
				e.preventDefault();
				
				if(user_right == 'A') {
					fn_incidentBasicWrite();
				}
				else{
					alert('권한이 없습니다.');	
				}
			});	
			
			
			$("#search").on("click", function(e){ //검색 버튼
				e.preventDefault();
				$("#PAGE_INDEX").val("1");
				fn_selectAllIncidentList();
			});
			
			$("#initial").on("click", function(e){ //초기화 버튼
				e.preventDefault();				
				$("#form1").each(function() {				 
			       this.reset();  
			    });
				
				fn_initial_setting();
			});
			
			
			$("#print").on("click", function(e){ //프린트 버튼
				e.preventDefault();
				fn_print();
							
			});	
			
			
			$("#excel").on("click", function(e){ //엑셀다운 버튼
				e.preventDefault();
				fn_exceldown();
			});
			
			$("input[name=DEPT_NAME]").keydown(function (key) {

				if(key.keyCode == 13){//키가 13이면 실행 (엔터는 13)
					$("#PAGE_INDEX").val("1");
					fn_selectAllIncidentList();
				}

			});
			
			
			$("input:radio[name=INCDNT_GB]").click(function(){			
			    //alert($(this).val());
				$("#PAGE_INDEX").val("1");
				fn_selectAllIncidentList();
			});
 
		});
		
		function fn_initial_setting(){
			
			$('input:radio[id=INCDNT_GB4]').prop("checked", true);
		}
		
		
		function fn_print(){     
			
			
			$('#divMemoMainBody3').addClass('tbl_print');		
			$('#divMemoMainBody1').css("display","none");
			$('#divMemoMainBody2').css("display","none");
			$('#divMemoMainBody3').removeClass("display","none");
			$('#divMemoMainBody3').removeClass('list_tit_line_s');
			$('#divMemoMainBody3').removeClass('list_tit_bar');
			$('#divMemoMainBody3').removeClass('list_tit_tr');
			$('#PAGE_NAVI').css("display","none");
			
			//factory.printing.header = "머릿글" //머릿말 설정 
			//factory.printing.footer = "바닥글"  //꼬릿말 설정 
			factory.printing.portrait = false;                    //출력방향 설정: true-세로, false-가로
			factory.printing.leftMargin = 0.0;                //왼쪽 여백 설정 
			factory.printing.topMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.rightMargin = 0.0;              //왼쪽 여백 설정 
			factory.printing.bottomMargin = 0.0;            //왼쪽 여백 설정		
			factory.printing.Print(false, window);		
		}
		
		
		function fn_incidentBasicWrite(){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicWrite.do' />");
			comSubmit.submit();
		}
		
		function fn_incidentBasicDetail(obj){
			var comSubmit = new ComSubmit();
			comSubmit.setUrl("<c:url value='/incident/incidentBasicDetail.do' />");
			comSubmit.addParam("ID", obj.parent().find("#ID").val());
			comSubmit.submit();
		}
				
		
		function fn_selectAllIncidentList(pageNo){
			alert('00');
			var comAjax = new ComAjax();
			alert('11');			
			comAjax.setUrl("<c:url value='/incident/selectAllIncidentList.do' />");
			comAjax.setCallback("fn_selectAllIncidentListCallback");
			comAjax.addParam("PAGE_INDEX",$("#PAGE_INDEX").val());
			comAjax.addParam("PAGE_ROW", $("#RECORD_COUNT").val());
			comAjax.addParam("CMPLN_NAME",$("#CMPLN_NAME").val());
			comAjax.addParam("ICDNT_NO", $("#ICDNT_NO").val());
			comAjax.addParam("ID",$("#ID").val());
			
			if(user_right == 'A') {//관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comAjax.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			}
			else{
				$("#DEPT_NAME").val("${sessionScope.userinfo.deptName}");
				$("#DEPT_NAME").attr("readonly", true);
				comAjax.addParam("DEPT_NAME","${sessionScope.userinfo.deptName}");
				
			}
			
			if( $("input:radio[name='INCDNT_GB']").is(":checked") == true){				
				comAjax.addParam("INCDNT_GB", $("input:radio[name='INCDNT_GB']:checked").val());
				
			}
			
			
		
			comAjax.ajax();
		}
		
		
		function fn_selectAllIncidentListCallback(data){			
			var total = data.TOTAL;			
			var body = $("#mytable");
			var recordcnt = $("#RECORD_COUNT").val();
			
			$("#tcnt").text(comma(total));
			
			body.empty();
			if(total == 0){
				var str = "<tr class=\"list1\" onmouseover=\"this.className='list_over'\" onmouseout=\"this.className='list1'\">" + 
								"<td colspan=\"8\" class=\"list_center\" nowrap>조회된 결과가 없습니다.</td>" +
						  "</tr>" +
						  "<tr>" + 
							    "<td colspan=\"8\" class=\"list_line\"></td>" + 
						  "</tr>";
						  
				body.append(str);
			}
			else{
				var params = {
					divId : "PAGE_NAVI",
					pageIndex : "PAGE_INDEX",
					totalCount : total,
					recordCount : recordcnt,
					eventName : "fn_selectAllIncidentList"
				};
				gfn_renderPaging(params);
				
				var str = "";
				var ow_type1 = "";
				var ow_type2 = "";
				var ow_type3 = "";
				var tr_class = "";
				$.each(data.list, function(key, value){
					
				tr_class = "tr1";
												
							str += "<tr class="\"" + tr_class + ""\>"; + 
									   "<td>" + 
									   value.RNUM + " &nbsp;<a href='#this' name='title' >" +  value.ID + "</a>" + 
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" +
									   "</td>" +					                   
					                   "<td>" + 
									   "<a href='#this' name='title' >" + CutString(NvlStr(value.INCDNT_NAME), 10)  + "</a>" + 									  
									   "<input type='hidden' name='title' id='ID' value=" + value.ID + ">" + 
									   "</td>" +
					                   "<td>" + CutString(NvlStr(value.CMPLN_NAME), 8) + "</td>" + 									 
									   "<td>" + CutString(NvlStr(value.DFNDNT_NAME), 8) +  "</td>" +
									   "<td>" + CutString(NvlStr(value.NOTE), 8) +  "</td>" +
									   "<td>" + CutString(NvlStr(value.DEPT_NAME), 8) +  "</td>" +								
									   "<td>" + comma(NvlStr(value.LAWSUIT_VALUE)) + "</td>" +
									   "<td>" + NvlStr(value.LAST_RSLT_CONT) + "</td>" +									   
									   "</tr>";
									   							
							
				});
				body.append(str);
				
				$("a[name='title']").on("click", function(e){ //제목 
					e.preventDefault();
					fn_incidentBasicDetail($(this));
				});
			}
		}
		
		function fn_exceldown(){			
			var comSubmit = new ComSubmit();			
			
			comSubmit.setUrl("<c:url value='/incident/excelAllIncidentList.do' />");		
			
			if(user_right == 'A') {//관리자면 전체리스트 보여주고 일반사용자는 해당부서만			
				comSubmit.addParam("DEPT_NAME",$("#DEPT_NAME").val());
			}
			else{
				$("#DEPT_NAME").val("${sessionScope.userinfo.deptName}");
				$("#DEPT_NAME").attr("readonly", true);
				comSubmit.addParam("DEPT_NAME","${sessionScope.userinfo.deptName}");
				
			}
			
			if( $("input:radio[name='INCDNT_GB']").is(":checked") == true){				
				comSubmit.addParam("INCDNT_GB", $("input:radio[name='INCDNT_GB']:checked").val());
				
			}
			
			comSubmit.addParam("CMPLN_NAME",$("#CMPLN_NAME").val());
			comSubmit.addParam("ICDNT_NO", $("#ICDNT_NO").val());
			comSubmit.addParam("ID",$("#ID").val());
			
			comSubmit.submit();
		}
	</script>
</head>
<body bgcolor="#FFFFFF">
<object id="factory" style="display:none"  classid="clsid:1663ed61-23eb-11d2-b92f-008048fdd814"  codebase="http://98.23.7.110:8099/sosong/common/smsx.cab#Version=7,7,0,20"></object>
<form id="form1" name="form1">
<input type="hidden" id="USER_ID" name="USER_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="DEPT_ID" name="DEPT_ID" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="BOARD_ID" name="BOARD_ID" value="1">

<table class="tblm">
	<tr>
	<td>
    <table class="tbl1">
      <thead>
        <tr>
          <th>사건명</th><th>원고</th><th>피고</th><th>특이사항</th><th>소관부서</th><th>소가</th><th>최종결과</th>
        </tr>
      </thead>
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