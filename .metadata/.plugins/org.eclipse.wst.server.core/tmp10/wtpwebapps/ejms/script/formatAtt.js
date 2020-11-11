/**
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식속성지정
 * 설명:
 */
var eventCellAtt = "";
var changed = false;
var subMenuPosLeft = 30;
var subMenuPosTop = 5;

function change() {
	changed = true;
}

function click_preview(actionpath) {
	hideMainMenu(eventCellAtt, document.getElementById("mainmenu"));
	var selectObj = document.getElementsByTagName("select");
	for(var i = 0; i < selectObj.length; i++) {
		selectObj[i].disabled = false;
	}
	
	var oldTarget = document.forms[0].target;
	document.forms[0].target = "bgProcFrame";
	document.forms[0].action = actionpath;
	processingShow();
	document.forms[0].submit();
	document.forms[0].target = oldTarget;
}

function click_popup(actionpath, width, height) {
	var left = (screen.width - width) / 2 + 30;
	var top = (screen.height - height) / 2 + 30;
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
		alert("미리보기 기능을 사용하시려면 팝업 차단 사용을 꺼주시거나\n" +
					"팝업 허용 사이트로 등록해주시기 바랍니다.\n\n" +
					"제어판 → 인터넷옵션 → 개인정보 → 팝업차단사용(체크해제)\n" +
					"또는 설정(버튼클릭) → 허용할 웹 사이트 주소 입력 후 추가");
		try {processingHide();} catch(ex) {}
		try {parent.processingHide();} catch(ex) {}
	}
}

function click_prev(actionpath) {
	hideMainMenu(eventCellAtt, document.getElementById("mainmenu"));
	if(changed == true) {
		if(confirm("작성된 양식을 버리시겠습니까?") == false) {
			return;
		}
	}

	document.forms[0].action = actionpath;
	processingShow();
	document.forms[0].submit();
}

function click_comp(actionpath) {
	processingHide();
	var formtitle = document.forms[0].formtitle;
	
	if(formtitle.value.trim() == "") {
		formtitle.focus();
		alert("제목을 입력하세요.");
		return;
	}
	
	if(confirm("완료하시겠습니까?") == false) {
		return;
	}
	
	document.forms[0].action = actionpath;
	processingShow();
	document.forms[0].submit();
}

function click_cancel() {
	if(confirm("작업을 취소하시겠습니까?") == true)
		window.close();
}

function hideMainMenu(component, divobj) {		
	component.disabled = false;

	divobj.style.display = "none";
}

function showSubMenu(menuobj, divobj) {
	menuobj.style.background = "FFFFCC";

	if(divobj != "") {
		divobj.style.display = "block";
	}
}

function hideSubMenu(menuobj, divobj) {			
	menuobj.style.background = "FFFFFF";

	if(divobj != "") {
		divobj.style.display = "none";
	}
}

