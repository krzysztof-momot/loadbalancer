package com.loadbalancerdemo.server;

import java.util.List;

import com.loadbalancerdemo.api.Request;
import org.springframework.stereotype.Component;

/**
 * {@link LoadBalacingAlgorithm} that walks through list of instances sequentially passing request to the next
 * instance.
 */
@Component
class RoundRobin implements LoadBalacingAlgorithm
{
   private int cursor = 0;

   @Override
   public void apply(Request request, List<Instance> instances)
   {
      Instance instance = instances.get(cursor);
      cursor++;
      instance.handleRequest(request);
      if (cursor == instances.size())
      {
         cursor = 0;
      }
   }
}
