
var KE;
  KindEditor.ready(function(K) {
        KE = K.create("textarea[name='goodsBody']", {
						items : ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about'],
			//	cssPath : "../../common/widget/kindeditor/themes/default/default.css",
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
		//Ajax提示
	    $('.tip').poshytip({
	    	className: 'tip-yellowsimple',
	    	showTimeout: 1,
	    	alignTo: 'target',
	    	alignX: 'left',
	    	alignY: 'top',
	    	offsetX: 5,
	    	offsetY: -78,
	    	allowTipHover: false
	    });
	    $('.tip2').poshytip({
	    	className: 'tip-yellowsimple',
	    	showTimeout: 1,
	    	alignTo: 'target',
	    	alignX: 'right',
	    	alignY: 'center',
	    	offsetX: 5,
	    	offsetY: 0,
	    	allowTipHover: false
	    });
	})

	$(function(){
	    $.validator.addMethod('checkPrice', function(value,element,param){
	    	_goodsStorePrice = parseFloat($('input[name="goodsStorePrice"]').val());
	        _goodsMarketprice = parseFloat($('input[name="goodsMarketprice"]').val());
	        if (_goodsStorePrice > _goodsMarketprice) {
	            return false;
	        }else {
	            return true;
	        }
	    }, '<i class="icon-exclamation-sign"></i>商品价格不能高于市场价格');
	    $('#goods_form').validate({
	        errorPlacement: function(error, element){
	            $(element).nextAll('span').append(error);
	        },
	                rules : {
	            name : {
	                required    : true,
	                minlength   : 3,
	                maxlength   : 50
	            },
	            adWord : {
	                maxlength   : 50
	            },
	            goodsStorePrice : {
	                required    : true,
	                number      : true,
	                min         : 0.01,
	                max         : 9999999,
	                checkPrice  : true
	            },
	            goodsMarketprice : {
	                required    : true,
	                number      : true,
	                min         : 0.01,
	                max         : 9999999
	            },
	            goodsCostprice : {
	                number      : true,
	                min         : 0.00,
	                max         : 99999999
	            },
	            goodsStorage  : {
	                required    : true,
	                digits      : true,
	                min         : 1,
	                max         : 999999999
	            },
	            goodsImage : {
	                required    : true
	            }
	        },
	        messages : {
	            name  : {
	                required    : '<i class="icon-exclamation-sign"></i>商品名称不能为空',
	                minlength   : '<i class="icon-exclamation-sign"></i>商品标题名称长度至少3个字符，最长50个汉字',
	                maxlength   : '<i class="icon-exclamation-sign"></i>商品标题名称长度至少3个字符，最长50个汉字'
	            },
	            adWord : {
	                maxlength   : '<i class="icon-exclamation-sign"></i>广告不能超过50个字符'
	            },
	            goodsStorePrice : {
	                required    : '<i class="icon-exclamation-sign"></i>商品价格不能为空',
	                number      : '<i class="icon-exclamation-sign"></i>商品价格只能是数字',
	                min         : '<i class="icon-exclamation-sign"></i>商品价格必须是0.01~9999999之间的数字',
	                max         : '<i class="icon-exclamation-sign"></i>商品价格必须是0.01~9999999之间的数字'
	            },
	            goodsMarketprice : {
	                required    : '<i class="icon-exclamation-sign"></i>请填写市场价',
	                number      : '<i class="icon-exclamation-sign"></i>请填写正确的价格',
	                min         : '<i class="icon-exclamation-sign"></i>请填写0.01~9999999之间的数字',
	                max         : '<i class="icon-exclamation-sign"></i>请填写0.01~9999999之间的数字'
	            },
	            goodsCostprice : {
	                number      : '<i class="icon-exclamation-sign"></i>请填写正确的价格',
	                min         : '<i class="icon-exclamation-sign"></i>请填写0.00~9999999之间的数字',
	                max         : '<i class="icon-exclamation-sign"></i>请填写0.00~9999999之间的数字'
	            },
	            goodsStorage : {
	                required    : '<i class="icon-exclamation-sign"></i>商品库存不能为空',
	                digits      : '<i class="icon-exclamation-sign"></i>库存只能填写数字',
	                min         : '<i class="icon-exclamation-sign"></i>商铺库存数量必须为1~999999999之间的整数',
	                max         : '<i class="icon-exclamation-sign"></i>商铺库存数量必须为1~999999999之间的整数'
	            },
	            goodsImage : {
	                required    : '<i class="icon-exclamation-sign"></i>请选择商品主图'
	            }
	        }
	    });
	    });
