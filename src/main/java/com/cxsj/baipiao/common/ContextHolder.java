package com.cxsj.baipiao.common;

public class ContextHolder {

    private static ThreadLocal<BaiPiaoContext> contextLocal = new ThreadLocal<BaiPiaoContext>();

    private static BaiPiaoContext get(){
        return contextLocal.get() == null ? new BaiPiaoContext() : contextLocal.get();
    }

    public static void set(BaiPiaoContext context){
        contextLocal.set(context);
    }

    public static void clear(){
        contextLocal.set(null);
    }
}
