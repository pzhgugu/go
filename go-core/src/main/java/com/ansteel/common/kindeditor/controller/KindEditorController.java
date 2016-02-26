package com.ansteel.common.kindeditor.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.common.attachment.service.FileAttachmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.service.AttachmentService;
import com.ansteel.common.kindeditor.core.KindEditorMessage;
import com.ansteel.core.utils.RequestUtils;

@Controller
@RequestMapping(value = "/ke")
public class KindEditorController {
	
	@Autowired
	FileAttachmentService fileAttachmentService;

	@RequestMapping("/upload")
	@ResponseBody
	public void upload(
			@RequestParam(value = "imgFile", required = false) MultipartFile[] files,
			HttpServletRequest request, HttpServletResponse response) {

		KindEditorMessage message = new KindEditorMessage();
		for(MultipartFile file:files){
			try {
				Attachment att = fileAttachmentService.save(file);
				message.setError(0);
				message.setUrl(RequestUtils.getSiteURL(request)+"/att/download/"+att.getId());
			} catch (Exception e) {
				message.setError(1);
				message.setUrl(e.getMessage());
			}
		}
		
		message.returnAjaxMessage(response);
	}

}
