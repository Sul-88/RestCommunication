package com.sul.person.service.dto;

/**
 * @author Sulaiman Abboud
 */

/**
 * @author sulaiman
 */
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

@Entity
@Table(name = "PERSON")
@SequenceGenerator(name = "PERSON_SEQ")
public class PersonDTO {

	public PersonDTO() {
	}

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PERSON_SEQ")
	@SequenceGenerator(sequenceName = "PERSON_SEQ", allocationSize = 1, name = "PERSON_SEQ")
	@Column(name = "id")
	private Long id;

	@Column(name = "name")
	@NotEmpty(message = "error.name.empty")
	@Length(min = 3, max = 20, message = "error.name.length")
	private String name;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

}
