package com.mjc.school;

import com.mjc.school.menu.Menu;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new AnnotationConfigApplicationContext(ApiConfig.class);
        Menu menu = context.getBean(Menu.class);
        menu.run();

    }
}
