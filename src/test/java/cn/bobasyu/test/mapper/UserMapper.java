package cn.bobasyu.test.mapper;

import cn.bobasyu.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class UserMapper {
    private static Map<String, String> hashMap = new HashMap<>();

    static {
        hashMap.put("1", "AAA");
        hashMap.put("2", "BBB");
        hashMap.put("3", "CCC");
    }

    public String queryUserName(String uId) {
        return hashMap.get(uId);
    }
}
