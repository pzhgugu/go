package com.ansteel.common.attachment.repository;

import com.ansteel.common.attachment.domain.Attachment;
import com.ansteel.core.repository.ProjectRepository;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-28
 * 修 改 人：
 * 修改日 期：
 * 描   述：附件管理持久层。  
 */
public interface AttachmentRepository extends ProjectRepository<Attachment, String> {
	/**
	 * 通编码查询附件类
	 * @param name
	 * @return
	 */
	Attachment findOneByName(String name);
}
