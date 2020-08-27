package com.alibaba.cloud.examples;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.BlockExceptionHandler;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class CustomBlockExceptionHandler implements BlockExceptionHandler {
    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, BlockException e) throws Exception {
        httpServletResponse.setHeader("Content-Type", "application/json;charset=UTF-8");
        String msg = "{\"code\":999,\"msg\": \"访问过于频繁。\"}";
        httpServletResponse.getWriter().write(msg);
    }
}
