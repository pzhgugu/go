var KE;
  KindEditor.ready(function(K) {
        KE = K.create("textarea[name='g_body']", {
						items : ['source', '|', 'fullscreen', 'undo', 'redo', 'print', 'cut', 'copy', 'paste',
            'plainpaste', 'wordpaste', '|', 'justifyleft', 'justifycenter', 'justifyright',
            'justifyfull', 'insertorderedlist', 'insertunorderedlist', 'indent', 'outdent', 'subscript',
            'superscript', '|', 'selectall', 'clearhtml','quickformat','|',
            'formatblock', 'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold',
            'italic', 'underline', 'strikethrough', 'lineheight', 'removeformat', '|', 'image', 'flash', 'media', 'table', 'hr', 'emoticons', 'link', 'unlink', '|', 'about'],
						cssPath : "http://localhost/shopnc/data/resource/kindeditor/themes/default/default.css",
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
	// 按规格存储规格值数据
	var spec_group_checked = [];
	var str = '';
	var V = new Array();


	$(function(){
		$('dl[nctype="spec_group_dl"]').on('click', 'span[nctype="input_checkbox"] > input[type="checkbox"]',function(){
			into_array();
			goods_stock_set();
		});

		// 提交后不没有填写的价格或库存的库存配置设为默认价格和0
		// 库存配置隐藏式 里面的input加上disable属性
		$('input[type="submit"]').click(function(){
			$('input[data_type="price"]').each(function(){
				if($(this).val() == ''){
					$(this).val($('input[name="goodsStorePrice"]').val());
				}
			});
			$('input[data_type="stock"]').each(function(){
				if($(this).val() == ''){
					$(this).val('0');
				}
			});
			if($('dl[nc_type="spec_dl"]').css('display') == 'none'){
				$('dl[nc_type="spec_dl"]').find('input').attr('disabled','disabled');
			}
		});
		
	});

	// 将选中的规格放入数组
	function into_array(){
	}

	// 生成库存配置
	function goods_stock_set(){
	    // 店铺价格 商品库存改为只读
	    $('input[name="goodsStorePrice"]').attr('readonly','readonly').css('background','#E7E7E7 none');
	    $('input[name="goodsStorage"]').attr('readonly','readonly').css('background','#E7E7E7 none');

	    $('dl[nc_type="spec_dl"]').show();
	    str = '<tr>';
	    var tmp_spec_td = new Array();
	tmp_spec_td.sort(function(a,b){return a-b});
	var spec_bunch = 'i_';
	str += '<input type="hidden" name="spec['+spec_bunch+'][goods_id]" nc_type="'+spec_bunch+'|id" value="" />';str +='<td><input class="text price" type="text" name="spec['+spec_bunch+'][price]" data_type="price" nc_type="'+spec_bunch+'|price" value="" /><em class="add-on"><i class="icon-renminbi"></i></em></td><td><input class="text stock" type="text" name="spec['+spec_bunch+'][stock]" data_type="stock" nc_type="'+spec_bunch+'|stock" value="" /></td><td><input class="text sku" type="text" name="spec['+spec_bunch+'][sku]" nc_type="'+spec_bunch+'|sku" value="" /></td></tr>';
	    if(str == '<tr>'){
	        // 店铺价格 商品库存取消只读
	        $('input[name="goodsStorePrice"]').removeAttr('readonly').css('background','');
	        $('input[name="goodsStorage"]').removeAttr('readonly').css('background','');
	        $('dl[nc_type="spec_dl"]').hide();
	    }else{
	        $('tbody[nc_type="spec_table"]').empty().html(str)
	            .find('input[nc_type]').each(function(){
	                s = $(this).attr('nc_type');
	                try{$(this).val(V[s]);}catch(ex){$(this).val('');};
	                if($(this).attr('data_type') == 'price' && $(this).val() == ''){
	                    $(this).val($('input[name="goodsStorePrice"]').val());
	                }
	                if($(this).attr('data_type') == 'stock' && $(this).val() == ''){
	                    $(this).val('0');
	                }
	            }).end()
	            .find('input[data_type="stock"]').change(function(){
	                computeStock();    // 库存计算
	            }).end()
	            .find('input[data_type="price"]').change(function(){
	                computePrice();     // 价格计算
	            }).end()
	            .find('input[nc_type]').change(function(){
	                s = $(this).attr('nc_type');
	                V[s] = $(this).val();
	            });
	    }
	}
