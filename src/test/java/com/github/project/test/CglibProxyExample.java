package com.github.project.test;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author gaoxinyu
 * @date 2025/8/29 13:08
 **/
public class CglibProxyExample {
    public static void main(String[] args) {
        // 创建目标对象
        GreetingService2 target = new GreetingService2();

        // 创建 Enhancer 对象
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(GreetingService2.class); // 设置父类，即目标类
        enhancer.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
                // 代理逻辑：方法调用前
                System.out.println("Before method call: " + method.getName());

                // 执行目标方法
                Object result = proxy.invokeSuper(obj, args);

                // 代理逻辑：方法调用后
                System.out.println("After method call: " + method.getName());

                return result;
            }
        });

        // 创建代理对象
        GreetingService2 proxy = (GreetingService2) enhancer.create();

        // 使用代理对象
        proxy.sayHello("John");
        System.out.println();
        proxy.sayGoodbye("John");
    }
}

class GreetingService2 {
    public void sayHello(String name) {
        System.out.println("Hello, " + name);
    }

    public void sayGoodbye(String name) {
        System.out.println("Goodbye, " + name);
    }
}
