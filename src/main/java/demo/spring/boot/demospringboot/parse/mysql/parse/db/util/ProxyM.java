package demo.spring.boot.demospringboot.parse.mysql.parse.db.util;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiConsumer;


@Slf4j
class ProxyModel<S> implements InvocationHandler {

    /**
     * 被代理的对象
     */
    private S beProxy;

    public ProxyModel(S beProxy) {
        this.beProxy = beProxy;
    }

    public ProxyModel(S beProxy, BiConsumer<Method, Object[]> biConsumer) {
        this.beProxy = beProxy;
        this.biConsumer = biConsumer;
    }

    private BiConsumer<Method, Object[]> biConsumer;

    /**
     * 被代理的对象的方法都要执行的调用
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        if (null != biConsumer) {
            biConsumer.accept(method, args);//函数式调用
        }
        return method.invoke(beProxy, args);

    }

    /**
     * 获取实例->代理实例
     * 应该是根据classLoader
     * 所需要代理的接口Interfaces
     * 及invoke调用的的处理invocationHandler(ProxyModel<T>)
     * 转为static
     *
     * @param beProxy
     * @return
     */
    public static <T> T getInstance(T beProxy) {
        ProxyModel<T> invocationHandler = new ProxyModel(beProxy);
        return (T) Proxy.newProxyInstance(
                beProxy.getClass().getClassLoader(),
                beProxy.getClass().getInterfaces(), invocationHandler);
    }

    /**
     * 使用模板方法
     *
     * @param beProxy
     * @return
     */
    public static <T> T getInstance(T beProxy, BiConsumer<Method, Object[]> biConsumer) {
        ProxyModel<T> invocationHandler = new ProxyModel(beProxy, biConsumer);
        return (T) Proxy.newProxyInstance(
                beProxy.getClass().getClassLoader(),
                beProxy.getClass().getInterfaces(), invocationHandler);
    }
}

public class ProxyM {

    @Test
    public void test() {
        List<String> list = ProxyModel.getInstance(new ArrayList<>());
        list.add("xx");

    }

    @Test
    public void test2() {
        List<String> list = ProxyModel.getInstance(new ArrayList<>(), ((method, objects) -> {
            Logger logger = LoggerFactory.getLogger(getClass());
            logger.info("args：{}", objects);
            logger.info("method：{}", method);

        }));
        list.add("xx");
        list.size();


    }
}