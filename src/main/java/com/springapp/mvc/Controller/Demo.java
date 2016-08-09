package com.springapp.mvc.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by Administrator on 2016/1/3.
 */

public class Demo extends HttpServlet {
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        /**
         * 1.获得客户机信息
         */
        String requestUrl = request.getRequestURL().toString();//得到请求的URL地址
        String requestUri = request.getRequestURI();//得到请求的资源

        response.setCharacterEncoding("UTF-8");//设置将字符以"UTF-8"编码输出到客户端浏览器
        //通过设置响应头控制浏览器以UTF-8的编码显示数据，如果不加这句话，那么浏览器显示的将是乱码
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        out.write("获取到的客户机信息如下：");
        out.write("<hr/>");
        out.write("请求的URL地址：" + requestUrl);
        out.write("<br/>");
        out.write("请求的资源：" + requestUri);
        out.write("<br/>");
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        doGet(request, response);
    }
}
