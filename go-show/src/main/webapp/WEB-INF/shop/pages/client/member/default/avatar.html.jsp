<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<fis:html mapDir="/map">
  <div class="right-content">

  <div class="path">
    <div><a href="${S_URL}/shop/member/index">我的商城</a><span>»</span>
      <a href="${S_URL}/shop/member/set">个人资料</a><span>»</span>修改电子邮件</div>
  </div>

    <div class="main">
      <div class="wrap">
        <fis:block url="shop:pages/client/member/${P_STYLE}/tabmenu.html.jsp" />
        <form method="post" id="form_avaster" enctype="multipart/form-data" action="${S_URL}/shop/member/avatar_save">
          <div class="ncu-form-style">
            <dl>
              <dt>头像预览：</dt>
              <dd>
                <div class="member-avatar-thumb"><img nc_type="avatar" alt="" <c:choose><c:when test="${empty P_MEMBER.memberAvatar}">src="${S_URL}/res/img/default_user_portrait.gif"</c:when><c:otherwise>src="${S_URL}/att/download/${P_MEMBER.memberAvatar}"</c:otherwise></c:choose>>></div>
                <p class="hint mt5">完善个人信息资料，上传头像图片有助于您结识更多的朋友。<br><span style="color:orange;">头像默认尺寸为120x120像素，请根据系统操作提示进行裁剪并生效。</span></p>
              </dd>
            </dl>
            <dl>
              <dt>更换头像：</dt>
              <dd>
                <div class="upload-btn"> <a href="javascript:void(0);"> <span>
            <input type="file" maxlength="0" hidefocus="true" size="1" class="file" file_id="0" multiple="" id="pic" name="pic">
            </span>
                  <div class="upload-button">图片上传</div>
                  <input type="button" onclick="submit_form($(this))" value="&nbsp;" style="display:none" id="submit_button">
                </a></div>
              </dd>
            </dl>
          </div>
        </form>
      </div>

      <script type="text/javascript">
        $(function(){
          $('#pic').change(function(){
            var filepatd=$(this).val();
            var extStart=filepatd.lastIndexOf(".");
            var ext=filepatd.substring(extStart,filepatd.lengtd).toUpperCase();
            if(ext!=".PNG"&&ext!=".GIF"&&ext!=".JPG"&&ext!=".JPEG"){
              alert("file type error");
              $(this).attr('value','');
              return false;
            }
            if ($(this).val() == '') return false;
            $("#form_avaster").submit();
          });
        });
      </script>
    </div>

  </div>
</fis:html>
