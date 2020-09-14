package com.loadbalancerdemo.server;

import com.loadbalancerdemo.api.Application;
import com.loadbalancerdemo.api.Request;
import org.springframework.stereotype.Component;

/**
 * Instance of an {@link Application}.
 */
@Component
public class Instance
{
   private static final int MAX_CAPACITY = 10;
   private final Application application;
   private int capacity;

   public Instance(Application application)
   {
      this.application = application;
   }

   float getLoad()
   {
      return (float) (MAX_CAPACITY - capacity) / 10;
   }

   void handleRequest(Request request)
   {
      application.handleRequest(request);
   }

   public static class Builder
   {
      private final Instance instance;

      public Builder(Application application)
      {
         this.instance = new Instance(application);
      }

      public Builder withCapacity(int capacity)
      {
         instance.capacity = capacity;
         return this;
      }

      public Instance getInstance()
      {
         return instance;
      }
   }

}
