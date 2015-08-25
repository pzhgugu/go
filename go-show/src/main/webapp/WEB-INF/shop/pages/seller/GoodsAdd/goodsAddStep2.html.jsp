<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.Date"%>
<%@ taglib uri="/fis" prefix="fis"%>

<div class="ncsc-layout wrapper">
  <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp"/>
  <div class="ncsc-layout-right" id="layoutRight">
    <fis:block url="shop:widget/tpl/seller/nav.html.jsp" />
    <div id="mainContent" class="main-content">
      <fis:block url="shop:pages/seller/GoodsAdd/setp.html.jsp" />
      <div class="item-publish">
        <form action="${S_URL}/se/goods/addstep/savegoods" id="goods_form" method="post">
          <input type="hidden" value="ok" name="form_submit">
          <input type="hidden" value="0" name="type_id">
          <input type="hidden" value="${S_URL}/se/goods/addstep/one" name="ref_url">
          <div class="ncsc-form-goods">
            <h3>
              商品基本信息
            </h3>
            <dl>
              <dt>
                商品分类：
              </dt>
              <dd id="gcategory">
                ${P_GOODSCLASS_LISTNAME}
                <a href="${S_URL}/se/goods/addstep/one" class="ncsc-btn">
                  编辑
                </a>
                <input type="hidden" class="text" value="${P_GOODSCLASS.id}" name="gcId"
                id="cate_id">
                <input type="hidden" class="text" value="${P_GOODSCLASS_LISTNAME}" name="gcName">
              </dd>
            </dl>
            <dl>
              <dt class="required">
                <i class="required">
                  *
                </i>
                商品名称：
              </dt>
              <dd>
                <input type="text" value="" class="text w400" name="name">
                <span>
                </span>
                <p class="hint">
                  商品标题名称长度至少3个字符，最长50个汉字
                </p>
              </dd>
            </dl>
            <dl>
              <dt class="required">
                广告词：
              </dt>
              <dd>
                <input type="text" value="" class="text w400" name="adWord">
                <span>
                </span>
                <p class="hint">
                  广告词最长不能超过50个汉字
                </p>
              </dd>
            </dl>
            <dl>
              <dt nc_type="no_spec">
                <i class="required">
                  *
                </i>
                商品价格：
              </dt>
              <dd nc_type="no_spec">
                <input type="text" class="text w60" value="" name="goodsStorePrice">
                <em class="add-on">
                  <i class="icon-renminbi">
                  </i>
                </em>
                <span>
                </span>
                <p class="hint">
                  价格必须是0.01~999999之间的数字，且不能高于市场价。
                  <br>
                  此价格为商品实际销售价格，如果商品存在规格，该价格显示最低价格。
                </p>
              </dd>
            </dl>
            <dl>
              <dt>
                <i class="required">
                  *
                </i>
                市场价：
              </dt>
              <dd>
                <input type="text" class="text w60" value="" name="goodsMarketprice">
                <em class="add-on">
                  <i class="icon-renminbi">
                  </i>
                </em>
                <span>
                </span>
                <p class="hint">
                  价格必须是0.01~9999999之间的数字，此价格仅为市场参考售价，请根据该实际情况认真填写。
                </p>
              </dd>
            </dl>
            <dl>
              <dt>
                成本价：
              </dt>
              <dd>
                <input type="text" class="text w60" value="" name="goodsCostprice">
                <em class="add-on">
                  <i class="icon-renminbi">
                  </i>
                </em>
                <span>
                </span>
                <p class="hint">
                  价格必须是0.00~999999之间的数字，此价格为商户对所销售的商品实际成本价格进行备注记录，非必填选项，不会在前台销售页面中显示。
                </p>
              </dd>
            </dl>
            <dl>
              <dt>
                折扣：
              </dt>
              <dd>
                <input type="text" style="background:#E7E7E7 none;" readonly="readonly"
                class="text w60" value="" name="goodsDiscount">
                <em class="add-on">
                  %
                </em>
                <p class="hint">
                  根据销售价与市场价比例自动生成，不需要编辑。
                </p>
              </dd>
            </dl>
            <dl style="display:none" class="spec-bg" nc_type="spec_dl">
              <dt>
                库存配置：
              </dt>
              <dd class="spec-dd">
                <table cellspacing="0" cellpadding="0" border="0" class="spec_table">
                  <thead>
                    <tr>
                      <th class="w100">
                        <span class="red">
                          *
                        </span>
                        价格
                      </th>
                      <th class="w60">
                        <span class="red">
                          *
                        </span>
                        库存
                      </th>
                      <th class="w120">
                        商家货号
                      </th>
                    </tr>
                  </thead>
                  <tbody nc_type="spec_table">
                  </tbody>
                </table>
              </dd>
            </dl>
            <dl>
              <dt nc_type="no_spec">
                <i class="required">
                  *
                </i>
                商品库存：
              </dt>
              <dd nc_type="no_spec">
                <input type="text" class="text w60" value="" name="goodsStorage">
                <span>
                </span>
                <p class="hint">
                  商铺库存数量必须为1~999999999之间的整数
                  <br>
                  若启用了库存配置，则系统自动计算商品的总数，此处无需卖家填写
                </p>
              </dd>
            </dl>
            <dl>
              <dt nc_type="no_spec">
                商家货号：
              </dt>
              <dd nc_type="no_spec">
                <p>
                  <input type="text" class="text" value="" name="goodsSerial">
                </p>
                <p class="hint">
                  商家货号是指商家管理商品的编号，买家不可见
                  <br>
                  最多可输入20个字符，支持输入中文、字母、数字、_、/、-和小数点
                </p>
              </dd>
            </dl>
            <dl>
              <dt>
                <i class="required">
                  *
                </i>
                商品图片：
              </dt>
              <dd>
                <div class="ncsc-goods-default-pic">
                  <div class="goodspic-uplaod">
                    <div class="upload-thumb">
                      <img src="${S_URL}/res/img/default_goods_image_240.gif" nctype="goods_image">
                    </div>
                    <p class="hint">
                      上传商品默认主图，如多规格值时将默认使用该图或分规格上传各规格主图；支持jpg、gif、png格式上传或从图片空间中选择，建议使用
                      <font color="red">
                        尺寸800x800像素以上、大小不超过1M的正方形图片
                      </font>
                      ，上传后的图片将会自动保存在图片空间的默认分类中。
                    </p>
                    <input type="hidden" value="" nctype="goods_image" id="image_path" name="goodsImage">
                    <span>
                    </span>
                    <div class="handle">
                      <div class="ncsc-upload-btn">
                        <a href="javascript:void(0);">
                          <span>
                            <input type="file" id="goods_image" name="file" class="input-file"
                            size="1" hidefocus="true">
                          </span>
                          <p>
                            <i class="icon-upload-alt">
                            </i>
                            图片上传
                          </p>
                        </a>
                      </div>
                      <a href="${S_URL}/se/goods/addstep/two/images" nctype="show_image" class="ncsc-btn">
                        <i class="icon-picture">
                        </i>
                        从图片空间选择
                      </a>
                      <a style="display: none;" class="ncsc-btn ml5" nctype="del_goods_demo"
                      href="javascript:void(0);">
                        <i class="icon-circle-arrow-up">
                        </i>
                        关闭相册
                      </a>
                    </div>
                  </div>
                </div>
                <div id="demo">
                </div>
              </dd>
            </dl>
            <h3>
              商品详情描述
            </h3>
            <dl>
              <dt>
                商品品牌：
              </dt>
              <dd>
                <select name="brandId">
                  <option value="0">
                    请选择...
                  </option>
                </select>
                <input type="hidden" value="" name="brandName">
              </dd>
            </dl>
            <dl>
              <dt>
                商品描述：
              </dt>
              <dd>
                <textarea style="width: 100%; height: 480px; visibility: hidden; display: none;"
                name="g_body" id="g_body">
                </textarea>
                <div class="hr8">
                  <div class="ncsc-upload-btn">
                    <a href="javascript:void(0);">
                      <span>
                        <input type="file" multiple="multiple" id="add_album" name="file"
                        class="input-file" size="1" hidefocus="true">
                      </span>
                      <p>
                        <i nctype="add_album_i" data_type="0" class="icon-upload-alt">
                        </i>
                        图片上传
                      </p>
                    </a>
                  </div>
                  <a href="${S_URL}/se/goods/addstep/two/imagesdesc" nctype="show_desc"
                  class="ncsc-btn">
                    <i class="icon-picture">
                    </i>
                    插入相册图片
                  </a>
                  <a style="display: none;" class="ncsc-btn ml5" nctype="del_desc" href="javascript:void(0);">
                    <i class=" icon-circle-arrow-up">
                    </i>
                    关闭相册
                  </a>
                  <p>
                  </p>
                  <p id="des_demo">
                  </p>
                </div>
              </dd>
            </dl>
            <dl>
              <dt>
                关联版式：
              </dt>
              <dd>
                <span class="mr50">
                  <label>
                    顶部版式
                  </label>
                  <select name="plateidTop">
                    <option value="">
                      请选择
                    </option>
                  </select>
                </span>
                <span class="mr50">
                  <label>
                    底部版式
                  </label>
                  <select name="plateidBottom">
                    <option value="">
                      请选择
                    </option>
                  </select>
                </span>
              </dd>
            </dl>
            <!--transport info begin-->
            <h3>
              商品物流信息
            </h3>
            <dl>
              <dt>
                所在地：
              </dt>
              <dd>
          <p id="region">
            <select class="d_inline" name="provinceId" id="province_id">
            </select>
          </p>
              </dd>
            </dl>
            <dl>
              <dt>
                运费：
              </dt>
              <dd>
                <ul class="ncsc-form-radio-list">
                  <li>
                    <input type="radio" value="0" checked="checked" class="radio" name="goodsFreight"
                    nctype="freight" id="freight_0">
                    <label for="freight_0">
                      固定运费
                    </label>
                    <div nctype="div_freight">
                      <input type="text" name="g_freight" value="0.00" nc_type="transport" class="w50 text"
                      id="g_freight">
                      <em class="add-on">
                        <i class="icon-renminbi">
                        </i>
                      </em>
                    </div>
                  </li>
                  <li>
                    <input type="radio" value="1" class="radio" name="freight" nctype="freight"
                    id="freight_1">
                    <label for="freight_1">
                      使用运费模板
                    </label>
                    <div style="display: none;" nctype="div_freight">
                      <input type="hidden" name="transport_id" value="" id="transport_id">
                      <input type="hidden" name="transport_title" value="" id="transport_title">
                      <span class="transport-name" id="postageName">
                      </span>
                      <a id="postageButton" class="ncsc-btn" onclick="window.open('index.php?act=store_transport&amp;type=select')"
                      href="JavaScript:void(0);">
                        <i class="icon-truck">
                        </i>
                        选择运费模板
                      </a>
                    </div>
                  </li>
                </ul>
                <p class="hint">
                  运费设置为 0 元，前台商品将显示为免运费。
                </p>
              </dd>
            </dl>
            <!--transport info end-->
            <h3>
              发票信息
            </h3>
            <dl>
              <dt>
                是否开增值税发票：
              </dt>
              <dd>
                <ul class="ncsc-form-radio-list">
                  <li>
                    <label>
                      <input type="radio" value="1" name="goodsVat">
                      是
                    </label>
                  </li>
                  <li>
                    <label>
                      <input type="radio" checked="checked" value="0" name="goodsVat">
                      否
                    </label>
                  </li>
                </ul>
                <p class="hint">
                </p>
              </dd>
            </dl>
            <h3>
              其他信息
            </h3>
            <dl>
              <dt>
                本店分类：
              </dt>
              <dd>
                <span class="new_add">
                  <a class="ncsc-btn" id="add_sgcategory" href="javascript:void(0)">
                    新增分类
                  </a>
                </span>
                <select class="sgcategory" name="sgcate_id[]">
                  <option value="0">
                    请选择...
                  </option>
                </select>
                <p class="hint">
                  商品可以从属于店铺的多个分类之下,
                  <br>
                  店铺分类可以由 "用户中心 -&gt; 卖家 -&gt; 商品管理 -&gt; 分类管理" 中自定义
                </p>
              </dd>
            </dl>
            <dl>
              <dt>
                商品发布：
              </dt>
              <dd>
                <ul class="ncsc-form-radio-list">
                  <li>
                    <label>
                      <input type="radio" checked="checked" value="1" name="goodsState">
                      立即发布
                    </label>
                  </li>
                  <li>
                    <label>
                      <input type="radio" nctype="auto" value="0" name="goodsState">
                      发布时间
                    </label>
                    <input type="text" value='<fmt:formatDate value="<%=new Date() %>" pattern="yyyy-MM-dd"/>' id="starttime" style="background:#E7E7E7 none;"
                    disabled="disabled" name="starttime" class="w80 text hasDatepicker" readonly="readonly">
                    <select id="starttime_H" name="starttime_H" style="background:#E7E7E7 none;"
                    disabled="disabled">
                      <option value="00">
                        00
                      </option>
                      <option value="01">
                        01
                      </option>
                      <option value="02">
                        02
                      </option>
                      <option value="03">
                        03
                      </option>
                      <option value="04">
                        04
                      </option>
                      <option value="05">
                        05
                      </option>
                      <option value="06">
                        06
                      </option>
                      <option value="07">
                        07
                      </option>
                      <option selected="selected" value="08">
                        08
                      </option>
                      <option selected="selected" value="09">
                        09
                      </option>
                      <option selected="selected" value="10">
                        10
                      </option>
                      <option selected="selected" value="11">
                        11
                      </option>
                      <option selected="selected" value="12">
                        12
                      </option>
                      <option selected="selected" value="13">
                        13
                      </option>
                      <option selected="selected" value="14">
                        14
                      </option>
                      <option selected="selected" value="15">
                        15
                      </option>
                      <option selected="selected" value="16">
                        16
                      </option>
                      <option selected="selected" value="17">
                        17
                      </option>
                      <option selected="selected" value="18">
                        18
                      </option>
                      <option selected="selected" value="19">
                        19
                      </option>
                      <option selected="selected" value="20">
                        20
                      </option>
                      <option selected="selected" value="21">
                        21
                      </option>
                      <option selected="selected" value="22">
                        22
                      </option>
                      <option selected="selected" value="23">
                        23
                      </option>
                    </select>
                    时
                    <select id="starttime_i" name="starttime_i" style="background:#E7E7E7 none;"
                    disabled="disabled">
                      <option selected="selected" value="05">
                        05
                      </option>
                      <option selected="selected" value="10">
                        10
                      </option>
                      <option selected="selected" value="15">
                        15
                      </option>
                      <option selected="selected" value="20">
                        20
                      </option>
                      <option selected="selected" value="25">
                        25
                      </option>
                      <option selected="selected" value="30">
                        30
                      </option>
                      <option selected="selected" value="35">
                        35
                      </option>
                      <option selected="selected" value="40">
                        40
                      </option>
                      <option selected="selected" value="45">
                        45
                      </option>
                      <option selected="selected" value="50">
                        50
                      </option>
                      <option selected="selected" value="55">
                        55
                      </option>
                    </select>
                    分
                  </li>
                  <li>
                    <label>
                      <input type="radio" value="0" name="goodsState">
                      放入仓库
                    </label>
                  </li>
                </ul>
              </dd>
            </dl>
            <dl>
              <dt>
                商品推荐：
              </dt>
              <dd>
                <ul class="ncsc-form-radio-list">
                  <li>
                    <label>
                      <input type="radio" checked="checked" value="1" name="goodsCommend">
                      是
                    </label>
                  </li>
                  <li>
                    <label>
                      <input type="radio" value="0" name="goodsCommend">
                      否
                    </label>
                  </li>
                </ul>
                <p class="hint">
                  被推荐的商品会显示在店铺首页
                </p>
              </dd>
            </dl>
          </div>
          <div class="bottom tc hr32">
            <label class="submit-border">
              <input type="submit" value="下一步，上传商品图片" class="submit">
            </label>
          </div>
        </form>
      </div>
    </div>
  </div>
</div>

<fis:require id="common:widget/jquery/jquery.ajaxContent.pack.js" />
<fis:require id="common:widget/jquery-ui/i18n/zh-CN.js" />
<fis:require id="shop:scripts/common_select.js" />
<fis:require id="common:widget/fileupload/jquery.iframe-transport.js"/>
<fis:require id="common:widget/fileupload/jquery.ui.widget.js" />
<fis:require id="common:widget/fileupload/jquery.fileupload.js" />
<fis:require id="common:widget/poshytip/jquery.poshytip.js" />
<fis:require id="common:widget/kindeditor/kindeditor.js" />
<fis:require id="shop:scripts/page.step2.js" />
<fis:require id="shop:scripts/store_goods_add.step2.js" />