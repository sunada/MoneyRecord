<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <title>基金操作</title>
        <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
        <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
        <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>
        <script>
            $(document).ready(function(){
                $("#FBUY_RADIO").click(function(){
                    $('.FBUY').show();
                    $('.FREDEMP').hide();
                    $('.FREINVE').hide();
                    $('.FCASH').hide();
                });

                $("#FREDEMP_RADIO").click(function(){
                    $('.FBUY').hide();
                    $('.FREDEMP').show();
                    $('.FREINVE').hide();
                    $('.FCASH').hide();
                });

                $("#FREINVE_RADIO").click(function(){
                    $('.FBUY').hide();
                    $('.FREDEMP').hide();
                    $('.FREINVE').show();
                    $('.FCASH').hide();
                });

                $("#FCASH_RADIO").click(function(){
                    $('.FBUY').hide();
                    $('.FREDEMP').hide();
                    $('.FREINVE').hide();
                    $('.FCASH').show();
                });

                $("#C").focus(function(){
                    $("#prate").attr("value", 0);
                    $("#rrate").attr("value", 0);
                });

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
                    if(net != null && net != '' && amount != null && amount != ''){
                        var clean = amount / (1 + prate / 100);
                        var cost = amount - clean;
                        $("#cost").attr("value", cost.toFixed(2));
                    }
                    if(dealType == 'FREINVE'){
                        $("#amount").attr("value", 0);
                        $("cost").attr("value", 0);
                    }
                    if(dealType == 'FBUY'){
                        var share = (amount - cost) / net;
                        $("#share").attr("value", share.toFixed(2));
                    }
                });

                $("#fredemp_amount").focus(function(){
                    var share = $("#fredemp_share").val();
                    var net = $("#fredemp_net").val();
                    var cost = $("#fredemp_cost").val()
                    var dealType = $('#dealType input[name="dealType"]:checked').val();
                    if(net != null && net != ''){
                        if(dealType == 'FREDEMP') {
                            var clean = net * share - cost;
                            $("#fredemp_amount").attr("value", clean.toFixed(2));
                        }
                    }
                });

                var prate = getUrlParam("prate");
                if(prate == null){
                    prate = 0.15;
                }
                $("#prate").attr("value", prate);

                var rrate = getUrlParam("rrate");
                if(rrate == null){
                    rrate = 0.50;
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
                <li><a href="/balance">收支表</a> </li>
                <li><a href="/fund">基金</a></li>
                <li><a href="/fund/aipDisplay">定投</a></li>
                <li><a href="/loan">网贷</a></li>
                <li><a href="/stock">证券</a></li>
                <li><a href="/insurance">保险</a></li>
                <li  class="active"><a href="/fund/myFundAdd">基金操作</a></li>
                <li><a href="/loan/loanAdd">买入网贷</a></li>
                <li><a href="/stock/stockAdd">证券操作</a></li>
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


            <div class="form-group" id="dealType">
                <label for="dealType"> 基金操作：</label>
                <input type="radio" name="dealType" value="FBUY" id="FBUY_RADIO"/>申购
                <input type="radio" name="dealType" value="FREDEMP" id="FREDEMP_RADIO"/>赎回
                <input type="radio" name="dealType" value="FREINVE" id="FREINVE_RADIO"/>分红再投入
                <input type="radio" name="dealType" value="FCASH" id="FCASH_RADIO"/>现金分红
            </div>

            <c:set var="belong_to" value='<%=request.getParameter("belongTo")%>'/>
            <div class="form-group">
                <label for="belongTo">开户网站：</label>
                <input type="radio" name="belongTo" value="天天基金网" <c:out value='${belong_to=="天天基金网"||belong_to==null? "checked":""}'/>>天天基金网
                <input type="radio" name="belongTo" value="广发基金官网" <c:out value='${belong_to=="广发基金官网"? "checked":""}'/> id="guang"/>广发基金官网
                <input type="radio" name="belongTo" value="银河基金官网" <c:out value='${belong_to=="银河基金官网"? "checked":""}'/> id="yin"/>银河基金官网
                <input type="radio" name="belongTo" value="汇添富基金官网" <c:out value='${belong_to=="汇添富基金官网"? "checked":""}'/> id="hui"/>汇添富基金官网
                <input type="radio" name="belongTo" value="陆金所" <c:out value='${belong_to=="陆金所"? "checked":""}'/> id="lu"/>陆金所
                <input type="radio" name="belongTo" value="蛋卷基金" <c:out value='${belong_to=="蛋卷基金"? "checked":""}'/> id="dan">蛋卷基金
                <input type="radio" name="belongTo" value="支付宝" <c:out value='${belong_to=="支付宝"? "checked":""}'/> id="zhi">支付宝
                <input type="radio" name="belongTo" value="基有帮" <c:out value='${belong_to=="基有帮"? "checked":""}'/> id="ji">基有帮
                <input type="radio" name="belongTo" value="招商银行" <c:out value='${belong_to=="招商银行"? "checked":""}'/> id="zhaoshang">招商银行
            </div>

            <c:set var="risk" value='<%=request.getParameter("risk")%>'/>
            <div class="form-group FBUY" style="display:none">
                <div class="form-group">
                    <label for="risk"> 风险类型：</label>
                        <%--LOW(0, "low"), MIDLOW(1, "midlow"), MID(2, "mid"), MIDHIGH(3, "midhigh"),HIGH(4, "high");--%>
                    <input type="radio" name="risk" value="LOW" <c:out value='${risk=="LOW"? "checked":""}'/>/>低
                    <input type="radio" name="risk" value="MID" <c:out value='${risk=="MID"? "checked":""}'/>/>中等
                    <input type="radio" name="risk" value="HIGH" <c:out value='${risk=="HIGH"||risk==null? "checked":""}'/>/>高
                </div>

                <c:set var="mode" value='<%=request.getParameter("dMode")%>'/>
                <div class="form-group">
                    <label for="dividendMode">分红模式：</label>
                    <input type="radio" name="dividendMode" value="CASH" <c:out value='${mode=="CASH"||mode==null? "checked":""}'/> id="cash">现金分红
                    <input type="radio" name="dividendMode" value="REINVESTMENT" <c:out value='${mode=="REINVESTMENT"? "checked":""}'/> id="reinvest">分红再投资
                </div>

                <%
                    String cm = request.getParameter("cMode");
                    cm = (cm==null) ? "C" : cm;
                %>

                <c:set var="mode" value='<%=request.getParameter("cMode")%>'/>
                <div class="form-group" id="cMode">
                    <label for="chargeMode"> 收费类型：</label>
                    <input type="radio" name="chargeMode" value="FRONT" <c:out value='${mode=="FRONT"||mode==null? "checked":""}'/>/>前端
                    <input type="radio" name="chargeMode" value="BACK" <c:out value='${mode=="BACK"? "checked":""}'/>/>后端
                    <input type="radio" name="chargeMode" value="C" <c:out value='${mode=="C"? "checked":""}'/> id="C"/>C类
                </div>

                <div class="form-group">
                    <label for="prate">申购费率：</label>
                    <input type="text" name="prate" id="prate" value="0.15">%
                </div>

                <div class="form-group">
                    <label for="prate">赎回费率：</label>
                    <input type="text" name="rrate" id="rrate" value="0.5">%
                </div>

                <div class="form-group">
                    <label for="net">申购净值：</label>
                    <input type="text" name="net" id="net">元
                </div>

                <div class="form-group">
                    <label for="net">申购金额：</label>
                    <input type="text" name="amount" id="amount">元
                </div>

                <div class="form-group">
                    <label for="share">申购份额：</label>
                    <input type="text" name="share" id="share">份
                </div>

                <div class="form-group">
                    <label for="net">申购费用：</label>
                    <input type="text" name="cost" id="cost" readonly="true">元
                </div>
            </div>

            <div class="FREDEMP" style="display:none">
                <div class="form-group">
                    <label for="net">赎回净值：</label>
                    <input type="text" name="fredemp_net" id="fredemp_net">元
                </div>

                <div class="form-group">
                    <label for="share">赎回份额：</label>
                    <input type="text" name="fredemp_share" id="fredemp_share">份
                </div>

                <div class="form-group ">
                    <label for="amount">赎回费用：</label>
                    <input type="text" name="fredemp_cost" id="fredemp_cost">元
                </div>

                <div class="form-group">
                    <label for="amount">赎回金额：</label>
                    <input type="text" name="fredemp_amount" id="fredemp_amount" readonly="true">元
                </div>
            </div>

            <div class="form-group FREINVE" style="display:none">
                <label for="amount">分红再投入份额：</label>
                <input type="text" name="freinve_share">份
            </div>

            <div class="form-group FCASH" style="display:none">
                <label for="amount">现金分红金额：</label>
                <input type="text" name="cash_amount">元
            </div>

            <div class="form-group">
                <label for="date">申请日期：</label>
                <input type="text" name="date">
            </div>

            <input type="submit" value="保存" />
        </form>

    </body>
</html>