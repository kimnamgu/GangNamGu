/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ������ ������ϰ���
 * ����:
 */

// �μ� �Ǵ� ����� �˻��� ���� ����� ��ü�� ���� ���� �迭����
var globalObjStorage;
var treeformationState=false;
/**
 * �μ��� ���� �����´�.
 *
 */
 
 /**
 * ����� ������ ���� ��ü
 */
function deptObject(){
	this.deptId = '';
	this.deptName = '';
	this.deptFullName = '';
	this.grpGbn = '';
	this.mainFl = '';
	return this;
}

/**
 * ���� : ����� ��ü�� Ư�� �Ӽ��� �����͸� �����Ѵ�.
 * ����1 obj - �Ӽ������� ������ ����� ��ü
 * ����2 node - �Ӽ������� ���� �ִ� XML Node��ü
 */
deptObject.prototype.setData = function(obj, node){
	// obj�� �Ӽ�(prop)�� ���� ������ ���鼭 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};

function loadFormation(orgid) {
	var orggbn = document.forms[0].sch_orggbn.value;
	setUrl("/formation.do");
	setType("DEPTUSER");
	setViewObjectId(treebox_formation);
	setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
	if ( treeformationState == false) {
		getFormation(true, orggbn, orgid);
		treeformationState = true;
	}
}

function searchFormation() {
	if ( document.forms[0].searchValue.value.trim() != "" ) {		
		setUrl("/formation.do");
		setType("DEPTUSER");
		setViewObjectId(treebox_formation);
		setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
		getFormation(true, "", "");
	} else {
		setSearchData("", "");
		treeformationState = false;
		loadFormation();
	}
}

// �޺��ڽ����� ���� 
function orggbnChange(){
	treeformationState = false;
	loadFormation();
}

/**
 * ������ ���Ҹ� ���� ������ ����Ѵ�.
 * ����1 - id : ������ ���Ҹ� ���� ����ID 
 */
function getDetailList(sch_grplist){
	var params = 'sch_grplist=' + encodeURIComponent(sch_grplist) + '&mode=sub_l';
	var detailList = new Ajax.Request('/grpPopup.do', {method: 'post', postBody: params, onComplete: parseDetail});
}

/**
 * ���һ���ڿ� ���� ���(XML)�� �Ľ��Ѵ�.
 */
function parseDetail(httpXML){
	var xmlDoc = httpXML.responseXML;
	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var dept = new deptObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			dept.setData(dept, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = dept;
		dept = null;
	}
	writeDetail();
}
/**
 * ���������� ���ξ���(���ұ׷�) ���� �� �ش��ϴ� ���� ����ڸ� ȭ�鿡 ����Ѵ�.
 */
function writeDetail(){
	var grpDetail = document.forms[0].deptList;
	grpDetail.innerHTML = '';	
	if (globalObjStorage.length > 0){
		var opt;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + "(" + dept.grpGbn + ")";
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;
			
			grpDetail.add(opt);
			dept = null;
		}
	}else{
		grpDetail.innerHTML = '';
	}
	
	globalObjStorage = null;
}

/**
 * ������ ����  ����ڸ� ȭ�鿡 ����Ѵ�.
 */
function writeDept(){
	var DeptList = document.forms[0].deptList;
	DeptList.innerHTML = '';	
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + '('+ dept.grpGbn + ')';
			opt.text = dept.deptName;
			DeptList.add(opt);	
			user = null;
		}
	}else{
		DeptList.innerHTML = '';
	}
	
	globalObjStorage = null;
}

/**
 * ���õ� �μ���  �߰��Ѵ�.
 */
function addDept(){
	if ( SELECTED_ITEM == null ) return;
	
	var level = SELECTED_ITEM.level;
	var grpgbn = SELECTED_ITEM.grpgbn;
	var mainyn = SELECTED_ITEM.main_yn;
	var deptId = SELECTED_ITEM.dept_id;
	var deptName = SELECTED_ITEM.dept_name;
	var deptFullName = SELECTED_ITEM.dept_fullname;
	
	// �μ��� ���� �ߴ��� ���� Ȯ��
	if (!chkDeptStatus()){
		return;
	}	

	// ��������� ���� �ߴ��� ���� Ȯ��
	if (document.forms[0].sch_grplist.value == ""){
		alert("��������� �����Ͽ� �ֽʽÿ�.");
		return;
	}	
	
	if ( mainyn != "Y" ) {
		alert("���մ���� �ƴմϴ�");
		return;
	}
	
	var addDeptList = new Array();
	if (deptId){
		dept = new deptObject();
		dept.grpGbn = grpgbn;
		dept.deptId = deptId;
		dept.deptName = deptName;
		dept.deptFullName = deptFullName;

		addDeptList.push(dept);
		dept = null;
	}
		
	addList(addDeptList);
}

