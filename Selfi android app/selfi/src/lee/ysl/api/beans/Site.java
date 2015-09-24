/*
* Copyright (c) 2015 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

public class Site {

	private long id;
	private String name;
	private String nameWithCountry;
	private String loginUrl;
	private String baseUrl;
	private byte[] favicon;
	private byte[] logo;
	private String status;
	private String[] containerNames;
	private String mfaType;
	private String help;
	private String primaryLanguageISOCode;
	private String countryISOCode;
	private String oAuthSite;
	private LocalizedSiteAttributes[] localizedSiteAttributes;
	private String lastModified;
	private String forgetPasswordUrl;
	private String loginHelp;
	private LoginForm loginForm;
	private String availableFrom;
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNameWithCountry() {
		return nameWithCountry;
	}
	public void setNameWithCountry(String nameWithCountry) {
		this.nameWithCountry = nameWithCountry;
	}
	public String getLoginUrl() {
		return loginUrl;
	}
	public void setLoginUrl(String loginUrl) {
		this.loginUrl = loginUrl;
	}
	public String getBaseUrl() {
		return baseUrl;
	}
	public void setBaseUrl(String baseUrl) {
		this.baseUrl = baseUrl;
	}
	public LoginForm getLoginForm() {
		return loginForm;
	}
	public void setLoginForm(LoginForm loginForm) {
		this.loginForm = loginForm;
	}
	public byte[] getFavicon() {
		return favicon;
	}
	public void setFavicon(byte[] favicon) {
		this.favicon = favicon;
	}
	public byte[] getLogo() {
		return logo;
	}
	public void setLogo(byte[] logo) {
		this.logo = logo;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String[] getContainerNames() {
		return containerNames;
	}
	public void setContainerNames(String[] containerNames) {
		this.containerNames = containerNames;
	}
	public String getMfaType() {
		return mfaType;
	}
	public void setMfaType(String mfaType) {
		this.mfaType = mfaType;
	}
	public String getHelp() {
		return help;
	}
	public void setHelp(String help) {
		this.help = help;
	}
	public String getPrimaryLanguageISOCode() {
		return primaryLanguageISOCode;
	}
	public void setPrimaryLanguageISOCode(String primaryLanguageISOCode) {
		this.primaryLanguageISOCode = primaryLanguageISOCode;
	}
	public String getCountryISOCode() {
		return countryISOCode;
	}
	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}
	public String getoAuthSite() {
		return oAuthSite;
	}
	public void setoAuthSite(String oAuthSite) {
		this.oAuthSite = oAuthSite;
	}
	public LocalizedSiteAttributes[] getLocalizedSiteAttributes() {
		return localizedSiteAttributes;
	}
	public void setLocalizedSiteAttributes(
			LocalizedSiteAttributes[] localizedSiteAttributes) {
		this.localizedSiteAttributes = localizedSiteAttributes;
	}
	public String getLastModified() {
		return lastModified;
	}
	public void setLastModified(String lastModified) {
		this.lastModified = lastModified;
	}
	public String getForgetPasswordUrl() {
		return forgetPasswordUrl;
	}
	public void setForgetPasswordUrl(String forgetPasswordUrl) {
		this.forgetPasswordUrl = forgetPasswordUrl;
	}
	public String getLoginHelp() {
		return loginHelp;
	}
	public void setLoginHelp(String loginHelp) {
		this.loginHelp = loginHelp;
	}
	public String getAvailableFrom() {
		return availableFrom;
	}
	public void setAvailableFrom(String availableFrom) {
		this.availableFrom = availableFrom;
	}
}
