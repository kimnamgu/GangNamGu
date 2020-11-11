/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���մ������
 * ����:
 */

// �μ� �Ǵ� ����� �˻��� ���� ����� ��ü�� ���� ���� �迭����
var globalObjStorage;
var treeformation1=null;
var treeformation1State=false;
var treeformation2=null;
var treeformation2State=false;

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
	this.mainyn = '';
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
	setTree("treeformation1");
	setUrl("/formation.do");
	setType("DEPTUSER");
	setViewObjectId(treebox_formation);
	setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
	if ( treeformation1State == false) {
		getFormation(true, orggbn, orgid);
		treeformation1State = true;
	}
}

function searchFormation() {
	if ( document.forms[0].searchValue.value.trim() != "" ) {		
		setTree("treeformation1");
		setUrl("/formation.do");
		setType("DEPTUSER");
		setViewObjectId(treebox_formation);
		setSearchData(document.forms[0].searchType.value, document.forms[0].searchValue.value);
		getFormation(true, "", "");
	} else {
		setSearchData("", "");
		treeformation1State = false;
		loadFormation();
	}
}

function loadDistribute() {
	var orggbn = document.forms[0].grp_orggbn.value;
	setTree("treeformation2");
	setUrl("/formation.do");
	setType("GROUP");
	setViewObjectId(treebox_distribute);
	setSearchData("", "");
	if ( treeformation2State == false ) {
		getFormation(true, orggbn, "");
		treeformation2State = true;
	}
}

// �޺��ڽ����� ���� 
function orggbnChange(gubun){
	if ( gubun == 0 ) {
		treeformation1State = false;
		loadFormation();
	} else {
		treeformation2State = false;
		loadDistribute();
	}
}

/**
 * ������Ͽ� ���� ���(XML)�� �Ľ��Ѵ�.
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
 * ������Ͽ� ���� ������ ����Ѵ�.
 * ����1 - id : ������ ������Ͽ� ���� �μ��ڵ�
 */
function getDetailList(id){
	var params = 'grpId=' + encodeURIComponent(id);
	var detailList = new Ajax.Request('/commsubdept/grpdetailList.do', {method: 'post', postBody: params, onComplete: parseDetail});
}

/**
 * ������� ���� �� �ش��ϴºμ������ ȭ�鿡 ����Ѵ�.
 */
