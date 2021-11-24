package com.trainting.MyBoutique.persistence.abstracts;

import java.io.Serializable;
import java.time.Instant;


import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class AbstractEntity implements Serializable{
	
	
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy =GenerationType.AUTO)
	private long id;
	
	@CreatedDate
	@Column(name="creationDate")
	@JsonIgnore
	private Instant creationdate=Instant.now();
	
	@LastModifiedDate
	@JsonIgnore
    @Column(name="lastModificationDate")
	private Instant lastModificationDate=Instant.now();

}