function showMainMenu(component, divobj) {
	if(divobj.style.display == "block") {
		divobj.style.display = "none";
		component.disabled = true;
		setTimeout("eventCellAtt.disabled = false;", 0);
		return;
	}

	var cellatt = document.forms[0].cellatt;
	for(var i = 0; i < cellatt.length; i++) {
		cellatt[i].disabled = false;
	}
	component.disabled = true;
	setTimeout("eventCellAtt.disabled = false;", 0);
	
	if(event.type == "mousedown") {
		resetAtt();
		
		var characterRadio = document.forms[0].characterRadio;
		var character = document.forms[0].character;
		var formula = document.forms[0].formula;
		var userList = document.forms[0].userList;
		var adminList = document.forms[0].adminList;
		var listType = document.forms[0].listType;
		var optVal = component[component.selectedIndex].value.split("|");
		
		if(optVal[0] == "02") {
			if(optVal[1] == "1") {
				characterRadio[0].checked = true;
			} else {
				characterRadio[1].checked = true;
			}
			character.value = optVal[1];
		} else if(optVal[0] == "03") {
			formula.value = optVal[1];
		} else if(optVal[0] == "04") {
			if(component.currListType == undefined) {
				component.currListType = listType[0];
				component.currList = userList;
			}
			var selectedListType = component.currListType;
			var selectedList = component.currList;
			
			selectedListType.checked = true;
			selectedListType.onclick();

			for(var i = 0; i < selectedList.length; i++) {
				if(selectedList[i].value.split(",")[0] == optVal[1]) {	
					selectedList[i].selected = true;
					selectedList.onclick();	
					selectedListType.checked = true;
					selectedListType.onclick();
					break;
				} else if (i + 1 == selectedList.length) {
					if ( selectedListType == listType[1] ) break;
					i = -1;
					selectedListType = component.currListType = listType[1];
					selectedList = component.currList = adminList;
				}
			}
		}
	}
	
	var fLayer = document.getElementById("formatLayer");
	var xpos = getAbsoluteLeft(component) - getAbsoluteLeft(fLayer);
	var ypos = getAbsoluteTop(component) - getAbsoluteTop(fLayer) + component.offsetHeight;

	divobj.style.posLeft = xpos;
	divobj.style.posTop = ypos;
	divobj.style.display = "block";
	
	fLayer.onscroll = function() {
		if ( this.width == undefined || this.width < this.scrollWidth )
			this.width = this.scrollWidth;
		if ( this.height == undefined || this.height < this.scrollHeight )
			this.height = this.scrollHeight;
			
		var tblLayer = document.getElementById("tbl");
		tblLayer.style.marginRight = this.width - tblLayer.offsetWidth;
		tblLayer.style.marginBottom = this.height - tblLayer.offsetHeight;
	}
}

function setCellatt(attName, attCode, attValue) {
  var optionName;
  var optionValue;

  if(attCode == '01') {
		optionName = attName;
		optionValue = attCode + "|"
  } else if(attCode == '02') {
  	if(attValue.trim() == "1") {
  		optionName = attName + ":" + "단문(1줄)";
  	} else {
	  	optionName = attName + ":" + "장문";
  	}
		attValue = parseInt(attValue);
		optionValue = attCode + "|" + attValue;
  } else if(attCode == '03') {
  	if(attValue.trim() == "") {
  		alert("수식을 입력하세요");
  		return;
  	}
  	attValue = attValue.toUpperCase();
  	optionName = attName + ":" + attValue;
		optionValue = attCode + "|" + attValue;
  } else if(attCode == '04') {
  	var optVal = attValue.split(",");
  
  	optionName = attName + ":" + optVal[1];
		optionValue = attCode + "|" + optVal[0];
	}
	
  for(var i = eventCellAtt.length; i > 0 ; i--) {
		eventCellAtt.options[eventCellAtt.length - 1] = null;
	}
  
  eventCellAtt.options[0] = new Option(optionName, optionValue);
  
  hideSubMenu(document.getElementById("mainmenu01"), '');
  hideSubMenu(document.getElementById("mainmenu02"), document.getElementById("submenu02"));
  hideSubMenu(document.getElementById("mainmenu03"), document.getElementById("submenu03"));
  hideSubMenu(document.getElementById("mainmenu04"), document.getElementById("submenu04"));
  hideMainMenu(eventCellAtt, document.getElementById("mainmenu"));
   
  resetAtt();
  
  change();
}
	
function masterListSelect(masterList, detailList, masterCode, detailCode, viewType) {
	if ( masterList.selectedIndex == -1 ) return;
		
	var optVal = masterList[masterList.selectedIndex].value.split(",");
	for(var i = detailList.length; i > 0 ; i--) {
		detailList.options[detailList.length - 1] = null;
	}
	
	for(var i = 2; i < optVal.length; i += 2) {
		if ( optVal[i + 1] == "" ) continue;
		detailList.options[i / 2 - 1] = new Option(optVal[i + 1], optVal[i]);
		detailList.options[i / 2 - 1].title = optVal[i + 1];
	}
	
	if ( viewType == "show" ) {
		masterCode.value = optVal[1];
		masterCode.code = optVal[0];
		masterCode.change = "select";
	} else if ( viewType == "hide" ) {
		masterCode.value = "";
		masterCode.code = "";
		masterCode.change = "";
	}
	detailCode.value = "";
	detailCode.code = "";
	detailCode.change = "";
	
	showMessage();
}

