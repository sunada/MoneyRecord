<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/31
  Time: 21:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script>
        $(document).ready(function() {
            $("#submit").click(function(){
                var stockBelongTos = $("input[name='stockBelongTos']:checked").serialize();
                stockBelongTos += "&" + getArgs();
                $.ajax({
                    url:"/stock/saveStrategyStocks?strategyCode=3",
                    type:"post",
                    data:stockBelongTos,
                    success: function() {
                        window.location.href="/stock/strategy";
//                        alert("保存成功！");
                    },
                    error: function() {
                        alert("出错啦");
                    }
                })
            });

            function getArgs(){
                var args={};
                var query=location.search.substring(1);
                return query;
            }
        });
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
                <li><a href="/loan">网贷</a></li>
                <li><a href="/insurance">保险</a></li>
            </ul>
        </div>
    </nav>

    <button id="submit">提交</button>
    <table class="table table-striped">
        <thead>
            <tr>
                <th></th>
                <th>代码</th>
                <th>名称</th>
                <th>账户</th>
                <th>市值</th>
                <th>成本</th>
                <th>现价</th>
                <th>份额</th>
                <th>盈亏</th>
                <th>盈亏率(%)</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${stocksWithoutStrategy}" var="stock" varStatus="status">
                <tr>
                    <td><input type="checkbox" name="stockBelongTos" value=${stock.code}&${stock.belongTo}></td>
                    <td>${stock.code}</td>
                    <td>${stock.name}</td>
                    <td>${stock.belongTo}</td>
                    <td><fmt:formatNumber type="number" value="${stock.current * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>${stock.cost}</td>
                    <td>${stock.current}</td>
                    <td>${stock.share}</td>
                    <td><fmt:formatNumber type="number" value="${(stock.current - stock.cost) * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${(stock.current * 10 - stock.cost * 10) / stock.cost * 10}" pattern="0.00" maxFractionDigits="2"/></td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>
