package com.fastcampus.sns.controller.response;

import java.sql.Timestamp;

import com.fastcampus.sns.model.Alarm;
import com.fastcampus.sns.model.AlarmArgs;
import com.fastcampus.sns.model.AlarmType;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AlarmResponse {
	private Integer id;
	private AlarmType alarmType;
	private AlarmArgs args;
	private String text;
	private Timestamp registeredAt;
	private Timestamp updatedAt;
	private Timestamp deletedAt;

	public static AlarmResponse fromAlarm(Alarm alarm) {
		return new AlarmResponse(
			alarm.getId(),
			alarm.getAlarmType(),
			alarm.getArgs(),
			alarm.getAlarmType().getAlarmText(),
			alarm.getRegisteredAt(),
			alarm.getUpdatedAt(),
			alarm.getDeletedAt()
		);
	}
}
