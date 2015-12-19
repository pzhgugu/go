<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib uri="/fis" prefix="fis"%>

<div class="ncsc-layout wrapper">
  <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp" />
  <div class="ncsc-layout-right" id="layoutRight">
    <fis:block url="shop:widget/tpl/seller/nav.html.jsp" />
    <div id="mainContent" class="main-content">


        <c:if test="${P_EDIT!=1}">
            <fis:block url="shop:pages/seller/GoodsAdd/setp.html.jsp" />
        </c:if>
        <c:if test="${P_EDIT==1}">
            <div class="tabmenu">
                <ul class="tab pngFix">
                    <li class="normal"><a href="${S_URL}/se/goods/editgoods?commonid=${S_PARAM.goodsid}">编辑商品</a></li><li class="active"><a href="${S_URL}/se/goods/addstep/editimages?goodsid=${S_PARAM.goodsid}&edit=1">编辑图片</a></li></ul>
            </div>
        </c:if>

<form method="post" id="goods_image" action="${S_URL}/se/goods/addstep/saveimages<c:if test="${P_EDIT==1}">?edit=1</c:if>">
  <input type="hidden" name="form_submit" value="ok">
  <input type="hidden" name="goodsId" value="${S_PARAM.goodsid}">
  <input type="hidden" name="ref_url" value="${S_URL}/se/goods/addstep/one"/>


    <div class="ncsc-form-goods-pic">
        <c:if test="${!empty P_COLOR_LIST}">
        <div class="container">
            <c:forEach items="${P_COLOR_LIST}" var="color" varStatus="status">
            <div class="ncsc-goodspic-list">
                <div class="title">
                    <h3>颜色：${color.name}</h3></div>
                <input type="hidden" value="${color.id}" name="gimList[${status.index}].colorId" />
                <c:set var="image" value="${P_IMAGES_MAP[color.id]}"></c:set>
                <ul nctype="ul${status.index}">
                    <c:forEach begin="0" end="4" step="1" varStatus="i">
                    <li class="ncsc-goodspic-upload">
                        <div class="upload-thumb">
                            <c:choose>
                                <c:when test="${i.index==0}">
                                    <c:if test="${!empty image[0]}">
                                        <img nctype="file_${status.index}${i.index}" src="${S_URL}/att/download/${image[0].goodsImage}" />
                                        <input type="hidden" nctype="file_${status.index}${i.index}" value="${image[0].goodsImage}"
                                               name="gimList[${status.index}].imgList[${i.index}].goodsImage">
                                    </c:if>
                                    <c:if test="${empty image[0]}">
                                    <img nctype="file_${status.index}${i.index}" src="${S_URL}/att/download/${P_GOODSCOMMON.goodsImage}" />
                                    <input type="hidden" nctype="file_${status.index}${i.index}" value="${P_GOODSCOMMON.goodsImage}"
                                           name="gimList[${status.index}].imgList[${i.index}].goodsImage">
                                    </c:if>
                                </c:when>
                                <c:otherwise>
                                    <c:if test="${!empty image[i.index]}">
                                        <img nctype="file_${status.index}${i.index}" src="${S_URL}/att/download/${image[i.index].goodsImage}" />
                                        <input type="hidden" nctype="file_${status.index}${i.index}" value="${image[i.index].goodsImage}"
                                               name="gimList[${status.index}].imgList[${i.index}].goodsImage">
                                    </c:if>
                                    <c:if test="${empty image[i.index]}">
                                        <img nctype="file_${status.index}${i.index}" src="${S_URL}/res/img/default_goods_image_240.gif" />
                                        <input type="hidden" nctype="file_${status.index}${i.index}" value=""
                                               name="gimList[${status.index}].imgList[${i.index}].goodsImage">
                                    </c:if>

                                </c:otherwise>
                            </c:choose>
                        </div>
                        <div class='show-default <c:if test="${i.index==0}">selected</c:if>' nctype="file_${status.index}${i.index}">
                            <p><i class="icon-ok-circle"></i>默认主图
                                <input type="hidden" name="gimList[${status.index}].imgList[${i.index}].isDefault" value='<c:choose><c:when test="${i.index==0}">1</c:when><c:otherwise>0</c:otherwise></c:choose>'>
                            </p><a href="javascript:void(0)" nctype="del" class="del" title="移除">X</a>
                        </div>
                        <div class="show-sort">排序：<input name="gimList[${status.index}].imgList[${i.index}].goodsImageSort" type="text" class="text" value="0" size="1" maxlength="1">
                        </div>
                        <div class="ncsc-upload-btn"><a href="javascript:void(0);"><span><input type="file" hidefocus="true" size="1" class="input-file" name="file_${status.index}${i.index}" id="file_${status.index}${i.index}"></span><p><i class="icon-upload-alt"></i>上传</p>
                        </a></div>

                    </li>
                    </c:forEach>
                </ul>
                <div class="ncsc-select-album">
                    <a class="ncsc-btn" href="${S_URL}/se/goods/addstep/three/images?id=${status.index}" nctype="select-${status.index}"><i class="icon-picture"></i>从图片空间选择</a>
                    <a href="javascript:void(0);" nctype="close_album" class="ncsc-btn ml5" style="display: none;"><i class=" icon-circle-arrow-up"></i>关闭相册</a>
                </div>
                <div nctype="album-${status.index}"></div>
            </div>
            </c:forEach>
            </c:if>

            <c:if test="${empty P_COLOR_LIST}">
            <div class="container">
                <div class="ncsc-goodspic-list">
                    <div class="title">
                        <h3>无颜色</h3></div>
                    <c:set var="image" value="${P_IMAGES_MAP['NULL']}"></c:set>
                    <input type="hidden" value="" name="gimList[0].colorId" />
                    <ul nctype="ul0">
                        <c:forEach begin="0" end="4" step="1" varStatus="i">
                            <li class="ncsc-goodspic-upload">
                                <div class="upload-thumb">
                                    <c:choose>
                                        <c:when test="${i.index==0}">
                                            <c:if test="${!empty image[0]}">
                                                <img nctype="file_0${i.index}" src="${S_URL}/att/download/${image[0].goodsImage}" />
                                                <input type="hidden" nctype="file_0${i.index}" value="${image[0].goodsImage}"
                                                       name="gimList[0].imgList[${i.index}].goodsImage">
                                            </c:if>
                                            <c:if test="${empty image[0]}">
                                                <img nctype="file_0${i.index}" src="${S_URL}/att/download/${P_GOODSCOMMON.goodsImage}" />
                                                <input type="hidden" nctype="file_0${i.index}" value="${P_GOODSCOMMON.goodsImage}"
                                                       name="gimList[0].imgList[${i.index}].goodsImage">
                                            </c:if>
                                        </c:when>
                                        <c:otherwise>
                                            <c:if test="${!empty image[i.index]}">
                                                <img nctype="file_0${i.index}" src="${S_URL}/att/download/${image[i.index].goodsImage}" />
                                                <input type="hidden" nctype="file_0${i.index}" value="${image[i.index].goodsImage}"
                                                       name="gimList[0].imgList[${i.index}].goodsImage">
                                            </c:if>
                                            <c:if test="${empty image[i.index]}">
                                                <img nctype="file_0${i.index}" src="${S_URL}/res/img/default_goods_image_240.gif" />
                                                <input type="hidden" nctype="file_0${i.index}" value=""
                                                       name="gimList[0].imgList[${i.index}].goodsImage">
                                            </c:if>

                                        </c:otherwise>
                                    </c:choose>
                                </div>
                                <div class='show-default <c:if test="${i.index==0}">selected</c:if>' nctype="file_0${i.index}">
                                    <p><i class="icon-ok-circle"></i>默认主图
                                        <input type="hidden" name="gimList[0].imgList[${i.index}].isDefault" value='<c:choose><c:when test="${i.index==0}">1</c:when><c:otherwise>0</c:otherwise></c:choose>'>
                                    </p><a href="javascript:void(0)" nctype="del" class="del" title="移除">X</a>
                                </div>
                                <div class="show-sort">排序：<input name="gimList[0].imgList[${i.index}].goodsImageSort" type="text" class="text" value="0" size="1" maxlength="1">
                                </div>
                                <div class="ncsc-upload-btn"><a href="javascript:void(0);"><span><input type="file" hidefocus="true" size="1" class="input-file" name="file_0${i.index}" id="file_0${i.index}"></span><p><i class="icon-upload-alt"></i>上传</p>
                                </a></div>

                            </li>
                        </c:forEach>
                    </ul>
                    <div class="ncsc-select-album">
                        <a class="ncsc-btn" href="${S_URL}/se/goods/addstep/three/images?id=0" nctype="select-0"><i class="icon-picture"></i>从图片空间选择</a>
                        <a href="javascript:void(0);" nctype="close_album" class="ncsc-btn ml5" style="display: none;"><i class=" icon-circle-arrow-up"></i>关闭相册</a>
                    </div>
                    <div nctype="album-0"></div>
            </div>
            </c:if>


        </div>

      <div class="sidebar">
        <div class="alert alert-info alert-block" id="uploadHelp">
          <div class="faq-img">
          </div>
          <h4>
            上传要求：
          </h4>
          <ul>
            <li>
              1. 请使用jpg\jpeg\png等格式、单张大小不超过单张大小不超过1M的正方形图片
                M的正方形图片。
            </li>
            <li>
              2. 上传图片最大尺寸将被保留为1280像素。
            </li>
            <li>
              3. 每种颜色最多可上传5张图片或从图片空间中选择已有的图片，上传后的图片也将被保存在店铺图片空间中以便其它使用。
            </li>
            <li>
              4. 通过更改排序数字修改商品图片的排列显示顺序。
            </li>
            <li>
              5. 图片质量要清晰，不能虚化，要保证亮度充足。
            </li>
            <li>
              6. 操作完成后请点下一步，否则无法在网站生效。
            </li>
          </ul>
          <h4>
            建议:
          </h4>
          <ul>
            <li>
              1. 主图为白色背景正面图。
            </li>
            <li>
              2. 排序依次为正面图->背面图->侧面图->细节图。
            </li>
          </ul>
        </div>
      </div>
    </div>
      <div class="bottom tc hr32">
        <label class="submit-border">
          <input type="submit" class="submit" value="下一步，确认商品发布"/>
        </label>
      </div>
</form>
<fis:require id="common:widget/jquery/jquery.ajaxContent.pack.js" />
<fis:require id="common:widget/fileupload/jquery.ui.widget.js" />
<fis:require id="common:widget/fileupload/jquery.fileupload.js" />
<fis:require id="common:widget/ajaxfileupload/ajaxfileupload.js" />
<fis:require id="shop:scripts/store_goods_add.step3.js" />
<fis:require id="shop:scripts/page.step3.js" />
<script>
var DEFAULT_GOODS_IMAGE = "${S_URL}/res/img/default_goods_image_240.gif";
$(function(){
    /* ajax打开图片空间 */
    <c:forEach items="${P_COLOR_LIST}" var="color" varStatus="status">
        $('a[nctype="select-${status.index}"]').ajaxContent({
            event:'click', //mouseover
            loaderType:"img",
            loadingMsg:SITEURL + '/res/img/loading.gif',
            target:'div[nctype="album-${status.index}"]'
        }).click(function(){
            $(this).hide();
            $(this).next().show();
        });
    </c:forEach>
});
</script>

	</div>
  </div>
</div>