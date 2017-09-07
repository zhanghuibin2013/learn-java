package learn.common.service.impl;

import learn.common.service.HelloService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Service;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-31
 * Time: 下午4:32
 * To change this template use File | Settings | File Templates.
 */
@Service("helloService")
public class HelloServiceImpl implements HelloService, ApplicationContextAware {
    @Override
    public String sayHello(String name) {
        return "Hello," + name + " !";
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(this.getClass() + "," + applicationContext);
    }
}
