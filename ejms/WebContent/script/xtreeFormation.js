/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: xtree ������
 * ����:
 */
var FORMATION_TREE = null;
var SELECTED_TREE = null;
var SELECTED_ITEM = null;
var GROUP_GBN = "1";
var DEPT_GBN = "2";
var USER_GBN = "3";
var completeEvent = function(){};
var isFirst = true;
var treeName = "";
var url;
var type;
var viewObjectId;
var selectedItem;
var searchKey;
var searchValue;

function setTree(_treeName) {
	treeName = _treeName;
	treeReset(false);
	FORMATION_TREE = eval(treeName);
}

function setUrl(_url) {
	url = _url;
}

function setType(_type) {
	type = _type;
}

function setViewObjectId(_target) {
	viewObjectId = _target;
}

function setSelectedItem(_selectedItem) {
	selectedItem = _selectedItem;
}

function setSearchData(_searchKey, _searchValue) {
	searchKey = _searchKey;
	searchValue = _searchValue;
}

function treeReset(_isFirst) {
	FORMATION_TREE = null;
	SELECTED_TREE = null;
	SELECTED_ITEM = null;
	completeEvent = function(){};
	isFirst = ( _isFirst == undefined ) ? true : _isFirst;
	webFXTreeHandler.selected = null;
}

function getFormation(isNew, orggbn, orgid) {
	try {		
		processingShow();
		
		var callBack = function(resultXML) {
			try {
				var xmlDoc = resultXML.responseXML;
				var result = xmlDoc.getElementsByTagName('result')[0];
				showXTree(result);
			} catch ( exception ) {
			} finally {
				processingHide();
			}
		}

		if ( isNew ) {
			treeReset();
		}

		if ( typeof(orggbn) == "undefined" ) orggbn = "";
		if ( typeof(orgid) == "undefined" ) orgid = "";
		if ( typeof(selectedItem) == "undefined" ) selectedItem = "";
		if ( typeof(searchKey) == "undefined" ) searchKey = "";
		if ( typeof(searchValue) == "undefined" ) searchValue = "";

		var dataUrl = url + "?type=" + type + "&orggbn=" + orggbn + "&orgid=" + orgid +
									"&searchKey=" + searchKey + "&searchValue=" + escape(encodeURIComponent(searchValue)) + "&selectedItem=" + selectedItem;
		
		new Ajax.Request(dataUrl, {onComplete:callBack});
	} catch ( exception ) {
		processingHide();
	}
}

