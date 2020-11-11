/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����ϱ�
 * ����:
 */

/**
 * ��ȯ�� ���� ����
 */
var result;

function viewForm() {	
	//�������,�ý��۹�����ȣ,��Ĺ�ȣ,�߰��Ű�����
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/deliveryFormatLineView.do";
	else if (args[0] == "02")	childURL = "/deliveryFormatFixedView.do";
	else if (args[0] == "03") childURL = "/deliveryFormatBookView.do";
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

// ���ó���� ��ȿ�� üũ
function IsAssignCharge(sysdocno) {
	var params = '';
	
	params += 'sysdocno=' + encodeURIComponent(sysdocno);

	result = new Ajax.Request('/isAssignCharge.do', {method: 'post', postBody: params, onComplete: callBack});
}

function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var sysdocno, gubun, retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	sysdocno = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	
	if(retCode == "OVERTIME") {
		alert("���������� �ʰ��� �����Դϴ�.\n����Ͻ÷��� ���մ���ڿ��� �������� ���� ��û�� �Ͻñ� �ٶ��ϴ�.");
		return;
	} else if(retCode == "NONINPUTUSR") {
		alert("�Է´���ڰ� ������ ���� �ʾҽ��ϴ�.\n�Է´���ڸ� �����Ͻʽÿ�.");
		location.href = "/deliveryCollDocInfoView.do" + location.href.substring(location.href.indexOf("?"));
		return;
	} else if(retCode == "NONAPPROVAL") {
//			var cnf = window.confirm("�����ڰ� �������� �ʾҽ��ϴ�.\n����� ����� ó���Ͻðڽ��ϱ�?");
			
//			if(cnf) {
				location.href = "/deliveryProcess.do?sysdocno=" + sysdocno;
//				return;
//			}
			
			return;
	} else if(retCode == "OK") {
		var cnf = window.confirm("����Ͻðڽ��ϱ�?");
		
		if(cnf) {
			location.href = "/deliveryProcess.do?sysdocno=" + sysdocno;
		}
	} else {
		alert("�˼����� ������ �߻��߽��ϴ�. ����� �ٽ� �õ��ϼ���.\n������ ���ӵ� ��� �����ڿ��� �����ϼ���.");
		return;
	}
}

function chkDeliveryProc(sysdocno)
{
	IsAssignCharge(sysdocno);
}

//�˻� ��ư Ŭ����
function getSearch(frm, isSysMgr){
  if(isSysMgr == '001'){
		if(frm.sch_deptnm.value == '' && frm.sch_usernm.value == '') frm.initentry.value = "first";
		else	frm.initentry.value = "second";
	}else{
		frm.initentry.value = "first";
	}
	frm.submit();
}

//������ ������ ȣ���
var formationObj;
function fTree(frm, orggbn, orgid){
	if(orggbn == 'null') orggbn = "";
	if(orgid == 'null')  orgid = "";
	
	var url  = '/formation/formation.jsp?orggbn=' + orggbn + '&orgid=' + orgid;
	formationObj = window.open(url, "popup", "width=390,height=700");
}

//���������� �Ѿ���� ������ : ������ ����Ŭ���� ȣ�� �޼ҵ� 
function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if(formationObj != null) formationObj.close();
	if(grpgbn == 2){
		document.forms[0].sch_old_deptcd.value = orgid;
		document.forms[0].sch_deptnm.value 		 = orgfullnm;
		document.forms[0].sch_old_userid.value = "";
		document.forms[0].sch_usernm.value 		 = "";
	}else{
		document.forms[0].sch_old_deptcd.value = upper_orgid;
		document.forms[0].sch_deptnm.value 	 	 = upper_orgfullnm;
		document.forms[0].sch_old_userid.value = orgid;
		document.forms[0].sch_usernm.value 		 = orgnm;
	}
	getSearch(document.forms[0], '001');
}

function click_popup(actionpath, width, height) {
	var left = (screen.width - width) / 2;
	var top = (screen.height - height) / 2;
	var option = 
		"dialogWidth:" + width + "px; " +
		"dialogHeight:" + height + "px; " +
		"dialogLeft:" + left + "px; " +
		"dialogTop:" + top + "px; " +
		"resizable:yes; scroll:yes; status:yes; center:no; " +
		"dialogHide:no; edge:raised; help:no; unadorned:no";

	try {
		window.showModalDialog(actionpath , window, option);
	} catch(exception) {
		alert("�̸����� ����� ����Ͻ÷��� �˾� ���� ����� ���ֽðų�\n" +
					"�˾� ��� ����Ʈ�� ������ֽñ� �ٶ��ϴ�.\n\n" +
					"������ �� ���ͳݿɼ� �� �������� �� �˾����ܻ��(üũ����)\n" +
					"�Ǵ� ����(��ưŬ��) �� ����� �� ����Ʈ �ּ� �Է� �� �߰�");
		try {processingHide();} catch(ex) {}
		try {parent.processingHide();} catch(ex) {}
	}
}