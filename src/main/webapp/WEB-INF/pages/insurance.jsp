<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/25
  Time: 20:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>保险</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/resources/js/sortTable.js" type="text/javascript"></script>

</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div>
        <ul class="nav navbar-nav">
            <li><a href="/inAll">总览</a></li>
            <li><a href="/fund">基金</a></li>
            <li><a href="/fund/aipDisplay">定投</a></li>
            <li><a href="/loan">网贷</a></li>
            <li><a href="/stock">股票</a></li>
            <li class="active"><a href="/insurance">保险</a></li>
            <li><a href="/fund/myFundAdd">买入基金</a></li>
            <li><a href="/loan/loanAdd">买入网贷</a></li>
            <li><a href="/stock/stockAdd">买入股票</a></li>
            <li><a href="/fund/updateFundNet">更新基金净值</a></li>
            <li><a href="/deal/updateAipDeal">更新定投交易</a></li>
        </ul>
    </div>
</nav>

    <table class="table table-bordered">
        <caption>保险列表</caption>
        <thead>
        <tr>
            <th>产品名</th>
            <th>平台</th>
            <th>保险类型</th>
            <th>被保险人</th>
            <th>保额(万)</th>
            <th>交费年限(年)</th>
            <th>保障期限(岁)</th>
            <th>年交保费(元)</th>
            <th>保单生效时间</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="i">
                <tr>
                    <td>${i.name}</td>
                    <td>${i.belongTo}</td>
                    <td>${i.type}</td>
                    <td>${i.owner}</td>
                    <td>${i.coverage}</td>
                    <td>${i.years}</td>
                    <td>${i.age}</td>
                    <td>${i.amount}</td>
                    <td><fmt:formatDate value="${i.start}"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-primary" onclick="window.location='/insurance/add'">买入保险</a></button>
</body>
</html>
