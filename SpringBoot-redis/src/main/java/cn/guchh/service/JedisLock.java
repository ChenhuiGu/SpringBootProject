package cn.guchh.service;


import org.apache.commons.lang3.StringUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.params.SetParams;

import java.util.Arrays;
import java.util.Collections;

/**
 * @author chenhuigu
 */
public class JedisLock {
    static final String _LOCKKEY = "videoLock";
    static final String _UUID = "APP01";
    static final long _TIMEOUT = 40;
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //加锁
        boolean lock = lock(jedis, _LOCKKEY, _UUID, _TIMEOUT);
        //业务流程
        if(lock){
            System.out.println("加锁成功");
            //执行业务
        }else {
            System.out.println("加锁失败");
            //TODO 如何操作？
        }
        //释放锁
        boolean unLock = unLock(jedis, _LOCKKEY, _UUID);
        if(unLock){
            System.out.println("释放锁成功");
        }else {
            System.out.println("释放锁失败");
        }


    }

    /**
     *
     * @param jedis
     * @param lockKey
     * @param uuid
     * @param timeOut
     * @return
     */
    public static boolean lock(Jedis jedis,String lockKey,String uuid,long timeOut){
        SetParams params = new SetParams();
        params.ex(timeOut).nx();
        String res = jedis.set(lockKey, uuid, params);
        if(StringUtils.isNotBlank(res)&&res.equals("OK")){
            return true;
        }
        return false;
    }

    /**
     *
     * @param jedis
     * @param lockKey
     * @param uuid
     * @return
     */
    public static boolean unLock(Jedis jedis,String lockKey,String uuid){
        // LUA脚本
        String script = "if redis.call('get', KEYS[1]) == ARGV[1] then return redis.call('del', KEYS[1]) else return 0 end";
        Object result = jedis.eval(script, Collections.singletonList(lockKey), Collections.singletonList(uuid));
        if(result.equals(1L)){
            return true;
        }
        return false;
    }

}
