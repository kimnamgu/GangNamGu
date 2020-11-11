<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(
		function() {
			
			fn_setting_val(); //입력된 값 셋팅		
		
			//입력일
			$("#WRITE_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//생년월일
			$("#BIRTH").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//모니터링 시작일
			$("#COME_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//모니터링 종료일
			$("#FREE_DATE").datepicker({
				dateFormat: 'yy-mm-dd',
				monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
			    dayNamesMin: ['일','월','화','수','목','금','토'],
				changeMonth: true, //월변경가능
			    changeYear: true, //년변경가능
				showMonthAfterYear: true //년 뒤에 월 표시				
			});
			
			//기본주소찾기	
			$("#juso_org").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupOrg.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});
			
			//자가격리주소찾기	
			$("#juso_iso").on("click", function(e){ 				
				e.preventDefault();
				var pop = window.open("/corona/common/jusoPopupIso.do","pop","width=570,height=420, scrollbars=yes, resizable=yes"); 
			});

			//정보수정
			$("#update").on("click", function(e){							
				e.preventDefault();				
				fn_update();				
			});
		});
	
	//정보 입력
	function fn_update(){
		var comSubmit = new ComSubmit("form1");
		
		var tmp1 = "${map.ID}";
		comSubmit.addParam("ID", "${map.ID}");
		
		comSubmit.addParam("WRITE_DATE", $("#WRITE_DATE").val().replace(/-/gi, ""));
		comSubmit.addParam("BIRTH", $("#BIRTH").val().replace(/-/gi, ""));
		comSubmit.addParam("COME_DATE", $("#COME_DATE").val().replace(/-/gi, ""));
		comSubmit.addParam("FREE_DATE", $("#FREE_DATE").val().replace(/-/gi, ""));

		if($('#CELL_NUM').val() == "")
		{
			alert('핸드폰번호를 입력하세요!!');
			$('#CELL_NUM').focus();
			return false;
		}
		
		if($('#PATIENT_NUM').val() == "")
		{
			alert('환자번호를 입력하세요!!');
			$('#PATIENT_NUM').focus();
			return false;
		}
		
		if($('#PATIENT_NAME').val() == "")
		{
			alert('환자명을 입력하세요!!');
			$('#PATIENT_NAME').focus();
			return false;
		}
		
		if($('#SELF_ISO_AREA_DONG').val() == "")
		{
			alert('자가격리지동을 입력하세요!!');
			$('#SELF_ISO_AREA_DONG').focus();
			return false;
		}
		if($('#SELF_ISO_AREA_ADDRESS').val() == "")
		{
			alert('자가격리지주소를 입력하세요!!');
			$('#SELF_ISO_AREA_ADDRESS').focus();
			return false;
		}
		
		if($('#SELF_ISO_AREA_ADDRESS_DTL').val() == "")
		{
			alert('자가격리 주소상세를 입력하세요!!');
			$('#SELF_ISO_AREA_ADDRESS_DTL').focus();
			return false;
		}
		
		if($('#COME_DATE').val() == "")
		{
			alert('입국일을 입력하세요!!');
			$('#COME_DATE').focus();
			return false;
		}
		
		if($('#FREE_DATE').val() == "")
		{
			alert('격리해제일을 입력하세요!!');
			$('#FREE_DATE').focus();
			return false;
		}
		
		if($('#VISIT_NATION').val() == "")
		{
			alert('체류국가를 입력하세요!!');
			$('#VISIT_NATION').focus();
			return false;
		}
		
		if(confirm('데이터를 수정하시겠습니까?'))
		{					
			comSubmit.setUrl("<c:url value='/write/updateOversea.do' />");									
			comSubmit.submit();
			alert('수정 되었습니다.!!');
		}
	}
			
			
	function trim(str) {
	    return str.replace(/(^\s*)|(\s*$)/gi, '');
	}	

	

	//기존주소 jusoPopupOrgCallBack
	function jusoPopupOrgCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#ORG_ADDRESS').val(roadAddrPart1);
		$('#ORG_ADDRESS_DTL').val(addrDetail);
	}
	
	//자가격리주소 jusoPopupIsoCallBack
	function jusoPopupIsoCallBack(roadFullAddr,roadAddrPart1,addrDetail,roadAddrPart2,engAddr, jibunAddr, zipNo, admCd, rnMgtSn, bdMgtSn,detBdNmList,bdNm,bdKdcd,siNm,sggNm,emdNm,liNm,rn,udrtYn,buldMnnm,buldSlno,mtYn,lnbrMnnm,lnbrSlno,emdNo){
		// 팝업페이지에서 주소입력한 정보를 받아서, 현 페이지에 정보를 등록합니다.
		$('#SELF_ISO_AREA_ADDRESS').val(roadAddrPart1);
		$('#SELF_ISO_AREA_ADDRESS_DTL').val(addrDetail);
		
		$('#SELF_ISO_AREA_DONG').val(emdNm);
	}
	
	function fn_setting_val(){
		var tmp1 = "${map.SEX}";

		//성별체크
		if(tmp1 == 'M')
			$('#SEX_M').attr("checked", true);
		
		if(tmp1 == 'W')
			$('#SEX_W').attr("checked", true);
	}
	
