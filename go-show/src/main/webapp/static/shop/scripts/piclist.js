 $(function() {
    //鼠标触及区域li改变class
    $(".ncsc-picture-list ul li").hover(function() {
      $(this).addClass("hover");
    },
    function() {
      $(this).removeClass("hover");
    });
    //凸显鼠标触及区域、其余区域半透明显示
    $(".ncsc-picture-list > ul > li").jfade({
      start_opacity: "1",
      high_opacity: "1",
      low_opacity: ".5",
      timing: "200"
    });

    // 替换图片
    $('input[nctype="replace_image"]').each(function() {
      $(this).fileupload({
        dataType: 'json',
        url: 'image/update?id=' + $(this).attr('value')+"&category_id="+$('#category_id').val(),
        done: function(e, data) {
          var param = data.result;
          if (param.state == true) {
            img_refresh(param.id,param.attId);
          } else {
            alert(param.message);
          }
        }
      });
    });

    // ajax 上传图片
    var upload_num = 0; // 上传图片成功数量
    $('#fileupload').fileupload({
      dataType: 'json',
      url: 'image/upload',
      add: function(e, data) {
        $.each(data.files,
        function(index, file) {
          $('<div nctype=' + file.name.replace(/\./g, '_') + '><p>' + file.name + '</p><p class="loading"></p></div>').appendTo('div[nctype="file_loading"]');
        });
        data.submit();
      },
      done: function(e, data) {
        var param = data.result;
        $this = $('div[nctype="' + param.origin_file_name.replace(/\./g, '_') + '"]');
        $this.fadeOut(3000,
        function() {
          $(this).remove();
          //if ($('div[nctype="file_loading"]').html() == '') {
            setTimeout("window.location.reload()", 1000);
         // }
        });
        if (param.state == true) {
          upload_num++;
          $('div[nctype="file_msg"]').html('<i class="icon-ok-sign">'+'</i>'+'成功上传'+upload_num+'张图片');

        } else {
          $this.find('.loading').html(param.message).removeClass('loading');
        }
      }
    });
  });
  // 重新加载图片，替换上传使用
  function img_refresh(id,attId) {
    $('#img_' + id).attr('src', SITEURL+"/att/download/"+attId);
  }

  // 全选
  function checkAll() {
    $('#batchClass').hide();
    $('input[type="checkbox"]').each(function() {
      $(this).prop("checked", true);
    });
  }
  // 取消
  function uncheckAll() {
    $('#batchClass').hide();
    $('input[type="checkbox"]').each(function() {
      $(this).prop("checked", false);
    });
  }
  // 反选
  function switchAll() {
    $('#batchClass').hide();
    $('input[type="checkbox"]').each(function() {
      $(this).prop("checked", !$(this).prop("checked"));
    });
  }

  //控制图片名称input焦点可编辑
  function _focus(o) {
    var name;
    obj = o;
    name = obj.val();
    obj.attr('readonly', '');
    obj.attr('class', 'editInput2');
    obj.select();
    obj.blur(function() {
      if (name != obj.val()) {
        _save(this);
      } else {
        obj.attr('class', 'editInput1');
        obj.attr('readonly', 'readonly');
      }
    });
  }
  function _save(obj) {
    $.post('#', {
      id: obj.id,
      name: obj.value
    },
    function(data) {
      if (data == 'false') {
        showError('操作失败');
      } else {
        showDialog('操作成功', 'succ');
      }
    });
    obj.className = "editInput1";
    obj.readOnly = true;
  }
  function submit_form(type) {
    if (type != 'move') {
      $('#batchClass').hide();
    }
    var id = '';
    $('input[type=checkbox]:checked').each(function() {
      if (!!$(this).val()) {
        id += $(this).val();
      }
    });
    if (id == '') {
      alert('请选择图片');
      return false;
    }
    var url="";
    if (type == 'del') {
      if (!confirm('您确定进行该操作吗?\n注意：使用中的图片也将被删除')) {
        return false;
      }
      url="se/albumpic/image/deletes?for=xml";
    }
    if (type == 'move') {
      $('#checkboxform').append('<input type="hidden" name="form_submit" value="ok" /><input type="hidden" name="cid" value="' + $('#cid').val() + '" />');
      url="se/albumpic/image/moves?for=xml";
    }
    $('#checkboxform').attr('action', url);
    ajaxpost('checkboxform', '', '', 'onerror');
  }
  // 相册封面
  function cover(id,cid) {
    if ($('#aclass_cover').attr('src') != $('#img_' + id).attr('src')) {
      ajaxget("se/albumpic/image/cover?id="+id+"&category_id=" + cid);
    } else {
      showError('请不要重复设置相同的图片为相册封面');
    }
  }

  $(function() {
    $("#img_sort").change(function() {
      $('#select_sort').submit();
    });
    $("#img_move").click(function() {
      if ($('#batchClass').css('display') == 'none') {
        $('#batchClass').show();
      } else {
        $('#batchClass').hide();
      }
    });
  });