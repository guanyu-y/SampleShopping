package la.bean;
import java.io.Serializable;
import java.text.NumberFormat;
import java.util.Locale;

public class ItemBean implements Serializable{
	private int code;
	private String name;
	private int price;
	private int quantity;
	private String url;
	private String currency;
	@ SuppressWarnings("unused")
	private String subTotalCurrency;


	public ItemBean(int code, String name, int price, String url) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.url = url;
	}
	public ItemBean(int code, String name, int price, int quantity) {
		this.code = code;
		this.name = name;
		this.price = price;
		this.quantity = quantity;
	}
	public ItemBean(int price, String currency) {
		this.price = price;
		this.currency = currency;
	}
	public ItemBean() {
	}

	public int getCode() {
		return code;
	}
	public void setCode(int code) {
		this.code = code;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getQuantity() {
		return quantity;
	}
	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
		public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public String getSubTotalCurrency() {
		NumberFormat nf = NumberFormat.getCurrencyInstance(Locale.JAPAN);
		int subTotal = this.price*this.quantity;
		return nf.format(subTotal);
	}
	public void setSubTotalCurrency() {
		this.subTotalCurrency = this.getSubTotalCurrency();
	}

}