function detailListSelect(masterList, detailList, masterCode, detailCode) {
	if ( masterList.selectedIndex == -1 ) return;
	if ( detailList.selectedIndex == -1 ) return;
	
	var mstoptVal = masterList[masterList.selectedIndex].value.split(",");	
	var optVal = detailList[detailList.selectedIndex].value;
	var optTxt = detailList[detailList.selectedIndex].text;
	
	if ( masterList.style.display == "block" ) {
		masterCode.value = mstoptVal[1];
		masterCode.code = mstoptVal[0];
		masterCode.change = "select";
		detailCode.value = optTxt;
		detailCode.code = optVal;
		detailCode.change = "select";
	}
	
	showMessage();
}

function setCellattList(attName, attCode, attValue) {
	var userList = document.forms[0].userList;
	var adminList = document.forms[0].adminList;
	var listType = document.forms[0].listType;
	
	if ( userList.style.display == "block" ) {
		eventCellAtt.currListType = listType[0];
		eventCellAtt.currList = userList;
	} else if ( adminList.style.display == "block" ) {
		eventCellAtt.currListType = listType[1];
		eventCellAtt.currList = adminList;
	}
	
	var selectedList = eventCellAtt.currList;
	
	if ( selectedList != undefined && selectedList.selectedIndex != -1 ) {
		setCellatt(attName, attCode, eval(attValue));
	} else {
		alert("목록을 선택하세요.");
	}
}

function showListTab(obj) {
	obj.checked = true;
	obj.onclick();
}

function showList(list1, list2, dlist, actview1, actview2, tabName, tabImage1, tabImage2) {
	list1.style.display = "block";
	list2.style.display = "none";
	actview1.style.display = "block";
	actview2.style.display = "none";
	eval("document." + tabName)[0].src = tabImage1;
	eval("document." + tabName)[1].src = tabImage2;
	
	if ( list1.selectedIndex == -1 ) {
		for(var i = dlist.length; i > 0 ; i--) {
			dlist.options[dlist.length - 1] = null;
		}
	}
	
	list1.onclick();
}

function showEdit(editModeObj, userActViewInputBoxObj, addButtonObj, modButtonObj, delButtonObj, okButtonObj, messageObj) {
	if ( editModeObj.checked == true ) {
		userActViewInputBoxObj.style.display = "block";
		okButtonObj.style.visibility = "hidden";
		messageObj.style.display = "block";
	} else if ( editModeObj.checked == false ) {
		userActViewInputBoxObj.style.display = "none";
		okButtonObj.style.visibility = "visible";
		messageObj.style.display = "none";
	}
	addButtonObj.style.visibility = "hidden";
	modButtonObj.style.visibility = "hidden";
	delButtonObj.style.visibility = "hidden";
	
	document.forms[0].masterCode.onkeyup();
	
	showMessage();
}

