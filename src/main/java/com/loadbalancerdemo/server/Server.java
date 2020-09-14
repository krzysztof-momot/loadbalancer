package com.loadbalancerdemo.server;

import com.loadbalancerdemo.api.Application;
import com.loadbalancerdemo.api.Request;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Server of an {@link Application}.
 */
@Component
public class Server
{
   private final LoadBalancer loadBalancer;
   private final Replicator replicator;

   private Application currentApplication;

   @Autowired
   public Server(Replicator replicator, LoadBalancer loadBalancer)
   {
      this.replicator = replicator;
      this.loadBalancer = loadBalancer;
   }

   /**
    * Deploy an application.
    * @param application app to deploy
    * @param instanceBuilderFactory factory that produces builder that can build instance of particular app
    */
   public void deploy(Application application, InstanceBuilderFactory instanceBuilderFactory)
   {
      replicator.registerInstanceFactory(application, instanceBuilderFactory);

      if (isCurrentlyDeployed())
      {
         replicator.requestScale(currentApplication, 0);
      }
      this.currentApplication = application;
      replicator.requestScale(currentApplication, 1);
   }

   public void handleRequest(Request request)
   {
      loadBalancer.handleRequest(request);
   }

   private boolean isCurrentlyDeployed()
   {
      return currentApplication != null;
   }
}
