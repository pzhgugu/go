<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<fis:html mapDir="/map">

  <div class="right-content">

    <div class="path">
      <div><a href="${S_URL}/shop/member/index">我的商城</a><span>»</span>
        <a href="${S_URL}/shop/member/set">个人资料</a><span>»</span>基本信息</div>
    </div>

    <div class="main">
      <style type="text/css">
        dl dd span {
          display: inline-block;
        }
      </style>

      <div class="wrap">
        <fis:block url="shop:pages/client/member/${P_STYLE}/tabmenu.html.jsp" />
        <div class="ncu-form-style">
          <form action="shop/member/set_save?for=xml" id="profile_form" method="post">
            <input type="hidden" value="ok" name="form_submit">
            <input type="hidden" value="" name="old_member_avatar">
            <dl>
              <dt>用户名称：</dt>
              <dd><span class="w340">${P_MEMBER.alias}</span><span>&nbsp;&nbsp;隐私设置</span></dd>
            </dl>
            <dl>
              <dt>电子邮件：</dt>
              <dd><span class="w340">${P_MEMBER.memberEmail}</span><span>
          <select name="privacyModel.email">
            <option <c:if test="${P_PRIVACY.email==0}">selected="selected"</c:if> value="0">公开</option>
            <option <c:if test="${P_PRIVACY.email==1}">selected="selected"</c:if>  value="1">好友可见</option>
            <option <c:if test="${P_PRIVACY.email==2}">selected="selected"</c:if>  value="2">保密</option>
          </select>
          </span></dd>
            </dl>
            <dl>
              <dt>真实姓名：</dt>
              <dd><span class="w340">
          <input type="text" value="${P_MEMBER.memberTrueName}" name="memberTrueName" maxlength="20" class="text">
          </span><span>
          <select name="privacyModel.truename">
            <option <c:if test="${P_PRIVACY.truename==0}">selected="selected"</c:if>  value="0">公开</option>
            <option <c:if test="${P_PRIVACY.truename==1}">selected="selected"</c:if> value="1">好友可见</option>
            <option <c:if test="${P_PRIVACY.truename==2}">selected="selected"</c:if> value="2">保密</option>
          </select>
          </span></dd>
            </dl>
            <dl>
              <dt>性别：</dt>
              <dd><span class="w340">
          <label>
            <input type="radio" <c:if test="${P_MEMBER.memberSex==3}" > checked="checked" </c:if> value="3" name="memberSex">
            保密</label>
          &nbsp;&nbsp;
          <label>
            <input type="radio" <c:if test="${P_MEMBER.memberSex==2}" > checked="checked" </c:if>  value="2" name="memberSex">
            女</label>
          &nbsp;&nbsp;
          <label>
            <input type="radio" <c:if test="${P_MEMBER.memberSex==1}" > checked="checked" </c:if>  value="1" name="memberSex">
            男</label>
          </span><span>
          <select name="privacyModel.sex">
            <option <c:if test="${P_PRIVACY.sex==0}">selected="selected"</c:if> value="0">公开</option>
            <option <c:if test="${P_PRIVACY.sex==1}">selected="selected"</c:if> value="1">好友可见</option>
            <option <c:if test="${P_PRIVACY.sex==2}">selected="selected"</c:if> value="2">保密</option>
          </select>
          </span></dd>
            </dl>
            <dl>
              <dt>生日：</dt>
              <dd><span class="w340">
          <input type="text" value="<fmt:formatDate value='${P_MEMBER.memberBirthday}' pattern='yyyy-MM-dd'/>" id="memberBirthday" maxlength="10" name="memberBirthday" class="text" readonly="readonly">
          </span><span>
          <select name="privacyModel.birthday">
            <option <c:if test="${P_PRIVACY.birthday==0}">selected="selected"</c:if> value="0">公开</option>
            <option <c:if test="${P_PRIVACY.birthday==1}">selected="selected"</c:if> value="1">好友可见</option>$
            <option <c:if test="${P_PRIVACY.birthday==2}">selected="selected"</c:if> value="2">保密</option>
          </select>
          </span></dd>
            </dl>
            <dl>
              <dt class="required">所在地区：</dt>
              <dd><span class="w340" id="region">
          <input type="hidden" id="province_id" name="memberProvinceId" value="${P_MEMBER.memberProvinceId}">
          <input type="hidden" id="city_id" name="memberCityId" value="${P_MEMBER.memberCityId}">
          <input type="hidden" class="area_ids" id="area_id" name="memberAreaId" value="${P_MEMBER.memberAreaId}">
          <input type="hidden" class="area_names" id="area_info" name="memberAreaInfo" value="${P_MEMBER.memberAreaInfo}">
              <c:choose>
                <c:when test="${empty P_MEMBER.memberAreaId}"><select></select></c:when>
                <c:otherwise>
                  <span>${P_MEMBER.memberAreaInfo}</span>
                  <input type="button" value="编辑" class="edit_region" />
                  <select style="display:none;">
                  </select>
                </c:otherwise>
              </c:choose>


              </span><span>
          <select name="privacyModel.area">
            <option <c:if test="${P_PRIVACY.area==0}">selected="selected"</c:if> value="0">公开</option>
            <option <c:if test="${P_PRIVACY.area==1}">selected="selected"</c:if> value="1">好友可见</option>
            <option <c:if test="${P_PRIVACY.area==2}">selected="selected"</c:if> value="2">保密</option>
          </select>
          </span></dd>
            </dl>
            <dl>
              <dt>QQ：</dt>
              <dd><span class="w340">
          <input type="text" value="${P_MEMBER.memberQq}" name="memberQq" maxlength="30" class="text">
          </span><span>
          <select name="privacyModel.qq">
            <option <c:if test="${P_PRIVACY.qq==0}">selected="selected"</c:if> value="0">公开</option>
            <option <c:if test="${P_PRIVACY.qq==1}">selected="selected"</c:if> value="1">好友可见</option>
            <option <c:if test="${P_PRIVACY.qq==2}">selected="selected"</c:if> value="2">保密</option>
          </select>
          </span> </dd>
            </dl>
            <dl>
              <dt>阿里旺旺：</dt>
              <dd><span class="w340">
          <input type="text" value="${P_MEMBER.memberWw}" id="member_ww" maxlength="50" class="text" name="memberWw">
          </span><span>
          <select name="privacyModel.ww">
            <option <c:if test="${P_PRIVACY.ww==0}">selected="selected"</c:if> value="0">公开</option>
            <option <c:if test="${P_PRIVACY.ww==1}">selected="selected"</c:if> value="1">好友可见</option>
            <option <c:if test="${P_PRIVACY.ww==2}">selected="selected"</c:if> value="2">保密</option>
          </select>
          </span></dd>
            </dl>
            <dl class="bottom">
              <dt>&nbsp;</dt>
              <dd>
                <input type="submit" value="保存修改" class="submit">
              </dd>
            </dl>
          </form>
        </div>
      </div>
     <script type="text/javascript">
        //注册表单验证
        $(function(){
          regionInit("region");
          $('#memberBirthday').datepicker({dateFormat: 'yy-mm-dd'});
          $('#profile_form').validate({
            submitHandler:function(form){
              if ($('select[class="valid"]').eq(0).val()>0) $('#province_id').val($('select[class="valid"]').eq(0).val());
              if ($('select[class="valid"]').eq(1).val()>0) $('#city_id').val($('select[class="valid"]').eq(1).val());
              ajaxpost('profile_form', '', '', 'onerror')
            },
            rules : {
              memberTrueName : {
                minlength : 2,
                maxlength : 20
              },
              memberQq : {
                digits  : true,
                minlength : 5,
                maxlength : 12
              }
            },
            messages : {
              memberTrueName : {
                minlength : '姓名长度大于等于2位小于等于20位',
                maxlength : '姓名长度大于等于2位小于等于20位'
              },
              memberQq  : {
                digits    : '请填入正确的QQ号码',
                minlength : '请填入正确的QQ号码',
                maxlength : '请填入正确的QQ号码'
              }
            }
          });
        });
      </script>

  </div>
</fis:html>