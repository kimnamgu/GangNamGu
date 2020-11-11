/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���缱����
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
	this.gubun = '';
	this.sancYn = '';
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
 
function loadDept(){
	var treeUser = document.getElementById('treebox_user');
	treeUser.innerHTML = '';
	tree=new dhtmlxtreeObject("treebox_user","100%","100%",0);
	tree.setImagePath("/dhtmlxtree/imgs/");
	  
	//tree.enableCheckBoxes(true);
	tree.setOnDblClickHandler(addUser);
	tree.loadXML("/commapproval/designateList.do");
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
	
	if (userId){
		user = new userObject();
		user.userId = userId;
		user.userName = userName;
		user.className = className;
		user.deptName = deptName;
		
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
	var gubun;
	var flag;

	if(document.frm.gubun[0].checked == true){
		 gubun= document.frm.gumTo;
		 flag = '1';
	}else{
		 gubun= document.frm.sungIn;
		 flag = '2';
	}
	
	var opt;
	var user;
	
	if(flag == '1'){
		if(stateChk()){
			return; 
		}
	}
	
	for (var i = 0; i < userList.length ; i++){
		user = userList[i];
		// ������ �������� �ʴ� ����ڿ� ���ؼ��� �߰�
		if (!existUserChk(user.userId, flag)){
		
			opt = document.createElement('OPTION');
			opt.value = user.userId + '(0)';
			opt.text = user.userName;
			
			gubun.add(opt);
			
			user = null;
			opt = null;
		}
	}
}

/**
 * ���� : ����ڰ� �����ϴ��� ���θ� üũ
 * ���� : �����ID, ��
 */
function existUserChk(userId,flag){
	var UserList;
	var localId;
	var startIdx = 0;
	if(flag == '1'){
		UserList = document.frm.gumTo.options;
	}else{
		UserList = document.frm.sungIn.options;
	}

	if (UserList.length > 0){
		for (var i = 0 ; i < UserList.length ; i++){
			localId = UserList[i].value;
			startIdx = localId.indexOf('(');
			if (localId.substring(0,(startIdx>-1)? startIdx: localId.length) == userId){
				return true;
				break;
			}
		}
	}
	return false;
}

/**
 * ���� : ����ڰ� ���� ���� �� ���� ���¿� ���� ������ �߰� ����  ���θ� üũ
 * ���� : �����ID, ��
 */
function stateChk(){
	var type = document.frm.type.value;
	var state = document.frm.state.value;

	if(type == '1'){
		if(state == '03'){
			alert("���δ�� �����Դϴ� �����ڸ� �߰� �� ���� �Ҽ� �����ϴ�.");
			return true;
		}
	}else{
		if(state == '04'){
			alert("���δ�� �����Դϴ� �����ڸ� �߰� �� ���� �Ҽ� �����ϴ�.");
			return true;
		}
	}
	return false;
}


/**
 * ���õ� ����ڸ� �����Ѵ�.
 */
function delUser(userList){
	var userList;
	var startIdx;
	var endIdx;

	//����Ŭ������ �����ϱ� ���Ͽ� ���� (080206 �����)
	if (userList == null) {
		if(document.frm.gubun[0].checked == true){
			if(stateChk()){
				return;
			}else{
				userList = document.frm.gumTo;
			}
		}else{
			userList = document.frm.sungIn;
		}
	}
		
	var UserCnt =userList.options.length;
	for (var i = (UserCnt - 1) ; i > -1 ; i--){
		if (userList.options[i].selected){
			var selectId = userList.options[i].value;
			startIdx = selectId.indexOf('('); 
			endIdx = selectId.indexOf(')');

			var yn = selectId.substring((startIdx + 1),endIdx);

			if(yn == '1'){
				alert('���簡�Ϸ�Ǿ����ϴ� ������ �� �����ϴ�.');
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
 * ���� : �������� ������ϳ��뺸��.
 * ���� : �ý��۹�����ȣ, ����(���պμ�����:1, ����μ�����:2)
 */
function viewUser(){

	var params = '';
	var sysdocno = document.frm.sysdocno;
	var type = document.frm.type;
	
	params = 'sysdocno=' + encodeURIComponent(sysdocno.value)+ '&type=' + encodeURIComponent(type.value); 

	var userList = new Ajax.Request('/commapproval/designateView.do', {method: 'post', postBody: params, onComplete: parseGumToUser});
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
	var gumToUser = document.frm.gumTo;
	var sungInUser = document.frm.sungIn;
	gumToUser.innerHTML = '';	
	sungInUser.innerHTML = '';
	
	if (globalObjStorage.length > 0){
		var opt;
		var opt1;
		for (var i = 0 ; i < globalObjStorage.length ; i++){
			user = globalObjStorage[i];
			
			if(user.gubun == '1'){
				opt = document.createElement('OPTION');
				opt.value = user.userId + '('+ user.sancYn + ')';
				opt.text = user.userName + '(' + user.className + ')';
				gumToUser.add(opt);	
				user = null;
			}else{
				opt1 = document.createElement('OPTION');
				opt1.value = user.userId + '('+ user.sancYn + ')';
				opt1.text = user.userName + '(' + user.className + ')';
				sungInUser.add(opt1);	
				user = null;
			}
		}
	
	}else{
		gumToUser.innerHTML = '';
		sungInUser.innerHTML = '';
	}
	
	globalObjStorage = null;
}

function validation(){
	var opener = window.dialogArguments; 
	var url = '/commapproval/designateInsert.do';
	var params = '';
	var gumToUser = document.frm.gumTo;
	var sungInUser = document.frm.sungIn;
	var isMaking = document.frm.isMaking.value;
	var sysdocno = document.frm.sysdocno.value;
	var type = document.frm.type.value;
	var retid = document.frm.retid.value;
	
	var idList = new Array();
	var nameList = new Array();
	var nameList1 = new Array();
	var gubunList = new Array();
	
	var gumtoList = new Array();
	var sunginList = new Array();
	
	var tmpId = '';
	var tmpName = '';
	var startIdx = 0;
	
	if(gumToUser.options.length > 0){
		if(sungInUser.options.length<=0){
			alert("���������ڸ� �Է��Ͽ��� �մϴ�.")
			return;
		}
	}
	
	for (var i = 0 ; i < gumToUser.options.length; i++){
		user = new userObject();
		tmpId = gumToUser.options[i].value;
		tmpName = gumToUser.options[i].text;
		
		startIdx = tmpId.indexOf('(');

		user.sancYn = tmpId.substring((startIdx + 1),(startIdx +2));
		if(tmpId.substring((startIdx + 1),(startIdx +2)) == '0'){
			idList.push(tmpId.substring(0,(startIdx>-1)? startIdx: tmpId.length));
		
			startIdx = tmpName.indexOf('(');
			nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
			gubunList.push('1');
		}
		startIdx = tmpName.indexOf('(');
		user.userName = tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length);

		gumtoList.push(user);
		user = null;
		startIdx = 0;
	}
	if(retid ==""){
		opener.document.forms[0].sancusrnm1.value = nameList ;
	}
	
	for (var i = 0 ; i < sungInUser.options.length; i++){
		user1 = new userObject();
		tmpId = sungInUser.options[i].value;
		tmpName = sungInUser.options[i].text;
		
		startIdx = tmpId.indexOf('(');
		user1.sancYn = tmpId.substring((startIdx + 1),(startIdx +2));
		if(tmpId.substring((startIdx + 1),(startIdx +2)) == '0'){
			idList.push(tmpId.substring(0,(startIdx>-1)? startIdx: tmpId.length));
		
			startIdx = tmpName.indexOf('(');
			nameList.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
			nameList1.push(tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length));
			gubunList.push('2');
		}
		startIdx = tmpName.indexOf('(');
		user1.userName = tmpName.substring(0, (startIdx > -1)? startIdx : tmpName.length);
		
		sunginList.push(user1);
		user1 = null;
		startIdx = 0;
	}
	
	
	if(retid == ""){
		opener.document.forms[0].sancusrnm2.value= nameList1;
	}else{
		opener.designateset(gumtoList, sunginList, retid);
	}

	
	params = 'cmd=INSERT' + '&isMaking=' + isMaking + '&sysdocno='+ sysdocno + '&type=' + type + '&idList=' + encodeURIComponent(idList.join(':')) 
			+ '&nameList=' + encodeURIComponent(nameList.join(':')) + '&gubunList=' + encodeURIComponent(gubunList.join(':'));
	
	var update = new Ajax.Request(url, {method: 'post', postBody: params, onComplete: callBack, onFailure: errMsg});
}

function callBack(resXML){
	var isMaking = document.frm.isMaking.value;
	var xmlDoc = resXML.responseXML;
	var cmd, retCode, msg;
	
	var resultNode = xmlDoc.getElementsByTagName('result');

	cmd = resultNode[0].childNodes[0].text;
	retCode = resultNode[0].childNodes[1].text;
	msg = resultNode[0].childNodes[2].text;

	if (cmd == 'INSERT') {
		if (retCode == "true") {
			if(msg != "") {
				window.dialogArguments.alert(msg);
			}
			self.close();
		} else {
			alert('���缱���� ����� ������ �߻��Ͽ����ϴ�.');
		}
	}

	if(isMaking != 1) {
		window.dialogArguments.location.reload();
	}	
}

function errMsg(e){
	alert(e);
}

function cancel(){
	self.close();
}