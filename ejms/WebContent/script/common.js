/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ����
 * ����:
 */
 
function getCookieVal(offset) {
	var endstr = document.cookie.indexOf (";", offset);
	if (endstr == -1) endstr = document.cookie.length;
	return unescape(document.cookie.substring(offset, endstr)); 
}

function getCookie(name) {
	var arg = name + "=";
	var alen = arg.length;
	var clen = document.cookie.length;
	var i = 0;
	while (i < clen) {
		var j = i + alen;
		if (document.cookie.substring(i, j) == arg) return getCookieVal(j);
		i = document.cookie.indexOf(" ", i) + 1;
		if (i == 0) break;
	}
	return null;
}

function setCookie (name, value) {
	var argv = setCookie.arguments;
	var argc = setCookie.arguments.length;
	var expires = (2 < argc) ? argv[2] : null; var path = (3 < argc) ? argv[3] : null;
	var domain = (4 < argc) ? argv[4] : null; var secure = (5 < argc) ? argv[5] : false;
	document.cookie = name + "=" + escape(value) +
										((expires == null) ? "" : ("; expires=" + expires.toGMTString())) +
										((path == null) ? "" : ("; path=" + path)) +
										((domain == null) ? "" : ("; domain=" + domain)) +
										((secure == true) ? "; secure" : "");
}

function delCookie( key ) {
  var expireDate = new Date();
  expireDate.setDate( expireDate.getDate() - 1 );
  document.cookie = key + "= " + "; expires=" + expireDate.toGMTString() + "; path=/";
}

function maxlength_check(obj, len) {
	if(obj.value.length > len) {
		obj.blur();
		obj.focus();
		obj.value = obj.value.substring(0, len);
	}
}

function convertSize(size) {
	if(size > 1000) {
		var size = parseInt(size / 1000).toString();
		var conSize = "";

		if(size.length <= 3) {
			conSize = size;
		} else {
			var spot = parseInt(size.length % 3);
			if(spot > 0) {
				conSize = size.substr(0, spot) + ","
			}
			for(var i = spot; i < size.length; i+=3) {
				conSize += size.substr(i, 3) + ",";
			}
			conSize = conSize.substr(0, conSize.length - 1);
		}
		document.write(conSize + " KByte");
	} else  {
		document.write("1 KByte");
	}
}

