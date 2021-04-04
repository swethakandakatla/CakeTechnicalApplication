package uk.task.waracle.cakemanagement.rest.datatype;

import org.springframework.lang.NonNull;

public class Cake {

	private Long id;
	@NonNull
	private String cakeName;

	@NonNull
	private String cakeType;
	private long price;

	public Cake(String cakeName, String cakeType, long price) {
		super();
		this.cakeName = cakeName;
		this.cakeType = cakeType;
		this.price = price;
	}

	public Cake() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCakeName() {
		return cakeName;
	}

	public void setCakeName(String cakeName) {
		this.cakeName = cakeName;
	}

	public String getCakeType() {
		return cakeType;
	}

	public void setCakeType(String cakeType) {
		this.cakeType = cakeType;
	}

	public long getPrice() {
		return price;
	}

	public void setPrice(long price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Cake{" +
			"id=" + id +
			", cakeName='" + cakeName + '\'' +
			", cakeType='" + cakeType + '\'' +
			", price=" + price +
			'}';
	}
}
