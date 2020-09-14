package com.loadbalancerdemo;

import com.loadbalancerdemo.api.Request;
import com.loadbalancerdemo.app.MyApplication;
import com.loadbalancerdemo.app.MyApplicationInstanceBuilderFactory;
import com.loadbalancerdemo.server.Server;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LoadbalancerdemoApplication
{

   public static void main(String[] args)
   {
      SpringApplication.run(LoadbalancerdemoApplication.class, args);
   }

   @Bean
   public CommandLineRunner runServer(Server server)
   {
      return arg -> {
         server.deploy(new MyApplication(), new MyApplicationInstanceBuilderFactory());

         Request sayHelloToMeRequest = new Request();
         sayHelloToMeRequest.setPayload("Krzysiek");

         server.handleRequest(sayHelloToMeRequest);
      };
   }

}
