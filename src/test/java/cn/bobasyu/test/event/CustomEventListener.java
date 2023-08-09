package cn.bobasyu.test.event;

import cn.bobasyu.springframework.context.ApplicationListener;


public class CustomEventListener implements ApplicationListener<CustomEvent> {
    @Override
    public void onApplicationEvent(CustomEvent event) {
        System.out.println("自定义事件监听器，收到：" + event.getSource() + "消息：" + event.getId() + ":" + event.getMessage());
    }
}
