package com.ansteel.core.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

import com.ansteel.dhtmlx.jsonclass.DhtmlxContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.model.domain.EntityFields;
import com.ansteel.common.model.domain.Models;
import com.ansteel.core.annotation.PathClass;
import com.ansteel.core.annotation.PathDatabaseEntity;
import com.ansteel.core.annotation.PathGridData;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.constant.Constants;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.domain.BaseEntity;
import com.ansteel.core.exception.DaoException;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.query.PageUtils;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.dhtmlx.xml.GridXml;
import com.ansteel.interfaces.service.MakeReport;
import com.ansteel.common.model.service.ModelService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-04-22
 * 修 改 人：
 * 修改日 期：
 * 描   述：全局控制器，用于统一的增删改查功能。 
 */
public class GlobalBaseController extends TreeController {
	

	@Autowired
	MakeReport makeReport;
	
	@Autowired
	ModelService modelService;

	private int pageNum;
		
	/**
	 * ajax调用
	 * 通过实体class、id查询数据
	 * @param clazz
	 * @param id
	 * @return
	 */
	@RequestMapping("/a/get/{className}")
	public @ResponseBody UDataSet getAjax(@PathClass("className")Class clazz,
			@RequestParam("id")String id,HttpServletRequest request,
			HttpServletResponse response){
		BaseEntity result = baseService.findOne(clazz, id);
		return new UDataSet(request,DHtmlxConstants.UI_DATA,result);
	}
	
