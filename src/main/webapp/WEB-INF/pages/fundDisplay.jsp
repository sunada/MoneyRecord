<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<html>
    <head>
        <title>基金</title>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="/resources/js/sortTable.js" type="text/javascript"></script>
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

//                    var dMode;
//                    if(para[9] == "现金分红"){
//                        dMode = "CASH";
//                    }else{
//                        dMode = "REINVESTMENT";
//                    }

                    var str = "code=" + para[0] + "&name=" + para[1] + "&share=" + para[4] +
                            "&cost=" + para[5] + "&belongTo=" + para[7] + "&risk=" + para[8];
//                    alert(str);
                    //ajax提交表单
                    $.ajax({
                        type: "post",
                        url: "/fund/myFundUpdate",
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
                <li class="active"><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">证券操作</a></li>
                <li><a href="/fund/updateFundNet">更新基金净值</a></li>
                <li><a href="/deal/updateAipDeal">更新定投交易</a></li>
            </ul>
        </div>
        </nav>

        <table class="table table-bordered">
            <tr>
                <td>
                    <form class="form-inline" role="form" action="/fund/picture" method="post" accept-charset="utf-8">
                        <div class="form-group">
                            <input type="text" class="form-control" name="date" placeholder="最新快照日期 <fmt:formatDate value="${latestDate}"/>"/>
                        </div>

                        <input type="submit" value="保存快照"/>
                    </form>
                </td>
            </tr>
        </table>

        <h4>持有基金记录：</h4>
        <table class="table table-striped" onclick="sortColumn(event)">
            <thead>
                <tr>
                    <th>代码</th>
                    <th>名称</th>
                    <th><a href="#"/>资金（元）</th>
                    <th>
                        单位净值/万份收益
                    </th>
                    <%--<th>净值日期</th>--%>
                    <th>份额</th>
                    <th>成本</th>
                    <th>盈亏</th>
                    <th><a href="#"/>所属账户</th>
                    <th><a href="#"/>风险等级</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${funds}" var="fund" varStatus="status">
                    <tr>
                        <td>
                            ${fund.code}
                        </td>
                        <td>
                            ${fund.name}
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${fund.risk=='LOW' && fn:length(fund.code)==6}">
                                    <fmt:formatNumber type="number" value="${fund.share}" pattern="0.00" maxFractionDigits="2"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber type="number" value="${fund.net * fund.share}" pattern="0.00" maxFractionDigits="2"/>
                                </c:otherwise>
                            </c:choose>
                            <%--${fund.net * fund.share}--%>
                        </td>
                        <td>
                            ${fund.net}<br/>
                            <fmt:setLocale value="zh"/>
                            <fmt:formatDate value="${fund.date}"/>
                        </td>
                        <%--<td>--%>
                                <%--<fmt:setLocale value="zh"/>--%>
                                <%--<fmt:formatDate value="${fund.date}"/>--%>
                        <%--</td>--%>
                        <td>${fund.share}</td>
                        <td>${fund.cost}</td>
                        <td>
                            <c:choose>
                                <c:when test="${fund.risk=='LOW'}">
                                    <fmt:formatNumber type="number" value="${fund.share - fund.cost}" pattern="0.00" maxFractionDigits="2"/>
                                    <br/>
                                    <%--haha--%>
                                    <fmt:formatNumber type="number" value="${(fund.share - fund.cost) / fund.cost}" pattern="0.00%" maxFractionDigits="2"/>
                                </c:when>
                                <c:otherwise>
                                    <fmt:formatNumber type="number" value="${fund.net * fund.share - fund.cost}" pattern="0.00" maxFractionDigits="2"/>
                                    <br/>
                                    <%--haha--%>
                                    <c:choose>
                                        <c:when test="${fund.cost != '0.00'}">
                                            <fmt:formatNumber type="number" value="${(fund.net * fund.share - fund.cost) / fund.cost}" pattern="0.00%" maxFractionDigits="2"/>
                                        </c:when>
                                        <c:otherwise>
                                            0.00%
                                        </c:otherwise>
                                    </c:choose>
                                </c:otherwise>
                            </c:choose>
                        </td>

                        <td>${fund.belongTo}</td>
                        <td>${fund.risk}</td>
                        <td>
                            <a class="btn btn-primary" href="/fund/myFundAdd?code=${fund.code}&name=${fund.name}&belongTo=${fund.belongTo}&risk=${fund.risk}&prate=${fund.purchaseRate}&rrate=${fund.redemptionRate}&cMode=${fund.chargeMode}&dMode=${fund.dividendMode}&dealType=FBUY" role="button">操作</a>
                            <a class="btn btn-primary edit" role="button">编辑</a>
                            <%--<a class="btn btn-primary edit update" role="button">更新</a>--%>
                            <button class="update">更新</button>
                            <a class="btn" href="/deal/fundList?code=${fund.code}&belongTo=${fund.belongTo}" role="button">交易记录</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>持有基金分类统计（按风险）</caption>
            <thead>
            <tr>
                <th>风险等级</th>
                <th>资金</th>
                <th>资金占比(%)</th>
                <th>成本</th>
                <th>盈亏</th>
                <th>收益率(%)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${groupByRisk}" var="g" varStatus="status">
                <tr>
                    <td>${g.key}</td>
                    <c:forEach items="${g.value}" var="ac" varStatus="status">
                        <td>
                            <fmt:formatNumber type="number" value="${ac}" pattern="0.00" maxFractionDigits="2"/><br/>
                            <%--<fmt:formatNumber type="number" value="${ac/sum * 100}" pattern="0.00" maxFractionDigits="2"/>--%>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>持有基金分类统计（按平台）</caption>
            <thead>
                <tr>
                    <th>平台名称</th>
                    <th>总金额</th>
                    <th>基金数量</th>
                </tr>
            </thead>
            <tbody>
            <c:forEach items="${belongTos}" var="b" varStatus="status">
                <tr>
                    <td>${b.key}</td>
                    <c:forEach items="${b.value}" var="bv">
                        <td>
                            <fmt:formatNumber type="number" value="${bv}" pattern="0.00" maxFractionDigits="0"/><br/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>历史收益统计（按风险）</caption>
            <thead>
            <tr>
                <th>风险等级</th>
                <th>成本</th>
                <th>盈亏</th>
                <th>收益率(%)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${historyProfit}" var="g">
                <tr>
                    <td>${g.key}</td>
                    <c:forEach items="${g.value}" var="gc">
                        <td>
                            <fmt:formatNumber type="number" value="${gc}" pattern="0.00" maxFractionDigits="2"/>
                        </td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <h4>历史基金记录：</h4>

        <table class="table table-striped">
            <thead>
            <tr>
                <th>代码</th>
                <th>名称</th>
                <th>份额</th>
                <th>成本</th>
                <th>盈亏</th>
                <th>所属账户</th>
                <th>风险等级</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${hisFunds}" var="fund" varStatus="status">
                <tr>
                    <td>
                            ${fund.code}
                    </td>
                    <td>
                            ${fund.name}
                    </td>
                    <td>${fund.share}</td>
                    <td>${fund.cost}</td>
                    <td>
                        <fmt:formatNumber type="number" value="${fund.profit}" pattern="0.00" maxFractionDigits="2"/><br/>
                        <fmt:formatNumber type="number" value="${fund.profit/fund.cost}" pattern="0.00%" maxFractionDigits="2"/>
                    </td>
                    <td>${fund.belongTo}</td>
                    <td>${fund.risk}</td>
                    <td>
                        <a class="btn" href="/deal/fundList?code=${fund.code}&belongTo=${fund.belongTo}" role="button">交易记录</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </body>
</html>