package com.loadbalancerdemo.server;

import static org.mockito.Mockito.times;

import java.util.List;

import com.loadbalancerdemo.api.Application;
import com.loadbalancerdemo.api.Request;
import com.loadbalancerdemo.app.MyApplication;
import com.loadbalancerdemo.app.MyApplicationInstanceBuilderFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
class LoadbalancerdemoApplicationTests {

	@MockBean(name = "instance1")
	private Instance instance1;

	@MockBean(name = "instance2")
	private Instance instance2;

	@MockBean(name = "instance3")
	private Instance instance3;

	private Request request;
	private Server server;
	private LoadBalancer loadBalancer;

	@Test
	void contextLoads() {
	}

	@BeforeEach
	public void init()
	{
		List<Instance> myAppInstances = List.of(instance1, instance2, instance3);
		Application myApp = new MyApplication();
		InstanceBuilderFactory myAppInstanceBuilderFactory = new MyApplicationInstanceBuilderFactory();
		request = new Request();

		loadBalancer = new LoadBalancer(myAppInstances, new RoundRobin());
		server = new Server(new Replicator(loadBalancer), loadBalancer);
		server.deploy(myApp, myAppInstanceBuilderFactory);
	}

	@Test
	void testRoundRobin()
	{
		Mockito.when(instance1.getLoad()).thenReturn(0.80f);
		Mockito.when(instance2.getLoad()).thenReturn(0.85f);
		Mockito.when(instance3.getLoad()).thenReturn(0.90f);

		server.handleRequest(request);
		server.handleRequest(request);
		server.handleRequest(request);
		server.handleRequest(request);

		Mockito.verify(instance1, times(2)).handleRequest(request);
		Mockito.verify(instance2, times(1)).handleRequest(request);
		Mockito.verify(instance3, times(1)).handleRequest(request);

	}

	@Test
	void testLoadThresholdBasedSelectLowestLoad()
	{
		loadBalancer.setLoadBalancingAlgorithm(new LoadThresholdBased());

		Mockito.when(instance1.getLoad()).thenReturn(0.80f);
		Mockito.when(instance2.getLoad()).thenReturn(0.85f);
		Mockito.when(instance3.getLoad()).thenReturn(0.90f);

		server.handleRequest(request);
		server.handleRequest(request);
		server.handleRequest(request);
		server.handleRequest(request);

		Mockito.verify(instance1, times(4)).handleRequest(request);
	}

	@Test
	void testLoadThresholdBasedSelectLoadUnderThreshold()
	{
		loadBalancer.setLoadBalancingAlgorithm(new LoadThresholdBased());

		Mockito.when(instance1.getLoad()).thenReturn(0.80f);
		Mockito.when(instance2.getLoad()).thenReturn(0.85f);
		Mockito.when(instance3.getLoad()).thenReturn(0.20f);

		server.handleRequest(request);
		server.handleRequest(request);
		server.handleRequest(request);
		server.handleRequest(request);

		Mockito.verify(instance3, times(4)).handleRequest(request);
	}
}
