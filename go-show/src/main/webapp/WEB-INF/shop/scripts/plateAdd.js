var KE;
  KindEditor.ready(function(K) {
        KE = K.create("textarea[name='p_content']", {
						items : ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about'],
						cssPath : "../../common/widget/kindeditor/themes/default/default.css",
						allowImageUpload : false,
						allowFlashUpload : false,
						allowMediaUpload : false,
						allowFileManager : false,
						syncType:"form",
						afterCreate : function() {
							var self = this;
							self.sync();
						},
						afterChange : function() {
							var self = this;
							self.sync();
						},
						afterBlur : function() {
							var self = this;
							self.sync();
						}
        });
			KE.appendHtml = function(id,val) {
				this.html(this.html() + val);
				if (this.isCreated) {
					var cmd = this.cmd;
					cmd.range.selectNodeContents(cmd.doc.body).collapse(false);
					cmd.select();
				}
				return this;
			}
	});

$(function(){
	$('#plate_form').validate({
		submitHandler:function(form){
			ajaxpost('plate_form', '', '', 'onerror');
		},
		rules : {
			p_name : {
				required : true,
				maxlength: 10
			},
			p_content : {
				required : true
			}
		},
		messages : {
			p_name : {
				required : '<i class="icon-exclamation-sign"></i>请填写版式名称',
				maxlength: '<i class="icon-exclamation-sign"></i>版式名称不能超过10个字符'
			},
			p_content : {
				required : '<i class="icon-exclamation-sign"></i>请填写版式内容'
			}
		}
	});

	// 版式内容使用
	$('a[nctype="show_desc"]').ajaxContent({
		event:'click', //mouseover
		loaderType:"img",
		loadingMsg:SHOP_TEMPLATES_URL+"/images/loading.gif",
		target:'#des_demo'
	}).click(function(){
		$(this).hide();
		$('a[nctype="del_desc"]').show();
	});
	$('a[nctype="del_desc"]').click(function(){
		$(this).hide();
		$('a[nctype="show_desc"]').show();
		$('#des_demo').html('');
	});
});
/* 插入编辑器 */
function insert_editor(file_path) {
	KE.appendHtml('p_content', '<img src="'+ file_path + '">');
}