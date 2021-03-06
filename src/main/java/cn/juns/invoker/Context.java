package cn.juns.invoker;

import cn.juns.exception.ClientException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.util.StringUtil;

import java.beans.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by 水信玄饼 on 2017/12/22.
 */
public class Context {
    private final static ThreadLocal<Context> threadLocal = new ThreadLocal();
    private Request request;

    public static Context get() {
        return threadLocal.get();
    }

    public void set() {
        threadLocal.set(this);
    }

    public String getParam(String name) {
        return request.getParameter(name);
    }

    public String getRequiredParam(String name) {
        String value = request.getParameter(name);
        if (StringUtil.isBlank(value)) {
            throw new ClientException("参数" + name + "不能为空");
        }
        return value.trim();
    }

    public int getParamInt(String name) {
        return Integer.parseInt(request.getParameter(name));
    }

    public long getParamLong(String name) {
        return Long.parseLong(request.getParameter(name));
    }

    public <T> T form(Class<T> clazz) {
        try {
            T t = clazz.newInstance();
            BeanInfo beanInfo = Introspector.getBeanInfo(clazz);
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
            for (int i = 0; i < propertyDescriptors.length; i++) {
                PropertyDescriptor prop = propertyDescriptors[i];
                if ("class".equals(prop.getName())) continue;
                String requestValue = getParam(prop.getName());
                if (StringUtil.isNotBlank(requestValue)) {
                    Method writeMethod = prop.getWriteMethod();
                    if (writeMethod != null) {
                        writeMethod.invoke(t, requestValue);
                    }
                }
            }
            return t;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IntrospectionException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    public String getPostData() {
        return null;
    }

    public Request getRequest() {
        return request;
    }

    public void setRequest(Request request) {
        this.request = request;
    }
}
