package com.loadbalancerdemo.api;

/**
 * Simple request to an application.
 */
public class Request
{
   private String payload;

   public String getPayload()
   {
      return payload;
   }

   public void setPayload(String payload)
   {
      this.payload = payload;
   }
}
