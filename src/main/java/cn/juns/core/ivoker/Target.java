package cn.juns.core.ivoker;

import java.lang.reflect.Method;

/**
 * Created by 水信玄饼 on 2017/12/22.
 */
public class Target<T> {
    private Class<?> action;
    private Method method;
    private Object obj;

    public Class<?> getAction() {
        return action;
    }

    public void setAction(Class<?> action) {
        this.action = action;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public Object getObj() {
        return obj;
    }

    public void setObj(Object obj) {
        this.obj = obj;
    }
}
