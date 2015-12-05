$(function () {

    GPW.toolbar.mainToolbar.hideItem("maximized");
    GPW.toolbar.mainToolbar.hideItem("edit");
    GPW.toolbar.mainToolbar.hideItem("update");
    GPW.grid.mainGrid.attachEvent("onXLE", function (grid_obj, count) {
        grid_obj.forEachRow(function (id) {
            var state = this.cells(id, this.getColIndexById("joininState")).getValue();
            var dImg;
            if (state == 10 || state == 11) {
                dImg = __uri("../../images/member/modify1.png");
            } else {
                dImg = __uri("../../images/member/search1.png");
            }
            this.cells(id, this.getColIndexById("linkInfo")).setValue(dImg);
            $("td img").hover(function () {
                $(this).css({cursor: "pointer"})
            }, function () {
                $(this).css({cursor: ""})
            })
        });
    });
    GPW.grid.mainGrid.attachEvent("onRowSelect", function (id, ind) {
        if (this.getColIndexById("linkInfo") == ind) {
            var pTab = parent.GPW.tabbar.subListTabbar;
            var state = this.cells(id, this.getColIndexById("joininState")).getValue();
            if (state == 10 || state == 11) {
                parent.GPC.url.verifyUrl = parent.GPC.url.verifyDefalutUrl + id;
                pTab.addTab("verify", "审核", null, -1);
            } else {
                parent.GPC.url.verifyUrl = parent.GPC.url.verifyDefalutUrl + id + "&query=1";
                pTab.addTab("verify", "查看", null, -1);
            }


            pTab.tabs("verify").setActive();
        }
    });
});

