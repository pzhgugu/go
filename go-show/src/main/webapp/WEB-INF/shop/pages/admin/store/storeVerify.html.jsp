<%@page language="Java" contentType="text/html; charset=UTF-8"
        pageEncoding="UTF-8" %>
<%@ taglib prefix='spring' uri="http://www.springframework.org/tags" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<!DOCTYPE html>
<fis:html mapDir="/map">
    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <title>审核</title>

        <fis:require id="shop:styles/skin_0.css"/>
        <fis:styles/>


    </head>
    <body>

    <div id="append_parent"></div>
    <div id="ajaxwaitid"></div>

    <div class="page">
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">公司及联系人信息</th>
            </tr>
            </thead>
            <tbody>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                <th class="w150">公司名称：</th>
                <td colspan="20">${P_STOREJOININ.companyName}</td>
            </tr>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                <th>公司所在地：</th>
                <td>${P_STOREJOININ.companyAddress}</td>
                <th>公司详细地址：</th>
                <td colspan="20">${P_STOREJOININ.companyAddressDsetail}</td>
            </tr>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                <th>公司电话：</th>
                <td>${P_STOREJOININ.companyPhone}</td>
                <th>员工总数：</th>
                <td>${P_STOREJOININ.companyEmployeeCount}&nbsp;人</td>
                <th>注册资金：</th>
                <td>${P_STOREJOININ.companyRegisteredCapital}&nbsp;万元</td>
            </tr>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                <th>联系人姓名：</th>
                <td>${P_STOREJOININ.contactsName}</td>
                <th>联系人电话：</th>
                <td>${P_STOREJOININ.contactsPhone}</td>
                <th>电子邮箱：</th>
                <td>${P_STOREJOININ.contactsEmail}</td>
            </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">营业执照信息（副本）</th>
            </tr>
            </thead>
            <tbody>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                <th class="w150">营业执照号：</th>
                <td>${P_STOREJOININ.businessLicenceNumber}</td>
            </tr>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">

                <th>营业执照所在地：</th>
                <td>${P_STOREJOININ.businessLicenceAddress}</td>
            </tr>
            <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">

                <th>营业执照有效期：</th>
                <td>${P_STOREJOININ.businessLicenceStart} - ${P_STOREJOININ.businessLicenceEnd}</td>
            </tr>
            <tr>
                <th>法定经营范围：</th>
                <td colspan="20">${P_STOREJOININ.businessSphere}</td>
            </tr>
            <tr>
                <th>营业执照号<br>
                    电子版：
                </th>
                <td colspan="20"><a href="${S_URL}/att/download/${P_STOREJOININ.businessLicenceNumberElectronic}"
                                    nctype="nyroModal">
                    <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.businessLicenceNumberElectronic}"> </a></td>
            </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">组织机构代码证</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th>组织机构代码：</th>
                <td colspan="20">${P_STOREJOININ.organizationCode}</td>
            </tr>
            <tr>
                <th>组织机构代码证<br> 电子版：</th>
                <td colspan="20"><a href="${S_URL}/att/download/${P_STOREJOININ.organizationCodeElectronic}"
                                    nctype="nyroModal">
                    <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.organizationCodeElectronic}"> </a></td>
            </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">一般纳税人证明：</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th>一般纳税人证明：</th>
                <td colspan="20"><a href="${S_URL}/att/download/${P_STOREJOININ.generalTaxpayer}" nctype="nyroModal">
                    <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.generalTaxpayer}"> </a></td>
            </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">开户银行信息：</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th class="w150">银行开户名：</th>
                <td>${P_STOREJOININ.bankAccountName}</td>
            </tr>
            <tr>
                <th>公司银行账号：</th>
                <td>${P_STOREJOININ.bankAccountNumber}</td>
            </tr>
            <tr>
                <th>开户银行支行名称：</th>
                <td>${P_STOREJOININ.bankName}</td>
            </tr>
            <tr>
                <th>支行联行号：</th>
                <td>${P_STOREJOININ.bankCode}</td>
            </tr>
            <tr>
                <th>开户银行所在地：</th>
                <td colspan="20">${P_STOREJOININ.bankAddress}</td>
            </tr>
            <tr>
                <th>开户银行许可证<br>电子版：</th>
                <td colspan="20"><a href="${S_URL}/att/download/${P_STOREJOININ.bankLicenceElectronic}"
                                    nctype="nyroModal">
                    <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.bankLicenceElectronic}"> </a></td>
            </tr>
            </tbody>

        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">结算账号信息：</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th class="w150">银行开户名：</th>
                <td>${P_STOREJOININ.settlementBankAccountName}</td>
            </tr>
            <tr>
                <th>公司银行账号：</th>
                <td>${P_STOREJOININ.settlementBankAccountNumber}</td>
            </tr>
            <tr>
                <th>开户银行支行名称：</th>
                <td>${P_STOREJOININ.settlementBankName}</td>
            </tr>
            <tr>
                <th>支行联行号：</th>
                <td>${P_STOREJOININ.settlementBankCode}</td>
            </tr>
            <tr>
                <th>开户银行所在地：</th>
                <td>${P_STOREJOININ.settlementBankAddress}</td>
            </tr>
            </tbody>

        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">税务登记证</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <th class="w150">税务登记证号：</th>
                <td>${P_STOREJOININ.taxRegistrationCertificate}</td>
            </tr>
            <tr>
                <th>纳税人识别号：</th>
                <td>${P_STOREJOININ.taxpayerId}</td>
            </tr>
            <tr>
                <th>税务登记证号<br>
                    电子版：
                </th>
                <td><a href="${S_URL}/att/download/${P_STOREJOININ.taxRegistrationCertificateElectronic}"
                       nctype="nyroModal">
                    <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.taxRegistrationCertificateElectronic}"> </a>
                </td>
            </tr>
            </tbody>
        </table>
        <table cellspacing="0" cellpadding="0" border="0" class="store-joinin">
            <thead>
            <tr>
                <th colspan="20">店铺经营信息</th>
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
                <th>店铺等级：</th>
                <td>${P_STOREJOININ.sgName}</td>
            </tr>
            <tr>
                <th>店铺分类：</th>
                <td>${P_STOREJOININ.scName}</td>
            </tr>
            <tr>
                <th>经营类目：</th>
                <td colspan="2">
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

            <c:if test="${P_STOREJOININ.joininState==11}">
                <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                    <th>付款凭证：</th>
                    <td><a href="${S_URL}/att/download/${P_STOREJOININ.payingMoneyCertificate}" nctype="nyroModal">
                        <img alt="" src="${S_URL}/att/download/${P_STOREJOININ.payingMoneyCertificate}"> </a></td>
                </tr>
                <tr style="background: rgb(255, 255, 255) none repeat scroll 0% 0%;">
                    <th>付款凭证说明：</th>
                    <td>${P_STOREJOININ.payingMoneyCertificateExplain}</td>
                </tr>
            </c:if>
            <c:if test="${P_STOREJOININ.joininState==10}">
            <form method="post" action="${S_URL}/admin/storemanage/verifyinfo" id="form_store_verify">
                </c:if>
                <c:if test="${P_STOREJOININ.joininState==11}">
                <form method="post" action="${S_URL}/admin/storemanage/verifypayment" id="form_store_verify">
                    </c:if>
                    <input type="hidden" value="${P_STOREJOININ.id}" name="id">
                    <input type="hidden" name="verify_type" id="verify_type">
                    <tr>
                        <th>审核意见：</th>
                        <td colspan="2">
                            <c:if test="${param.query==1}">
                                <div style="color:red;" id="validation_message">${P_STOREJOININ.joininMessage}</div>
                            </c:if>
                            <c:if test="${param.query!=1}">
                                <textarea name="joininMessage"
                                          id="joininMessage">${P_STOREJOININ.joininMessage}</textarea>
                            </c:if>
                        </td>
                    </tr>
            </tbody>
        </table>
        <c:if test="${param.query!=1}">
            <div><a href="JavaScript:void(0);" class="btn" id="btn_fail"><span>拒绝</span></a>
                <a href="JavaScript:void(0);" class="btn" id="btn_pass"><span>通过</span></a></div>
        </c:if>
        </form>
    </div>

    <div></div>

    <fis:require id="common:widget/jquery.nyroModal/custom.min.js"/>
    <fis:require id="common:widget/jquery/jquery.poshytip.js"/>
    <fis:require id="common:widget/jquery/jquery.validation.js"/>
    <fis:scripts/>
    <script type="text/javascript">
        $(document).ready(function () {
            $('a[nctype="nyroModal"]').nyroModal();

            $('#btn_fail').on('click', function () {
                if ($('#joininMessage').val() == '') {
                    $('#joininMessage').text('请输入审核意见');
                    $('#joininMessage').show();
                    return false;
                } else {
                    $('#joininMessage').hide();
                }
                if (confirm('确认拒绝申请？')) {
                    $('#verify_type').val('fail');
                    $('#form_store_verify').submit();
                }
            });
            $('#btn_pass').on('click', function () {
                var valid = true;
                $('[nctype="commis_rate"]').each(function (commis_rate) {
                    rate = $(this).val();
                    if (rate == '') {
                        valid = false;
                        return false;
                    }

                    var rate = Number($(this).val());
                    if (isNaN(rate) || rate < 0 || rate >= 100) {
                        valid = false;
                        return false;
                    }
                });
                if (valid) {
                    $('#joininMessage').hide();
                    if (confirm('确认通过申请？')) {
                        $('#verify_type').val('pass');
                        $('#form_store_verify').submit();
                    }
                } else {
                    $('#joininMessage').text('请正确填写分佣比例');
                    $('#joininMessage').show();
                }
            });
        });
    </script>
    </body>

</fis:html>