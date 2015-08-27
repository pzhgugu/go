<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib prefix='c' uri='http://java.sun.com/jstl/core_rt' %>
<%@ taglib uri="/fis" prefix="fis" %>

<!DOCTYPE html>
<fis:html mapDir="/map">
    <HEAD>
        <title>十分抱歉，您要查看的网页出错啦！</title>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <fis:require id="common:widget/exception/error.css"/>
        <fis:styles/>

        <META content="Microsoft FrontPage 5.0" name=GENERATOR>
    </HEAD>
    <BODY>
    <P align=center>　</P>

    <P align=center>　</P>
    <TABLE cellSpacing=0 cellPadding=0 width=540 align=center border=0>
        <TBODY>
        <TR>
            <TD vAlign=top height=270>
                <DIV align=center><BR>
                    <!--<IMG height=211 src="${S_URL_R}/images/error.gif"
      width=329>  -->
                    <div class=imgError></div>
                    <BR><BR>
                    <TABLE cellSpacing=0 cellPadding=0 width="80%" border=0>
                        <TBODY>
                        <TR>
                            <TD><FONT class=p2>&nbsp;&nbsp;&nbsp; <FONT color=#ff0000>
                                <!-- <IMG  height=13 src="${S_URL_R}/images/emessage.gif" width=12> -->
                                <div class=imgEmessage></div>
                                &nbsp;页面出错的原因是：</FONT></FONT>
                            </TD>
                        </TR>
                        <TR>
                            <BR>
                            <TD height=40>&nbsp;&nbsp;&nbsp;&nbsp;<font size="+1"> ${ERROR_MESSAGE}<font></TD>
                        <TR>
                            <TD>
                                <P><FONT color=#000000><BR>　　</FONT>
                                    　</P></TD>
                        </TR>
                        </TBODY>
                    </TABLE>
                </DIV>
            </TD>
        </TR>
        <TR>
            <TD height=5></TD>
        <TR>
            <TD align=middle>
                <CENTER>
                    <TABLE cellSpacing=0 cellPadding=0 width=480 border=0>
                        <TBODY>
                        <TR>
                            <TD width=6></TD>
                            <TD>
                                <DIV align=center><FONT class=p6>　　 <A
                                        href="javascript:history.go(-1)">返回出错页</A>　 　　</FONT></DIV>
                            </TD>
                            <TD width=7></TD>
                        </TR>
                        </TBODY>
                    </TABLE>
                </CENTER>
            </TD>
        </TR>
        </TBODY>
    </TABLE>
    <P align=center>　</P>

    <P align=center>　</P>

    </body>
</fis:html>