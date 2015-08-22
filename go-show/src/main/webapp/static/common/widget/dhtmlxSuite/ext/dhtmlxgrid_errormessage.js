/**
 * @require common:widget/dhtmlxSuite/dhtmlx.js
 */
dhtmlXGridObject.prototype.errorMessage = function(obj){
	var xmlLoader = obj.xmlLoader;
	var xmlDoc = xmlLoader.xmlDoc;
	var responseText = xmlDoc.responseText;
	var messageObject= jQuery.parseJSON(responseText);
	if(-1==messageObject.code){	
		if(window.parent==window){
			dhtmlx.message({
				type: "error",
				text: messageObject.message,
				expire: -1
			});
		}else{
			parent.dhtmlx.message({
				type: "error",
				text: messageObject.message,
				expire: -1
			});
		}
	}
}