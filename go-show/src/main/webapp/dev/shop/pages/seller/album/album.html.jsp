<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>

<div class="ncsc-layout wrapper"> 
   <fis:block url="shop:widget/tpl/seller/layoutLeft.html.jsp" /> 
   <div class="ncsc-layout-right" id="layoutRight"> 
   	<fis:block url="shop:widget/tpl/seller/nav.html.jsp" /> 
   <div id="mainContent" class="main-content">
  <div class="tabmenu">
    <ul class="tab pngFix">
      <li class="active">
        <a href="${S_URL}/se/album/cate">
          我的相册
        </a>
      </li>
    </ul>
    <a style="right: 100px;" class="ncsc-btn ncsc-btn-green" dialog_title="创建相册"
    nc_type="dialog" uri="${S_URL}/se/album/addpage">
      <i class="icon-folder-open ">
      </i>
      创建相册
    </a>
    <a class="ncsc-btn ncsc-btn-acidblue" href="JavaScript:void(0);" id="open_uploader">
      <i class="icon-cloud-upload">
      </i>
      上传图片
    </a>
    <div style="display: none;" id="uploader" class="upload-con">
      <form enctype="multipart/form-data" id="fileupload" action="" method="post">
        <div class="upload-con-div">
          选择相册：
          <select class="select w80" id="category_id" name="category_id">
           <c:forEach items="${P_ALBUM_CLASS}" var="album">
            <option class="w80" value="${album.id}"> ${album.aclassName}</option>
            </c:forEach>
          </select>
        </div>
        <div class="upload-con-div">
          选择文件：
          <div class="ncsc-upload-btn">
            <a href="javascript:void(0);">
              <span>
                <input type="file" multiple="multiple" name="file" class="input-file"
                size="1" hidefocus="true">
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
        <div nctype="file_loading" class="upload-pmgressbar">
        </div>
        <div class="upload-txt">
          <span>
            支持Jpg、Gif、Png格式，大小不超过1024KB的图片上传；浏览文件时可以按住ctrl或shift键多选。
          </span>
        </div>
      </form>
    </div>
  </div>
  <div class="ncsc-picture-folder" id="pictureIndex">
    <table class="search-form">
      <tbody>
        <tr>
          <td>
            &nbsp;
          </td>
          <th>
            排序方式
          </th>
          <td class="w100">
            <form class="sortord" id="select_sort" name="select_sort">
              <input type="hidden" value="store_album" name="act">
              <input type="hidden" value="album_cate" name="op">
              <select id="img_sort" name="sort">
                <option selected="" value="4">
                  按排序从大到小
                </option>
                <option value="5">
                  按排序从小到大
                </option>
                <option value="0">
                  按创建时间从晚到早
                </option>
                <option value="1">
                  按创建时间从早到晚
                </option>
                <option value="2">
                  按相册名降序
                </option>
                <option value="3">
                  按相册名升序
                </option>
              </select>
            </form>
          </td>
        </tr>
      </tbody>
    </table>
    <div class="ncsc-album">
      <ul>
      <c:forEach items="${P_ALBUM_CLASS}" var="album">
      
     
        <li class="hidden" style="opacity: 1;">
          <dl>
            <dt>
              <div class="covers">
                <a href="${S_URL}/se/albumpic/piclist?category_id=${album.id}">
                  
                  <c:choose>    
    	<c:when test="${empty album.aclassCover}">   
    	<i class="icon-camera-retro"></i>   	 
    	</c:when> 
		<c:otherwise>
          <img id="aclass_cover" src="${S_URL}/att/download/${album.aclassCover}">
        </c:otherwise>
	</c:choose> 
                </a>
              </div>
              <h3 class="title">
                <a href="2">
                  ${album.aclassName}
                </a>
              </h3>
            </dt>
            <dd class="date">
              共${album.picNumber}张
            </dd>
            <dd class="buttons">
              <span uri="${S_URL}/se/album/updatepage?id=${album.id}" dialog_width="480"
              dialog_id="album_1" dialog_title="编辑相册" nc_type="dialog">
                <a href="JavaScript:void(0);">
                  <i class="icon-pencil">
                  </i>
                  编辑
                </a>
              </span>
              <c:if test="${album.isDefault!=1}">
              <a onclick="ajax_get_confirm('删除相册?\n注意：相册内的图片将转移到默认相册', 'se/album/delclass?id=${album.id}');" href="javascript:void(0)"><i class="icon-remove-sign"></i>删除</a>
             </c:if>
            </dd>
          </dl>
        </li>
         </c:forEach>
      </ul>
    </div>
  </div>
</div>
   
   </div>
</div>

<fis:require id="common:widget/fileupload/jquery.iframe-transport.js"/>
<fis:require id="common:widget/fileupload/jquery.ui.widget.js"/>
<fis:require id="common:widget/fileupload/jquery.fileupload.js"/>
<fis:require id="shop:scripts/album.js"/>