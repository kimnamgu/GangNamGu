var target;
var stime;
var calendar;

document.writeln('<div id="minical" onmouseover="Calendar_Over()" onmouseout="Calendar_Out()" style="background: buttonface; margin:2; border: 1 solid buttonshadow; width:160; display:none; position:absolute; z-index:100">');
document.writeln('<iframe id="Cal_iFrame" width=160 height=130 src="/script/cal.html" scrolling=no frameborder=no border=1 bordercolor=red title="Ķ����" oncontextmenu="return false" ondragstart="return false" onselectstart="return false"></iframe>');
document.writeln('</div>');

function Calendar_Over() {
	window.clearTimeout(stime);
}

function Calendar_Out() {
	stime=window.setTimeout("calendar.style.display='none';", 500);
}

function Calendar_Click(e) {
	cal_Day = e.title;
	if (cal_Day.length > 6) {
		target.value = cal_Day
	}	
	calendar.style.display='none';
	extrafnc();    //��û�� �ۼ����� ���
}

function Calendar_D(obj) {
	var now = obj.value.split("-");
	target = obj;															

	var top = document.body.clientTop + GetObjectTop(obj);
	var left = document.body.clientLeft + GetObjectLeft(obj);

	calendar = document.getElementById("minical");
	calendar.style.pixelTop = top + obj.offsetHeight;
	calendar.style.pixelLeft = left;
  calendar.style.display = (calendar.style.display == "block") ? "none" : "block";

	if (now.length == 3) {											
		Cal_iFrame.Show_cal(now[0],now[1],now[2]);					
	} else {
		now = new Date();
		Cal_iFrame.Show_cal(now.getFullYear(), now.getMonth()+1, now.getDate());
	}
}

function Calendar_M(obj) {
	var now = obj.value.split("-");
	target = obj;															

	var top = document.body.clientTop + GetObjectTop(obj);
	var left = document.body.clientLeft + GetObjectLeft(obj);

	calendar = document.getElementById("minical");
	calendar.style.pixelTop = top + obj.offsetHeight;
	calendar.style.pixelLeft = left;
  calendar.style.display = (calendar.style.display == "block") ? "none" : "block";
	
	if (now.length == 2) {
		Cal_iFrame.Show_cal_M(now[0],now[1]);					
	} else {
		now = new Date();
		Cal_iFrame.Show_cal_M(now.getFullYear(), now.getMonth()+1);
	}
}

/**
	HTML ?????? ???????? ????
**/
function GetObjectTop(obj)
{
	if (obj.offsetParent == document.body)
		return obj.offsetTop;
	else
		return obj.offsetTop + GetObjectTop(obj.offsetParent);
}

function GetObjectLeft(obj)
{
	if (obj.offsetParent == document.body)
		return obj.offsetLeft;
	else
		return obj.offsetLeft + GetObjectLeft(obj.offsetParent);
}

function extrafnc(){
	if(typeof(document.forms[0]) != 'undefined'){		
		if(typeof(document.forms[0].limitfl) != 'undefined'){				
			document.forms[0].limitfl.checked = false;
		}
	}
}