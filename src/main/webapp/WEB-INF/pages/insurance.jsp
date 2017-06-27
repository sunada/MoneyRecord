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
            <li><a href="/balance">收支表</a> </li>
            <li><a href="/fund">基金</a></li>
            <li><a href="/stock">证券</a></li>
            <li><a href="/loan">网贷</a></li>
            <li class="active"><a href="/insurance">保险</a></li>
        </ul>
    </div>
</nav>

    <% Calendar calendar=Calendar.getInstance();
        int month=calendar.get(Calendar.MONTH)+1;
    %>

<jsp:useBean id="now" class="java.util.Date" scope="page"/>

    <table class="table table-bordered">
        <caption>保险列表</caption>
        <thead>
        <tr>
            <th>产品名</th>
            <th>保险公司</th>
            <th>购买平台</th>
            <th>保险类型</th>
            <th>被保险人</th>
            <th>保额(万)</th>
            <th>交费年限(年)</th>
            <th>保障期限(岁)</th>
            <th>年交保费(元)</th>
            <th>保单生效时间</th>
            <th>保单过期时间</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${list}" var="i">
                <c:choose>
                    <c:when test="${i.start.getMonth()==now.getMonth()&&i.start.getDate() >= now.getDate()}">
                        <tr style='color: red'>
                    </c:when>
                    <c:otherwise>
                        <tr>
                    </c:otherwise>
                </c:choose>
                            <td>${i.name}</td>
                            <th>${i.company}</th>
                            <td>${i.belongTo}</td>
                            <td>${i.type}</td>
                            <td>${i.owner}</td>
                            <td>${i.coverage}</td>
                            <td>${i.years}</td>
                            <td>${i.age}</td>
                            <td>${i.amount}</td>
                            <td><fmt:formatDate value="${i.start}"/></td>
                            <td><fmt:formatDate value="${i.end}"/></td>
                        </tr>
            </c:forEach>
        </tbody>
    </table>

    <table class="table table-bordered">
        <caption>预计保费支出</caption>
        <thead>
            <tr>
                <td>被保险人</td>
                <td>保费(元）</td>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${addUpByOwner}" var="m">
                <tr>
                    <td>${m.key}</td>
                    <td>${m.value}</td>
                </tr>

            </c:forEach>
        </tbody>
    </table>

    <a class="btn btn-primary" onclick="window.location='/insurance/add'">买入保险</a></button>

<br/>
<br/>
<table class="table table-bordered">
    <caption>过期保险列表</caption>
    <thead>
    <tr>
        <th>产品名</th>
        <th>保险公司</th>
        <th>购买平台</th>
        <th>保险类型</th>
        <th>被保险人</th>
        <th>保额(万)</th>
        <th>交费年限(年)</th>
        <th>保障期限(岁)</th>
        <th>年交保费(元)</th>
        <th>保单生效时间</th>
        <th>保单过期时间</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${pastList}" var="i">
        <tr>
            <td>${i.name}</td>
            <th>${i.company}</th>
            <td>${i.belongTo}</td>
            <td>${i.type}</td>
            <td>${i.owner}</td>
            <td>${i.coverage}</td>
            <td>${i.years}</td>
            <td>${i.age}</td>
            <td>${i.amount}</td>
            <td><fmt:formatDate value="${i.start}"/></td>
            <td><fmt:formatDate value="${i.end}"/></td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>
