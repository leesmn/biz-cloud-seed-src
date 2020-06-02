import com.gov.security.ServerGatewayApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ServerGatewayApplication.class)
public class Test {

    @Autowired
    private RedisTemplate<String,Object> redisTemplate;


    @org.junit.Test
    public void test(){
        redisTemplate.opsForValue().set("abc",123);
        System.err.println("aasdjjalskdjl");
    }
}
