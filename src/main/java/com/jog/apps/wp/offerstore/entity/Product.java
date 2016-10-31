/**
 * 
 */
package com.jog.apps.wp.offerstore.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Product entity
 * 
 * @author Julius Oduro
 */
@XmlRootElement
public class Product implements  Serializable  {

	private static final long serialVersionUID = 1L;
	
	private int id;
	private String name;
	private String description;
	private BigDecimal price;
	
	public Product(){		
	}
	
	public Product(String name, String description, BigDecimal price) {
		this.name = name;
		this.description = description;
		this.price = price;
	}
	
	
	public int getId() {
		return id;
	}
	public Product setId(int id) {
		this.id = id;
		return this;
	}
	public String getName() {
		return name;
	}
	public Product setName(String name) {
		this.name = name;
		return this;
	}
	public String getDescription() {
		return description;
	}
	public Product setDescription(String description) {
		this.description = description;
		return this;
	}	
	
	public BigDecimal getPrice() {
		return price;
	}
	public Product setPrice(BigDecimal price) {
		this.price = price;
		return this;
	}
	
	@Override
	public String toString() {
		return "Product [id=" + id + ", name=" + name + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Product))
			return false;
		Product other = (Product) obj;
		if (id != other.id)
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}
	
	

}
