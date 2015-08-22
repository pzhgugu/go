/**
 * @require common:widget/dhtmlxSuite/dhtmlx.js
 * @require common:widget/kindeditor/kindeditor.js
 */
dhtmlXForm.prototype.items.kindeditor = {
	
	kindeditor: {},
	
	uid:{},
	
	editor:{},
	
	render: function(item, data) {
		itemEditArea=item;
		var ta = (!isNaN(data.rows));
		item._type = "kindeditor";
		item._enabled = true;
		
		this.doAddLabel(item, data);
		this.doAddInput(item, data, "TEXTAREA", null, true, true, "dhxform_item_kindeditor");
		
		
		item._value = (data.value||"");
		
		var that = this;
		
		this.uid=data.uid;
		
		if(!!data.rows){
			var t = document.getElementById(this.uid); 
			t.rows=data.rows;
		}
		KindEditor.ready(function(K) {
			that.editor = K.create('textarea[name="'+item._idd+'"]', {
				uploadJson : GLOBAL.S.URL+'/ke/upload',
				allowFileManager : false
			});
		});
		return this;
		
	},
	
	// destructor for kindeditor needed
	doOnBlur: function(item, kindeditor) {
		
	},
	
	setValue: function(item, value) {
		this.editor.html(value);
		item._value = value;
	},
	
	getValue: function(item) {
		 item._value=this.editor.html();		
		 return item._value;
	},
	
	enable: function(item) {
	},
	
	disable: function(item) {
	},
	
	setReadonly: function(item, mode) {
	},
	
	destruct: function(item) {
		
		
	},
	
	setFocus: function(item) {
	}
	
};

(function(){
	for (var a in {doAddLabel:1,doAddInput:1,doUnloadNestedLists:1,setText:1,getText:1,setWidth:1,isEnabled:1})
		dhtmlXForm.prototype.items.kindeditor[a] = dhtmlXForm.prototype.items.template[a];
})();

dhtmlXForm.prototype.setFormData_kindeditor = function(name,value){
	return this.doWithItem(name, "setValue", value)
};
dhtmlXForm.prototype.getFormData_kindeditor = function(name){
	return this.doWithItem(name, "getValue")
};

