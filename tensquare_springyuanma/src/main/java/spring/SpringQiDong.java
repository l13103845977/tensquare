package spring;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * <h3>tensquare</h3>
 * <p></p>
 *
 * @author : lsy
 * @date : 2020-09-02 10:15
 **/
public class SpringQiDong {
    public static void main(String[] args) {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("application.xml");
        MessageInteface messageInteface  = (MessageImpl)applicationContext.getBean("message");
    messageInteface.message();
    }
}
