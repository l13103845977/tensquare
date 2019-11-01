import com.tensquare.spit.pojo.Spit;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Component;

@Component
public class text {

   @Autowired
    SpitDao spitDao;
    @Test
    public  void  aa(){

        Spit spit=new Spit();
        ExampleMatcher matcher = ExampleMatcher.matching() //构建对象
                .withIncludeNullValues() //改变“Null值处理方式”：包括
                .withIgnorePaths("_id","content","publishtime","userid","nickname","visits","thumbup","share","comment","state");
        Example<Spit> example=Example.of(spit,matcher);
        System.out.println(spitDao.findAll(example).size());
    }
}
