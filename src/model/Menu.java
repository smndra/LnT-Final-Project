package model;

public class Menu {
	private String kode;
	private String nama;
	private Integer harga;
	private Integer stok;
	public Menu(String kode, String nama, Integer harga, Integer stok) {
		super();
		this.kode = kode;
		this.nama = nama;
		this.harga = harga;
		this.stok = stok;
	}
	public String getKode() {
		return kode;
	}
	public void setKode(String kode) {
		this.kode = kode;
	}
	public String getNama() {
		return nama;
	}
	public void setNama(String nama) {
		this.nama = nama;
	}
	public Integer getHarga() {
		return harga;
	}
	public void setHarga(Integer harga) {
		this.harga = harga;
	}
	public Integer getStok() {
		return stok;
	}
	public void setStok(Integer stok) {
		this.stok = stok;
	}
	
}