</script>


<style>
  table {
    border: 1px solid #444444;
     border-collapse: collapse;
  }
  th, td {
    border: 1px solid #444444;
    padding : 10px;
  }
</style>
</head>

<body>

<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/corona/images/popup_title.gif" align="absmiddle">
	</a>해외입국자 수정
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="/corona/manage/overseaManage.do" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 해외입국자 관리화면</a>
	</div>
</div>


<form id="form1" name="form1" enctype="multipart/form-data">
	<input type="hidden" id="INS_ID" name="INS_ID" value="${sessionScope.userinfo.userId}">
	
		<!--리스트 시작 -->
		<table style="margin:20px;">
			<tr>
				<td><font color='RED'>* 입력일자</font></td>
				<td><input type="text" class="form-control" id="WRITE_DATE" value="${map.WRITE_DATE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 핸드폰번호</font></td>
				<td><input type="text" class="form-control" id="CELL_NUM" name="CELL_NUM" value="${map.CELL_NUM}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 이름</font></td>
				<td><input type="text" class="form-control" id="NAME" name="NAME" value="${map.NAME}"></td>
			</tr>
			<tr>
				<td>성별</td>
				<td>
					<input type="radio" id="SEX_M" name="SEX" value="M"> 남자 &nbsp; 
					<input type="radio" id="SEX_W" name="SEX" value="W"> 여자 &nbsp; 
				</td>
			</tr>
			<tr>
				<td>생년월일</td>
				<td><input type="text" class="form-control" id="BIRTH" value="${map.BIRTH}"></td>
			</tr>
			<tr>
				<td>직업</td>
				<td><input type="text" class="form-control" id="JOB" name="JOB" value="${map.JOB}"></td>
			</tr>
			<tr>
				<td>기존주소</td>
	            <td width="607">
					<input type="text" id="ORG_ADDRESS" name="ORG_ADDRESS" class="form-control" style="width:400;" value="${map.ORG_ADDRESS}" >       
					<input type="text" id="ORG_ADDRESS_DTL" name="ORG_ADDRESS_DTL" class="form-control" style="width:400;" value="${map.ORG_ADDRESS_DTL}" >
		            <a href="#this" class="btn" id="juso_org">주소찾기</a>
	            </td>	
				
			</tr>
			<tr>
				<td><font color='RED'>* 자가격리지동</font></td>
				<td>
					<input type="text" class="form-control" id="SELF_ISO_AREA_DONG" name="SELF_ISO_AREA_DONG" value="${map.SELF_ISO_AREA_DONG}">
					<font color='#DF0174'>* 주소 입력시 자동 입력됩니다.</font>
				</td>
			</tr>
			<tr>
				<td><font color='RED'>* 자가격리주소</font></td>
	            <td width="607">
					<input type="text" id="SELF_ISO_AREA_ADDRESS" name="SELF_ISO_AREA_ADDRESS" class="form-control" style="width:400;" value="${map.SELF_ISO_AREA_ADDRESS}" >       
					<input type="text" id="SELF_ISO_AREA_ADDRESS_DTL" name="SELF_ISO_AREA_ADDRESS_DTL" class="form-control" style="width:400;" value="${map.SELF_ISO_AREA_ADDRESS_DTL}" >
		            <a href="#this" class="btn" id="juso_iso">주소찾기</a>
	            </td>	
				
			</tr>
			
			<tr>
				<td><font color='RED'>* 입국일</font></td>
				<td><input type="text" class="form-control" id="COME_DATE"  value="${map.COME_DATE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 격리해제일</font></td>
				<td><input type="text" class="form-control" id="FREE_DATE" value="${map.FREE_DATE}"></td>
			</tr>
			<tr>
				<td><font color='RED'>* 체류국가</font></td>
				<td><input type="text" class="form-control" id="VISIT_NATION" name="VISIT_NATION" value="${map.VISIT_NATION}"></td>
			</tr>
			
			
			<tr>
				<td>소속</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_DEPART" name="DAMDANG_DEPART" value="${map.DAMDANG_DEPART}">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>직위</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_CLASS" name="DAMDANG_CLASS" value="${map.DAMDANG_CLASS}">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>	
				</td>
			</tr>
			<tr>
				<td>직급</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_POSITION" name="DAMDANG_POSITION" value="${map.DAMDANG_POSITION}">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>전담공무원명</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_NAME" name="DAMDANG_NAME" value="${map.DAMDANG_NAME}">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			<tr>
				<td>내선번호</td>
				<td>
					<input type="text" class="form-control" id="DAMDANG_CALL" name="DAMDANG_CALL" value="${map.DAMDANG_CALL}">
					<font color='#DF0174'>* 추후 담당 공무원이 배정되면 입력</font>
				</td>
			</tr>
			
			
			<tr align="right" >
				<td colspan="2">
					<a href="#this" class="btn btn-warning" id="update">수정</a>
				</td>
			</tr>
		</table>
	</form>

</body>
</html>