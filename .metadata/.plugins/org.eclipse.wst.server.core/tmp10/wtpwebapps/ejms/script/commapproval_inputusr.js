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
	  
	//tree.enableCheckBoxes(true);
	tree.setOnDblClickHandler(addUser);
	tree.loadXML("/commapproval/inputusrList.do");
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
	var className = tree.getUserData(userId, 'className');
	var deptName = tree.getUserData(userId, 'deptName');
	var sancYn = tree.getUserData(userId, 'sancYn');
	var chrgunitNm = tree.getUserData(userId, 'chrgunitNm');
	
	if (userId){
		user = new userObject();
		user.userId = userId;
		user.userName = userName;
		user.className = className;
		user.deptName = deptName;
		user.chrgunitNm = chrgunitNm;

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
			opt.value = user.userId + '(' + '01'+ '|'+ user.chrgunitNm + ')';
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
			var selectId = userList.options[i].value;
			startIdx = selectId.indexOf('('); 
			endIdx = selectId.indexOf('|');

			var yn = selectId.substring((startIdx + 1),endIdx);	
			if(yn != '01'){
				alert('�Էµ� ������ �ֽ��ϴ� ������ �� �����ϴ�.');
			}else{
				userList.remove(i);
			}	
		}
	}
}

/**
 * ���� : ����_����� �߰�/���� �� ����� ���õǾ� �ִ��� ����üũ
 */
function chkUserStatus(){
	var selectedUserId = tree.getSelectedItemId();
	
	if (selectedUserId == ''){
		alert('�����Ͻ� ����� �����ϴ�.');
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
	var sysdocno = document.frm.sysdocno;
	
	params += 'sysdocno=' + encodeURIComponent(sysdocno.value);

	var userList = new Ajax.Request('/commapproval/inputusrView.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
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
			opt.value = user.userId + '('+user.sancYn + '|'+ user.chrgunitNm + ')';
			opt.text = user.userName + '(' + user.className + ')';
			userList.add(opt);	
			user = null;
		}
	}else{
		userList.innerHTML = '';
	}
	
	globalObjStorage = null;
	
	var user_id = document.forms[0].user_id.value;
	var user_name = document.forms[0].user_name.value;
	var dept_code = document.forms[0].dept_code.value;
	var class_name = document.forms[0].class_name.value;
	var origindeptcd = document.forms[0].origindeptcd.value;
	
	if ( dept_code == origindeptcd ) {
		
		if ( !existUserChk(user_id) && confirm("������ �Է´���ڷ� �����Ͻðڽ��ϱ�?") ) {
			var addUserList = new Array();
			var user = new userObject();
			user.userId = user_id;
			user.userName = user_name + "(" + class_name + ")";
			addUserList.push(user);
			addList(addUserList);
			//validation();
		}
	}	
}
/**
 * �Է�  ����ڸ� ����Ѵ�. 
 */
function validation(){
	var opener = window.dialogArguments; 
	var url = '/commapproval/inputusrInsert.do';
	var params = '';
	var retid = document.frm.retid.value;
	var approvalUser = document.frm.userList;
	var sysdocno = document.frm.sysdocno.value;
	
	var idList = new Array();
	var nameList = new Array();
	var nameList1 = new Array();
	var setList = new Array();
	
	var tmpId = '';
	var tmpName = '';
	var startIdx = 0;
	var startIdx1 = 0;
	
	if(approvalUser.length  < 1) {
		alert("�Է´���ڰ� ������ ���� �ʾҽ��ϴ�.\n�Է´���ڸ� �����Ͻʽÿ�.");
		return;
	}
	
	for (var i = 0 ; i < approvalUser.options.length; i++){
		user = new userObject();
		tmpId 	= approvalUser.options[i].value;
		tmpName = approvalUser.options[i].text;
		
	  startIdx = tmpId.indexOf('(');
	  startIdx1 = tmpId.indexOf('|');
	  user.sancYn = tmpId.substring((startIdx + 1), startIdx1);
	  user.chrgunitNm = tmpId.substring((startIdx1 + 1 ), (tmpId.length - 1));
		if(user.sancYn == '01'){
			idList.push(tmpId.substring(0, (startIdx > -1)? startIdx : tmpId.length));
		}
		startIdx = tmpName.indexOf('(');
		user.userName = tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length);
		if(user.sancYn == '01'){
			nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
		}
		
		nameList1.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
		setList.push(user);
		user = null;
		
	}
	
	if(retid == ""){
		opener.document.forms[0].appntusrnm.value = nameList1;
	}else{
		opener.inputusrset(setList, retid);
	}

	params = 'cmd=INSERT' + '&sysdocno=' + sysdocno + '&idList=' + encodeURIComponent(idList.join(':')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(':'));
	var insert = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}


function callBack(resXML){
	var xmlDoc = resXML.responseXML;
	var cmd, retCode, msg;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	msg = resultNode[0].childNodes[2].text;
	
	if (cmd == 'INSERT'){
		if (parseInt(retCode, 10) > 0){
			//alert('�Է´���ڰ�  ������ �Ϸ�Ǿ����ϴ�.');
			if(msg != "") {
				window.dialogArguments.alert(msg);
			}
			self.close();
			window.dialogArguments.location.reload();
		} else if (parseInt(retCode, 10) == 0){
			alert('�Է´���� ����� ������ �߻��Ͽ����ϴ�.');
		}
	}
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}