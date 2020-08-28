package moban;

/**
 * <h3>tensquare_parent</h3>
 * <p></p>
 *
 * @author : lsy
 * @date : 2020-08-26 11:02
 * 模板模式
 **/
public abstract class Moban {
     abstract void MethodFirst();

    abstract void MethodSecond();

     void MethodThird(){
         System.out.println("执行了MethodThird");
     };

    public  void template() {
        MethodFirst();
        MethodSecond();
        MethodThird();
    }
}
