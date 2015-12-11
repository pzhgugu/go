package com.ansteel.shop.goods.web;

import java.text.MessageFormat;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import com.ansteel.core.utils.JsonUtils;
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
import org.springframework.web.multipart.MultipartHttpServletRequest;

@Controller
@RequestMapping(value = ShopConstant.SELLER + "/goods")
public class SellerGoodsController {

    @Autowired
    GoodsSpecService goodsSpecService;

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
    GoodsService goodsService;

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

    @RequestMapping("/editgoods")
    public String editGoods(Model model,
                      @RequestParam("commonid") String commonId,
                      HttpServletRequest request,
                      HttpServletResponse response) {
        GoodsCommon goodsCommon = goodsCommonService.findOneByStoreIdAndId(commonId);
        Assert.notNull(goodsCommon,commonId+",没有这个商品！");
        goodsCommon.setGoodsStorage(goodsService.grossInventory(goodsCommon.getId()));
        GoodsClass goodsClass = goodsClassService.findOne(goodsCommon.getGcId());
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
        String goodsStcids=goodsCommon.getGoodsStcids();
        if(StringUtils.hasText(goodsStcids)){
            model.addAttribute("P_STC_IDS",goodsStcids.split(","));
        }

        //选中规格
        String specName=goodsCommon.getSpecName();
        List<GoodsSpecValueSelectListModel> gsvslList =JsonUtils.readValue(specName,GoodsSpecValueSelectListModel.class);
        model.addAttribute("P_GOODSSPEC_SELECT",gsvslList);
        //获取当前店铺、当前商品分类所有规格
        List<GoodsSpecValue> goodsSpecValueList=goodsSpecValueService.findByCurrentStoreIdAndGcId(goodsCommon.getGcId());
        Map<String,String> goodsSpecValueMap = new HashMap<>();
        for(GoodsSpecValue goodsSpecValue:goodsSpecValueList){
            goodsSpecValueMap.put(goodsSpecValue.getId(),goodsSpecValue.getName());
        }
        model.addAttribute("P_GOODSSPECVALUE_ALL",goodsSpecValueMap);


        //选中规格值，表格选项
        String specValue=goodsCommon.getSpecValue();
        if(StringUtils.hasText(specValue)) {
            List<GoodsSpecValueStockModel> stockList = JsonUtils.readValue(specValue, GoodsSpecValueStockModel.class);
            model.addAttribute("P_GOODSSPECVALUE_SELECT", stockList);
        }


        //选中属性
        String goodsAttr=goodsCommon.getGoodsAttr();
        List<GoodsAttrModel> attrList=JsonUtils.readValue(goodsAttr, GoodsAttrModel.class);
        model.addAttribute("P_GOODSATTR_SELECT",attrList);

        model.addAttribute("P_GOODSCLASS_LISTNAME", this.getGoodsClassListName(goodsClass));
        model.addAttribute("P_GOODSCLASS", goodsClass);
        model.addAttribute("P_GOODSCOMMON",goodsCommon);

        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        nav.put("n3", "出售中的商品");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_STEP", 2);
        model.addAttribute("P_CURRENT_OP", "Online");
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
                    @RequestParam(value = "name") String fileName,
                    HttpServletRequest request, HttpServletResponse response) {
        if (!StringUtils.hasText(id)) {
            AlbumClass albumClass = albumClassService.getCurrentDefalue();
            id = albumClass.getId();
        }
        MultipartHttpServletRequest mhs = (MultipartHttpServletRequest) request;
        MultipartFile file=mhs.getFile(fileName);
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
        String commonId=goodsCommon.getId();
        GoodsCommon newGoods = goodsCommonService.saveGoodsCommonAndGodds(goodsCommon,goodsModel);
        if(StringUtils.hasText(commonId)){
            return "redirect:/se/goodsonline/list";
        }
        return "redirect:/se/goods/addstep/editimages?goodsid=" + newGoods.getId();
    }

    @RequestMapping(value = "/addstep/editimages")
    public String editImages(Model model,
                             @RequestParam(value = "goodsid") String goodsId,
                             @RequestParam(value = "edit",required = false) Integer edit,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        //获取颜色id
        String colorId=goodsSpecService.getColorId();
        GoodsCommon goodsCommon = goodsCommonService.findOneByStoreIdAndId(goodsId);
        Assert.notNull(goodsCommon,goodsId+"，无效商品id！");
        String specName=goodsCommon.getSpecName();
        List<GoodsSpecValueSelectListModel> gsvslList = JsonUtils.readValue(specName, GoodsSpecValueSelectListModel.class);
        //颜色规格
        String[] spvIdArray=null;
        for(GoodsSpecValueSelectListModel gsvslm:gsvslList){
            if(gsvslm.getSpId().equals(colorId)){
                spvIdArray=gsvslm.getSpvId();
            }
        }
        if(spvIdArray==null||spvIdArray.length<1){
            if(edit!=null&&edit==1){
                return "redirect:/se/goodsonline/list";
            }
            return "redirect:/se/goods//addstep/four";
        }
        //颜色规格
        List<GoodsSpecValue> goodsSpecValueList=goodsSpecValueService.findById(spvIdArray);

        List<GoodsImages> goodsImagesList = goodsImagesService.findByGoodsIdAndStoreId(goodsId);
        Map<String,GoodsImages[]> imagesMap = new HashMap<>();
        for(GoodsSpecValue goodsSpecValue:goodsSpecValueList){
            String goodsColorId=goodsSpecValue.getId();
            GoodsImages[] GoodsImagesArray = new GoodsImages[5];
            int i=0;
            for(GoodsImages g:goodsImagesList){
                if(g.getColorId().equals(goodsColorId)){
                    GoodsImagesArray[i]=g;
                    i++;
                }
            }
            imagesMap.put(goodsColorId,GoodsImagesArray);
        }
        model.addAttribute("P_IMAGES_MAP", imagesMap);


        model.addAttribute("P_COLOR_LIST", goodsSpecValueList);
        model.addAttribute("P_GOODSCOMMON", goodsCommon);


        Map<String, String> nav = new HashMap<>();
        nav.put("n1", "商家管理中心");
        nav.put("n2", "商品");
        if(edit!=null&&edit==1){
            nav.put("n3", "出售中的商品");
            model.addAttribute("P_CURRENT_OP", "Online");
        }else {
            nav.put("n3", "商品发布");
            model.addAttribute("P_STEP", 3);
            model.addAttribute("P_CURRENT_OP", "GoodsAdd");
        }

        model.addAttribute("P_CURRENT_TOP", "goods");
        model.addAttribute("P_NAV", nav);
        model.addAttribute("P_EDIT", edit);
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
    @RequestParam(value = "edit", required = false) Integer edit,
                             ColorImagesModel colorImagesModel,
                             HttpServletRequest request,
                             HttpServletResponse response) {
        goodsImagesService.save(colorImagesModel);
        if(edit!=null&&edit==1){
            return "redirect:/se/goodsonline/list";
        }
        return "redirect:/se/goods/addstep/four";
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