function changeMode(userListObj, detailListObj, masterCodeObj, detailCodeObj, codeObjGbn) {
	var findName = function(gbn, list, code, name) {
		var type = "insert";
		if ( gbn == "master" && ( list.selectedIndex == -1 && name.trim() == "" ) ) return "";
		if ( gbn == "master" && ( list.selectedIndex != -1 && name.trim() == "" ) ) return "";
		if ( gbn == "detail" && name.trim() == "" ) return "";
		for ( var i = 0; i < list.length; i++ ) {
			var listCode = list[i].value.split(",")[0];
			if      ( listCode == code && list[i].text == name ) {type = "select"; break;}
			else if ( listCode == code && list[i].text != name ) {if ( type != "duplicate" ) type = "update";}
			else if ( listCode != code && list[i].text == name ) {type = "duplicate";}
		}
		return type;
	}
	
	if ( codeObjGbn == "master" ) {
		detailCodeObj.value = "";
		detailCodeObj.change = "";
	} else if ( codeObjGbn == "detail" ) {
		if ( userListObj.selectedIndex == -1 || masterCodeObj.value.trim() == "" ) {
			detailCodeObj.value = "";
			detailCodeObj.code = "";
			detailCodeObj.change = "";
			return;
		}
	
		var optVal = userListObj[userListObj.selectedIndex].value.split(",");	
		masterCodeObj.value = optVal[1];
		masterCodeObj.code = optVal[0];
		masterCodeObj.change = "select";
	}

	masterCodeObj.change = findName(codeObjGbn, userListObj, masterCodeObj.code, masterCodeObj.value);
	detailCodeObj.change = findName(codeObjGbn, detailListObj, detailCodeObj.code, detailCodeObj.value);

	showMessage();
}

function showMessage() {
	var mlst = document.forms[0].userList;
	var dlst = document.forms[0].detailList;
	var mval = document.forms[0].masterCode.value;
	var dval = document.forms[0].detailCode.value;
	var mcd = document.forms[0].masterCode.code;
	var dcd = document.forms[0].detailCode.code;
	var mchg = document.forms[0].masterCode.change;
	var dchg = document.forms[0].detailCode.change;
	var addButton = document.getElementById("addButton");
	var modButton = document.getElementById("modButton");
	var delButton = document.getElementById("delButton");
	var okButton = document.getElementById("okButton");
	
	var findCode = function(list, code) {
		for ( var i = 0; i < list.length; i++ ) {
			var optVal = list[i].value.split(",");
			if ( optVal[0] == code ) return optVal[1];
		}
		return "";
	}
	var findDetailCode = function(list, code) {
		for ( var i = 0; i < list.length; i++ ) {
			if ( list[i].value == code ) return list[i].text;
		}
		return "";
	}
	var hideAllButton = function() {
		if ( document.forms[0].editMode.checked == false ) return;
		addButton.style.visibility = "hidden";
		modButton.style.visibility = "hidden";
		delButton.style.visibility = "hidden";
		okButton.style.visibility = "hidden";
	}
	var showInsertButton = function() {
		if ( document.forms[0].editMode.checked == false ) return;
		hideAllButton();
		addButton.style.visibility = "visible";
	}
	var showUpdateButton = function() {
		if ( document.forms[0].editMode.checked == false ) return;
		hideAllButton();
		addButton.style.visibility = "visible";
		modButton.style.visibility = "visible";
	}
	var showDeleteButton = function() {
		if ( document.forms[0].editMode.checked == false ) return;
		hideAllButton();
		delButton.style.visibility = "visible";
	}
	
	var gbn = "";
	var msg = "";

	if ( mchg == "select" ) {
		showDeleteButton();
		gbn = "master"
		msg = "☞ 목록명에 " + findCode(mlst, mcd) + " 을(를) 삭제할 수 있습니다.";
		if ( dchg == "select" ) {
			showDeleteButton();
			gbn = "detail"
			msg = "☞ 목록내용에 " + findDetailCode(dlst, dcd) + " 을(를) 삭제할 수 있습니다.";
		} else if ( dchg == "update" ) {
			showUpdateButton();
			gbn = "detail"
			msg = "☞ 목록내용에 " + dval + " 을(를) 추가하거나  " + 
					findDetailCode(dlst, dcd) + " 을(를) " + dval + " 로 변경할 수 있습니다.";
		} else if ( dchg == "duplicate" ) {
			hideAllButton();
			gbn = "detail"
			msg = "☞ 목록내용에 " + dval + " 이(가) 이미 등록되어 있습니다.";
		} else if ( dchg == "insert" ) {
			showInsertButton();
			gbn = "detail"
			msg = "☞ 목록내용에 " + dval + " 을(를) 추가할 수 있습니다.";
		} else if ( dchg == "" ) {
			gbn = "master"
			msg = "☞ 목록내용에서 선택 또는  새로 입력하거나 목록명에 " + findCode(mlst, mcd) + " 을(를) 삭제할 수 있습니다.";
		}
	} else if ( mchg == "update" ) {
		showUpdateButton();
		gbn = "master"
		msg = "☞ 목록명에 " + mval + " 을(를) 추가하거나  " + 
					findCode(mlst, mcd) + " 을(를) " + mval + " 로 변경할 수 있습니다.";
	} else if ( mchg == "duplicate" ) {
		hideAllButton();
		gbn = "master"
		msg = "☞ 목록명에 " + mval + " 이(가) 이미 등록되어 있습니다.";
	} else if ( mchg == "insert" ) {
		showInsertButton();
		gbn = "master"
		msg = "☞ 목록명에 " + mval + " 을(를) 추가할 수 있습니다.";
	} else if ( mchg == "" ) {
		hideAllButton();
		gbn = "master"
		msg = "☞ 목록명에서 선택 또는 새로 입력하십시오.";
	}
	
	//디버그용 메세지
	//msg = mchg + " | " + mcd + " == " + dchg + " | " + dcd;
	message.innerHTML = msg;
	
	return gbn;
}

