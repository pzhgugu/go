package com.ansteel.shop.album.web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ansteel.core.constant.Public;
import com.ansteel.core.utils.JavaScriptUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.ResponseUtils;
import com.ansteel.shop.album.domain.AlbumClass;
import com.ansteel.shop.album.domain.AlbumPic;
import com.ansteel.shop.album.service.AlbumClassService;
import com.ansteel.shop.album.service.AlbumPicService;
import com.ansteel.shop.utils.JsonImage;

@Controller
@RequestMapping(value = Public.SELLER + "/albumpic")
public class AlbumPicController {
	
	@Autowired
	AlbumPicService albumPicService;
	
	@Autowired
	AlbumClassService albumClassService;
	
	@RequestMapping("/image/upload")
	public @ResponseBody
	JsonImage  imageUpload(@RequestParam(value="category_id") String id,
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {	
		return albumPicService.saveImage(id,file);
	}

	@RequestMapping("/piclist")
	public String cate(@RequestParam("category_id") String id,
			Model model, 
			@RequestParam(value="sort",required=false) String sortType,
			@RequestParam(value="curpage",required=false) Integer curPage,
			HttpServletRequest request,
			HttpServletResponse response) {
		AlbumClass albumClass=albumClassService.findOne(id);
		Assert.notNull(albumClass,id+"，没有找到此分类！");
		Page<AlbumPic> albumPicPage=albumPicService.findByAclassId(id,sortType,curPage,15);
		
		List<AlbumClass> acList = albumClassService.getCurrentAlbumClass();
		model.addAttribute("P_ALBUM_CLASSLIST", acList);
		
		Map<String, String> nav = new HashMap<>();
		nav.put("n1", "商家管理中心");
		nav.put("n2", "商品");
		nav.put("n3", "图片空间");
		model.addAttribute("P_NAV", nav);
		model.addAttribute("P_CURRENT_OP", "Album");
	
		model.addAttribute("P_ALBUM_PICLIST", albumPicPage.getContent());
		model.addAttribute("P_PAGE_SHOW", albumPicPage);
		model.addAttribute("P_ALBUM_CLASS", albumClass);
		model.addAttribute("P_VIEW", "shop:pages/seller/album/piclist.html.jsp");
		return FisUtils.page("shop:widget/tpl/seller/framework.html");
	}
	
	@RequestMapping("/image/deletes")
	public @ResponseBody
	void  imageDeletes(@RequestParam(value = "id[]") String[] ids,
			@RequestParam("category_id") String categoryId,
			HttpServletRequest request, HttpServletResponse response) {	
		albumPicService.delete(ids);
		String url=request.getContextPath()+"/se/albumpic/piclist?category_id="+categoryId;
		String name="图片删除成功";	
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}
	
	@RequestMapping("/image/delete")
	public @ResponseBody
	void  imageDelete(@RequestParam(value = "id") String id,
			@RequestParam("category_id") String categoryId,
			HttpServletRequest request, HttpServletResponse response) {	
		albumPicService.delete(id);
		String url=request.getContextPath()+"/se/albumpic/piclist?category_id="+categoryId;
		String name="图片删除成功";	
		ResponseUtils.xmlCDataOut(response, JavaScriptUtils.returnShowDialog(name, url));
	}

	@RequestMapping("/image/moves")
	public @ResponseBody
	void  imageMoves(@RequestParam(value = "id[]") String[] ids,
			@RequestParam("category_id") String categoryId,
			@RequestParam("cid") String toCategoryId,
			HttpServletRequest request, HttpServletResponse response) {	
		albumPicService.move(toCategoryId,ids);
		String url=request.getContextPath()+"/se/albumpic/piclist?category_id="+categoryId;
		String name="图片移动成功";	
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}
	
	@RequestMapping("/image/move")
	public @ResponseBody
	void  imageMove(@RequestParam(value = "id") String id,
			@RequestParam("category_id") String categoryId,
			@RequestParam("cid") String toCategoryId,
			HttpServletRequest request, HttpServletResponse response) {	
		albumPicService.move(toCategoryId,id);
		String url=request.getContextPath()+"/se/albumpic/piclist?category_id="+categoryId;
		String name="图片移动成功";	
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}
	
	@RequestMapping("/image/update")
	public @ResponseBody
	Map  imageUpdate(@RequestParam(value = "id") String id,
			@RequestParam("category_id") String categoryId,
			@RequestParam(value = "file") MultipartFile file,
			HttpServletRequest request, HttpServletResponse response) {	
		AlbumPic albumPic=albumPicService.update(id,file);
		/*String url=request.getContextPath()+"/se/albumpic/piclist?category_id="+categoryId;
		String name="图片移动成功";	
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));*/
		Map map = new HashMap<>();
		map.put("state", true);
		map.put("id", albumPic.getId());
		map.put("attId", albumPic.getApicCover());
		return map;
	}
	
	@RequestMapping("/image/cover")
	public @ResponseBody
	void  imageCover(@RequestParam(value = "id") String id,
			@RequestParam("category_id") String categoryId,
			HttpServletRequest request, HttpServletResponse response) {	
		albumPicService.cover(id,categoryId);
		String url=request.getContextPath()+"/se/albumpic/piclist?category_id="+categoryId;
		String name="设置封面成功";	
		ResponseUtils.xmlCDataOut(response,JavaScriptUtils.returnShowDialog(name,url));
	}
	
	@RequestMapping("/movepage")
	public String addPage(@RequestParam(value = "id") String id,
			@RequestParam("category_id") String cid,
			Model model, HttpServletRequest request,
			HttpServletResponse response) {

		List<AlbumClass> acList = albumClassService.getCurrentAlbumClass();
		model.addAttribute("P_ALBUM_CLASSLIST", acList);
		model.addAttribute("P_ID", id);
		model.addAttribute("P_CID", cid);
		return FisUtils.page("shop:pages/seller/album/movePage.html");
	}
}
