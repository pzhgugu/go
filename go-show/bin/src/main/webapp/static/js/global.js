var GLOBAL = {};
GLOBAL.namespace = function(str){
    var arr = str.split("."),o = GLOBAL;
    for (i=(arr[0] == "GLOBAL") ? 1 : 0; i<arr.length; i++) {
        o[arr[i]]=o[arr[i]] || {};
        o=o[arr[i]];
    }
}
GLOBAL.namespace("S");

GLOBAL.getQueryObjct = function(name,operator,value){
	var queryObject = {};
	queryObject.name=name;
	queryObject.operator=operator;
	queryObject.value=value;
	return queryObject;
}

GLOBAL.getBetweenQueryObjct = function(name,operator,begin,end){
	var queryObject = {};
	queryObject.name=name;
	queryObject.operator=operator;
	queryObject.begin=begin;
	queryObject.end=end;
	return queryObject;
}
