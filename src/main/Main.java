package main;

import view.Page;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application{

	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage stage) throws Exception {
		// TODO Auto-generated method stub
		Page p = new Page();
		Scene sc = new Scene(p.getLayout());
		stage.setScene(sc);
		stage.show();
	}

}
