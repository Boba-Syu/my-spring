package cn.bobasyu.test.bean;

public class User2 {
    private String msg;

    public User2(String msg) {
        this.msg = msg;
    }

    public void hello() {
        System.out.println(this.msg);
    }
}
