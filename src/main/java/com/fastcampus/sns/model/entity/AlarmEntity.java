package com.fastcampus.sns.model.entity;

import java.sql.Timestamp;
import java.time.Instant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.Where;

import com.fastcampus.sns.model.AlarmArgs;
import com.fastcampus.sns.model.AlarmType;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "\"alarm\"", indexes = {
	@Index(name = "alarm_user_id_idx", columnList = "user_id")
})
@TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
@SQLDelete(sql = "UPDATE \"alarm\" SET deleted_at = NOW() WHERE id=?")
@Where(clause = "deleted_at is NULL")
@NoArgsConstructor
public class AlarmEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	// 알람 받은 사람
	@ManyToOne
	@JoinColumn(name = "user_id")
	private UserEntity user;

	@Enumerated(EnumType.STRING)
	private AlarmType alarmType;

	@Type(type = "jsonb")
	@Column(columnDefinition = "json")
	private AlarmArgs args;

	private Timestamp registeredAt;

	private Timestamp updatedAt;

	private Timestamp deletedAt;

	@PrePersist
	void registeredAt() {
		this.registeredAt = Timestamp.from(Instant.now());
	}

	@PreUpdate
	void updatedAt() {
		this.updatedAt = Timestamp.from(Instant.now());
	}

	public static AlarmEntity of(UserEntity userEntity, AlarmType alarmType, AlarmArgs args) {
		AlarmEntity entity = new AlarmEntity();
		entity.setUser(userEntity);
		entity.setAlarmType(alarmType);
		entity.setArgs(args);
		return entity;
	}
}