	/**
	 * ajax调用
	 * 通过实体class、id查询数据
	 * @param clazz
	 * @return
	 */
	@RequestMapping("/a/loadPage/{className}")
	public @ResponseBody UDataSet loadPageAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			HttpServletRequest request,
			HttpServletResponse response){	
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());

		Page page = baseService.find(clazz, key, value, order, pageable);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,page);
	}
		
	/**
	 * ajax调用
	 * 详细查询、带分页
	 * @param clazz
	 * @return
	 */
	@RequestMapping("/a/queryDetailPage/{className}")
	public  @ResponseBody UDataSet queryDetailPageAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){		
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());
		Page page = baseService.find(clazz,key,value,order,queryList,pageable);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,page);
	}
	
	/**
	 * ajax调用
	 * 单条件查询、带分页
	 * @param clazz
	 * @return
	 */
	@RequestMapping("/a/queryPage/{className}")
	public  @ResponseBody UDataSet queryPageAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		Pageable pageable=new PageRequest(PageUtils.getTotalPages(posStart),PageUtils.getMaxResults());

		Page page = baseService.find(clazz,key,value,order,queryList,pageable);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,page);
	}
	
	@RequestMapping("/toexcel/{className}")
	public  void toExcel(@PathClass("className")Class clazz,
			@RequestParam(value="_report",required=false)String reportName,
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		to(reportName,clazz,order, queryList,  "EXCEL", request, response);
	}
	
	@RequestMapping("/topdf/{className}")
	public  void toPdf(@PathClass("className")Class clazz,
			@RequestParam(value="_report",required=false)String reportName,
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		to(reportName,clazz,order, queryList,  "PDF", request, response);
	}
	
	@RequestMapping("/inexcel/{modelName}/{inName}")
	public  @ResponseBody void inExcel(@PathVariable("modelName")String modelName,
			@PathVariable("inName")String inName,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
		if(file!=null&&file.getSize()>0){
			List resultList=makeReport.inExcel(modelName,inName,file);
			Integer totalCount = resultList.size();
			Pageable pageable=new PageRequest(0,totalCount);
			Page page = new PageImpl(resultList, pageable, totalCount);
			//return new UDataSet(request,DHtmlxConstants.UI_ROWS,page);

			//---------------------------------------------------------------
			String result= null;
			try {
				Object dataSet=new DhtmlxContext().get(new UDataSet(request,DHtmlxConstants.UI_ROWS,page));

				ObjectMapper objectMapper = new ObjectMapper();

				try {
					result = objectMapper.writeValueAsString(dataSet);
				} catch (JsonProcessingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				response.reset();
				response.setCharacterEncoding("UTF-8");
				response.setContentType("text/plain;charset=UTF-8");

				PrintWriter writer = null;
				try {
					writer = response.getWriter();
					if(StringUtils.hasText(result)){
						writer.print(result);
					}else {
						writer.print("{}");
					}
					writer.flush();
				} catch (IOException e) {
				} finally {
					if (writer != null) {
						writer.close();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			throw new PageException("请上传导入Excel文件！");
		}
	}
	
	public  String to(String reportName,Class clazz,String order,
			@QueryJson List<QueryMapping> queryList,
			String type,
			HttpServletRequest request,
			HttpServletResponse response){		
		List listMap =baseService.findMap(clazz,null,null,order,queryList);
		String path="";
		if(StringUtils.hasText(reportName)){
			path=makeReport.show(reportName,listMap, type, null, request, response,null);
		}else{
			Map<String,String> nameMap = new HashMap<String,String>();
			Models model=modelService.findOneByClazz(clazz.getName());
			for(EntityFields field:model.getFields()){
				nameMap.put(field.getName(), field.getAlias());
			}
			path=makeReport.show(listMap, nameMap, type,
					null, request,response,null);
		}
		return path;
	}

	/**
	 * ajax调用
	 * 通过实体class、id查询数据
	 * @param clazz
	 * @return
	 */
	@RequestMapping("/a/load/{className}")
	public @ResponseBody UDataSet loadAjax(@PathClass("className")Class clazz,
			@RequestParam(value="_key",required=false)String key,//过滤字段名（一般用于主从表）
			@RequestParam(value="_value",required=false)String value,//过滤字段值（一般用于主从表）
			@RequestParam(value="_order",required=false)String order,//排序字段名
			HttpServletRequest request,
			HttpServletResponse response){		
		List<BaseEntity> result =baseService.findAll(clazz,key,value,order);
		return new UDataSet(request,DHtmlxConstants.UI_DATA,result);
	}
	
	/**
	 * ajax调用
	 * 删除数据，有回调则返回此页面,没有回调刷新当前页
	 * @param clazz
	 * @param id
	 * @return
	 */
	@RequestMapping("/a/delect/{className}")
	@ResponseBody
	public void delectAjax(@PathClass("className")Class clazz,
			@RequestParam("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		baseService.delete(clazz,id);
	}
	
	@RequestMapping("/a/update/{className}")
	@ResponseBody
	public void updateAjax(@Valid @PathDatabaseEntity("className")BaseEntity entity
			,@RequestParam(value="_key",required=false)String key
			,@RequestParam(value="_value",required=false)String value
			,BindingResult result,HttpServletRequest request){
		
		if (result.hasErrors()) {  
			ExceprionUtils.BindingResultError(result);
	    }
        this.saveBefore(entity);
		baseService.save(entity);
	}
	
	@RequestMapping("/a/save/{className}")
	@ResponseBody
	public void saveAjax(@Valid @PathDatabaseEntity("className")BaseEntity entity,
			BindingResult result,
			@RequestParam(value="_key",required=false)String key,
			@RequestParam(value="_value",required=false)String value,
			HttpServletRequest request,
			HttpServletResponse response
			){
		if (result.hasErrors()) {  			
			ExceprionUtils.BindingResultError(result);
	    }  
		if(entity.getId()==null&&StringUtils.hasText(key)){
			//新增时如果有主表，加入主表实体
			String[] k = key.split(Public.SPLIT_POINT);
			if(k.length==2){
				Field field = ReflectionUtils.findField(entity.getClass(), k[0]);
				if(field!=null){
					Class parentClass = field.getType();
					BaseEntity parentEntity = baseService.findOne(parentClass, value);
					field.setAccessible(true);
					ReflectionUtils.setField(field, entity, parentEntity);
				}				
			}
		}
		this.saveBefore(entity);
		baseService.save(entity);
	}

	@RequestMapping("/a/updateAll/{className}")
	@ResponseBody
	public Data updateAllAjax(@PathGridData("className")List<BaseEntity> baseEntityLsit,
			@RequestParam(value="_key",required=false)String key,
			@RequestParam(value="_value",required=false)String value,
			HttpServletRequest request){ 
		Data data = new Data();
		if(baseEntityLsit.size()>0){
			for(BaseEntity baseEntity:baseEntityLsit){
				GridXml gridXml = new GridXml();
				String id = baseEntity.getId();
				if(id.lastIndexOf("inserted_")==0){
					gridXml.setType("inserted");
					baseEntity.setId(null);
					id=id.replaceFirst("inserted_", "");
					gridXml.setTid(id);
					gridXml.setSid(id);
				}else{	
					gridXml.setType("update");
					gridXml.setTid(baseEntity.getId());
					gridXml.setSid(baseEntity.getId());
				}
				try {
					this.saveBefore(baseEntity);
					baseService.save(baseEntity);
				} catch (Exception e) {
					throw new DaoException(e.getMessage());
				}
				
				
				
				data.addAction(gridXml);
			}			
		}	
		
		
		return data;
	}

}
