<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/10
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>收支表</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div>
        <ul class="nav navbar-nav">
            <li><a href="/inAll">总览</a></li>
            <li class="active"><a href="income">收支表</a> </li>
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
    <caption>
        工资收入
        <a class="btn btn-primary" onclick="window.location='/balance/addSalary'">新增工资收入</a></button>
    </caption>

    <thead>
    <tr>
        <th>日期</th>
        <th>来源</th>
        <th>税前</th>
        <th>税后</th>
        <th>公积金(个人)</th>
        <th>公积金(公司)</th>
        <th>医疗保险(个人)</th>
        <th>医疗保险(公司)</th>
        <th>养老保险(个人)</th>
        <th>养老保险(公司)</th>
        <th>工伤保险(个人)</th>
        <th>工伤保险(公司)</th>
        <th>个人所得税</th>
    </tr>
    </thead>
</table>
</body>
</html>
