package cn.bobasyu.springframework.aop.aspectj;

import cn.bobasyu.springframework.aop.Pointcut;
import cn.bobasyu.springframework.aop.PointcutAdvisor;
import org.aopalliance.aop.Advice;

/**
 * 包装了连接点，拦截器和拦截表达式，用于配置对应的切面拦截器PointAdvisor
 */
public class AspectJExpressionPointcutAdvisor implements PointcutAdvisor {
    /**
     * 切面
     */
    private AspectJExpressionPointcut pointcut;
    /**
     * 具体拦截器
     */
    private Advice advice;
    /**
     * 表达式
     */
    private String expression;


    @Override
    public Advice getAdvice() {
        return advice;
    }

    @Override
    public Pointcut getPointcut() {
        if(this.pointcut == null) {
            this.pointcut = new AspectJExpressionPointcut(expression);
        }
        return this.pointcut;
    }

    public void setAdvice(Advice advice) {
        this.advice = advice;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
