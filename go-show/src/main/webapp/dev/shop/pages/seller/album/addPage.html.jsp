<%@page language="Java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt'%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %> 
<%@ taglib uri="/fis" prefix="fis"%>
<c:choose>
<c:when test="${P_CLASS_COUNT<20}">

<div class="eject_con">
  <div id="warning"></div>
  <form id="category_form" method="post" target="_parent" action="se/album/saveclass?for=xml">
    <input type="hidden" name="form_submit" value="ok" />
    <dl>
      <dt><i class="required">*</i>相册名称</dt>
      <dd>
        <input class="w300 text" type="text" name="aclassName" id="aclassName" value="" />
      </dd>
    </dl>
    <dl>
      <dt>描述</dt>
      <dd>
        <textarea class="w300 textarea" rows="3" name="aclassDes" id="aclassDes"></textarea>
      </dd>
    </dl>
    <dl>
      <dt>排序</dt>
      <dd>
        <input class="w50 text" type="text" name="aclassSort" id="aclassSort" value="" />
      </dd>
    </dl>
    <div class="bottom">
      <label class="submit-border">
        <input type="submit" class="submit" value="提交" />
      </label>
    </div>
  </form>
</div>
</c:when> 
<c:otherwise>
<div class="warning-option"><i class="icon-warning-sign">&nbsp;</i><span>相册最大只能建20个</span></div>
</c:otherwise>
</c:choose> 

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
                maxlength	: 20,
                remote   : {
                    url :'${S_URL}/se/album/checkname',
                    type:'get',
                    data:{
                        ac_name : function(){
                            return $('#aclassName').val();
                        }
                    }
                }
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
                required : '相册名称不能为空',
                maxlength	: '相册名称不能超过20个字符',
                remote		: '相册名称已存在'
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