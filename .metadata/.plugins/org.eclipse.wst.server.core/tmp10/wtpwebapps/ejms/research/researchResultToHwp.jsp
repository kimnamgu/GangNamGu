<%@ page contentType="text/html;charset=euc-kr" %><%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %><%response.setHeader("Content-Disposition", "attachment; filename=" + nexti.ejms.util.commfunction.fileNameFix(((String)request.getAttribute("reqTitle") + ".hwp").replaceAll(";", ":")));%><bean:define id="rchno" name="researchForm" property="rchno"/><bean:define id="totcnt" name="researchForm" property="anscount"/><bean:define id="deptName" name="researchForm" property="sch_deptcd"/><logic:equal name="researchForm" property="range" value="1">▶ 부서별 : <logic:equal name="researchForm" property="sch_orggbn" value="%">전체</logic:equal><logic:notEqual name="researchForm" property="sch_orggbn" value="%"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("023",(String)request.getAttribute("sch_orggbn"))%></logic:notEqual>  <logic:equal name="researchForm" property="sch_deptcd" value="%">전체</logic:equal><logic:notEqual name="researchForm" property="sch_deptcd" value="%"><%=nexti.ejms.dept.model.DeptManager.instance().getDeptInfo((String)deptName).getDept_name()%></logic:notEqual>   ▶ 성별 : <logic:equal name="researchForm" property="sch_sex" value="%">전체</logic:equal><logic:equal name="researchForm" property="sch_sex" value="M">남자</logic:equal><logic:equal name="researchForm" property="sch_sex" value="F">여자</logic:equal>   ▶ 연령 : <logic:equal name="researchForm" property="sch_age" value="">전체</logic:equal><logic:notEqual name="researchForm" property="sch_age" value=""><bean:write filter="false" name="researchForm" property="sch_age"/>세</logic:notEqual><%="\n\n===== 설문제목 : " + (String)request.getAttribute("reqTitle")%></logic:equal><logic:equal name="researchForm" property="range" value="2">===== 설문제목 : <%=(String)request.getAttribute("reqTitle")%></logic:equal> =====

● 설문개요
<bean:write filter="false" name="researchForm" property="summary"/>

● 설문 담당자 : [<bean:write filter="false" name="researchForm" property="coldeptnm"/>] <bean:write filter="false" name="researchForm" property="chrgusrnm"/> <logic:notEmpty name="researchForm" property="coldepttel">문의전화(<bean:write filter="false" name="researchForm" property="coldepttel"/>)</logic:notEmpty>
● 설 문 기 간 : <bean:write filter="false" name="researchForm" property="strymd"/> 부터  <bean:write filter="false" name="researchForm" property="endymd"/> 까지
● 총 참여자수 : <bean:write filter="false" name="researchForm" property="anscount"/>명
<% if ( nexti.ejms.common.appInfo.isOutside() ) { %>● 설 문 범 위 : <logic:equal name="researchForm" property="range" value="1"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%></logic:equal><logic:equal name="researchForm" property="range" value="2"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%><bean:define id="rangedetail" name="researchForm" property="rangedetail"/>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)</logic:equal><% } %><logic:notEmpty name="researchForm" property="listrch"><logic:iterate id="list" name="researchForm" property="listrch" indexId="idx"><bean:define id="formtype" name="list" property="formtype"/><bean:define id="listseq" name="list" property="formseq"/><%="\n"%><logic:notEmpty name="list" property="formgrp">
<bean:write filter="false" name="list" property="formgrp" filter="false"/></logic:notEmpty>
<%=idx.intValue()+1%>. <bean:write filter="false" name="list" property="formtitle"/><logic:notEmpty name="list" property="originfilenm"> (첨부파일:<bean:write filter="false" name="list" property="originfilenm"/>)</logic:notEmpty><logic:lessEqual name="list" property="formtype" value="02"><logic:notEqual name="list" property="sch_exam" value="%"><bean:define id="exam" name="list" property="examList"/><bean:define id="sch_examNo" name="list" property="sch_exam"/><%
	String msg = "기타";
	try {
		int examNo = Integer.parseInt(((String)sch_examNo).substring(idx.toString().length()), 10);
		java.util.List lstExam = (java.util.List)exam;
		nexti.ejms.research.model.ResearchExamBean rebean = null;
		if (examNo > 0) {
			rebean = (nexti.ejms.research.model.ResearchExamBean)lstExam.get(examNo - 1);
			msg = rebean.getExamcont();
		}
	} catch (Exception e) {
		msg = "";
	} finally {
		out.print("\n* 답변유형별 : " + msg);
	}
%></logic:notEqual><bean:size id="esize" name="list" property="examList"/><logic:notEmpty name="list" property="examList"><logic:iterate id="elist" name="list" property="examList" indexId="eidx"><logic:notEmpty name="elist" property="examcont"><bean:define id="anscnt" name="elist" property="examcnt"/>
 <%=eidx.intValue()+1%>) <bean:write filter="false" name="elist" property="examcont"/><logic:notEmpty name="elist" property="originfilenm"> (첨부파일:<bean:write filter="false" name="elist" property="originfilenm"/>)</logic:notEmpty> (<bean:write filter="false" name="elist" property="examcnt"/>명/<%
	int rate = 0;
	double drate = 0;
	if(Integer.parseInt("0" + totcnt.toString(), 10) > 0){
		drate  = (Double.parseDouble(anscnt.toString())/Double.parseDouble(totcnt.toString()))*100;
		rate = (int)Math.round(drate);
	}
	out.write(Integer.toString(rate, 10));
%>%)</logic:notEmpty></logic:iterate></logic:notEmpty><logic:equal name="list" property="examtype" value="Y"><bean:size id="osize" name="list" property="otherList"/>
 <%=esize.intValue()+1 %>) 기타  (<%=osize.intValue()%>명 /<%
	int rate = 0;
	double drate = 0;
	if(Integer.parseInt(totcnt.toString()) > 0){
		drate  = (Double.parseDouble(osize.toString())/Double.parseDouble(totcnt.toString()))*100;
		rate = (int)Math.round(drate);
	}
	out.write(Integer.toString(rate, 10));
%>%)<logic:notEmpty name="list" property="otherList"><logic:iterate id="olist" name="list" property="otherList" indexId="oidx"><logic:notEmpty name="elist" property="examcont">
   - <bean:write filter="false" name="olist" property="other"/></logic:notEmpty></logic:iterate></logic:notEmpty></logic:equal></logic:lessEqual><logic:greaterEqual name="list" property="formtype" value="03"><logic:notEmpty name="list" property="examList"><logic:iterate id="elist" name="list" property="examList"><logic:notEmpty name="elist" property="anscont">
 - <bean:write filter="false" name="elist" property="anscont"/></logic:notEmpty></logic:iterate></logic:notEmpty></logic:greaterEqual></logic:iterate></logic:notEmpty>
 <%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 설문조사 조회된설문조사결과 한글다운로드
 * 설명:
--%>