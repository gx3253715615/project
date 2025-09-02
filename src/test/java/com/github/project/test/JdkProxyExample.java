package com.github.project.test;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author gaoxinyu
 * @date 2025/8/29 13:00
 **/
public class JdkProxyExample {
    public static void main(String[] args) {
// 目标对象
        GreetingService target = new GreetingServiceImpl();

        // 创建 InvocationHandler
        InvocationHandler handler = new GreetingInvocationHandler(target);

        // 创建代理对象
        GreetingService proxy = (GreetingService) Proxy.newProxyInstance(
                target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                handler
        );

        // 使用代理对象
        proxy.sayHello("John");
        proxy.sayGoodbye("John");
    }

}

interface GreetingService {
    void sayHello(String name);
    void sayGoodbye(String name);
}

class GreetingServiceImpl implements GreetingService {
    @Override
    public void sayHello(String name) {
        System.out.println("Hello, " + name);
    }
    @Override
    public void sayGoodbye(String name) {
        System.out.println("Goodbye, " + name);
    }
}

class GreetingInvocationHandler implements InvocationHandler {
    private Object target;
    public GreetingInvocationHandler(Object target) {
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 判断不同的方法名，执行不同的代理逻辑
        if (method.getName().equals("sayHello")) {
            System.out.println("Before calling sayHello");
            // 执行 sayHello 方法
            Object result = method.invoke(target, args);
            System.out.println("After calling sayHello");
            return result;
        } else if (method.getName().equals("sayGoodbye")) {
            System.out.println("Before calling sayGoodbye");
            // 执行 sayGoodbye 方法
            Object result = method.invoke(target, args);
            System.out.println("After calling sayGoodbye");
            return result;
        }
        return null;
    }
}
