package com.ansteel.common.dynamicmodel.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.dhtmlx.jsonclass.DhtmlxContext;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.common.dynamicmodel.domain.DynamicFields;
import com.ansteel.common.dynamicmodel.domain.DynamicModels;
import com.ansteel.common.dynamicmodel.service.DynamicModelsService;
import com.ansteel.core.annotation.QueryJson;
import com.ansteel.core.constant.DHtmlxConstants;
import com.ansteel.core.constant.Public;
import com.ansteel.core.exception.PageException;
import com.ansteel.core.utils.QueryMapping;
import com.ansteel.core.utils.RequestUtils;
import com.ansteel.dhtmlx.jsonclass.UDataSet;
import com.ansteel.dhtmlx.xml.Data;
import com.ansteel.dhtmlx.xml.GridXml;
import com.ansteel.interfaces.service.MakeReport;
import com.ansteel.common.tpl.service.TplService;
import com.ansteel.common.viewelement.service.ViewElement;
import com.ansteel.common.viewelement.service.ViewElementService;
/**
 * 创 建 人：gugu
 * 创建日期：2015-05-30
 * 修 改 人：
 * 修改日 期：
 * 描   述：动态建模模板控制器。  
 */
@Controller
@RequestMapping(value = "/dmtpl")
public class DynamicModelsTplController {
	
	private static final String sataus = "!nativeeditor_status";
	
	@Autowired
	ViewElementService viewElementService;
	
	@Autowired
	DynamicModelsService dynamicModelsService;
	
	@Autowired
	MakeReport makeReport;
	

	@Autowired
	TplService tplService;
	
