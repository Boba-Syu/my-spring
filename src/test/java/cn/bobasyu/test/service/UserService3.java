package cn.bobasyu.test.service;

import java.util.Random;

public class UserService3 implements IUserService {
    public String queryUserInfo() {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello world";
    }

    public String register(String name) {
        try {
            Thread.sleep(new Random(1).nextInt(100));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "Hello, " + name;
    }

}
