package uk.task.waracle.cakemanagement.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cakes")
public class CakeEntity {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	@Column(name = "cake_name")
	private String cakeName;
	@Column(name = "cake_type")
	private String cakeType;
	@Column(name = "price")
	private long price;

	public CakeEntity(String cakeName, String cakeType, long price) {
		super();
		this.cakeName = cakeName;
		this.cakeType = cakeType;
		this.price = price;
	}

	public CakeEntity() {
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
