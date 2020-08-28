import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.xml.XmlBeanDefinitionReader;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import java.io.IOException;
import java.io.InputStream;

/**
 * <h3>tensquare_parent</h3>
 * <p></p>
 *
 * @author : lsy
 * @date : 2020-08-19 10:55
 **/
public class Qidong {
    public static void main(String[] args) throws IOException {
        ApplicationContext applicationContext=new ClassPathXmlApplicationContext("classpath:application.xml");

        System.out.println("spring启动成功");
        MessageService messageService= (MessageService) applicationContext.getBean("messageService");
        Resource classPathResource=new ClassPathResource("messageService");
           InputStream inputStream= classPathResource.getInputStream();
           new XmlBeanFactory(classPathResource);
        System.out.println(messageService.getMessaget());
        ClassPathResource classPathResource1=new ClassPathResource("application.xml");
        DefaultListableBeanFactory defaultListableBeanFactory=new DefaultListableBeanFactory();
        XmlBeanDefinitionReader xmlBeanDefinitionReader=new XmlBeanDefinitionReader(defaultListableBeanFactory);
   xmlBeanDefinitionReader.loadBeanDefinitions(classPathResource1);
    }
}
