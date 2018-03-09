package com.example.mySpring.controller;

import com.example.mySpring.model.User;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.*;

@Controller
public class IndexController {
    @RequestMapping(path = {"/"})
    @ResponseBody
    public String simpleIndex() {
        return "this is index, plus /index on url to see full pages";
    }

    @RequestMapping(path = {"/index"}, method = RequestMethod.GET)
    public String fullIndex() {
        return "index";
    }


    @RequestMapping(path = {"/question/{page}"})
    @ResponseBody
    public String page(@PathVariable("page") int page) {
        return "test PathVariable. question page" + page;
    }

    @RequestMapping(path = {"/submit"})
    @ResponseBody
    public String form(@RequestParam("username") String username,
                       @RequestParam(value = "age", defaultValue = "18", required = false) int age) {
        return "username:" + username + " age:" + age;
    }

    @RequestMapping("/home")
    public String useFreeMarker(Model model) {
        model.addAttribute("value", 10);

        List<String> directions = Arrays.asList(new String[] {"north", "south", "west", "east"});
        model.addAttribute("directions", directions);

        Map<String, String> map = new HashMap<>();
        for(int i=0;i<5;i++) {
            map.put(String.valueOf(i), String.valueOf(i*i));
        }
        model.addAttribute("map", map);

        User user = new User("Jhon", 20);
        model.addAttribute("user", user);

        return "home";
    }

    @RequestMapping("/request")
    @ResponseBody
    public String request(Model model, HttpServletResponse response,
                          HttpServletRequest request, HttpSession session,
                          @CookieValue("JSESSIONID") String sessionId) { //把网页请求里面的response request session包装成了对象
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append(sessionId + "<br>");

        Enumeration<String> headerNames = request.getHeaderNames();
        while(headerNames.hasMoreElements()) {
            String name = headerNames.nextElement();
            stringBuilder.append(name + ":" + request.getHeader(name) + "<br>");
        }

        if(request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                stringBuilder.append("name:" + cookie.getName() + " value:" + cookie.getValue());
            }
        }

        stringBuilder.append(request.getMethod() + "<br>");
        stringBuilder.append(request.getQueryString() + "<br>");
        stringBuilder.append(request.getPathInfo() + "<br>");
        stringBuilder.append(request.getRequestURI() + "<br>");

        //这样http请求里面的Response Header里面就会有相应的字段
        response.addHeader("nowcoderId", "hello");
        //这样加进去的cookie就会出现在response cookie中
        response.addCookie(new Cookie("username", "nowcoder"));

        return stringBuilder.toString();
    }

    @RequestMapping(path = {"/redirect/{code}"}, method = RequestMethod.GET)
    public RedirectView redirect(@PathVariable("code") int code,
                                 HttpSession httpSession) {
        httpSession.setAttribute("msg", " jump from redirect");

        RedirectView redirectView = new RedirectView("/", true);
        if(code == 301) {
            //301强制性跳转
            redirectView.setStatusCode(HttpStatus.MOVED_PERMANENTLY);
        }
        return redirectView;
    }

    @RequestMapping(path = {"/admin"})
    @ResponseBody
    public String admin(@RequestParam("key") String key) {
        if("admin".equals(key)) {
            return "hello, admin";
        } else {
            throw new IllegalArgumentException("参数不对");
        }
    }

    /**
     * 自定义一个ExceptionHandler 如果系统出现一些spring没有处理的异常的话就跳到这里来
     * @param e
     * @return
     */
    @ExceptionHandler()
    @ResponseBody
    public String error(Exception e) {
        return "ERROR:" + e.getMessage();
    }
}
