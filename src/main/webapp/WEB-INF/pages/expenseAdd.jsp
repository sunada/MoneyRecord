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
    <title>新增支出</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

    <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li><a href="/inAll">总览</a></li>
                <li><a href="/balance">收支表</a> </li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/stock">证券</a></li>
                <%--<li><a href="/fund/aipDisplay">定投</a></li>--%>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/insurance">保险</a></li>
                <%--<li><a href="/fund/myFundAdd">买入基金</a></li>--%>
                <%--<li><a href="/loan/loanAdd">买入网贷</a></li>--%>
                <%--<li><a href="/stock/stockAdd">买入证券</a></li>--%>
            </ul>
        </div>
    </nav>
</head>
<body>
    <form action="/balance/saveExpense" method="post" accept-charset="utf-8" role="form">

        <div>
            <label>日期：</label>
            <input type="text" name="date" id="date" value="2017-0">
        </div>

        <br/>
        <div>
            <label>消费支出：</label>
            <input type="text" name="dailyExpense" id="dailyExpense">

            <label>房贷支出：</label>
            <input type="text" name="mortgage" id="mortgage" value="7795.79">

        </div>
        <br/>

        <div>
            <label>备注：</label>
            <input type="text" name="note" id="note">
        </div>
        <br/>

        <input type="submit" value="保存" />
    </form>
</body>
</html>
