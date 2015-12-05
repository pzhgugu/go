<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/fis" prefix="fis" %>
<c:set var="S_URL" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<fis:html mapDir="/map">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <meta charset="utf-8">
        <title>商家缴费</title>
        <fis:require id="shop:styles/base.css"/>
        <fis:require id="shop:scripts/dialog/dialog.css"/>
        <fis:require id="shop:styles/store_joinin.css"/>

        <fis:styles/>
    </head>


    <body>
    <fis:block url="shop:pages/client/store/header.html.jsp"/>

    <div class="container wrapper">
        <div class="joinin-pay">
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="6">公司及联系人信息</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">公司名称：</th>
                    <td colspan="5">${P_STOREJOININ.companyName}</td>
                </tr>
                <tr>
                    <th class="w150">公司所在地：</th>
                    <td>${P_STOREJOININ.companyAddress}</td>
                    <th class="w150">公司详细地址：</th>
                    <td colspan="3">${P_STOREJOININ.companyAddressDsetail}</td>
                </tr>
                <tr>
                    <th class="w150">公司电话：</th>
                    <td>${P_STOREJOININ.companyPhone}</td>
                    <th class="w150">员工总数：</th>
                    <td>${P_STOREJOININ.companyEmployeeCount}&nbsp;人</td>
                    <th class="w150">注册资金：</th>
                    <td>${P_STOREJOININ.companyRegisteredCapital}&nbsp;万元</td>
                </tr>
                <tr>
                    <th class="w150">联系人姓名：</th>
                    <td>${P_STOREJOININ.contactsName}</td>
                    <th class="w150">联系人电话：</th>
                    <td>${P_STOREJOININ.contactsPhone}</td>
                    <th class="w150">电子邮箱：</th>
                    <td>${P_STOREJOININ.contactsEmail}</td>
                </tr>
                </tbody>
            </table>
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="2">营业执照信息（副本）</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">营业执照号：</th>
                    <td>${P_STOREJOININ.businessLicenceNumber}</td>
                </tr>
                <tr>

                    <th class="w150">营业执照所在地：</th>
                    <td>${P_STOREJOININ.businessLicenceAddress}</td>
                </tr>
                <tr>

                    <th class="w150">营业执照有效期：</th>
                    <td>${P_STOREJOININ.businessLicenceStart} - ${P_STOREJOININ.businessLicenceEnd}</td>
                </tr>
                <tr>
                    <th class="w150">法定经营范围：</th>
                    <td colspan="20">${P_STOREJOININ.businessSphere}</td>
                </tr>
                <tr>
                    <th class="w150">营业执照号<br>
                        电子版：
                    </th>
                    <td colspan="20"><a href="${S_URL}/att/download/${P_STOREJOININ.businessLicenceNumberElectronic}"
                                        nctype="nyroModal"> <img alt=""
                                                                 src="${S_URL}/att/download/${P_STOREJOININ.businessLicenceNumberElectronic}">
                    </a></td>
                </tr>
                </tbody>
            </table>
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="2">组织机构代码证</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">组织机构代码：</th>
                    <td>${P_STOREJOININ.organizationCode}</td>
                </tr>
                <tr>
                    <th class="w150">组织机构代码证<br> 电子版：</th>
                    <td><a href="${S_URL}/att/download/${P_STOREJOININ.organizationCodeElectronic}" nctype="nyroModal">
                        <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.organizationCodeElectronic}"> </a></td>
                </tr>
                </tbody>
            </table>
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="2">一般纳税人证明：</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">一般纳税人证明：</th>
                    <td><a href="${S_URL}/att/download/${P_STOREJOININ.generalTaxpayer}" nctype="nyroModal">
                        <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.generalTaxpayer}"> </a></td>
                </tr>
                </tbody>
            </table>
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="2">开户银行信息：</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">银行开户名：</th>
                    <td>${P_STOREJOININ.bankAccountName}</td>
                </tr>
                <tr>
                    <th class="w150">公司银行账号：</th>
                    <td>${P_STOREJOININ.bankAccountNumber}</td>
                </tr>
                <tr>
                    <th class="w150">开户银行支行名称：</th>
                    <td>${P_STOREJOININ.bankName}</td>
                </tr>
                <tr>
                    <th class="w150">支行联行号：</th>
                    <td>${P_STOREJOININ.bankCode}</td>
                </tr>
                <tr>
                    <th class="w150">开户银行所在地：</th>
                    <td colspan="20">${P_STOREJOININ.bankAddress}</td>
                </tr>
                <tr>
                    <th class="w150">开户银行许可证<br>电子版：</th>
                    <td colspan="20"><a href="${S_URL}/att/download/${P_STOREJOININ.bankLicenceElectronic}"
                                        nctype="nyroModal">
                        <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.bankLicenceElectronic}"> </a></td>
                </tr>
                </tbody>

            </table>
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="2">结算账号信息：</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">银行开户名：</th>
                    <td>${P_STOREJOININ.settlementBankAccountName}</td>
                </tr>
                <tr>
                    <th class="w150">公司银行账号：</th>
                    <td>${P_STOREJOININ.settlementBankAccountNumber}</td>
                </tr>
                <tr>
                    <th class="w150">开户银行支行名称：</th>
                    <td>${P_STOREJOININ.settlementBankName}</td>
                </tr>
                <tr>
                    <th class="w150">支行联行号：</th>
                    <td>${P_STOREJOININ.settlementBankCode}</td>
                </tr>
                <tr>
                    <th class="w150">开户银行所在地：</th>
                    <td>${P_STOREJOININ.settlementBankAddress}</td>
                </tr>
                </tbody>

            </table>
            <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                <thead>
                <tr>
                    <th colspan="2">税务登记证</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <th class="w150">税务登记证号：</th>
                    <td>${P_STOREJOININ.taxRegistrationCertificate}</td>
                </tr>
                <tr>
                    <th class="w150">纳税人识别号：</th>
                    <td>${P_STOREJOININ.taxpayerId}</td>
                </tr>
                <tr>
                    <th class="w150">税务登记证号<br>
                        电子版：
                    </th>
                    <td><a href="${S_URL}/att/download/${P_STOREJOININ.taxRegistrationCertificateElectronic}"
                           nctype="nyroModal">
                        <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.taxRegistrationCertificateElectronic}">
                    </a></td>
                </tr>
                </tbody>
            </table>
            <form enctype="multipart/form-data" method="post" action="${S_URL}/storejoinin/paysave"
                  id="form_paying_money_certificate">
                <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                    <thead>
                    <tr>
                        <th colspan="2">店铺经营信息</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr>
                        <th class="w150">卖家帐号：</th>
                        <td>${P_STOREJOININ.sellerName}</td>
                    </tr>
                    <tr>
                        <th class="w150">店铺名称：</th>
                        <td>${P_STOREJOININ.storeName}</td>
                    </tr>
                    <tr>
                        <th class="w150">店铺等级：</th>
                        <td>${P_STOREJOININ.sgName}</td>
                    </tr>
                    <tr>
                        <th class="w150">店铺分类：</th>
                        <td>${P_STOREJOININ.scName}</td>
                    </tr>
                    <tr>
                        <th class="w150">经营类目：</th>
                        <td>
                            <table cellspacing="0" cellpadding="0" border="0" class="type" id="table_category">
                                <thead>
                                <tr>
                                    <th>分类1</th>
                                    <th>分类2</th>
                                    <th>分类3</th>
                                </tr>
                                </thead>
                                <tbody>
                                <c:forEach items="${P_CLASS_LIST}" var="CLASS">
                                    <tr>
                                        <c:forEach items="${CLASS.jmcs}" var="c">
                                            <td>${c.name}</td>
                                        </c:forEach>
                                    </tr>
                                </c:forEach>

                                </tbody>
                            </table>
                        </td>
                    </tr>
                    <tr>
                        <th class="w150">审核意见：</th>
                        <td colspan="2">${P_STOREJOININ.joininMessage}</td>
                    </tr>
                    </tbody>
                </table>
                <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
                    <tbody>
                    <tr>
                        <th class="w150">上传付款凭证：</th>
                        <td>
                            <input type="file" name="paying_money_certificate"><span></span>
                        </td>
                    </tr>
                    <tr>
                        <th class="w150">备注：</th>
                        <td>
                            <textarea cols="30" rows="10" name="paying_money_certificate_explain"></textarea>
                            <span></span>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </form>
            <div class="bottom"><a class="btn" href="javascript:;" id="btn_paying_money_certificate">提交</a></div>
        </div>
    </div>


    <fis:block url="cms:widget/tpl/button.html.jsp"/>
    <fis:block url="cms:widget/tpl/footer.html.jsp"/>


    <fis:require id="common:widget/jquery/jquery.js"/>
    <fis:require id="common:widget/jquery/jquery-browser.js"/>
    <fis:require id="common:widget/jquery/waypoints.js"/>
    <fis:require id="common:widget/jquery/jquery.validation.js"/>
    <fis:require id="common:widget/jquery-ui/jquery.ui.js"/>
    <fis:require id="shop:scripts/area_array.js"/>
    <fis:require id="shop:scripts/common.js"/>
    <fis:scripts/>
    <script type="text/javascript">
        $(document).ready(function () {

            $('#form_paying_money_certificate').validate({
                errorPlacement: function (error, element) {
                    element.nextAll('span').first().after(error);
                },
                rules: {
                    paying_money_certificate: {
                        required: true
                    },
                    paying_money_certificate_explain: {
                        maxlength: 100
                    }
                },
                messages: {
                    paying_money_certificate: {
                        required: '请选择上传付款凭证'
                    },
                    paying_money_certificate_explain: {
                        maxlength: jQuery.validator.format("最多{0}个字")
                    }
                }
            });

            $('#btn_paying_money_certificate').on('click', function () {
                $('#form_paying_money_certificate').submit();
            });

        });
    </script>

    </body>
</fis:html>