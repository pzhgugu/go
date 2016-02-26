package com.ansteel.common.attachment.service;

import java.io.File;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.common.attachment.domain.AttachmentTree;
import com.ansteel.common.attachment.repository.AttachmentRepository;
import com.ansteel.common.attachment.repository.AttachmentTreeRepository;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.DateTimeUtils;
import com.ansteel.core.utils.DownloadUtils;
import com.ansteel.core.utils.FileUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：附件树管理服务接口实现。  
 */
@Service
@Transactional(readOnly=true)
public class AttachmentServiceImpl implements AttachmentService {
/*

	@Value("${go_pro.attPath}")
	private String attPath;
	
	@Value("${go_pro.attWeb}")
	private String attWeb;

	
	@Autowired
	AttachmentRepository attachmentRepository;
	
	@Autowired
	AttachmentTreeRepository attachmentTreeRepository;

	@Override
	public Attachment saveAttachment(MultipartFile file, String id,
			Attachment attachment) {
		
		
		if(!(StringUtils.hasText(attachment.getId())&&file==null)){
			Assert.notNull(file, "文件不能为空！");
			AttachmentTree attachmentTree = attachment.getAttachmentTree();
			Assert.hasText(id, "树id不能为空！");
			if(attachmentTree==null||!attachmentTree.getId().equals(id)){
				attachmentTree = getAttachmentTree(id);
				attachment.setAttachmentTree(attachmentTree);
			}
			attachment=this.saveAttachment(attachment,file);
		}
		
		return attachment;
	}

	private void saveFile(MultipartFile file, Attachment attachment,
			AttachmentTree attachmentTree) {
		//获取文件名称
		String fileName=attachment.getFileName();
		if(!StringUtils.hasText(fileName)){
			String uuid = StringUtils.getUuid();
			fileName=uuid+"."+attachment.getType();
			attachment.setFileName(fileName);
		}
		//获取目录名称
		String path=attachment.getPath();
		if(!StringUtils.hasText(path)){
			path = this.getPath(attachmentTree,fileName,attachment.getType());
		}
		
		//保存文件
		try {
			FileUtils.saveFile(file.getInputStream(),this.getPath(path));
		} catch (Exception e) {
			throw new PageException(e.getMessage());
		}
		//没有编码则设置为文件uuid
		if(!StringUtils.hasText(attachment.getName())){
			attachment.setName(fileName);
		}
		//没有名称设置为上传文件名称
		if(!StringUtils.hasText(attachment.getAlias())){
			attachment.setAlias(attachment.getLoadFileName());
		}
		//没有名称设置为上传文件名称
		if(!StringUtils.hasText(attachment.getPath())){
			attachment.setPath(path);
		}
	}

	@Override
	public String getPath(String path) {
		return  attPath+path;
	}


	@Override
	public String getPath(AttachmentTree attachmentTree, String fileName,String type) {
		if(attachmentTree==null||!StringUtils.hasText(attachmentTree.getCatalogue())){
			return this.getDefaultPath(type,fileName);
		}
		String catalogue = attachmentTree.getCatalogue();
		if(attachmentTree.getIsDate()==1){
			catalogue+="/"+DateTimeUtils.format(new Date(),"yyyyMMdd");
		}
		return catalogue+"/"+fileName;
	}

	private String getDefaultPath(String type,String fileName) {
		StringBuffer sb = new  StringBuffer();
		sb.append(this.getLocalPath(type));
		sb.append(fileName);
		return sb.toString();
	}


	private String getLocalPath(String type) {
		StringBuffer sb = new  StringBuffer();
		sb.append("/");
		sb.append(DateTimeUtils.format(new Date(), "yyyyMMdd"));
		sb.append("/");
		if(StringUtils.hasText(type)){
			sb.append(type);
			sb.append("/");
		}
		return sb.toString();
	}


    @Override
	public void setFileInfo(MultipartFile file, Attachment entity) {
		String fileName = file.getOriginalFilename();
		entity.setLoadFileName(fileName);
		entity.setFileSize(file.getSize());
		entity.setType(FileUtils.getFileType(fileName).toLowerCase());
		entity.setContentType(file.getContentType());
	}

	private AttachmentTree getAttachmentTree(String id) {
		AttachmentTree attachmentTree=attachmentTreeRepository.findOne(id);
		Assert.notNull(attachmentTree, "附件树"+id+",没有找到");
		return attachmentTree;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public UDataSet setAttachmentToPath(UDataSet dataSet, HttpServletRequest request) {
		List<Attachment> result = (List<Attachment>) ((Page) dataSet.getResult()).getContent();
		String url = (String) request
				.getAttribute(ViewModelConstant.S_URL);		
		for(Attachment att:result){
			String path = att.getPath();
			att.setPath(this.getPath(path));
			String web=url+"/att/download/"+att.getId();
			att.setWebPath(web+"^"+web);
		}
		return dataSet;
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String getFilePathById(String id) {
		Attachment attachment=this.getAttachmentByIdToPath(id);
		return attachment.getPath();
	}

	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public Attachment getAttachmentByIdToPath(String id) {
		Attachment attachment=attachmentRepository.findOne(id);
		Assert.notNull(attachment, id+",不存在！");
		attachment.setPath(this.getPath(attachment.getPath()));
		return attachment;
	}


	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public String getWebPathById(HttpServletRequest request,String id) {
		String url = (String) request
				.getAttribute(ViewModelConstant.S_URL);
		Attachment attachment=attachmentRepository.findOne(id);
		if(attachment==null){
			return "";		}
		return url+"/att/download/"+attachment.getId();
	}
	
	@Override
	@Transactional(propagation=Propagation.REQUIRES_NEW,readOnly=true)
	public Attachment getAttachmentById(HttpServletRequest request,String id) {
		Attachment attachment=attachmentRepository.findOne(id);
		String path=attachment.getPath();
		attachment.setPath(this.getPath(path));
		String url = (String) request
				.getAttribute(ViewModelConstant.S_URL);
		String web=url+"/att/download/"+attachment.getId();
		attachment.setWebPath(web);
		return attachment;
	}

	@Override
	public AttachmentTree getAttachmentTreeByName(String nameValue) {
		return attachmentTreeRepository.findOneByName(nameValue);
	}

	*/
/*@Override
	@Transactional(readOnly=false)
	public AttachmentTree getAttachmentTreeByAdd(String name,String alias,int isDate) {
		AttachmentTree attachmentTree =  getAttachmentTreeByName(name);
		if(attachmentTree==null){
			attachmentTree = new AttachmentTree();
			attachmentTree.setName(name);
			attachmentTree.setAlias(alias);
			attachmentTree.setIsDate(isDate);
			attachmentTree.setLayer(0);
			attachmentTree=attachmentTreeRepository.save(attachmentTree);
		}
		return attachmentTree;
	}*//*

	
	
	@Override
	@Transactional
	public AttachmentTree saveAttachmentTree(AttachmentTree attachmentTree) {
		return attachmentTreeRepository.save(attachmentTree);
	}

	@Override
	public Attachment saveAttachment(AttachmentTree attachmentTree,
			MultipartFile file, String name, String alias) {
		
		Attachment attachment = new Attachment();
		attachment.setAttachmentTree(attachmentTree);
		attachment.setName(name);
		attachment.setAlias(alias);
		
		return this.saveAttachment(attachment, file);
	}

	@Override
	@Transactional(readOnly=false)
	public Attachment saveAttachment(Attachment attachment, MultipartFile file) {
		this.setFileInfo(file,attachment);
		this.saveFile(file,attachment,attachment.getAttachmentTree());
		return attachmentRepository.save(attachment);
	}

	@Override
	public Attachment getAttachmentById(String id) {
		return attachmentRepository.findOne(id);
	}

	@Override
	@Transactional(readOnly=false)
	public void delect(String id) {
		Attachment attachment = attachmentRepository.findOne(id);
		if(attachment!=null){
			String path = this.getPath(attachment.getPath());
			FileUtils.deleteFile(path);
			attachmentRepository.delete(attachment);
		}
		
	}

	@Override
	public String getAttachmentCatalogue(AttachmentTree attachmentTree,String type) {
		StringBuffer sb = new  StringBuffer();
		if(StringUtils.hasText(attachmentTree.getCatalogue())){
			sb.append(attachmentTree.getCatalogue());
			sb.append("/");
		}else{
			sb.append("/");
			sb.append(attachmentTree.getName());
			sb.append("/");
		}
		if(attachmentTree.getIsDate()!=null&&attachmentTree.getIsDate()==1){
			sb.append(DateTimeUtils.format(new Date(),"yyyyMMdd"));
			sb.append("/");
		}
		if(StringUtils.hasText(type)){
			sb.append(type);
			sb.append("/");
		}
		return sb.toString();
	}

	@Override
	@Transactional
	public Attachment saveAttachment(Attachment attachment) {
		return attachmentRepository.save(attachment);
	}

	@Override
	@Transactional(readOnly=false)
	public Attachment saveAttachment(String outPath,AttachmentTree attachmentTree) {
		if(attachmentTree==null){
			attachmentTree = this.getOtherAttachmentTree();
		}
		File file = new File(outPath);
		Attachment entity=new Attachment();
		String fileName = file.getName();
		entity.setName(fileName);
		entity.setAlias(fileName);
		entity.setLoadFileName(fileName);
		entity.setFileSize(file.length());
		entity.setType(FileUtils.getFileType(fileName).toLowerCase());
		entity.setContentType(file.getName());
		entity.setPath(file.getPath().replace(attPath, ""));
		entity.setAttachmentTree(attachmentTree);
		return this.saveAttachment(entity);
	}
	
	public AttachmentTree getOtherAttachmentTree(){
		AttachmentTree attachmentTree = this.getAttachmentTreeByName(REPORT_OTHER_NAME);
		if (attachmentTree == null) {
			attachmentTree=this.saveAttachmentTree(getOtherFileAttachmentTree());
		}
		return attachmentTree;
	}
	
	public static final String REPORT_OTHER_ALIAS = "其他文件";
	public static final String REPORT_OTHER_NAME = "OtherFile";
	public static final String REPORT_OTHER_PATH = "/OtherFile";
	
	public static AttachmentTree getOtherFileAttachmentTree(){
		return getAttachmentTree(REPORT_OTHER_NAME,REPORT_OTHER_ALIAS,REPORT_OTHER_PATH,1);
	}
	
	public static AttachmentTree getAttachmentTree(String name,String alias,String catlogue,Integer isDate){
		AttachmentTree attachmentTree = new AttachmentTree();
		attachmentTree.setName(name);
		attachmentTree.setAlias(alias);
		attachmentTree.setIsDate(isDate);
		attachmentTree.setCatalogue(catlogue);
		attachmentTree.setLayer(0);
		return attachmentTree;
	}

	@Override
	@Transactional
	public Attachment saveAttachment(MultipartFile file) {
		AttachmentTree attachmentTree=this.getOtherAttachmentTree();
		return this.saveAttachment(attachmentTree, file, file.getOriginalFilename(), file.getOriginalFilename());
	}
*/

}
