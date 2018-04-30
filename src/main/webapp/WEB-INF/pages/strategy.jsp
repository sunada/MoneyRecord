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
                $(".update").hide();
                $(".edit").click(function(){
                    $(this).closest('td').siblings().html(function(i,html){
                        return "<input type=\"text\" style=\"width:80px;\" value=" + html + ">";
                    });
                    $(this).hide();
                    $(this).siblings().show();
                });

                $('.update').click(function(){
                    var list = $(this).parent().parent().find("td :input[type='text']");
                    var para = new Array();
                    $.each(list, function (i, obj) {
                        $(obj).parent().html($(obj).val());
                        para.push($(obj).val());
                    });
                    $(this).hide();
                    $(this).siblings().show();

                    var str = "code=" + para[0] + "&name=" + para[1] + "&amount=" + para[2] +
                            "&currentAmount=" + para[3] + "&usedAmount=" + para[4] + "&profit=" + para[6];
                    alert(str);
                    //ajax提交表单
                    $.ajax({
                        type: "post",
                        url: "/stock/strategyUpdate",
                        data: str,
                        success: function() {
                            alert("保存成功！");
                        },
                        error: function() {
                            alert("出错啦");
                        }
                    })
                });

                $("#strategyAdd").click(function(){
                    var code = $("#code").val();
                    var name = $("#name").val();
                    var amount = $("#amount").val();
                    var cash = $("#cash").val();

                    var str = "code=" + code + "&name=" + name + "&amount=" + amount + "&cash=" + cash;
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

        <table class="table table-bordered">
            <tr>
                <td>策略代码：<input type="text" name="code" id="code"> </td>
                <td>策略名称：<input type="text" name="name" id="name"></td>
                <td>策略预算：<input type="text" name="amount" id="amount"></td>
                <td>策略可用现金：<input type="text" name="cash" id="cash"></td>
                <td><a class="btn btn-primary" id="strategyAdd">新增</a></td>
            </tr>
        </table>

        <c:forEach items="${strategysAndStocks}" var="ss">
            <table class="table table-bordered">
                <thead>
                <tr>
                    <th>代码</th>
                    <th>名称</th>
                    <th>预算</th>
                    <th>现金</th>
                    <th>现值</th>
                    <th>策略盈亏</th>
                    <th>策略盈亏比例<br/>(占策略预算)</th>
                    <th>持仓市值</th>
                    <th>仓位成本</th>
                    <th>仓位成本占比(%)</th>


                    <th>仓位盈亏</th>
                    <th>仓位盈亏比例<br/>(占仓位成本)</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <tr>
                    <td>${ss.key.code}</td>
                    <td>${ss.key.name}</td>
                    <td>${ss.key.amount}</td>
                    <td>${ss.key.cash}</td>
                    <td><fmt:formatNumber type="number" value="${ss.key.currentValue}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${ss.key.currentValue - ss.key.amount}" pattern="0.00" maxFractionDigits="2"/> </td>
                    <td><fmt:formatNumber type="number" value="${(ss.key.currentValue - ss.key.amount) / ss.key.amount * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${ss.key.currentValue - ss.key.cash}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${ss.key.usedAmount}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${ss.key.usedAmount / ss.key.currentValue * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${ss.key.profit}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${ss.key.profit / ss.key.currentValue * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>
                        <a class="btn btn-primary edit" role="button">编辑</a>
                        <a class="btn btn-primary update" role="button">更新</a>
                        <a class="btn btn-primary" href="/stock/strategyAddStocks?strategyCode=${ss.key.code}" role="button">添加证券</a>
                        <a class="btn btn-primary" href="/deal/strategyDeals?strategyCode=${ss.key.code}" role="button">交易记录</a>
                    </td>
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
                <c:forEach items="${ss.value}" var="stock" varStatus="status">
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
                        <td><fmt:formatNumber type="number" value="${stock.cost * stock.share / ss.key.amount * 100}" pattern="0.00" maxFractionDigits="2"/> </td>
                        <td><a class="btn" href="/deal/stockList?code=${stock.code}&belongTo=${stock.belongTo}" role="button">交易记录</a></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            <br/><br/>
        </c:forEach>
</body>
</html>
