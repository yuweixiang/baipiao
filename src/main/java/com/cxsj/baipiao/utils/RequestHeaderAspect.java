package com.cxsj.baipiao.utils;

import com.cxsj.baipiao.common.AspectContextHolder;
import com.cxsj.baipiao.common.BaiPiaoContext;
import com.cxsj.baipiao.common.ContextHolder;
import com.cxsj.baipiao.common.TokenContext;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Aspect
@Component
@Slf4j
public class RequestHeaderAspect {

    @Around("execution(* com.cxsj.baipiao.controller.*.*(..))") // 根据实际情况配置切点表达式
    public Object aroundAdvice(ProceedingJoinPoint joinPoint) {

        try {
            HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
            // 获取请求头中的值
            String headerValue = request.getHeader("Authorization");

            AspectContextHolder.clear();
            TokenContext context = new TokenContext();
            context.setToken(headerValue);
            AspectContextHolder.set(context);

            return joinPoint.proceed();

        }catch (Throwable e){
            log.error("系统异常！e",e);
            Result result = new Result();
            result.setResultCode(ResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setResultMsg("服务器出了点小差");
            result.setSuccess(false);
            return result;
        }finally {
            AspectContextHolder.clear();
        }
    }
}
