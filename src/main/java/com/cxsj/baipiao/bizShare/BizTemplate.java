package com.cxsj.baipiao.bizShare;

import com.cxsj.baipiao.common.BaiPiaoContext;
import com.cxsj.baipiao.common.ContextHolder;
import com.cxsj.baipiao.enums.ResultCodeEnum;
import com.cxsj.baipiao.exception.BizException;
import com.cxsj.baipiao.result.PageResult;
import com.cxsj.baipiao.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
public class BizTemplate {

    @Transactional
    protected void processWithTransation(BaseRequest request, Result result, String actionType,BusinessProcess processCallback){
        Long startTime = System.currentTimeMillis();
        try {
            ContextHolder.clear();
            BaiPiaoContext context = new BaiPiaoContext();
            context.setActionType(actionType);
            ContextHolder.set(context);

            processCallback.doProcess();

            log.error("["+actionType+"]--业务处理完成,耗时:{}",System.currentTimeMillis()-startTime);
        }catch (BizException e){
            log.error("["+actionType+"]--业务处理异常,erroCode={},errorMsg={},request:{}",e.getErrorCode(),e.getMessage(),request,e);
            result.setResultCode(e.getErrorCode());
            result.setResultMsg(e.getMessage());
            result.setSuccess(false);
        }catch (Throwable e){
            log.error("["+actionType+"]--未知异常异常,request:{}",request,e);
            result.setResultCode(ResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setResultMsg(ResultCodeEnum.SYSTEM_ERROR.getDesc());
            result.setSuccess(false);
        }finally {
            ContextHolder.clear();
        }
    }

    @Transactional
    protected void processWithPageTransation(BaseRequest request, PageResult result, String actionType, BusinessProcess processCallback){
        Long startTime = System.currentTimeMillis();
        try {
            ContextHolder.clear();
            BaiPiaoContext context = new BaiPiaoContext();
            context.setActionType(actionType);
            ContextHolder.set(context);

            processCallback.doProcess();

            log.error("["+actionType+"]--业务处理完成,耗时:{}",System.currentTimeMillis()-startTime);
        }catch (BizException e){
            log.error("["+actionType+"]--业务处理异常,erroCode={},errorMsg={},request:{}",e.getErrorCode(),e.getMessage(),request,e);
            result.setResultCode(e.getErrorCode());
            result.setResultMsg(e.getMessage());
            result.setSuccess(false);
        }catch (Throwable e){
            log.error("["+actionType+"]--未知异常异常,request:{}",request,e);
            result.setResultCode(ResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setResultMsg(ResultCodeEnum.SYSTEM_ERROR.getDesc());
            result.setSuccess(false);
        }finally {
            ContextHolder.clear();
        }
    }

    protected void processWithNoTransation(BaseRequest request, Result result, String actionType,BusinessProcess processCallback){
        Long startTime = System.currentTimeMillis();
        try {
            ContextHolder.clear();
            BaiPiaoContext context = new BaiPiaoContext();
            context.setActionType(actionType);
            ContextHolder.set(context);

            processCallback.doProcess();

            log.error("["+actionType+"]--业务处理完成,耗时:{}",System.currentTimeMillis()-startTime);
        }catch (BizException e){
            log.error("["+actionType+"]--业务处理异常,erroCode={},errorMsg={},request:{}",e.getErrorCode(),e.getMessage(),request,e);
            result.setResultCode(e.getErrorCode());
            result.setResultMsg(e.getMessage());
            result.setSuccess(false);
        }catch (Throwable e){
            log.error("["+actionType+"]--未知异常异常,request:{}",request,e);
            result.setResultCode(ResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setResultMsg(ResultCodeEnum.SYSTEM_ERROR.getDesc());
            result.setSuccess(false);
        }finally {
            ContextHolder.clear();
        }
    }

    protected void processPageWithNoTransation(BaseRequest request, PageResult result, String actionType,BusinessProcess processCallback){
        Long startTime = System.currentTimeMillis();
        try {
            ContextHolder.clear();
            BaiPiaoContext context = new BaiPiaoContext();
            context.setActionType(actionType);
            ContextHolder.set(context);

            processCallback.doProcess();

            log.error("["+actionType+"]--业务处理完成,耗时:{}",System.currentTimeMillis()-startTime);
        }catch (BizException e){
            log.error("["+actionType+"]--业务处理异常,erroCode={},errorMsg={},request:{}",e.getErrorCode(),e.getMessage(),request,e);
            result.setResultCode(e.getErrorCode());
            result.setResultMsg(e.getMessage());
            result.setSuccess(false);
        }catch (Throwable e){
            log.error("["+actionType+"]--未知异常异常,request:{}",request,e);
            result.setResultCode(ResultCodeEnum.SYSTEM_ERROR.getCode());
            result.setResultMsg(ResultCodeEnum.SYSTEM_ERROR.getDesc());
            result.setSuccess(false);
        }finally {
            ContextHolder.clear();
        }
    }

    public void buildSuccess(Result result){
        result.setSuccess(true);
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultCode(ResultCodeEnum.SUCCESS.getDesc());
    }

    public void buildSuccess(PageResult result){
        result.setSuccess(true);
        result.setResultCode(ResultCodeEnum.SUCCESS.getCode());
        result.setResultCode(ResultCodeEnum.SUCCESS.getDesc());
    }
}