/**
 * ���� : ���õ� �μ�  �߰��Ѵ�.
 * ����1 userObject��ü�� ��� �ִ� Array
 */
function addList(deptList){
	var opt;
	var dept;
	
	for (var i = 0; i < deptList.length ; i++){
		dept = deptList[i];
		// ������ �������� �ʴ� �μ��� ���ؼ��� �߰�
		if (!existDeptChk(dept.deptId)){
		
			opt = document.createElement('OPTION');
			opt.value = dept.deptId + '('+ dept.grpGbn + ')';
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;

			document.forms[0].deptList.add(opt);
			dept = null;
			opt = null;
		}
	}
}

function fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if ( grpgbn == 1 ) {
		getDetailList(orgid);
	}
}

function fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	if ( grpgbn == 1 ) {
		addDept1();
	} else if ( grpgbn == 2 ) {
		if ( SELECTED_ITEM.main_yn == "Y" ) {
			addDept();
			event.cancelBubble = true;
		}
	} else if ( grpgbn == 3 ) {
		if ( SELECTED_ITEM.main_yn == "Y" ) {
			addDept();
			event.cancelBubble = true;
		}
	}
}

/**
 * ���� : �����ϴ��� ���θ� üũ
 * ���� : �μ��ڵ�
 */
function existDeptChk(deptId){
	var DeptList= document.forms[0].deptList.options;
	
	if (DeptList.length > 0){
		for (var i = 0 ; i < DeptList.length ; i++){
		
				localId = DeptList[i].value;
			startIdx = localId.indexOf('(');
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length) == deptId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * ���õ� �μ��� �����Ѵ�.
 */
function delDept(){
	var DeptList= document.forms[0].deptList;
	var DeptCnt =DeptList.options.length;
	for (var i = (DeptCnt - 1) ; i > -1 ; i--){
		if (DeptList.options[i].selected){
			DeptList.remove(i);
		}
	}
}

/**
 * ���� :�μ� �߰�/���� �� �μ��� ���õǾ� �ִ��� ����üũ
 */
function chkDeptStatus(){
	if ( SELECTED_ITEM == null ) return;
	var selectedDeptId = SELECTED_ITEM.dept_id;
	
	if (selectedDeptId == ''){
		alert('�����Ͻ� �μ��� �����ϴ�.');
		return false;
	}
	return true;
}

function validation(){
	var url = '/grpDB.do';
	var params = '';
	var deptList = document.forms[0].deptList;
	var sch_grplist = document.forms[0].sch_grplist.value;
	
	var idList = new Array();
	var grpgbnList = new Array();
	var nameList = new Array();
	
	var tmpId = '';
	var startIdx = 0;
	for (var i = 0 ; i < deptList.options.length; i++){
		tmpId = deptList.options[i].value ;
		startIdx = tmpId.indexOf('(');
		
		idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
		grpgbnList.push(tmpId.substring((startIdx + 1),(startIdx + 2)));
		nameList.push(deptList.options[i].originText);
	}
	
	params = 'cmd=INSERT' + '&mode=sub_i&sch_grplist='+ encodeURIComponent(sch_grplist) + '&idList=' + encodeURIComponent(idList.join(';')) + 
					 '&grpgbnList=' + encodeURIComponent(grpgbnList.join(';')) + '&nameList=' + encodeURIComponent(nameList.join(';'));
					 
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var opener = window.dialogArguments; 
	var xmlDoc = resXML.responseXML;
	var cmd, retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	
	if (cmd == 'INSERT'){
		
		if (parseInt(retCode, 10) > 0){
			if ( location.search.indexOf("userModifyMode=y") == -1 ) {
				var sch_grplist = document.forms[0].sch_grplist.value;
				var ppos = opener.parentdiv.scrollTop;	 //�θ�â��ũ��(����)
				var cpos = opener.childdiv.scrollTop;	 //�ڽ�â��ũ��(����)
				opener.location.href = '/grpList.do?grplistcd='+sch_grplist+'&posi='+ppos+'&cposi='+cpos;
			}
			self.close();
		} else if (parseInt(retCode, 10) == 0){
			alert('������� ���κμ����� ������ �߻��Ͽ����ϴ�.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}