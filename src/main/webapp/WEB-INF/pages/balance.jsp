<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/10
  Time: 21:48
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>收支表</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/resources/js/echarts.js" type="text/javascript"></script>
</head>
<body>
<nav class="navbar navbar-default" role="navigation">
    <div>
        <ul class="nav navbar-nav">
            <li><a href="/inAll">总览</a></li>
            <li class="active"><a href="balance">收支表</a> </li>
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

<table>
    <tr>
        <td>
            <div id="main" style="width: 650px;height:400px;">
                <script type="text/javascript">
                    var myChart = echarts.init(document.getElementById('main'));
                    var balanceListJson = ${balanceListJson};
                    var leftList = new Array();
                    var leftRateList = new Array();
                    var dateList = new Array();
                    var expenseList = new Array();
                    var income = 0;
                    var left = 0;
                    for(b in balanceListJson){
                        left = balanceListJson[b]["left"];
                        income = 0
                        for (i in balanceListJson[b]["incomeList"]){
                            income += balanceListJson[b]["incomeList"][i]["incomeAll"];
                        }
                        leftList.push(left);
                        leftRateList.push((left * 100/income).toFixed(2));
                        dateList.push(balanceListJson[b]["date"]);
                        if (balanceListJson[b]["expense"] == null){
                            expenseList.push(0);
                        }else {
                            expenseList.push((-1 * balanceListJson[b]["expense"]["dailyExpense"]));
                        }
                    }
                    leftList = leftList.reverse();
                    leftRateList = leftRateList.reverse();
                    dateList = dateList.reverse();
                    expenseList = expenseList.reverse();

                    for(i in leftList){
                        leftList[i] = (leftList[i] / 1000).toFixed(3);
                    }

                    for(i in expenseList){
                        expenseList[i] = (expenseList[i] / 1000).toFixed(3);
                    }

                    var option = {
                        title: {
                            text: '每月收支状况'
                        },
                        tooltip: {
                            trigger: 'axis'
                        },
                        legend: {
                            data:['当月结余占收入比(%)','当月结余(k)','当月日常支出(k)']
                        },
                        toolbox: {
                            show: true,
                            feature: {
                                magicType: {show: true, type: ['stack', 'tiled']},
                                saveAsImage: {show: true}
                            }
                        },
                        xAxis: {
                            type: 'category',
                            boundaryGap: false,
                            data: dateList
                        },
                        yAxis: {
                            type: 'value'
                        },
                        series: [
                            {
                                name: '当月结余占收入比(%)',
                                type: 'line',
                                smooth: true,
                                data: leftRateList
                            },
                            {
                                name: '当月结余(k)',
                                type: 'line',
                                smooth: true,
                                data: leftList
                            },
                            {
                                name: '当月日常支出(k)',
                                type: 'line',
                                smooth: true,
                                data: expenseList
                            }
                            ]
                    };
                    myChart.setOption(option);
                </script>
            </div>
        </td>
        <td>
            <div id="leftAcc" style="width: 650px;height:400px;">
                <script>
                    var myChart = echarts.init(document.getElementById('leftAcc'));
                    var balanceListJson = ${balanceListJson};
                    var leftList = new Array();
                    var dateList = new Array();
                    for(b in balanceListJson){
                        left = balanceListJson[b]["left"];
                        leftList.push(left);
                        dateList.push(balanceListJson[b]["date"]);
                    }
                    leftList = leftList.reverse();
                    dateList = dateList.reverse();

                    var leftAccList = new Array();
                    var amount = 0;
                    for(b in leftList){
                        leftAccList.push(amount);
                        amount += leftList[b];
                    }

                    option = {
                        title:{
                            text: '累计结余'
                        },
                        tooltip : {
                            trigger: 'item'
                        },
                        toolbox: {
                            show : true,
                            feature : {
                                mark : {show: true},
                                magicType: {show: true, type: ['line', 'bar']},
                                restore : {show: true},
                                saveAsImage : {show: true}
                            }
                        },
                        calculable : true,
//                        legend: {
//                            data:['累计结余'],
//                            itemGap: 1
//                        },
                        grid: {
                            top: '12%',
                            left: '1%',
                            right: '10%',
                            containLabel: true
                        },
                        xAxis: [
                            {
                                type : 'category',
                                data : dateList
                            }
                        ],
                        yAxis: [
                            {
//                                type : 'value',
//                                name : '累计结余',
                                axisLabel: {
                                    formatter: function (a) {
                                        a = +a;
                                        return isFinite(a)
                                                ? echarts.format.addCommas(+a / 1000)
                                                : '';
                                    }
                                }
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
                                "name": "上月结余",
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
                                "data": leftAccList
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
                            }
                        ]
                    };
                    myChart.setOption(option);
                </script>
            </div>
        </td>
    </tr>
