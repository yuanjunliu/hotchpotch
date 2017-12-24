package cn.juns.invoker;

import java.lang.reflect.Method;

/**
 * Created by 水信玄饼 on 2017/12/22.
 */
public class Target {
    private Class<?> action;
    private Method method;

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

    public Object getInstance() throws IllegalAccessException, InstantiationException {
        return action.newInstance();
    }

}
