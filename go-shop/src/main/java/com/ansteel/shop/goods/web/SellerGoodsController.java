package com.ansteel.shop.goods.web;

import java.text.MessageFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ansteel.shop.goods.domain.*;
import com.ansteel.shop.goods.service.*;
import com.ansteel.shop.store.domain.StoreGoodsClass;
import com.ansteel.shop.store.domain.StorePlate;
import com.ansteel.shop.store.service.StoreGoodsClassService;
import com.ansteel.shop.store.service.StorePlateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.ansteel.core.utils.ExceprionUtils;
import com.ansteel.core.utils.FisUtils;
import com.ansteel.core.utils.StringUtils;
import com.ansteel.shop.album.domain.AlbumClass;
import com.ansteel.shop.album.domain.AlbumPic;
import com.ansteel.shop.album.service.AlbumClassService;
import com.ansteel.shop.album.service.AlbumPicService;
import com.ansteel.shop.constant.ShopConstant;
import com.ansteel.shop.store.domain.Store;
import com.ansteel.shop.store.service.StoreService;

@Controller
@RequestMapping(value = ShopConstant.SELLER + "/goods")
public class SellerGoodsController {

    @Autowired
    GoodsClassService goodsClassService;

    @Autowired
    AlbumClassService albumClassService;

    @Autowired
    GoodsClassStapleService goodsClassStapleService;

    @Autowired
    AlbumPicService albumPicService;

    @Autowired
    GoodsCommonService goodsCommonService;

    @Autowired
    GoodsImagesService goodsImagesService;

    @Autowired
    StoreService storeService;

    @Autowired
    GoodsSpecValueService goodsSpecValueService;

    @Autowired
    StorePlateService storePlateService;

    @Autowired
    StoreGoodsClassService storeGoodsClassService;

    @RequestMapping("/addstep/one")
    public String one(Model model,
                      HttpServletRequest request,
                      HttpServletResponse response) {

        List<GoodsClassStaple> goodsClassStapleList = goodsClassStapleService.getCurrentGoodsClassStaple();
        model.addAttribute("P_GOODSCLASSSTAPLE_LIST", goodsClassStapleList);

        List<GoodsClass> goodsClassList = goodsClassService.findByParentIsNull();
        model.addAttribute("P_GOODSCLASS_LIST", goodsClassList);

        model.addAttribute("P_STEP", 1);
        model.addAttribute("P_CURRENT_OP", "GoodsAdd");
        model.addAttribute("P_CURRENT_TOP", "goods");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "商品发布");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_VIEW", "shop:pages/seller/GoodsAdd/goodsAddStep1.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/class")
    public
    @ResponseBody
    List getClass(
            @RequestParam("gc_id") String id,
            @RequestParam("deep") String deep,
            HttpServletRequest request,
            HttpServletResponse response) {

        List<GoodsClass> goodsClassList = goodsClassService.findByParentId(id);

        List<JsonGoodsClass> json = new ArrayList<JsonGoodsClass>();
        for (GoodsClass gc : goodsClassList) {
            JsonGoodsClass jgc = new JsonGoodsClass();
            jgc.setGc_id(gc.getId());
            jgc.setGc_name(gc.getAlias());
            jgc.setType_id("0");
            json.add(jgc);
        }
        return json;
    }


    @RequestMapping("/class/stap")
    public
    @ResponseBody
    Object getClassStap(
            @RequestParam("stapleid") String stapleid,
            HttpServletRequest request,
            HttpServletResponse response) {

        JsonGoodsClass json = goodsClassStapleService.selectGoodsClassStaple(stapleid);
        return json;
    }


    @RequestMapping("/stapledel")
    public
    @ResponseBody
    Object stapledel(
            @RequestParam("stapleid") String stapleid,
            HttpServletRequest request,
            HttpServletResponse response) {
        goodsClassStapleService.delect(stapleid);
        JsonGoodsClass json = new JsonGoodsClass();
        json.setDone(true);
        return json;
    }

