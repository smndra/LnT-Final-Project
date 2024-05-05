package view;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import main.Connect;
import model.Menu;

public class Page {
	BorderPane bp;
	GridPane gp;
	VBox vb1, vb2;
	Label kodeLB, namaLB, hargaLB, stokLB;
	TextField kodeTF, namaTF, hargaTF, stokTF;
	TableView<Menu> tv;
	TableColumn<Menu, String> kodeCol, namaCol;
	TableColumn<Menu, Integer> hargaCol, stokCol;
	Button btn, update, delete;
	ObservableList<Menu> list;
	Connect connect = Connect.getConnection();
	
	public void init() {
		list = FXCollections.observableArrayList();
		bp = new BorderPane();
		gp = new GridPane();
		vb1 = new VBox();
		vb2 = new VBox();
		
		kodeLB = new Label("Kode");
		namaLB = new Label("Nama");
		hargaLB = new Label("Harga");
		stokLB = new Label("Stok");
		
		btn = new Button("Insert");
		update = new Button("Update");
		delete = new Button("Delete");
		
		kodeTF = new TextField();
		namaTF = new TextField();
		hargaTF = new TextField();
		stokTF = new TextField();
		
		tv = new TableView<Menu>();
		kodeCol = new TableColumn<Menu, String>("Kode");
		namaCol = new TableColumn<Menu, String>("Nama");
		hargaCol = new TableColumn<Menu, Integer>("Harga");
		stokCol = new TableColumn<Menu, Integer>("Stok");
		
		getAllMenu();
	}
	
