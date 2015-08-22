
dhtmlXForm.prototype.items.editArea = {
	
	editArea: {},
	
	uid:{},
	
	itemEditArea:{},
	
	changeCallback : function(id){
		itemEditArea._value = editAreaLoader.getValue(id);
	},
	
	render: function(item, data) {
		itemEditArea=item;
		var ta = (!isNaN(data.rows));
		item._type = "editArea";
		item._enabled = true;
		
		this.doAddLabel(item, data);
		this.doAddInput(item, data, "TEXTAREA", null, true, true, "dhxform_item_editArea");
		
		
		item._value = (data.value||"");
		
		var that = this;
		
		this.uid=data.uid;
		
		if(!!data.rows){
			var t = document.getElementById(this.uid); 
			t.rows=data.rows;
		}
		editAreaLoader.init({
			id : data.uid,
			start_highlight : true,
			font_size : "9",
			font_family : "verdana, monospace",
			allow_toggle : false,
			language : "zh",
			change_callback:"dhtmlXForm.prototype.items.editArea.changeCallback",
			syntax : "SQL"
		});
		return this;
		
	},
	
	// destructor for editArea needed
	doOnBlur: function(item, editArea) {
		
	},
	
	setValue: function(item, value) {
		editAreaLoader.setValue(this.uid,value);
		item._value = value;
	},
	
	getValue: function(item) {
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
		dhtmlXForm.prototype.items.editArea[a] = dhtmlXForm.prototype.items.template[a];
})();

dhtmlXForm.prototype.setFormData_editArea = function(name,value){
	return this.doWithItem(name, "setValue", value)
};
dhtmlXForm.prototype.getFormData_editArea = function(name){
	return this.doWithItem(name, "getValue")
};

