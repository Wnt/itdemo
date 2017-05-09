package com.example.itdemo;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class SimpleTest {
	private boolean labelAdded;
	private String labelStr;
	@Before
    public void setUp() {
    }
    
    @Test
    public void testUserIsGreeted() {
    	labelAdded = false;
    	UiPresenter presenter = new UiPresenter(new UiView() {


			@Override
			public void addLabel(String content) {
				labelStr = content;
				labelAdded = true;
			}
		});
    	presenter.buttonClicked("foobar");
    	Assert.assertTrue("addLabel should have been called", labelAdded);
    	Assert.assertTrue("label content should contain foobar", labelStr.contains("foobar"));
    }
}
