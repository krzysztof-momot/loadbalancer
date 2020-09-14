package com.loadbalancerdemo.api;

/**
 * An application than can be deployed on {@link com.loadbalancerdemo.server.Server}
 */
public interface Application
{
   void handleRequest(Request request);

}
