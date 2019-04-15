package com.common.models;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name = "items")
@Data
public class Items extends AbstractEntity {

	private static final long serialVersionUID = -9017650847571487337L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long items_id;

	@OneToMany(mappedBy = "itemsData", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private Set<Laptops> laptops;

	@OneToMany(mappedBy = "itemsData", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Bikes> bikes;
}
