package com.loadbalancerdemo.app;

import com.loadbalancerdemo.api.Application;
import com.loadbalancerdemo.api.Request;
import org.springframework.stereotype.Component;

@Component
public class MyApplication implements Application
{

   public void handleRequest(Request request)
   {
      System.out.println("Hello, " + request.getPayload());
   }
}
