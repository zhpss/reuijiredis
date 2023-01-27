package com.zhp.sggruijiredis;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.omg.CORBA.PUBLIC_MEMBER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.data.redis.connection.DataType;
import org.springframework.data.redis.core.*;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
/*import redis.clients.jedis.Jedis;*/

@SpringBootTest
@RunWith(SpringRunner.class)
class SggruijiredisApplicationTests {

  /*  @Test
    void contextLoads() {
        //1.获取连接
        Jedis jedis = new Jedis("192.168.0.101",6379,100000);
        jedis.auth("123456");
        //2 执行具体操作
        jedis.set("username","小明");
        String username = jedis.get("username");
        System.out.println(username);
        jedis.hset("zhp","height","180");
        String hget = jedis.hget("zhp", "height");
        System.out.println(hget);
        //3关闭连接
        jedis.close();
    }*/
    @Autowired
    private RedisTemplate redisTemplate;
    /*
    * 操作Sstring类型数据
    *
    * */
    @Test
    public void testString(){

        ValueOperations valueOperations = redisTemplate.opsForValue();

        valueOperations.set("xiaohong","hp");
        Object xiaohong = valueOperations.get("xiaohong");
        System.out.println(xiaohong);
        valueOperations.set("key1","value1",101, TimeUnit.SECONDS);
        Boolean xiaohong1 = valueOperations.setIfAbsent("xiaohong", "123");
        System.out.println(xiaohong1);


    }
    /*
    * 操作Hash类型数据
    *
    * */
    @Test
    public void testHash(){
        HashOperations hashOperations = redisTemplate.opsForHash();

        //存值
        hashOperations.put("002","name","xiaoming");
        hashOperations.put("002","age","20");
        hashOperations.put("002","address","bj");

        //取值
        Object age = hashOperations.get("002", "age");
        System.out.println(age);
        //获得hash结构中的所有字段
        Set keys = hashOperations.keys("002");
        for (Object key : keys) {
            System.out.println(key);
        }
        //获得hash结构中的所有值
        List values = hashOperations.values("002");
        for (Object value : values) {
            System.out.println(value);

        }
    }
    /*
    * 操作Set类型的数据
    *
    * */
    @Test
    public void testSet(){
        SetOperations setOperations = redisTemplate.opsForSet();
        //存值
        setOperations.add("myset","a","b","c","a");


        //取值
        Set<String> myset = setOperations.members("myset");

        for (Object o : myset) {
            System.out.println(o);
        }
        //删除成员
        setOperations.remove("myset","a","b");

        //取值
        myset = setOperations.members("myset");
        for (String s : myset) {
            System.out.println(s);
        }
    }
    /*
    * 操作ZSet类型的数据
    *
    * */
    @Test
    public void testZset(){
        ZSetOperations zSetOperations = redisTemplate.opsForZSet();
        //存值
        zSetOperations.add("myZset","a",10.0);
        zSetOperations.add("myZset","b",10.0);
        zSetOperations.add("myZset","c",10.0);
        zSetOperations.add("myZset","a",10.0);

        //取值
        Set<String> myZset = zSetOperations.range("myZset",0,-1);
        for (String s : myZset) {
            System.out.println(s);
        }
        //修改分数
        zSetOperations.incrementScore("myZset","b",20.0);
        //取值
        myZset = zSetOperations.range("myZset",0,-1);
        for (String s : myZset) {
            System.out.println(s);
        }

        //删除成员
        zSetOperations.remove("myZset","a","b");

        //取值
        myZset = zSetOperations.range("myZset",0,-1);
        for (String s : myZset) {
            System.out.println(s);
        }



    }
    /*
     * 通用操作，针对不同的数据类型都可以操作
     *
     * */
    @Test
    public void testCommon(){
        //获取Redis中所有的key
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            System.out.println(key);
        }
        //判断某个key是否存在
        Boolean itcast = redisTemplate.hasKey("itcast");
        System.out.println(itcast);

        //删除指定key
        redisTemplate.delete("myZset");
        //获取指定key对应的value的数据类型
        DataType dataType = redisTemplate.type("myset");
        System.out.println(dataType.name());
    }
}
