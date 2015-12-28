package com.ansteel.common.backup.service;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.common.backup.core.IDataXmlEport;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.DownloadUtils;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：自动备份控制器。  
 */
@Controller
@RequestMapping(value = Public.ADMIN+"/backup")
public class BackupController {
	
	
	@Autowired
	IDataXmlEport dataXmlEport;
	
	@Autowired
	AttachmentService attachmentService;
	
	@RequestMapping(value = "/autoPublish")
    @ResponseBody
    public void backupAutoPublish(HttpServletResponse response) {
        String path = dataXmlEport.getPath();
        dataXmlEport.backupAll(path);
        DownloadUtils.download(response, path,null);

    }
}