function showXTree(resultXML) {
	if ( resultXML == null || resultXML.childNodes.length == 0 ) {
		FORMATION_TREE = new WebFXTree("�����������ϴ�");
		viewObjectId.innerHTML = FORMATION_TREE;
		FORMATION_TREE.expand();
		FORMATION_TREE.collapseChildren();
		
		if ( treeName != "" ) {
			eval(treeName + " = FORMATION_TREE");
		}
	}

	var selectedItemId = "";
	for ( var i = 0; i < resultXML.childNodes.length; i++ ) {
		var level = resultXML.childNodes[i].childNodes[0].text;
		var grpgbn = resultXML.childNodes[i].childNodes[1].text;
		var main_yn = resultXML.childNodes[i].childNodes[2].text;
		var upper_dept_id = resultXML.childNodes[i].childNodes[3].text;
		var upper_dept_name = resultXML.childNodes[i].childNodes[4].text;
		var upper_dept_fullname = resultXML.childNodes[i].childNodes[5].text;
		var dept_id = resultXML.childNodes[i].childNodes[6].text;
		var dept_name = resultXML.childNodes[i].childNodes[7].text;
		var dept_fullname = resultXML.childNodes[i].childNodes[8].text;
		
		var tree = null;

		if ( resultXML.getAttribute("forceFolderIcon") == "true" ) {
			webFXTreeConfig.fileIcon = webFXTreeConfig.folderIcon;
		} else {
			webFXTreeConfig.fileIcon = webFXTreeConfig._fileIcon;
		}

		if ( FORMATION_TREE == null && resultXML.getAttribute("rootId") == dept_id ) {
			FORMATION_TREE = new WebFXTree(dept_name,
								"javascript:fTreeClickEvent('" + dept_id + "', '" + dept_name + "', '" + dept_fullname + "', '" + upper_dept_id + "', '" + upper_dept_name + "', '" + upper_dept_fullname + "', '" + grpgbn + "')");
			FORMATION_TREE.rootId = resultXML.getAttribute("rootId");
			FORMATION_TREE.forceFolding = resultXML.getAttribute("forceFolding");
		} else if ( FORMATION_TREE == null && resultXML.getAttribute("rootId") == "GROUP" ) {
			FORMATION_TREE = new WebFXTree("�������?");
			FORMATION_TREE.add(new WebFXTreeItem(upper_dept_name, "", "", "", "",
								"level='" + level + "' grpgbn='" + grpgbn + "' main_yn='" + main_yn + "' " +
								"upper_dept_id='' upper_dept_name='' upper_dept_fullname='' dept_id='" + dept_id + "' dept_name='" + dept_name + "' dept_fullname='' " +
								"onclick=\"sClick('" + dept_id + "', '" + dept_name + "', '', '', '', '', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\" " +
								"ondblclick=\"dClick('" + dept_id + "', '" + dept_name + "', '', '', '', '', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\""));
		} else if ( grpgbn == GROUP_GBN ) {
			FORMATION_TREE.add(new WebFXTreeItem(upper_dept_name, "", "", "", "",
								"level='" + level + "' grpgbn='" + grpgbn + "' main_yn='" + main_yn + "' " +
								"upper_dept_id='' upper_dept_name='' upper_dept_fullname='' dept_id='" + dept_id + "' dept_name='" + dept_name + "' dept_fullname='' " +
								"onclick=\"sClick('" + dept_id + "', '" + dept_name + "', '', '', '', '', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\" " +
								"ondblclick=\"dClick('" + dept_id + "', '" + dept_name + "', '', '', '', '', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\""));
		} else if ( grpgbn == DEPT_GBN ) {
			if ( SELECTED_TREE == null ) {
				tree = FORMATION_TREE;
			} else {
				tree = SELECTED_TREE;
				if ( level == "1" ) continue;
			}
			var item = new WebFXTreeItem(dept_name, "", "", "", "",
								"level='" + level + "' grpgbn='" + grpgbn + "' main_yn='" + main_yn + "' " +
								"upper_dept_id='" + upper_dept_id + "' upper_dept_name='" + upper_dept_name + "' upper_dept_fullname='" + upper_dept_fullname + "' dept_id='" + dept_id + "' dept_name='" + dept_name + "' dept_fullname='" + dept_fullname + "' " +
								"onclick=\"sClick('" + dept_id + "', '" + dept_name + "', '" + dept_fullname + "', '" + upper_dept_id + "', '" + upper_dept_name + "', '" + upper_dept_fullname + "', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\" " +
								"ondblclick=\"dClick('" + dept_id + "', '" + dept_name + "', '" + dept_fullname + "', '" + upper_dept_id + "', '" + upper_dept_name + "', '" + upper_dept_fullname + "', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\"");
														
			if ( FORMATION_TREE.forceFolding == "true" ) item.add(new WebFXTreeItem(""));
			tree.add(item);
			
			if ( isFirst && resultXML.getAttribute("selectedItem") == dept_id ) {
				isFirst = false;
				selectedItemId = webFXTreeHandler.idPrefix + (webFXTreeHandler.idCounter - 2);
				completeEvent = function () {
					setTimeout(function() {
						document.getElementById(selectedItemId+"-anchor").onfocus();
						document.getElementById(selectedItemId+"-anchor").onclick();
					}, 0);
				};
			}			
		} else if ( grpgbn == USER_GBN ) {
			if ( SELECTED_TREE == null ) {
				tree = FORMATION_TREE;
			} else {
				tree = SELECTED_TREE;
				if ( level == "1" ) continue;
			}
			tree.add(new WebFXTreeItem(dept_name, "", "", "", "",
								"level='" + level + "' grpgbn='" + grpgbn + "' main_yn='" + main_yn + "' " +
								"upper_dept_id='" + upper_dept_id + "' upper_dept_name='" + upper_dept_name + "' upper_dept_fullname='" + upper_dept_fullname + "' dept_id='" + dept_id + "' dept_name='" + dept_name + "' dept_fullname='" + dept_fullname + "' " +
								"onclick=\"sClick('" + dept_id + "', '" + dept_name + "', '" + dept_fullname + "', '" + upper_dept_id + "', '" + upper_dept_name + "', '" + upper_dept_fullname + "', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\" " +
								"ondblclick=\"dClick('" + dept_id + "', '" + dept_name + "', '" + dept_fullname + "', '" + upper_dept_id + "', '" + upper_dept_name + "', '" + upper_dept_fullname + "', '" + grpgbn + "', " +
														"'" + webFXTreeHandler.idPrefix + webFXTreeHandler.idCounter + "')\""));
			
			if ( isFirst && resultXML.getAttribute("selectedItem") == dept_id ) {
				isFirst = false;
				selectedItemId = webFXTreeHandler.idPrefix + (webFXTreeHandler.idCounter - 2);
				completeEvent = function () {
					setTimeout(function() {
						document.getElementById(selectedItemId+"-anchor").onfocus();
						document.getElementById(selectedItemId+"-anchor").onclick();
					}, 0);
				};
			}
		}
	}

	if ( resultXML.childNodes.length > 0 && SELECTED_TREE == null ) {
		viewObjectId.innerHTML = FORMATION_TREE;
		FORMATION_TREE.expand();
		FORMATION_TREE.collapseChildren();
		
		if ( treeName != "" ) {
			eval(treeName + " = FORMATION_TREE");
		}
	}
	
	completeEvent();
}

