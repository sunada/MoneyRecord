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
                <li><a href="/stock">证券</a></li>
                <%--<li><a href="/fund/aipDisplay">定投</a></li>--%>
                <li><a href="/loan">网贷</a></li>
                <li class="active"><a href="/insurance">保险</a></li>
                <%--<li><a href="/fund/myFundAdd">基金操作</a></li>--%>
                <%--<li><a href="/loan/loanAdd">买入网贷</a></li>--%>
                <%--<li><a href="/stock/stockAdd">证券操作</a></li>--%>
                <%--<li><a href="/fund/updateFundNet">更新基金净值</a></li>--%>
                <%--<li><a href="/deal/updateAipDeal">更新定投交易</a></li>--%>
            </ul>
        </div>
    </nav>

    <script>
        $(document).ready(function() {
            $("#husband").focus(function () {
                $("#beforeTax").attr("value", 38500);
                $("#insuranceBase").attr("value", 23000);
                $("#fundBase").attr("value", 23116);
                $("#date").attr("value", "2018-0");
                $("#afterTax").attr("value", 0)
            });

            $("#wife").focus(function () {
                $("#date").attr("value", "2018-0");
                $("#beforeTax").attr("value", 29700);
                $("#insuranceBase").attr("value", 23120);
                $("#fundBase").attr("value", 23120);

                $("#afterTax").attr("value", 0)

            });

            $("#afterTax").focus(function(){
                var insuranceBase = $("#insuranceBase").val();
                var fundBase = $("#fundBase").val();
                var beforeTax = $("#beforeTax").val();
                var tmp1 = fundBase * 0.12;
                if(tmp1 > 2774) tmp1 = 2774;
                $("#houseFunds").attr("value", tmp1.toFixed(0));
                $("#houseFundsCompany").attr("value", tmp1.toFixed(0));
                $("#medicareCompany").attr("value", insuranceBase * 0.1);
                $("#medicare").attr("value", (insuranceBase * 0.020148).toFixed(2));
                $("#pensionInsuranceCompany").attr("value", insuranceBase * 0.19);
                $("#pensionInsurance").attr("value", insuranceBase * 0.08);
                $("#unemployInsurance").attr("value", (insuranceBase * 0.002).toFixed(2));
                $("#unemployInsuranceCompany").attr("value", (insuranceBase * 0.008).toFixed(2));

                var houseFunds = $("#houseFunds").val();
                var medicare = $("#medicare").val();
                var pensionInsurance = $("#pensionInsurance").val();
                var unemployInsurance = $("#unemployInsurance").val();

                var factor1 = 0;
                var factor2 = 0;
                var tmp = beforeTax - 3500 - houseFunds - medicare - pensionInsurance - unemployInsurance;
                if(tmp > 9000 && tmp < 35000){
                    factor1 = 0.25;
                    factor2 = 1005;
                }

                var tax = tmp * factor1 - factor2;
                $("#tax").attr("value", tax.toFixed(2));
                $("#afterTax").attr("value", (tmp + 3500 - tax).toFixed(2));

                $("#mediaCash").attr("value", (insuranceBase * 0.020148 + insuranceBase*0.008).toFixed(2));
            })
        });

    </script>
</head>
<body>
    <form action="/balance/saveSalary" method="post" accept-charset="utf-8" role="form">

        <div>
            <label>来源：</label>
            <input type="radio" name="owner" value="老婆" id="wife">老婆
            <input type="radio" name="owner" value="老公" id="husband">老公
        </div>
        <br/>

        <div>
            <label>日期：</label>
            <input type="text" name="date" id="date">
        </div>

        <br/>
        <div>
            <label>税前工资：</label>
            <input type="text" name="beforeTax" id="beforeTax">

            <label>社保基数：</label>
            <input type="text" name="insuranceBase" id="insuranceBase">

            <label>公积金基数：</label>
            <input type="text" name="fundBase" id="fundBase">
        </div>
        <br/>

        <div>
            <label>税&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;后：</label>
            <input type="text" name="afterTax" id="afterTax">
        </div>
        <br/>

        <div>
            <label>公积金（个人）：</label>
            <input type="text" name="houseFunds" id="houseFunds"> (12%)
            <label>公积金（公司）：</label>
            <input type="text" name="houseFundsCompany" id="houseFundsCompany"> (12%)
        </div>
        <br/>

        <div>
            <label>医疗保险(个人)：</label>
            <input type="text" name="medicare" id="medicare"> (2.0148%)
            <label>医疗保险(公司)：</label>
            <input type="text" name="medicareCompany" id="medicareCompany"> (10%)
        </div>
        <br/>

        <div>
            <label>养老保险(个人)：</label>
            <input type="text" name="pensionInsurance" id="pensionInsurance"> (8%)
            <label>养老保险(公司)：</label>
            <input type="text" name="pensionInsuranceCompany" id="pensionInsuranceCompany"> (19%)
        </div>
        <br/>

        <div>
            <label>失业保险(个人)：</label>
            <input type="text" name="unemployInsurance" id="unemployInsurance"> (0.2%)
            <label>失业保险(公司)：</label>
            <input type="text" name="unemployInsuranceCompany" id="unemployInsuranceCompany"> (0.8%)
        </div>
        <br/>

        <div>
            <label>个人所得税：</label>
            <input type="text" name="tax" id="tax">
        </div>
        <br/>

        <div>
            <label>计算得到医保个人账户金额：</label>
            <input type="text" name="mediaCash" id="mediaCash"/>
        </div>
        <br/>

        <div>
            <label>备注：</label>
            <input type="text" name="note" id="note"/>
        </div>
        <br/>

        <input type="submit" value="保存" />
    </form>
</body>
</html>