	@RequestMapping("/a/loadPage/{modelName}")
	public @ResponseBody UDataSet loadPageAjax(@PathVariable("modelName")String modelName,
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			HttpServletRequest request,
			HttpServletResponse response){
		Page page=dynamicModelsService.loadAll(modelName,posStart,count,order);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,page);
	}
	
	@RequestMapping("/a/queryPage/{modelName}")
	public  @ResponseBody UDataSet queryPageAjax(@PathVariable("modelName")String modelName,
			@RequestParam(value="posStart",required=false)String posStart,//分页当前记录行
			@RequestParam(value="count",required=false)String count,//分页记录行
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		Page result = dynamicModelsService.find(modelName,posStart,count,order,queryList);
		return new UDataSet(request,DHtmlxConstants.UI_ROWS,result);
	}
	
	@RequestMapping("/inexcel/{modelName}/{inName}")
	public  @ResponseBody void inExcel(@PathVariable("modelName")String modelName,
			@PathVariable("inName")String inName,
			@RequestParam(value = "file", required = false) MultipartFile file,
			HttpServletRequest request,
			HttpServletResponse response){
		if(file==null){
			throw new PageException("请选择上传excel！");
		}
		List resultList=makeReport.inExcel(modelName,inName,file);
		Integer totalCount = resultList.size();
		
		Pageable pageable=new PageRequest(0,totalCount);
		Page page = new PageImpl(resultList, pageable, totalCount);
		//return new UDataSet(request,DHtmlxConstants.UI_ROWS,page);
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
	}
	
	@RequestMapping("/toexcel/{modelName}")
	public  void toExcel(@PathVariable("modelName")String modelName,
			@RequestParam(value="_report",required=false)String reportName,
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		to(reportName,modelName,order, queryList,  "EXCEL", request, response);
	}
	
	@RequestMapping("/topdf/{modelName}")
	public  void toPdf(@PathVariable("modelName")String modelName,
			@RequestParam(value="_report",required=false)String reportName,
			@RequestParam(value="_order",required=false)String order,//排序字段名
			@QueryJson List<QueryMapping> queryList,
			HttpServletRequest request,
			HttpServletResponse response){
		to(reportName,modelName,order, queryList,  "PDF", request, response);
	}
	
	public  void to(String reportName,String modelName,String order,
			@QueryJson List<QueryMapping> queryList,
			String type,
			HttpServletRequest request,
			HttpServletResponse response){
		Map<String,Object> operMap = new HashMap<String,Object>();
		for(QueryMapping qm:queryList){
			if(Public.QUERY_BETWEEN.endsWith(qm.getOperator())){
				operMap.put("_BEGIN_"+qm.getName(), qm.getBegin());
				operMap.put("_END_"+qm.getName(), qm.getEnd());
			}else if(StringUtils.hasText(qm.getValue())){
				operMap.put(qm.getName(), qm.getValue());
			} 
		}

		List listMap = dynamicModelsService.find(modelName, order, queryList);
		Assert.isTrue(listMap.size()>0, "数据为空，不能导出！");
		if(StringUtils.hasText(reportName)){
			makeReport.show(reportName,listMap, type, null, request, response,null);
		}else{
			Map<String,String> nameMap = new HashMap<String,String>();
			DynamicModels model=dynamicModelsService.getDynamicModels(modelName);
			for(DynamicFields field:model.getFields()){
				nameMap.put(field.getName(), field.getAlias());
			}
			makeReport.show(listMap, nameMap, type,
					null, request,response,null);
		}
		
		
	}
	
	@RequestMapping("/a/save/{modelName}")
	@ResponseBody
	public void saveAjax(@PathVariable("modelName")String modelName,HttpServletRequest request){
		dynamicModelsService.save(modelName,request);
	}
	
	@RequestMapping("/a/update/{modelName}")
	@ResponseBody
	public void updateAjax(@PathVariable("modelName")String modelName
			,HttpServletRequest request){
		dynamicModelsService.save(modelName,request);
	}
	
	@RequestMapping("/a/delect/{modelName}")
	@ResponseBody
	public void delectAjax(@PathVariable("modelName")String modelName,
			@RequestParam("id")String id,
			HttpServletRequest request,
			HttpServletResponse response){
		dynamicModelsService.delect(modelName,id);
	}

	@RequestMapping("/a/clean/{modelName}")
	@ResponseBody
	public void cleanAjax(@PathVariable("modelName")String modelName,
						   HttpServletRequest request,
						   HttpServletResponse response){
		dynamicModelsService.clean(modelName);
	}
	
	@RequestMapping("/a/updateAll/{modelName}")
	@ResponseBody
	public Data updateAllAjax(@PathVariable("modelName")String modelName,
			HttpServletRequest request){ 
		Data data = new Data();
		String[] ids= getIds(request);
		if(ids!=null&&ids.length>0){
			for(String id:ids){
				String gridSataus = "";
				String idSataus=id+"_"+sataus;
				if(request.getParameterMap().containsKey(idSataus)){
					gridSataus=request.getParameter(idSataus);
				}else{
					gridSataus=null;
				}
				boolean is = false;

				GridXml gridXml = new GridXml();
				switch (gridSataus) {
					case "updated":
						is=dynamicModelsService.save(modelName,this.getUpdatedBaseMap(id,request));
						gridXml.setType("updated");
						break;
					case "inserted":
						is=dynamicModelsService.save(modelName,getAddMap(id, request));
						gridXml.setType("inserted");
						break;
					case "deleted":
						
						break;
					case "invalid":
						
						break;
					case "error":
						
						break;
					default:
				}

				if(!is){
					gridXml.setType("error");	
				}
				gridXml.setTid(id);
				gridXml.setSid(id);
				data.addAction(gridXml);
			}			
		}	
		
		
		return data;
	}
	
	private Map<String,String> getUpdatedBaseMap(String id,HttpServletRequest request) {
		Map<String,String> reqCurrentMap = getAddMap(id, request);
		reqCurrentMap.put("id", id);
		return reqCurrentMap;
	}
	
	private Map<String,String> getAddMap(String id,HttpServletRequest request) {
		Map<String,String> reqCurrentMap = new HashMap<String,String>();
		Map<String, String> map = RequestUtils.getRequestMap(request);
		for(Entry<String, String> entry:map.entrySet()){
			String key = entry.getKey();
			if(key.indexOf(id)==0&&!key.equals(id+"_"+sataus)&&!key.equals(id+"_total_count")&&!key.equals(id+"_gr_id")&&!key.equals(id+"_pos")&&!key.equals(id+"_data")){
				String fieldName=key.replace(id+"_", "");
				String valueR = request.getParameter(key); 
				reqCurrentMap.put(fieldName, valueR);
			}
		}
		return reqCurrentMap;
	}
	
	private String[] getIds(HttpServletRequest request) {
		String[] ids=null;
		if(request.getParameterMap().containsKey(Public.GRID_IDS)){
			String sIds = request.getParameter(Public.GRID_IDS);
			if(StringUtils.hasText(sIds)){
				ids=sIds.split(",");
			}
		}
		return ids;
	}
	 
	@RequestMapping("/tpl/{tplName}/{modelType}/{fieldsCategory}")
	public String tpl(@PathVariable("tplName")String tplName,
			@PathVariable("modelType")String modelType,
			@PathVariable("fieldsCategory")String fieldsCategory,
			@RequestParam(value="_view",required = false) String viewName,
			Model model,
			HttpServletRequest request,
			HttpServletResponse response){
		
		ViewElement viewElement = viewElementService.getDynamicViewElement(
				request, response, modelType, 
				fieldsCategory, tplName, viewName);
		viewElement.setRequestMappingName("/dmtpl");
		
		return tplService.runTpl(viewElement, model);
		
	}
}
