package com.sul.rest.service.error;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sulaiman Abboud
 */
public class FieldValidationErrorDetails {
	private String title;
	private int status;
	private String detail;
	private long timeStamp;
	private String path;
	private String devMessage;
	private Map<String, List<FieldValidationError>> errors = new HashMap<String, List<FieldValidationError>>();

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getDetail() {
		return detail;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	public long getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(long timeStamp) {
		this.timeStamp = timeStamp;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getDevMessage() {
		return devMessage;
	}

	public void setDevMessage(String devMessage) {
		this.devMessage = devMessage;
	}

	public Map<String, List<FieldValidationError>> getErrors() {
		return errors;
	}

	public void setErrors(Map<String, List<FieldValidationError>> errors) {
		this.errors = errors;
	}

}