    @RequestMapping("/addstep/two")
    public String two(Model model,
                      @RequestParam("class_id") String classId,
                      @RequestParam("t_id") String tId,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        //判断分类是否3级
        GoodsClass goodsClass = goodsClassService.findOne(classId);
        Assert.notNull(goodsClass, "你选择的分类不存在，请重新选择！");
        Integer layer = goodsClass.getLayer();
        Assert.isTrue(layer == 2, "你选择的分类没有选择最后一级分类，请重新选择！");
        //检查并保存常用分类
        goodsClassStapleService.checkSaveStaple(goodsClass);


        //得到商品类型
        Store store = storeService.getCurrentStore();
        GoodsType goodsType = goodsClass.getGoodsType();
        if (goodsType != null) {
            //关联规格
            Collection<GoodsSpec> goodsSpecs = goodsType.getGoodsSpecs();
            model.addAttribute("P_GOODSSPECS", goodsSpecs);
            if (goodsSpecs.size() > 0) {
                List<GoodsSpecValue> goodsSpecValueList = goodsSpecValueService.findByStoreIdOrderByStoreIdAsc(store.getId());
                model.addAttribute("P_GOODSSPECVALUE_LIST", goodsSpecValueList);
            }
            //关联品牌
            Collection<GoodsBrand> goodsBrands = goodsType.getGoodsBrands();
            model.addAttribute("P_GOODSBRADNS", goodsBrands);
            //关联属性
            Collection<GoodsAttribute> goodsAttributes = goodsType.getGoodsAttribute();
            model.addAttribute("P_GOODSATTRIBUTE", goodsAttributes);
        }
        model.addAttribute("P_GOODSTYPE", goodsType);

        //关联版式
        List<StorePlate> storePlateList= storePlateService.findAllCurrentStore();
        model.addAttribute("P_STOREPLATE_LIST", storePlateList);

        //店铺分类
        List<StoreGoodsClass> storeGoodsClassList=storeGoodsClassService.findCurrentByIsParentNull();
        model.addAttribute("P_STOREGOODSCLASS_PARENT_LIST", storeGoodsClassList);


        model.addAttribute("P_GOODSCLASS_LISTNAME", this.getGoodsClassListName(goodsClass));
        model.addAttribute("P_GOODSCLASS", goodsClass);

        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "商品发布");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_STEP", 2);
        model.addAttribute("P_CURRENT_OP", "GoodsAdd");
        model.addAttribute("P_CURRENT_TOP", "goods");
        model.addAttribute("P_VIEW", "shop:pages/seller/GoodsAdd/goodsAddStep2.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    private String getGoodsClassListName(GoodsClass goodsClass) {
        String pattern = "{0}>{1}>{2}";
        GoodsClass p1 = goodsClass.getParent();
        GoodsClass p0 = p1.getParent();
        return MessageFormat.format(pattern, p0.getName(), p1.getName(), goodsClass.getName());
    }

    @RequestMapping("/addstep/two/images")
    public String twoImages(Model model,
                            HttpServletRequest request,
                            @RequestParam(value = "sort", required = false) String sortType,
                            @RequestParam(value = "curpage", required = false) Integer curPage,
                            @RequestParam(value = "class_id", required = false) String classId,
                            HttpServletResponse response) {
        List<AlbumClass> acList = albumClassService.getCurrentAlbumClass();
        model.addAttribute("P_ALBUM_CLASS", acList);

        Page<AlbumPic> albumPicPage = null;
        if (StringUtils.hasText(classId)) {
            albumPicPage = albumPicService.findByAclassId(classId, sortType, curPage, 14);
            model.addAttribute("P_CLASSID", classId);
        } else {
            albumPicPage = albumPicService.findAll(sortType, curPage, 14);
        }
        model.addAttribute("P_ALBUM_PICLIST", albumPicPage.getContent());
        model.addAttribute("P_PAGE_SHOW", albumPicPage);

        String page = "shop:pages/seller/GoodsAdd/goodsAddStep2ImageList.html";
        return FisUtils.page(page);
    }

    @RequestMapping("/addstep/two/imagesdesc")
    public String twoImagesDesc(Model model,
                                HttpServletRequest request,
                                @RequestParam(value = "sort", required = false) String sortType,
                                @RequestParam(value = "curpage", required = false) Integer curPage,
                                @RequestParam(value = "class_id", required = false) String classId,
                                HttpServletResponse response) {
        List<AlbumClass> acList = albumClassService.getCurrentAlbumClass();
        model.addAttribute("P_ALBUM_CLASS", acList);

        Page<AlbumPic> albumPicPage = null;
        if (StringUtils.hasText(classId)) {
            albumPicPage = albumPicService.findByAclassId(classId, sortType, curPage, 14);
            model.addAttribute("P_CLASSID", classId);
        } else {
            albumPicPage = albumPicService.findAll(sortType, curPage, 14);
        }
        model.addAttribute("P_ALBUM_PICLIST", albumPicPage.getContent());
        model.addAttribute("P_PAGE_SHOW", albumPicPage);

        String page = "shop:pages/seller/GoodsAdd/goodsAddStep2ImageDescList.html";
        return FisUtils.page(page);
    }

