package com.cxsj.baipiao.common;

public class AspectContextHolder {

    private static ThreadLocal<TokenContext> contextLocal = new ThreadLocal<TokenContext>();

    public static TokenContext get(){
        return contextLocal.get() == null ? new TokenContext() : contextLocal.get();
    }

    public static void set(TokenContext context){
        contextLocal.set(context);
    }

    public static void clear(){
        contextLocal.set(null);
    }
}
