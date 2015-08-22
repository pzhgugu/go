/**
 * @require common:widget/utils/underscore.js
 * @require common:widget/dhtmlxSuite/dhtmlx.js
 */

dhtmlXToolbarObject.prototype.removeItem = function(itemId) {
	var t = this.getType(itemId);
	if(t=="selectEx"){
		itemId = this.idPrefix+itemId;
		var p = this.objPull[itemId];
		
		p.obj.childNodes[0].onkeydown = null;
		p.obj.removeChild(p.obj.childNodes[0]);
		
		p.obj.w = null;
		p.obj.idd = null;
		p.obj.parentNode.removeChild(p.obj);
		p.obj = null;
		
		p.id = null;
		p.type = null;
		
		p.enableItem = null;
		p.disableItem = null;
		p.isEnabled = null;
		p.showItem = null;
		p.hideItem = null;
		p.isVisible = null;
		p.setItemToolTip = null;
		p.getItemToolTip = null;
		p.setWidth = null;
		p.getWidth = null;
		p.setValue = null;
		p.getValue = null;
		p.setItemText = null;
		p.getItemText = null;
		
		t = null;
		p = null;
		this.objPull[this.idPrefix+itemId] = null;
		delete this.objPull[this.idPrefix+itemId];
	}else{
		this._removeItem(itemId);
		if (this.conf.skin == "dhx_terrace") this._improveTerraceSkin();
	}
};

dhtmlXToolbarObject.prototype.addSelectEx = function(id, pos, value, width,onchange) {
	this._addItem({id:id,type:"selectEx",value:value,width:width,onchange:onchange}, pos);
}

dhtmlXToolbarObject.prototype._selectExObject = function(that, id, data) {
	
	this.id = that.idPrefix+id;
	this.obj = document.createElement("DIV");
	this.obj.className = "dhx_toolbar_btn dhxtoolbar_btn_def";
	this.obj.style.display = (data.hidden!=null?"none":"");
	this.obj.idd = String(id);
	this.obj.w = (data.width!=null?data.width:100);
	this.obj.title = (data.title!=null?data.title:"");
	
	//设置下拉选择的值
	var selectHtml = "<select style='width:"+this.obj.w+"px;margin-top: 2px;' onchange="+data.onchange+" >";
	var value = data.value;
	if(_.isObject(value)){
		_.each(value,function (v, k, value) { 			
			selectHtml+="<option value ='"+v.value+"'>"+v.text+"</option>";
		}); 
	}
	selectHtml+="</select>";
	this.obj.innerHTML = selectHtml;  
	
	var th = that;
	var self = this;
	this.obj.childNodes[0].onkeydown = function(e) {
		e = e||event;
		if (e.keyCode == 13) { th.callEvent("onEnter", [self.obj.idd, this.value]); }
	}
	// add
	that.base.appendChild(this.obj);

	this.enableItem = function() {
		this.obj.childNodes[0].disabled = false;
	}
	this.disableItem = function() {
		this.obj.childNodes[0].disabled = true;
	}
	this.isEnabled = function() {
		return (!this.obj.childNodes[0].disabled);
	}
	this.showItem = function() {
		this.obj.style.display = "";
	}
	this.hideItem = function() {
		this.obj.style.display = "none";
	}
	this.isVisible = function() {
		return (this.obj.style.display != "none");
	}
	this.setValue = function(value) {
		this.obj.childNodes[0].value = value;
	}
	this.getValue = function() {
		return this.obj.childNodes[0].value;
	}
	this.setWidth = function(width) {
		this.obj.w = width;
		this.obj.childNodes[0].style.width = this.obj.w+"px";
	}
	this.getWidth = function() {
		return this.obj.w;
	}
	this.setItemToolTip = function(tip) {
		this.obj.title = tip;
	}
	this.getItemToolTip = function() {
		return this.obj.title;
	}
	this.getInput = function() {
		return this.obj.firstChild;
	}
	
	if (typeof(data.enabled) != "undefined" && window.dhx4.s2b(data.enabled) == false) {
		this.disableItem();
	}
	
	//
	return this;
}

