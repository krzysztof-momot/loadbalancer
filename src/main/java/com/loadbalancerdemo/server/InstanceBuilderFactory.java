package com.loadbalancerdemo.server;

/**
 * Factory for {@link Instance.Builder}.
 */
public abstract class InstanceBuilderFactory
{
   public abstract Instance.Builder newInstanceBuilder();
}
