package com.ansteel.common.attachment.service;


import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：附件树管理服务接口。  
 */
public interface AttachmentService {
	
	/*Attachment saveAttachment(MultipartFile file);
	*//**
	 * 保存附件
	 * @param inputStream
	 * @param id 附件树id
	 * @param entity 附件用户定义基本信息
	 * @return
	 *//*
	Attachment saveAttachment(MultipartFile file, String treeId,
			Attachment entity);

	*//**
	 * 将路径信息放入实体中
	 * @return
	 *//*
	UDataSet setAttachmentToPath(UDataSet dataSet, HttpServletRequest request);
	
	*//**
	 * 通过附件id获取附件物理文件路径
	 * @param id
	 * @return
	 *//*
	String getFilePathById(String id);
	
	*//**
	 * 通过id获取附件实体
	 * @param id
	 * @return
	 *//*
	Attachment getAttachmentByIdToPath(String id);
	
	*//**
	 * 通过附件id获取附件web路径
	 * @param id
	 * @return
	 *//*
	String getWebPathById(HttpServletRequest request,String id);
	*//**
	 * 通过id获取附件实体,带web路径
	 * @param id
	 * @return
	 *//*
	Attachment getAttachmentById(HttpServletRequest request, String id);

	*//**
	 * 通过名称获取附件树
	 * @param nameValue
	 * @return
	 *//*
	AttachmentTree getAttachmentTreeByName(String nameValue);
	
	*//**
	 * 保存附件树
	 * @param attachmentTree
	 *//*
	AttachmentTree saveAttachmentTree(AttachmentTree attachmentTree);

	*//**
	 * 保存附件
	 * @param attachmentTree
	 * @param file
	 * @return
	 *//*
	Attachment saveAttachment(AttachmentTree attachmentTree, MultipartFile file,String name,String alias);
	
	*//**
	 * 保存附件
	 * @param attachment
	 * @param file
	 * @return
	 *//*
	Attachment saveAttachment(Attachment attachment, MultipartFile file);

	*//**
	 * 获取附件
	 * @param id
	 * @return
	 *//*
	Attachment getAttachmentById(String id);

	*//**
	 * 删除附件
	 * @param id
	 *//*
	void delect(String id);

	*//**
	 * 根据附件树获取附件目录
	 * @param attachmentTree
	 * @return
	 *//*
	String getAttachmentCatalogue(AttachmentTree attachmentTree,String rType);

	*//**
	 * 保存附件
	 * @param attachment
	 * @return
	 *//*
	Attachment saveAttachment(Attachment attachment);

	*//**
	 * 通过文件保存附件实体
	 * @param outPath 文件路径
	 * @param attachmentTree 树目录，可以为空
	 * @return
	 *//*
	Attachment saveAttachment(String outPath,AttachmentTree attachmentTree);

	*//**
	 * 获取文件路径
	 * @param attachmentTree 树目录，可以为空
	 * @param fileName 文件名称
	 * @param type 文件类型 比如：excl、pdf
	 * @return
	 *//*
	String getPath(AttachmentTree attachmentTree, String fileName,String type) ;

    void setFileInfo(MultipartFile file, Attachment entity);

	String getPath(String path);
*/
}
