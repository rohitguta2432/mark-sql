package com.common.models;


import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.index.Indexed;


/**
 * @author Rohit Raj
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
public class AbstractEntity implements Serializable {
    
	private static final long serialVersionUID = 5646557584769485464L;
	
	@Indexed
    @CreatedDate
    protected Long createdDate;
    @Indexed
    @CreatedBy
    protected String createdBy;
    @Indexed
    @LastModifiedDate
    protected Long updatedDate;
    @Indexed
    @LastModifiedBy
    protected String updatedBy;
    
    public void setCreatedDate(){
		  this.createdDate = System.currentTimeMillis();
	  }
	  
	  public void setUpdatedDate(){
		  this.updatedDate = System.currentTimeMillis();
	  }
	  
	  public void createdBy(String createdBy){
		  this.createdBy = createdBy;
	  }
	  
	  public void updatedBy(String updatedBy){
		  this.updatedBy = updatedBy;
	  }
}
