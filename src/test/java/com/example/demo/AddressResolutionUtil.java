package com.example.demo;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Rhy
 * @title java从地址串中解析提取省市区-完美匹配中国所有地址|自动解析地址
 * @description java从地址串中解析提取省市区-完美匹配中国所有地址|自动解析地址
 * @createTime 2021年01月05日 15:20:00
 * @modifier：Rhy
 * @modification_time：2021-01-05 15:20
 */
public class AddressResolutionUtil {

    /**
     * 解析地址
     * @author lin
     * @param address
     * @return
     */
    public static List<Map<String,String>> addressResolution(String address){
        String regex1="(?<province>[^省]+自治区|.*?省|.*?行政区|.*?市)(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇|.+乡)?(?<village>.*)";
        Matcher m= Pattern.compile(regex1).matcher(address);
        String province=null,city=null,county=null,town=null,village=null;
        List<Map<String,String>> table=new ArrayList<Map<String,String>>();
        Map<String,String> row=null;
        while(m.find()){
            row=new LinkedHashMap<String,String>();
            province=m.group("province");
            row.put("province", province==null?"":province.trim());
            city=m.group("city");
            row.put("city", city==null?"":city.trim());
            county=m.group("county");
            row.put("county", county==null?"":county.trim());
            town=m.group("town");
            row.put("town", town==null?"":town.trim());
            village=m.group("village");
            row.put("village", village==null?"":village.trim());
            table.add(row);
        }
        if(table.isEmpty()){
            String regex2="(?<city>[^市]+自治州|.*?地区|.*?行政单位|.+盟|市辖区|.*?市|.*?县)(?<county>[^县]+县|.+区|.+市|.+旗|.+海域|.+岛)?(?<town>[^区]+区|.+镇|.+乡)?(?<village>.*)";
            m = Pattern.compile(regex2).matcher(address);
            table=new ArrayList<Map<String,String>>();
            row=null;
            while(m.find()){
                row=new LinkedHashMap<String,String>();
                city=m.group("city");
                row.put("city", city==null?"":city.trim());
                county=m.group("county");
                row.put("county", county==null?"":county.trim());
                town=m.group("town");
                row.put("town", town==null?"":town.trim());
                village=m.group("village");
                row.put("village", village==null?"":village.trim());
                table.add(row);
            }
        }

        return table;
    }

    public static void main(String[] args) {
        System.out.println(addressResolution("内蒙古自治区通辽市奈曼旗"));
    }

}
