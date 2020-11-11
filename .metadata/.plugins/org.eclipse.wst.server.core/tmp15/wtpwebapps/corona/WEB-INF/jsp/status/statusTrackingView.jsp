<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<html>
<head>
<%@ include file="/WEB-INF/include/include-header.jspf"%>


<script type="text/javascript">
	var user_right = "${sessionScope.userinfo.userright}";

	$(document).ready(function() {
		//공통
		var WRITE_DATE="${WRITE_DATE}";
		var ETC_GUBUN="${ETC_GUBUN}"; 
		var ETC_GUBUN_CONDITION="${ETC_GUBUN_CONDITION}"; 
		
		//총괄
		var TOTAL_GUBUN="${TOTAL_GUBUN}";
		var TOTAL_CONDITION="${TOTAL_CONDITION}";
		var TOTAL_ICHUP="${TOTAL_ICHUP}";
		var TOTAL_DONG_GUBUN="${TOTAL_DONG_GUBUN}";
		
		if(TOTAL_GUBUN != ''){
			fn_selectTotalDtlList(TOTAL_GUBUN,TOTAL_CONDITION,TOTAL_ICHUP,TOTAL_DONG_GUBUN,WRITE_DATE);
		}
		
		//확진자
		var INFECT_GUBUN="${INFECT_GUBUN}";
		var INFECT_DAE="${INFECT_DAE}";
		var INFECT_SO="${INFECT_SO}";
		
		
		if(INFECT_GUBUN != ''){
			fn_selectConfirmDtlList(INFECT_GUBUN,INFECT_DAE,INFECT_SO,WRITE_DATE,ETC_GUBUN,ETC_GUBUN_CONDITION);
		}
		
		if(INFECT_DAE != '' || INFECT_SO != ''){
			fn_selectConfirmDtlList2(INFECT_GUBUN,INFECT_DAE,INFECT_SO,WRITE_DATE,ETC_GUBUN,ETC_GUBUN_CONDITION);
		}
		
		
		//확진자 - 집단감염
		var JIPDAN_GUBUN="${JIPDAN_GUBUN}";
		var JIPDAN_DIVIDE="${JIPDAN_DIVIDE}";
		var JIPDAN_INFECT_DAE="${JIPDAN_INFECT_DAE}";
		
		
		if(JIPDAN_GUBUN != ''){
			fn_selectConfirmJipdanDtlList(JIPDAN_DIVIDE,JIPDAN_INFECT_DAE);
		}

		
		
		//자가격리자
		var ISO_GUBUN="${ISO_GUBUN}";
		var ISO_TYPE="${ISO_TYPE}";
		var ISO_DONG="${ISO_DONG}";
		var SELDATE="${SELDATE}";
		
		if(ISO_TYPE == 'OVERSEA'){
		 	fn_selectIsoOverseaDtlList(ISO_GUBUN,ISO_TYPE,ISO_DONG,SELDATE);
	 	}
		if(ISO_TYPE == 'DOM'){
		 	fn_selectIsoDomesticDtlList(ISO_GUBUN,ISO_TYPE,ISO_DONG,SELDATE);
	 	}
		
		var ISO_ALL_GUBUN="${ISO_ALL_GUBUN}";
		
		//상담
		var CONSULT_DEPART="${CONSULT_DEPART}";
		var CONSULT_JUYA_GUBUN="${CONSULT_JUYA_GUBUN}";
		var CONSULT_CONSULT_GUBUN="${CONSULT_CONSULT_GUBUN}";
		
		
		if(CONSULT_DEPART != ''){
			fn_selectConsultDtlList(CONSULT_DEPART,CONSULT_JUYA_GUBUN,CONSULT_CONSULT_GUBUN,WRITE_DATE);
		}
		
		
		//선별진료소
		var CLINIC_DEPART="${CLINIC_DEPART}";
		var CLINIC_SUSPICION_GUBUN="${CLINIC_SUSPICION_GUBUN}";
		var CLINIC_SUSPICION_DAE="${CLINIC_SUSPICION_DAE}";
		var CLINIC_SUSPICION_SO="${CLINIC_SUSPICION_SO}";
		var ISO="${ISO}";
		
		if(CLINIC_DEPART != ''){
		 	fn_selectClinicDtlList(CLINIC_DEPART,CLINIC_SUSPICION_GUBUN,CLINIC_SUSPICION_DAE,CLINIC_SUSPICION_SO,WRITE_DATE,ISO);
	 	}
		
		//선별진료소
		var CLINIC_CONFIRM_DEPART="${CLINIC_CONFIRM_DEPART}";
		var CLINIC_CONFIRM_SUSPICION_GUBUN="${CLINIC_CONFIRM_SUSPICION_GUBUN}";
		var CLINIC_CONFIRM_SUSPICION_DAE="${CLINIC_CONFIRM_SUSPICION_DAE}";
		var CLINIC_CONFIRM_SUSPICION_SO="${CLINIC_CONFIRM_SUSPICION_SO}";
		var CONFIRM_ISO="${CONFIRM_ISO}";
		
		
		if(CLINIC_CONFIRM_DEPART != ''){
		 	fn_selectClinicConfirmDtlList(CLINIC_CONFIRM_DEPART,CLINIC_CONFIRM_SUSPICION_GUBUN,CLINIC_CONFIRM_SUSPICION_DAE,CLINIC_CONFIRM_SUSPICION_SO,WRITE_DATE,CONFIRM_ISO);
	 	}
		
		
		//선별진료소 케이스
		var CLINIC_CASE_GUBUN="${CLINIC_CASE_GUBUN}";
		var CLINIC_CASE_CONDITION="${CLINIC_CASE_CONDITION}";
		var CLINIC_CASE_SELDATE="${CLINIC_CASE_SELDATE}";
		
		if(CLINIC_CASE_GUBUN == 'clinicCase'){
		 	fn_selectClinicCaseDtlList(CLINIC_CASE_CONDITION,CLINIC_CASE_SELDATE);
	 	}
		
	});
	//총괄
	function fn_selectTotalDtlList(TOTAL_GUBUN,TOTAL_CONDITION,TOTAL_ICHUP,TOTAL_DONG_GUBUN,WRITE_DATE){
		var comAjax = new ComAjax();
		
		comAjax.setUrl("<c:url value='/status/selectTotalDtlList.do' />");
		comAjax.addParam("TOTAL_GUBUN",TOTAL_GUBUN);
		comAjax.addParam("TOTAL_CONDITION",TOTAL_CONDITION);
		comAjax.addParam("TOTAL_ICHUP",TOTAL_ICHUP);
		comAjax.addParam("TOTAL_DONG_GUBUN",TOTAL_DONG_GUBUN);
		comAjax.addParam("WRITE_DATE",WRITE_DATE);
		comAjax.setCallback("fn_selectTotalDtlListCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_selectTotalDtlListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectTotalDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			   str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
					"			<th>입력일자</th>                    "+
					"			<th>핸드폰번호</th>                   "+
					"			<th>환자번호</th>                    "+
					"			<th>대상자이름</th>                   "+
					"			<th>접촉자번호</th>                   "+
					"			<th>접촉자명</th>                    "+
					"			<th>확진차수</th>                    "+
					"			<th>성별</th>                      "+
					"			<th>생년월일</th>                    "+
					"			<th>직업</th>                      "+
					"			<th>기존주소구분</th>                    "+
					"			<th>기존주소동</th>                    "+
					"			<th>기존주소</th>                    "+
					"			<th>기존상세</th>                    "+
					"			<th>감염경로구분</th>                  "+
					"			<th>감염경로동</th>                   "+
					"			<th>감염경로대구분</th>                 "+
					"			<th>감염경로소구분</th>                 "+
					"			<th>추정감염경로</th>                  "+
					"			<th>추정감염경로상세</th>                "+
					"			<th>확진 판정일</th>                  "+
					"			<th>입원병원</th>                    "+
					"			<th>퇴원일자</th>                    "+
					"			<th>비고 </th>                     "+
					"			<th>이첩 </th>                     "+
					"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
				    	   "<tr class='" + tr_class + "'>" + 
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NUM) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='#this' name='PATIENT_NAME'>" + NvlStr(value.PATIENT_NAME) + "</a>"+
								   "	<input type='hidden' name='PATIENT_NAME' id='UID' value=" + value.ID + ">" +
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONFIRM_GRADE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_DAE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_SO) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_ASFECT_AREA) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_ASFECT_AREA_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONFIRM_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.HOSPITAL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DISCHARGE_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.REMARK) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ICHUP_YN) + "</td>" +
							   "</tr>"
							   ;
								   
			}); 
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	
	
	//확진자
	function fn_selectConfirmDtlList(INFECT_GUBUN,INFECT_DAE,INFECT_SO,WRITE_DATE,ETC_GUBUN,ETC_GUBUN_CONDITION){
		var comAjax = new ComAjax();
		
		comAjax.setUrl("<c:url value='/status/selectConfirmDtlList.do' />");
		comAjax.addParam("INFECT_GUBUN",INFECT_GUBUN);
		comAjax.addParam("INFECT_DAE",INFECT_DAE);
		comAjax.addParam("INFECT_SO",INFECT_SO);
		comAjax.addParam("WRITE_DATE", WRITE_DATE);
		comAjax.addParam("ETC_GUBUN", ETC_GUBUN);
		comAjax.addParam("ETC_GUBUN_CONDITION", ETC_GUBUN_CONDITION);
		comAjax.setCallback("fn_selectConfirmDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectConfirmDtlList2(INFECT_GUBUN,INFECT_DAE,INFECT_SO,WRITE_DATE,ETC_GUBUN,ETC_GUBUN_CONDITION){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectConfirmDtlList.do' />");
		comAjax.addParam("INFECT_GUBUN",INFECT_GUBUN);
		comAjax.addParam("INFECT_DAE",INFECT_DAE);
		comAjax.addParam("INFECT_SO",INFECT_SO);
		comAjax.addParam("WRITE_DATE", WRITE_DATE);	
		comAjax.addParam("ETC_GUBUN", ETC_GUBUN);
		comAjax.addParam("ETC_GUBUN_CONDITION", ETC_GUBUN_CONDITION);
		comAjax.setCallback("fn_selectConfirmDtlListCallback");
		
		comAjax.ajax();
	}
	
	
	//확진자 - 집단감염
	function fn_selectConfirmJipdanDtlList(JIPDAN_DIVIDE,JIPDAN_INFECT_DAE){
		var comAjax = new ComAjax();
		
		comAjax.setUrl("<c:url value='/status/selectConfirmJipdanDtlList.do' />");
		comAjax.addParam("JIPDAN_DIVIDE",JIPDAN_DIVIDE);
		comAjax.addParam("JIPDAN_INFECT_DAE",JIPDAN_INFECT_DAE);
		comAjax.setCallback("fn_selectConfirmDtlListCallback");
		
		comAjax.ajax();
	}
	
	
	function fn_selectConfirmDtlListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectConfirmList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			   str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
					"			<th>입력일자</th>                    "+
					"			<th>핸드폰번호</th>                   "+
					"			<th>환자번호</th>                    "+
					"			<th>대상자이름</th>                   "+
					"			<th>접촉자번호</th>                   "+
					"			<th>접촉자명</th>                    "+
					"			<th>확진차수</th>                    "+
					"			<th>성별</th>                      "+
					"			<th>생년월일</th>                    "+
					"			<th>직업</th>                      "+
					"			<th>기존주소</th>                    "+
					"			<th>기존상세</th>                    "+
					"			<th>감염경로구분</th>                  "+
					"			<th>감염경로동</th>                   "+
					"			<th>감염경로대구분</th>                 "+
					"			<th>감염경로소구분</th>                 "+
					"			<th>추정감염경로</th>                  "+
					"			<th>추정감염경로상세</th>                "+
					"			<th>확진 판정일</th>                  "+
					"			<th>입원병원</th>                    "+
					"			<th>퇴원일자</th>                    "+
					"			<th>비고 </th>                     "+
					"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
				    	   "<tr class='" + tr_class + "'>" + 
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NUM) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='#this' name='PATIENT_NAME'>" + NvlStr(value.PATIENT_NAME) + "</a>"+
								   "	<input type='hidden' name='PATIENT_NAME' id='UID' value=" + value.ID + ">" +
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONFIRM_GRADE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_DAE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_SO) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_ASFECT_AREA) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.INFECT_ASFECT_AREA_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONFIRM_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.HOSPITAL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DISCHARGE_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.REMARK) + "</td>" +
							   "</tr>"
							   ;
								   
			}); 
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	//자가격리자-해외입국자
	function fn_selectIsoOverseaDtlList(ISO_GUBUN,ISO_TYPE,ISO_DONG,SELDATE){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectIsoOverseaDtlList.do' />");
		comAjax.addParam("ISO_GUBUN",ISO_GUBUN);
		comAjax.addParam("ISO_TYPE", ISO_TYPE);
		comAjax.addParam("ISO_DONG", ISO_DONG);
		comAjax.addParam("SELDATE", SELDATE);	
		comAjax.setCallback("fn_selectIsoOverseaDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectIsoOverseaDtlListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectOverseaDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			  str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
					"			<th>입력일자</th>                    "+
					"			<th>핸드폰번호</th>                   "+
					"			<th>이름</th>                    "+
					"			<th>성별</th>                      "+
					"			<th>생년월일</th>                    "+
					"			<th>직업</th>                      "+
					"			<th>기존주소</th>                    "+
					"			<th>기존상세</th>                    "+
					"			<th>자가격리지동</th>                   "+
					"			<th>자가격리지</th>                   "+
					"			<th>자가격리지상세</th>                   "+
					"			<th>입국일</th>                 "+
					"			<th>격리해제일</th>                 "+
					"			<th>체류국가</th>                  "+
					"			<th>소속</th>                "+
					"			<th>직위</th>                  "+
					"			<th>직급</th>                    "+
					"			<th>전담공무원명</th>                    "+
					"			<th>내선번호 </th>                     "+
					"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
				    	   "<tr class='" + tr_class + "'>" + 
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='#this' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
								   "	<input type='hidden' name='NAME' id='UID' value=" + value.ID + ">" +
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.COME_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.FREE_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.VISIT_NATION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_DEPART) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CLASS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_POSITION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CALL) + "</td>" +
							   "</tr>"
							   ;
								   
			});
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	//자가격리자-국내접촉자
	function fn_selectIsoDomesticDtlList(ISO_GUBUN,ISO_TYPE,ISO_DONG,SELDATE){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectIsoDomesticDtlList.do' />");
		comAjax.addParam("ISO_GUBUN",ISO_GUBUN);
		comAjax.addParam("ISO_TYPE", ISO_TYPE);
		comAjax.addParam("ISO_DONG", ISO_DONG);
		comAjax.addParam("SELDATE", SELDATE);				
		comAjax.setCallback("fn_selectIsoDomesticDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectIsoDomesticDtlListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectIsoDomesticDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			  str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
						"<th>입력일자</th>                        "+   
						"<th>핸드폰번호</th>                       "+   
						"<th>이름</th>                          "+   
						"<th>성별</th>                          "+   
						"<th>생년월일</th>                        "+   
						"<th>직업</th>                          "+   
						"<th>자가격리지동</th>                      "+   
						"<th>자가격리지</th>                       "+   
						"<th>자가격리지상세</th>                     "+   
						"<th>환자번호</th>                        "+   
						"<th>환자명</th>                         "+   
						"<th>접촉자번호</th>                       "+   
						"<th>접촉자명</th>                        "+
						"<th>최종접촉일</th>                     "+
						"<th>모니터링시작일</th>                     "+   
						"<th>모니터링종료일</th>                     "+   
						"<th>접촉장소</th>                        "+   
						"<th>접촉유형</th>                        "+   
						"<th>접촉자 구분</th>                      "+   
						"<th>환자여부</th>                        "+   
						"<th>통지서 발급 여부</th>                   "+   
						"<th>소속</th>                          "+   
						"<th>직위</th>                          "+   
						"<th>직급</th>                          "+   
						"<th>전담공무원명</th>                      "+   
						"<th>내선번호</th>                        "+   
					"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
						    	   "<tr class='" + tr_class + "'>" + 
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='#this' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
								   "	<input type='hidden' name='NAME' id='UID' value=" + value.ID + ">" +
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.FINAL_CONTACT) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.MONITOR_START) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.MONITOR_END) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_TYPE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_YN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BALGUB_YN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_DEPART) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CLASS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_POSITION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CALL) + "</td>" +
							   "</tr>"
							   ;
								   
			});
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	
	//자가격리자-해외입국자
	function fn_selectIsoAllOverseaDtlList(ISO_GUBUN,ISO_CONDITION2,WRITE_DATE){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectIsoOverseaDtlList.do' />");
		comAjax.addParam("ISO_GUBUN",ISO_GUBUN);
		comAjax.addParam("ISO_CONDITION2", ISO_CONDITION2);
		comAjax.addParam("WRITE_DATE", WRITE_DATE);	
		comAjax.setCallback("fn_selectIsoAllOverseaDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectIsoAllOverseaDtlListCallback(data){
		var total = data.total;			
		var body = $("#custom-nav-home");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectIsoAllOverseaDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			  str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
					"			<th>입력일자</th>                    "+
					"			<th>핸드폰번호</th>                   "+
					"			<th>이름</th>                    "+
					"			<th>성별</th>                      "+
					"			<th>생년월일</th>                    "+
					"			<th>직업</th>                      "+
					"			<th>기존주소</th>                    "+
					"			<th>기존상세</th>                    "+
					"			<th>자가격리지동</th>                   "+
					"			<th>자가격리지</th>                   "+
					"			<th>자가격리지상세</th>                   "+
					"			<th>입국일</th>                 "+
					"			<th>격리해제일</th>                 "+
					"			<th>체류국가</th>                  "+
					"			<th>소속</th>                "+
					"			<th>직위</th>                  "+
					"			<th>직급</th>                    "+
					"			<th>전담공무원명</th>                    "+
					"			<th>내선번호 </th>                     "+
					"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
				    	   "<tr class='" + tr_class + "'>" + 
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='#this' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
								   "	<input type='hidden' name='NAME' id='UID' value=" + value.ID + ">" +
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.COME_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.FREE_DATE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.VISIT_NATION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_DEPART) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CLASS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_POSITION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CALL) + "</td>" +
							   "</tr>"
							   ;
								   
			});
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	//자가격리자-국내접촉자
	function fn_selectIsoAllDomesticDtlList(ISO_GUBUN,ISO_CONDITION2,WRITE_DATE){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectIsoDomesticDtlList.do' />");
		comAjax.addParam("ISO_GUBUN",ISO_GUBUN);
		comAjax.addParam("ISO_CONDITION2", ISO_CONDITION2);
		comAjax.addParam("WRITE_DATE", WRITE_DATE);				
		comAjax.setCallback("fn_selectIsoAllDomesticDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectIsoAllDomesticDtlListCallback(data){
		var total = data.total;			
		var body = $("#custom-nav-profile");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectIsoAllDomesticDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			  str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
						"<th>입력일자</th>                        "+   
						"<th>핸드폰번호</th>                       "+   
						"<th>이름</th>                          "+   
						"<th>성별</th>                          "+   
						"<th>생년월일</th>                        "+   
						"<th>직업</th>                          "+   
						"<th>기존주소</th>                        "+   
						"<th>기존상세</th>                        "+   
						"<th>자가격리지동</th>                      "+   
						"<th>자가격리지</th>                       "+   
						"<th>자가격리지상세</th>                     "+   
						"<th>환자번호</th>                        "+   
						"<th>환자명</th>                         "+   
						"<th>접촉자번호</th>                       "+   
						"<th>접촉자명</th>                        "+
						"<th>최종접촉일</th>                     "+
						"<th>모니터링시작일</th>                     "+   
						"<th>모니터링종료일</th>                     "+   
						"<th>접촉장소</th>                        "+   
						"<th>접촉유형</th>                        "+   
						"<th>접촉자 구분</th>                      "+   
						"<th>환자여부</th>                        "+   
						"<th>통지서 발급 여부</th>                   "+   
						"<th>소속</th>                          "+   
						"<th>직위</th>                          "+   
						"<th>직급</th>                          "+   
						"<th>전담공무원명</th>                      "+   
						"<th>내선번호</th>                        "+   
					"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
						    	   "<tr class='" + tr_class + "'>" + 
								   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
								   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
								   "<td align='center' style='color:blue;'>" +
								   "	<a href='#this' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
								   "	<input type='hidden' name='NAME' id='UID' value=" + value.ID + ">" +
								   "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.ORG_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_DONG) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.SELF_ISO_AREA_ADDRESS_DTL) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NUM) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.FINAL_CONTACT) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.MONITOR_START) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.MONITOR_END) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_ADDRESS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_TYPE) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONTACT_GUBUN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.PATIENT_YN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.BALGUB_YN) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_DEPART) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CLASS) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_POSITION) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_NAME) + "</td>" +
								   "<td align='center' style='color:#444444;'>" + NvlStr(value.DAMDANG_CALL) + "</td>" +
							   "</tr>"
							   ;
								   
			});
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	//상담민원
	function fn_selectConsultDtlList(CONSULT_DEPART,CONSULT_JUYA_GUBUN,CONSULT_CONSULT_GUBUN,WRITE_DATE){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectConsultDtlList.do' />");
		comAjax.addParam("CONSULT_DEPART",CONSULT_DEPART);
		comAjax.addParam("CONSULT_JUYA_GUBUN", CONSULT_JUYA_GUBUN);
		comAjax.addParam("CONSULT_CONSULT_GUBUN", CONSULT_CONSULT_GUBUN);	
		comAjax.addParam("WRITE_DATE", WRITE_DATE);	
		comAjax.setCallback("fn_selectConsultDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectConsultDtlListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectConsultDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			  str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
							"<th>입력일자</th>                          "+
							"<th>상담구분</th>                          "+
							"<th>시간</th>                            "+
							"<th>담당자</th>                           "+
							"<th>성명</th>                            "+
							"<th>성별</th>                            "+
							"<th>연락처</th>                           "+
							"<th>상담내역</th>                          "+
							"<th>처리내용</th>                          "+
				"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
				    	   "<tr class='" + tr_class + "'>" + 
							   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
							   "<td width=200 align='center' style='color:#444444;'>" + NvlStr(value.CONSULT_GUBUN) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.CONSULT_TIME) + "</td>" +
							   "<td align='center' style='color:blue;'>" +
							   "	<a href='#this' name='NAME'>" + NvlStr(value.CONSULT_NAME) + "</a>"+
							   "	<input type='hidden' name='NAME' id='UID' value=" + value.ID + ">" +
							   "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.MINWON_NAME) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.MINWON_PHONE) + "</td>" +
							   "<td align='left'' style='color:#444444;'>" + NvlStr(value.MINWON_CONTENT) + "</td>" +
							   "<td align='left' style='color:#444444;'>" + NvlStr(value.PROCESS_RESULT) + "</td>" +
						   "</tr>"
						   ;
								   
			});
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	
	//선별진료소
	function fn_selectClinicDtlList(CLINIC_DEPART,CLINIC_SUSPICION_GUBUN,CLINIC_SUSPICION_DAE,CLINIC_SUSPICION_SO,WRITE_DATE,ISO){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectClinicDtlList.do' />");
		comAjax.addParam("CLINIC_DEPART",CLINIC_DEPART);
		comAjax.addParam("CLINIC_SUSPICION_GUBUN",CLINIC_SUSPICION_GUBUN);
		comAjax.addParam("CLINIC_SUSPICION_DAE", CLINIC_SUSPICION_DAE);
		comAjax.addParam("CLINIC_SUSPICION_SO", CLINIC_SUSPICION_SO);
		comAjax.addParam("ISO", ISO);
		
		comAjax.addParam("WRITE_DATE", WRITE_DATE);		
		
		comAjax.setCallback("fn_selectClinicDtlListCallback");
		
		comAjax.ajax();
	}
	
	//선별진료소 양성자
	function fn_selectClinicConfirmDtlList(CLINIC_CONFIRM_DEPART,CLINIC_CONFIRM_SUSPICION_GUBUN,CLINIC_CONFIRM_SUSPICION_DAE,CLINIC_CONFIRM_SUSPICION_SO,WRITE_DATE,CONFIRM_ISO){
		
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectClinicConfirmDtlList.do' />");
		comAjax.addParam("CLINIC_CONFIRM_DEPART",CLINIC_CONFIRM_DEPART);
		comAjax.addParam("CLINIC_CONFIRM_SUSPICION_GUBUN",CLINIC_CONFIRM_SUSPICION_GUBUN);
		comAjax.addParam("CLINIC_CONFIRM_SUSPICION_DAE", CLINIC_CONFIRM_SUSPICION_DAE);
		comAjax.addParam("CLINIC_CONFIRM_SUSPICION_SO", CLINIC_CONFIRM_SUSPICION_SO);
		comAjax.addParam("CONFIRM_ISO", CONFIRM_ISO);
		
		comAjax.addParam("WRITE_DATE", WRITE_DATE);		
		
		comAjax.setCallback("fn_selectClinicDtlListCallback");
		
		comAjax.ajax();
	}
	
	//선별진료소 케이스
	function fn_selectClinicCaseDtlList(CLINIC_CASE_CONDITION,CLINIC_CASE_SELDATE){
		var comAjax = new ComAjax();
		comAjax.setUrl("<c:url value='/status/selectClinicCaseDtlList.do' />");
		comAjax.addParam("CLINIC_CASE_CONDITION",CLINIC_CASE_CONDITION);
		comAjax.addParam("CLINIC_CASE_SELDATE", CLINIC_CASE_SELDATE);			
		comAjax.setCallback("fn_selectClinicDtlListCallback");
		
		comAjax.ajax();
	}
	
	function fn_selectClinicDtlListCallback(data){
		var total = data.total;			
		var body = $("#mytable");
		var recordcnt = $("#RECORD_COUNT").val();
		
		
		$("#tcnt").text(comma(total));
		
		body.empty();
		if(total == 0){
			var str = "<tr>" + 
				  "<td colspan='14' style='text-align:center;'>조회된 결과가 없습니다.</td>" +
				  "</tr>";
			body.append(str);
		}
		else{
			var params = {
				divId : "PAGE_NAVI",
				pageIndex : "PAGE_INDEX",
				totalCount : total,
				recordCount : recordcnt,
				eventName : "fn_selectClinicDtlList"
			};
			gfn_renderPaging(params);
			
			var str = "";
			var t_str = "";
			var tr_class = "";
			var jsonData = null;
			var jsonStr = null;
			
			  str += 
				     "<thead style='background-color:#0B2161;'>  "+
				     "   <tr align='center'>                     "+
						"<th>입력일자</th>                        "+ 
						"<th>방문일자</th>                        "+ 
						"<th>핸드폰번호</th>                       "+ 
						"<th>이름</th>                          "+ 
						"<th>성별</th>                          "+ 
						"<th>생년월일</th>                        "+ 
						"<th>직업</th>                          "+
						"<th>검사케이스</th>                          "+ 
						"<th>사례분류</th>                        "+ 
						"<th>의심경로구분</th>                      "+ 
						"<th>의심경로대구분</th>                     "+ 
						"<th>의심경로소구분</th>                     "+ 
				"		</tr>                                "+
				     " </thead>                                  "+
				    	   "<tbody>";
			$.each(data.list, function(key, value){
				       t_str = "";
					
				       if ((value.RNUM % 2) == 0 )
							tr_class = "tr1";
					   else
							tr_class = "tr2";
				      
				       
				       str += 
				    	   "<tr class='" + tr_class + "'>" + 
							   "<td width=90 align='center' style='color:#444444;word-break:break-all;'><span class='status--denied'>" + formatDate(NvlStr(value.WRITE_DATE), ".") + "</span></td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.CLINIC_VISIT_DATE) + "</td>"  +
							   "<td align='center' style='color:#444444;'>" + hp_format(NvlStr(value.CELL_NUM)) + "</td>" +
							   "<td align='center' style='color:blue;'>" +
							   "	<a href='javascript:void(0);' onclick='fn_updateClinic("+ value.ID +")' name='NAME'>" + NvlStr(value.NAME) + "</a>"+
							   "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SEX) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.BIRTH) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.JOB) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.INSPECTION_CASE) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SARAE_GUBUN) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUSPICION_GUBUN) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUSPICION_DAE) + "</td>" +
							   "<td align='center' style='color:#444444;'>" + NvlStr(value.SUSPICION_SO) + "</td>" +
						   "</tr>"
						   ;
								   
			});
			
			str += "</tbody>"
			body.append(str);
		}
	}
	
	//수정화면가기
	function fn_updateClinic(UID) {
		var comSubmit = new ComSubmit();
		
		comSubmit.setUrl("<c:url value='/write/updateClinicView.do' />");
		comSubmit.addParam("ID", UID);
		comSubmit.submit();
	}
	
