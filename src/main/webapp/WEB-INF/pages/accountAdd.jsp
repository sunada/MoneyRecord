<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
    <head>
        <script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>
        <%--<script src="<c:url value="/resources/js/main.js" />"></script>--%>
        <script>
            function showDes(){
                type = $("input[name='type']:checked").val();
                if(type == 'CREDIT' || type == 'DEPOSIT'){
                    document.getElementById("account").innerHTML = "开户行：";
                }else if(type == 'FUND'){
                    document.getElementById("account").innerHTML = "开户网站：";
                }else if(type  == 'SECURITIES'){
                    document.getElementById("account").innerHTML = "证券公司：";
                }else{
                    document.getElementById("account").innerHTML = "网贷网站：";
                }
            }
        </script>

    </head>

    <body>
            <form action="/accountAdd/save" method="post" accept-charset="utf-8">
                开户人：
                <input type="radio" name="owner" value="老婆" checked/>老婆
                <input type="radio" name="owner" value="老公" />老公<br/><br/>

                账户类型：
                <%--CREDIT(0,"credit"),DEPOSIT(1,"deposit"), FUND(2,"fund"),SECURITIES(3,"securities"),LOAN(4,"loan");--%>
                <input type="radio" name="type" value="CREDIT" onclick="showDes()"/>信用卡
                <input type="radio" name="type" value="DEPOSIT" onclick="showDes()"/>借记卡
                <input type="radio" name="type" value="FUND" onclick="showDes()" checked/>基金
                <input type="radio" name="type" value="SECURITIES" onclick="showDes()"/>A股
                <input type="radio" name="type" value="LOAN" onclick="showDes()"/>借贷（应收款）<br/><br/>

                <div id="account">
                    开户网站：
                </div>
                <input type="text" name="account"/><br/><br/>

            <input type="submit" value="保存" />
        </form>
    </body>
</html>