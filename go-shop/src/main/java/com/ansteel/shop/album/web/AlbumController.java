package com.ansteel.shop.album.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ansteel.core.utils.BeanUtils;
import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.album.domain.AlbumClass;
import com.ansteel.shop.album.service.AlbumClassService;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.utils.JavaScriptUtils;

@Controller
@RequestMapping(value = ShopConstant.SELLER + "/album")
public class AlbumController {

	@Autowired
	AlbumClassService albumClassService;

	@RequestMapping("/cate")
	public String cate(Model model, 
			@RequestParam(value="sort",required=false) String sortType,
			HttpServletRequest request,
			HttpServletResponse response) {

		List<AlbumClass> acList = albumClassService.getCurrentAlbumClass(sortType);

		model.addAttribute("P_ALBUM_CLASS", acList);

		Map<String, String> nav = new HashMap<>();
		nav.put("n1", "商家管理中心");
		nav.put("n2", "商品");
		nav.put("n3", "图片空间");
		model.addAttribute("P_NAV", nav);
		model.addAttribute("P_CURRENT_OP", "Album");
		model.addAttribute("P_VIEW", "shop:pages/seller/album/album.html.jsp");
		return FisUtils.page("shop:widget/tpl/seller/framework.html");
	}

	@RequestMapping("/addpage")
	public String addPage(Model model, HttpServletRequest request,
			HttpServletResponse response) {

		List<AlbumClass> acList = albumClassService.getCurrentAlbumClass();

		model.addAttribute("P_CLASS_COUNT", acList.size());
		return FisUtils.page("shop:pages/seller/album/addPage.html");
	}

	@RequestMapping("/checkname")
	public @ResponseBody
	Boolean getClass(@RequestParam("ac_name") String name,
			HttpServletRequest request, HttpServletResponse response) {
		AlbumClass albumClass = albumClassService.findOneByAclassName(name);
		return albumClass == null;
	}

	@RequestMapping("/saveclass")
	public @ResponseBody
	void  saveClass(@Valid AlbumClass albumClass, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		String url=request.getContextPath()+"/se/album/cate";
		if (result.hasErrors()) {
			JavaScriptUtils.BindingResultError(result,url,response);
			return ;
		}
		albumClassService.saveUse(albumClass);
		
		String name="相册创建成功";
		
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}
	
	@RequestMapping("/delclass")
	public @ResponseBody
	void  delClass(@RequestParam("id") String id,
			HttpServletRequest request, HttpServletResponse response) {
		albumClassService.delete(id);
		String url=request.getContextPath()+"/se/album/cate";
		String name="相册删除成功";	
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}
	
	@RequestMapping("/updatepage")
	public String updatePage(@RequestParam("id") String id,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {

		AlbumClass albumClass=albumClassService.findOne(id);
		Assert.notNull(albumClass, id+",没有找到！");

		model.addAttribute("P_ALBUMCLASS", albumClass);
		return FisUtils.page("shop:pages/seller/album/updatePage.html");
	}
	
	@RequestMapping("/updateclass")
	public @ResponseBody
	void  updateClass(@Valid AlbumClass albumClass, BindingResult result,
			HttpServletRequest request, HttpServletResponse response) {

		String url=request.getContextPath()+"/se/album/cate";
		if (result.hasErrors()) {
			JavaScriptUtils.BindingResultError(result,url,response);
			return ;
		}

		AlbumClass album=albumClassService.findOne(albumClass.getId());
		Assert.notNull(albumClass, albumClass.getId()+",没有找到！");
		try {
			BeanUtils.applyIf(album, albumClass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		albumClassService.save(album);
		
		String name="相册修改成功";
		
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}

}