</script>

<style>
.tb_wrap {
	position: relative;
	width: 100%;
	padding-top: 25px;
}

.tb_box {
	max-height: 300px;
	overflow-y: scroll;
	border-bottom: 1px solid #dedede;
}

.fixed_top {
	display: inline-table;
	position: absolute;
	top: 0;
	width: 100%;
	background: black;
}

</style>
</head>

<body>

<div style="background-color:#170B3B;color:white; margin-bottom:10px; width: 100%;">
	<a href="#this" class="btn" id="logout">
		<img src="/corona/images/popup_title.gif" align="absmiddle">
	</a>현황 상세 정보
	
	<div style="padding-right:10px;padding-top:7px;float:right;">
		<a href="javascript:history.back()" class="btn btn-success" id="statistics"><i class="fa fa-reply"></i> 현황 요약 정보</a>
	</div>
	
</div>

<!-- /# column -->
<div class="col-lg-6" id="disp_tab" style='display:none;'>
	<div class="card">
		<div class="card-body">
			<div class="custom-tab">

				<nav>
					<div class="nav nav-tabs" id="nav-tab" role="tablist">
						<a class="nav-item nav-link active" id="custom-nav-home-tab" data-toggle="tab" href="#custom-nav-home" role="tab" aria-controls="custom-nav-home"
						 aria-selected="true">해외</a>
						<a class="nav-item nav-link" id="custom-nav-profile-tab" data-toggle="tab" href="#custom-nav-profile" role="tab" aria-controls="custom-nav-profile"
						 aria-selected="false">국내</a>
					</div>
				</nav>
				<div class="tab-content pl-3 pt-2" id="nav-tabContent">
					<div class="tab-pane fade show active" id="custom-nav-home" role="tabpanel" aria-labelledby="custom-nav-home-tab">
					</div>
					<div class="tab-pane fade" id="custom-nav-profile" role="tabpanel" aria-labelledby="custom-nav-profile-tab">
					</div>
				</div>

			</div>
		</div>
	</div>
</div>
<!-- /# column -->


<div align="left" style="width: 100%; padding-right: 20px;" id="disp_former">
	<div style="margin-left: 20px;" align="center">
		<div class="table-responsive m-b-40">
			<table class="table table-borderless table-data3" id="mytable">
			</table>
		</div>
	</div>
</div>


								


<%@ include file="/WEB-INF/include/include-body.jspf" %>
</body>
</html>