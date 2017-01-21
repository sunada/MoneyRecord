<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>新增网贷</title>
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
                } else{
                    $("#mid").attr("checked", "checked");
                }
            });

//                var belongTo = decodeURI(getUrlParam('belongTo')); //这样也不能解决中文乱码的问题
//                if(belongTo == "汇添富基金官网"){
//                    alert(1);
//                    $("#tian").attr("checked","checked");
//                }else if(belongTo == "广发基金官网"){
//                    alert(2);
//                    $("#guang").attr("checked","checked");
//                }else if(belongTo == "银河基金官网"){
//                    alert(3);
//                    $("#yin").attr("checked","checked");
//                }else{
//                    alert(belongTo);
//                    $("#tian").attr("checked","checked");
//                }

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
                <li><a href="/stock">证券</a></li>
                <%--<li><a href="/fund/aipDisplay">定投</a></li>--%>
                <li><a href="/loan">网贷</a></li>
                <li class="active"><a href="/insurance">保险</a></li>
                <%--<li><a href="/fund/myFundAdd">基金操作</a></li>--%>
                <%--<li><a href="/loan/loanAdd">买入网贷</a></li>--%>
                <%--<li><a href="/stock/stockAdd">证券操作</a></li>--%>
                <%--<li><a href="/fund/updateFundNet">更新基金净值</a></li>--%>
                <%--<li><a href="/deal/updateAipDeal">更新定投交易</a></li>--%>
            </ul>
        </div>
    </nav>

        <form action="/loan/loanSave" method="post" accept-charset="utf-8" role="form" name="aip">

            <%!
                String blanknull(String s){
                    return (s == null) ? "" : s;
                }
            %>

            <div class="form-group">
                <label for="code">项目代码：</label>
                <input type="text" name="code" value="<%=blanknull(request.getParameter("code"))%>"/>
            </div>

            <div class="form-group">
                <label for="belongTo">平台名称：</label>
                <input type="radio" name="belongTo" value="陆金所" checked/>陆金所
                <input type="radio" name="belongTo" value="懒投资"/>懒投资
                <input type="radio" name="belongTo" value="积木盒子"/>积木盒子
                <input type="radio" name="belongTo" value="开鑫贷"/>开鑫贷
            </div>

            <div class="form-group">
                <label for="risk"> 风险类型：</label>
                <%--LOW(0, "low"), MIDLOW(1, "midlow"), MID(2, "mid"), MIDHIGH(3, "midhigh"),HIGH(4, "high");--%>
                <input type="radio" name="risk" value="LOW"/>低
                <input type="radio" name="risk" value="MID" checked/>中等
                <input type="radio" name="risk" value="HIGH"/>高
            </div>

            <div class="form-group">
                <label for="amount">投入本金：</label>
                <input type="text" name="amount" id="amount">元
            </div>

            <div class="form-group">
                <label for="interestRate">项目利率：</label>
                <input type="text" name="interestRate" id="interestRate">%
            </div>

            <%--<div class="form-group" id="inter">--%>
                <%--<label for="inter">时长单位：</label>--%>
                <%--<input type="radio" name="inter" value="MONTH" id="month">月--%>
                <%--<input type="radio" name="inter" value="DAY" id="day">天--%>
                <%--<input type="radio" name="inter" value="YEAR" id="year">年--%>
            <%--</div>--%>

            <%--<div class="form-group">--%>
                <%--<label for="interLong">项目时长：</label>--%>
                <%--<input type="text" name="interLong" id="interLong">个单位--%>
            <%--</div>--%>

            <div class="form-group" id="approach">
                <label for="approach">收益方式：</label>
                <input type="radio" name="approach" value="EPI" id="epi">等额本息
                <input type="radio" name="approach" value="PI" id="pi" checked>一次还本付息
                <input type="radio" name="approach" value="IF" id="if">先息后本
            </div>

            <div class="form-group">
                <label for="startTime">起期日：</label>
                <input type="text" name="startTime" id="startTime">
            </div>

            <div class="form-group">
                <label for="endTime">还本日：</label>
                <input type="text" name="endTime" id="endTime">
            </div>

            <input type="submit" value="保存" />
        </form>

    </body>
</html>