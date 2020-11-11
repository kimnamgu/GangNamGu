<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<html>
<head>
<title>주요사업관리시스템</title>
<%@ include file="/WEB-INF/include/include-header.jspf" %>
<script type="text/javascript" src="/bims/se2/js/service/HuskyEZCreator.js" charset="utf-8"></script>
<script type="text/javascript">
		var gfv_count = 1;
	
		$(document).ready(function(){
			
			$("#list").on("click", function(e){ //목록으로 버튼
				e.preventDefault();
				fn_mainSaupSubList();
			});
			
			$("#update").on("click", function(e){ //수정버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_updateMainSaupSubData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
			$("#del").on("click", function(e){ //삭제버튼
				if( $('#USER_RIGHT').val() == 'A' || $('#WRITE_DEPT').val() == $('#LOGIN_DEPT').val())
				{				
					e.preventDefault();
					fn_deleteMainSaupSubData();
				}
				else{
					alert('권한이 없습니다.');
				}
			});
			
			
$("#THWK_ST_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			
			$("#THWK_ED_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			$("#NTWK_ST_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
		    $("#NTWK_ED_DATE").datepicker({
				
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
		});
		
	
		function fn_mainSaupSubList(){
			var comSubmit = new ComSubmit();
			
			comSubmit.setUrl("<c:url value='/daejang/mainSaupSubList.do?year=${param.year}&sgb=${param.sgb}' />");
			
			comSubmit.addParam("MAST_ID", "${param.MAST_ID}");
			comSubmit.addParam("ID", "${param.MAST_ID}");
			comSubmit.addParam("SAUP_NM", "${param.SAUP_NM}");
			comSubmit.addParam("SAUP_DEPT_CD", "${map.INS_DEPT}");
			comSubmit.addParam("SAUP_DEPT_NM", "${param.SAUP_DEPT_NM}");
			comSubmit.submit();
		}
		
		function fn_updateMainSaupSubData(){
			var comSubmit = new ComSubmit("form1");
			
			oEditors.getById["SAUP_PUSH_CONTENT"].exec("UPDATE_CONTENTS_FIELD", []);
			oEditors.getById["SAUP_NEXT_CONTENT"].exec("UPDATE_CONTENTS_FIELD", []);
			
			if($('#SAUP_PROCESS_PERCENT').val() == "")
			{
				alert('2020년 진척률 입력하세요.');
				$('#SAUP_PROCESS_PERCENT').focus();
				return false();
			}
			
			if($('#THWK_ST_DATE').val() == "")
			{
				alert('지난 2주의 첫번째 월요일 날짜를 입력하세요.');
				$('#THWK_ST_DATE').focus();
				return false();
			}
			
			if($('#THWK_ED_DATE').val() == "")
			{
				alert('지난 2주의 마지막 금요일 날짜를 입력하세요.');
				$('#THWK_ED_DATE').focus();
				return false();
			}
			
			if($('#SAUP_PUSH_CONTENT').val() == "")
			{
				alert('지난 추진내용을 입력하세요.');
				$('#SAUP_PUSH_CONTENT').focus();
				return false();
			}
			
			
			if($('#NTWK_ST_DATE').val() == "")
			{
				alert('다음 2주의 첫번째 월요일 날짜를 입력하세요.');
				$('#NTWK_ST_DATE').focus();
				return false();
			}
			
			if($('#NTWK_ED_DATE').val() == "")
			{
				alert('다음 2주의 마지막 금요일 날짜를 입력하세요.');
				$('#NTWK_ED_DATE').focus();
				return false();
			}
			
			if($('#SAUP_NEXT_CONTENT').val() == "")
			{
				alert('이전 추진계획을 입력하세요.');
				$('#SAUP_NEXT_CONTENT').focus();
				return false();
			}
						
			if($('#YEAR').val() == 2019)
			{
				alert('2019년도는 더이상 입력/수정 되지 않습니다.\n 2020 년도로 입력/수정 하시길 바랍니다.');
				return false();
			}
			
			
			if(confirm('데이터를 수정하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/updateMainSaupSub.do' />");
				comSubmit.submit();
			}
		}
		

		function fn_deleteMainSaupSubData(){
			var comSubmit = new ComSubmit("form1");
			
			if($('#YEAR').val() == 2019)
			{
				alert('2019년도는 더이상 입력/수정 되지 않습니다.\n 2020 년도로 입력/수정 하시길 바랍니다.');
				return false();
			}
			
			if(confirm('데이터를 삭제하시겠습니까?'))
			{
				comSubmit.setUrl("<c:url value='/daejang/deleteMainSaupSub.do' />");
				comSubmit.submit();
			}
		}
		
	</script>
</head>

<body>
<form id="form1" name="form1" enctype="multipart/form-data">
<input type="hidden" id="MOD_ID" name="MOD_ID" value="${sessionScope.userinfo.userId}">
<input type="hidden" id="MOD_DEPT" name="MOD_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="USER_RIGHT" name="USER_RIGHT" value="${sessionScope.userinfo.userright}">
<input type="hidden" id="WRITE_DEPT" name="WRITE_DEPT" value="${map.INS_DEPT}">
<input type="hidden" id="LOGIN_DEPT" name="LOGIN_DEPT" value="${sessionScope.userinfo.deptId}">
<input type="hidden" id="MAST_ID" name="MAST_ID" value="${map.MAST_ID}">
<input type="hidden" id="ID" name="ID" value="${map.ID}">
<input type="hidden" id="SAUP_NM" name="SAUP_NM" value="${param.SAUP_NM}">
<input type="hidden" id="SAUP_DEPT_NM" name="SAUP_DEPT_NM" value="${param.SAUP_DEPT_NM}">
<input type="hidden" id="LST_GB" name="LST_GB" value="${param.LST_GB}">
<input type="hidden" id="YEAR" name="YEAR" value="${param.year}">
<input type="hidden" id="SGB" name="SGB" value="${param.sgb}">

<table  border="0" cellspacing="0" cellpadding="0" class="bodyframe_full">
  <tr>
    <td class="bodyframe">
    <!--페이지 타이틀 시작 -->
    <table width="100%" border="0" cellspacing="0" cellpadding="0">
      <tr>
        <td class="pupup_title"><img src="/bims/images/popup_title.gif" align="absmiddle">주요사업 내용</td>
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
				<td class="title"><img src="/bims/images/title_ico.gif" align="absmiddle">진행상황 확인 및 수정</td>
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
	            <td width="120" class="tbl_field">사업명</td>
	            <td width="710" class="tbl_list_left">${param.SAUP_NM}</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">최종수정일</td>
	            <td width="710" class="tbl_list_left">${param.UPD_DATE}</td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">입력일</td>
	            <td width="710" class="tbl_list_left">${param.INS_DATE}</td>
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">2020년 진척률</td>
	            <td width="710" class="tbl_list_left">	                
	                <input type="text" id="SAUP_PROCESS_PERCENT" name="SAUP_PROCESS_PERCENT" value="${map.SAUP_PROCESS_PERCENT}" class="input" style="width:80;"> (숫자만 입력)	                
	            </td>
	          </tr>
	          <tr>
	            <td width="120" class="tbl_field">최종목표 대비 진척률</td>
	            <td width="710" class="tbl_list_left">	                
	                <input type="text" id="SAUP_PROCESS_TOT_PERCENT" name="SAUP_PROCESS_TOT_PERCENT" value="${map.SAUP_PROCESS_TOT_PERCENT}" class="input" style="width:80;"> (숫자만 입력)	                
	            </td>
	          </tr>	                 
	          <tr>
	            <td width="120" class="tbl_field">지난 추진내용<br><br><input type="text" id="THWK_ST_DATE" name="THWK_ST_DATE" value="<fmt:parseDate value="${map.THWK_ST_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:80;"> ~ <input type="text" id="THWK_ED_DATE" name="THWK_ED_DATE" value="<fmt:parseDate value="${map.THWK_ED_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>"class="input" style="width:80;"></td>
	            <td width="710" class="tbl_list_left">	                
	                <textarea id="SAUP_PUSH_CONTENT" name="SAUP_PUSH_CONTENT" class="input" rows="10" cols="100" style="width:700px; height:300px; display:none;">${fn:replace(fn:replace(fn:replace(map.SAUP_PUSH_CONTENT, "<!--[if !supportEmptyParas]-->", ""),"<!--[endif]-->", ""), "<!--[if !vml]-->", "")}</textarea>	                
	            </td>
	          </tr>	          
	          <tr>
	            <td width="120" class="tbl_field">이번 추진계획<br><br><input type="text" id="NTWK_ST_DATE" name="NTWK_ST_DATE" value="<fmt:parseDate value="${map.NTWK_ST_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:80;"> ~ <input type="text" id="NTWK_ED_DATE" name="NTWK_ED_DATE" value="<fmt:parseDate value="${map.NTWK_ED_DATE}" var="dateFmt1" pattern="yyyyMMdd"/><fmt:formatDate value="${dateFmt1}" pattern="yyyy-MM-dd"/>" class="input" style="width:80;"></td>
	            <td width="710" class="tbl_list_left">	                
	                <textarea id="SAUP_NEXT_CONTENT" name="SAUP_NEXT_CONTENT" class="input" rows="10" cols="100" style="width:700px; height:300px; display:none;">${fn:replace(fn:replace(fn:replace(map.SAUP_NEXT_CONTENT, "<!--[if !supportEmptyParas]-->", ""),"<!--[endif]-->", ""), "<!--[if !vml]-->", "")}</textarea>	                
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
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="update"><font color="white">수정</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemo21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type0_head.gif"></td>
                    <td background="/bims/images/btn_type0_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="del"><font color="white">삭제</font></a></td>
                    <td><img src="/bims/images/btn_type0_end.gif"></td>
                  </tr>
                </table>                
                </td>				               
                <td>
                <table border="0" cellspacing="0" cellpadding="0" class="btn" id="tblAddMemoTempSave21" style="filter:none;">
                  <tr>
                    <td><img src="/bims/images/btn_type1_head.gif"></td>
                    <td background="/bims/images/btn_type1_bg.gif" class="btn_type1" nowrap><a href="#this" class="btn" id="list">목록</a></td>
                    <td><img src="/bims/images/btn_type1_end.gif"></td>
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


<script type="text/javascript">
var oEditors = [];

var sLang = "ko_KR";	// 언어 (ko_KR/ en_US/ ja_JP/ zh_CN/ zh_TW), default = ko_KR

// 추가 글꼴 목록
//var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "SAUP_PUSH_CONTENT",
	sSkinURI: "/bims/se2/SmartEditor2Skin.html",	
	htParams : {
		bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		//bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
		//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
		fOnBeforeUnload : function(){
			//alert("완료!");
		},
		I18N_LOCALE : sLang
	}, //boolean
	fOnAppLoad : function(){
		//예제 코드
		//oEditors.getById["SAUP_PUSH_CONTENT"].exec("PASTE_HTML", ["지난 추진실적"]);
	},
	fCreator: "createSEditor2"
});


nhn.husky.EZCreator.createInIFrame({
	oAppRef: oEditors,
	elPlaceHolder: "SAUP_NEXT_CONTENT",
	sSkinURI: "/bims/se2/SmartEditor2Skin.html",	
	htParams : {
		bUseToolbar : true,				// 툴바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseVerticalResizer : true,		// 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
		bUseModeChanger : true,			// 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
		//bSkipXssFilter : true,		// client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
		//aAdditionalFontList : aAdditionalFontSet,		// 추가 글꼴 목록
		fOnBeforeUnload : function(){
			//alert("완료!");
		},
		I18N_LOCALE : sLang
	}, //boolean
	fOnAppLoad : function(){
		//예제 코드
		//oEditors.getById["SAUP_PUSH_CONTENT"].exec("PASTE_HTML", ["이번 추진계획"]);
	},
	fCreator: "createSEditor2"
});

</script>
</body>
</html>
