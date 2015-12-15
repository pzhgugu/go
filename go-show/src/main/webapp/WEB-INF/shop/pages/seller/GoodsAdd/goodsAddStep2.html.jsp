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
      <c:if test="${empty P_GOODSCOMMON}">
      <fis:block url="shop:pages/seller/GoodsAdd/setp.html.jsp" />
      </c:if>
      <c:if test="${!empty P_GOODSCOMMON}">
        <div class="tabmenu">
          <ul class="tab pngFix">
            <li class="active"><a href="${S_URL}/se/goods/editgoods?commonid=${P_GOODSCOMMON.id}">编辑商品</a></li><li class="normal"><a href="${S_URL}/se/goods/addstep/editimages?goodsid=${P_GOODSCOMMON.id}&edit=1">编辑图片</a></li></ul>
        </div>
      </c:if>
      <div class="item-publish">
        <form action="${S_URL}/se/goods/addstep/savegoods" id="goods_form" method="post">
          <input type="hidden" value="${P_GOODSCOMMON.id}" name="id">
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
                <input type="text" value="${P_GOODSCOMMON.name}" class="text w400" name="name">
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
                <input type="text" value="${P_GOODSCOMMON.adWord}" class="text w400" name="adWord">
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
                <input type="text" class="text w60" value="${P_GOODSCOMMON.goodsStorePrice}" name="goodsStorePrice">
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
                <input type="text" class="text w60" value="${P_GOODSCOMMON.goodsMarketprice}" name="goodsMarketprice">
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
                <input type="text" class="text w60" value="${P_GOODSCOMMON.goodsCostprice}" name="goodsCostprice">
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
                class="text w60" value="${P_GOODSCOMMON.goodsDiscount}" name="goodsDiscount">
                <em class="add-on">
                  %
                </em>
                <p class="hint">
                  根据销售价与市场价比例自动生成，不需要编辑。
                </p>
              </dd>
            </dl>
            <c:if test="${!empty P_GOODSSPECS}">
              <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">
                <dl <c:if test="${goodsSpec.spName=='颜色'}" > spec_img="t"</c:if> class="spec-bg" nctype="spec_group_dl" nc_type="spec_group_dl_${status.index}">
                  <dt>
                    <input type="text" data-param="{id:'${goodsSpec.id}',name:'${goodsSpec.spName}'}" nctype="spec_name"
                           maxlength="4" value="${goodsSpec.spName}" title="自定义规格类型名称，规格值名称最多不超过4个字"
                           class="text w60 tip2 tr" name="gsvslList[${status.index}].spName">
                    <input type="hidden" name="gsvslList[${status.index}].spId" value="${goodsSpec.id}">
                    ：
                  </dt>
                  <dd nctype="sp_group_val">
                    <ul class="spec">



                      <c:if test="${empty P_GOODSSPEC_SELECT}" >
                        <c:forEach items="${P_GOODSSPECVALUE_LIST}" var="goodsSpecValue" varStatus="vs">
                          <c:if test="${goodsSpec.id==goodsSpecValue.spId}">
                            <li><span nctype="input_checkbox">
              <input type="checkbox" name="gsvslList[${status.index}].spvId" class="sp_val"
                     nc_type="${goodsSpecValue.name}"
                     value="${goodsSpecValue.id}">
                     </span>
                              <span nctype="pv_name">${goodsSpecValue.name}</span>
                            </li>
                          </c:if>
                        </c:forEach>
                      </c:if>
                      <!--修改时的代码 -->
                      <c:if test="${!empty P_GOODSSPEC_SELECT}" >
                        <c:forEach items="${P_GOODSSPECVALUE_LIST}" var="goodsSpecValue" varStatus="vs">
                          <c:set var="currentSelect" value=""></c:set>
                          <c:forEach items="${P_GOODSSPEC_SELECT}" var="specSelect">
                            <c:if test="${goodsSpecValue.spId==specSelect.spId}">
                              <c:set var="currentSelect" value="${specSelect.spvId}"></c:set>
                            </c:if>
                          </c:forEach>
                          <c:if test="${goodsSpec.id==goodsSpecValue.spId}">
                            <li><span nctype="input_checkbox">
              <input type="checkbox" name="gsvslList[${status.index}].spvId" class="sp_val"    nc_type="${goodsSpecValue.name}" value="${goodsSpecValue.id}"
                     <c:forEach items="${currentSelect}" var="select"><c:if test="${select==goodsSpecValue.id}">checked="checked"</c:if></c:forEach>  >
                     </span>
                              <span nctype="pv_name">${goodsSpecValue.name}</span>
                            </li>
                          </c:if>
                        </c:forEach>
                      </c:if>


                      <li data-param="{gc_id:'${P_GOODSCLASS.id}',sp_id:'${goodsSpec.id}',url:'${S_URL}/se/spec/addspec'}">
                        <div nctype="specAdd1"><a nctype="specAdd" class="ncsc-btn" href="javascript:void(0);"><i
                                class="icon-plus"></i>添加${goodsSpec.spName}值</a></div>
                        <div style="display:none;" nctype="specAdd2">
                          <input type="text" maxlength="20" placeholder="${goodsSpec.spName}值名称" class="text w60">
                          <a class="ncsc-btn ncsc-btn-acidblue ml5 mr5" nctype="specAddSubmit"
                             href="javascript:void(0);">确认</a><a class="ncsc-btn ncsc-btn-orange"
                                                                 nctype="specAddCancel"
                                                                 href="javascript:void(0);">取消</a></div>
                      </li>
                    </ul>
                  </dd>
                </dl>
              </c:forEach>
            </c:if>

            <dl style="display:none" class="spec-bg" nc_type="spec_dl">
              <dt>
                库存配置：
              </dt>
              <dd class="spec-dd">
                <table cellspacing="0" cellpadding="0" border="0" class="spec_table">
                  <thead>
                    <tr>
                      <c:forEach items="${P_GOODSSPECS}" var="specList">
                        <th nctype="spec_name_${specList.id}">${specList.spName}</th>
                      </c:forEach>
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
                <input type="text" class="text w60" value="${P_GOODSCOMMON.goodsStorage}" name="goodsStorage">
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
                  <input type="text" class="text" value="${P_GOODSCOMMON.goodsSerial}" name="goodsSerial">
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
                      <c:if test="${empty P_GOODSCOMMON}">
                      <img src="${S_URL}/res/img/default_goods_image_240.gif" nctype="goods_image">
                      </c:if>
                      <c:if test="${!empty P_GOODSCOMMON}">
                        <img src="${S_URL}/att/download/${P_GOODSCOMMON.goodsImage}">
                      </c:if>
                    </div>
                    <p class="hint">
                      上传商品默认主图，如多规格值时将默认使用该图或分规格上传各规格主图；支持jpg、gif、png格式上传或从图片空间中选择，建议使用
                      <font color="red">
                        尺寸800x800像素以上、大小不超过1M的正方形图片
                      </font>
                      ，上传后的图片将会自动保存在图片空间的默认分类中。
                    </p>
                    <input type="hidden" value="${P_GOODSCOMMON.goodsImage}" nctype="goods_image" id="image_path" name="goodsImage">
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
            <c:if test="${!empty P_GOODSBRADNS}">
            <dl>
              <dt>
                商品品牌：
              </dt>
              <dd>
                <select name="brandId">
                  <option value="">请选择...</option>
                  <c:forEach items="${P_GOODSBRADNS}" var="goodsBradns">
                    <option value="${goodsBradns.id}" <c:if test="${P_GOODSCOMMON.brandId==goodsBradns.id}"> selected="selected"</c:if> >${goodsBradns.brandName}</option>
                  </c:forEach>
                </select>
                <input type="hidden" value="${P_GOODSCOMMON.brandName}" name="brandName">
              </dd>
            </dl>
            </c:if>

            <c:if test="${fn:length(P_GOODSATTRIBUTE)>0}">
              <dl>
                <dt>商品属性：</dt>
                <dd>
                  <c:set var="selectAttr" value=""></c:set>
                  <c:forEach items="${P_GOODSATTRIBUTE}" var="goodsAttribute" varStatus="status">
                    <c:forEach items="${P_GOODSATTR_SELECT}" var="sAttr">
                      <c:if test="${goodsAttribute.id==sAttr.id}"><c:set var="selectAttr" value="${sAttr.value}"></c:set></c:if>
                    </c:forEach>
                    <span class="mr30">
          <label class="mr5">${goodsAttribute.attrName}</label>
          <input type="hidden" value="${goodsAttribute.id}" name="attrList[${status.index}].id">
                    <select nc_type="attr_select" name="attrList[${status.index}].value"  >
                      <option nc_type="0" value="">不限</option>
                      <c:forEach items="${goodsAttribute.goodsAttributeValueList}" var="v">
                        <option value="${v.id}" <c:if test="${selectAttr==v.id}">selected="selected"</c:if> >${v.name}</option>
                      </c:forEach>
                    </select>
                    </span>
                  </c:forEach>
                </dd>
              </dl>
            </c:if>

            <dl>
              <dt>
                商品描述：
              </dt>
              <dd>
                <textarea style="width: 100%; height: 480px; visibility: hidden; display: none;"
                name="goodsBody" id="goodsBody">${P_GOODSCOMMON.goodsBody}
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
                    <option value="">请选择</option>
                    <c:forEach items="${P_STOREPLATE_LIST}" var="storePlate">
                      <c:if test="${storePlate.platePosition=='1'}">
                        <option <c:if test="${P_GOODSCOMMON.plateidTop==storePlate.id}"> selected="selected"</c:if> value="${storePlate.id}">${storePlate.plateName}</option>
                      </c:if>
                    </c:forEach>
                  </select>
                </span>
                <span class="mr50">
                  <label>
                    底部版式
                  </label>
                  <select name="plateidBottom">
                    <option value="">请选择</option>
                    <c:forEach items="${P_STOREPLATE_LIST}" var="storePlate">
                      <c:if test="${storePlate.platePosition!='1'}">
                        <option <c:if test="${P_GOODSCOMMON.plateidBottom==storePlate.id}"> selected="selected"</c:if> value="${storePlate.id}">${storePlate.plateName}</option>
                      </c:if>
                    </c:forEach>
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
                    <input type="radio" value="0" <c:if test="${empty P_GOODSCOMMON.transportId}"> checked="checked"</c:if> class="radio" name="goodsFreight"
                    nctype="freight" id="freight_0">
                    <label for="freight_0">
                      固定运费
                    </label>
                    <div nctype="div_freight">
                      <input type="text" name="goodsFreight" value="0.00" nc_type="transport" class="w50 text"
                      id="goodsFreight">
                      <em class="add-on">
                        <i class="icon-renminbi">
                        </i>
                      </em>
                    </div>
                  </li>
                  <li>
                    <input type="radio" value="1" <c:if test="${!empty P_GOODSCOMMON.transportId}"> checked="checked"</c:if> class="radio" name="freight" nctype="freight"
                    id="freight_1">
                    <label for="freight_1">
                      使用运费模板
                    </label>
                    <div style="display: none;" nctype="div_freight">
                      <input type="hidden" name="transport_id" value="" id="transport_id">
                      <input type="hidden" name="transport_title" value="" id="transport_title">
                      <span class="transport-name" id="postageName">
                      </span>
                      <a id="postageButton" class="ncsc-btn" onclick="window.open('url')"
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
                      <input type="radio" <c:if test="${P_GOODSCOMMON.goodsVat!=0}"> checked="checked"</c:if> value="1" name="goodsVat">
                      是
                    </label>
                  </li>
                  <li>
                    <label>
                      <input type="radio" <c:if test="${P_GOODSCOMMON.goodsVat==0}"> checked="checked"</c:if> value="0" name="goodsVat">
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

                <c:if test="${empty P_STC_IDS}">
                <select class="sgcategory" name="sgcateIdList">
                  <option value="">请选择...</option>
                  <c:forEach items="${P_STOREGOODSCLASS_PARENT_LIST}" var="storeGoodsClass">
                    <c:if test="${storeGoodsClass.stcState=='1'}">
                      <option value="${storeGoodsClass.id}">${storeGoodsClass.name}</option>
                      <c:forEach items="${storeGoodsClass.children}" var="children">
                        <c:if test="${children.stcState=='1'}">
                        <option value="${children.id}" >&nbsp;&nbsp;${children.name}</option>
                        </c:if>
                      </c:forEach>
                    </c:if>
                  </c:forEach>
                </select>
                </c:if>

                <c:if test="${!empty P_STC_IDS}">
                  <c:forEach items="${P_STC_IDS}" var="sId">
                    <select class="sgcategory" name="sgcateIdList">
                      <option value="">请选择...</option>
                      <c:forEach items="${P_STOREGOODSCLASS_PARENT_LIST}" var="storeGoodsClass">
                        <c:if test="${storeGoodsClass.stcState=='1'}">
                          <option value="${storeGoodsClass.id}" <c:if test="${storeGoodsClass.id==sId}">selected="selected"</c:if>>${storeGoodsClass.name}</option>
                          <c:forEach items="${storeGoodsClass.children}" var="children">
                            <c:if test="${children.stcState=='1'}">
                              <option value="${children.id}" <c:if test="${children.id==sId}">selected="selected"</c:if>>&nbsp;&nbsp;${children.name}</option>
                            </c:if>
                          </c:forEach>
                        </c:if>
                      </c:forEach>
                    </select>
                  </c:forEach>
                </c:if>
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
                      <input type="radio" <c:if test="${P_GOODSCOMMON.goodsState!=0}"> checked="checked"</c:if> value="1" name="goodsState">
                      立即发布
                    </label>
                  </li>
                  <li>
                    <label>
                      <input type="radio" nctype="auto"   value="0" name="goodsState">
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
                      <input type="radio" <c:if test="${P_GOODSCOMMON.goodsState==0}"> checked="checked"</c:if>  value="0" name="goodsState">
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
                      <input type="radio" <c:if test="${P_GOODSCOMMON.goodsCommend!=0}"> checked="checked"</c:if>  value="1" name="goodsCommend">
                      是
                    </label>
                  </li>
                  <li>
                    <label>
                      <input type="radio" <c:if test="${P_GOODSCOMMON.goodsCommend==0}"> checked="checked"</c:if>  value="0" name="goodsCommend">
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

