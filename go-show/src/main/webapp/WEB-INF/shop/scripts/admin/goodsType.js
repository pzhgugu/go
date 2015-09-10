GLOBAL.P.initMainWindow=GPW.window.initMainWindow;

GPW.window.initMainWindow=function(type){
    var that = this;
    var tree=GPW.tree.mainTree;
    var selectId=tree.getSelectedItemId();
    if(tree.getLevel(selectId)!=3){
        alert("请选中第三级分类创建商品类型！");
        return ;
    }
    GLOBAL.P.initMainWindow.call(that, type);
}

GPW.window.createWindow=function(){
    this.initHandle();
    this.handle.attachViewportTo("layoutObj");
    this.mainWindow = this.handle.createWindow("w1", $("#layoutObj").width()/2-350, 30, 700, 180);
}

GPW.tabbar={
    attachURL:function(actvId,id){
        var url="";
        if(actvId=="spec"){
            url=GLOBAL.S.URL+"/goodsscpec/tpl/SingleGrid/GoodsSpec/d?_value="+id;
        }else if(actvId=="brand"){
            url=GLOBAL.S.URL+"/goodstype/brand?id="+id;
        }else if(actvId=="attribute"){
            url=GLOBAL.S.URL+"/goodstype/attribute?id="+id;
        }else{
            return ;
        }
        this.subListTabbar.tabs(actvId).attachURL(url);
    },
    initSubList:function(){
        this.subListTabbar=GPW.layout.tabbarLayout.attachTabbar({
            tabs:[{id: "spec", text: "关联规格", active: 1},
                    {id: "brand", text: "关联品牌"},
                    {id: "attribute", text: "属性管理"}]
        });
        this.subListTabbar.attachEvent("onSelect", function(actvId, lastId){
            var grid=GPW.grid.mainGrid;
            var id = grid.getSelectedRowId();
            if(!id){
                return ;
            }
            GPW.tabbar.attachURL(actvId,id);
            return true;
        });
    },
    loadActiveTab:function(id){
        var actvId = this.subListTabbar.getActiveTab();
        this.attachURL(actvId,id);
    }
}