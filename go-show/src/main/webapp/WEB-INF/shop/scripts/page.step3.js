$(function() {
	/* ajax打开图片空间 */
	$('a[nctype="select-0"]').ajaxContent({
		event : 'click', //mouseover
		loaderType : "img",
		loadingMsg : SITEURL + '/res/img/loading.gif',
		target : 'div[nctype="album-0"]'
	}).click(function() {
		$(this).hide();
		$(this).next().show();
	});
});