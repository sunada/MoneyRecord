<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2016/9/25
  Time: 20:22
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>新增保险</title>
    <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
    <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
    <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
    <script src="/resources/js/sortTable.js" type="text/javascript"></script>
</head>
<body>
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
                <li><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">证券操作</a></li>
            </ul>
        </div>
    </nav>

    <form action="/insurance/save" method="get" accept-charset="utf-8" role="form">
        <div class="form-group">
            <label>产品名：  </label>
            <input type="text" name="name">
        </div>

        <div class="form-group">
            <label>平   台：</label>
            <input type="radio" name="belongTo" value="泰康">泰康
            <input type="radio" name="belongTo" value="阳光">阳光
            <input type="radio" name="belongTo" value="国泰">国泰
            <input type="radio" name="belongTo" value="弘康">弘康
            <input type="radio" name="belongTo" value="平安">平安
        </div>

        <div class="form-group">
            <label>保险类型：</label>
            <input type="radio" name="type" value="重疾">重疾
            <input type="radio" name="type" value="人寿">人寿
            <input type="radio" name="type" value="意外">意外
            <input type="radio" name="type" value="防癌">防癌
            <input type="radio" name="type" value="医疗">医疗
        </div>

        <div class="form-group">
            <label>被保险人：</label>
            <input type="radio" name="owner" value="老婆">老婆
            <input type="radio" name="owner" value="老公">老公
            <input type="radio" name="owner" value="老婆的妈">老婆的妈
            <input type="radio" name="owner" value="老公的妈">老公的妈
        </div>

        <div class="form-group">
            <label>保    额：</label>
            <input type="text" name="coverage">万
        </div>

        <div class="form-group">
            <label>交费年限：</label>
            <input type="text" name="years">年
        </div>

        <div class="form-group">
            <label>生效时间</label>
            <input type="text" name="start">
        </div>

        <div class="form-group">
            <label>保障期限：</label>
            <input type="text" name="age">岁
        </div>

        <div class="form-group">
            <label>年交费：  </label>
            <input type="text" name="amount">元
        </div>

        <button type="submit">保存</button>
    </form>
</body>
</html>
