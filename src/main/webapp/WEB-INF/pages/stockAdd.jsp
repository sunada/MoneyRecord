<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>证券操作</title>
        <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
        <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(document).ready(function() {
                var risk = getUrlParam('risk');
                if (risk == "HIGH") {
                    $("#high").attr("checked", "checked");
                } else if (risk == "LOW") {
                    $("#low").attr("checked", "checked");
                } else if (risk == "MIDLOW") {
                    $("#midlow").attr("checked", "checked");
                } else if (risk == "MID") {
                    $("#mid").attr("checked", "checked");
                } else {
                    $("#midhigh").attr("checked", "checked");
                }
            });

            function getUrlParam(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(|&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = window.location.search.substr(1).match(reg); //匹配目标参数
                if (r != null) return unescape(r[2]); return null; //返回参数值
            }

        </script>
    </head>
    <body>

    <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li><a href="/inAll">总览</a></li>
                <li><a href="/balance">收支表</a> </li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li class="active"><a href="/stock/stockAdd">证券操作</a></li>
            </ul>
        </div>
    </nav>

        <form action="/stock/stockSave" method="post" accept-charset="utf-8" role="form" name="aip">
            <%!
                String blanknull(String s){
                    return (s == null) ? "" : s;
                }
            %>

            <div class="form-group">
                <label for="code">股票代码：</label>
                <input type="text" name="code" value="<%=blanknull(request.getParameter("code"))%>"/>
            </div>

            <div class="form-group">
                <label for="name">股票名称：</label>
                <input type="text" name="name" value="<%=blanknull(request.getParameter("name"))%>"/>
            </div>

            <div class="form-group">
                <label for="belongTo">所属账户：</label>
                <input type="radio" name="belongTo" value="华666600196751" checked/>华666600196751
                <input type="radio" name="belongTo" value="华010600052829"/>华010600052829
                <input type="radio" name="belongTo" value="国金39997769"/>国金39997769
                <input type="radio" name="belongTo" value="积7XJ11330"/>积7XJ11330
            </div>


            <c:set var="dealType" value='<%=request.getParameter("dealType")%>' />
            <div class="form-group">
                <label for="dealType"> 买入或赎回：</label>
                <c:choose>
                    <c:when test="${dealType=='SBUY'}">
                        <input type="radio" name="dealType" value="SBUY" checked/>买入
                        <input type="radio" name="dealType" value="SSELL"/>卖出
                    </c:when>
                    <c:when test="${dealType=='SSELL'}">
                        <input type="radio" name="dealType" value="SBUY"/>买入
                        <input type="radio" name="dealType" value="SSELL" checked/>卖出
                    </c:when>
                    <c:otherwise>
                        <input type="radio" name="dealType" value="SBUY"/>买入
                        <input type="radio" name="dealType" value="SSELL"/>卖出
                    </c:otherwise>
                </c:choose>

            </div>

            <div class="form-group">
                <label for="cost">交易价：</label>
                <input type="text" name="cost" id="cost">元/美元
            </div>

            <%--<div class="form-group">--%>
                <%--<label for="current">现价：</label>--%>
                <%--<input type="text" name="current" id="current">--%>
            <%--</div>--%>

            <div class="form-group">
                <label for="share">份额：</label>
                <input type="text" name="share" id="share">
            </div>

            <div class="form-group">
                <label for="date">交易日期：</label>
                <input type="text" name="date" id="date">
            </div>

            <%--<div class="form-group">--%>
                <%--<label for="amount">市值：</label>--%>
                <%--<input type="text" name="amount" id="amount">--%>
            <%--</div>--%>

            <div class="form-group">
                <label for="risk"> 风险类型：</label>
                <%--LOW(0, "low"), MIDLOW(1, "midlow"), MID(2, "mid"), MIDHIGH(3, "midhigh"),HIGH(4, "high");--%>
                <input type="radio" name="risk" value="LOW"/>低
                <input type="radio" name="risk" value="MID"/>中等
                <input type="radio" name="risk" value="HIGH"/>高
            </div>

            <input type="submit" value="保存" />
        </form>

    </body>
</html>