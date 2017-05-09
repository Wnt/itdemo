package com.example.itdemo;

public class UiPresenter {

	private UiView view;

	public UiPresenter(UiView myUI) {
		this.view = myUI;
	}

	public void buttonClicked(String value) {
		String content = "Thanks " + value + ", it works!";
		view.addLabel("test breaking change");
		
	}

}
