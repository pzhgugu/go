package com.ansteel.core.controller;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.common.attachment.service.FileAttachmentService;
import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.DownloadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2015/12/15.
 */
@Controller
@RequestMapping("/att")
public class AttController {

    @Autowired
    FileAttachmentService fileAttachmentService;

    @RequestMapping("/download/{id}")
    public void download(@PathVariable("id") String id,
                         @RequestParam(value = "_inline", required = false) String inline,
                         HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        Attachment attachment = fileAttachmentService.findOne(id);
        DownloadUtils.download(response, attachment.getPath(), inline);
    }

    @RequestMapping("/downloadPath")
    public void downloadPath(@RequestParam(value = "_path", required = false) String path,
                             @RequestParam(value = "_inline", required = false) String inline,
                             HttpServletRequest request, HttpServletResponse response)
            throws Exception {
        DownloadUtils.download(response, path, inline);
    }
}
