<%@ page import="java.util.ArrayList" %>
<%@ page import="com.springapp.mvc.Model.Loan" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<html>
    <head>
        <title>总览</title>
            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
            <script src="/resources/js/echarts.js" type="text/javascript"></script>
    </head>
    <body>

        <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="/inAll">总览</a></li>
                <li><a href="/balance">收支表</a> </li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">证券操作</a></li>
            </ul>
        </div>
        </nav>

        <table class="table table-bordered">
            <tr>
                <td>
                    <form class="form-inline" role="form" action="/inAll/picture" method="post" accept-charset="utf-8">
                        <div class="form-group">
                            <input type="text" class="form-control" name="date" placeholder="日期（yyyy-MM）"/>
                            <input type="text" class="form-control" name="cnyAsset" placeholder="人民币总资产金额（元）"/>
                            <input type="text" class="form-control" name="usdAsset" placeholder="美元总资产金额（元）"/>
                            <input type="text" class="form-control" name="hkdAsset" placeholder="港币总资产金额（元）"/>
                        </div>
                        <input type="submit" value="保存总资产"/>
                    </form>
                </td>
            </tr>
        </table>

        <table>
            <tr>
                <td>
                    <div id="monthAssets" style="width: 650px;height:400px;">
                        <script>
                            var myChart = echarts.init(document.getElementById('monthAssets'));
                            var monthAssets = ${monthAssets};
                            var monthLeft = ${monthLeft};
                            monthLeft.reverse();
                            var leftList = new Array();
                            var monthes = new Array();
                            var assetsBesidesLeft = new Array();
                            var assets = new Array();
                            for(m in monthAssets){
                                month = monthAssets[m]["month"];
                                monthes.push(month);
                                asset = monthAssets[m]["amount"];
                                leftList.push(monthLeft[m]["left"]);
                                assetsBesidesLeft.push(asset - leftList[m]);
                                assets.push(asset);
                            }
                            assetsBesidesLeft[0] = monthAssets[0]["amount"] - leftList[0];

                            option = {
                                "title": {
                                    "text": "总资产",
                                    "x": "center"
                                },
                                "tooltip": {
                                    "trigger": "axis",
                                    "axisPointer": {
                                        "type": "shadow"
                                    }
                                },
                                "grid": {
                                    "borderWidth": 0,
                                    "y2": 120
                                },
                                "legend": {
                                    "x": "right",
                                    "data": [ ]
                                },
                                "toolbox": {
                                    "show": true,
                                    "feature": {
                                        "restore": { },
                                        "saveAsImage": { }
                                    }
                                },
                                "calculable": true,
                                "xAxis": [
                                    {
                                        "type": "category",
                                        "splitLine": {
                                            "show": false
                                        },
                                        "axisTick": {
                                            "show": false
                                        },
                                        "splitArea": {
                                            "show": false
                                        },
                                        "axisLabel": {
                                            "interval": 0,
                                            "rotate": 45,
                                            "show": true,
                                            "splitNumber": 15,
                                            "textStyle": {
                                                "fontFamily": "微软雅黑",
                                                "fontSize": 12
                                            }
                                        },
                                        "data": monthes
                                    }
                                ],
                                "yAxis": [
                                    {
                                        "type": "value",
                                        "splitLine": {
                                            "show": false
                                        },
                                        "axisLine": {
                                            "show": true
                                        },
                                        "axisTick": {
                                            "show": false
                                        },
                                        "splitArea": {
                                            "show": false
                                        }
                                    }
                                ],
                                "dataZoom": [
                                    {
                                        "show": true,
                                        "height": 30,
                                        "xAxisIndex": [
                                            0
                                        ],
                                        bottom:40,
                                        "start": 0,
                                        "end": 80
                                    },
                                    {
                                        "type": "inside",
                                        "show": true,
                                        "height": 15,
                                        "xAxisIndex": [
                                            0
                                        ],
                                        "start": 1,
                                        "end": 35
                                    }
                                ],
                                "series": [
                                    {
                                        "name": "上月结余+资产增值",
                                        "type": "bar",
                                        "stack": "总量",
                                        "barMaxWidth": 50,
                                        "barGap": "10%",
                                        "itemStyle": {
                                            "normal": {
                                                "barBorderRadius": 0,
                                                "color": "rgba(60,169,196,0.5)",
                                                "label": {
                                                    "show": true,
                                                    "textStyle": {
                                                        "color": "rgba(0,0,0,1)"
                                                    },
                                                    "position": "insideTop",
                                                    formatter : function(p) {
                                                        return p.value > 0 ? (p.value ): '';
                                                    }
                                                }
                                            }
                                        },
                                        "data": assetsBesidesLeft
                                    },
                                    {
                                        "name": "当月结余",
                                        "type": "bar",
                                        "stack": "总量",
                                        "itemStyle": {
                                            "normal": {
                                                "color": "rgba(193,35,43,1)",
                                                "barBorderRadius": 0,
                                                "label": {
                                                    "show": true,
                                                    "position": "top",
                                                    formatter : function(p) {
                                                        return p.value > 0 ? ('▲'
                                                                + p.value + '')
                                                                : '';
                                                    }
                                                }
                                            }
                                        },
                                        "data": leftList
                                    },
                                    {
                                        "name": "当月总资产",
                                        "type": "line",
                                        "stack": "总量",
                                        symbolSize:10,
                                        symbol:'circle',
                                        "itemStyle": {
                                            "normal": {
                                                "color": "rgba(252,230,48,1)",
                                                "barBorderRadius": 0,
                                                "label": {
                                                    "show": true,
                                                    "position": "top",
                                                    formatter: function(p) {
                                                        return p.value > 0 ? (p.value) : '';
                                                    }
                                                }
                                            }
                                        },
                                        "data": assets
                                    }
                                ]
                            };
                            myChart.setOption(option);
                        </script>
                    </div>
                </td>
                <td>
                    <div id="monthProfit" style="width: 650px;height:400px;">
                        <script>
                            var myChart = echarts.init(document.getElementById('monthProfit'));
                            var monthAssets = ${monthAssets};
                            var monthLeft = ${monthLeft};
                            monthLeft.reverse();
                            var monthes = new Array();
                            var investProfit = new Array();
                            last = 0;
                            for(m in monthAssets){
                                month = monthAssets[m]["month"];
                                monthes.push(month);
                                asset = monthAssets[m]["amount"];
                                left = monthLeft[m]["left"];
                                investProfit.push(asset - left - last);
                                last = asset;
                            }
                            investProfit[0] = 0;
                            option = {
                                "title": {
                                    "text": "月投资收益",
                                    "x": "center"
                                },
                                color: ['#3398DB'],
                                tooltip : {
                                    trigger: 'axis',
                                    axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                                        type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                                    }
                                },
                                grid: {
                                    left: '1%',
                                    right: '10%',
                                    bottom: '10%',
                                    containLabel: true
                                },
                                xAxis : [
                                    {
                                        type : 'category',
                                        data : monthes,
                                        axisTick: {
                                            alignWithLabel: true
                                        }
                                    }
                                ],
                                yAxis : [
                                    {
                                        type : 'value'
                                    }
                                ],
                                dataZoom: [
                                    {
                                        show: true,
                                        start: 0,
                                        end: 100
                                    },
                                    {
                                        type: 'inside',
                                        start: 94,
                                        end: 100
                                    },
                                    {
                                        show: true,
                                        yAxisIndex: 0,
                                        filterMode: 'empty',
                                        width: 30,
                                        height: '80%',
                                        showDataShadow: false,
                                        left: '93%'
                                    }
                                ],
                                series : [
                                    {
                                        name:'投资收益',
                                        type:'bar',
                                        barWidth: '60%',
                                        data:investProfit
                                    }
                                ]
                            };
                            myChart.setOption(option);
                        </script>
                    </div>
                </td>
            </tr>
        </table>

        低：中：高 = 20% : 45% : 35%
        <table class="table table-bordered">
            <thead>
            <tr>
                <th>风险/资产</th>
                <th>基金</th>
                <th>证券</th>
                <th>网贷</th>
                <th>各风险累计金额</th>
                <th>占比(%)</th>
                <th>合理目标</th>
                <th>差距</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${riskValues}" var="s">
                <tr>
                    <td>${s.key}</td>
                    <c:forEach items="${s.value}" var="sv">
                        <td><fmt:formatNumber type="number" value="${sv}" pattern="0.00" maxFractionDigits="2"/></td>
                    </c:forEach>
                </tr>
            </c:forEach>
            </tbody>
        </table>

        <table class="table table-bordered">
            <caption>投资收益率计算：</caption>
            <form class="form-inline" role="form" action="/inAll/calProfitRate" method="post" accept-charset="utf-8">
                <tr>
                    <td><input type="text" placeholder="开始日期" name="start"></td>
                    <td><input type="text" placeholder="结束日期" name="end"></td>
                    <td>
                        <div> 资产类型：
                        <input type="checkbox" value="fund" name="type">基金
                        <input type="checkbox" value="stock" name="type">证券
                        </div>
                    </td>
                    <%--<td>--%>
                        <%--<div> 风险类型：--%>
                        <%--<input type="checkbox" value="HIGH" name="risk">高--%>
                        <%--<input type="checkbox" value="MID" name="risk">中--%>
                        <%--<input type="checkbox" value="LOW" name="risk">低--%>
                        <%--</div>--%>
                    <%--</td>--%>
                    <td><input type="submit" value="提取文件"/></td>
                    </td>
                </tr>
            </form>
        </table>

    <c:set var="exchangeRate" value="6.94"></c:set>
    <table class="table table-bordered">
        <caption>美元资产（汇率：${exchangeRate})</caption>
        <thead>
        <tr>
            <th>风险</th>
            <th>金额(美元)</th>
            <th>金额(人民币)</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${sumUSA}" var="u">
            <tr>
                <td>${u.key}</td>
                <td><fmt:formatNumber type="number" value="${u.value}" pattern="0.00" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber type="number" value="${u.value * exchangeRate}" pattern="0.00" maxFractionDigits="2"/></td>
            </tr>
            </c:forEach>
    </table>

        <c:set var="exchangeRate" value="0.8949"></c:set>
        <table class="table table-bordered">
            <caption>港币资产（汇率：${exchangeRate})</caption>
            <thead>
            <tr>
                <th>风险</th>
                <th>金额(港币)</th>
                <th>金额(人民币)</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sumHKD}" var="u">
            <tr>
                <td>${u.key}</td>
                <td><fmt:formatNumber type="number" value="${u.value}" pattern="0.00" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber type="number" value="${u.value * exchangeRate}" pattern="0.00" maxFractionDigits="2"/></td>
            </tr>
            </c:forEach>
        </table>

    </body>
</html>