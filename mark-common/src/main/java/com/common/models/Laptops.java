package com.common.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;


@Entity
@Table(name="laptops")
@Data
public class Laptops extends AbstractEntity{

	 private static final long serialVersionUID = -9017650847571487336L;
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long laptop_Id;
	
	@ElementCollection
	private List<String> list =new ArrayList<>();
	
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="items_id",nullable=false,unique=true)
	private Items itemsData;
}
