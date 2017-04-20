package com.example.tests;

import com.thoughtworks.selenium.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;
import java.util.regex.Pattern;

public class test1 {
	private Selenium selenium;

	@Before
	public void setUp() throws Exception {
		selenium = new DefaultSelenium("localhost", 4444, "*chrome", "https://www.tutu.ru/");
		selenium.start();
	}

	@Test
	public void testTest1() throws Exception {
		selenium.open("/");
		selenium.click("//div[@class='l-page_wrapper']/div[4]/div/div[1]/div[2]");
		selenium.click("//div[@class='l-page_wrapper']/div[4]/div/div[2]/div[2]");
		selenium.click("//div[@class='l-page_wrapper']/div[4]/div/div[3]/div[2]");
	}

	@After
	public void tearDown() throws Exception {
		selenium.stop();
	}
}
