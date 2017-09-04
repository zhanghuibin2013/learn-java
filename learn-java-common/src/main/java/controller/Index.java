package controller;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import service.HelloService;

/**
 * Created with IntelliJ IDEA.
 * User: zhanghuibin
 * Date: 17-8-31
 * Time: 下午3:59
 * To change this template use File | Settings | File Templates.
 */
@Controller
@RequestMapping("")
public class Index implements ApplicationContextAware {

    @Autowired
    private HelloService helloService;

    @RequestMapping("")
    @ResponseBody
    public String welcome(String name) {
        if(name==null || name.equals("")){
            name="none";
        }
        return helloService.sayHello(name);
    }
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println(this.getClass() + "," + applicationContext);
    }
}
