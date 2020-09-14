package com.loadbalancerdemo.server;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import com.loadbalancerdemo.api.Request;
import org.springframework.stereotype.Component;

/**
 * {@link LoadBalacingAlgorithm} that has following strategy: it will either take the first instance that has a load
 * under 0.75 or if all instances in the list are above 0.75, it will take the one with the lowest load.
 */
@Component
class LoadThresholdBased implements LoadBalacingAlgorithm
{

   @Override
   public void apply(Request request, List<Instance> instances)
   {
      Instance selectedInstance = instances.stream()
            .filter(instance -> instance.getLoad() < 0.75)
            .findFirst()
            .orElse(getLowestLoad(instances).orElseThrow());

      selectedInstance.handleRequest(request);
   }

   private Optional<Instance> getLowestLoad(List<Instance> instances)
   {
      return instances.stream()
            .min(Comparator.comparing(Instance::getLoad));
   }
}
