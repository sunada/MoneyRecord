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

        <table class="table table-bordered">
            <thead>
            <tr>
                <th>风险/资产</th>
                <th>基金</th>
                <th>证券</th>
                <th>网贷</th>
                <th>各风险累计金额</th>
                <th>占比(%)</th>
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


    </body>
</html>