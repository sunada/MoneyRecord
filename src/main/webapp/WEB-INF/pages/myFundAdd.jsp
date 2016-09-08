<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
        <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(document).ready(function(){
                var risk = getUrlParam('risk');
                if(risk = "HIGH"){
                    $("#high").attr("checked","checked");
                }else if(risk == "LOW"){
                    $("#low").attr("checked","checked");
                }else if(risk == "MIDLOW"){
                    $("#midlow").attr("checked","checked");
                }else if(risk == "MID"){
                    $("#mid").attr("checked","checked");
                }else{
                    $("#midhigh").attr("checked","checked");
                }

                $("#C").focus(function(){
                    $("#prate").attr("value", 0);
                    $("#rrate").attr("value", 0);
                })

                $("#share").focus(function() {
                    var amount = $("#amount").val();
                    var net = $("#net").val();
                    var cMode = $('#cMode input[name="chargeMode"]:checked ').val();
                    var dealType = $('#dealType input[name="dealType"]:checked').val();
//                    var dealType = getUrlParam("dealType");
                    if (cMode != "FRONT" || dealType == 'FREINVE'){
                        $("#prate").attr("value", 0.0);
                        $("#rrate").attr("value", 0.0);
                    }
                    var prate = $("#prate").val();
                    if(net != null && net != ''){
                        var clean = amount / (1 + prate / 100);
                        var cost = amount - clean;
                        $("#cost").attr("value", cost.toFixed(2));
                    }
                    if(dealType == 'FREINVE'){
                        $("#amount").attr("value", 0);
                        $("cost").attr("value", 0);
                    }
                });

                $("#amount").focus(function(){
                    var prate = $("#prate").val();
                    var share = $("#share").val();
                    var net = $("#net").val();
                    var cMode = $('#cMode input[name="chargeMode"]:checked ').val();
//                    if (cMode != "FRONT"){
//                        $("#rrate").attr("value", 0);
//                    }

//                    var rrate = $("#rrate").val();
                    var dealType = $('#dealType input[name="dealType"]:checked').val();
                    if(net != null && net != ''){
                        if(dealType != "FREINVE" && dealType != "FCASH") {
                            var clean = net * share;
                            var cost = clean * prate / 100;
                            $("#cost").attr("value", cost.toFixed(2));
                            $("#amount").attr("value", clean.toFixed(2));
                        }else if(dealType == "FREINVE"){
                            $("#cost").attr("value", 0);
                            $("#amount").attr("value", 0);
                        }
                    }
                });

//                var belongTo = decodeURI(getUrlParam('belongTo')); //这样也不能解决中文乱码的问题
//                if(belongTo == "汇添富基金官网"){
//                    alert(1);
//                    $("#tian").attr("checked","checked");
//                }else if(belongTo == "广发基金官网"){
//                    alert(2);
//                    $("#guang").attr("checked","checked");
//                }else if(belongTo == "银河基金官网"){
//                    alert(3);
//                    $("#yin").attr("checked","checked");
//                }else{
//                    alert(belongTo);
//                    $("#tian").attr("checked","checked");
//                }

                var cMode = getUrlParam("cMode");
                if(cMode == "BACK"){
                    $("#back").attr("checked","checked");
                }else if(cMode == "C"){
                    $("#c").attr("checked","checked");
                }else{
                    $("#front").attr("checked","checked");
                }

                var dMode = getUrlParam("dMode");
                if(dMode == "CASH"){
                    $("#cash").attr("checked","checked");
                }else{
                    $("#reinvest").attr("checked","checked");
                }

                var prate = getUrlParam("prate");
                if(prate == null){
                    prate = 0.6;
                }
                $("#prate").attr("value", prate);

                var rrate = getUrlParam("rrate");
                if(rrate == null){
                    rrate = 0.5;
                }
                $("#rrate").attr("value", rrate);

            });

            function getUrlParam(name) {
                var reg = new RegExp("(^|&)" + name + "=([^&]*)(|&|$)"); //构造一个含有目标参数的正则表达式对象
                var r = window.location.search.substr(1).match(reg); //匹配目标参数
                if (r != null) return unescape(r[2]); return null; //返回参数值
            }

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
                <li><a href="/stock">股票</a></li>
                <li  class="active"><a href="/fund/myFundAdd">买入基金</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">买入股票</a></li>
            </ul>
        </div>
        </nav>

        <form action="/fund/saveMyFund" method="post" accept-charset="utf-8" role="form" name="myFundAdd">

            <%!
                String blanknull(String s){
                    return (s == null) ? "" : s;
                }
            %>


            <div class="form-group">
                <label for="code">基金代码：</label>
                <input type="text" name="code" value="<%=blanknull(request.getParameter("code"))%>"/>
            </div>

            <div class="form-group">
                <label for="name">基金名称：</label>
                <input type="text" name="name" value="<%=blanknull(request.getParameter("name"))%>"/>
            </div>

            <c:set var="dealType" value='<%=request.getParameter("dealType")%>' />

            <div class="form-group" id="dealType">
                <c:choose>
                    <c:when test="${dealType=='FREDEMP' or dealType=='FCASH'}">
                        <label for="dealType"> 赎回 或 现金分红：</label>
                        <input type="radio" name="dealType" value="FREDEMP" checked/>赎回
                        <input type="radio" name="dealType" value="FCASH"/>现金分红
                    </c:when>
                    <c:otherwise>
                        <label for="dealType"> 申购 或 分组再投入：</label>
                        <input type="radio" name="dealType" value="FBUY" checked/>买入
                        <input type="radio" name="dealType" value="FREINVE"/>分红再投入
                    </c:otherwise>
                </c:choose>
            </div>



            <% String i = request.getParameter("belongTo"); %>

            <div class="form-group">
                <label for="belongTo">开户网站：</label>
                <%--因为jquery里不能解决中文乱码的问题，所以用这个方法解决--%>
                <% if (i == null || i.equals("天天基金网")) { %>
                <input type="radio" name="belongTo" value="天天基金网" checked/>天天基金网
                <input type="radio" name="belongTo" value="广发基金官网" id="guang"/>广发基金官网
                <input type="radio" name="belongTo" value="银河基金官网" id="yin"/>银河基金官网
                <input type="radio" name="belongTo" value="汇添富基金官网" id="hui"/>汇添富基金官网
                <input type="radio" name="belongTo" value="陆金所" id="lu"/>陆金所
                <input type="radio" name="belongTo" value="蛋卷基金" id="dan">蛋卷基金
                <input type="radio" name="belongTo" value="支付宝" id="zhi">支付宝
                <%} else if (i.equals("广发基金官网")) { %>
                <input type="radio" name="belongTo" value="天天基金网"/>天天基金网
                <input type="radio" name="belongTo" value="广发基金官网" id="guang" checked/>广发基金官网
                <input type="radio" name="belongTo" value="银河基金官网" id="yin" >银河基金官网
                <input type="radio" name="belongTo" value="汇添富基金官网" id="hui"/>汇添富基金官网
                <input type="radio" name="belongTo" value="陆金所" id="lu"/>陆金所
                <input type="radio" name="belongTo" value="蛋卷基金" id="dan">蛋卷基金
                <input type="radio" name="belongTo" value="支付宝" id="zhi">支付宝
                <%}else if (i.equals("银河基金官网")) { %>
                <input type="radio" name="belongTo" value="天天基金网"/>天天基金网
                <input type="radio" name="belongTo" value="广发基金官网" id="guang"/>广发基金官网
                <input type="radio" name="belongTo" value="银河基金官网" id="yin" checked/>银河基金官网
                <input type="radio" name="belongTo" value="汇添富基金官网" id="hui"/>汇添富基金官网
                <input type="radio" name="belongTo" value="陆金所" id="lu"/>陆金所
                <input type="radio" name="belongTo" value="蛋卷基金" id="dan">蛋卷基金
                <input type="radio" name="belongTo" value="支付宝" id="zhi">支付宝
                <%}else if (i.equals("陆金所")) { %>
                <input type="radio" name="belongTo" value="天天基金网"/>天天基金网
                <input type="radio" name="belongTo" value="广发基金官网" id="guang"/>广发基金官网
                <input type="radio" name="belongTo" value="银河基金官网" id="yin"/>银河基金官网
                <input type="radio" name="belongTo" value="汇添富基金官网" id="hui"/>汇添富基金官网
                <input type="radio" name="belongTo" value="陆金所" id="lu" checked/>陆金所
                <input type="radio" name="belongTo" value="蛋卷基金" id="dan">蛋卷基金
                <input type="radio" name="belongTo" value="支付宝" id="zhi">支付宝
                <%}else { %>
                <input type="radio" name="belongTo" value="天天基金网"/>天天基金网
                <input type="radio" name="belongTo" value="广发基金官网" id="guang"/>广发基金官网
                <input type="radio" name="belongTo" value="银河基金官网" id="yin"/>银河基金官网
                <input type="radio" name="belongTo" value="汇添富基金官网" id="hui" checked/>汇添富基金官网
                <input type="radio" name="belongTo" value="陆金所" id="lu"/>陆金所
                <input type="radio" name="belongTo" value="蛋卷基金" id="dan">蛋卷基金
                <input type="radio" name="belongTo" value="支付宝" id="zhi">支付宝
                <%}%>
            </div>

            <c:choose>
                <c:when test="${dealType!='FREDEMP' and dealType != 'FCASH'}">
                    <div class="form-group">
                        <label for="risk"> 风险类型：</label>
                            <%--LOW(0, "low"), MIDLOW(1, "midlow"), MID(2, "mid"), MIDHIGH(3, "midhigh"),HIGH(4, "high");--%>
                        <input type="radio" name="risk" value="LOW"/>低
                        <input type="radio" name="risk" value="MID"/>中等
                        <input type="radio" name="risk" value="HIGH" checked/>高
                    </div>
                    <div class="form-group">
                        <label for="dividendMode">分红模式：</label>
                        <input type="radio" name="dividendMode" value="CASH" id="cash">现金分红
                        <input type="radio" name="dividendMode" value="REINVESTMENT" id="reinvest">分红再投资
                    </div>

                    <div class="form-group" id="cMode">
                        <label for="chargeMode"> 收费类型：</label>
                        <input type="radio" name="chargeMode" value="FRONT"/>前端
                        <input type="radio" name="chargeMode" value="BACK"/>后端
                        <input type="radio" name="chargeMode" value="C" id="C"/>C类
                    </div>

                    <div class="form-group">
                        <label for="prate">申购费率：</label>
                        <input type="text" name="prate" id="prate">%
                    </div>
                    <div class="form-group">
                        <label for="prate">赎回费率：</label>
                        <input type="text" name="rrate" id="rrate">%
                    </div>
                </c:when>
            </c:choose>

            <div class="form-group">
                <c:choose>
                    <c:when test="${dealType=='FREDEMP'}">
                        <label for="net">赎回净值：</label>
                        <input type="text" name="net" id="net">元
                    </c:when>
                    <%--<c:when test="$dealType == 'FCASH'}">--%>
                    <%--</c:when>--%>
                    <c:when test="${dealType != 'FCASH'}">
                        <label for="net">申购净值：</label>
                        <input type="text" name="net" id="net">元
                    </c:when>
                    <%--</c:otherwise>--%>
                </c:choose>

            </div>

            <div class="form-group">
                <c:choose>
                    <c:when test="${dealType != 'FCASH'}">
                        <label for="share">交易份额：</label>
                        <input type="text" name="share" id="share">份
                    </c:when>
                </c:choose>
            </div>

            <div class="form-group">
                <c:choose>
                    <c:when test="${dealType=='FREDEMP'}">
                        <label for="amount">赎回金额：</label>
                        <input type="text" name="amount" id="amount">元 (无需修改，使用程序自动运算结果)
                    </c:when>
                    <c:when test="${dealType != 'FCASH'}">
                        <label for="net">申购金额：</label>
                        <input type="text" name="amount" id="amount">元
                    </c:when>
                </c:choose>

            </div>

            <div class="form-group">
                <c:choose>
                    <c:when test="${dealType=='FREDEMP'}">
                        <label for="amount">赎回费用：</label>
                        <input type="text" name="cost" id="cost">元
                    </c:when>
                    <c:when test="${dealType != 'FCASH'}">
                        <label for="net">申购费用：</label>
                        <input type="text" name="cost" id="cost">元
                    </c:when>
                </c:choose>
            </div>



            <div class="form-group">
                <c:choose>
                    <c:when test="${dealType == 'FCASH'}">
                        <label for="cash_amount">现金分红金额：</label>
                        <input type="text" name="cash_amount" id="cash_amount">元
                    </c:when>
                </c:choose>
            </div>

            <div class="form-group">
                <label for="date">申请日期：</label>
                <input type="text" name="date" id="date">
            </div>


            <input type="submit" value="保存" />
        </form>

    </body>
</html>