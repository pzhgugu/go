<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>


  <div class="ncsc-layout wrapper"> 
   <fis:block url="shop:widget/tpl/seller/layoutLeftGoods.html.jsp" /> 
   <div class="ncsc-layout-right" id="layoutRight"> 
	<fis:block url="shop:widget/tpl/seller/nav.html.jsp" /> 
	
	<div id="mainContent" class="main-content">
	
<div class="tabmenu">
  <ul class="tab pngFix">
    <li class="active">
      <a href="#">
        出售中的商品
      </a>
    </li>
  </ul>
  <a title="发布新商品" class="ncsc-btn ncsc-btn-green" href="${S_URL}/se/goods/addstep/one">
    发布新商品
  </a>
</div>

<!--搜索开始 -->
<form action="#" method="get">
  <table class="search-form">
    <input type="hidden" value="store_goods_online" name="act">
    <input type="hidden" value="index" name="op">
    <tbody>
      <tr>
        <td>
          &nbsp;
        </td>
        <th>
          本店分类
        </th>
        <td class="w160">
          <select class="w150" name="stc_id">
            <option value="0">
              请选择...
            </option>
          </select>
        </td>
        <th>
          <select name="search_type">
            <option selected="selected" value="0">
              商品名称
            </option>
            <option value="1">
              商家货号
            </option>
            <option value="2">
              平台货号
            </option>
          </select>
        </th>
        <td class="w160">
          <input type="text" value="" name="keyword" class="text w150">
        </td>
        <td class="tc w70">
          <label class="submit-border">
            <input type="submit" value="搜索" class="submit">
          </label>
        </td>
      </tr>
    </tbody>
  </table>
</form>
<!--搜索结束 -->


<!--表格开始 -->
<table class="ncsc-table-style">
  <thead>
    <tr nc_type="table_header">
      <th class="w30">
        &nbsp;
      </th>
      <th class="w50">
        &nbsp;
      </th>
      <th inputwidth="230px" checker="check_required" column="goods_name" coltype="editable">
        商品名称
      </th>
      <th class="w100">
        价格
      </th>
      <th class="w100">
        库存
      </th>
      <th class="w100">
        发布时间
      </th>
      <th class="w100">
        操作
      </th>
    </tr>
    <tr>
      <td class="tc">
        <input type="checkbox" class="checkall" id="all">
      </td>
      <td colspan="20">
        <label for="all">
          全选
        </label>
        <a confirm="您确定要删除吗?" name="commonid" uri="http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=drop_goods"
        nc_type="batchbutton" class="ncsc-btn-mini" href="javascript:void(0);">
          <i class="icon-trash">
          </i>
          删除
        </a>
        <a name="commonid" uri="http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=goods_unshow"
        nc_type="batchbutton" class="ncsc-btn-mini" href="javascript:void(0);">
          <i class="icon-level-down">
          </i>
          下架
        </a>
        <a data-param="{url:'http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=edit_jingle', sign:'jingle'}"
        nctype="batch" class="ncsc-btn-mini" href="javascript:void(0);">
          <i>
          </i>
          设置广告词
        </a>
        <a data-param="{url:'http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=edit_plate', sign:'plate'}"
        nctype="batch" class="ncsc-btn-mini" href="javascript:void(0);">
          <i>
          </i>
          设置关联版式
        </a>
      </td>
    </tr>
  </thead>
  <tbody>
    <tr>
      <th class="tc">
        <input type="checkbox" value="100089" class="checkitem tc">
      </th>
      <th colspan="20">
        平台货号：100089
      </th>
    </tr>
    <tr>
      <td class="trigger">
        <i title="点击展开查看此商品全部规格；规格值过多时请横向拖动区域内的滚动条进行浏览。" data-comminid="100089"
        nctype="ajaxGoodsList" class="tip icon-plus-sign">
        </i>
      </td>
      <td>
        <div class="pic-thumb">
          <a target="_blank" href="http://localhost/shopnc/shop/index.php?act=goods&amp;op=index&amp;goods_id=235">
            <img src="http://localhost/shopnc/data/upload/shop/store/goods/1/1_04928652283443171_60.jpg">
          </a>
        </div>
      </td>
      <td class="tl">
        <dl class="goods-name">
          <dt>
            <span>
              荐
            </span>
            <a target="_blank" href="http://localhost/shopnc/shop/index.php?act=goods&amp;op=index&amp;goods_id=235">
              3333
            </a>
          </dt>
          <dd>
            服饰鞋帽 &gt;童装 &gt;套装
          </dd>
          <dd>
            商家货号：
          </dd>
        </dl>
      </td>
      <td>
        <span>
          ¥3.00
        </span>
      </td>
      <td>
        <span style="color:red;">
          3件
        </span>
      </td>
      <td class="goods-time">
        2015-08-17
      </td>
      <td class="nscs-table-handle">
        <span>
          <a class="btn-blue" href="http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=edit_goods&amp;commonid=100089">
            <i class="icon-edit">
            </i>
            <p>
              编辑
            </p>
          </a>
        </span>
        <span>
          <a class="btn-red" onclick="ajax_get_confirm('您确定要删除吗?', 'http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=drop_goods&amp;commonid=100089');"
          href="javascript:void(0);">
            <i class="icon-trash">
            </i>
            <p>
              删除
            </p>
          </a>
        </span>
      </td>
    </tr>
    <tr style="display:none;">
      <td colspan="20">
        <div class="ncsc-goods-sku ps-container">
        </div>
      </td>
    </tr>
  </tbody>
  <tfoot>
    <tr>
      <th class="tc">
        <input type="checkbox" class="checkall" id="all2">
      </th>
      <th colspan="10">
        <label for="all2">
          全选
        </label>
        <a class="ncsc-btn-mini" confirm="您确定要删除吗?" name="commonid" uri="http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=drop_goods"
        nc_type="batchbutton" href="javascript:void(0);">
          <i class="icon-trash">
          </i>
          删除
        </a>
        <a class="ncsc-btn-mini" name="commonid" uri="http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=goods_unshow"
        nc_type="batchbutton" href="javascript:void(0);">
          <i class="icon-level-down">
          </i>
          下架
        </a>
        <a data-param="{url:'http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=edit_jingle', sign:'jingle'}"
        nctype="batch" class="ncsc-btn-mini" href="javascript:void(0);">
          <i>
          </i>
          设置广告词
        </a>
        <a data-param="{url:'http://localhost/shopnc/shop/index.php?act=store_goods_online&amp;op=edit_plate', sign:'plate'}"
        nctype="batch" class="ncsc-btn-mini" href="javascript:void(0);">
          <i>
          </i>
          设置关联版式
        </a>
      </th>
    </tr>
    <tr>
      <td colspan="20">
        <div class="pagination">
          <ul>
            <li>
              <span>
                首页
              </span>
            </li>
            <li>
              <span>
                上一页
              </span>
            </li>
            <li>
              <span class="currentpage">
                1
              </span>
            </li>
            <li>
              <span>
                下一页
              </span>
            </li>
            <li>
              <span>
                末页
              </span>
            </li>
          </ul>
        </div>
      </td>
    </tr>
  </tfoot>
</table>
<!--表格结束 -->



	</div>
	
	
   </div> 
  </div>
  
  

