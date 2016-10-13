<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/10/11
  Time: 23:01
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增工资</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

    <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li><a href="/inAll">总览</a></li>
                <li><a href="/balance">收支表</a> </li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li><a href="/fund/myFundAdd">买入基金</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">买入证券</a></li>
            </ul>
        </div>
    </nav>
</head>
<body>
    <form action="/balance/saveSalary" method="post" accept-charset="utf-8" role="form">
        <div>
            <label>日期：</label>
            <input type="text" name="date">
        </div>
        <br/>
        <div>
            <label>来源：</label>
            <input type="radio" name="owner" value="wife">老婆
            <input type="radio" name="owner" value="husband">老公
        </div>
        <br/>
        <div>
            <label>社保基数：</label>
            <input type="text" name="insuranceBase" value="1000">

            <label>公积金基数：</label>
            <input type="text" name="fundBase" value="1000">
        </div>
        <br/>

        <div>
            <label>税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;前：</label>
            <input type="text" name="beforeTax" value="1000">
            <label>税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;后：</label>
            <input type="text" name="afterTax" value="1000">
        </div>
        <br/>

        <div>
            <label>公积金（个人）：</label>
            <input type="text" name="houseFunds" value="1000">
            <label>公积金（公司）：</label>
            <input type="text" name="houseFundsCompany" value="1000">
        </div>
        <br/>

        <div>
            <label>医疗保险(个人)：</label>
            <input type="text" name="medicare" value="1000">
            <label>医疗保险(公司)：</label>
            <input type="text" name="medicareCompany" value="1000">
        </div>
        <br/>

        <div>
            <label>养老保险(个人)：</label>
            <input type="text" name="pensionInsurance" value="1000">
            <label>养老保险(公司)：</label>
            <input type="text" name="pensionInsuranceCompany" value="1000">
        </div>
        <br/>

        <div>
            <label>工伤保险(个人)：</label>
            <input type="text" name="unemployInsurance" value="1000">
            <label>工伤保险(公司)：</label>
            <input type="text" name="unemployInsuranceCompany" value="1000">
        </div>
        <br/>

        <div>
            <label>个人所得税：</label>
            <input type="text" name="tax" value="1000">
        </div>
        <br/>

        <input type="submit" value="保存" />
    </form>
</body>
</html>
