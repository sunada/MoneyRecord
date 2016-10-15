<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>定投</title>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(function(){
                $(".update").hide();
                $(".edit").click(function(){
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

//                    var str = "code=" + para[0] + "&name=" + para[1] + "&belongTo=" + para[2] + "&risk=" + para[3]
//                            + "&amount=" + para[4] + "&interval=" + para[5] + "&time=" + para[6] + "&cMode=" + para[7] + "&dMode=" + para[8]
//                            + "&prate=" + para[8] + "&rrate=" + para[9] + "&startTime=" + para[10] + "&valid=" + para[11];
                    var str = "code=" + para[0] + "&name=" + para[1] + "&belongTo=" + para[2] + "&risk=" + para[3]
                            + "&amount=" + para[4] + "&interval=" + para[5] + "&time=" + para[6] + "&cMode=" + para[7]
                            + "&prate=" + para[8] + "&rrate=" + para[9] + "&startTime=" + para[10] + "&valid=" + para[11];
//                    alert(str);
                    //ajax提交表单
                    $.ajax({
                        type: "post",
                        url: "/fund/updateAip",
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
                <li class="active"><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
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
                    <th>代码</th>
                    <th>名称</th>
                    <th>所属账户</th>
                    <th>风险等级</th>
                    <th>定投金额</th>
                    <th>定投周期</th>
                    <th>扣款时间</th>
                    <th>收费方式</th>
                    <%--<th>分红方式</th>--%>
                    <th>申购费率（%）</th>
                    <th>赎回费率（%）</th>
                    <th>定投开始时间</th>
                    <th>定投是否有效</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${fundValid}" var="fund" varStatus="status">
                    <tr>
                        <td>${fund.code}</td>
                        <td>${fund.name}</td>
                        <td>${fund.belongTo}</td>
                        <td>${fund.risk}</td>
                        <td>${fund.amount}</td>
                        <td>${fund.interval}</td>
                        <c:choose>
                            <c:when test="${fund.interval == 'MONTH' }">
                                <td>${fund.date}号</td>
                            </c:when>
                            <c:otherwise>
                                <td>${fund.week}</td>
                            </c:otherwise>
                        </c:choose>
                        <td>${fund.chargeMode}</td>
                        <%--<td>${fund.dividendMode}</td>--%>
                        <td>${fund.purchaseRate}</td>
                        <td>${fund.redemptionRate}</td>
                        <td><fmt:formatDate value="${fund.startTime}"/></td>
                        <td>${fund.valid}</td>
                        <td>
                            <button class="edit">编辑</button>
                            <button class="update">更新</button>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <a class="btn btn-primary" href="/fund/aipAdd" role="button">新增定投</a>

        <h4>分类统计（按风险）</h4>
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>风险等级</th>
                <th>资金</th>
                <th>占比(%)</th>
            </tr>
            </thead>
            <tbody>
            <tr>
                <td>总计</td>
                <td>${sum}</td>
                <td>100</td>
            </tr>
            <c:forEach items="${group}" var="g" varStatus="status">
                <tr>
                    <td>${g.key}</td>
                    <td>${g.value}</td>
                    <td>${g.value/sum * 100}</td>
                </tr>
            </c:forEach>

            </tbody>
        </table>

        <br/>
        <br/>
        <h4>已停止的定投</h4>
        <table class="table table-striped">
            <thead>
            <tr>
                <th>代码</th>
                <th>名称</th>
                <th>所属账户</th>
                <th>风险等级</th>
                <th>定投金额</th>
                <th>定投周期</th>
                <th>扣款时间</th>
                <th>收费方式</th>
                <%--<th>分红方式</th>--%>
                <th>申购费率</th>
                <th>赎回费率</th>
                <th>定投开始时间</th>
                <th>定投是否有效</th>
                <th>操作</th>
            </tr>
            </thead>
        <tbody>
        <c:forEach items="${fundNotValid}" var="fund" varStatus="status">
            <tr>
                <td>${fund.code}</td>
                <td>${fund.name}</td>
                <td>${fund.belongTo}</td>
                <td>${fund.risk}</td>
                <td>${fund.amount}</td>
                <td>${fund.interval}</td>
                <c:choose>
                    <c:when test="${fund.interval == 'MONTH' }">
                        <td>${fund.date}号</td>
                    </c:when>
                    <c:otherwise>
                        <td>${fund.week}</td>
                    </c:otherwise>
                </c:choose>
                <td>${fund.chargeMode}</td>
                <%--<td>${fund.dividendMode}</td>--%>
                <td>${fund.purchaseRate}</td>
                <td>${fund.redemptionRate}</td>
                <td><fmt:formatDate value="${fund.startTime}"/></td>
                <td>${fund.valid}</td>
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