function sClick(dept_id, dept_name, dept_fullname, upper_dept_id, upper_dept_name, upper_dept_fullname, grpgbn, anchorId) {
	try {
		SELECTED_TREE = FORMATION_TREE.getSelected();
		SELECTED_ITEM = document.getElementById(anchorId + "-anchor");
		completeEvent = function() {
			setTimeout(function() {
				if ( FORMATION_TREE.forceFolding == "true" ) SELECTED_TREE.childNodes[0].remove();
				if ( SELECTED_TREE.childNodes.length > 0 ) SELECTED_TREE.expand();
				SELECTED_TREE.collapseChildren();
				document.getElementById(anchorId + "-anchor").isLoad = "true";
				document.getElementById(anchorId).ondblclick = document.getElementById(anchorId)._ondblclick;
				document.getElementById(anchorId)._ondblclick = null;
			}, 0);
		}
		if ( document.getElementById(anchorId + "-anchor").isLoad != "true" && grpgbn == DEPT_GBN ) {
			document.getElementById(anchorId)._ondblclick = document.getElementById(anchorId).ondblclick
			document.getElementById(anchorId).ondblclick = null;
			getFormation(false, "", dept_id);
		}
		
		fTreeClickEvent(dept_id, dept_name, dept_fullname, upper_dept_id, upper_dept_name, upper_dept_fullname, grpgbn);
	} catch ( exception ) {}
}

function dClick(dept_id, dept_name, dept_fullname, upper_dept_id, upper_dept_name, upper_dept_fullname, grpgbn, anchorId) {
	try {
		fTreeDblClickEvent(dept_id, dept_name, dept_fullname, upper_dept_id, upper_dept_name, upper_dept_fullname, grpgbn);
	} catch ( exception ) {}
}

function fTreeClickEvent(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	try {
		fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
	} catch ( exception ) {
		try {
			parent.fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
		} catch ( exception ) {
			try {
				opener.fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
			} catch ( exception ) {
				try {
					window.dialogArguments.fTreeClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
				} catch ( exception ) {
				}
			}
		}
	}
}

function fTreeDblClickEvent(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn) {
	try {
		fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
	} catch ( exception ) {
		try {
			parent.fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
		} catch ( exception ) {
			try {
				opener.fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
			} catch ( exception ) {
				try {
					window.dialogArguments.fTreeDblClick(orgid, orgnm, orgfullnm, upper_orgid, upper_orgnm, upper_orgfullnm, grpgbn);
				} catch ( exception ) {
				}
			}
		}
	}
}