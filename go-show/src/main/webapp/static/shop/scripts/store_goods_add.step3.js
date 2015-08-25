$(function(){
    //凸显鼠标触及区域、其余区域半透明显示
    $(".container > div").jfade({
        start_opacity:"1",
        high_opacity:"1",
        low_opacity:".5",
        timing:"200"
    });
    //浮动导航  waypoints.js
    $("#uploadHelp").waypoint(function(event, direction) {
        $(this).parent().toggleClass('sticky', direction === "down");
        event.stopPropagation();
    }); 
    // 关闭相册
    $('a[nctype="close_album"]').click(function(){
        $(this).hide();
        $(this).prev().show();
        $(this).parent().next().html('');
    });
    // 绑定点击事件
    $('div[nctype^="file"]').each(function(){
        if ($(this).prev().find('input[type="hidden"]').val() != '') {
            selectDefaultImage($(this));
        }
    });
});


// 选择默认主图&&删除
function selectDefaultImage($this) {
    // 默认主题
    $this.click(function(){
        $(this).parents('ul:first').find('.show-default').removeClass('selected').find('input').val('0');
        $(this).addClass('selected').find('input').val('1');
    });
    // 删除
    $this.parents('li:first').find('a[nctype="del"]').click(function(){
        $this.unbind('click').removeClass('selected').find('input').val('0');
        $this.prev().find('input').val('').end().find('img').attr('src', DEFAULT_GOODS_IMAGE);
    });
}

// 从图片空间插入主图
function insert_img(name, src, color_id) {
    var $_thumb = $('ul[nctype="ul'+ color_id +'"]').find('.upload-thumb');
    $_thumb.each(function(){
        if ($(this).find('input').val() == '') {
            $(this).find('img').attr('src', src);
            $(this).find('input').val(name);
            selectDefaultImage($(this).next());      // 选择默认主图
            return false;
        }
    });
}