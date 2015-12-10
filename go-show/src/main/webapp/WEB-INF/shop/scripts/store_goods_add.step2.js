$(function(){
    // 添加店铺分类
    $("#add_sgcategory").unbind().click(function(){
        $(".sgcategory:last").after($(".sgcategory:last").clone(true).val(0));
    });
    // 选择店铺分类
    $('.sgcategory').unbind().change( function(){
        var _val = $(this).val();       // 记录选择的值
        $(this).val('0');               // 已选择值清零
        // 验证是否已经选择
        if (!checkSGC(_val)) {
            alert('该分类已经选择,请选择其他分类');
            return false;
        }
        $(this).val(_val);              // 重新赋值
    });
    
    /* 商品图片ajax上传 */
    $('#goods_image').fileupload({
        dataType: 'json',
        url: SITEURL + '/se/goods/image/upload',
        formData: {name:'goods_image'},
        add: function (e,data) {
        	$('img[nctype="goods_image"]').attr('src', SITEURL + '/res/img/loading.gif');
            data.submit();
        },
        done: function (e,data) {
            var param = data.result;
            if (typeof(param.error) != 'undefined') {
                alert(param.error);
                $('img[nctype="goods_image"]').attr('src',DEFAULT_GOODS_IMAGE);
            } else {
                $('input[nctype="goods_image"]').val(param.name);
                $('img[nctype="goods_image"]').attr('src',param.thumb_name);
            }
        }
    });

    /* ajax打开图片空间 */
    // 商品主图使用
    $('a[nctype="show_image"]').unbind().ajaxContent({
        event:'click', //mouseover
        loaderType:"img",
        loadingMsg:SITEURL+"/res/img/loading.gif",
        target:'#demo'
    }).click(function(){
        $(this).hide();
        $('a[nctype="del_goods_demo"]').show();
    });
    $('a[nctype="del_goods_demo"]').unbind().click(function(){
        $('#demo').html('');
        $(this).hide();
        $('a[nctype="show_image"]').show();
    });
    // 商品描述使用
    $('a[nctype="show_desc"]').unbind().ajaxContent({
        event:'click', //mouseover
        loaderType:"img",
        loadingMsg: SITEURL + "/res/img/loading.gif",
        target:'#des_demo'
    }).click(function(){
        $(this).hide();
        $('a[nctype="del_desc"]').show();
    });
    $('a[nctype="del_desc"]').click(function(){
        $('#des_demo').html('');
        $(this).hide();
        $('a[nctype="show_desc"]').show();
    });
    $('#add_album').fileupload({
        dataType: 'json',
        url: SITEURL+'/se/goods/image/upload',
        formData: {name:'add_album'},
        add: function (e,data) {
            $('i[nctype="add_album_i"]').removeClass('icon-upload-alt').addClass('icon-spinner icon-spin icon-large').attr('data_type', parseInt($('i[nctype="add_album_i"]').attr('data_type'))+1);
            data.submit();
        },
        done: function (e,data) {
            var _counter = parseInt($('i[nctype="add_album_i"]').attr('data_type'));
            _counter -= 1;
            if (_counter == 0) {
                $('i[nctype="add_album_i"]').removeClass('icon-spinner icon-spin icon-large').addClass('icon-upload-alt');
                $('a[nctype="show_desc"]').click();
            }
            $('i[nctype="add_album_i"]').attr('data_type', _counter);
        }
    });
    /* ajax打开图片空间 end */
    
    // 商品属性
   /* attr_selected();
    $('select[nc_type="attr_select"]').change(function(){
        id = $(this).find('option:selected').attr('nc_type');
        name = $(this).attr('attr').replace(/__NC__/g,id);
        $(this).attr('name',name);
    });*/
    
    // 修改规格名称
    $('dl[nctype="spec_group_dl"]').on('click', 'input[type="checkbox"]', function(){
        pv = $(this).parents('li').find('span[nctype="pv_name"]');
        if(typeof(pv.find('input').val()) == 'undefined'){
            pv.html('<input type="text" maxlength="20" class="text" value="'+pv.html()+'" />');
        }else{
            pv.html(pv.find('input').val());
        }
    });
    
    $('span[nctype="pv_name"] > input').on('change',function(){
        change_img_name($(this));       // 修改相关的颜色名称
        into_array();           // 将选中的规格放入数组
        goods_stock_set();      // 生成库存配置
    });
    
    // 修改品牌名称
    $('select[name="brandId"]').change(function(){
        getBrandName();
    });

    // 运费部分显示隐藏
    $('input[nctype="freight"]').click(function(){
            $('input[nctype="freight"]').nextAll('div[nctype="div_freight"]').hide();
            $(this).nextAll('div[nctype="div_freight"]').show();
    });
    
    // 商品所在地
    var area_select = $("#provinceId");
    areaInit(area_select,0);//初始化地区
    $("#provinceId").change(function (){
        // 删除后面的select
        $(this).nextAll("select").remove();
        if (this.value > 0){
            var text = $(this).get(0).options[$(this).get(0).selectedIndex].text;
            var area_id = this.value;
            var EP = new Array();
            EP[1]= true;EP[2]= true;EP[9]= true;EP[22]= true;EP[34]= true;EP[35]= true;
            if(typeof(nc_a[area_id]) != 'undefined'){//数组存在
                var areas = new Array();
                var option = "";
                areas = nc_a[area_id];
            if (typeof(EP[area_id]) == 'undefined'){
                option = "<option value='0'>"+text+"(*)</option>";
            }
            $("<select name='cityId' id='cityId'>"+option+"</select>").insertAfter(this);
                for (var i = 0; i <areas.length; i++){
                    $(this).next("select").append("<option value='" + areas[i][0] + "'>" + areas[i][1] + "</option>");
                }
            }
        }
     });
    
    // 定时发布时间
    $('#starttime').datepicker({dateFormat: 'yy-mm-dd'});
    $('input[name="goodsState"]').click(function(){
        if($(this).attr('nctype') == 'auto'){
            $('#starttime').removeAttr('disabled').css('background','');
            $('#starttime_H').removeAttr('disabled').css('background','');
            $('#starttime_i').removeAttr('disabled').css('background','');
        }else{
            $('#starttime').attr('disabled','disabled').css('background','#E7E7E7 none');
            $('#starttime_H').attr('disabled','disabled').css('background','#E7E7E7 none');
            $('#starttime_i').attr('disabled','disabled').css('background','#E7E7E7 none');
        }
    });
    
    // 计算折扣
    $('input[name="goodsStorePrice"],input[name="goodsMarketprice"]').change(function(){
        discountCalculator();
    });
    
    /* AJAX添加规格值 */
    // 添加规格
    $('a[nctype="specAdd"]').click(function(){
        var _parent = $(this).parents('li:first');
        _parent.find('div[nctype="specAdd1"]').hide();
        _parent.find('div[nctype="specAdd2"]').show();
        _parent.find('input').focus();
    });
    // 取消
    $('a[nctype="specAddCancel"]').click(function(){
        var _parent = $(this).parents('li:first');
        _parent.find('div[nctype="specAdd1"]').show();
        _parent.find('div[nctype="specAdd2"]').hide();
        _parent.find('input').val('');
    });
    // 提交
    $('a[nctype="specAddSubmit"]').click(function(){
        var _parent = $(this).parents('li:first');
        eval('var data_str = ' + _parent.attr('data-param'));
        var _input = _parent.find('input');
        $.getJSON(data_str.url, {gc_id : data_str.gc_id , sp_id : data_str.sp_id , name : _input.val()}, function(data){
            if (data.done) {
                _parent.before('<li><span nctype="input_checkbox"><input type="checkbox" name="sp_val[' + data_str.sp_id + '][' + data.value_id + ']" nc_type="' + data.value_id + '" value="' +_input.val()+ '" /></span><span nctype="pv_name">' + _input.val() + '</span></li>');
                _input.val('');
            }
            _parent.find('div[nctype="specAdd1"]').show();
            _parent.find('div[nctype="specAdd2"]').hide();
        });
    });
    // 修改规格名称
    $('input[nctype="spec_name"]').change(function(){
        eval('var data_str = ' + $(this).attr('data-param'));
        if ($(this).val() == '') {
            $(this).val(data_str.name);
        }
        $('th[nctype="spec_name_' + data_str.id + '"]').html($(this).val());
    });
});
// 计算商品库存
function computeStock(){
    // 库存
    var _stock = 0;
    $('input[data_type="stock"]').each(function(){
        if($(this).val() != ''){
            _stock += parseInt($(this).val());
        }
    });
    $('input[name="goodsStorage"]').val(_stock);
}

