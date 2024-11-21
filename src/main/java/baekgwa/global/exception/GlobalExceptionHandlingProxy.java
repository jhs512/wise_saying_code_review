package baekgwa.global.exception;

import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class GlobalExceptionHandlingProxy implements InvocationHandler {

    private final Object target;

    public GlobalExceptionHandlingProxy(Object target) {
        this.target = target;
    }

    @SuppressWarnings("unchecked")
    public static <T> T createProxy(T target,
            Class<T> classType) {
        return (T) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                new Class<?>[]{classType},
                new GlobalExceptionHandlingProxy(target)
        );
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            return method.invoke(this.target, args);
        } catch (InvocationTargetException e) {
            Throwable cause = e.getCause();
            if(cause instanceof IOException) {
                System.out.println("연결 중 오류가 발생하였습니다. message = " + cause.getMessage());
                System.exit(1);
            } else if(cause instanceof CustomException) {
                System.out.println(cause.getMessage());
            } else {
                System.out.println("기타 오류가 발생하였습니다. message = " + cause.getMessage());
                System.exit(1);
            }
        } catch (Exception e) {
            e.getCause();
            System.out.println("처리되지 않은 Exception 발생" + e.getMessage());
            System.exit(1);
        }
        return null;
    }
}
