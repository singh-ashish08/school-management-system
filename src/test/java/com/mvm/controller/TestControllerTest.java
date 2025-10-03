package com.mvm.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(TestController.class)
public class TestControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testSayHello() throws Exception {
		mockMvc.perform(get("/api/test/hello")).andExpect(status().isOk())
				.andExpect(MockMvcResultMatchers.content().string("Hello, welcome to Maharishi Vidya Mandir School "));
		System.out.println("Test for /api/test/hello passed.");
	}
}
