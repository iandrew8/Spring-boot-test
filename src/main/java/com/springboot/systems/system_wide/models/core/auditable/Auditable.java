package com.springboot.systems.system_wide.models.core.auditable;

import com.springboot.systems.system_wide.models.enums.RecordStatus;
import com.springboot.systems.system_wide.models.core.security.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/*
 * Designates a class whose mapping information is applied to the entities that inherit from it. 
 * A mapped superclass has no separate table defined for it.
 * AuditingEntityListener :- Spring Data JPA provides a JPA entity listener class, 
 * AuditingEntityListener, which contains the callback methods 
 * (annotated with the @PrePersist and @PreUpdate annotations), which will be used to manage (persist or update) 
 * audit related properties when the entity is persisted or updated.
 * 
 * @EntityListeners can be used to specify listener classes which will listen for the event of the entities through some annotated methods. 
 * If any event is occurred, the relevant annotated method will get notified. 
 * We have registered AuditEntityListener as the entity listener class.  
 */

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Setter
public class Auditable implements Serializable {

	private static final long serialVersionUID = -938829688521108282L;

	private String id;
	private User createdBy;
	private User updatedBy;
	private Date dateCreated;
	private Date dateChanged;
	private RecordStatus recordStatus;

	public Auditable() {
		this.recordStatus = RecordStatus.ACTIVE;
	}

	// If you want String to be database key you need to use UUID to automatically
	// generate key.
	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid")
	public String getId() {
		return id;
	}

	@CreatedBy
	@ManyToOne
	@JoinColumn(name = "created_by", nullable = true)
	@JsonIgnore
	public User getCreatedBy() {
		return createdBy;
	}

	@LastModifiedBy
	@ManyToOne
	@JoinColumn(name = "updated_by", nullable = true)
	@JsonIgnore
	public User getUpdatedBy() {
		return updatedBy;
	}

	@CreatedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_created", nullable = true)
	public Date getDateCreated() {
		return dateCreated;
	}

	@LastModifiedDate
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "date_changed", nullable = true)
	public Date getDateChanged() {
		return dateChanged;
	}

	@Enumerated(EnumType.STRING)
	@Column(name = "record_status")
	public RecordStatus getRecordStatus() {
		return recordStatus;
	}

}
