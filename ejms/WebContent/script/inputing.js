/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է��ϱ�
 * ����:
 */
 
/**
 * ��ȯ�� ���� ����
 */
var result;
var docno;
var move_path;
var chrgunitInputPagePath;
var changed = false;
var param = location.href.substring(location.href.indexOf("?"));

function collprocViewForm() {	
	//�������,�ý��۹�����ȣ,��Ĺ�ȣ,�߰��Ű�����
	var args = collprocViewForm.arguments;
	var childURL = "";
	var parentURL = "/collproc";
	var addQueryString = "";

	if (args[0] == "01")			{childURL = "/formatLineOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "02")	{childURL = "/formatFixedOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	else if (args[0] == "03") {childURL = "/formatBookOtherDeptDataView.do"; addQueryString = "&includeNotSubmitData=true";}
	
	showPopup(parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3] + addQueryString, '905', '600');
}

function viewForm() {	
	//�������,�ý��۹�����ȣ,��Ĺ�ȣ,�߰��Ű�����
	var args = viewForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/inputFormatLineView.do";
	else if (args[0] == "02")	childURL = "/inputFormatFixedView.do";
	else if (args[0] == "03") childURL = "/inputFormatBookView.do";

	try {
		if(inputComplete == true) viewCompForm(args[0], args[1], args[2], args[3]);
		return;
	} catch (e) {}
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

function viewCompForm() {	
	//�������,�ý��۹�����ȣ,��Ĺ�ȣ,�߰��Ű�����
	var args = viewCompForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/inputCompFormatLineView.do";
	else if (args[0] == "02")	childURL = "/inputCompFormatFixedView.do";
	else if (args[0] == "03") childURL = "/inputCompFormatBookView.do";
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

function viewModifyForm() {	
	//�������,�ý��۹�����ȣ,��Ĺ�ȣ,�߰��Ű�����
	var args = viewModifyForm.arguments;
	var childURL = "";
	var parentURL = "";

	if (args[0] == "01")			childURL = "/inpCompFrmLineModifyView.do";
	else if (args[0] == "02")	childURL = "/inpCompFrmFixedModifyView.do";
	else if (args[0] == "03") childURL = "/inpCompFrmBookModifyView.do";
	
	location.href = parentURL + childURL + "?formkind=" + args[0] + "&sysdocno=" + args[1] + "&formseq=" + args[2] + "&" + args[3];
}

function IsAssignChrgUnit(sysdocno) {
	var params = '';
	
	docno = sysdocno;
	
	result = new Ajax.Request('/isAssignChrgUnit.do', {method: 'post', postBody: params, onComplete: callBack});
}

function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	retCode = resultNode[0].childNodes[0].text;
	
	if(move_path == "notapplicable") {
		location.href = "/collDocNotApplicable.do" + param + "&sysdocno=" + docno;
	} else {
		location.href = "/inputingComplete.do" + param + "&sysdocno=" + docno;
	}
	/*
	if(retCode == "NOTASSIGN") {
		alert("�Է´������ �������� �������� �ʾҽ��ϴ�.\n�������� �����Ͻʽÿ�.");
		location.href=chrgunitInputPagePath;
		return;
	} else {
		if(move_path == "notapplicable") {
			location.href = "/collDocNotApplicable.do" + param + "&sysdocno=" + docno;
		} else {
			location.href = "/inputingComplete.do" + param + "&sysdocno=" + docno;
		}
	}
	*/
}

function IsExistDataFrm(sysdocno, path) {
	var params = '';
	
	docno = sysdocno;
	move_path = path;
	
	params += 'sysdocno=' + encodeURIComponent(sysdocno);
	
	result = new Ajax.Request('/isExistDataFrm.do', {method: 'post', postBody: params, onComplete: completeexecute});
}

function completeexecute(resXML){
	var xmlDoc = resXML.responseXML;
	var retCode;
	var cnf;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	retCode = resultNode[0].childNodes[0].text;
	
	if(retCode == "EXIST" && move_path == "notapplicable") {
		cnf = window.confirm("�Է��ڷᰡ �ֽ��ϴ�.\n�Է��ڷ���� ó���� �ڷᰡ �����˴ϴ�.\n\n�Է��ڷ���� ó�� �Ͻðڽ��ϱ�?");
		
		if(cnf) {
			IsAssignChrgUnit(docno);
		}
	} else if(retCode == "NOTEXIST" && move_path == "inputcomp") {
		cnf = window.confirm("�Է��ڷᰡ �����ϴ�.\n\n�Է��ڷ���� ó�� �Ͻðڽ��ϱ�?");
		
		if(cnf) {
			move_path = "notapplicable";
			IsAssignChrgUnit(docno);
		}
	} else if(retCode == "NOTEXIST" && move_path == "notapplicable") {
		cnf = window.confirm("�Է��ڷ���� ó�� �Ͻðڽ��ϱ�?");
		
		if(cnf) {
			IsAssignChrgUnit(docno);
		}
	} else if(retCode == "EXIST" && move_path == "inputcomp") {
		cnf = window.confirm("�Է��ڷ����� ó�� �Ͻðڽ��ϱ�?");
		
		if(cnf) {
			IsAssignChrgUnit(docno);
		}
	}
}

function CollDocNotApplicable(sysdocno, _path) {
	chrgunitInputPagePath = _path;
	IsExistDataFrm(sysdocno, "notapplicable");
}

function InputingComplete(sysdocno, _path) {
	if(changed == true) {
		changeCheck(sysdocno);
	} else {
		chrgunitInputPagePath = _path;
		IsExistDataFrm(sysdocno, "inputcomp");
	}
}

function setChanged() {
	changed = true;
}

function changeCheck(sysdocno) {
	if(document.forms.length != 1) {
		return;
	}
	
	if(confirm("����� ������ ���� �Ͻðڽ��ϱ�?") == false) {
		IsExistDataFrm(sysdocno, "inputcomp");
		return;
	}
	
	var frm = document.forms[0];
	
	if(frm.name.toLowerCase().indexOf("line") > -1) {
		document.images["savebutton"].click();
	} else if(frm.name.toLowerCase().indexOf("fixed") > -1) {
		document.forms[0].target = "actionFrame";
		document.forms[0].mode.value = "insert";
		document.forms[0].action += param;
		document.forms[0].submit();
	} else if(frm.name.toLowerCase().indexOf("book") > -1) {
	 	var title = document.dataBookForm.formtitle.value;
		if( title == "") {
			alert("������ �Է��Ͻñ� �ٶ��ϴ�.");
			document.dataBookForm.formtitle.focus();
			return;
		} else if(title.length > 100){
			alert("������ �Է³����� �ڸ�����  �ʰ� �Ͽ����ϴ� .");
			document.dataBookForm.formtitle.focus();
			return;	
		}else if(document.dataBookForm.inputFile.value == "") {
			alert("���ε��� ������ �����Ͽ� �ֽʽÿ�.");
			return;
		} else {			
			document.dataBookForm.target = "actionFrame";
			document.dataBookForm.action += param;
			document.dataBookForm.submit();
		}
	}
}

function InputInsertDataBookFrm() {
 	var title = document.dataBookForm.formtitle.value;
	if( title == "") {
		alert("������ �Է��Ͻñ� �ٶ��ϴ�.");
		document.dataBookForm.formtitle.focus();
		return;
	} else if(title.length > 100){
		alert("������ �Է³����� �ڸ�����  �ʰ� �Ͽ����ϴ� .");
		document.dataBookForm.formtitle.focus();
		return;	
	}else if(document.dataBookForm.inputFile.value == "") {
		alert("���ε��� ������ �����Ͽ� �ֽʽÿ�.");
		return;
	} else {
		if(window.confirm("����Ͻðڽ��ϱ�?") == false) {
			return;
		}		
		document.dataBookForm.target = "actionFrame";
		document.dataBookForm.action += param;
		document.dataBookForm.submit();
	}
}

function InputDeleteDataBookFrm(sysdocno, formseq, fileseq, filename) {
	var cnf = window.confirm("������ �����Ͻðڽ��ϱ�?\n�ѹ� ������ �����ʹ� ������ ���� �ʽ��ϴ�.");
	
	if(cnf) {
		location.href = "/inputDataBookDelete.do" + param + "&sysdocno=" + sysdocno + "&formseq=" + formseq + "&fileseq=" + fileseq + "&filename=" + filename;
	}
}

function InputCompInsertDataBookFrm() {
	if(document.dataBookForm.formtitle.value == "") {
		alert("������ �Է��Ͻñ� �ٶ��ϴ�.");
		document.dataBookForm.formtitle.focus();
		return;
	} else if(document.dataBookForm.inputFile.value == "") {
		alert("���ε��� ������ �����Ͽ� �ֽʽÿ�.");
		return;
	} else {
		document.dataBookForm.target = "actionFrame";
		document.dataBookForm.action += param;
		document.dataBookForm.submit();
	}
}

function InputCompDeleteDataBookFrm(sysdocno, formseq, fileseq, filename) {
	var cnf = window.confirm("������ �����Ͻðڽ��ϱ�?\n�ѹ� ������ �����ʹ� ������ ���� �ʽ��ϴ�.");
	
	if(cnf) {
		location.href = "/inpCompDataBookDelete.do" + param + "&action=inpComp&sysdocno=" + sysdocno + "&formseq=" + formseq + "&fileseq=" + fileseq + "&filename=" + filename;
	}
}

var addCount = 0;
function getObj() { 
	var obj = event.srcElement ;
  while (obj.tagName != 'TD') obj = obj.parentElement;
  return obj;
}

function window::onload() {
	try {
		addCount = ((document.getElementById("datacnt").value > 0) ? (document.getElementById("datacnt").value - 1): 0);
	} catch(Exception) {}
}

function addRow() {
	var idx = getObj().parentElement.rowIndex + 2 + addCount; //�ʰ� �Է��Ѱ� �����ϴܿ� ���������� Idx�� ���ؿ´�.
	var r = tbl.insertRow(idx);
	addCount++;
	
	for (var i = 0; i < tbl.rows(0).cells.length; i++) {
		// �Էµ� Row�� ����ü�� �����´�.
		var bb = tbl.rows(0).cells(i);
		// Insert�� Row�� ����ü�� �����´�.
		var c = tbl.rows(idx).insertCell(i);

		if (i == 0) { // ù��° ���� ���鶧 �Ϸù�ȣ�� �ִ´�.
			c.align = 'center';
			c.innerHTML = addCount + 1;
		} else	{			
			c.align ='center';
			c.innerHTML = tbl.rows(1).cells(i).innerHTML;
		}
	}
}

function delRow() { 
	var idx = getObj().parentElement.rowIndex;

	if(addCount == 0) {
		alert("ù��° ���� ������ �� �����ϴ�.");
		return;
	}
	
	tbl.deleteRow(idx);
 	addCount--;
 	
 	// ������ �ʱ�ȭ �����ش�.
 	for (var i = 0;i <= addCount ; i++) {
 		tbl.rows(i+1).cells(0).innerHTML = i + 1;
 	}
}

function makeDraftDocument(sysdocno) {
	showModalPopup('/draftDocument.do?sysdocno=' + sysdocno, 800, 750, 0, 0);
}

function instantCalculation(objName, limit) {
	try {
		if ( document.getElementsByName(objName).length == 0 ) return;
	
		var colCount = document.getElementsByName(objName).length;
		var rowCount = document.getElementsByName("A").length;
		var calcCount = colCount / rowCount;
		var index = 0;
		if ( event == null ) {
			if ( limit != undefined && limit > 0) {
				colCount = colCount - (colCount / rowCount * (rowCount - limit))
				rowCount = limit;
				calcCount = colCount / rowCount;
				instantCalculation(objName, limit - 1);
			}
			index = rowCount - 1;
		} else {
			for ( var i = 0; i < colCount; i++ ) {
				if ( event.srcElement == document.getElementsByName(event.srcElement.name)[i] ) {
					index = i;
					break;
				}
			}
		}
			
		for ( var cnt = index * calcCount; cnt < index * calcCount + calcCount; cnt++ ) {
			var infoCell = document.getElementsByName(objName)[cnt];
			var cellname = infoCell.cellname;
			var formula = infoCell.title;
			var calcCell = document.getElementsByName(cellname)[cnt];
			
			for ( var i = 0; i < 52; i++ ) {
				var cell = "";
				if ( i < 26 ) {
					cell = String.fromCharCode("A".charCodeAt() + i);
				} else {
					cell = String.fromCharCode("A".charCodeAt() + i - 26) + String.fromCharCode("A".charCodeAt() + i - 26);
				}

				if ( formula.indexOf(cell) > -1 ) {
					if ( !( i < 26 && formula.substr(formula.indexOf(cell), 2) == cell + cell ) ) {
						var valueCell = document.getElementsByName(cell)[index];
						var val = valueCell.value.replace(/(^\s*)|(\s*$)/gi, "");
	
						if ( isNaN(val) == true || val == "" ) val = 0;
						if ( i < 26 ) {
							formula = formula.replace(new RegExp(cell + "{1}", "g"), "(" + val + ")");
						} else {
							formula = formula.replace(new RegExp(cell, "g"), "(" + val + ")");
						}

						try { eval(formula); break; } catch(exception) {}
					}
				}
			}
			infoCell.value = numberFormat(eval(formula));
		}
	} catch(exception) {}
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