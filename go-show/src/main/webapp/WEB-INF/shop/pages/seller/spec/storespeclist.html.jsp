<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib uri="/fis" prefix="fis" %>

<div class="ncsc-layout wrapper">
    <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp"/>
    <div class="ncsc-layout-right" id="layoutRight">
        <fis:block url="shop:widget/tpl/seller/nav.html.jsp"/>


        <div id="mainContent" class="main-content">

            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="active"><a href="index.php?act=store_spec">商品规格</a></li>
                </ul>
            </div>
            <div class="alert mt15 mb5"><strong>操作提示：</strong>
                <ul>
                    <li>1、选择店铺经营的商品分类，以读取平台绑定的商品分类-规格类型，如分类：“服装”；规格：“颜色”、“尺码”等；当商品分类具有“颜色”规格时，可选择色块加以标识。</li>
                    <li>2、添加所属规格下的规格值，已有规格值可以删除，新增未保存的规格值可以移除；<font color="red">新增的规格值必须填写</font>，否则该行数据不会被更新或者保存。</li>
                    <li>3、可通过排序0-255改变规格值显示顺序；在发布商品时勾选已绑定的商品规格，还可对规格值进行“别名”修改操作，但不会影响规格值默认名称的设定。</li>
                </ul>
            </div>
            <table class="search-form">
                <tbody>
                <tr>
                    <td class="w20">&nbsp;</td>
                    <td class="w120"><strong>选择经营的商品分类</strong></td>
                    <td>
        <span nctype="gc1">
                  <select data-param="{deep:1}" nctype="gc">
                      <option>请选择...</option>
                      <c:forEach items="${P_GOODSCLASS_LIST}" var="goodsClass">
                          <option value="${goodsClass.id}">${goodsClass.name}</option>
                      </c:forEach>
                  </select>
        </span>
                        <span nctype="gc2"></span>
                        <span nctype="gc3"></span>
                    </td>
                    <td>&nbsp;</td>
                </tr>
                </tbody>
            </table>
            <div class="ncsc-goods-spec" nctype="class_spec">
                <div class="spec-tabmenu" nctype="spec_ul"></div>
                <div class="spec-iframe" nctype="spec_iframe">
                    <div class="norecord tc">
                        <div class="warning-option"><i class="icon-warning-sign"></i><span>暂无符合条件的数据记录</span></div>
                    </div>
                </div>
            </div>
            <script>
                $(function () {
                    // 查询下级分类，分类不存在显示当前分类绑定的规格
                    $('select[nctype="gc"]').change(function () {
                        $(this).parents('td:first').nextAll().html('');
                        $('div[nctype="spec_ul"]').html('');
                        $('div[nctype="spec_iframe"]').html('');
                        getClassSpec($(this));
                    });
                });

                // ajax选择商品分类
                function getClassSpec($this) {
                    var id = $this.val();
                    var data_str = '';
                    eval('data_str =' + $this.attr('data-param'));
                    var deep = data_str.deep;
                    if (isNaN(id)) {
                        // 清理分类
                        clearClassHtml(parseInt(deep) + 1);
                    }
                    $.getJSON('${S_URL}/se/spec/class?id=' + id + '&deep=' + deep, function (data) {
                        $('div[nctype="spec_iframe"]').empty();
                        $('div[nctype="spec_ul"]').empty();
                        if (data) {
                            if (data.type == 'class') {
                                nextClass(data.data, data.deep);
                            } else if (data.type == 'spec') {
                                specList(data.data, data.deep, data.gcid);
                            }
                        }
                    });
                }

                // 下一级商品分类
                function nextClass(data, deep) {
                    $('span[nctype="gc' + deep + '"]').html('').append('<select data-param="{deep:' + deep + '}"></select>')
                            .find('select').change(function () {
                                getClassSpec($(this));
                            }).append('<option>请选择...</option>');
                    $.each(data, function (i, n) {
                        if (n != null) {
                            $('span[nctype="gc' + deep + '"] > select').append('<option value="' + n.gc_id + '">' + n.gc_name + '</option>');
                        }
                    });
                    // 清理分类
                    clearClassHtml(parseInt(deep) + 1);
                }

                // 列出规格信息
                function specList(data, deep, gcid) {
                    if (typeof(data) != 'undefined' && data != '') {
                        var $_ul = $('<ul></ul>');
                        $.each(data, function (i, n) {
                            var param = "{spid:'" + n.sp_id + "',gcid:'" + gcid + "'}";
                            $_ul.append('<li><a href="javascript:void(0);" nctype="editSpec" data-param=' + param + '>编辑' + n.sp_name + '规格</a></li>');
                        });
                        $_ul.find('a').click(function () {
                            $_ul.find('li').removeClass('selected');
                            $(this).parents('li:first').addClass('selected');
                            editSpecValue($(this));
                        });
                        $_ul.find('a:first').click();
                        $('div[nctype="spec_ul"]').append($_ul);
                    } else {
                        $('div[nctype="spec_ul"]').append('<div class="warning-option"><i class="icon-warning-sign"></i><span>该分类不能添加规格</span></div>');
                    }
                    // 清理分类
                    clearClassHtml(deep);
                }

                // 清理二级分类信息
                function clearClassHtml(deep) {
                    switch (deep) {
                        case 2:
                            $('span[nctype="gc2"]').empty();
                        case 3:
                            $('span[nctype="gc3"]').empty();
                            break;
                    }
                }

                // ajax编辑规格值
                function editSpecValue(o) {
                    $('div[nctype="spec_iframe"]').html('');
                    var data_str = '';
                    eval('data_str =' + o.attr('data-param'));
                    $_iframe = $('<iframe id="iframepage" name="iframepage" frameBorder=0 scrolling=no width="100%" height="630px" src="${S_URL}/se/spec/addPage?spid=' + data_str.spid + '&gcid=' + data_str.gcid + '" ></iframe>');
                    $('div[nctype="spec_iframe"]').append($_iframe);
                }

            </script>
        </div>


    </div>
</div>
