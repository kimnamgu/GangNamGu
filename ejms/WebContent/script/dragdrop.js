/**
 * �ۼ���: 2009.09.09
 * �ۼ���: �븮 ������
 * ����: ���缱���� �巡�׾ص��
 * ����:
 */

var d;
function drag(objSource) {
	this.select = objSource;
}

function drag.prototype.drop(objDest) {
	if (!this.dragStart) return;
	this.dest = objDest;
	
	var o = this.option.cloneNode(true);
	this.dest.appendChild(o);
	this.select.removeChild(this.option);
	//alert('done!');
}

function drag.prototype.setIndex() {
	var i = this.select.selectedIndex;
	
	//i returns -1 if no option is "truly" selected
	window.status="selectedIndex = "+i;
	if (i==-1) return;
	
	this.option = this.select.options[i];
	this.dragStart=true;
}

function drag.prototype.dump() {
	var dump = '';
	for (var i in this) {
	if (typeof this[i] != 'function')
	dump += i + ' : ' + this[i] + ' : ' + this[i].nodeName + ' : ' + typeof this[i] + '\n';
	}
	//alert(dump);
}
