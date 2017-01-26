<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2017/1/25
  Time: 21:32
  To change this template use File | Settings | File Templates.
--%>
<html>
    <head>
        <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
        <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(document).ready(function() {
                $("#strategyAdd").click(function(){
                    var code = $("#code").val();
                    var name = $("#name").val();
                    var amount = $("#amount").val();

                    var str = "code=" + code + "&name=" + name + "&amount=" + amount;
//                    alert(str);

                    $.ajax({
                        type:"post",
                        url:"/stock/strategyAdd",
                        data: str,
                        success: function() {
                            alert("保存成功！");
                        },
                        error: function() {
                            alert("出错啦");
                        }
                    })
                })
            })
        </script>
    </head>
    <body>
        <table class="table table-bordered">
            <tr>
                <td>策略代码：<input type="text" name="code" id="code"> </td>
                <td>策略名称：<input type="text" name="name" id="name"></td>
                <td>策略预算：<input type="text" name="amount" id="amount"></td>
                <td><a class="btn btn-primary" id="strategyAdd">新增</a></td>
            </tr>
        </table>

        <c:forEach items="${strategys}" var="stragety">
            <table class="table table-stiped">
                <thead>
                <tr>
                    <th>策略代码</th>
                    <th>策略名称</th>
                    <th>策略预算</th>
                    <th>已使用预算额度</th>
                    <th>占比(%)</th>
                    <th>盈亏</th>
                    <th>盈亏比例(% 占已使用额度)</th>
                    <th>盈亏比例(% 占预算额度)</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${stragety.code}</td>
                    <td>${stragety.name}</td>
                    <td>${stragety.amount}</td>
                    <td><fmt:formatNumber type="number" value="${stragety.usedAmount}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${stragety.usedAmount / stragety.amount * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${stragety.profit}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${stragety.profit / stragety.usedAmount * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${stragety.profit / stragety.amount * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                </tr>
                </tbody>
            </table>
            <table class="table table-striped">
                <thead>
                <tr>
                    <th>代码</th>
                    <th>名称</th>
                    <th>账户</th>
                    <th>市值</th>
                    <th>成本</th>
                    <th>现价</th>
                    <th>份额</th>
                    <th>盈亏</th>
                    <th>盈亏率(%)</th>
                    <th>占策略预算比(%)</th>
                    <th>记录</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${stocks}" var="stock" varStatus="status">
                    <tr>
                        <td>${stock.code}</td>
                        <td>${stock.name}</td>
                        <td>${stock.belongTo}</td>
                        <td><fmt:formatNumber type="number" value="${stock.current * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td>${stock.cost}</td>
                        <td>${stock.current}</td>
                        <td>${stock.share}</td>
                        <td><fmt:formatNumber type="number" value="${(stock.current - stock.cost) * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${(stock.current * 10 - stock.cost * 10) / stock.cost * 10}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${stock.cost * stock.share / stragety.amount * 100}" pattern="0.00" maxFractionDigits="2"/> </td>
                        <td><a class="btn" href="/deal/stockList?code=${stock.code}&belongTo=${stock.belongTo}" role="button">交易记录</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:forEach>
</body>
</html>
