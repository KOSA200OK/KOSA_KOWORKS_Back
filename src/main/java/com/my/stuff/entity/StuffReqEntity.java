package com.my.stuff.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "stuff_req")
@SequenceGenerator(name = "req_seq_generator", sequenceName = "req_seq", initialValue = 1, allocationSize = 1)
public class StuffReqEntity {
	@Id
	@Column(name = "reqId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "req_seq_generator")
	private int reqId;
	
	@OneToOne(mappedBy = "departmentId")
	private int departmentId;
	
	@Column(name = "reqStatus")
	private int reqStatus;
	
    @Column(name = "reqDate")
	private Date reqDate;
    
    @Column(name = "purpose")
	private String purpose;
    
    @Column(name = "reject")
	private String reject;
    
    
    @OneToMany(mappedBy="reqId")
    private List<StuffReqLineEntity> lines;
}
