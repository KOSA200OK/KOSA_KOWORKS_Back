package com.my.notification.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicInsert;

import com.my.member.entity.MemberEntity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@DynamicInsert

@Entity
@Table(name="notification")
@SequenceGenerator(name = "notification_seq_generator",
				   sequenceName = "notification_seq",
				   initialValue = 1,
				   allocationSize = 1)
public class NotificationEntity {
	
	@Id
	@Column(name="notificationId")
	@GeneratedValue(strategy = GenerationType.SEQUENCE,
					generator = "notification_seq_generator")
	private Integer id;
	
	@ManyToOne
	@JoinColumn(name = "receiverId",
				nullable = false)
	private MemberEntity memberEntity;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "type",
			nullable = false)
	private NotificationType notificationType;
	
	@Column(name = "content")
	private String content;
	
	@Column(name = "createdAt")
	private Date createdAt;

	@Column(name = "status")
	private Integer status;
	
	public enum NotificationType {
		
		NOTICE,
		MEETING,
		CAR,
		STUFF
		
	}

} // end class
