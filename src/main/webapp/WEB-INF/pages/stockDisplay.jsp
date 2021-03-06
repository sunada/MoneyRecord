<%@ page import="java.util.ArrayList" %>
<%@ page import="com.springapp.mvc.Model.Loan" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>证券</title>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(function(){
                $(".update").hide();
                $(".updateUSD").hide();
                $(".edit").click(function(){
                    $(this).closest('td').siblings().html(function(i,html){
                        return "<input type=\"text\" style=\"width:80px;\" value=" + html + ">";
                    });
                    $(this).hide();
                    $(this).siblings().show();
                })
            });

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

                    var str = "code=" + para[0] + "&name=" + para[1] + "&belongTo=" + para[2] + "&cost=" + para[4]
                            + "&current=" + para[5] + "&share=" + para[6] + "&amount=" + para[7]
                            + "&risk=" + para[9] + "&type=" + para[10] + "&currency=" + para[11];

//                    alert(str);
                    //ajax提交表单
                    $.ajax({
                        type: "post",
                        url: "/stock/update",
                        data: str,
                        success: function() {
                            alert("保存成功！");
                        },
                        error: function() {
                            alert("出错啦");
                        }
                    })
                });
            });

            $(function(){
                $('.updateUSD').click(function(){
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

                    var str = "code=" + para[0] + "&name=" + para[1] + "&rmb_cost=" + para[2] + "&usd_cost=" + para[4]
                            + "&current=" + para[5] + "&share=" + para[6] + "&risk=" + para[10]
                            + "&belongTo=" + para[11] + "&currency=" + para[12];
//                    alert(str)
                    //ajax提交表单
                    $.ajax({
                        type: "post",
                        url: "/stock/updateUSD",
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
                <li class="active"><a href="/stock">证券</a></li>
                <%--<li><a href="/fund/aipDisplay">定投</a></li>--%>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/insurance">保险</a></li>
                <%--<li><a href="/fund/myFundAdd">基金操作</a></li>--%>
                <%--<li><a href="/loan/loanAdd">买入网贷</a></li>--%>
                <%--<li><a href="/stock/stockAdd">证券操作</a></li>--%>
                <%--<li><a href="/stock/updateCurrent">更新证券现价</a></li>--%>
            </ul>
        </div>
        </nav>

        <table class="table table-bordered">
            <tr>
                <c:forEach items="${accDate}" var="date" varStatus="status">
                    <td>${date.key}:${date.value} </td>
                </c:forEach>
            </tr>
            <tr>
                <td>
                    <form class="form-inline" role="form" action="/stock/saveStockByFile" method="post" accept-charset="utf-8">
                        <div class="form-group">
                            <input type="text" class="form-control" name="strPath" placeholder="交易记录文件本地地址">
                        </div>
                        <input type="submit" value="提交"/>
                    </form>
                </td>
                <td>
                    <%--<form class="form-inline" role="form" action="/stock/picture" method="post" accept-charset="utf-8">--%>
                        <%--<div class="form-group">--%>
                        <a href="/stock/stockAdd">证券操作</a>
                            <%--<input type="text" class="form-control" name="date" placeholder="最新快照日期 <fmt:formatDate value="${latestDate}"/>">--%>
                        <%--</div>--%>
                        <%--<input type="submit" value="保存快照"/>--%>
                    <%--</form>--%>
                </td>
                <td>
                    <a href="/stock/updateCurrent">更新证券现价</a>
                </td>
                <td>
                    <a href="/stock/strategy">策略收益</a>
                </td>
            </tr>
        </table>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>股票代码</th>
                    <th>股票名称</th>
                    <th>账号</th>
                    <th>市值</th>
                    <th>成本</th>
                    <th>现价</th>
                    <th>份额</th>
                    <th>盈亏</th>
                    <th>盈亏率(%)</th>
                    <th>风险</th>
                    <th>类型</th>
                    <th>币种</th>
                    <th>操作</th>
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
                        <td>${stock.risk}</td>
                        <td>${stock.type}</td>
                        <td>${stock.currency}</td>
                        <td>
                            <a class="btn btn-primary edit">编辑</button>
                            <a class="btn btn-primary update">更新</button>
                            <a class="btn btn-primary" href="/stock/stockAdd?code=${stock.code}&name=${stock.name}&belongTo=${stock.belongTo}&cost=${stock.cost}&share=${stock.share}&amount=${stock.amount}&dealType=SBUY" role="button">买入</a>
                            <a class="btn btn-primary" href="/stock/stockAdd?code=${stock.code}&name=${stock.name}&belongTo=${stock.belongTo}&cost=${stock.cost}&share=${stock.share}&amount=${stock.amount}&dealType=SSELL" role="button">卖出</a>
                            <a class="btn" href="/deal/stockList?code=${stock.code}&belongTo=${stock.belongTo}" role="button">交易记录</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <%--暂时先这么处理好了--%>
        <c:set var="exchangeRate" value="6.4666"></c:set>
        <table class="table table-striped">
            <caption>美元资产 (汇率：${exchangeRate})</caption>
            <thead>
            <tr>
                <th>代码</th>
                <th>名称</th>
                <th>人民币成本</th>
                <th>人民币市值</th>
                <th>美元成本</th>
                <th>现价（美元）</th>
                <th>份额</th>
                <th>美元市值</th>
                <th>盈亏(RMB)</th>
                <th>盈亏率(%)</th>
                <th>风险</th>
                <th>帐户</th>
                <th>币种</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${useStocks}" var="stock" varStatus="status">
                <tr>
                    <td>${stock.code}</td>
                    <td>${stock.name}</td>
                    <td>${stock.rmbCost}</td>
                    <td><fmt:formatNumber type="number" value="${stock.current * stock.share * exchangeRate}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>1</td>
                    <td>${stock.current}</td>
                    <td>${stock.share}</td>
                    <td><fmt:formatNumber type="number" value="${stock.current * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${stock.current * stock.share * exchangeRate - stock.rmbCost}" pattern="0.00" maxFractionDigits="2"/></td>
                    <%--<td><fmt:formatNumber type="number" value="${(stock.current * 10 - stock.cost * 10) / stock.cost * 10}" pattern="0.00" maxFractionDigits="2"/></td>--%>
                    <td><fmt:formatNumber type="number" value="${(stock.current * stock.share * exchangeRate - stock.rmbCost) / stock.rmbCost * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>${stock.risk}</td>
                    <td>${stock.belongTo}</td>
                    <td>${stock.currency}</td>
                    <td>
                        <a class="btn btn-primary edit">编辑</button>
                            <a class="btn btn-primary updateUSD">更新</button>
                                <a class="btn btn-primary" href="/stock/stockAdd?code=${stock.code}&name=${stock.name}&belongTo=${stock.belongTo}&cost=${stock.cost}&share=${stock.share}&amount=${stock.amount}&dealType=SBUY" role="button">买入</a>
                                <a class="btn btn-primary" href="/stock/stockAdd?code=${stock.code}&name=${stock.name}&belongTo=${stock.belongTo}&cost=${stock.cost}&share=${stock.share}&amount=${stock.amount}&dealType=SSELL" role="button">卖出</a>
                                <a class="btn" href="/deal/stockList?code=${stock.code}&belongTo=${stock.belongTo}" role="button">交易记录</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <%--暂时先这么处理好了--%>
        <c:set var="exchangeRate" value="0.8238"></c:set>
        <table class="table table-striped">
            <caption>港币资产 (汇率：${exchangeRate})</caption>
            <thead>
            <tr>
                <th>代码</th>
                <th>股票名称</th>
                <th>持仓成本(￥)</th>
                <th>人民币市值</th>
                <th>人民币成本/股</th>
                <th>现价(港币）</th>
                <th>份额</th>
                <th>港元市值</th>
                <th>盈亏(RMB)</th>
                <th>盈亏率(%)</th>
                <th>风险</th>
                <th>帐户</th>
                <th>币种</th>
                <th>操作</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${hkdStocks}" var="stock" varStatus="status">
                <tr>
                    <td>${stock.code}</td>
                    <td>${stock.name}</td>
                    <td><fmt:formatNumber type="number" value="${stock.share * stock.cost}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${stock.current * stock.share * exchangeRate}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>${stock.cost}</td>
                    <td>${stock.current}</td>
                    <td>${stock.share}</td>
                    <td><fmt:formatNumber type="number" value="${stock.current * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td><fmt:formatNumber type="number" value="${(stock.current * exchangeRate - stock.cost)*stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                        <%--<td><fmt:formatNumber type="number" value="${(stock.current * 10 - stock.cost * 10) / stock.cost * 10}" pattern="0.00" maxFractionDigits="2"/></td>--%>
                    <td><fmt:formatNumber type="number" value="${(stock.current * exchangeRate - stock.cost) / stock.cost * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    <td>${stock.risk}</td>
                    <td>${stock.belongTo}</td>
                    <td>${stock.currency}</td>
                    <td>
                        <a class="btn btn-primary edit">编辑</button>
                            <a class="btn btn-primary updateUSD">更新</button>
                                <a class="btn btn-primary" href="/stock/stockAdd?code=${stock.code}&name=${stock.name}&belongTo=${stock.belongTo}&cost=${stock.cost}&share=${stock.share}&amount=${stock.amount}&dealType=SBUY" role="button">买入</a>
                                <a class="btn btn-primary" href="/stock/stockAdd?code=${stock.code}&name=${stock.name}&belongTo=${stock.belongTo}&cost=${stock.cost}&share=${stock.share}&amount=${stock.amount}&dealType=SSELL" role="button">卖出</a>
                                <a class="btn" href="/deal/stockList?code=${stock.code}&belongTo=${stock.belongTo}&name=${stock.name}&currency=${stock.currency}" role="button">交易记录</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <ul id="statisticTab" class="nav nav-tabs">
            <li class="active">
                <a href="#groupByRisk" data-toggle="tab">人民币资产按风险统计</a>
            </li>
            <li>
                <a href="#groupByType" data-toggle="tab">人民币资产按类型统计</a>
            </li>
            <li>
                <a href="#groupByBelongTo" data-toggle="tab">人民币资产按账户统计</a>
            </li>
            <li>
                <a href="#historyGroupByRisk" data-toggle="tab">历史持仓盈亏统计（按风险）</a>
            </li>
        </ul>

        <div id="myTabContent" class="tab-content">
            <div class="tab-pane fade in active" id="groupByRisk">
                <table class="table table-bordered">
                <caption>人民币资产分类统计（按风险）</caption>
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
            </div>
            <div class="tab-pane fade in" id="groupByType">
                <table class="table table-bordered">
                <caption>人民币资产分类统计（按类型）</caption>
                <thead>
                <tr>
                    <th>类型</th>
                    <th>资金</th>
                    <th>资金占比(%)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${groupByType}" var="g" varStatus="status">
                    <tr>
                        <td>${g.key}</td>
                        <td><fmt:formatNumber type="number" value="${g.value}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${g.value / rmbSum * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>
            <div class="tab-pane fade in" id="groupByBelongTo">
                <table class="table table-bordered">
                <caption>人民币资产分类统计（按帐户）</caption>
                <thead>
                <tr>
                    <th>账户</th>
                    <th>资金</th>
                    <th>资金占比(%)</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${belongToSum}" var="g" varStatus="status">
                    <tr>
                        <td>${g.key}</td>
                        <td></t><fmt:formatNumber type="number" value="${g.value}" pattern="0.00" maxFractionDigits="2"/><br/></td>
                        <td><fmt:formatNumber type="number" value="${g.value / rmbSum * 100}" pattern="0.00" maxFractionDigits="2"/></td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
            </div>
            <div class="tab-pane fade in" id="historyGroupByRisk">
                <table class="table table-bordered">
                    <caption>历史持仓盈亏统计（按风险）</caption>
                    <thead>
                    <tr>
                        <th>风险等级</th>
                        <%--<th>成本</th>--%>
                        <th>盈亏</th>
                        <%--<th>收益率(%)</th>--%>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${historyProfit}" var="g" varStatus="status">
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
            </div>
        </div>



        <table class="table table-bordered">
            <caption>历史持仓</caption>
            <thead>
            <tr>
                <th>代码</th>
                <th>名称</th>
                <th>平台</th>
                <%--<th>成本</th>--%>
                <th>收益</th>
                <%--<th>开始时间</th>--%>
                <%--<th>结束时间</th>--%>
                <th>风险</th>
                <th>交易记录</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${historyAssets}" var="asset"  varStatus="status">
                <tr>
                    <td>${asset.code}</td>
                    <td>${asset.name}</td>
                    <td>${asset.belongTo}</td>
                    <%--<td>${asset.cost}</td>--%>
                    <td>${asset.profit}</td>
                    <%--<td><fmt:formatDate value="${asset.start}"/></td>--%>
                    <%--<td><fmt:formatDate value="${asset.end}"/></td>--%>
                    <td>${asset.risk}</td>
                    <td>
                        <a class="btn btn-primary" href="/stock/stockAdd?code=${asset.code}&name=${asset.name}&belongTo=${asset.belongTo}&dealType=SBUY" role="button">买入</a>
                        <a class="btn btn-primary" href="/stock/stockAdd?code=${asset.code}&name=${asset.name}&belongTo=${asset.belongTo}&dealType=SSELL" role="button">卖出</a>
                        <a class="btn" href="/deal/stockList?code=${asset.code}&belongTo=${asset.belongTo}&from=${start}&to=${end}" role="button">交易记录</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </body>
</html>