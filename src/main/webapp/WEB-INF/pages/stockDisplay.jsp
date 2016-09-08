<%@ page import="java.util.ArrayList" %>
<%@ page import="com.springapp.mvc.Model.Loan" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
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

                    var str = "code=" + para[0] + "&name=" + para[1] + "&belongTo=" + para[2] + "&cost=" + para[3]
                            + "&current=" + para[4] + "&share=" + para[5] + "&amount=" + para[6] + "&risk=" + para[9];

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
            })
        </script>
    </head>
    <body>

        <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li><a href="/inAll">总览</a></li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li class="active"><a href="/stock">股票</a></li>
                <li><a href="/fund/myFundAdd">买入基金</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">买入股票</a></li>
                <li><a href="/stock/updateCurrent">更新股票现价</a></li>
            </ul>
        </div>
        </nav>

        <c:forEach items="${accDate}" var="date" varStatus="status">
            ${date.key}:${date.value} <br/>
        </c:forEach>


        <form class="form-inline" role="form" action="/stock/saveStockByFile" method="post" accept-charset="utf-8">
            <div class="form-group">
                <input type="text" class="form-control" name="strPath" placeholder="交易记录文件本地地址">
            </div>
            <input type="submit" value="提交"/>
        </form>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>股票代码</th>
                    <th>股票名称</th>
                    <th>账号</th>
                    <th>成本</th>
                    <th>现价</th>
                    <th>份额</th>
                    <th>市值</th>
                    <th>盈亏</th>
                    <th>盈亏率(%)</th>
                    <th>风险</th>
                    <th>操作</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${stocks}" var="stock" varStatus="status">
                    <tr>
                        <td>${stock.code}</td>
                        <td>${stock.name}</td>
                        <td>${stock.belongTo}</td>
                        <td>${stock.cost}</td>
                        <td>${stock.current}</td>
                        <td>${stock.share}</td>
                        <td><fmt:formatNumber type="number" value="${stock.current * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${(stock.current - stock.cost) * stock.share}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td><fmt:formatNumber type="number" value="${(stock.current * 10 - stock.cost * 10) / stock.cost * 10}" pattern="0.00" maxFractionDigits="2"/></td>
                        <td>${stock.risk}</td>
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

        <table class="table table-bordered">
            <caption>分类统计（按风险）</caption>
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
            <c:forEach items="${group}" var="g" varStatus="status">
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
            <caption>历史持仓</caption>
            <thead>
            <tr>
                <th>代码</th>
                <th>名称</th>
                <th>平台</th>
                <th>成本</th>
                <th>利润</th>
                <%--<th>开始时间</th>--%>
                <th>结束时间</th>
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
                    <td>${asset.cost}</td>
                    <td>${asset.profit}</td>
                    <%--<td><fmt:formatDate value="${asset.start}"/></td>--%>
                    <td><fmt:formatDate value="${asset.end}"/></td>
                    <td>${asset.risk}</td>
                    <td>
                        <a class="btn btn-primary" href="/stock/stockAdd?code=${asset.code}&name=${asset.name}&belongTo=${asset.belongTo}&cost=${asset.cost}&dealType=SBUY" role="button">买入</a>
                        <a class="btn btn-primary" href="/stock/stockAdd?code=${asset.code}&name=${asset.name}&belongTo=${asset.belongTo}&cost=${asset.cost}&dealType=SSELL" role="button">卖出</a>
                        <a class="btn" href="/deal/stockList?code=${asset.code}&belongTo=${asset.belongTo}&from=${start}&to=${end}" role="button">交易记录</a>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>

    </body>
</html>