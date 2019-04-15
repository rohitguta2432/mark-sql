package com.common.models;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "bike")
public class Bikes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long bike_id;

	@ElementCollection
	private List<String> bike;

	@ManyToOne(cascade = CascadeType.REFRESH)
	@JoinColumn(name = "items_id",nullable=false,unique=true)
	private Items itemsData;
}
