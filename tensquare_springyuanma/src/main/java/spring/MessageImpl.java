package spring;

import javax.xml.transform.Source;

/**
 * <h3>tensquare</h3>
 * <p></p>
 *
 * @author : lsy
 * @date : 2020-09-02 10:17
 **/
public class MessageImpl implements MessageInteface {
    @Override
    public void message() {
        System.out.println("hello world");
    }
}
