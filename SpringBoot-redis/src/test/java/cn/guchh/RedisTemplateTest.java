package cn.guchh;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;

import javax.annotation.Resource;

@SpringBootTest
@Slf4j
public class RedisTemplateTest {
    @Resource
    private StringRedisTemplate template;

    @Test
    public void StringTest() {
        String v1 = template.opsForValue().get("k1");
        log.info("k1:{}", v1);
        Long k1 = template.opsForValue().increment("k1");
        log.info("k1:{}", k1);

    }

    @Test
    public void TransactionTest() {
        template.execute(new SessionCallback<Object>() {
            @Override
            public <K, V> Object execute(RedisOperations<K, V> operations) throws DataAccessException {
                template.opsForValue().set("k4", "v4");
                template.opsForValue().set("k3", "v3");
                log.info("k4;{}", template.opsForValue().get("k4"));
                log.info("k4;{}", template.opsForValue().get("k3"));
                template.watch("k4");
                template.multi();
                template.opsForValue().set("k3", "v31");
                template.opsForValue().set("k4", "v41");

                log.info("k4;{}", template.opsForValue().get("k4"));
                log.info("k4;{}", template.opsForValue().get("k3"));
                return template.exec();
            }
        });
    }

    @Test
    public void PipelineTest() {
        // 不用管道 23ms
        Jedis jedis = new Jedis("127.0.0.1", 6379);
        long begin = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        for(int i=0;i<100;i++){
            pipeline.set("k" + i,"v"+i);
            pipeline.del("k" + i);
        }
        pipeline.sync();
        long end = System.currentTimeMillis();
        log.info("add time:{}",end-begin);
    }
}
