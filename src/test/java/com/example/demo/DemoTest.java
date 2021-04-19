package com.example.demo;

import com.example.demo.entity.SysUser;
import com.example.demo.entity.User;
import com.example.demo.service.Formula;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.xb.xsdschema.Public;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Math.sqrt;
import static java.util.stream.Collectors.toList;

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

    /**
     * @Author: Wyy
     * @Date: 2021/2/4 16:35
     * @Description: 功能描述 测试Excel导入库里
     * @Param: []
     * @Return: void
     */
    @Test
    public void testExcelInMysql() throws IOException {
        //通过流读取Excel文件
        FileInputStream fileInputStream = new FileInputStream("D:\\file\\公卫公分初始化.xls");
        //2.通过poi解析流 HSSFWorkbook 处理流得到的对象中 就封装了Excel文件所有的数据
        HSSFWorkbook workbook = new HSSFWorkbook(fileInputStream);
        //获取表格中的sheet页数量
        int numberOfSheets = workbook.getNumberOfSheets();
        //FORMULA类型值读取器
        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        for (int i = 0; i < numberOfSheets; i++) {
            //3.从文件中获取表对象  getSheetAt通过下标获取
            HSSFSheet sheetAt = workbook.getSheetAt(i);
            //4.从表中获取到行数据  从第二行开始 到 最后一行  getLastRowNum() 获取最后一行的下标
            int lastRowNum = sheetAt.getLastRowNum();
            for (int j = 0; j <= lastRowNum; j++) {
                //通过下标获取行
                HSSFRow row = sheetAt.getRow(j);
                //获得行中的列总数
                short lastCellNum = row.getLastCellNum();
                //固定模板
//                XSSFCell cell = row.getCell(0);
//                String stringCellValue = cell.getStringCellValue();
                //             从行中获取数据         
                //    /**             * getNumericCellValue() 获取数字             * getStringCellValue 获取String             */    
//                double id=row.getCell(0).getNumericCellValue();
//                String name=row.getCell(1).getStringCellValue();
//                String sex=  String.valueOf(Double.valueOf(row.getCell(2).getNumericCellValue()).intValue());
//                double age=row.getCell(3).getNumericCellValue();
                double q = 0.0d;
                String w = null;
                String e = null;
                double r = 0.0d;
                double t = 0.0d;
                String y = null;
                String u = null;
                double a = 0.0d;
                double o = 0.0d;
                double p = 0.0d;

                if (row.getCell(0) != null) {
                    q = row.getCell(0).getNumericCellValue();
                }
                if (row.getCell(1) != null) {
                    w = row.getCell(1).getStringCellValue();
                }
                if (row.getCell(2) != null) {
                    e = row.getCell(2).getStringCellValue();
                }
                if (row.getCell(3) != null) {
                    r = row.getCell(3).getNumericCellValue();
                }
                if (row.getCell(4) != null) {
                    t = row.getCell(4).getNumericCellValue();
                }
                if (row.getCell(5) != null) {
                    y = row.getCell(5).getStringCellValue();
                }
                if (row.getCell(6) != null) {
                    u = row.getCell(6).getStringCellValue();
                }
                if (row.getCell(7) != null) {
                    a = row.getCell(7).getNumericCellValue();
                }
                if (row.getCell(8) != null) {
                    o = row.getCell(8).getNumericCellValue();
                }
                if (row.getCell(9) != null) {
                    p = row.getCell(9).getNumericCellValue();
                }


//                String p=row.getCell(1).getStringCellValue();
//                String s=  String.valueOf(Double.valueOf(row.getCell(2).getNumericCellValue()).intValue());
//                double d=row.getCell(3).getNumericCellValue();
//                //             封装到对象中       
//                User user=new User();
//                user.setId((int) id);
//                user.setName(name);
//                user.setSex(sex);
//                user.setAge((int)age);
                //             将对象添加数据库  
                System.out.println(i + "--" + j + "--" + q + " ??? " + w + " ??? " + e + " ??? " + r + " ??? " + t + " ??? " + y + " ??? " + u + " ??? " + a + " ??? " + o + " ??? " + p + " ??? ");
            }
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
                if (split2.length > 2) {
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
            if (split[i].contains("~")) {
                s = split[i].replaceFirst("~", "");
            }
            li.add(s);
        }
        System.out.println("tttt" + li);
    }


    @Test
    public void IntegerExcemple() {
        Integer i1 = 40;
        Integer i2 = 40;
        Integer i3 = 0;
        Integer i4 = new Integer(40);
        Integer i5 = new Integer(40);
        Integer i6 = new Integer(0);

        System.out.println("i1=i2   " + (i1 == i2));
        System.out.println("i1=i2+i3   " + (i1 == i2 + i3));
        System.out.println("i1=i4   " + (i1 == i4));
        System.out.println("i4=i5   " + (i4 == i5));
        System.out.println("i4=i5+i6   " + (i4 == i5 + i6));
        System.out.println("40=i5+i6   " + (40 == i5 + i6));

    }

    @Test
    public void BigDecimalExample() {
        BigDecimal b1 = new BigDecimal("11");
        BigDecimal b2 = new BigDecimal("35");

        System.out.println(b1.add(b2));
        System.out.println(b1.subtract(b2));
        System.out.println(b1.multiply(b2));
        //结果：6.1755。除法保留有效位，不然容易报错
        //ROUND_HALF_UP:四舍五入(一般采用这个)
        System.out.println(b1.divide(b2, 4, BigDecimal.ROUND_HALF_UP));
        //ROUND_HALF_DOWN:四舍五入，如果结果小数位只比保留位多一位且最后一位为5则不进位
        System.out.println(b1.divide(b2, 4, BigDecimal.ROUND_HALF_DOWN));
        //ROUND_UP:始终加一
        System.out.println(b1.divide(b2, 4, BigDecimal.ROUND_UP));
        //ROUND_DOWN:直接截取
        System.out.println((b1.divide(b2, 4, BigDecimal.ROUND_DOWN)).multiply(new BigDecimal(100)).setScale(2, BigDecimal.ROUND_HALF_UP));

        System.out.println((b1.divide(b2, 2, BigDecimal.ROUND_DOWN)));

    }

    @Test
    public void StringTolistTransformation() {
        List<String> li = new ArrayList<>(1);
        String s = "2,4,6";
        if (StringUtils.isBlank(s) || StringUtils.equals("~", s)) {
            System.out.println(li);
            ;
        }
        String[] split = s.split("\\,");

        for (int i = 0; i < split.length; i++) {
            li.add(split[i]);
        }
        System.out.println(li);
//            return li;
    }

    @Test
    public void javaTest() {
//        // 通过匿名内部类方式访问接口
//        Formula formula = new Formula() {
//            @Override
//            public double calculate(int a) {
//                return sqrt(a * 100);
//            }
//        };
//
//        System.out.println(formula.calculate(100));     // 100.0
//        System.out.println(formula.sqrt(16));           // 4.0

    }

    public static void main(String[] args) {
        // 通过匿名内部类方式访问接口
        Formula formula = new Formula() {
            @Override
            public double calculate(int a) {
                return sqrt(a * 100);
            }
        };

        System.out.println(formula.calculate(100));     // 100.0
        System.out.println(formula.sqrt(16));           // 4.0
    }

    @Test
    public void javaTestList() {
        List<SysUser> list1 = new ArrayList<>();
        list1.add(new SysUser().setUserId(1L));
        list1.add(new SysUser().setUserId(2L));
        list1.add(new SysUser().setUserId(3L));
        list1.add(new SysUser().setUserId(4L));
        list1.add(new SysUser().setUserId(5L));

        List<SysUser> list2 = new ArrayList<>();
        list2.add(new SysUser().setUserId(1L));
        list2.add(new SysUser().setUserId(6L));
        //第一种
        List<SysUser> collect = list1.stream()
                //开始过滤
                .filter(sysUser -> {
                    //返回true则保留这条记录，返回false剔除
                    for (SysUser user : list2) {
                        if (sysUser.getUserId().equals(user.getUserId())) {
                            return false;
                        }
                    }
                    return true;
                })
                //创建一个新的集合List
                .collect(toList());
        //输出一下看看
        collect.stream().forEach(sysUser -> {
            System.out.println(sysUser.getUserId());
        });
        System.out.println("-----------------华丽的分割线-------------");
        //第二种
//        List<SysUser> collect1 = list1.stream().filter(sysUser -> {
//            return list2.stream().anyMatch(sysUser1 -> {
//                return !(sysUser1.getUserId().equals(sysUser.getUserId()));
//            });
//        }).collect(toList());
//        //输出一下看看
//        collect1.stream().forEach(sysUser -> {
//            System.out.println(sysUser.getUserId());
//        });
        List<SysUser> collect1 = list1.stream().filter(sysUser -> {
            return list2.stream().allMatch(sysUser1 -> {
                return !(sysUser1.getUserId().equals(sysUser.getUserId()));
            });
        }).collect(Collectors.toList());
//输出一下看看
        collect1.stream().forEach(sysUser -> {
            System.out.println(sysUser.getUserId());
        });
    }

    /**
     * 测试俩集合取交集并集差集
     */
    @Test
    public void testList() {
        List<String> list1 = new ArrayList<String>();
        list1.add("1");
        list1.add("2");
        list1.add("3");
        list1.add("5");
        list1.add("6");

        List<String> list2 = new ArrayList<String>();
        list2.add("2");
        list2.add("3");
//        list2.add("7");
//        list2.add("8");

        // 交集
        List<String> intersection = list1.stream().filter(item -> list2.contains(item)).collect(toList());
        System.out.println("---交集 intersection---");
        intersection.parallelStream().forEach(System.out::println);

        // 差集 (list1 - list2)
        List<String> reduce1 = list1.stream().filter(item -> !list2.contains(item)).collect(toList());
        System.out.println("---差集 reduce1 (list1 - list2)---");
        reduce1.parallelStream().forEach(System.out::println);

        // 差集 (list2 - list1)
        List<String> reduce2 = list2.stream().filter(item -> !list1.contains(item)).collect(toList());
        System.out.println("---差集 reduce2 (list2 - list1)---");
        reduce2.parallelStream().forEach(System.out::println);

        // 并集
        List<String> listAll = list1.parallelStream().collect(toList());
        List<String> listAll2 = list2.parallelStream().collect(toList());
        listAll.addAll(listAll2);
        System.out.println("---并集 listAll---");
        listAll.parallelStream().forEachOrdered(System.out::println);

        // 去重并集
        List<String> listAllDistinct = listAll.stream().distinct().collect(toList());
        System.out.println("---得到去重并集 listAllDistinct---");
        listAllDistinct.parallelStream().forEachOrdered(System.out::println);

        System.out.println("---原来的List1---");
        list1.parallelStream().forEachOrdered(System.out::println);
        System.out.println("---原来的List2---");
        list2.parallelStream().forEachOrdered(System.out::println);
    }

    @Test
    public void tesety() {
        List<String> stringList = new ArrayList<>();
        stringList.add("ddd2");
        stringList.add("aaa2");
        stringList.add("bbb1");
        stringList.add("aaa1");
        stringList.add("bbb3");
        stringList.add("ccc");
        stringList.add("bbb2");
        stringList.add("ddd1");
        // 测试 Map 操作
        stringList
                .stream()
                .map(String::toUpperCase)
                .sorted((a, b) -> b.compareTo(a))
                .forEach(new Consumer<String>() {
                    @Override
                    public void accept(String s) {
                        System.out.println(s);
                    }
                });
//                .forEach(System.out::println);// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
        // 测试 Map 操作
//        stringList
//                .stream()
//                .map(String::toUpperCase)
//                .sorted((a, b) -> b.compareTo(a))
//                .sorted(new Comparator<String>() {
//                    @Override
//                    public int compare(String o1, String o2) {
//                        return 0;
//                    }
//                })
//                .forEach(System.out::println);// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
//        stringList
//                .stream()
//                .map(String::toUpperCase)
////                .sorted((a, b) -> b.compareTo(a))
//                .forEach(System.out::println);// "DDD2", "DDD1", "CCC", "BBB3", "BBB2", "AAA2", "AAA1"
//

        //测试 Reduce (规约)操作
        Optional<String> reduced =
                stringList
                        .stream()
                        .sorted()
                        .reduce((s1, s2) -> s1 + "#" + s2);
        Optional<String> reduced1 =
                stringList
                        .stream()
                        .sorted()
                        .reduce(new BinaryOperator<String>() {
                            @Override
                            public String apply(String s, String s2) {
                                return (s + "=" + s2);

                            }
                        });
        String reduced11 =
                stringList
                        .stream()
                        .sorted((a, b) -> b.compareTo(a))
                        .reduce(" ", String::concat);
        System.out.println(reduced11);
        System.out.println("============================================");
        reduced.ifPresent(System.out::println);//aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2
        reduced1.ifPresent(System.out::println);//aaa1#aaa2#bbb1#bbb2#bbb3#ccc#ddd1#ddd2
        //===============================================================================
        // 字符串连接，concat = "ABCD"
        String concat = Stream.of("A", "B", "C", "D").reduce("", String::concat);
// 求最小值，minValue = -3.0
        double minValue = Stream.of(-1.5, 1.0, -3.0, -2.0).reduce(Double.MAX_VALUE, Double::min);
// 求和，sumValue = 10, 有起始值
        int sumValue = Stream.of(1, 2, 3, 4).reduce(0, Integer::sum);
// 求和，sumValue = 10, 无起始值
        sumValue = Stream.of(1, 2, 3, 4).reduce(Integer::sum).get();
// 过滤，字符串连接，concat = "ace"   过滤掉大写字母
        concat = Stream.of("a", "B", "c", "D", "e", "F").
                filter(x -> x.compareTo("Z") > 0).
                reduce("", String::concat);
    }

    @Test
    public void testStream(){
        //并行流
        int max = 1000000;
        List<String> values = new ArrayList<>(max);
        for (int i = 0; i < max; i++) {
            UUID uuid = UUID.randomUUID();
            values.add(uuid.toString());
        }
        //串行排序
//        long t0 = System.nanoTime();
//        //集合数量
//        long count = values.stream().sorted().count();
//        System.out.println(count);
//
//        long t1 = System.nanoTime();
//
//        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
//        System.out.println(String.format("sequential sort took: %d ms", millis));
//并行排序
        long t0 = System.nanoTime();

        long count = values.parallelStream().sorted().count();
        System.out.println(count);

        long t1 = System.nanoTime();

        long millis = TimeUnit.NANOSECONDS.toMillis(t1 - t0);
        System.out.println(String.format("parallel sort took: %d ms", millis));
            //上面两个代码几乎是一样的，但是并行版的快了 50% 左右，唯一需要做的改动就是将 stream() 改为parallelStream()。
    }
    @Test
    public  void maps(){
        Map<Integer, String> map = new HashMap<>();

        for (int i = 0; i < 10; i++) {
            map.putIfAbsent(i, "val" + i);
        }
        map.forEach(new BiConsumer<Integer, String>() {
            @Override
            public void accept(Integer integer, String s) {
                System.out.println(s);
            }
        });
//        map.forEach((id, val) -> System.out.println(val));//val0 val1 val2 val3 val4 val5 val6 val7 val8 val9
//putIfAbsent 阻止我们在null检查时写入额外的代码;forEach接受一个 consumer 来对 map 中的每个元素操作。
        /**
         * put在放入数据时，如果放入数据的key已经存在与Map中，最后放入的数据会覆盖之前存在的数据，
         * 而putIfAbsent在放入数据时，如果存在重复的key，那么putIfAbsent不会放入值。
         */
        map.computeIfPresent(3, (num, val) -> val + num);
        map.get(3);             // val33

        map.computeIfPresent(9, (num, val) -> null);
        map.containsKey(9);     // false

        map.computeIfAbsent(23, num -> "val" + num);
        map.containsKey(23);    // true

        map.computeIfAbsent(3, num -> "bam");
        map.get(3);             // val33

    }
}
