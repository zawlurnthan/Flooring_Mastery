package com.zthan;

import com.zthan.controller.FlooringController;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class App {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext app = new AnnotationConfigApplicationContext();
        app.scan("com.zthan");
        app.refresh();

        FlooringController controller = app.getBean("flooringController", FlooringController.class);
        controller.run();
    }
}
