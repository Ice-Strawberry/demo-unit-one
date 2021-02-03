package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import  com.google.gson.Gson ;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootTest
class DemoApplicationTests {

    @Test
    void contextLoads() {

    }
    public static Map<String,Object> getAddress(String addr){
        Map<String,Object> map = new HashMap();
        String city =null;
        String province = null;
        String district=null;
        String are = null;
        String regex1="(?<province>[^自治区]+自治区|[^省]+省|[^市]+市)";
        Matcher m1 = Pattern.compile(regex1).matcher(addr);
        while (m1.find()){
            province = m1.group("province");
            if(province != null){
                addr=addr.replaceFirst(province,"");
                break;
            }
        }
        String regex2="(?<city>[^辖区]+辖区|[^盟]+盟|[^自治州]+自治州|[^地区]+地区|[^市]+市|.+区划)";
        Matcher m2 = Pattern.compile(regex2).matcher(addr);
        while (m2.find()){
            city = m2.group("city");
            if (city != null){
                addr=addr.replaceFirst(city,"");
                break;
            }
        }
        String regex3="(?<district>[^市]+市|[^县]+县|[^旗]+旗|.+区)";
        Matcher m3 = Pattern.compile(regex3).matcher(addr);
        while (m3.find()){
            district = m3.group("district");
            if (district != null){
                addr=addr.replaceFirst(district,"");
                break;
            }
        }
        map.put("province",province);
        map.put("city",city);
        map.put("district",district);
        map.put("address",addr);
        return map;
    }
    public static void main(String[] args) {
        System.out.println(getAddress("内蒙古通辽市奈曼旗"));
    }

}
