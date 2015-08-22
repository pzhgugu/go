<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/fis" prefix="fis"%>
<!DOCTYPE html>
<fis:html mapDir="/map">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>密码修改</title>
<fis:require id="core:scripts/changePassword.js" />
<fis:styles />
</head>
<body>
	<br />
	<br />
	<br />
	<br />
	<table align="center">
		<tr>
			<td><h3 align="center">修 改 密 码</h3></td>
		</tr>
		<tr>
			<td><form id="changePassword" name="changePassword"
					action="${S_URL}/user/validate" method="POST">
					<table border="0" align="center">
						<tr>
							<td><input type="hidden" name="holdPwd" value="" /></td>
						</tr>
						<tr>
							<td><label>请输入原密码：</label></td>
							<td><input type="password" class="form-control"
								name="oldPwd" /></td>
						</tr>
						<tr>
							<td>&nbsp; <br />
							</td>
						</tr>
						<tr>
							<td><label>请输入新密码：</label></td>
							<td><input type="password" class="form-control"
								name="newPwd" /></td>
						</tr>
						<tr>
							<td>&nbsp; <br />
							</td>
						</tr>
						<tr>
							<td><label>请确认新密码：</label></td>
							<td><input type="password" class="form-control"
								name="newPwdConfirm" /></td>
						</tr>
						<tr>
							<td>&nbsp; <br />
							</td>
						</tr>
						<tr>
							<td colspan="2" align="center"><input type="button"
								value="确认修改" class="btn btn-default" name="submit"
								onClick="isSuccess();" />&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
								type="reset" value="重     置" class="btn btn-default"
								name="reset" /></td>
						</tr>
					</table>
				</form></td>
		</tr>
	</table>
	<fis:scripts />
	

	<script type="text/javascript" language="javascript">
		
        GLOBAL.S.URL="${S_URL}";
        GLOBAL.S.URL_R="${S_URL_R}";
    </script>
</body>
</fis:html>