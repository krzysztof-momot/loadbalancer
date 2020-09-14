package com.loadbalancerdemo.server;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import com.loadbalancerdemo.api.Application;
import org.springframework.stereotype.Component;

/**
 * Replicator of an {@link Application}. Part of {@link Server} used to manage {@link Instance} number.
 */
@Component
public class Replicator
{
   private final LoadBalancer loadBalancer;
   private final Map<Application, InstanceBuilderFactory> applicationInstanceFactoryMap = new HashMap<>();

   public Replicator(LoadBalancer loadBalancer)
   {
      this.loadBalancer = loadBalancer;
   }

   void requestScale(Application application, int replicas)
   {
      List<Instance> instances = loadBalancer.getInstances();
      int numberOfInstances = instances.size();
      if (numberOfInstances < replicas)
      {
         InstanceBuilderFactory instanceBuilderFactory = applicationInstanceFactoryMap.get(application);
         for (int i = numberOfInstances; i < replicas; i++)
         {
            Instance instance = instanceBuilderFactory.newInstanceBuilder()
                  .withCapacity(ThreadLocalRandom.current().nextInt(1, 10))
                  .getInstance();
            loadBalancer.addInstance(instance);
         }
      }
      else
      {
         instances.removeAll(instances.subList(0, replicas - 1));
      }
   }

   void registerInstanceFactory(Application application, InstanceBuilderFactory instanceBuilderFactory)
   {
      applicationInstanceFactoryMap.putIfAbsent(application, instanceBuilderFactory);
   }
}
