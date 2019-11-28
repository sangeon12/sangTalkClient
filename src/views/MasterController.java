package views;

import javafx.scene.layout.Pane;

public abstract class MasterController {
	private Pane root; //가장 기본이 되는 팬

	public Pane getRoot() {
		return root;
	}

	public void setRoot(Pane root) {
		this.root = root;
	}
	
	public abstract void reset();
}
