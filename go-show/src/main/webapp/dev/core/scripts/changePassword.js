/**
 * @require common:widget/jquery/jquery.form.js
 * @require core:styles/style.css
 * @require common:styles/bootstrap.css
 */

function checkForm(form) {
	if (form.oldPwd.value == "") {
		alert("请输入的原密码!");
		form.oldPwd.focus();
		return false;
	}
	if (form.newPwd.value == "") {
		alert("请输入的新密码!");
		form.newPwd.focus();
		return false;
	}
	if (form.newPwd.value.length > 25) {
		alert("新密码长度不能太长了!");
		form.newPwd.focus();
		return false;
	}
	if (form.newPwdConfirm.value == "") {
		alert("请确认新密码!");
		form.newPwdConfirm.focus();
		return false;
	}
	if (form.newPwd.value != form.newPwdConfirm.value) {
		alert("您两次输入的新密码不一致，请重新输入!");
		form.newPwd.value = "";
		form.newPwdConfirm.value = "";
		form.newPwd.focus();
		return false;
	}
	$("#changePassword").attr("action", GLOBAL.S.URL+"/user/validate");
	$("#changePassword").ajaxForm({
		dataType : "json",
		error : function(request) {
			alert(request.responseText)
		},
		success : processJson
	});
	function processJson(response) {
		alert("密码修改成功！");
	}
	$("#changePassword").submit();
}

function isSuccess() {

	$("#changePassword").attr("action", GLOBAL.S.URL+"/user/validate");
	$("#changePassword").ajaxForm({
		dataType : "json",
		error : function(request) {
			alert(request.responseText)
		},
		success : processJson
	});
	function processJson(response) {
		if (!!response.code && response.code < 0) {
			alert(response.message);
		} else {
			aler("密码修改成功！");
		}
	}
	$("#changePassword").submit();
}
