package com.ansteel.core.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathDatabaseEntity;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.domain.TreeEntity;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.service.BaseService;
import com.ansteel.core.service.CheckService;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-02
 * 修 改 人：
 * 修改日 期：
 * 描   述：树控制器，实现树的查询、拖拽、保存。 
 */
public class TreeController {

	
	/**
	 * 分页当前记录行
	 */
	protected static String POS_START="posStart";//posStart=10&count=10
	
	/**
	 * 分页记录条数
	 */
	protected static String COUNT="count";//posStart=10&count=10

	@Autowired
	BaseService baseService;
	
	@Autowired
	CheckService checkService;

	/**
	 * 异步树，通过id查询节点下数据
	 * 
	 * @param clazz
	 *            树class
	 * @param pid
	 *            父id
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/a/queryIdToTree/{className}")
	@ResponseBody
	public UDataSet asynQueryIdToTreeSql(
			@PathClass("className") Class clazz,
			@RequestParam(value="id",required = false) String pid, HttpServletRequest request,
			HttpServletResponse response) {
		if (pid != null && pid.equals("0")) {
			pid = null;
		}		
		List result = baseService.treeQuery(clazz,pid);
		
		return new UDataSet(DHtmlxConstants.UI_ASYN_TREE,result);
	}

	/**
	 * 
	 * @param clazz
	 * @param sid 选择节点的id
	 * @param tid 拖拽节点（拖拽到某个节点上）的父节点id
	 * @param id  拖拽节点的id
	 * @param request
	 * @param response
	 */
	@RequestMapping("/a/dragTree/{className}")
	public void dragTree(@PathClass("className") Class clazz,
			@RequestParam(value="sid",required = false) String sid,//当前节点
			@RequestParam(value="tid",required = false) String tid,//当前节点的父节点
			@RequestParam(value="id",required = false) String id, //当前节点的下一个节点
			HttpServletRequest request,
			HttpServletResponse response) {
		TreeEntity sTree = (TreeEntity)baseService.findOne(clazz, sid);

		if (StringUtils.hasText(id)) {
			String pid = sTree.getParentId();
			if (pid!=null&&!pid.equals(tid)) {
				if(tid.equals("0")){
					sTree.setParent(null);
					tid=null;
				}else{
					TreeEntity tree = (TreeEntity)baseService.findOne(clazz, id);
					sTree.setParent(tree.getParent());
					sTree.setLayer();
				}
				baseService.save(sTree);
			}
			List<TreeEntity> treeList = baseService.findAll(clazz, "parent.id", tid,
							"displayOrder");

			int i = 0, si = 0;
			TreeEntity currentTree=null;
			for (TreeEntity o : treeList) {
				Integer order = o.getDisplayOrder();
				if (o.getId().equals(id)) {
					if(si==0){
						si=i;
						o.setDisplayOrder(++i);
					}else{
						currentTree.setDisplayOrder(i);
						currentTree.setLayer();
						baseService.save(currentTree);
						o.setDisplayOrder(++i);
					}
					o.setLayer();
					baseService.save(o);
				} else if (o.getId().equals(sid)) {
					if(si==0){
						si=-1;
						--i;
						currentTree=o;
					}else{
						o.setDisplayOrder(si);
					}
					o.setLayer();
					baseService.save(o);
				} else {
					o.setDisplayOrder(i);
				}
				if (i != order) {
					o.setLayer();
					baseService.save(o);
				}
				i++;
			}

		} else {

			if (tid.equals(sTree.getParentId())) {
				List<TreeEntity> treeList = baseService
						.findAll(clazz, "parent.id", tid,
								"displayOrder");
				sTree.setDisplayOrder(treeList.get(treeList.size() - 1)
						.getDisplayOrder() + 1);
			} else {
				TreeEntity tTree =(TreeEntity) baseService.findOne(
						clazz, tid);
				sTree.setParent(tTree);
			}
			sTree.setLayer();
			baseService.save(sTree);
		}
	}

	
	@RequestMapping("/a/treeSave/{className}")
	public void treeSave(@PathDatabaseEntity("className") TreeEntity entity,
			@RequestParam(value="_pid",required = false) String pid, 
			HttpServletRequest request,
			HttpServletResponse response){
		if(StringUtils.hasText(pid)&&!StringUtils.hasText(entity.getId())){
			TreeEntity parent =  (TreeEntity) baseService.findOne(entity.getClass(), pid );
			entity.setParent(parent);
		}
		entity.setLayer();
		this.saveBefore(entity);
		Assert.isTrue(!checkService.isNameRepeat(entity), "编码："+entity.getName()+",已经存在！");
		baseService.save(entity);
	}
	

	public void saveBefore(BaseEntity entity){
		if(this instanceof SaveBefore){
			SaveBefore that=(SaveBefore) this;
			that.SaveCheck(entity);
		}
	}
	

	//----------------------------------------------------------------------------------

	
	
}
