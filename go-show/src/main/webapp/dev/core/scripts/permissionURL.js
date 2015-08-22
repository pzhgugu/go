/**
 * @require common:widget/dhtmlxSuite/dhtmlxEx.js
 */
$(function() {
	GPW.grid.mainGrid.attachEvent("onCellChanged", function(rId,cInd,nValue){
	    if(cInd==0&&nValue==null){
	    	nValue="创建";
	    	this.cells(rId, cInd).setValue(nValue);
	    }
	});
});