function numberFormat(num) {
	var left = "", right = "", connum = "";

	if (num < 0) left = "-";

	num = String(Math.abs(num));
	if (num.indexOf(".") != -1) {
		right = num.substring(num.indexOf("."));
		num = num.substring(0, num.indexOf("."));
	}
			
	if(num.length <= 3) {
		connum = num;
	} else {
		var spot = parseInt(num.length % 3);
		if(spot > 0) connum = num.substr(0, spot) + ",";

		for(var i = spot; i < num.length; i+=3) {
			connum += num.substr(i, 3) + ",";
		}
		connum = connum.substr(0, connum.length - 1);
	}

	return left + connum + right;
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

function MM_swapImgRestore() { //v3.0
  var i,x,a=document.MM_sr; for(i=0;a&&i<a.length&&(x=a[i])&&x.oSrc;i++) x.src=x.oSrc;
}

function MM_preloadImages() { //v3.0
  var d=document; if(d.images){ if(!d.MM_p) d.MM_p=new Array();
    var i,j=d.MM_p.length,a=MM_preloadImages.arguments; for(i=0; i<a.length; i++)
    if (a[i].indexOf("#")!=0){ d.MM_p[j]=new Image; d.MM_p[j++].src=a[i];}}
}

function MM_findObj(n, d) { //v4.01
  var p,i,x;  if(!d) d=document; if((p=n.indexOf("?"))>0&&parent.frames.length) {
    d=parent.frames[n.substring(p+1)].document; n=n.substring(0,p);}
  if(!(x=d[n])&&d.all) x=d.all[n]; for (i=0;!x&&i<d.forms.length;i++) x=d.forms[i][n];
  for(i=0;!x&&d.layers&&i<d.layers.length;i++) x=MM_findObj(n,d.layers[i].document);
  if(!x && d.getElementById) x=d.getElementById(n); return x;
}

function MM_swapImage() { //v3.0
  var i,j=0,x,a=MM_swapImage.arguments; document.MM_sr=new Array; for(i=0;i<(a.length-2);i+=3)
   if ((x=MM_findObj(a[i]))!=null){document.MM_sr[j++]=x; if(!x.oSrc) x.oSrc=x.src; x.src=a[i+2];}
}

function MM_showHideLayers() { //v3.0
  var i,p,v,obj,args=MM_showHideLayers.arguments;
  for (i=0; i<(args.length-2); i+=3) if ((obj=MM_findObj(args[i]))!=null) { v=args[i+2];
    if (obj.style) { obj=obj.style; v=(v=='show')?'visible':(v='hide')?'hidden':v; }
    obj.visibility=v; }
}

function MM_reloadPage(init) {  //reloads the window if Nav4 resized
  if (init==true) with (navigator) {if ((appName=="Netscape")&&(parseInt(appVersion)==4)) {
    document.MM_pgW=innerWidth; document.MM_pgH=innerHeight; onresize=MM_reloadPage; }}
  else if (innerWidth!=document.MM_pgW || innerHeight!=document.MM_pgH) location.reload();
}

/*
 * �־��� ���ڿ��� ������ padding ���ڷ� ä���
 */
function lpad(str, n, padding)
{
	str = String(str);
    if (str.length >= n)
        return str;
    else
    {
        var len = n - str.length;
        var pad_str = str;
        for (var i=0; i<len; i++)
            pad_str = padding + pad_str;

        return pad_str;
    }
}

/*
 * �־��� ���ڿ��� �������� padding ���ڷ� ä���
 */
function rpad(str, n, padding)
{
	str = String(str);
    if (str.length >= n)
        return str;
    else
    {
        var len = n - str.length;
        var pad_str = str;
        for (var i=0; i<len; i++)
            pad_str = pad_str + padding;

        return pad_str;
    }
}


// ���� �Է½� ��ũ��Ʈ ����
function isEnter(script) {
	if ( event.keyCode == "13" ) eval(script);
}

// ����Ű�� ������ ��Ű�� �Է��Ѱ�ó�� �Ѵ�.
function key_entertotab()
{
	if (event.keyCode == '13') event.keyCode = '9';
}

// ���ڸ� �Է°��� �ϵ��� �Ͽ���. ���� ����Ű�� ��Ű��.
function key_num()	// BackSpace, DEL, TAB, 0 .. 9
{
	key_entertotab();
	if (!
		(event.keyCode == '8' || event.keyCode == '9' || event.keyCode == '46' ||
		 event.keyCode == '39' || event.keyCode == '37' ||
		(event.keyCode >= '48' && event.keyCode <= '57') ||
		(event.keyCode >= '96' && event.keyCode <= '105'))
	   )
	{ event.returnValue = false; }
}

// ���ڿ��ٰ� ���ʽ��� '-'���� �Էµǵ��� �Ͽ���.
// ������ '-'�� �ڵ尡 189�� �����е��� '-'�ڵ�� 109��. ¥������ �Ѵ� �־���.
function key_num_minus()	// BackSpace, DEL, TAB, -, 0 .. 9
{
	key_entertotab();
	if (!
		(event.keyCode == '8' || event.keyCode == '9' || event.keyCode == '46' ||
		 event.keyCode == '109' || event.keyCode == '189' ||		// '-'�� Ű�� ���� ��.
  		 event.keyCode == '39' || event.keyCode == '37' ||
		(event.keyCode >= '48' && event.keyCode <= '57') ||
		(event.keyCode >= '96' && event.keyCode <= '105') )
	   )
	{ event.returnValue = false; }
}

function showModalPopup(actionpath, width, height, leftOffset, topOffset) {
	var left = (screen.width - width) / 2 + leftOffset;
	var top = (screen.height - height) / 2 + topOffset;
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

function showPopup(actionpath, width, height, leftOffset, topOffset) {
	var left = (screen.width - width) / 2 + leftOffset;
	var top = (screen.height - height) / 2 + topOffset;
	var option =
		"width=" + width + "," +
		"height="  + height + "," +
		"left=" + left + "," +
		"top=" + top + "," +
		"location=no, menubar=no, resizable=yes, scrollbars=yes, status=yes, " +
		"titlebar=no, toolbar=no, channelmode=no, directories=no, fullscreen=no";

	window.open(actionpath , "popup", option);
}

function showPopup2(actionpath, width, height, leftOffset, topOffset) {
	var left = (screen.width - width) / 2 + leftOffset;
	var top = (screen.height - height) / 2 + topOffset;
	var option =
		"width=" + width + "," +
		"height="  + height + "," +
		"left=" + left + "," +
		"top=" + top + "," +
		"location=no, menubar=no, resizable=yes, scrollbars=yes, status=yes, " +
		"titlebar=no, toolbar=no, channelmode=no, directories=no, fullscreen=no";

	window.open(actionpath , "popup2", option);
}

function fullSizeWindow(WIDTH, HEIGHT) {
	//<body onload="fullSizeWindow()">
	try {	
		window.dialogWidth = "0px";
		window.dialogHeight = "0px";
		try {window.resizeTo(0, 0);} catch (exception) {}
		
		var scrWidth = document.body.scrollWidth;		//����ȭ�� ����
		var scrHeight = document.body.scrollHeight;	//����ȭ�� ����
		var diaWidth = 0;
		var diaHeight = 0;
		var gapWidth = 0;
		var gapHeight = 0;
				
		//window.open()
		gapWidth = scrWidth - document.body.clientWidth;
		gapHeight = scrHeight - document.body.clientHeight;
		try {window.resizeBy(gapWidth, gapHeight);} catch (exception) {}

		gapWidth = scrWidth - document.body.clientWidth;
		gapHeight = scrHeight - document.body.clientHeight;
		try {window.resizeBy(gapWidth, gapHeight);} catch (exception) {}

		if ( WIDTH != undefined && WIDTH != -1 ) {
			if ( document.body.clientWidth+30 > parseInt(WIDTH) ) window.resizeTo(parseInt(WIDTH), document.body.clientHeight+30);
		}
		if ( HEIGHT != undefined && HEIGHT != -1 ) {
			if ( document.body.clientHeight+30 > parseInt(HEIGHT) ) window.resizeTo(document.body.clientWidth+30, parseInt(HEIGHT));
		}
		
		//alert(scrWidth + " : " + scrHeight + "\n" + document.body.scrollWidth + " : " + document.body.scrollHeight + "\n");
		
		//window.showModalDialog()
		diaWidth = String(window.dialogWidth);
		if ( diaWidth.substring(diaWidth.length - 2, diaWidth.length).toLowerCase() == "px" ) {
			gapWidth = scrWidth - document.body.clientWidth;
			diaWidth = parseInt(diaWidth.substring(0, diaWidth.length - 2), 10) + gapWidth;
			window.dialogWidth = diaWidth + "px";
		}
		diaHeight = String(window.dialogHeight);		
		if ( diaHeight.substring(diaHeight.length - 2, diaHeight.length).toLowerCase() == "px" ) {
			gapHeight = scrHeight - document.body.clientHeight;
			diaHeight = parseInt(diaHeight.substring(0, diaHeight.length - 2), 10) + gapHeight;
			window.dialogHeight = diaHeight + "px";
		}
		
		diaWidth = String(window.dialogWidth);
		if ( diaWidth.substring(diaWidth.length - 2, diaWidth.length).toLowerCase() == "px" ) {
			gapWidth = scrWidth - document.body.clientWidth;
			diaWidth = parseInt(diaWidth.substring(0, diaWidth.length - 2), 10) + gapWidth;
			window.dialogWidth = diaWidth + "px";
		}
		diaHeight = String(window.dialogHeight);		
		if ( diaHeight.substring(diaHeight.length - 2, diaHeight.length).toLowerCase() == "px" ) {
			gapHeight = scrHeight - document.body.clientHeight;
			diaHeight = parseInt(diaHeight.substring(0, diaHeight.length - 2), 10) + gapHeight;
			window.dialogHeight = diaHeight + "px";
		}

		if ( WIDTH != undefined && WIDTH != -1 ) {
			if ( diaWidth > parseInt(WIDTH)+30 ) window.dialogWidth = parseInt(WIDTH)+30 + "px";
		}
		if ( HEIGHT != undefined && HEIGHT != -1 ) {
			if ( diaHeight > parseInt(HEIGHT)+30 ) window.dialogHeight = parseInt(HEIGHT)+30 + "px";
		}
		
	} catch (exception) {}
}

//���缱 ����
//�˾�â�� ������ ����â�� ���缱�� Replace �����ش�.
function designateset(gumtoList, sunginList, retid){
		var user;
		var tmp;
		var tmp_set1  = '';
		var tmp_set2 = '';

		for (var i = 0; i < gumtoList.length; i++){
			user = gumtoList[i];
			/*
			//	userName �̸�
			//	sancYn   �Է¿Ϸᱸ��
			*/
			if(user.sancYn == '0'){
				tmp_set1	 = tmp_set1 + user.userName + ", ";
			}else{
				tmp_set1	 = tmp_set1 + user.userName + "<span class='style4'>(�����Ϸ�)</span>" + ", ";
			}
		}

		for (var i = 0; i < sunginList.length; i++){
			user = sunginList[i];
			/*
			//	userName �̸�
			//	sancYn   �Է¿Ϸᱸ��
			*/
			if(user.sancYn == '0'){
				tmp_set2	 = tmp_set2 + user.userName + ", ";
			}else{
				tmp_set2	 = tmp_set2 + user.userName + "<span class='style4'>(�����Ϸ�)</span>" + ", ";
			}
		}

    eval(retid+"1").innerHTML = tmp_set1.substring(0,tmp_set1.length-2);
    eval(retid+"2").innerHTML = tmp_set2.substring(0,tmp_set2.length-2);
}

//�Է´���� ����
function inputusrset(setList,retid){
	var user;
	var tmp_set1 = '';
	var tmp_set2 = '';
	var chrgunitnm = '';

	for (var i = 0; i < setList.length; i++){
		user = setList[i];
		/*
		//	userName �̸�
		//	sancYn   �Է¿Ϸᱸ��
		//	chrgunitNm ����������
		*/
		if(user.sancYn == '03' || user.sancYn == '04') {
			if(chrgunitnm == user.chrgunitNm) {
				tmp_set1 = tmp_set1 + ", " + user.userName;
			} else {
				if(i > 0) {
					tmp_set1 = "";
				}
				tmp_set1 = user.chrgunitNm + " : " + user.userName;
			}

			chrgunitnm = user.chrgunitNm;
		} else {
			if(tmp_set2 == '') {
				tmp_set2 = tmp_set2 + user.userName;
			} else {
				tmp_set2 = tmp_set2 + ", " + user.userName;
			}
		}
	}

	var tmp = document.getElementById(retid);

	eval(retid+"1").innerHTML = tmp_set1;
  eval(retid+"2").innerHTML = tmp_set2;
}

function filterKey(filter) {
  if(filter){
      var sKey = String.fromCharCode(event.keyCode);
      var re = new RegExp(filter);
      if(!re.test(sKey)) event.returnValue=false;

      event.keyCode = sKey.toUpperCase().charCodeAt();
  }
}

function inputFilterKey(filter) {
  if(filter){
      var sKey = String.fromCharCode(event.keyCode);
      var re = new RegExp(filter);
      if(!re.test(sKey)) event.returnValue=false;

      var srcValue = event.srcElement.value;

      if(sKey == "-" && srcValue.length > 0) {
      	 event.returnValue=false;
      }

      if((sKey == "." && srcValue.length == 0)
          || (sKey == "." && srcValue.length == 1 && srcValue == "-")
      		|| (sKey == "." && srcValue.indexOf(".") != -1)) {
      	 event.returnValue=false;
      }
  }
}

function IsNumeric(target) {	//�Է°��� ���ڸ� �Էµǵ��� üũ
	e = window.event;	// window�� �̺�Ʈ Catch

	//���ڿ� 0 ~ 9 : 48 ~ 57, Ű�е� 0 ~ 9 : 96 ~ 105 ,8 : backspace, 46 : delete -->Ű�ڵ尪�� ����, 9 : TABŰ, 110 : �Ҽ���(����Ű��Ʈ), 190 : �Ҽ���
	if(e.keyCode >= 48 && e.keyCode <= 57 || e.keyCode >= 96 && e.keyCode <= 105 || e.keyCode == 8 || e.keyCode == 46 || e.keyCode == 9 || e.keyCode == 110 || e.keyCode == 190)
	{
		return;
  }
  else {		// ���ڰ� �ƴϸ� ���â ���
		alert("���ڸ� �Է��� �� �ֽ��ϴ�.");
		target.value = "";
		e.returnValue = false;
 	}
}

function comma(target) {
	var num = target.value;

        if (num.length >= 4) {
            re = /^$|,/g;
            num = num.replace(re, "");
            fl=""
        if(isNaN(num)) {
        	alert("���ڴ� ����� �� �����ϴ�.");
        	target.value = "";
        	return 0;
        }
        if(num==0) return num
        if(num<0){
            num=num*(-1)
            fl="-"
        }
        else{
            num=num*1 //ó�� �Է°��� 0���� �����Ҷ� �̰��� �����Ѵ�.
        }
            num = new String(num)
            temp=""
            co=3
            num_len=num.length
    while (num_len>0){
        num_len=num_len-co
        if(num_len<0){co=num_len+co;num_len=0}
        temp=","+num.substr(num_len,co)+temp
        }
    target.value =  fl+temp.substr(1);
    }
}

function num_check() {
    var keyCode = event.keyCode
        if (keyCode < 48 || keyCode > 57){
            alert("���ڴ� ����� �� �����ϴ�."+"["+keyCode+"]")
            event.returnValue=false
        }
    }

// �յڷ� ������� ���ش�.
String.prototype.trim = function()
{
	return this.replace(/(^\s*)|(\s*$)/gi, "");
}

//����Ű �� ���� ������(nextItem)���� ��Ŀ�� �̵�
function enterKey_focus(nextItem){
	if(event.keyCode ==13){
		eval("document." + nextItem + ".focus()");
	}
}

//�ѱ�,����,���� ���� ���ϱ�
function getCharLen(str){
  var tcount = 0;
  var tmpStr = new String(str);
  var temp = tmpStr.length;

  var onechar;
  for ( k=0; k<temp; k++ ){
    onechar = tmpStr.charAt(k);
    if (escape(onechar).length > 4){
      tcount += 2;
    }else{
      tcount += 1;
    }
  }
  return tcount;
}

function loadJavascript(url, charset) {
    var head= document.getElementsByTagName('head')[0];
    var script= document.createElement('script');
    script.type= 'text/javascript';
    if (charset != null) {
        script.charset = "euc-kr";
    }
    var loaded = false;
    script.onreadystatechange= function () {
        if (this.readyState == 'loaded' || this.readyState == 'complete') {
            if (loaded) {
                return;
            }
            loaded = true;
        }
    }
    script.src = url;
    head.appendChild(script);
}