<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>

<div class="eject_con">
  <div id="warning"></div>
  <form id="category_form" method="post" target="_parent" action="se/album/updateclass?for=xml">
    <input type="hidden" name="id" value="${P_ALBUMCLASS.id}" />
    <dl>
      <dt><i class="required">*</i>相册名称</dt>
      <dd>
        <input class="w300 text" type="text" name="aclassName" id="aclassName" value="${P_ALBUMCLASS.aclassName}" />
      </dd>
    </dl>
    <dl>
      <dt>描述</dt>
      <dd>
        <textarea rows="3" class="textarea w300" name="aclassDes" id="aclassDes">${P_ALBUMCLASS.aclassDes}</textarea>
      </dd>
    </dl>
    <dl>
      <dt>排序</dt>
      <dd>
        <input type="text" class="text w50" name="aclassSort" id="aclassSort" value="${P_ALBUMCLASS.aclassSort}" />
      </dd>
    </dl>
    <div class="bottom">
      <label class="submit-border">
        <input type="submit" class="submit" value="提交" />
      </label>
    </div>
  </form>
</div>
<script type="text/javascript">
$(function(){
    $('#category_form').validate({
        errorLabelContainer: $('#warning'),
        invalidHandler: function(form, validator) {
               $('#warning').show();
        },
        submitHandler:function(form){
            ajaxpost('category_form', '', '', 'onerror') 
        },
        rules : {
        	aclassName : {
                required : true,
                maxlength: 20
            },
            aclassDes : {
            	maxlength	: 100
            },
            aclassSort : {
            	digits   : true
            }
        },
        messages : {
        	aclassName : {
                required : '册名称不能为空',
                maxlength	: '相册名称不能超过20个字符'
            },
            aclassDes : {
            	maxlength	: '描述名称不能超过100个字符'
            },
            aclassSort  : {
            	digits   : '排序只能填写数字'
            }
        }
    });
});
</script>