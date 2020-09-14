package com.loadbalancerdemo.server;

import java.util.List;

import com.loadbalancerdemo.api.Request;

/**
 * Algorithm used by {@link LoadBalancer} to do its job.
 */
interface LoadBalacingAlgorithm
{
   /**
    * Apply request to list of instances according to algoritm implementation.
    *
    * @param request   request to handle
    * @param instances instances that should handle request
    */
   void apply(Request request, List<Instance> instances);

}
