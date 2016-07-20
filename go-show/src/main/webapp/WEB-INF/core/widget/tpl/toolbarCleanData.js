$(function(){
    GPW.toolbar.mainToolbar.addButton("cleanData", 25, "清除数据", "selection.gif", "save_dis.gif");

    GPW.toolbar.mainToolbar.attachEvent("onClick", function(id) {
        if(id=="cleanData"){
            dhtmlx.confirm({
                ok:"是", cancel:"否",
                text:"你确定清除所有记录吗？此操作不可恢复数据！ ",
                callback:function(state){
                    if(state){
                        $.ajax({
                            type: "GET",
                            url:GLOBAL.S.URL+GLOBAL.P.MODULES+"/a/clean/"+GLOBAL.P.CLASSNAME,
                            success: function(msg){
                                GPW.grid.refreshMainGrid();
                            }
                        });
                    }
                }
            });
        }
    });
});

GPC.constant.defaultPagingNumber=1000;

