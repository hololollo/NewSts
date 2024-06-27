package com.kh.ajax.model.vo;

public class Menu {
	private int menuNumber;
	private String menuName;
	private int price;
	private String material;
	
	public Menu() {
		super();
	};
	public Menu(int menuNumber, String menuName, int price, String material) {
		super();
		this.menuNumber = menuNumber;
		this.menuName = menuName;
		this.price = price;
		this.material = material;
	}
	public int getMenuNumber() {
		return menuNumber;
	}
	public void setMenuNumber(int menuNumber) {
		this.menuNumber = menuNumber;
	}
	public String getMenuName() {
		return menuName;
	}
	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public String getMaterial() {
		return material;
	}
	public void setMaterial(String material) {
		this.material = material;
	}
	@Override
	public String toString() {
		return "Menu [menuNumber=" + menuNumber + ", menuName=" + menuName + ", price=" + price + ", material="
				+ material + "]";
	}
	
	
	
	
}
