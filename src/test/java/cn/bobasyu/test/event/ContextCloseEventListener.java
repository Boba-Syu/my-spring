package cn.bobasyu.test.event;

import cn.bobasyu.springframework.context.ApplicationListener;
import cn.bobasyu.springframework.context.event.ContextClosedEvent;

public class ContextCloseEventListener implements ApplicationListener<ContextClosedEvent> {
    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        System.out.println("关闭事件：" + this.getClass().getName());
    }
}
