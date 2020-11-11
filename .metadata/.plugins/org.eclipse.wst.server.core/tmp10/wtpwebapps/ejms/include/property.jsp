<%--
 * 작성일: 2009.09.09
 * 작성자: 대리 서동찬
 * 모듈명: 양식작성 사용자지정형 양식속성지정창
 * 설명:
--%>
<%@ page contentType="text/html;charset=euc-kr" %>
<%@ taglib uri="/WEB-INF/tlds/struts-bean.tld" prefix="bean" %>
<%@ taglib uri="/WEB-INF/tlds/struts-html.tld" prefix="html" %>
<%@ taglib uri="/WEB-INF/tlds/struts-logic.tld" prefix="logic" %>
<%
	response.setDateHeader("Expires", 0);
	response.setHeader("Pragma", "No-cache");
	response.setHeader("Cache-Control", "no-cache");
%>
<script>
String.prototype.trim = function() {
	return this.replace(/(^\s*)|(\s*$)/gi, "");
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

var targetObj = null;

function keyCheck() {
	if(event.keyCode == 13) {
		applyProperty();
	} else if(event.keyCode == 27) {
		hideProperty();
	}
}

function applyProperty() {
	var dataName = document.getElementById("dataName");
	var dataType = document.getElementById("dataType");
	var dataType_attrib;
	var tempAttrib;

	for(var i = 0; i < dataType.length; i++) {
		if(dataType[dataType.selectedIndex].text == dataType[i].text) {
			dataType_attrib = document.getElementById("dataType" + dataType[i].value + "_attrib");
		}
	}
	if(dataType_attrib != null) {
		tempAttrib = dataType_attrib[dataType_attrib.selectedIndex].value;
	} else {
		tempAttrib = "";
	}
	
	targetObj.value = dataType[dataType.selectedIndex].text + ":" + dataName.value.trim();
	targetObj.attrib = dataName.value.trim() + ":" + dataType[dataType.selectedIndex].value + ":" + tempAttrib;

	hideProperty();
	change();
}

function showProperty() {
	var eventObj = event.srcElement;
	var propertyObj = document.getElementById("property");
	var dataName = document.getElementById("dataName");
	var dataType = document.getElementById("dataType");
	var attribValue;
	var dataType_attrib;
	var xPos = getAbsoluteLeft(eventObj) + eventObj.clientWidth;
	var yPos = getAbsoluteTop(eventObj);

	hideAttrib();

	if(targetObj != null) {
		targetObj.style.backgroundColor = "";
	}

	if(eventObj.attrib != undefined) {
		attribValue = eventObj.attrib.split(":");
	} else {
		attribValue = "::".split(":");
	}
	dataName.value = attribValue[0];
	for(var i = 0; i < dataType.length; i++) {
		if(dataType[i].value == attribValue[1]) {
			dataType_attrib = document.getElementById("dataType" + attribValue[1] + "_attrib");
			dataType[i].selected = true;
			dataType.onchange();
		}
	}
	if(dataType_attrib != null) {
		for(var i = 0; i < dataType_attrib.length; i++) {
			if(dataType_attrib[i].value == attribValue[2]) {
				dataType_attrib[i].selected = true;
			}
		}
	}

	propertyObj.style.posLeft = xPos;
	propertyObj.style.posTop = yPos;
	propertyObj.style.display = "block";

	eventObj.style.backgroundColor = "rgb(222,222,222)";
	targetObj = eventObj;
}

function hideProperty() {
	var propertyObj = document.getElementById("property");
	
	hideAttrib();

	propertyObj.style.display = "none";

	targetObj.style.backgroundColor = "";
	targetObj = null;
}

function showAttrib() {
	var eventObj = event.srcElement;

	if(eventObj.name != "") {
		eventObj = document.getElementById("dataType");
	}

	for(var i = 0; i < eventObj.length; i++) {
		var attribObj = document.getElementById("dataType" + eventObj[i].value);
		if(attribObj != null) {
			if(i == eventObj.selectedIndex) {
				attribObj.style.display = "block";
			} else {
				attribObj.style.display = "none";
			}
		}
	}
}

function hideAttrib() {
	var dataType= document.getElementById("dataType");
	for(var i = 0; i < dataType.length; i++) {
		var attribObj = document.getElementById("dataType" + dataType[i].value);
		if(attribObj != null) {
			attribObj.style.display = "none";
		}
	}
}

var tempValue = null;
function submitProcCancel() {
	try {
		var propertyDataForm = document.forms[0];
		var dataList = propertyDataForm.elements;
		
		if(tempValue != null && tempValue.length > 0) {
			for(var i = 0; i < dataList.length; i++) {
				if(dataList[i].type == "text" && dataList[i].name == "cellatt") {
					dataList[i].value = tempValue[i];
				}
			}
		}
		tempValue = null;
		
		return true;
	} catch(exception) {
		alert(exception);
		return false;
	}
}
function submitProc() {
	try {
		var propertyDataForm = document.forms[0];
		var dataList = propertyDataForm.elements;
		
		tempValue = new Array(dataList.length);
	
		for(var i = 0; i < dataList.length; i++) {
			for(var j = 0; j < dataList.length; j++) {
				if(i != j && dataList[i].type == "text" && dataList[i].name == "cellatt"
						&& dataList[j].type == "text" && dataList[j].name == "cellatt") {
					if(dataList[i].attrib.split(":")[0] == dataList[j].attrib.split(":")[0]) {
						processingHide();
						alert("이름이 [" + dataList[i].attrib.split(":")[0] +
									"] 인 입력란이 두개 이상 있습니다.\n다른 이름으로 지정해 주시기 바랍니다.");
						return false;
					}
				}
			}
		}
		
		for(var i = 0; i < dataList.length; i++) {
			if(dataList[i].type == "text" && dataList[i].name == "cellatt") {
				tempValue[i] = dataList[i].value;
			}
		}
		
		for(var i = 0; i < dataList.length; i++) {
			if(dataList[i].type == "text" && dataList[i].name == "cellatt") {
				dataList[i].value = dataList[i].attrib;
			}
		}
		
		return true;
	} catch(exception) {
		alert(exception);
		return false;
	}
}
</script>

<table id="property" bordercolor="rgb(128,128,128)" border="1" style="border-collapse:collapse; border:2 ridge white; font-size:12; position:absolute; display:none">
	<tr bgcolor="rgb(236,233,216)">
		<td width="50">&nbsp;이 름</td>
		<td width="150" colspan="2"><input id="dataName" type="text" style="font-size:12; width:100%" onkeypress="keyCheck()"></td>
	</tr>
	<tr bgcolor="rgb(236,233,216)">
		<td>&nbsp;형 태</td>
		<td colspan="2"><select id="dataType" style="font-size:12; width:100%" onchange="showAttrib()" onkeypress="keyCheck()" onclick="this.onchange()">
			<option value="2">문자</option>
			<option value="6">날짜</option>
			<option value="7">HTML</option>
		</select>	</td>
	</tr>
	<tr id="dataType2" bgcolor="rgb(207,200,157)" style="display:none">
		<td colspan="2">&nbsp;&nbsp;&nbsp;입력행수</td>
		<td><select id="dataType2_attrib" style="font-size:12; width:100%" onkeypress="keyCheck()">
			<option value="1">단문(1줄)</option>
			<option value="5">장문</option>
		</select>	</td>
	</tr>
	<tr id="dataType7" bgcolor="rgb(207,200,157)" style="display:none">
		<td colspan="2">&nbsp;&nbsp;&nbsp;편집기높이</td>
		<td><select id="dataType7_attrib" style="font-size:12; width:100%" onkeypress="keyCheck()">
			<option value="5">5줄</option>
			<option value="10">10줄</option>
			<option value="15">15줄</option>
			<option value="20">20줄</option>
		</select>	</td>
	</tr>
	<tr bgcolor="rgb(255,255,255)">
		<td colspan="3" align="right"><input type="button" value="확인" style="font-size:12" onclick="applyProperty()"><input type="button" value="취소" style="font-size:12" onclick="hideProperty()"></td>
	</tr>
</table>