package com.mmall.common;

import com.mmall.exception.ParamException;
import com.mmall.exception.PermissionException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/***
 * 全局异常处理类
 * HandlerExceptionResolver 专门处理异常的接口
 * 需要配置 让spring管理此bean    把此bean配制在springmvc 配制文件中
 */
@Slf4j
public class SpringExceptionResolver implements HandlerExceptionResolver {


    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String url = request.getRequestURL().toString();
        ModelAndView mv;
        String defaultMsg = "System error";
        /**
         * 分别对数据请求 和 页面请求做异常处理
         *  1.json请求 .json结尾
         *  2.页面请求, .page结尾
         */
        // 这里我们要求项目中所有请求json数据，都使用.json结尾
        /**
         * public ModelAndView(String viewName, Map<String, ?> model)
         为了保证异常返回结果 和 正常返回结果保持一致
         jsonView   在springmvc的配置文件中 MappingJackson2JsonView
         */
        if (url.endsWith(".json")) {
            //自定义异常
            if (ex instanceof PermissionException || ex instanceof ParamException) {
                JsonData result = JsonData.fail(ex.getMessage());

                mv = new ModelAndView("jsonView", result.toMap());
            } else {
                //不是自定义异常
                log.error("unknown json exception, url:" + url, ex);
                JsonData result = JsonData.fail(defaultMsg);
                mv = new ModelAndView("jsonView", result.toMap());
            }
        } else if (url.endsWith(".page")){ // 这里我们要求项目中所有请求page页面，都使用.page结尾
            log.error("unknown page exception, url:" + url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            // 去寻找exception的jsp
            mv = new ModelAndView("exception", result.toMap());
        } else {
            log.error("unknow exception, url:" + url, ex);
            JsonData result = JsonData.fail(defaultMsg);
            mv = new ModelAndView("jsonView", result.toMap());
        }

        return mv;
    }
}