	public void arrange() {
		gp.add(kodeLB, 0, 0);
		gp.add(kodeTF, 1, 0);
		gp.add(namaLB, 0, 1);
		gp.add(namaTF, 1, 1);
		gp.add(hargaLB, 0, 2);
		gp.add(hargaTF, 1, 2);
		gp.add(stokLB, 0, 3);
		gp.add(stokTF, 1, 3);
		gp.setAlignment(Pos.CENTER);
		
		gp.setHgap(15);
		gp.setVgap(15);
		vb1.getChildren().addAll(gp, btn, update, delete);
		vb1.setSpacing(20);
		vb1.setAlignment(Pos.CENTER);
		
		kodeCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("kode"));
		namaCol.setCellValueFactory(new PropertyValueFactory<Menu, String>("nama"));
		hargaCol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("harga"));
		stokCol.setCellValueFactory(new PropertyValueFactory<Menu, Integer>("stok"));

		tv.getColumns().addAll(kodeCol, namaCol, hargaCol, stokCol);
		tv.setItems(list);
		
		tv.prefWidth(800);
		kodeCol.setPrefWidth(200);
		namaCol.setPrefWidth(200);
		hargaCol.setPrefWidth(200);
		stokCol.setPrefWidth(200);
		
		vb2.getChildren().addAll(tv, vb1);
		vb2.setSpacing(15);
		vb2.setAlignment(Pos.CENTER);
		bp.setCenter(vb2);
		bp.setPadding(new Insets(20));
	}
	
	public void eventHandler() {
		btn.setOnAction(e->{
			if(kodeTF.getText().isEmpty() || namaTF.getText().isEmpty() || hargaTF.getText().isEmpty() || stokTF.getText().isEmpty()) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Error!");
				a.setContentText("Kode, nama, harga, dan stok harus diisi!");
				a.show();
			}else if(!kodeTF.getText().startsWith("PD")) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Error!");
				a.setContentText("Kode harus dimulai dengan PD!");
				a.show();
			}else {
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Success!");
				a.setContentText("Berhasil menambahkan menu baru!");
				a.show();
				insert(kodeTF.getText(), namaTF.getText(), Integer.valueOf(hargaTF.getText()), Integer.valueOf(stokTF.getText()));
				list.add(new Menu(kodeTF.getText(), namaTF.getText(), Integer.valueOf(hargaTF.getText()), Integer.valueOf(stokTF.getText())));
				kodeTF.setText("");
				namaTF.setText("");
				hargaTF.setText("");
				stokTF.setText("");
			}
		});
		
		tv.setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event e) {
				// TODO Auto-generated method stub
				if(tv.getSelectionModel().getSelectedItem() != null) {
					kodeTF.setText(tv.getSelectionModel().getSelectedItem().getKode());
					namaTF.setText(tv.getSelectionModel().getSelectedItem().getNama());
					hargaTF.setText(String.valueOf(tv.getSelectionModel().getSelectedItem().getHarga()));
					stokTF.setText(String.valueOf(tv.getSelectionModel().getSelectedItem().getStok()));
				}
			}
		});
		
		update.setOnAction(e->{
			Menu menu1 = new Menu(kodeTF.getText(), namaTF.getText(), Integer.valueOf(hargaTF.getText()), Integer.valueOf(stokTF.getText()));
			if(!(menu1.getKode().equals(kodeTF.getText()) || menu1.getNama().equals(namaTF.getText()))) {
				Alert a = new Alert(AlertType.ERROR);
				a.setHeaderText("Error!");
				a.setContentText("Kode dan nama tidak boleh diubah!\nHarga dan stok harus diisi!");
				a.show();
			}else {
				Menu temp = null;
				for (Menu menu : list) {
					if(menu.getKode().equals(kodeTF.getText()) && menu.getNama().equals(namaTF.getText())) {
						temp = menu;
					}
				}
				Menu temp1 = new Menu(kodeTF.getText(), namaTF.getText(), Integer.valueOf(hargaTF.getText()), Integer.valueOf(stokTF.getText()));
				
				list.set(list.indexOf(temp), temp1);
				
				update(kodeTF.getText(), namaTF.getText(), Integer.valueOf(hargaTF.getText()), Integer.valueOf(stokTF.getText()));
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Success!");
				a.setContentText("Menu berhasil diupdate!");
				a.show();
			}
		});
		
		delete.setOnAction(e->{
			if(!kodeTF.getText().isEmpty()) {
				delete(kodeTF.getText());
				Menu temp = null;
				for (Menu menu : list) {
					if(menu.getKode().equals(kodeTF.getText())) {
						temp = menu;
					}
				}
				list.remove(temp);
				
				Alert a = new Alert(AlertType.INFORMATION);
				a.setHeaderText("Success!");
				a.setContentText("Menu berhasil didelete!");
				a.show();
			}
		});
	}
	
	public void delete(String kode) {
		String query = "DELETE FROM `menu` WHERE `Kode`=?";
		try {
			PreparedStatement ps = connect.prepareStatement(query);
			ps.setString(1, kode);
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void update(String kode, String nama, int harga, int stok) {
		String query = "UPDATE `menu` SET `Harga`=?,`Stok`=? WHERE `Nama`=? AND`Kode`=?";
		try {
			PreparedStatement ps = connect.prepareStatement(query);
			ps.setInt(1, harga);
			ps.setInt(2, stok);
			ps.setString(3, nama);
			ps.setString(4, kode);
			
			ps.execute();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void insert(String kode, String nama, int harga, int stok) {
		String query = "INSERT INTO `menu`(`Kode`, `Nama`, `Harga`, `Stok`) VALUES ('"+kode+"','"+nama+"','"+harga+"','"+stok+"')";
		try {
			connect.execUpdate(query);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public void getAllMenu() {
		String query = "Select * from menu";
		try {
			ResultSet rs = connect.execQuery(query);
			while(rs.next()) {
				Menu menu = new Menu(rs.getString("kode"), rs.getString("nama"), rs.getInt("harga"), rs.getInt("stok"));
				list.add(menu);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	
	public Page() {
		init();
		arrange();
		eventHandler();
	}
	
	public BorderPane getLayout() {
		return this.bp;
	}
}
