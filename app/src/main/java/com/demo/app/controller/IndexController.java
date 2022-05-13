package com.demo.app.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 类功能描述:　IndexController
 *
 * @author Eternal
 * @date 2019-11-26 11:14
 */
@Controller
@Api(value = "IndexController",tags = "首页跳转")
public class IndexController {

    @GetMapping(value = {"/default/**","/","/login"})
    public void index(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("/index.html").forward(request,response);
    }

}
