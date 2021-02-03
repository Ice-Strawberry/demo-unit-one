package com.example.demo;

import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @program: demo-unit-one
 * @description:
 * @author: Wyy
 * @create_time: 2020-12-25 10:40
 * @modifier：Wyy
 * @modification_time：2020-12-25 10:40
 **/
@SpringBootTest
public class DemoTest {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void testRedis() {
        String key = "hello";
        redisTemplate.opsForValue().set("hello", "你好");

        String res = (String) redisTemplate.opsForValue().get(key);
        System.out.println(res);
    }

    @Test
    public void testKeyOps() {
        // 测试redis操作key-value形式
        Set<String> keySet = new HashSet<>();

        String key1 = "name";
        keySet.add(key1);
        // 存储简单的key-value，并设置过期时间
        redisTemplate.opsForValue().set(key1, "eknown", 1, TimeUnit.MINUTES);

        String key2 = "token:user1";
        String key3 = "token:user2";
        keySet.add(key2);
        keySet.add(key3);
        //
        redisTemplate.opsForValue().set(key2, "{\"name\":\"eknown\"}, \"role\":\"admin\"");
        redisTemplate.opsForValue().set(key3, "{\"name\":\"test\"}, \"role\":\"test\"");

        // 根据key的集合获取多个value
        List<String> valueList = redisTemplate.opsForValue().multiGet(keySet);
        for (String value : valueList) {
            System.out.println(value);
        }
    }

    @Test
    public void testListOps() {
        String listKey = "list";
        redisTemplate.opsForList().leftPush(listKey, "first value"); // 从list最左边插入数据
        redisTemplate.opsForList().leftPush(listKey, "second value but left");
        redisTemplate.opsForList().rightPush(listKey, 3); // 从list最右边插入数据

        List<Object> list = new ArrayList<>();
        list.add("hello");
        list.add("http://www.eknown.cn");
        list.add(23344);
        list.add(false);
        redisTemplate.opsForList().rightPushAll(listKey, list); // 从list右边批量插入数据

        long size = redisTemplate.opsForList().size(listKey);
        if (size > 0) {
            for (int i = 0; i < size - 1; i++) {
                // 从list最左边开始读取list中的数据，注意pop会导致出栈，也就是数据被取出来了（redis中就没有这个值了）
                // 此处我们读取size-1条数据，仅留下最后一条数据
                System.out.println(i + ":" + redisTemplate.opsForList().leftPop(listKey).toString());
            }
        }
    }

    @Test
    public void testHashOps() {
        String key = "hash";
        // 单次往hash中存放一个数据
        redisTemplate.opsForHash().put(key, "1", "你好");

        Map<String, Object> map = new HashMap<>();
        map.put("2", "hello");
        map.put("3a", "china1=2");
        map.put("4", "hello2");
        map.put("5", "china1=22");
        // 一次性向hash中存放一个map
        redisTemplate.opsForHash().putAll(key, map);

        // 获取hash下的所有key和value
        Map<String, Object> resultMap = redisTemplate.opsForHash().entries(key);
        for (String hashKey : resultMap.keySet()) {
            System.out.println(hashKey + ": " + resultMap.get(hashKey));
        }
    }


    @Test
    public void terr() {
        String s = "2|3|10|~难受~";
        List<String> li = new ArrayList<>(1);
        if (StringUtils.isBlank(s) || StringUtils.equals("~", s)) {
            System.out.println(li);
        }
        String[] split = s.split("\\|");
        String[] split2 = s.split("\\~");
        System.out.println(split2[1]);
        for (int i = 0; i < split.length; i++) {
            int a = split[i].indexOf("~");
            if (0 == a) {
                if (split2.length >2) {
                    split[i] = split2[1];
                }
                split[i] = "";
            }

            li.add(split[i]);
        }
        System.out.println("hhh" + li);
    }
    @Test
    public void terr1() {
        String str = "2|3|10|~难受~难受";
        List<String> li = new ArrayList<>(1);
        String[] split = str.split("\\|");
        for (int i = 0; i < split.length; i++) {
            String s = split[i];
            if (split[i].contains("~")){
                s = split[i].replaceFirst("~", "");
            }
            li.add(s);
        }
        System.out.println("tttt"+li);
    }


}