// 计算价格
function computePrice(){
    // 计算最低价格
    var _price = 0;var _price_sign = false;
    $('input[data_type="price"]').each(function(){
        if($(this).val() != '' && $(this)){
            if(!_price_sign){
                _price = parseFloat($(this).val());
                _price_sign = true;
            }else{
                _price = (parseFloat($(this).val())  > _price) ? _price : parseFloat($(this).val());
            }
        }
    });
    $('input[name="goodsStorePrice"]').val(number_format(_price, 2));

    discountCalculator();       // 计算折扣
}

// 计算折扣
function discountCalculator() {
    var _price = parseFloat($('input[name="goodsStorePrice"]').val());
    var _marketprice = parseFloat($('input[name="goodsMarketprice"]').val());
    if((!isNaN(_price) && _price != 0) && (!isNaN(_marketprice) && _marketprice != 0)){
        var _discount = parseInt(_price/_marketprice*100);
        $('input[name="goodsDiscount"]').val(_discount);
    }
}

//获得商品名称
function getBrandName() {
    var brand_name = $('select[name="brandId"] > option:selected').html();
    $('input[name="brandName"]').val(brand_name);
}
//修改相关的颜色名称
function change_img_name(Obj){
     var S = Obj.parents('li').find('input[type="checkbox"]');
     S.val(Obj.val());
     var V = $('tr[nctype="file_tr_'+S.attr('nc_type')+'"]');
     V.find('span[nctype="pv_name"]').html(Obj.val());
     V.find('input[type="file"]').attr('name', Obj.val());
}
// 商品属性
/*function attr_selected(){
    $('select[nc_type="attr_select"] option:selected').each(function(){
        id = $(this).attr('nc_type');
        name = $(this).parents('select').attr('attr').replace(/__NC__/g,id);
        $(this).parents('select').attr('name',name);
    });
}*/
// 验证店铺分类是否重复
function checkSGC($val) {
    var _return = true;
    $('.sgcategory').each(function(){
        if ($val !=0 && $val == $(this).val()) {
            _return = false;
        }
    });
    return _return;
} 
/* 插入商品图片 */
function insert_img(name, src) {
    $('input[nctype="goods_image"]').val(name);
    $('img[nctype="goods_image"]').attr('src',src);
}

/* 插入编辑器 */
function insert_editor(file_path) {
    KE.appendHtml('goods_body', '<img src="'+ file_path + '">');
}

function setArea(area1, area2) {
    $('#provinceId').val(area1).change();
    $('#cityId').val(area2);
}