function writeDetail(){
	var grpDetail = document.forms[0].grpdetail;
	grpDetail.innerHTML = '';	
	if (globalObjStorage.length > 0){
		var opt;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			dept = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = dept.deptId;
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
 * ���õ� �μ���  �߰��Ѵ�.
 */
function addDept() {
	if ( SELECTED_ITEM == null ) return;

	var addDeptList = new Array();
	var level = SELECTED_ITEM.level;
	var grpgbn = SELECTED_ITEM.grpgbn;
	var mainyn = SELECTED_ITEM.main_yn;
	var deptId = SELECTED_ITEM.dept_id;
	var deptName = SELECTED_ITEM.dept_name;
	var deptFullName = SELECTED_ITEM.dept_fullname;
	
	if ( mainyn != "Y" && !document.getElementById("subdeptId").checked ) {
		alert("���μ��� �ƴմϴ�");
		return;
	}
	
	if ( deptId ) {
		if ( document.getElementById("subdeptId").checked ) {
			try {
				var url = "/researchSubDeptList.do?deptid=" + deptId;
				var subdeptCallBack = function(resultXML) {
					var xmlDoc = resultXML.responseXML;
					var resultNode = xmlDoc.getElementsByTagName('result');
					
					for ( var i = 0; i < resultNode[0].childNodes.length; i++ ) {
						dept = new deptObject();
						dept.grpGbn = 2
						dept.deptId = resultNode[0].childNodes[i].childNodes[0].text;
						dept.deptName = resultNode[0].childNodes[i].childNodes[1].text;
						dept.deptFullName = resultNode[0].childNodes[i].childNodes[2].text;
				
						addDeptList.push(dept);
						dept = null;
					}
					addList(addDeptList);
				}
				new Ajax.Request(url, {onComplete:subdeptCallBack});
			} catch (exception) {}
		} else {
			dept = new deptObject();
			dept.grpGbn = grpgbn;
			dept.deptId = deptId;
			dept.deptName = deptName;
			dept.deptFullName = deptFullName;
	
			addDeptList.push(dept);
			dept = null;
			
			addList(addDeptList);
		}
	}
}

function addDept1(){
	if ( SELECTED_ITEM == null ) return;
	
	var addDeptList = new Array();
	var level = SELECTED_ITEM.level;
	var grpgbn = SELECTED_ITEM.grpgbn;
	var mainyn = SELECTED_ITEM.main_yn;
	var deptId = SELECTED_ITEM.dept_id;
	var deptName = SELECTED_ITEM.dept_name;
	
	var findIndex;
	if ( (findIndex = deptName.indexOf(" (����)")) != -1 ) {
		deptName = deptName.substring(0, findIndex);
	}
	if ( (findIndex = deptName.toLowerCase().indexOf(" <img")) != -1 ) {
		deptName = deptName.substring(0, findIndex);
	}
	
	if (deptId){
		dept1 = new deptObject();
		dept1.grpGbn = grpgbn;
		dept1.deptId = deptId;
		dept1.deptName = deptName;
		dept1.deptFullName = deptName;
		
		addDeptList.push(dept1);
		dept1 = null;
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

function saveDistribute(type, value) {
	var action = "/grpSave.do";
	var deptList = document.forms[0].deptList;
	
	if ( type == "insert" ) {
		var groupName = document.forms[0].groupName;
		
		if ( groupName.value.trim() == "" ) return;
	
		var wrongList = "";
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
			
			if ( tmpId.substring((startIdx + 1),(startIdx + 2)) == "1" ) {
				if ( wrongList != "" ) wrongList += ", ";
				wrongList += deptList.options[i].text;
			}
		}
		
		if ( deptList.length == 0 ) {
			alert("��ϵ� ��������� �����ϴ�.");
			return;
		}
		if ( wrongList != "" ) {
			alert("��� ����� ��������� ������ �� �����ϴ�.\n\n- ������� : " + wrongList);
			return;
		}
		
		bgProcFrame.location.href = action + "?type=" + type + "&groupName=" + groupName.value +
																"&codeList=" + idList + "&codeGbnList=" + grpgbnList + "&nameList=" + nameList;
	} else if ( type = "delete" ) {
		var deptId = value;
		var deptName = event.srcElement.parentNode.innerHTML;
		
		var findIndex;
		if ( (findIndex = deptName.indexOf(" (����)")) != -1 ) {
			deptName = deptName.substring(0, findIndex);
		}
		if ( (findIndex = deptName.toLowerCase().indexOf(" <img")) != -1 ) {
			deptName = deptName.substring(0, findIndex);
		}
	
		if ( confirm("[" + deptName + "] ��������� �����Ͻðڽ��ϱ�?") == false ) {
			return;
		}
		
		for ( var i = 0; i < deptList.length; i++ ) {
			if ( deptList[i].value == deptId + "(1)" ) {
				deptList[i] = null;
			}
		}
		
		bgProcFrame.location.href = action + "?type=" + type + "&deptId=" + deptId;
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
 * ���� : �Է´���� ������ϳ��뺸��.
 * ���� : �ý��۹�����ȣ
**/
function viewDept(){

	var params = '';
	var rchno = document.forms[0].rchno;
	var rchgrpno = document.forms[0].rchgrpno;
	
	params += 'rchno=' + encodeURIComponent(rchno.value);
	params += '&rchgrpno=' + encodeURIComponent(rchgrpno.value);

	var userList = new Ajax.Request('/commrchdeptView.do', {method: 'post', postBody: params, onComplete: parseDept});
	return false;
}
/**
 * ����ڿ� ���� ���(XML)�� �Ľ��Ѵ�.
 */
function parseDept(httpXML){
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
	
	writeDept();
}
/**
 * ���մ�� ����  ����ڸ� ȭ�鿡 ����Ѵ�.
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
			opt.text = dept.deptFullName;
			opt.originText = dept.deptName;
			
			DeptList.add(opt);	
			user = null;
		}
	}else{
		DeptList.innerHTML = '';
	}
	
	globalObjStorage = null;
}

function divDisplay(n){
		var div = "";
		for(i=0; i<2; i++)
		{
			div = eval("div_display"+i+".style");

			if(i == n ){
				div.display ="block";
			}else{
				div.display ="none";
			}		
		}
	}

function validation(){
	var opener = window.dialogArguments; 
	var url = '/commrchdeptInsert.do';
	var params = '';
	var deptList = document.forms[0].deptList;
	var rchno = document.forms[0].rchno.value;
	var rchgrpno = document.forms[0].rchgrpno.value;
	
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
	
	params = 'cmd=INSERT' + '&rchno=' + rchno + '&rchgrpno=' + rchgrpno + '&idList=' + encodeURIComponent(idList.join(';')) + '&grpgbnList=' + encodeURIComponent(grpgbnList.join(';')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(';'));
	
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var opener = window.dialogArguments; 
	var xmlDoc = resXML.responseXML;
	var cmd, retCode;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	subdeptList = resultNode[0].childNodes[2].text;

	if (cmd == 'INSERT'){
		
		if (parseInt(retCode, 10) > 0){
			opener.document.forms[0].tgtdeptnm.value = subdeptList;
			self.close();
		} else if (parseInt(retCode, 10) == 0){
			alert('����������� ������ �߻��Ͽ����ϴ�.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}