package com.lee.autotriage;

import java.util.Date;
import java.util.List;

public class Bug {

	private int bugId;
	private String agentName;
	private String customer;
	private String priority;
	private String severity;
	private int sumInfo;
	private String shortDesc;
	private String dbName;
	private Date creationDate;
	private List<String> comments;
	private String agentType;
	private Date eta;
	private String alias;
	private long averageLatency;


	public int getBugId() {
		return bugId;
	}
	public void setBugId(int bugId) {
		this.bugId = bugId;
	}
	public String getAgentName() {
		return agentName;
	}
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	public String getCustomer() {
		return customer;
	}
	public void setCustomer(String customer) {
		this.customer = customer;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public int getSumInfo() {
		return sumInfo;
	}
	public void setSumInfo(int sumInfo) {
		this.sumInfo = sumInfo;
	}
	public String getShortDesc() {
		return shortDesc;
	}
	public void setShortDesc(String shortDesc) {
		this.shortDesc = shortDesc;
	}
	public String getDbName() {
		return dbName;
	}
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	public Date getCreationDate() {
		return creationDate;
	}
	public void setCreationDate(Date creationDate) {
		this.creationDate = creationDate;
	}
	public List<String> getComments() {
		return comments;
	}
	public void setComments(List<String> comments) {
		this.comments = comments;
	}
	public String getAgentType() {
		return agentType;
	}
	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}
	public Date getEta() {
		return eta;
	}
	public void setEta(Date eta) {
		this.eta = eta;
	}
	public String getAlias() {
		return alias;
	}
	public void setAlias(String alias) {
		this.alias = alias;
	}
	public long getAverageLatency() {
		return averageLatency;
	}
	public void setAverageLatency(long averageLatency) {
		this.averageLatency = averageLatency;
	}

}
