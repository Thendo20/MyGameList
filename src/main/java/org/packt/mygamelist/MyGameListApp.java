package org.packt.mygamelist;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages ="org.packt.mygamelist")
public class MyGameListApp {

    public static void main(String[] args) {
        SpringApplication.run(MyGameListApp.class, args);
    }

}
