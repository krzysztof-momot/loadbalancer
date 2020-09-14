package com.loadbalancerdemo.app;

import com.loadbalancerdemo.server.Instance;
import com.loadbalancerdemo.server.InstanceBuilderFactory;

/**
 * Factory that produces {@link MyApplication} instance builder.
 */
public class MyApplicationInstanceBuilderFactory extends InstanceBuilderFactory
{
   @Override
   public Instance.Builder newInstanceBuilder()
   {
      return new Instance.Builder(new MyApplication());
   }
}
