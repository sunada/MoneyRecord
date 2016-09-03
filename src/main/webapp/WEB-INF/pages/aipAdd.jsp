<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
        <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

        <script>
            function changePaymentDate() {
                type = $("input[name='interval']:checked").val();
                var i;
                var week=new Array();
                week[0]="MON";
                week[1]="TUE";
                week[2]="WEN";
                week[3]="THU";
                week[4]="FRI";

                if (type == 'MONTH') {
                    document.aip.tmpDate.length=31;
                    for (i=1;i<=31;i++) {//改变下拉菜单的内容
                        document.aip.tmpDate[i-1]=new Option(i,i);
                    }
                }else{
                    document.aip.tmpDate.length=5;
                    for(i=0;i<5;i++){
                        document.aip.tmpDate[i]=new Option(week[i],week[i]);
                    }
                }
            }
        </script>
    </head>

    <body>

        <nav class="navbar navbar-default" role="navigation">
            <div>
                <ul class="nav navbar-nav">
                    <li><a href="/inAll">总览</a></li>
                    <li><a href="/fund">基金</a></li>
                    <li class="active"><a href="/fund/aipDisplay">定投</a></li>
                    <li><a href="/loan">网贷</a></li>
                    <li><a href="/stock">股票</a></li>
                    <li><a href="/fund/myFundAdd">买入基金</a></li>
                    <li><a href="/loan/loanAdd">买入网贷</a></li>
                    <li><a href="/stock/stockAdd">买入股票</a></li>
                </ul>
            </div>
        </nav>

            <form action="/fund/saveAip" method="post" accept-charset="utf-8" role="form" name="aip">

                <div class="form-group">
                    <label for="code">基金代码：</label>
                    <input type="text" name="code"/>
                </div>

                <div class="form-group">
                    <label for="name">基金名称：</label>
                    <input type="text" name="name"/>
                </div>

                <div class="form-group">
                    <label for="risk"> 基金风险类型：</label>
                    <%--LOW(0, "low"), MIDLOW(1, "midlow"), MID(2, "mid"), MIDHIGH(3, "midhigh"),HIGH(4, "high");--%>
                    <input type="radio" name="risk" value="LOW"/>低
                    <input type="radio" name="risk" value="MIDLOW"/>中低
                    <input type="radio" name="risk" value="MID"/>中等
                    <input type="radio" name="risk" value="MIDHIGH" checked/>中高
                    <input type="radio" name="risk" value="HIGH"/>高
                </div>

                <div class="form-group">
                    <label for="belongTo">开户网站：</label>
                    <input type="radio" name="belongTo" value="天天基金网" checked/>天天基金网
                    <input type="radio" name="belongTo" value="广发基金官网"/>广发基金官网
                    <input type="radio" name="belongTo" value="银河基金官网"/>银河基金官网
                    <input type="radio" name="belongTo" value="汇添富基金官网"/>汇添富基金官网
                    <input type="radio" name="belongTo" value="支付宝"/>支付宝
                </div>

                <div class="form-group">
                    <label for="prate">申购费率：</label>
                    <input type="text" name="prate" value="0.6">%
                </div>

                <div class="form-group">
                    <label for="rrate">赎回费率：</label>
                    <input type="text" name="rrate" value="0.5">%
                </div>

                <div class="form-group">
                    <label for="amount">定投金额：</label>
                    <input type="text" name="amount">元
                </div>

                <div class="form-group">
                    <label for="interval">定投周期：</label>
                    <input type="radio" name="interval" value="ONEWEEK" checked ONCHANGE="changePaymentDate()"/>每周
                    <input type="radio" name="interval" value="TWOWEEKS" ONCHANGE="changePaymentDate()"/>双周
                    <input type="radio" name="interval" value="MONTH" ONCHANGE="changePaymentDate()"/>每月
                </div>

                <div class="form-group">
                    <label>扣款日期（周几）：</label>
                    <select name="tmpDate" size="1">
                        <option value="MON">MON</option>
                        <option value="TUE">TUE</option>
                        <option value="WEN">WEN</option>
                        <option value="THU">THU</option>
                        <option value="FRI">FRI</option>
                    </select>
                </div>

                <div class="form-group">
                    <label for="chargeMode">收费模式：</label>
                    <input type="radio" name="chargeMode" value="FRONT" checked>前端
                    <input type="radio" name="chargeMode" value="BACK">后端
                    <input type="radio" name="chargeMode" value="C">C
                </div>

                <div class="form-group">
                    <label for="dividendMode">分红模式：</label>
                    <input type="radio" name="dividendMode" value="CASH">现金分红
                    <input type="radio" name="dividendMode" value="REINVESTMENT" checked>分红再投资
                </div>

                <div class="form-group">
                    <label for="startTime">开始定投时间：</label>
                    <input type="text" name="startTime">
                </div>
            <input type="submit" value="保存" />
        </form>

    </body>
</html>