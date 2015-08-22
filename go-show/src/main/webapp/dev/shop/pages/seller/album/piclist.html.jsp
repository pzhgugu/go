<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>

<div class="ncsc-layout wrapper"> 
   <fis:block url="shop:widget/tpl/seller/layoutLeft.html.jsp" /> 
   <div class="ncsc-layout-right" id="layoutRight"> 
   	<fis:block url="shop:widget/tpl/seller/nav.html.jsp" /> 
   <div id="mainContent" class="main-content">

<div class="tabmenu">
  
<ul class="tab pngFix">
  <li class="normal">
  	<a href="${S_URL}/se/album/cate">我的相册</a>
  </li>
  <li class="active">
  	<a href="${S_URL}/se/albumpic/piclist?category_id=${P_ALBUM_CLASS.id}">图片列表</a>
  </li>
 </ul>


<a id="open_uploader" href="JavaScript:void(0);" class="ncsc-btn ncsc-btn-acidblue">
  <i class="icon-cloud-upload">
  </i>
  上传图片
</a>
<div class="upload-con" id="uploader" style="display: none;">
  <form method="post" action="" id="fileupload" enctype="multipart/form-data">
    <input type="hidden" name="category_id" id="category_id" value="${P_ALBUM_CLASS.id}">
    <div class="upload-con-div">
      选择文件：
      <div class="ncsc-upload-btn">
        <a href="javascript:void(0);">
          <span>
            <input type="file" hidefocus="true" size="1" class="input-file" name="file"
            multiple="multiple" />
          </span>
          <p>
            <i class="icon-upload-alt">
            </i>
            上传图片
          </p>
        </a>
      </div>
    </div>
    <div nctype="file_msg">
    </div>
    <div class="upload-pmgressbar" nctype="file_loading">
    </div>
    <div class="upload-txt">
      <span>支持Jpg、Gif、Png格式，大小不超过1024KB的图片上传；浏览文件时可以按住ctrl或shift键多选。</span>
    </div>
  </form>