    @RequestMapping("/image/upload")
    public
    @ResponseBody
    Map imageUpload(@RequestParam(value = "category_id", required = false) String id,
                    @RequestParam(value = "file") MultipartFile file,
                    HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasText(id)) {
            AlbumClass albumClass = albumClassService.getCurrentDefalue();
            id = albumClass.getId();
        }
        AlbumPic album = albumPicService.saveAlbumPic(id, file);
        Map<String, String> map = new HashMap<>();
        map.put("thumb_name", request.getContextPath() + "/att/download/" + album.getApicCover());
        map.put("name", album.getApicCover());
        return map;
    }

    @RequestMapping(value = "/addstep/savegoods", method = RequestMethod.POST)
    public String saveGoods(@Valid GoodsCommon goodsCommon, BindingResult result, Model model,
                            GoodsModel goodsModel,
                            HttpServletRequest request,
                            HttpServletResponse response) {
        if (result.hasErrors()) {
            ExceprionUtils.BindingResultError(result);
        }
        GoodsCommon newGoods = goodsCommonService.saveGoodsCommonAndGodds(goodsCommon,goodsModel);
        return "redirect:/se/goods/addstep/editimages?goodsid=" + newGoods.getId();
    }

    @RequestMapping(value = "/addstep/editimages")
    public String editImages(Model model,
                             @RequestParam(value = "goodsid") String goodsId,
                             HttpServletRequest request,
                             HttpServletResponse response) {

        List<GoodsImages> goodsImagesList = goodsImagesService.findByGoodsIdAndStoreId(goodsId);
        //GoodsImages defaultGoodsImages= goodsImagesService.getDefautlGoodsImages(goodsImagesList);
        model.addAttribute("P_GOODSIMAGES_LIST", goodsImagesList);
        //model.addAttribute("P_GOODSIMAGES_DEFAULT", defaultGoodsImages);


        model.addAttribute("P_CURRENT_OP", "GoodsAdd");
        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "商品发布");
        model.addAttribute("P_STEP", 3);
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_CURRENT_TOP", "goods");
        model.addAttribute("P_VIEW", "shop:pages/seller/GoodsAdd/goodsAddStep3.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

    @RequestMapping("/addstep/three/images")
    public String threeImages(Model model,
                              HttpServletRequest request,
                              @RequestParam(value = "sort", required = false) String sortType,
                              @RequestParam(value = "curpage", required = false) Integer curPage,
                              @RequestParam(value = "class_id", required = false) String classId,
                              HttpServletResponse response) {
        List<AlbumClass> acList = albumClassService.getCurrentAlbumClass();
        model.addAttribute("P_ALBUM_CLASS", acList);

        Page<AlbumPic> albumPicPage = null;
        if (StringUtils.hasText(classId)) {
            albumPicPage = albumPicService.findByAclassId(classId, sortType, curPage, 12);
            model.addAttribute("P_CLASSID", classId);
        } else {
            albumPicPage = albumPicService.findAll(sortType, curPage, 12);
        }
        model.addAttribute("P_ALBUM_PICLIST", albumPicPage.getContent());
        model.addAttribute("P_PAGE_SHOW", albumPicPage);

        String page = "shop:pages/seller/GoodsAdd/goodsAddStep3ImageList.html";
        return FisUtils.page(page);
    }

    @RequestMapping("/addstep/saveimages")
    public String saveImages(Model model,
                             @RequestParam(value = "goodsid") String goodsId,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        GoodsImages[] goodsImagesArray = this.getGoodsImagesArray(request);
        goodsImagesService.saevDefaultImage(goodsImagesArray, goodsId);
        goodsImagesService.save(goodsId, goodsImagesArray);
        return "redirect:/se/goods/addstep/four";
    }

    private GoodsImages[] getGoodsImagesArray(HttpServletRequest request) {
        GoodsImages[] goodsImagesArray = new GoodsImages[5];
        for (int i = 0; i < 5; i++) {
            GoodsImages goodsImages = new GoodsImages();
            String name = "img[0][" + i + "][name]";
            if (request.getParameterMap().containsKey(name)) {
                String isDefault = "img[0][" + i + "][default]";
                String sort = "img[0][" + i + "][sort]";
                goodsImages.setGoodsImage(request.getParameter(name));
                if (request.getParameterMap().containsKey(isDefault)) {
                    String sd = request.getParameter(isDefault);
                    if (StringUtils.replaceBlank(sd).equals("1")) {
                        goodsImages.setIsDefault(1);
                    }
                }
                if (request.getParameterMap().containsKey(sort)) {
                    int iSort = 0;
                    try {
                        iSort = Integer.valueOf(request.getParameter(sort));
                    } catch (Exception e) {
                        // TODO: handle exception
                    }
                    goodsImages.setGoodsImageSort(iSort);
                }
            }
            goodsImagesArray[i] = goodsImages;
        }
        return goodsImagesArray;
    }

    @RequestMapping("/addstep/four")
    public String four(Model model,
                       HttpServletRequest request,
                       HttpServletResponse response) {

        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "商品发布");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_STEP", 4);
        model.addAttribute("P_CURRENT_OP", "GoodsAdd");
        model.addAttribute("P_CURRENT_TOP", "goods");
        model.addAttribute("P_VIEW", "shop:pages/seller/GoodsAdd/goodsAddStep4.html.jsp");
        return FisUtils.page("shop:widget/tpl/seller/framework.html");
    }

}
