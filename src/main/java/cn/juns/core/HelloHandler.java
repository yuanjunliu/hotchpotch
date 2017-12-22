package cn.juns.core;

import cn.juns.core.ivoker.Context;
import cn.juns.core.ivoker.Target;
import cn.juns.exception.ClientException;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;
import org.eclipse.jetty.util.StringUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class HelloHandler extends AbstractHandler {
    private final static String PACKAGE_PREFIX = "cn.juns.core";
    private final static String METHOD_SUFFIX = ".htm";
    final String greeting;
    final String body;

    public HelloHandler() {
        this("Hello World");
    }

    public HelloHandler(String greeting) {
        this(greeting, null);
    }

    public HelloHandler(String greeting, String body) {
        this.greeting = greeting;
        this.body = body;
    }

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        // 根据target解析得到要执行的action和方法
        Target action = parseTarget(target);
        // 将baseRequest对象放入当前线程的localMap里面
        Context context = new Context();
        context.setRequest(baseRequest);
        context.set(context);
        // 执行
        try {
            action.getMethod().invoke(action.getObj());
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }

        response.setContentType("text/html; charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        PrintWriter out = response.getWriter();

        out.println("<h1>" + greeting + "</h1>");
        if (body != null) {
            out.println(body);
        }

        baseRequest.setHandled(true);
    }

    private Target parseTarget(String target) {
        if (StringUtil.isBlank(target)) return null;
        String[] paths = target.split("/");
        int len = paths.length;
        if (len < 2) return null;
        StringBuilder sb = new StringBuilder(PACKAGE_PREFIX);
        for (int i = 0; i < len - 1; i++) {
            sb.append(".").append(paths[i]);
        }
        String methodName = paths[len - 1].replace(METHOD_SUFFIX, "");
        Target action = new Target();
        try {
            Class<?> clazz = Class.forName(sb.toString());
            action.setAction(clazz);
            action.setMethod(clazz.getMethod(methodName));
            return action;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new ClientException("找不到类" + sb.toString());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
            throw new ClientException("找不到方法" + methodName);
        }
    }
}