function saveAttList(action, type, masterCodeObj, detailCodeObj) {
	var gbn = showMessage();
	if ( gbn == "" ) return;
	
	if ( type == "delete" ) {
		var msg;
		if ( gbn == "master") msg = "목록명에서 " + masterCodeObj.value.trim();
		else if ( gbn == "detail") msg = "목록내용에서 " + detailCodeObj.value.trim();
		
		if ( confirm(msg + " 을(를) 삭제 하시겠습니까?\n\n" +
					"취합된자료에 영향은 없지만 자료입력에 영향이 있을 수 있습니다.") == false ) return;
	}
	
	var mcd = masterCodeObj.code.trim();
	var mnm = masterCodeObj.value.trim();
	var dcd = detailCodeObj.code.trim();
	var dnm = detailCodeObj.value.trim();

	action += "?gbn=" + gbn.trim() + "&type=" + type.trim() + 
						"&mcd=" + mcd + "&mnm=" + mnm + "&dcd=" + dcd + "&dnm=" + dnm;
	
	bgProcFrame.location.href = action;
}

function setList(gbn, type, result) {
	if ( result.split(",")[0] == undefined && type != "delete") return;

	var ulist = document.forms[0].userList;
	var dlist = document.forms[0].detailList;
	var mcode = document.forms[0].masterCode;
	var dcode = document.forms[0].detailCode;
	var resultMcode, resultMname;
	var resultDcode, resultDname;
	var pos, idx;
	
	var findCodeIndex = function(list, code) {
		for ( var i = 0; i < list.length; i++ ) {
			if ( list[i].value.split(",")[0] == code ) return i;
		}
		return -1;
	}
	var findDetailCodeIndex = function(list, code) {
		for ( var i = 0; i < list.length; i++ ) {
			if ( list[i].value == code ) return i;
		}
		return -1;
	}

	if ( gbn == "master" ) {
		if ( type == "insert" ) {
			resultMname = ( result.split(",")[1] != undefined ) ? result.split(",")[1] : "";
			pos = ulist.length;
			ulist.options[pos] = new Option(resultMname, result);
			ulist.options[pos].title = resultMname;
			ulist[pos].selected = true;
			ulist.onclick();
		} else if ( type == "update" ) {
			resultMcode = result.split(",")[0];
			resultMname = ( result.split(",")[1] != undefined ) ? result.split(",")[1] : "";
			if ( ( pos = findCodeIndex(ulist, resultMcode) ) == -1 ) return;
			ulist.options[pos].value = result;
			ulist.options[pos].text = resultMname;
			ulist.options[pos].title = resultMname;
			ulist[pos].selected = true;
			ulist.onclick();
		} else if ( type == "delete" ) {
			resultMcode = result.split(",")[0];
			if ( ( pos = findCodeIndex(ulist, resultMcode) ) == -1 ) return;
			ulist.options[pos] = null;
			mcode.value = "";
			mcode.code = "";
			mcode.change = "";
			mcode.onkeyup();
			
			if ( ulist.selectedIndex == -1 ) {
				for ( var i = dlist.length; i > 0 ; i-- ) {
					dlist.options[dlist.length - 1] = null;
				}
			}
		}
	} else if ( gbn == "detail" ) {
		if ( type == "insert" ) {
			var reslutList = result.split(",");
			resultDcode = reslutList[reslutList.length - 2];
			resultDname = reslutList[reslutList.length - 1];
			pos = dlist.length;
			dlist.options[pos] = new Option(resultDname, resultDcode);
			dlist.options[pos].title = resultDname;
			dlist[pos].selected = true;
			dlist.onclick();
			ulist.options[ulist.selectedIndex].value = result;
		} else if ( type == "update" ) {
			resultDcode = dcode.code;
			resultDname = dcode.value;
			if ( ( pos = findDetailCodeIndex(dlist, resultDcode) ) == -1 ) return;
			dlist.options[pos].value = resultDcode;
			dlist.options[pos].text = resultDname;
			dlist.options[pos].title = resultDname;
			dlist[pos].selected = true;
			dlist.onclick();
			ulist.options[ulist.selectedIndex].value = result;
		} else if ( type == "delete" ) {
			resultDcode = dcode.code;
			if ( ( pos = findDetailCodeIndex(dlist, resultDcode) ) == -1 ) return;
			dlist.options[pos] = null;
			dcode.value = "";
			dcode.code = "";
			dcode.change = "";
			dcode.onkeyup();
			ulist.options[ulist.selectedIndex].value = result;
		}
	}
}

