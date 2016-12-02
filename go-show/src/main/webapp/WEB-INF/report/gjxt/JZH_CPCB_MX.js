$(function(){
    GPW.form.queryForm.attachEvent("onButtonClick", function(id) {
        if (id == "installData") {
            var handle = new dhtmlXWindows();
            var mainWindow = handle.createWindow("w1",  $("#layoutObj").width()/2-250, 100,500, 150);
            mainWindow.setText("导入数据");

            var myDate = new Date();
            formData = [
                {type: "settings", position: "label-left", labelWidth: 100, inputWidth: 120},
                {type: "block", inputWidth: "auto", offsetTop: 12, list: [
                    {type: "calendar",name: "myCalendar", label: "会计期", dateFormat: "%Y%m", serverDateFormat: "%Y%m", value: myDate.getFullYear()+""+(myDate.getMonth()+1), calendarPosition: "right"},
                    {type: "button","name":"install", value: "确定导入", offsetLeft: 70, offsetTop: 14}
                ]}
            ];

            myForm = mainWindow.attachForm(formData, true);

            myForm.attachEvent("onButtonClick", function(id) {
                if (id == "install") {
                    var value = myForm.getItemValue("myCalendar", true);
                    SpinnerCtl.show();
                    $.ajax({
                        type: "GET",
                        url:GLOBAL.S.URL+"/admin/dc/a/run/JZH_CPCB_MX",
                        data:{accountPeriod:value},
                        success: function(msg){
                            SpinnerCtl.close();
                            handle.unload();
                        },
                        error: function (msg) {
                            SpinnerCtl.close();
                            alert(msg);
                        }
                    });
                }
            });
        }
    })
});

GPW.form.unionDataList=function(dataList){
    dataList= _.union(dataList, [{"type":"newcolumn"},{"type":"button","name":"installData","value":"导入数据"}]);
    return dataList;
}

