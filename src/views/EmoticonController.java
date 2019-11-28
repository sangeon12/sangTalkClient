package views;

import main.MainApp;

public class EmoticonController {
	MainController m;
	public void initialize() {
		m = (MainController)MainApp.app.getController("main");
	}
	public void e1() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😃");
	}
	public void e2() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😁");
	}
	public void e3() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😆");
	}
	public void e4() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😤");
	}
	public void e5() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😝");
	}
	public void e6() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😍");
	}
	public void e7() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😵");
	}
	public void e8() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😱");
	}
	public void e9() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😠");
	}
	public void e10() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😖");
	}
	public void e11() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😲");
	}
	public void e12() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😐");
	}
	public void e13() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😷");
	}
	public void e14() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"😇");
	}
	public void e15() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"👿");
	}
	public void e16() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"💩");
	}
	public void e17() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"💀");
	}
	public void e18() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"👾");
	}
	public void e19() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"👽");
	}
	public void e20() {
		String a = m.talkField.getText();
		m.talkField.setText(a+"🖕");
	}
}