</div>
</div>
<div id="pictureFolder" class="ncsc-picture-folder">
  <dl class="ncsc-album-intro">
    <dt class="album-name">
      ${P_ALBUM_CLASS.aclassName}
    </dt>
    <dd class="album-covers">
    <c:choose>    
    	<c:when test="${empty P_ALBUM_CLASS.aclassCover}">   
    	<i class="icon-picture"></i>    	 
    	</c:when> 
		<c:otherwise>
          <img id="aclass_cover" src="${S_URL}/att/download/${P_ALBUM_CLASS.aclassCover}">
        </c:otherwise>
	</c:choose> 
    </dd>
    <dd class="album-info">
      ${P_ALBUM_CLASS.aclassDes}
    </dd>
  </dl>
  
  
  
  <table class="search-form">
    <tbody>
      <tr>
        <th>批量处理</th>
        <td>          
        <a onclick="checkAll()" class="ncsc-btn-mini" href="JavaScript:void(0);"><i class="icon-check"></i>全选</a>
        <a onclick="uncheckAll()" class="ncsc-btn-mini" href="JavaScript:void(0);"><i class="icon-check-sign"></i>取消</a>
        <a onclick="switchAll()" class="ncsc-btn-mini" href="JavaScript:void(0);"><i class="icon-check-empty"></i>反选</a>
        <a onclick="submit_form('del')" class="ncsc-btn-mini" href="JavaScript:void(0);"><i class="icon-trash"></i>删除</a>
        <a id="img_move" class="ncsc-btn-mini" href="JavaScript:void(0);"><i class="icon-move"></i>转移相册</a>

     

          <dd style=" display:none;" id="batchClass">
                        <span>选择相册：</span>
            <select style="width:100px;" id="cid" name="cid">
                             <c:forEach items="${P_ALBUM_CLASSLIST}" var="albums">
              <option style="width:80px;" value="${albums.id}">${albums.aclassName}</option>               
            </c:forEach>
                          </select>
            <input type="button" value="开始转移" onclick="submit_form('move')">
                      </dd>
          
          </td>
        <th>排序方式</th>
        <td class="w100">          <form id="select_sort" name="select_sort">
            <input type="hidden" value="store_album" name="act">
            <input type="hidden" value="album_pic_list" name="op">
            <input type="hidden" value="1" name="id">
            <select id="img_sort" name="sort">
              <option value="0">按上传时间从晚到早</option>
              <option value="1">按上传时间从早到晚</option>
              <option value="2">按图片从大到小</option>
              <option value="3">按图片从小到大</option>
              <option value="4">按图片名降序</option>
              <option value="5">按图片名升序</option>
            </select>
          </form>
          </td>
      </tr>
    </tbody>
  </table>
  
  
 <c:if test="${fn:length(P_ALBUM_PICLIST)>0}">
    <form name="checkboxform" id="checkboxform" method="POST" action="">
    <input type="hidden" value="${P_ALBUM_CLASS.id}" name="category_id" id="category_id">
      <div class="ncsc-picture-list">
        <div class="alert alert-info">
          <strong>
            注：在使用‘替换上传’功能时，请保持图片的扩展名与被替换图片相同。
          </strong>
        </div>
        <ul>
          <c:forEach items="${P_ALBUM_PICLIST}" var="pic">
                <li>
                  <dl>
                    <dt>
                      <div class="picture">
                        <a href="#">
                          <img id="img_${pic.id}" src="${S_URL}/att/download/${pic.apicCover}">
                        </a>
                      </div>
                      <input id="C${pic.id}" name="id[]" value="${pic.id}"
                      type="checkbox" class="checkbox" />
                      <input id="${pic.id}" class="editInput1" readonly onDblClick="$(this).unbind();_focus($(this));"
                      value="${pic.apicName}" title="双击名称可以进行编辑"
                      style="cursor:pointer;">
                      <span onDblClick="_focus($(this).prev());" title="双击名称可以进行编辑">
                        <i class="icon-pencil">
                        </i>
                      </span>
                    </dt>
                    <dd class="date">
                      <p>
                        上传时间：<fmt:formatDate value="${pic.updated}" pattern="yyyy年MM月dd日" />
                      </p>
                      <p>
                        原图尺寸：${pic.apicSpec}
                      </p>
                    </dd>
                    <dd class="buttons">
                      <div class="upload-btn">
                        <a href="javascript:void(0);">
                          <span>
                            <input type="file" name="file" id="file_${pic.id}" value="${pic.id}"
                            class="input-file" size="1" hidefocus="true" nctype="replace_image" />
                          </span>
                          <div class="upload-button">
                            <i class="icon-upload-alt"></i>替换上传 
                            </div>
                          <input id="submit_button" style="display:none" type="button" value=""
                          onClick="submit_form($(this))" />
                        </a>
                      </div>
                      <a href="JavaScript:void(0);" nc_type="dialog" dialog_title="转移相册"
                      uri="${S_URL}/se/albumpic/movepage?id=${pic.id}&category_id=${P_ALBUM_CLASS.id}">
                        <i class="icon-move"></i>转移相册</a>
                      <a href="JavaScript:void(0);" onclick="cover('${pic.id}','${P_ALBUM_CLASS.id}')">
                        <i class="icon-picture"></i>设为封面</a>
                      <a href="javascript:void(0)" onclick="ajax_get_confirm('您确定进行该操作吗?\n注意：使用中的图片也将被删除','se/albumpic/image/delete?id=${pic.id}&category_id=${P_ALBUM_CLASS.id}')">
                        <i class="icon-trash"></i>删除图片</a>
                    </dd>
                  </dl>
                </li>
              </c:forEach>
        </ul>
      </div>
    </form>
     <fis:block url="shop:widget/tpl/pagination.html.jsp" >
       <fis:param name="page" value="P_PAGE_SHOW"/>
       <fis:param name="paginationSize" value="9"/>
       <fis:param name="reqName" value="curpage"/>
       <fis:param name="url" value="${S_URL}/se/albumpic/piclist"/>
     </fis:block>
    </c:if>
 <c:if test="${fn:length(P_ALBUM_PICLIST)==0}">
      <div class="warning-option">
        <i class="icon-warning-sign">
          &nbsp;
        </i>
        <span>
          暂无符合条件的数据记录
        </span>
      </div>
</c:if>
</div>
<fis:require id="common:widget/fileupload/jquery.iframe-transport.js"/>
<fis:require id="common:widget/fileupload/jquery.ui.widget.js"/>
<fis:require id="common:widget/fileupload/jquery.fileupload.js"/>
<fis:require id="shop:scripts/piclist.js"/>

</div>
</div>
