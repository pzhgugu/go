package com.ansteel.common.tpl.core;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ansteel.common.tpl.domain.Tpl;
import com.ansteel.common.tpl.domain.TplUrl;
import com.ansteel.common.view.domain.View;
import com.ansteel.core.constant.Public;
import com.ansteel.core.constant.ServiceConstants;
import com.ansteel.core.constant.ViewModelConstant;
import com.ansteel.core.utils.AnalyzeTplSyntax;
import com.ansteel.dhtmlx.widget.form.FormFactory;
import com.ansteel.dhtmlx.widget.form.Options;
import com.ansteel.common.prentmodel.domain.Fields;
import com.ansteel.common.prentmodel.domain.FieldsForm;
import com.ansteel.common.prentmodel.domain.FieldsGrid;
import com.ansteel.common.viewelement.service.ViewElement;

/**
 * 创 建 人：gugu
 * 创建日期：2015-05-19
 * 修 改 人：
 * 修改日 期：
 * 描   述：表格模板模块。
 */
@Service(ServiceConstants.DTHMLX_GRID)
public class DhtmlxGrid implements IDhtmlxWidget{

	private static final String SELECT_HEADER="";
	private static final String SELECT_COLUMNIDS = "select_ids";
	private static final String SELECT_WIDTH = "20";
	private static final String SELECT_SORTING = "str";
	private static final String SELECT_ALIGN = "center";
	private static final String SELECT_TYPE = "ch";

	@Override
	public Map<String, Object> structure(ViewElement viewElement) {
		
		Map<String, Object> viewModel=new HashMap<String, Object>();
		viewModel.putAll(this.initGrid(viewElement));
		viewModel.putAll(this.getUrlInfo(viewElement));
		
		return viewModel;
	}
	
	private Map<String, Object> getUrlInfo(ViewElement viewElement) {
		Map<String, Object> viewModel=new HashMap<String, Object>();
		View view = viewElement.getView();
		Tpl tpl = viewElement.getTpl();
		List<TplUrl> urlList = null;
		if(tpl!=null){
			urlList=(List<TplUrl>) tpl.getTplUrlList();
			for(TplUrl o:urlList){
				viewModel.put(o.getName(), this.analyzeUrl(o.getUrl(),AnalyzeTplSyntax.getTplModelMap(viewElement)));
			}
		}
		if(view!=null){
			urlList = (List<TplUrl>) view.getTplUrlList();
			for(TplUrl o:urlList){
				viewModel.put(o.getName(), this.analyzeUrl(o.getUrl(),AnalyzeTplSyntax.getTplModelMap(viewElement)));
			}
		}
		
		return viewModel;
	}

	private String analyzeUrl(String url,Map<String, Object> modelMap) {
		return AnalyzeTplSyntax.analyze(url, modelMap);
	}

