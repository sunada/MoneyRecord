<%@ page import="java.util.ArrayList" %>
<%@ page import="com.springapp.mvc.Model.Loan" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>总览</title>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
    </head>
    <body>

        <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/inAll">总览</a></li>
                <li><a href="/balance">收支表</a> </li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">证券操作</a></li>
            </ul>
        </div>
        </nav>

        低：中：高 = 20% : 45% : 35%
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>风险/资产</th>
                <th>基金</th>
                <th>证券</th>
                <th>网贷</th>
                <th>各风险累计金额</th>
                <th>占比(%)</th>
                <th>合理目标</th>
                <th>差距</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${riskValues}" var="s">
                <tr>
                    <td>${s.key}</td>
                    <c:forEach items="${s.value}" var="sv">
                        <td><fmt:formatNumber type="number" value="${sv}" pattern="0.00" maxFractionDigits="2"/></td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>投资收益率计算：</caption>
            <form class="form-inline" role="form" action="/inAll/calProfitRate" method="post" accept-charset="utf-8">
                <tr>
                    <td><input type="text" placeholder="开始日期" name="start"></td>
                    <td><input type="text" placeholder="结束日期" name="end"></td>
                    <td>
                        <div> 资产类型：
                        <input type="checkbox" value="fund" name="type">基金
                        <input type="checkbox" value="stock" name="type">证券
                        </div>
                    </td>
                    <%--<td>--%>
                        <%--<div> 风险类型：--%>
                        <%--<input type="checkbox" value="HIGH" name="risk">高--%>
                        <%--<input type="checkbox" value="MID" name="risk">中--%>
                        <%--<input type="checkbox" value="LOW" name="risk">低--%>
                        <%--</div>--%>
                    <%--</td>--%>
                    <td><input type="submit" value="提取文件"/></td>
                    </td>
                </tr>
            </form>
        </table>

    <c:set var="exchangeRate" value="6.77"></c:set>
    <table class="table table-bordered">
        <caption>美元资产（汇率：${exchangeRate})</caption>
        <thead>
        <tr>
            <th>风险</th>
            <th>金额(美元)</th>
            <th>金额(人民币)</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${sumUSA}" var="u">
            <tr>
                <td>${u.key}</td>
                <td><fmt:formatNumber type="number" value="${u.value}" pattern="0.00" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber type="number" value="${u.value * exchangeRate}" pattern="0.00" maxFractionDigits="2"/></td>
            </tr>
            </c:forEach>
    </table>




    </body>
</html>