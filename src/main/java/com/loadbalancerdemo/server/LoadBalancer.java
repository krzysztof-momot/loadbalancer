package com.loadbalancerdemo.server;

import java.util.ArrayList;
import java.util.List;

import com.loadbalancerdemo.api.Request;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

/**
 * Part of {@link Server} that handles {@link Request} passed to it and passes it to {@link
 * com.loadbalancerdemo.api.Application} {@link Instance}s according to {@link LoadBalacingAlgorithm}.
 */
@Component
public class LoadBalancer
{
   private final List<Instance> appInstances;
   private LoadBalacingAlgorithm loadBalancingAlgorithm;

   public LoadBalancer(List<Instance> appInstances,
         @Qualifier("roundRobin") LoadBalacingAlgorithm algorithm)
   {
      this.appInstances = new ArrayList<>(appInstances);
      this.loadBalancingAlgorithm = algorithm;
   }

   public void handleRequest(Request request)
   {
      loadBalancingAlgorithm.apply(request, appInstances);
   }

   public void addInstance(Instance instance)
   {
      appInstances.add(instance);
   }

   public List<Instance> getInstances()
   {
      return appInstances;
   }

   public void setLoadBalancingAlgorithm(LoadBalacingAlgorithm algorithm)
   {
      this.loadBalancingAlgorithm = algorithm;
   }
}