	private Map<String, Object> initGrid(ViewElement viewElement){
		List<Fields> fields = (List<Fields>) viewElement.getFieldsList();
		List<FieldsGrid> grids = (List<FieldsGrid>) viewElement.getGridList();
		StringBuffer header =new StringBuffer();
		StringBuffer columnIds =new StringBuffer();
		StringBuffer initWidths =new StringBuffer();
		StringBuffer colSorting =new StringBuffer();
		StringBuffer colAlign =new StringBuffer();
		StringBuffer colTypes =new StringBuffer();
		StringBuffer colValidators =new StringBuffer();
		//合计
		StringBuffer totalFooter = new StringBuffer("合计,#cspan");
		StringBuffer totalFooterArray = new StringBuffer();
		//总计
		StringBuffer grandTotalFooter = new StringBuffer("总计,#cspan");
		StringBuffer grandTotalFooterArray = new StringBuffer();
		//选计
		StringBuffer selectSumFooter = new StringBuffer("选计,#cspan");
		StringBuffer selectSumFooterArray = new StringBuffer();
		//是否选计
		Boolean isSelectSum=false;
		//是否合计
		Boolean isTotal=false;
		//是否总计
		Boolean isGrandTotal=false;

		int size=grids.size();
		
		/*header.append(SELECT_HEADER+",");
		columnIds.append(SELECT_COLUMNIDS+",");
		initWidths.append(SELECT_WIDTH+",");
		colSorting.append(SELECT_SORTING+",");
		colAlign.append(SELECT_ALIGN+",");
		colTypes.append(SELECT_TYPE+",");*/
		
		Map<String,String> nameMap = new LinkedHashMap<String, String>();
		for(Fields f:fields){
			nameMap.put(f.getName(), f.getAlias());
		}
		
		Map<String,FieldsForm> formMap=this.getFormGridMap(viewElement.getFormList());
		
		for(int i=0;i<size;i++){
			FieldsGrid o = grids.get(i);
			String alias ="";
			if(nameMap.containsKey(o.getName())){
				alias=nameMap.get(o.getName());
			}
			if(o.getIsShow()!=null&&o.getIsShow()==1){
				columnIds.append(o.getName());
				header.append(alias);
				
				if(o.getInitWidths()==null){
					initWidths.append(Public.DEFAULE_MODELGRID_WIDTHS);
				}else{
					initWidths.append(o.getInitWidths());
				}
				
				if(StringUtils.hasText(o.getColSorting())){
					colSorting.append(o.getColSorting());
				}else{
					colSorting.append(Public.DEFAULE_MODELGRID_SORTING);
				}
				
				if(StringUtils.hasText(o.getColAlign())){
					colAlign.append(o.getColAlign());
				}else{
					colAlign.append(Public.DEFAULE_MODELGRID_ALIGN);
				}
				
				if(StringUtils.hasText(o.getColTypes())){
					colTypes.append(o.getColTypes());
				}else{
					colTypes.append(Public.DEFAULE_MODELGRID_TYPES);
				}	
				
				//验证
				String validator=this.getValidator(o.getName(),formMap);
				if(StringUtils.hasText(validator)){
					colValidators.append("\""+validator+"\"");
				}else{
					colValidators.append(Public.NULL);
				}
				//将Footer前两行不做计算
				if(i>1){
					if(o.getIsTotal()!=null&&o.getIsTotal()){
						//便于理解写在一起
						totalFooter.append(",<div id='"+o.getName()+"_total'>0<div>");
						totalFooterArray.append("'"+o.getName()+"'");
						isTotal=true;

						totalFooterArray.append(",");
					}else{
						totalFooter.append(",-");
					}
					if(o.getIsGrandTotal()!=null&&o.getIsGrandTotal()){
						//便于理解写在一起
						grandTotalFooter.append(",<div id='"+o.getName()+"_grandtotal'>0<div>");
						grandTotalFooterArray.append("'"+o.getName()+"'");
						isGrandTotal=true;
						grandTotalFooterArray.append(",");
					}else{
						grandTotalFooter.append(",-");
					}
					if(o.getIsSelectSum()!=null&&o.getIsSelectSum()){
						//便于理解写在一起
						selectSumFooter.append(",<div id='"+o.getName()+"_selectsum'>0<div>");
						selectSumFooterArray.append("'"+o.getName()+"'");
						isSelectSum=true;
						selectSumFooterArray.append(",");
					}else{
						selectSumFooter.append(",-");
					}
				}
				header.append(",");
				columnIds.append(",");
				initWidths.append(",");
				colSorting.append(",");
				colAlign.append(",");
				colTypes.append(",");
				colValidators.append(",");
			}
		}
		
		
		
		String formGridAssemble = this.getFormGridAssemble(viewElement.getFormList(),viewElement.getRequest());
		
		Map<String, Object> viewModel=new HashMap<String, Object>();
		
		
		String prefix=viewElement.getViewPrefix();
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_HEADER, prefix),header.substring(0, header.length()-1));
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_COLUMNIDS, prefix),columnIds.substring(0, columnIds.length()-1));
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_WIDTH, prefix),initWidths.substring(0, initWidths.length()-1));
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_COLSORTING, prefix),colSorting.substring(0, colSorting.length()-1));
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_COLALIGN, prefix),colAlign.substring(0, colAlign.length()-1));
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_COLTYPES, prefix),colTypes.substring(0, colTypes.length()-1));
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_VALIDATORS, prefix),"["+colValidators.substring(0, colValidators.length()-1)+"]");
		//是否表格编辑(是否应该在视图中管理？？)
		//viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_EDITABLE, prefix), viewElement.getFieldsCategory().getIsEditable());
		//表格集合映射
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FORMGRIDASSEMBLE, prefix), formGridAssemble);

		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_TOTAL, prefix), totalFooter.toString());
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_IS_TOTAL, prefix), isTotal);
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_TOTAL_ARRAY, prefix), (totalFooterArray.length()>0)?"["+totalFooterArray.substring(0, totalFooterArray.length()-1)+"]":"[]");
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_GRANDTOTAL, prefix), grandTotalFooter.toString());
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_IS_GRANDTOTAL, prefix), isGrandTotal);
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_GRANDTOTAL_ARRAY, prefix), (grandTotalFooterArray.length()>0)?"["+grandTotalFooterArray.substring(0, grandTotalFooterArray.length()-1)+"]":"[]");
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_SELECTSUM, prefix), selectSumFooter.toString());
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_IS_SELECTSUM, prefix), isSelectSum);
		viewModel.put(TplUtils.getName(ViewModelConstant.P_GRID_FOOTER_SELECTSUM_ARRAY, prefix), (selectSumFooterArray.length()>0)?"["+selectSumFooterArray.substring(0, selectSumFooterArray.length()-1)+"]":"[]");
		return viewModel;
	}

	private String getValidator(String name, Map<String, FieldsForm> formMap) {
		if(formMap.containsKey(name)){
			FieldsForm fieldsForm=formMap.get(name);
			return fieldsForm.getValidate();
		}
		return null;
	}

	private Map<String, FieldsForm> getFormGridMap(
			Collection<FieldsForm> fieldsForm) {
		Map<String, FieldsForm> optionMap=new LinkedHashMap<String, FieldsForm>();
		for(FieldsForm f:fieldsForm){
			optionMap.put(f.getName(), f);
		}
		return optionMap;
	}

	private String getFormGridAssemble(Collection<FieldsForm> fieldsForm,HttpServletRequest request) {
		
		Map<String, List<Options>> optionMap=new LinkedHashMap<String, List<Options>>();
		FormFactory formFactory = new FormFactory();
		for(FieldsForm f:fieldsForm){
			List<Options>  options = formFactory.getOptions(f,request);
			if(options!=null&&options.size()>0){
				optionMap.put(f.getName(), options);
			}
		}
		ObjectMapper objectMapper = new ObjectMapper();  
		String sOption = "";
	    try {
	    	sOption = objectMapper.writeValueAsString(optionMap);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return sOption;
	}

}
