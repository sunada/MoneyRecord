<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
                <li><a href="/inAll">总览</a></li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">股票</a></li>
                <li><a href="/fund/myFundAdd">买入基金</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">买入股票</a></li>
                <li><a href="/fund/updateFundNet">更新基金净值</a></li>
                <li><a href="/deal/updateAipDeal">更新定投交易</a></li>
            </ul>
        </div>
        </nav>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>代码</th>
                    <th>名称</th>
                    <th>所属账户</th>
                    <th>资金（元）</th>
                    <th>净值</th>
                    <th>份额</th>
                    <th>交易费用</th>
                    <th>预定申请日期</th>
                    <th>实际定投日期/合同号</th>
                    <th>方向</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${deals}" var="deal" varStatus="status">
                    <c:choose>
                        <c:when test="${deal.dealType == 'SSELL' || deal.dealType == 'FREDEMP'}">
                            <tr class="info">
                        </c:when>
                        <c:otherwise>
                            <tr class="warning">
                        </c:otherwise>
                    </c:choose>
                        <td>${deal.code}</td>
                        <td>${deal.name}</td>
                        <td>${deal.belongTo}</td>
                        <td>${deal.amount}</td>
                        <td>${deal.net}</td>
                        <td>${deal.share}</td>
                        <td>${deal.cost}</td>
                        <td>
                                <fmt:setLocale value="zh"/>
                                <fmt:formatDate value="${deal.date}"/>
                        </td>
                        <td>
                            <fmt:formatDate value="${deal.dateReal}"/>
                            ${deal.contract}
                        </td>
                        <td>${deal.dealType}</td>
                        <td>
                            <a class="btn" href="/deal/delete?id=${deal.id}&code=${deal.code}&belongTo=${deal.belongTo}&share=${deal.share}&amount=${deal.amount}&net=${deal.net}&dealType=${deal.dealType}&cost=${deal.cost}" role="button">删除</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </body>
</html>