<script>

  // 按规格存储规格值数据
  var spec_group_checked = [<c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status"><c:if test="${status.last}">''</c:if><c:if test="${!status.last}">'', </c:if> </c:forEach>];
  var str = '';
  var V = new Array();

  var E_SPV = new Array();

  <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">
  var spec_group_checked_${status.index} = new Array();
  </c:forEach>

  $(function () {
    $('dl[nctype="spec_group_dl"]').on('click', 'span[nctype="input_checkbox"] > input[type="checkbox"]', function () {
      into_array();
      goods_stock_set();
    });

    // 提交后不没有填写的价格或库存的库存配置设为默认价格和0
    // 库存配置隐藏式 里面的input加上disable属性
    $('input[type="submit"]').click(function () {
      $('input[data_type="price"]').each(function () {
        if ($(this).val() == '') {
          $(this).val($('input[name="g_price"]').val());
        }
      });
      $('input[data_type="stock"]').each(function () {
        if ($(this).val() == '') {
          $(this).val('0');
        }
      });
      if ($('dl[nc_type="spec_dl"]').css('display') == 'none') {
        $('dl[nc_type="spec_dl"]').find('input').attr('disabled', 'disabled');
      }
    });
    <c:if test="${!empty P_GOODSCOMMON}">
    setTimeout("setArea(${P_GOODSCOMMON.provinceId}, ${P_GOODSCOMMON.cityId})", 1000);
</c:if>
  });

  // 将选中的规格放入数组
  function into_array() {
    <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">

    spec_group_checked_${status.index} = new Array();
    $('dl[nc_type="spec_group_dl_${status.index}"]').find('input[type="checkbox"]:checked').each(function () {
      i = $(this).attr('nc_type');
      v = $(this).val();
      c = null;
      if ($(this).parents('dl:first').attr('spec_img') == 't') {
        c = 1;
      }
      spec_group_checked_${status.index}[spec_group_checked_${status.index}.length] = [v, i, c];
    });

    spec_group_checked[${status.index}] = spec_group_checked_${status.index};

    </c:forEach>
  }

  // 生成库存配置
  function goods_stock_set() {
    //  店铺价格 商品库存改为只读
    $('input[name="goodsStorePrice"]').attr('readonly', 'readonly').css('background', '#E7E7E7 none');
    $('input[name="goodsStorage"]').attr('readonly', 'readonly').css('background', '#E7E7E7 none');
    $('dl[nc_type="spec_dl"]').show();
    str = '<tr>';
    var spvIndex=0;
    <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">
    for (var i_${status.index} = 0; i_${status.index} < spec_group_checked[${status.index}].length; i_${status.index}++) {
      td_${status.index+1} = spec_group_checked[${status.index}][i_${status.index}];
      <c:if test="${status.last}" >
      var tmp_spec_td = new Array();
      <c:forEach items="${P_GOODSSPECS}" var="gs" varStatus="vs">
      tmp_spec_td[${vs.index}] = td_${vs.index+1}[0];
      </c:forEach>
      tmp_spec_td.sort(function (a, b) {
        return a - b
      });
      var spec_bunch = 'i_';
      <c:forEach items="${P_GOODSSPECS}" var="gs" varStatus="vs">
      spec_bunch += td_${vs.index+1}[0];;
      </c:forEach>

      //str += '<input type="hidden" name="spec[' + spec_bunch + '][goods_id]" nc_type="' + spec_bunch + '|id" value="" />';
      <c:forEach items="${P_GOODSSPECS}" var="gs" varStatus="vs">
      if (td_${vs.index+1}[2] != null) {
        str += "<input type='hidden' name='stockList["+spvIndex+"].colorId' value='" + td_${vs.index+1}[0] + "' />"
        str += "<input type='hidden' name='stockList["+spvIndex+"].goodsId' value='" + E_SPV[spec_bunch+'|goodsId'] + "' />";
      }
      str += "<input type='hidden' name='stockList["+spvIndex+"].specName' value='" + td_${vs.index+1}[1] + "' />";
      str += "<td><input type='hidden' name='stockList["+spvIndex+"].specId' value='" + td_${vs.index+1}[0] + "' />" + td_${vs.index+1}[1] + "</td>";
      </c:forEach>

      str += "<td><input class='text price' type='text' name='stockList["+spvIndex+"].price' data_type='price' nc_type='" + spec_bunch + "|price' value='' /><em class='add-on'><i class='icon-renminbi'></i></em></td><td><input class='text stock' type='text' name='stockList["+spvIndex+"].stock' data_type='stock' nc_type='" + spec_bunch + "|stock' value='' /></td><td><input class='text sku' type='text' name='stockList["+spvIndex+"].sku' nc_type='" + spec_bunch + "|sku' value='' /></td></tr>";


      </c:if>
      </c:forEach>
      spvIndex++;
      <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">
      }
      </c:forEach>


    if (str == '<tr>') {
      //  店铺价格 商品库存取消只读
      $('input[name="goodsStorePrice"]').removeAttr('readonly').css('background', '');
      $('input[name="goodsStorage"]').removeAttr('readonly').css('background', '');
      $('dl[nc_type="spec_dl"]').hide();
    } else {
      $('tbody[nc_type="spec_table"]').empty().html(str)
              .find('input[nc_type]').each(function () {
                s = $(this).attr('nc_type');
                try {
                  $(this).val(V[s]);
                } catch (ex) {
                  $(this).val('');
                }
                ;
                if ($(this).attr('data_type') == 'price' && $(this).val() == '') {
                  $(this).val($('input[name="goodsStorePrice"]').val());
                }
                if ($(this).attr('data_type') == 'stock' && $(this).val() == '') {
                  $(this).val('0');
                }
              }).end()
              .find('input[data_type="stock"]').change(function () {
                computeStock();    // 库存计算
              }).end()
              .find('input[data_type="price"]').change(function () {
                computePrice();     // 价格计算
              }).end()
              .find('input[nc_type]').change(function () {
                s = $(this).attr('nc_type');
                V[s] = $(this).val();
              });
    }
  }

  <c:if test="${!empty P_GOODSCOMMON}">
 //  编辑商品时处理JS
    $(function(){
      var E_SP = new Array();

<c:forEach items="${P_GOODSSPEC_SELECT}" var="goodsSpecSelect">
      <c:forEach items="${goodsSpecSelect.spvId}" var="gss">
      E_SP['${gss}'] = '${P_GOODSSPECVALUE_ALL[gss]}';
      </c:forEach>
</c:forEach>

<c:forEach items="${P_GOODSSPECVALUE_SELECT}" var="goodsSpecValueSelect">
      <c:set var="ids" value=""></c:set>
      <c:forEach items="${goodsSpecValueSelect.specId}" var="id">
      <c:set var="ids" value="${ids}${id}"></c:set>
      </c:forEach>

      E_SPV['i_${ids}|price'] = ${goodsSpecValueSelect.price};
      E_SPV['i_${ids}|stock'] = ${goodsSpecValueSelect.stock};
      E_SPV['i_${ids}|sku'] = '${goodsSpecValueSelect.sku}';
      E_SPV['i_${ids}|goodsId'] = '${goodsSpecValueSelect.goodsId}';
</c:forEach>

      V = E_SPV;
      $('dl[nc_type="spec_dl"]').show();

      into_array();	// 将选中的规格放入数组
      str = '<tr>';
      var spvIndex=0;
      <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">
      for (var i_${status.index} = 0; i_${status.index} < spec_group_checked[${status.index}].length; i_${status.index}++) {
        td_${status.index+1} = spec_group_checked[${status.index}][i_${status.index}];
        <c:if test="${status.last}" >
        var tmp_spec_td = new Array();
        <c:forEach items="${P_GOODSSPECS}" var="gs" varStatus="vs">
        tmp_spec_td[${vs.index}] = td_${vs.index+1}[0];
        </c:forEach>
        tmp_spec_td.sort(function (a, b) {
          return a - b
        });
        var spec_bunch = 'i_';
        <c:forEach items="${P_GOODSSPECS}" var="gs" varStatus="vs">
        spec_bunch += td_${vs.index+1}[0];
        </c:forEach>

        <c:forEach items="${P_GOODSSPECS}" var="gs" varStatus="vs">
        if (td_${vs.index+1}[2] != null) {
          str += "<input type='hidden' name='stockList["+spvIndex+"].colorId' value='" + td_${vs.index+1}[0] + "' />";
          str += "<input type='hidden' name='stockList["+spvIndex+"].goodsId' value='" + E_SPV[spec_bunch+'|goodsId'] + "' />";
        }
        str += "<input type='hidden' name='stockList["+spvIndex+"].specName' value='" + td_${vs.index+1}[1] + "' />";
        str += "<td><input type='hidden' name='stockList["+spvIndex+"].specId' value='" + td_${vs.index+1}[0] + "' />" + td_${vs.index+1}[1] + "</td>";
        </c:forEach>

        str += "<td><input class='text price' type='text' name='stockList["+spvIndex+"].price' data_type='price' nc_type='" + spec_bunch + "|price' value='' /><em class='add-on'><i class='icon-renminbi'></i></em></td><td><input class='text stock' type='text' name='stockList["+spvIndex+"].stock' data_type='stock' nc_type='" + spec_bunch + "|stock' value='' /></td><td><input class='text sku' type='text' name='stockList["+spvIndex+"].sku' nc_type='" + spec_bunch + "|sku' value='' /></td></tr>";


        </c:if>
        </c:forEach>
        spvIndex++;
        <c:forEach items="${P_GOODSSPECS}" var="goodsSpec" varStatus="status">
      }
      </c:forEach>
      if(str == '<tr>'){
        $('dl[nc_type="spec_dl"]').hide();
        $('input[name="goodsStorePrice"]').removeAttr('readonly').css('background','');
        $('input[name="goodsStorage"]').removeAttr('readonly').css('background','');
      }else{
        $('tbody[nc_type="spec_table"]').empty().html(str)
                .find('input[nc_type]').each(function(){
                  s = $(this).attr('nc_type');
                  try{$(this).val(E_SPV[s]);}catch(ex){$(this).val('');};
                }).end()
                .find('input[data_type="stock"]').change(function(){
                  computeStock();    // 库存计算
                }).end()
                .find('input[data_type="price"]').change(function(){
                  computePrice();     // 价格计算
                }).end()
                .find('input[type="text"]').change(function(){
                  s = $(this).attr('nc_type');
                  V[s] = $(this).val();
                });
      }

    });
</c:if>

</script>