</table>

<table class="table table-bordered">
    <tr>
        <td>
            <form class="form-inline" role="form" action="/balance/addSocialFunds" method="post" accept-charset="utf-8">
                <div class="form-group">
                    老公公积金：<input type="text" class="form-control" name="hHouseFund" value=${socialFunds.hHouseFund}>
                    老公医保：<input type="text" class="form-control" name="hMediFund" value=${socialFunds.hMediFund}>
                    老婆公积金：<input type="text" class="form-control" name="wHouseFund" value=${socialFunds.wHouseFund}>
                    老婆医保：<input type="text" class="form-control" name="wMediFund" value=${socialFunds.wMediFund}>
                </div>
                <input type="submit" value="保存"/>
            </form>
        </td>
    </tr>
</table>


<table class="table table-bordered">
    <caption>
        <a class="btn btn-primary" onclick="window.location='/balance/addSalary'">新增月工资记录</a></button>
        <a class="btn btn-primary" onclick="window.location='/balance/addExpense'">新增月支出记录</a></button>
        <br/><br/>
        9月至今结余总计：${leftSum}
        月消费支出预算结余总计：${budgetSum}
    </caption>

    <thead>
    <tr>
        <th>日期</th>
        <th>收入</th>
        <th>支出</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${balanceList}" var="b">
        <tr>
            <td width="20%">
                ${b.date} <br/>
                当月结余：${b.left}<br/>
                月消费支出预算：${b.budget}<br/>
                月消费支出预算结余：${b.monthBudgetLeft}<br/>
            </td>
            <td>
                总计：
                <table class="table table-bordered">
                    <thead>
                        <th>贡献者</th>
                        <th>收入总计</th>
                        <th>税后</th>
                        <th>公积金(个人)</th>
                        <th>公积金(公司)</th>
                        <th>医保个人账户</th>
                    </thead>
                    <tbody>
                    <c:set var="all" value="${0}"/>
                    <c:forEach items="${b.incomeList}" var="bl">
                            <tr>
                                <td>${bl.owner}</td>
                                <td>${bl.incomeAll}</td>
                                <td>${bl.afterTax}</td>
                                <td>${bl.houseFunds}</td>
                                <td>${bl.houseFundsCompany}</td>
                                <td>${bl.mediaCash}</td>
                                <c:set var = "all" value="${all + bl.incomeAll}"/>
                            </tr>
                        </c:forEach>
                    <c:out value="${all}"/>
                    </tbody>
                </table>
            </td>
            <td>
                <table class="table table-bordered">
                    总计：${b.expense.dailyExpense + b.expense.mortgage}
                    <thead>
                        <th>日常支出</th>
                        <th>房贷</th>
                    </thead>
                    <tbody>
                        <td>${b.expense.dailyExpense}</td>
                        <td>${b.expense.mortgage}</td>
                    </tbody>
                </table>
            </td>
        </tr>
    </c:forEach>
    </tbody>

<table class="table table-bordered">
    <caption>
        详细工资收入
    </caption>

    <thead>
    <tr>
        <th>日期</th>
        <th>贡献者</th>
        <th>税前</th>
        <th>税后</th>
        <th>公积金<br/>(个人)</th>
        <th>公积金<br/>(公司)</th>
        <th>医疗保险<br/>(个人)</th>
        <th>医疗保险<br/>(公司)</th>
        <th>养老保险<br/>(个人)</th>
        <th>养老保险<br/>(公司)</th>
        <th>失业保险<br/>(个人)</th>
        <th>失业保险<br/>(公司)</th>
        <th>个人所得税</th>
    </tr>
    </thead>
    <tbody>
        <c:forEach items="${salary}" var="s">
            <tr>
                <td>${s.date}</td>
                <td>${s.owner}</td>
                <td>${s.beforeTax}</td>
                <td>${s.afterTax}</td>
                <td>${s.houseFunds}</td>
                <td>${s.houseFundsCompany}</td>
                <td>${s.medicare}</td>
                <td>${s.medicareCompany}</td>
                <td>${s.pensionInsurance}</td>
                <td>${s.pensionInsuranceCompany}</td>
                <td>${s.unemployInsurance}</td>
                <td>${s.unemployInsuranceCompany}</td>
                <td>${s.tax}</td>
            </tr>
        </c:forEach>
    </tbody>
</table>
</body>
</html>
