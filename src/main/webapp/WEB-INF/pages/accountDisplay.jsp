<%@ page import="java.net.URLEncoder" %>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
    <link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
    <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
    <%--<script src="<c:url value="/resources/js/main.js" />"></script>--%>


    <script>
        $(function(){
            $('.save').hide();
            $('.edit').click(function(){
                $(this).closest('td').siblings().html(function(i,html){
                    return '<input type="text" value='+html+' />';
                });
                $(this).hide();
                $(this).siblings().show();
            })
        })

        $(function(){
            $('.save').click(function(){
                var list = $(this).parent().parent().find("td :input[type='text']");
                var para = new Array();
                $.each(list, function (i, obj) {
                    $(obj).parent().html($(obj).val());
                    para.push($(obj).val());
                    //alert($(obj).val());  //结果为panda,浦发银行，1000
                });
                //alert(para.toString());
                $(this).hide();
                $(this).siblings().show();
                var str = "id=" + para[0] + "&type=" + para[1] + "&owner=" + para[2] + "&account=" + para[3]
                                + "&balance=" + para[4];
                alert(str);
                //ajax提交表单
                $.ajax({
                    type: "post",
                    url: "/update",
//                    data: $("#studentInfo_form").serialize(),  //提交整个表格
                    data: str
//                    success: function() {
//                        alert("保存成功！");
//                    },
//                    error: function() {
//                        alert("出错啦");
//                    }
                })
            });
        })
    </script>
</head>
<body>

<br/>
<br/>

<h3>所有帐户</h3>

<a href="http://localhost:8080/fund">常用基金库</a><br><br>

<%--<a href="http://localhost:8080/hello">hello</a><br><br>--%>

<ul id="tab">
    <li class="fli">信用卡</li>
    <li>储蓄卡</li>
    <li>基金</li>
    <li>A股</li>
    <li>网贷</li>
