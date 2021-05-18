package com.example.routes;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * A bean that returns a message when you call the {@link #saySomething()} method.
 * <p/>
 * Uses <tt>@Component("myBean")</tt> to register this bean with the name <tt>myBean</tt>
 * that we use in the Camel route to lookup this bean.
 */
@Component("myBean2")
public class MySpringBean2 {

    @Value("${greeting}")
    private String say;

    public String saySomething() {
        return "the time is : " + LocalDate.now();
    }

}
