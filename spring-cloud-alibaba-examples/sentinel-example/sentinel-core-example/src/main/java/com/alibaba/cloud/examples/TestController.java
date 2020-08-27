/*
 * Copyright 2013-2018 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.alibaba.cloud.examples;

import com.alibaba.csp.sentinel.annotation.SentinelResource;

import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author xiaojing
 */
@RestController
public class TestController {

	@Autowired
	private RestTemplate restTemplate;

	@GetMapping("/hello")
	// blockHandler 是位于当前类下的 handleResourceException方法，需符合对应的类型限制.
	@SentinelResource(value = "resource", blockHandler = "handleResourceException")
	public String hello() {
		return "Hello";
	}

	@GetMapping("/hello/{name}")
	@SentinelResource(value = "hellouser", blockHandler = "handleHelloException", blockHandlerClass = ExceptionUtil.class)
	public String hello(@PathVariable String name) {
		return "Hello " + name;
	}

	@GetMapping("/aa")
	@SentinelResource("aa")
	public String aa(int b, int a) {
		return "Hello test";
	}

	@GetMapping("/test")
	public String test1() {
		return "Hello test";
	}

	@GetMapping("/template")
	public String client() {
		return restTemplate.getForObject("http://www.taobao.com/test", String.class);
	}


	public String handleResourceException(BlockException ex) {
		ex.printStackTrace();
		return "HandleResourceException, error occurred.";
	}


}
