<%@ page contentType="text/html;charset=euc-kr" %><%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %><%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %><%response.setHeader("Content-Disposition", "attachment; filename=" + nexti.ejms.util.commfunction.fileNameFix(((String)request.getAttribute("reqTitle") + ".hwp").replaceAll(";", ":")));%><bean:define id="rchno" name="researchForm" property="rchno"/><bean:define id="totcnt" name="researchForm" property="anscount"/><bean:define id="deptName" name="researchForm" property="sch_deptcd"/><logic:equal name="researchForm" property="range" value="1">�� �μ��� : <logic:equal name="researchForm" property="sch_orggbn" value="%">��ü</logic:equal><logic:notEqual name="researchForm" property="sch_orggbn" value="%"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("023",(String)request.getAttribute("sch_orggbn"))%></logic:notEqual>  <logic:equal name="researchForm" property="sch_deptcd" value="%">��ü</logic:equal><logic:notEqual name="researchForm" property="sch_deptcd" value="%"><%=nexti.ejms.dept.model.DeptManager.instance().getDeptInfo((String)deptName).getDept_name()%></logic:notEqual>   �� ���� : <logic:equal name="researchForm" property="sch_sex" value="%">��ü</logic:equal><logic:equal name="researchForm" property="sch_sex" value="M">����</logic:equal><logic:equal name="researchForm" property="sch_sex" value="F">����</logic:equal>   �� ���� : <logic:equal name="researchForm" property="sch_age" value="">��ü</logic:equal><logic:notEqual name="researchForm" property="sch_age" value=""><bean:write filter="false" name="researchForm" property="sch_age"/>��</logic:notEqual><%="\n\n===== �������� : " + (String)request.getAttribute("reqTitle")%></logic:equal><logic:equal name="researchForm" property="range" value="2">===== �������� : <%=(String)request.getAttribute("reqTitle")%></logic:equal> =====

�� ��������
<bean:write filter="false" name="researchForm" property="summary"/>

�� ���� ����� : [<bean:write filter="false" name="researchForm" property="coldeptnm"/>] <bean:write filter="false" name="researchForm" property="chrgusrnm"/> <logic:notEmpty name="researchForm" property="coldepttel">������ȭ(<bean:write filter="false" name="researchForm" property="coldepttel"/>)</logic:notEmpty>
�� �� �� �� �� : <bean:write filter="false" name="researchForm" property="strymd"/> ����  <bean:write filter="false" name="researchForm" property="endymd"/> ����
�� �� �����ڼ� : <bean:write filter="false" name="researchForm" property="anscount"/>��
<% if ( nexti.ejms.common.appInfo.isOutside() ) { %>�� �� �� �� �� : <logic:equal name="researchForm" property="range" value="1"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","1")%></logic:equal><logic:equal name="researchForm" property="range" value="2"><%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013","2")%><bean:define id="rangedetail" name="researchForm" property="rangedetail"/>(<%=nexti.ejms.ccd.model.CcdManager.instance().getCcdName("013", rangedetail.toString())%>)</logic:equal><% } %><logic:notEmpty name="researchForm" property="listrch"><logic:iterate id="list" name="researchForm" property="listrch" indexId="idx"><bean:define id="formtype" name="list" property="formtype"/><bean:define id="listseq" name="list" property="formseq"/><%="\n"%><logic:notEmpty name="list" property="formgrp">
<bean:write filter="false" name="list" property="formgrp" filter="false"/></logic:notEmpty>
<%=idx.intValue()+1%>. <bean:write filter="false" name="list" property="formtitle"/><logic:notEmpty name="list" property="originfilenm"> (÷������:<bean:write filter="false" name="list" property="originfilenm"/>)</logic:notEmpty><logic:lessEqual name="list" property="formtype" value="02"><logic:notEqual name="list" property="sch_exam" value="%"><bean:define id="exam" name="list" property="examList"/><bean:define id="sch_examNo" name="list" property="sch_exam"/><%
	String msg = "��Ÿ";
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
		out.print("\n* �亯������ : " + msg);
	}
%></logic:notEqual><bean:size id="esize" name="list" property="examList"/><logic:notEmpty name="list" property="examList"><logic:iterate id="elist" name="list" property="examList" indexId="eidx"><logic:notEmpty name="elist" property="examcont"><bean:define id="anscnt" name="elist" property="examcnt"/>
 <%=eidx.intValue()+1%>) <bean:write filter="false" name="elist" property="examcont"/><logic:notEmpty name="elist" property="originfilenm"> (÷������:<bean:write filter="false" name="elist" property="originfilenm"/>)</logic:notEmpty> (<bean:write filter="false" name="elist" property="examcnt"/>��/<%
	int rate = 0;
	double drate = 0;
	if(Integer.parseInt("0" + totcnt.toString(), 10) > 0){
		drate  = (Double.parseDouble(anscnt.toString())/Double.parseDouble(totcnt.toString()))*100;
		rate = (int)Math.round(drate);
	}
	out.write(Integer.toString(rate, 10));
%>%)</logic:notEmpty></logic:iterate></logic:notEmpty><logic:equal name="list" property="examtype" value="Y"><bean:size id="osize" name="list" property="otherList"/>
 <%=esize.intValue()+1 %>) ��Ÿ  (<%=osize.intValue()%>�� /<%
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
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �������� ��ȸ�ȼ��������� �ѱ۴ٿ�ε�
 * ����:
--%>