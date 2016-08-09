<%@ page import="java.util.ArrayList" %>
<%@ page import="com.springapp.mvc.Model.Loan" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
    </head>
    <body>

        <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/inAll">总览</a></li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">股票</a></li>
                <li><a href="/fund/myFundAdd">买入基金</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">买入股票</a></li>
            </ul>
        </div>
        </nav>


        <table class="table table-striped">
            <thead>
                <tr>
                    <th>资产性质</th>
                    <th>风险</th>
                    <th>金额</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${sumFund}" var="f" varStatus="status">
                    <tr>
                        <td>基金</td>
                        <td>${f.key}</td>
                        <td><fmt:formatNumber type="number" value="${f.value}" pattern="0.00" maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
                <c:forEach items="${sumLoan}" var="l" varStatus="status">
                    <tr>
                        <td>网贷</td>
                        <td>${l.key}</td>
                        <td><fmt:formatNumber type="number" value="${l.value}" pattern="0.00" maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
                <c:forEach items="${sumStock}" var="s" varStatus="status">
                    <tr>
                        <td>证券</td>
                        <td>${s.key}</td>
                        <td><fmt:formatNumber type="number" value="${s.value}" pattern="0.00" maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>分类统计（按风险）</caption>
            <thead>
                <tr>
                    <th>风险等级</th>
                    <th>资金</th>
                    <th>占比(%)</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${group}" var="g" varStatus="status">
                    <tr>
                        <td>${g.key}</td>
                        <td><fmt:formatNumber type="number" value="${g.value}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${g.value/sum * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
                <tr>
                    <td>总计</td>
                    <td><fmt:formatNumber type="number" value="${sum}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>100</td>
                </tr>
            </tbody>
        </table>
    </body>
</html>