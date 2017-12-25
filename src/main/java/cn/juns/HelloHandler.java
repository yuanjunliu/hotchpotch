package cn.juns;

import cn.juns.invoker.Context;
import cn.juns.invoker.Target;
import cn.juns.exception.ClientException;
import cn.juns.util.StaticResourceUtil;
import cn.juns.util.StringUtil;
import com.alibaba.fastjson.JSONObject;
import org.eclipse.jetty.server.Request;
import org.eclipse.jetty.server.handler.AbstractHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by liuyuanjun on 2017/12/21.
 */
public class HelloHandler extends AbstractHandler {
    private final static String PACKAGE_PREFIX = "cn.juns.action";
    private final static String METHOD_SUFFIX = ".htm";
    private final static String ACTION_SUFFIX = "Action";

    public void handle(String target,
                       Request baseRequest,
                       HttpServletRequest request,
                       HttpServletResponse response) throws IOException,
            ServletException {
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);

        if (StringUtil.isBlank(target) || "/".equals(target)) {
            response.getWriter().println(StaticResourceUtil.welcomeHtml());
        } else if (!target.endsWith(".htm")) {
            StaticResourceUtil.StaticResource staticResource = StaticResourceUtil.getStaticResource(target);
            response.setContentType(staticResource.contentType);
            if (staticResource.raw != null) {
                response.getOutputStream().write(staticResource.raw);
            } else {
                response.getWriter().println(staticResource.content);
            }
        } else {
            PrintWriter out = response.getWriter();
            try {
                // 根据target解析得到要执行的action和方法
                Target action = parseTarget(target);
                // 将baseRequest对象放入当前线程的localMap里面
                Context context = new Context();
                context.setRequest(baseRequest);
                context.set();
                // 执行
                String result = (String) action.getMethod().invoke(action.getInstance());
                JSONObject data = new JSONObject();
                data.put("response", result);
                out.println(data);
            } catch (ClientException ex) {
                out.println("can not parse target [" + target + "];" + ex.getMessage() + "");
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        baseRequest.setHandled(true);
    }

    private Target parseTarget(String target) {
        if (target.startsWith("/")) {
            target = target.substring(1);
        }
        String[] paths = target.split("/");
        int len = paths.length;
        if (len < 2) {
            throw new ClientException();
        }
        ;
        StringBuilder sb = new StringBuilder(PACKAGE_PREFIX);
        for (int i = 0; i < len - 1; i++) {
            if (i == len - 2) {
                sb.append(".").append(StringUtil.upperFirstChar(paths[i]));
            } else {
                sb.append(".").append(paths[i]);
            }
        }
        String actionName = sb.append(ACTION_SUFFIX).toString();
        String methodName = paths[len - 1].replace(METHOD_SUFFIX, "");
        Target action = new Target();
        try {
            Class<?> clazz = Class.forName(actionName);
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
