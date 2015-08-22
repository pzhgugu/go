var GLOBAL = {};

GLOBAL.namespace = function(str) {
	var arr = str.split("."), o = GLOBAL;
	for (i = (arr[0] == "GLOBAL") ? 1 : 0; i < arr.length; i++) {
		o[arr[i]] = o[arr[i]] || {};
		o = o[arr[i]];
	}
}

GLOBAL.namespace("S");
GLOBAL.namespace("P");
GLOBAL.namespace("V");


GLOBAL.globalPath=__uri('global.js');
GLOBAL.commonPath=GLOBAL.globalPath.substr(0,GLOBAL.globalPath.indexOf("scripts/global"));
GLOBAL.IconsPath=GLOBAL.commonPath+"widget/dhtmlxSuite/imgs/";
//权限增删改查
GLOBAL.S.SEC_C=0;
GLOBAL.S.SEC_R=1;
GLOBAL.S.SEC_U=2;
GLOBAL.S.SEC_D=3;

GLOBAL.S.SEC_C_V=true;
GLOBAL.S.SEC_R_V=true;
GLOBAL.S.SEC_U_V=true;
GLOBAL.S.SEC_D_V=true;

GLOBAL.isPermission=function(permission,urlPermissionBit) {
	var temp = 1;
	temp <<= urlPermissionBit;
	temp &= permission;
	if (temp > 0) {
		return true;
	}
	return false;
}

GLOBAL.isPermissionArray=function(permissionArray,urlPermissionBit) {
	if(permissionArray instanceof Array){
		for(var i=0;i<permissionArray.length;i++){ 
			var p=parseInt(permissionArray[i]);
			if(GLOBAL.isPermission(p,urlPermissionBit))
				return true;
		} 

	}else{
		var p=parseInt(permissionArray);
		return GLOBAL.isPermission(p,urlPermissionBit);
	}
	return false;
}