</ul>
<div id="tab_con">
    <div class="fdiv">
        <table style="width:100%" cellspacing="0" >
            <tr id="title">
                <%--<td>类型</td>--%>
                <td>持有人</td>
                <%--<td>尾号</td>--%>
                <td>银行</td>
                <td>余额</td>
                <%--<td>最后还款日</td>--%>
                <%--<td>帐单日</td>--%>
                <td>操作</td>
            </tr>
            <c:forEach items="${creditCards}" var="creditCard" varStatus="status">
                <tr <c:if test="${status.count%2==0}"> bgcolor="#E6E6FA" </c:if>>
                    <td style="display: none">${creditCard.accountId}</td>
                    <td style="display: none">${creditCard.type}</td>
                    <td>${creditCard.owner}</td>
                    <%--<td>${creditCard.tailNum}</td>--%>
                    <td>${creditCard.account}</td>
                    <td>${creditCard.balance}</td>
                    <%--<td>${creditCard.finalRepaymentDate}</td>--%>
                    <%--<td>${creditCard.billDate}</td>--%>
                    <td>
                        <a style="cursor: pointer;color: blue" class="edit">编辑</a>
                        <a style="cursor: pointer;color: blue" class="save">保存</a>
                        <a href="/delete?id=${creditCard.accountId}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td><a href="/account/accountAdd">增加帐户</a></td>
                <td><input type="button" value="增加帐户"></td>
            </tr>
        </table>
    </div>
    <div>
        <table style="width:100%" cellspacing="0" >
            <tr id="title">
                <%--<td>类型</td>--%>
                <td>持有人</td>
                <%--<td>尾号</td>--%>
                <td>银行</td>
                <td>余额</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${debitCards}" var="debitCard" varStatus="status">
                <tr <c:if test="${status.count%2==0}"> bgcolor="#E6E6FA" </c:if>>
                    <td style="display: none">${debitCard.accountId}</td>
                    <td style="display: none">${debitCard.type}</td>
                    <td>${debitCard.owner}</td>
                    <%--<td>${debitCard.tailNum}</td>--%>
                    <%--使用下面这个会影响点击edit的表现。只能取到<a，取不到${debitCard.account}的值--%>
                    <%--<td><a href="/deposit?account=${debitCard}">${debitCard.account}</a></td>--%>
                    <td>${debitCard.account}</td>
                    <td>${debitCard.balance}</td>
                    <td>
                        <a style="cursor: pointer;color: blue" class="edit">编辑</a>
                        <a style="cursor: pointer;color: blue" class="save">保存</a>
                        <a href="/delete?id=${debitCard.accountId}">删除</a>
                    </td>
                </tr>
            </c:forEach>

        </table>
        </br>
        <tr>
            <td><a href="/account/accountAdd">增加帐户</a></td>
            <td><input type="button" value="增加帐户"></td>
        </tr>
    </div>
    <div>
        <table style="width:100%" cellspacing="0" >
            <tr id="title">
                <%--<td>类型</td>--%>
                <td>持有人</td>
                <td>网站</td>
                <td>投资额</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${funds}" var="fund" varStatus="status">
                <tr <c:if test="${status.count%2==0}"> bgcolor="#E6E6FA" </c:if>>
                    <td style="display: none">${fund.accountId}</td>
                    <td style="display: none">${fund.type}</td>
                    <td>${fund.owner}</td>
                    <td>${fund.account}</td>
                    <td>${fund.balance}</td>
                    <td>
                        <a href="/fund?belongTo=${fund.accountId}">查看</a>
                        <a style="cursor: pointer;color: blue" class="edit">编辑</a>
                        <a style="cursor: pointer;color: blue" class="save">保存</a>
                        <a href="/delete?id=${fund.accountId}">删除</a>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td><a href="/account/accountAdd">增加基金帐户</a></td>
                <td><a href="/fund/aipAdd">新增定投</a></td>
                <td><input type="button" value="增加基金帐户"></td>
            </tr>
        </table>
    </div>
    <div>
        <table style="width:100%" cellspacing="0" >
            <tr id="title">
                <%--<td>类型</td>--%>
                <td>持有人</td>
                <td>证券公司</td>
                <td>投资额</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${As}" var="A" varStatus="status">
                <tr <c:if test="${status.count%2==0}"> bgcolor="#E6E6FA" </c:if>>
                    <td style="display: none">${A.accountId}</td>
                    <td style="display: none">${A.type}</td>
                    <td>${A.owner}</td>
                    <td>${A.account}</td>
                    <td>${A.balance}</td>
                    <td>
                        <a style="cursor: pointer;color: blue" class="edit">编辑</a>
                        <a style="cursor: pointer;color: blue" class="save">保存</a>
                        <a href="/delete?id=${A.accountId}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </br>
        <tr>
            <td><a href="/account/accountAdd">增加帐户</a></td>
            <td><input type="button" value="增加帐户"></td>
        </tr>
    </div>
    <div>
        <table style="width:100%" cellspacing="0" >
            <tr id="title">
                <%--<td>类型</td>--%>
                <td>持有人</td>
                <td>网站</td>
                <td>投资额</td>
                <td>操作</td>
            </tr>
            <c:forEach items="${loans}" var="loan" varStatus="status">
                <tr <c:if test="${status.count%2==0}"> bgcolor="#E6E6FA" </c:if>>
                    <td style="display:none">${loan.accountId}</td>
                    <td style="display: none">${loan.type}</td>
                    <td>${loan.owner}</td>
                    <td>${loan.account}</td>
                    <td>${loan.balance}</td>
                    <td>
                        <a style="cursor: pointer;color: blue" class="edit">编辑</a>
                        <a style="cursor: pointer;color: blue" class="save">保存</a>
                        <a href="/delete?id=${loan.accountId}">删除</a>
                    </td>
                </tr>
            </c:forEach>
        </table>
        </br>
        <tr>
            <td><a href="/account/accountAdd">增加帐户</a></td>
            <td><input type="button" value="增加帐户"></td>
        </tr>
    </div>
</div>


<script type="text/javascript">
    var tabs=document.getElementById("tab").getElementsByTagName("li");
    var divs=document.getElementById("tab_con").getElementsByTagName("div");
    for(var i=0;i<tabs.length;i++){
        //tabs[i].onclick=function(){change(this);}
        tabs[i].onmouseover=function(){change(this);}

    }

    function change(obj){
        for(var i=0;i<tabs.length;i++)
        {
            if(tabs[i]==obj){
                tabs[i].className="fli";
                divs[i].className="fdiv";
            }
            else{
                tabs[i].className="";
                divs[i].className="";
            }
        }
    }

</script>

<br/>
<br/>

<br/>

</body>
</html>