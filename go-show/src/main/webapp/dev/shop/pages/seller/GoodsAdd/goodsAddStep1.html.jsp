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
     
   	<fis:block url="shop:pages/seller/GoodsAdd/setp.html.jsp" /> 
     <!--S 分类选择区域--> 
     <div class="wrapper_search"> 
      <div class="wp_sort"> 
       <div class="wp_data_loading" id="dataLoading"> 
        <div class="data_loading">
         加载中...
        </div> 
       </div> 
       <div class="sort_selector"> 
        <div class="sort_title">
         您常用的商品分类： 
         <div id="commSelect" class="text"> 
          <div>
           请选择
          </div> 
          <div id="commListArea" class="select_list" > 
           <ul> 
           <c:forEach items="${P_GOODSCLASSSTAPLE_LIST}" var="staple">           
            <li data-param="{stapleid:'${staple.id}'}">
            <span nctype="staple_name">${staple.stapleName}</span>
            <a title="删除" nctype="del-comm-cate" href="JavaScript:void(0);">X</a>
            </li> 
            </c:forEach>
            <li <c:if test="${fn:length(P_GOODSCLASSSTAPLE_LIST)>0}">style="display: none;" </c:if> id="select_list_no"><span class="title">您还没有添加过常用的分类</span></li> 
           </ul> 
          </div> 
         </div> 
         <i class="icon-angle-down"></i> 
        </div> 
       </div> 
       <div class="wp_sort_block" id="class_div"> 
        <div class="sort_list"> 
         <div class="wp_category_list"> 
          <div class="category_list ps-container" id="class_div_1"> 
           <ul> 
           <c:forEach items="${P_GOODSCLASS_LIST}" var="c1" >
           <li data-param="{gcid:'${c1.id}',deep:1,tid:0}" nctype="selClass" > 
           	<a href="javascript:void(0)" class="">
           <i class="icon-double-angle-right"></i>${c1.alias}</a></li>             
           </c:forEach>
            </ul> 
           <div class="ps-scrollbar-x" style="left: 0px; bottom: 3px; width: 0px;"></div>
           <div class="ps-scrollbar-y" style="top: 0px; right: 3px; height: 172px;"></div>
          </div> 
         </div> 
        </div> 
        <div class="sort_list"> 
         <div class="wp_category_list blank"> 
          <div class="category_list ps-container" id="class_div_2" style=""> 
           <ul> 
           </ul> 
           <div class="ps-scrollbar-x" style="left: 0px; bottom: 3px; width: 0px;"></div>
           <div class="ps-scrollbar-y" style="top: 0px; right: 3px; height: 0px;"></div>
          </div> 
         </div> 
        </div> 
        <div class="sort_list sort_list_last"> 
         <div class="wp_category_list blank"> 
          <div class="category_list ps-container" id="class_div_3" style=""> 
           <ul> 
           </ul> 
           <div class="ps-scrollbar-x" style="left: 0px; bottom: 3px; width: 0px;"></div>
           <div class="ps-scrollbar-y" style="top: 0px; right: 3px; height: 0px;"></div>
          </div> 
         </div> 
        </div> 
       </div> 
      </div> 
      <div class="alert"> 
       <dl class="hover_tips_cont"> 
        <dt id="commodityspan">
         <span style="color:#F00;">请选择商品类别</span>
        </dt> 
        <dt class="current_sort" style="display: none;" id="commoditydt">
         您当前选择的商品类别是：
        </dt> 
        <dd id="commoditydd"></dd> 
       </dl> 
      </div> 
      <div class="wp_confirm"> 
       <form method="get" action="${S_URL}/se/goods/addstep/two"> 
        <input type="hidden" value="" id="class_id" name="class_id" /> 
        <input type="hidden" value="" id="t_id" name="t_id" /> 
        <div class="bottom tc"> 
         <label class="submit-border">
         <input type="submit" style=" width: 200px;" class="submit" value="下一步，填写商品信息" nctype="buttonNextStep" disabled="disabled" /></label> 
        </div> 
       </form> 
      </div> 
     </div> 
    </div> 
   </div> 
  </div>
  
  
<fis:require id="shop:scripts/common_select.js"/>
<fis:require id="common:widget/jquery/perfect-scrollbar.js"/>
<fis:require id="common:widget/jquery/jquery.mousewheel.js"/>
<fis:require id="shop:scripts/store_goods_add.step1.js"/>
