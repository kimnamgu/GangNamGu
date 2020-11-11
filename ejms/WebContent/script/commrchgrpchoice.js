/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: �Է´��������
 * ����:
 */

// �μ� �Ǵ� ����� �˻��� ���� ����� ��ü�� ���� ���� �迭����
var globalObjStorage;
/**
 * �μ��� ���� �����´�.
 *
 */
 
 /**
 * ����� ������ ���� ��ü
 */
function userObject(){
	this.userId = '';
	this.userName = '';
	this.className = '';
	this.deptName = '';
	this.sancYn = '';
	this.chrgunitNm ='';
	this.tgtcode="";
	this.tgtname="";
	return this;
}

/**
 * ���� : ����� ��ü�� Ư�� �Ӽ��� �����͸� �����Ѵ�.
 * ����1 obj - �Ӽ������� ������ ����� ��ü
 * ����2 node - �Ӽ������� ���� �ִ� XML Node��ü
 */
userObject.prototype.setData = function(obj, node){
	// obj�� �Ӽ�(prop)�� ���� ������ ���鼭 
	for (prop in obj){
		if (prop == node.attributes[0].value){
			obj[prop] = node.text;
			break;
		}
	}
	return obj;
};
 
function loadUser(){
	var treeUser = document.getElementById('treebox_user');
	treeUser.innerHTML = '';
	tree=new dhtmlxtreeObject("treebox_user","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	
	var param = "";
	var opener = window.dialogArguments;
	if ( opener.document.forms[0].range[1].checked ) {
		param = "?range=1";
	} else {
		param = "?range=2";
	}
	  
	//tree.enableCheckBoxes(true);
	tree.setOnDblClickHandler(addUser);
	tree.loadXML("/commrchgrpchoiceList.do" + param);
}

/**
 * ���õ� ����ڸ� ���ұ׷쿡 �߰��Ѵ�.
 */
function addUser(){
	var addUserList = new Array();
	
	// ����ڸ� ���� �ߴ��� ���� Ȯ��
	if (!chkUserStatus()){
		return;
	}	
	
	var userId = tree.getSelectedItemId();
	var level = tree.getLevel(userId);
	var userName = tree.getItemText(userId);
	
	if (userId){
		user = new userObject();
		user.userId = userId;
		user.userName = userName;
		
		addUserList.push(user);
		user = null;
	}
		
	addList(addUserList);
	
	return;
}

/**
 * ���� : ����_����� ��Ͽ� ���õ� ����� ����� �߰��Ѵ�.
 * ����1 userObject��ü�� ��� �ִ� Array
 */
function addList(userList){
	var opt;
	var user;
	
	for (var i = 0; i < userList.length ; i++){
		user = userList[i];
		// ������ �������� �ʴ� ����ڿ� ���ؼ��� �߰�
		if (!existUserChk(user.userId)){
		
			opt = document.createElement('OPTION');
			opt.value = user.userId;
			opt.text = user.userName ;
			
			document.frm.userList.add(opt);
			
			user = null;
			opt = null;
		}
	}
}

/**
 * ���� : ����ڰ� �����ϴ��� ���θ� üũ
 * ���� : �����ID, ��
 */
function existUserChk(userId){
	var startIdx = 0;
	var localId = "";
	var UserList= document.frm.userList.options;
	
	if (UserList.length > 0){
		for (var i = 0 ; i < UserList.length ; i++){
			localId = UserList[i].value;
			startIdx = localId.indexOf('(');	
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length)== userId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * ���õ� ����ڸ� �����Ѵ�.
 */
function delUser(){
	var userList= document.frm.userList;
	var UserCnt =userList.options.length;
	
	for (var i = (UserCnt - 1) ; i > -1 ; i--){
		if (userList.options[i].selected){
			userList.remove(i);
		}
	}
	
	setOrderButton();
}

/**
 * ���� : ����_����� �߰�/���� �� ����� ���õǾ� �ִ��� ����üũ
 */
function chkUserStatus(){
	var selectedUserId = tree.getSelectedItemId();
	
	if (selectedUserId == ''){
		alert('������ ����� �����ϴ�.');
		return false;
	}
	return true;
}

/**
 * ���� : �Է´���� ������ϳ��뺸��.
 * ���� : �ý��۹�����ȣ
 */
function viewUser(){

	var params = '';
	var rchgrpno = document.frm.rchgrpno;
	
	params += 'rchgrpno=' + encodeURIComponent(rchgrpno.value);

	var userList = new Ajax.Request('/commrchgrpchoiceView.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
	return false;
}
/**
 * ����ڿ� ���� ���(XML)�� �Ľ��Ѵ�.
 */
function parseGumToUser(httpXML){
	var xmlDoc = httpXML.responseXML;	
	var root = xmlDoc.documentElement;
	var dataList = root.childNodes;

	globalObjStorage = new Array();

	for (var i = 0 ; i < dataList.length; i++){
		
		var user = new userObject();
		for (var j = 0 ; j < dataList[i].childNodes.length; j++){
			user.setData(user, dataList[i].childNodes[j]);
		}
		
		globalObjStorage[i] = user;
		user = null;
	}
	
	writeGumToUser();
}
/**
 * ������ ����  ����ڸ� ȭ�鿡 ����Ѵ�.
 */
function writeGumToUser(){
	var userList = document.frm.userList;
	userList.innerHTML = '';	
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
			  
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			opt = document.createElement('OPTION');
			opt.value = user.tgtcode;
			opt.text = user.tgtname;
			userList.add(opt);	
			user = null;
		}
	}else{
		userList.innerHTML = '';
	}
	
	globalObjStorage = null;
}
/**
 * ����� ����Ѵ�. 
 */
function validation(){
	var opener = window.dialogArguments; 
	var url = '/commrchgrpchoiceInsert.do';
	var params = '';
	var approvalUser = document.frm.userList;
	var rchgrpno = document.frm.rchgrpno.value;
	
	var idList = new Array();
	var nameList = new Array();
	
	var tmpId = '';
	var tmpName = '';
	
	for (var i = 0 ; i < approvalUser.options.length; i++){
		user = new userObject();
		tmpId 	= approvalUser.options[i].value;
		tmpName = approvalUser.options[i].text;
		
		idList.push(tmpId);
		if ( i == 0 ) nameList.push(tmpName);
		
		user = null;
	}

	opener.document.forms[0].rchnolist.value = idList;
	if ( approvalUser.options.length > 1 ) {
		opener.document.forms[0].rchname.value = "[" + nameList + "] �������� �� " + (approvalUser.options.length - 1) + "��";
	} else {
		opener.document.forms[0].rchname.value = nameList;
	}

	params = 'cmd=INSERT' + '&rchgrpno=' + rchgrpno + '&idList=' + encodeURIComponent(idList.join(':'));
			
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var cmd, retCode, msg;
	var opener = window.dialogArguments;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	msg = resultNode[0].childNodes[2].text;
	
	if (cmd == 'INSERT'){
		if (parseInt(retCode, 10) > 0){
			if(msg != "") {
				window.dialogArguments.alert(msg);
			}
			self.close();
		} else if (parseInt(retCode, 10) == 0){
			opener.document.forms[0].rchnolist.value = "";
			opener.document.forms[0].rchname.value = "";
			alert('�������� ���� �� ������ �߻��Ͽ����ϴ�.');
		}
	}
}

function preview1() {
	var rchno = tree.getSelectedItemId();
	if ( rchno != "" ) {
		showPopup("/researchPreview.do?rchno=" + rchno, 700, 680, 0, 0);
	}
}

function preview2() {
	var rchlist = document.frm.userList;
	if ( rchlist.selectedIndex != -1 ) {
		var rchno = rchlist.options[rchlist.selectedIndex].value;
		showPopup("/researchPreview.do?rchno=" + rchno, 700, 680, 0, 0);
	}
}

function setOrderButtonChk() {
	var ordChk = document.getElementById("ordChk");
	var rchlist = document.frm.userList;
	
	if ( rchlist.selectedIndex == -1 ) {
		orderButton.style.display = "none";
		return;
	}
	
	if ( ordChk.checked ) {
		orderButton.style.display = "block";
	} else {
		orderButton.style.display = "none";
	}
}

function setOrderButton() {
	var rchlist = document.frm.userList;
	var xPos = event.clientX;
	var yPos = event.clientY;
	
	if ( rchlist.selectedIndex == -1 ) {
		orderButton.style.display = "none";
		return;
	}
	
	orderButton.style.left = xPos - 15;
	orderButton.style.top = yPos - 15;
	setOrderButtonChk();
}

function orderChange(gbn) {
	var rchlist = document.frm.userList;
	var rIdx = rchlist.selectedIndex;
	var text = rchlist[rIdx].text;
	var value = rchlist[rIdx].value;
	
	if ( gbn == 1 ) {
		if ( rIdx == 0 ) return;
		rchlist[rIdx].selected = false;
		rchlist[rIdx].text = rchlist[rIdx - 1].text;
		rchlist[rIdx].value = rchlist[rIdx - 1].value;
		rchlist[rIdx - 1].selected = true;
		rchlist[rIdx - 1].text = text;
		rchlist[rIdx - 1].value = value;
	} else if ( gbn == 2 ) {
		if ( rIdx == rchlist.length - 1 ) return;
		rchlist[rIdx].selected = false;
		rchlist[rIdx].text = rchlist[rIdx + 1].text;
		rchlist[rIdx].value = rchlist[rIdx + 1].value;
		rchlist[rIdx + 1].selected = true;
		rchlist[rIdx + 1].text = text;
		rchlist[rIdx + 1].value = value;
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}