function resetAtt() {
	document.forms[0].character.value = "1";
	document.forms[0].formula.value = "";
	document.forms[0].userList.selectedIndex = -1;
	document.forms[0].adminList.selectedIndex = -1;
	document.forms[0].listType[0].checked = true;
	document.forms[0].listType[0].onclick();
	document.forms[0].editMode.checked = false;
	document.forms[0].editMode.onclick();
	document.forms[0].masterCode.value = "";
	document.forms[0].masterCode.code = "";
	document.forms[0].masterCode.change = "";
	document.forms[0].detailCode.value = "";
	document.forms[0].detailCode.code = "";
	document.forms[0].detailCode.change = "";
	message.innerHTML = "&nbsp;"

	var detailList = document.forms[0].detailList;
	for(var i = detailList.length; i > 0 ; i--) {
		detailList.options[detailList.length - 1] = null;
	}
}

function getAbsoluteLeft(htmlObject) {
	var xPos = htmlObject.offsetLeft;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		xPos += temp.offsetLeft;
		temp = temp.offsetParent;
	}
	return xPos;
}

function getAbsoluteTop(htmlObject) {
	var yPos = htmlObject.offsetTop;
	var temp = htmlObject.offsetParent;
	while (temp != null) {
		yPos += temp.offsetTop;
		temp = temp.offsetParent;
	}
	return yPos;
}

function filterKey(filter) {
  if(filter){
      var sKey = String.fromCharCode(event.keyCode);
      var re = new RegExp(filter);
      if(!re.test(sKey)) event.returnValue=false;

      event.keyCode = sKey.toUpperCase().charCodeAt();
  }
}

function maxlength_check(obj, len) {
	if(obj.value.length > len) {
		obj.blur();
		obj.focus();
		obj.value = obj.value.substring(0, len);
	}
}

String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/gi, "");
}