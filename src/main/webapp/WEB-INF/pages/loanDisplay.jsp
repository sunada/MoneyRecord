<%@ page import="java.util.ArrayList" %>
<%@ page import="com.springapp.mvc.Model.Loan" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>网贷</title>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(function(){
                $('.update').hide();
                $('.edit').click(function(){
                    $(this).closest('td').siblings().html(function(i,html){
                        return "<input type=\"text\" style=\"width:80px;\" value=" + html + ">";
                    });
                    $(this).hide();
                    $(this).siblings().show();
                })
            })

            $(function(){
                $('.update').click(function(){
                    var list = $(this).parent().parent().find("td :input[type='text']");
                    var para = new Array();
                    $.each(list, function (i, obj) {
                        $(obj).parent().html($(obj).val());
                        para.push($(obj).val());
//                        alert($(obj).val());
                    });
                    //alert(para.toString());
                    $(this).hide();
                    $(this).siblings().show();
                    var approach;
                    if(para[4] == "先息后本"){
                        approach = "IF";
                    }else if(para[4] == "等额本息"){
                        approach = "EPI";
                    }else{
                        approach = "PI";
                    }

                    var str = "code=" + para[1] + "&belongTo=" + para[0] + "&amount=" + para[2] + "&interestRate=" + para[3]
                            + "&approach=" + approach + "&hadPI=" + para[5] + "&waitPI=" + para[6] + "&startTime=" + para[7]
                            + "&endTime=" + para[8] + "&risk=" + para[9] + "&interest=" + para[10] + "&valid=" + para[11];
//                    alert(str);
                    //ajax提交表单
                    $.ajax({
                        type: "post",
                        url: "/loan/update",
                        data: str,
                    success: function() {
                        alert("保存成功！");
                    },
                    error: function() {
                        alert("出错啦");
                    }
                    })
                });
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
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li class="active"><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">证券操作</a></li>
            </ul>
        </div>
        </nav>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>平台</th>
                    <th>项目代号</th>
                    <th>本金（元）</th>
                    <th>利率(%)</th>
                    <%--<th>时长</th>--%>
                    <th>收益方式</th>
                    <th>已收本息</th>
                    <th>待收本息</th>
                    <th>起息日</th>
                    <th>还本日</th>
                    <th>风险</th>
                    <th>预期收益（元）</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${loanValid}" var="loan" varStatus="status">
                    <tr>
                        <td>${loan.belongTo}</td>
                        <td>${loan.code}</td>
                        <td>${loan.amount}</td>
                        <td>${loan.interestRate}</td>
                        <%--private LoanInterval inter;--%>
                        <%--private int interLong;--%>
                        <%--<td>${loan.share}</td>--%>
                        <c:choose>
                            <c:when test="${loan.approach == 'IF'}">
                                <td>先息后本</td>
                            </c:when>
                            <c:when test="${loan.approach == 'EPI'}">
                                <td>等额本息</td>
                            </c:when>
                            <c:otherwise>
                                <td>还本付息</td>
                            </c:otherwise>
                        </c:choose>
                        <td>${loan.hadPI}</td>
                        <td>${loan.waitPI}</td>
                        <fmt:setLocale value="zh"/>
                        <td><fmt:formatDate value="${loan.startTime}"/></td>
                        <td><fmt:formatDate value="${loan.endTime}"/></td>
                        <td>${loan.risk}</td>
                        <td>${loan.interest}</td>
                        <%--<td><a class="btn btn-primary" role="button" id="edit${status.index}">编辑</a></td>--%>
                        <%--<td><a class="btn btn-primary" role="button" id="update${status.index}">更新</a></td>--%>
                        <td>
                            <button class="edit">编辑</button>
                            <button class="update">更新</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>合计</caption>
            <tbody>
                <c:forEach items="${sum}" var="entry">
                    <tr>
                        <td>${entry.key}</td>
                        <td>${entry.value}</td>
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
                    <td>${g.value}</td>
                    <td>${g.value/amountAll * 100}</td>
                </tr>
            </c:forEach>
            <tr>
                <td>总计</td>
                <td>${amountAll}</td>
                <td>100</td>
            </tr>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>分类统计（按平台）</caption>
            <thead>
            <tr>
                <th>平台</th>
                <th>资金</th>
                <th>占比(%)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sumByBelongTo}" var="g" varStatus="status">
                <tr>
                    <%--<c:forEach items="${g}" var="gg" varStatus="status">--%>
                        <%--<td>${gg.value}</td>--%>
                    <%--</c:forEach>--%>
                    <td>${g.key}</td>
                    <td>${g.value}</td>
                    <td>${g.value/amountAll * 100}</td>
                </tr>
            </c:forEach>
            <tr>
                <td>总计</td>
                <td>${amountAll}</td>
                <td>100</td>
            </tr>
            </tbody>
        </table>

        <h4>已完结的网贷</h4>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>平台</th>
                <th>项目代号</th>
                <th>本金（元）</th>
                <th>利率(%)</th>
                <%--<th>时长</th>--%>
                <th>收益方式</th>
                <th>已收本息</th>
                <th>待收本息</th>
                <th>起息日</th>
                <th>还本日</th>
                <th>风险</th>
                <th>预期收益（元）</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${loanNotValid}" var="loan" varStatus="status">
                <tr>
                    <td>${loan.belongTo}</td>
                    <td>${loan.code}</td>
                    <td>${loan.amount}</td>
                    <td>${loan.interestRate}</td>
                        <%--private LoanInterval inter;--%>
                        <%--private int interLong;--%>
                        <%--<td>${loan.share}</td>--%>
                    <c:choose>
                        <c:when test="${loan.approach == 'IF'}">
                            <td>先息后本</td>
                        </c:when>
                        <c:when test="${loan.approach == 'EPI'}">
                            <td>等额本息</td>
                        </c:when>
                        <c:otherwise>
                            <td>还本付息</td>
                        </c:otherwise>
                    </c:choose>
                    <td>${loan.hadPI}</td>
                    <td>${loan.waitPI}</td>
                    <fmt:setLocale value="zh"/>
                    <td><fmt:formatDate value="${loan.startTime}"/></td>
                    <td><fmt:formatDate value="${loan.endTime}"/></td>
                    <td>${loan.risk}</td>
                    <td>${loan.interest}</td>
                        <%--<td><a class="btn btn-primary" role="button" id="edit${status.index}">编辑</a></td>--%>
                        <%--<td><a class="btn btn-primary" role="button" id="update${status.index}">更新</a></td>--%>
                    <td>
                        <button class="edit">编辑</button>
                        <button class="update">更新</button>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </body>
</html>