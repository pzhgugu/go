package com.ansteel.shop.goods.domain;

import com.ansteel.core.constant.Constants;
import com.ansteel.core.domain.BaseEntity;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

/**
 * 商品咨询表
 */
@Entity
@Table(name = Constants.G_TABLE_PREFIX + "goods_consult")
public class GoodsConsult extends BaseEntity {
    /**
     * 商品ID
     */
    @NotEmpty(message = "商品ID不能为空")
    private String goodsId;

    /**
     * 商品名称
     */
    @NotEmpty(message = "商品名称不能为空")
    private String  goodsName;
    /**
     * 咨询发布者会员编号(0：游客)
     */
    private String memberId;
    /**
     * 会员名称
     */
    private String memberName;
    /**
     * 店铺编号
     */
    @NotEmpty(message = "店铺编号不能为空")
    private String storeId;
    /**
     * 咨询发布者邮箱
     */
    private String email;
    /**
     *咨询内容
     */
    @Lob
    private String consultContent;
    /**
     *咨询回复内容
     */
    @Lob
    private String consultReply;
    /**
     * 咨询回复时间
     */
    private Date consultReplyTime;
    /**
     * 0表示不匿名 1表示匿名
     */
    private Integer isAnonymous;

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getConsultContent() {
        return consultContent;
    }

    public void setConsultContent(String consultContent) {
        this.consultContent = consultContent;
    }

    public String getConsultReply() {
        return consultReply;
    }

    public void setConsultReply(String consultReply) {
        this.consultReply = consultReply;
    }

    public Date getConsultReplyTime() {
        return consultReplyTime;
    }

    public void setConsultReplyTime(Date consultReplyTime) {
        this.consultReplyTime = consultReplyTime;
    }

    public Integer getIsAnonymous() {
        return isAnonymous;
    }

    public void setIsAnonymous(Integer isAnonymous) {
        this.isAnonymous = isAnonymous;
    }
}
