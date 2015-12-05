GPC.url.formGridLoadUrl = GLOBAL.S.URL + "/admin/goodsattributevalue/load";
GPC.url.updateformGridUrl = GLOBAL.S.URL + "/admin/goodsattributevalue/updateAll/GoodsAttributeValue?format=xml";
GPC.url.delectAttrUrl = GLOBAL.S.URL + "/admin/goodsattributevalue/del";

GPW.formGrid = {
    attrListGrid: {},
    formGridDataProcessor: {},
    init: function (mainForm, type) {

        attrListGrid = new dhtmlXGridObject(mainForm.getContainer("attrList"));
        attrListGrid.setImagePath(GLOBAL.IconsPath);
        attrListGrid.setHeader("排序,名称");
        attrListGrid.setColumnIds("sort,name");
        attrListGrid.setInitWidths("50,428");
        attrListGrid.setColAlign("left,left");
        attrListGrid.setColTypes("dyn,edtxt");
        attrListGrid.setColSorting("str,str");
        attrListGrid.setEditable(true);
        attrListGrid.init();
        var selectId = GPW.grid.mainGrid.getSelectedRowId();
        if (!!selectId && type != GPC.constant.add) {
            SpinnerCtl.show();
            attrListGrid.load(GPC.url.formGridLoadUrl + "?attId=" + selectId, "js");
        }

        attrListGrid.attachEvent("onXLE", function (grid_obj, count) {
            SpinnerCtl.close();
        });

        //attrListGrid.attachFooter("<span style='margin:5px;border: 1px solid #a4bed4;border-radius: 2px;padding: 4px 8px 4px 4px;' id='addLine'><img src='/show/static/common/widget/dhtmlxSuite/imgs/new.gif' style='margin-bottom:-3px'>新增一行</span><span style='border-left: 1px solid #c0d9ec;height: 20px;margin: 5px 6px 0 5px;'></span>,#cspan,#cspan",["text-align:left;height:30px;background: rgba(0, 0, 0, 0) linear-gradient(#e2efff, #d3e7ff) repeat scroll 0 0;"]);


        /* $('#addLine').bind("click", function () {
         alert("增加一行");
         });
         $("#addLine").hover(function () {
         $(this).css({cursor: "pointer"})
         }, function () {
         $(this).css({cursor: ""})
         })*/
        var itemData =
        {
            "type": "block", "list": [{"type": "button", "value": "新增属性", "name": "addLine"},
            {"type": "newcolumn", "offset": 10},
            {"type": "button", "value": "更新属性", "name": "updateAtt"},
            {"type": "newcolumn", "offset": 10},
            {"type": "button", "value": "删除属性", "name": "delAtt"},], "width": 300
        };
        mainForm.addItem("id", itemData, 1);


        formGridDataProcessor = new dataProcessor(GPC.url.updateformGridUrl + "&_value=" + selectId); //lock feed url
        //gridDataProcessor.setTransactionMode("POST",false); //set mode as send-all-by-post
        formGridDataProcessor.setTransactionMode("REST");
        formGridDataProcessor.setUpdateMode("off"); //disable auto-update
        formGridDataProcessor.enableDataNames(true);
        //只发送更改的项目
        formGridDataProcessor.enablePartialDataSend(true);
        formGridDataProcessor.init(attrListGrid); //link dataprocessor to the grid

        formGridDataProcessor.attachEvent("onBeforeDataSending", function (id, state, data) {
            //去除Data数据的发送
            _.each(data, function (v, k, value) {
                if (!!v.data) {
                    v.data = null;
                    delete v.data;
                }
            });
            return true;
        });
        formGridDataProcessor.attachEvent("onAfterUpdate", function (id, action, tid, element) {
            if (element instanceof Element) {
                if (element.getElementsByTagName("code").item(0) != null) {
                    var code = element.getElementsByTagName("code").item(0).childNodes[0].nodeValue;
                    var message = element.getElementsByTagName("message").item(0).childNodes[0].nodeValue;
                    if (code == -1) {
                        parent.dhtmlx.message({
                            type: "error",
                            expire: -1,
                            text: message
                        });
                    }
                }
            }
        });

        mainForm.attachEvent("onButtonClick", function (id) {
            if (id == "addLine") {
                attrListGrid.addRow(attrListGrid.uid(), "", 0);
            }
            if (id == "updateAtt") {
                formGridDataProcessor.sendData();
            }
            if (id == "delAtt") {
                var selectAttrId = attrListGrid.getSelectedRowId();
                if (!selectAttrId) {
                    dhtmlx.alert("请选中一条记录删除！");
                    return;
                }
                if (confirm("你确定删除此记录吗？")) {
                    $.ajax({
                        type: "GET",
                        url: GPC.url.delectAttrUrl,
                        data: "id=" + selectAttrId,
                        success: function (msg) {
                            attrListGrid.clearAll();
                            SpinnerCtl.show();
                            attrListGrid.load(GPC.url.formGridLoadUrl + "?attId=" + selectId, "js");
                        }
                    });
                }

            }
        });
    }
}

$(function () {

    GPW.form.getForm = function (window, formData, type) {
        var mainForm = window.attachForm(formData);
        GPW.formGrid.init(mainForm, type);
        GPW.window.mainWindow.attachEvent("onClose", function (win) {
            GPW.grid.refreshMainGrid();
            return true;
        });
        return mainForm;
    }

});
