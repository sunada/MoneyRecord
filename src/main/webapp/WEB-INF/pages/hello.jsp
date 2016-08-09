<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
    <head>
        <%--<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">--%>
        <%--<script src="http://ajax.aspnetcdn.com/ajax/jQuery/jquery-1.8.0.js"></script>--%>
        <%--<script src="<c:url value="/resources/js/main.js" />"></script>--%>

        <%--<link href="http://libs.baidu.com/bootstrap/3.0.3/css/bootstrap.min.css" rel="stylesheet">--%>
        <%--<script src="http://libs.baidu.com/jquery/2.0.0/jquery.min.js"></script>--%>
        <%--<script src="http://libs.baidu.com/bootstrap/3.0.3/js/bootstrap.min.js"></script>--%>

            <link href="/resources/css/bootstrap.min.css" rel="stylesheet" type="text/css">
            <script src="/resources/js/jquery-2.2.3.min.js" type="text/javascript"></script>
            <script src="/resources/js/bootstrap.min.js" type="text/javascript"></script>

        <script>
            $(function(){
                $('.save').hide();
                $('.edit').click(function(){
                    $(this).closest('td').siblings().html(function(i,html){
                        return '<input type="text" value='+html+' />';
                    });
                    $(this).hide();
                    $(this).siblings().show();
//                    $('#save').show();
                })
            })

            $(function(){
                $('.save').click(function(){
//                    var t_this = $(this);
//                    var list = $(t_this).parent().parent().find("td :input[type='text']");
                    var list = $(this).parent().parent().find("td :input[type='text']");
                    $.each(list, function (i, obj) {
                        $(obj).parent().html($(obj).val());
                    });
                    $(this).hide();
                    $('.edit').show();
                });
            })

            $(function() {
//获取class为caname的元素
                $(".caname").click(function() {
                    var td = $(this);
                    var txt = td.text();
                    var input = $("<input type='text'value='" + txt + "'/>");
                    td.html(input);
                    input.click(function() { return false; });
//获取焦点
                    input.trigger("focus");
//文本框失去焦点后提交内容，重新变为文本
                    input.blur(function() {
                        var newtxt = $(this).val();
//判断文本有没有修改
                        if (newtxt != txt) {
                            td.html(newtxt);
                            /*
                             *不需要使用数据库的这段可以不需要
                             var caid = $.trim(td.prev().text());
                             //ajax异步更改数据库,加参数date是解决缓存问题
                             var url = "../common/Handler2.ashx?caname=" + newtxt + "&caid=" + caid + "&date=" + new Date();
                             //使用get()方法打开一个一般处理程序，data接受返回的参数（在一般处理程序中返回参数的方法 context.Response.Write("要返回的参数");）
                             //数据库的修改就在一般处理程序中完成
                             $.get(url, function(data) {
                             if(data=="1")
                             {
                             alert("该类别已存在！");
                             td.html(txt);
                             return;
                             }
                             alert(data);
                             td.html(newtxt);
                             });
                             */
                        }
                        else
                        {
                            td.html(newtxt);
                        }
                    });
                });
            });
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
                <li  class="active"><a href="/">回首页</a></li>
            </ul>
        </div>
    </nav>

    <table width="200">
        <tr>
            <td><b>ID</b></td>
            <td><b>名称</b></td>
            <td><b>操作</b></td>
        </tr>
        <tr>
            <td><b>1</b></td>
            <td class="caname"><b>哈哈</b></td>
            <td><b>删除</b></td>
        </tr>
        <tr>
            <td><b>2</b></td>
            <td class="caname"><b>哈哈</b></td>
            <td><b>删除</b></td>
        </tr>
        <tr>
            <td><b>3</b></td>
            <td class="caname"><b>哈哈</b></td>
            <td><b>删除</b></td>
        </tr>
    </table>

    <form role="form">
        <div class="form-group">
            <label for="name">名称</label>
            <input type="text" class="form-control" id="name"
                   placeholder="请输入名称">
        </div>
        <div class="form-group">
            <label for="inputfile">文件输入</label>
            <input type="file" id="inputfile">
            <p class="help-block">这里是块级帮助文本的实例。</p>
        </div>
        <div class="checkbox">
            <label>
                <input type="checkbox"> 请打勾
            </label>
        </div>
        <button type="submit" class="btn btn-default">提交</button>
    </form>

    <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
            <a class="navbar-brand" href="#">W3Cschool</a>
        </div>
        <div>
            <!--向左对齐-->
            <ul class="nav navbar-nav navbar-left">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Java
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">jmeter</a></li>
                        <li><a href="#">EJB</a></li>
                        <li><a href="#">Jasper Report</a></li>
                        <li class="divider"></li>
                        <li><a href="#">分离的链接</a></li>
                        <li class="divider"></li>
                        <li><a href="#">另一个分离的链接</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-left" role="search">
                <button type="submit" class="btn btn-default">
                    向左对齐-提交按钮
                </button>
            </form>
            <p class="navbar-text navbar-left">向左对齐-文本</p>
            <!--向右对齐-->
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Java <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">jmeter</a></li>
                        <li><a href="#">EJB</a></li>
                        <li><a href="#">Jasper Report</a></li>
                        <li class="divider"></li>
                        <li><a href="#">分离的链接</a></li>
                        <li class="divider"></li>
                        <li><a href="#">另一个分离的链接</a></li>
                    </ul>
                </li>
            </ul>
            <form class="navbar-form navbar-right" role="search">
                <button type="submit" class="btn btn-default">
                    向右对齐-提交按钮
                </button>
            </form>
            <p class="navbar-text navbar-right">向右对齐-文本</p>
        </div>
    </nav>

    <nav class="navbar navbar-default" role="navigation">
        <div>
            <ul class="nav navbar-nav">
                <li class="active"><a href="#">iOS</a></li>
                <li><a href="#">SVN</a></li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown">
                        Java
                        <b class="caret"></b>
                    </a>
                    <ul class="dropdown-menu">
                        <li><a href="#">jmeter</a></li>
                        <li><a href="#">EJB</a></li>
                        <li><a href="#">Jasper Report</a></li>
                        <li class="divider"></li>
                        <li><a href="#">分离的链接</a></li>
                        <li class="divider"></li>
                        <li><a href="#">另一个分离的链接</a></li>
                    </ul>
                </li>
            </ul>
        </div>
    </nav>

    <SCRIPT LANGUAGE=javascript>

        //定义一个二维数组aArray,用于存放城市名称。
        var aCity=new Array();
        aCity[0]=new Array();
        aCity[1]=new Array();
        aCity[2]=new Array();
        aCity[3]=new Array();
        //赋值，每个省份的城市存放于数组的一行。
        aCity[0][0]="--请选择--";
        aCity[1][0]="--请选择--";
        aCity[1][1]="广州市";
        aCity[1][2]="深圳市";
        aCity[1][3]="珠海市";
        aCity[1][4]="汕头市";
        aCity[1][5]="佛山市";
        aCity[2][0]="--请选择--";
        aCity[2][1]="长沙市";
        aCity[2][2]="株州市";
        aCity[2][3]="湘潭市";
        aCity[3][0]="--请选择--";
        aCity[3][1]="杭州市";
        aCity[3][2]="苏州市";
        aCity[3][3]="温州市";
        function ChangeCity()
        {
            var i,iProvinceIndex;
            iProvinceIndex=document.frm.optProvince.selectedIndex;
            iCityCount=0;
            while (aCity[iProvinceIndex][iCityCount]!=null)
                iCityCount++;
            //计算选定省份的城市个数
            document.frm.optCity.length=iCityCount;//改变下拉菜单的选项数
            for (i=0;i<=iCityCount-1;i++)//改变下拉菜单的内容
                document.frm.optCity[i]=new Option(i);
//                document.frm.optCity[i]=new Option(aCity[iProvinceIndex][i]);
//            document.frm.optCity.focus();
        }

    </SCRIPT>

    <%--<BODY ONfocus=ChangeCity()>--%>
    <H3>选择你所在的省份及城市</H3>
    <FORM NAME="frm">
        <P>省份：
        <SELECT NAME="optProvince" SIZE="1" ONCHANGE=ChangeCity()>
            <OPTION>--请选择--</OPTION>
            <OPTION>广东省</OPTION>
            <OPTION>湖南省</OPTION>
            <OPTION>浙江省</OPTION>
            </SELECT>
        </P>
        <P>城市：
        <SELECT NAME="optCity" SIZE="1">
            <OPTION>--请选择--</OPTION>
            </SELECT>
        </P>
    </FORM>


    <a href="/account">帐户总览</a>
    <a href="/fund">常用基金帐户</a>
        <br/>
        <br/>

        <table id="tab">
            <tr>
                <td>td1</td>
                <td>td2</td>
                <td>
                    <a style="cursor: hand;cursor: pointer;" class="edit">edit</a>
                    <a class="save">save</a>
                </td>
            </tr>
            <tr>
                <td>td3</td>
                <td>td4</td>
                <td>
                    <a class="edit">edit</a>
                    <a class="save">save</a>
                </td>
            </tr>
        </table>

        hello world!

        <table>
            <tr>
                <td id="id1">1</td>
                <td id="id2">2</td>
                <td id="id3">3</td>
                <td><input type="button" value="编辑" id="button1"></td>
            </tr>
            <tr>
                <td>a</td>
                <td>b</td>
                <td>c</td>
            </tr>
        </table>


        <%--<h1> Test CSS </h1>--%>
        <%--<h2> Test JS</h2>--%>
        <%--<div id="msg"></div>--%>
        <%--<br/>--%>
        <%--<br/>--%>


        <%--<table style="width:100%">--%>
            <%--<tr>--%>
                <%--<th colspan="6">所有帐户</th>--%>
            <%--</tr>--%>
            <%--<tr id="title">--%>
                <%--<td>持有人</td>--%>
                <%--<td>尾号</td>--%>
                <%--<td>银行</td>--%>
                <%--<td>最后还款日</td>--%>
                <%--<td>帐单日</td>--%>
                <%--<td>操作</td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td>老婆</td>--%>
                <%--<td>1234</td>--%>
                <%--<td>招商</td>--%>
                <%--<td>2015-12-14</td>--%>
                <%--<td>2015-12-14</td>--%>
                <%--<td><input type="button" value="编辑">  <input type="button" value="删除"></td>--%>
            <%--</tr>--%>
            <%--<tr class="content">--%>
                <%--<td>老公</td>--%>
                <%--<td>1234</td>--%>
                <%--<td>招商</td>--%>
                <%--<td>2015-12-14</td>--%>
                <%--<td>2015-12-14</td>--%>
                <%--<td><input type="button" value="编辑">  <input type="button" value="删除"></td>--%>
            <%--</tr>--%>
            <%--<tr>--%>
                <%--<td><a href="/accountAdd">增加帐户</a></td>--%>
                <%--<td><input type="button" value="增加帐户"></td>--%>
            <%--</tr>--%>
        <%--</table>--%>
        <%--<br/>--%>
        <%--<br/>--%>
        <%--<div style="width:100%; height: 25px;float:left;">所有帐户</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #FFE4E1">持有人</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #FFE4E1">尾号</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #FFE4E1">银行</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #FFE4E1">最后还款日</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #FFE4E1">帐单日</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #FFE4E1">操作</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;">老婆</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;">1234</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;">招商</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;">2015-12-14</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;">2015-12-14</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;">编辑 删除</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #E6E6FA">老公</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #E6E6FA">1234</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #E6E6FA">广发</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #E6E6FA">2015-12-14</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #E6E6FA">2015-12-14</div>--%>
        <%--<div style="width:16%; height: 25px;float:left;background-color: #E6E6FA">编辑 删除</div>--%>
        <%--<div style="width:100%; height: 25px;float:left;"><a href="/accountAdd">增加帐户</a> <br/></div>--%>
        <%--<div style="clear:both;"></div>--%>
        <br/>


        <a href="/account/display?accountId=1">自动生成一个帐户展示看帐户信息</a> <br/>

    <table class="table">
        <caption>基本的表格布局</caption>
        <thead>
        <tr>
            <th>名称</th>
            <th>城市</th>
        </tr>
        </thead>
        <tbody>
        <tr>
            <td>Tanmay</td>
            <td>Bangalore</td>
        </tr>
        <tr>
            <td>Sachin</td>
            <td>Mumbai</td>
        </tr>
        </tbody>
    </table>

    </body>
</html>