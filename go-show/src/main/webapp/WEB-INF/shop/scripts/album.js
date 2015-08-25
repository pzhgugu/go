$(function() {
    //鼠标触及区域li改变class		
    $(".ncsc-album ul li").hover(function() {
        $(this).addClass("hover");
    }, function() {
        $(this).removeClass("hover");
    });
    //凸显鼠标触及区域、其余区域半透明显示
    $(".ncsc-album > ul > li").jfade({
        start_opacity:"1",
        high_opacity:"1",
        low_opacity:".5",
        timing:"200"
    });

    // ajax 上传图片
    var upload_num = 0; // 上传图片成功数量
    $('#fileupload').fileupload({
        dataType: 'json',
        url: SITEURL+'/se/albumpic/image/upload',
        add: function (e,data) {
        	$.each(data.files, function (index, file) {
                $('<div nctype=' + file.name.replace(/\./g, '_') + '><p>'+ file.name +'</p><p class="loading"></p></div>').appendTo('div[nctype="file_loading"]');
            });
        	data.submit();
        },
        done: function (e,data) {
            var param = data.result;
            if (!!param.code && param.code < 0) {
        		alert(param.message);
        		window.location.reload();
        		return;
            }
            $this = $('div[nctype="' + param.origin_file_name.replace(/\./g, '_') + '"]');
            $this.fadeOut(3000, function(){
                $(this).remove();
                if ($('div[nctype="file_loading"]').html() == '') {
                    setTimeout("window.location.reload()", 1000);
                }
            });
            if(param.state == true){
                upload_num++;
                $('div[nctype="file_msg"]').html('<i class="icon-ok-sign">'+'</i>'+'成功上传'+upload_num+'张图片');
                
            } else {
                $this.find('.loading').html(param.message).removeClass('loading');
            }
        }
    });

});

$(function(){
	$("#img_sort").change(function(){
		$('#select_sort').submit();
	}); 
});