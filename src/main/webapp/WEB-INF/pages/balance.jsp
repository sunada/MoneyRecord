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

<table class="table table-bordered">

    <caption>
        <a class="btn btn-primary" onclick="window.location='/balance/addSalary'">新增月工资记录</a></button>
        <a class="btn btn-primary" onclick="window.location='/balance/addExpense'">新增月支出记录</a></button>
        <br/><br/>
        月收支状态
        月结余总计：${leftSum}
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
