/*
* Copyright (c) 2014 lee, Inc. All Rights Reserved.
*
* This software is the confidential and proprietary information of lee, Inc.
* Use is subject to license terms.
*/
package lee.ysl.api.beans;

public class LocalizedSiteAttributes {
	private long localeId;
	private String countryISOCode;
	private String languageISOCode;
	private String name;
	private String nameWithCountry;
	private String loginUrl;
	private String baseUrl;
	private String help;
	private String forgetPasswordURL;
	private String loginHelp;
	
	public LocalizedSiteAttributes() {
	}

	/**
	 * @return the localeId
	 */
	public long getLocaleId() {
		return localeId;
	}

	/**
	 * @param localeId the localeId to set
	 */
	public void setLocaleId(long localeId) {
		this.localeId = localeId;
	}

	/**
	 * @return the countryISOCode
	 */
	public String getCountryISOCode() {
		return countryISOCode;
	}

	/**
	 * @param countryISOCode the countryISOCode to set
	 */
	public void setCountryISOCode(String countryISOCode) {
		this.countryISOCode = countryISOCode;
	}

	/**
	 * @return the languageISOCode
	 */
	public String getLanguageISOCode() {
		return languageISOCode;
	}

	/**
	 * @param languageISOCode the languageISOCode to set
	 */
	public void setLanguageISOCode(String languageISOCode) {
		this.languageISOCode = languageISOCode;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the nameWithCountry
	 */
	public String getNameWithCountry() {
		return nameWithCountry;
	}

	/**
	 * @param nameWithCountry the nameWithCountry to set
	 */
	public void setNameWithCountry(String nameWithCountry) {
		this.nameWithCountry = nameWithCountry;
	}

	/**
	 * @return the loginURL
	 */
	public String getLoginUrl() {
		return loginUrl;
	}

	/**
	 * @param loginURL the loginURL to set
	 */
	public void setLoginUrl(String loginURL) {
		this.loginUrl = loginURL;
	}

	/**
	 * @return the baseURL
	 */
	public String getBaseUrl() {
		return baseUrl;
	}

	/**
	 * @param baseURL the baseURL to set
	 */
	public void setBaseUrl(String baseURL) {
		this.baseUrl = baseURL;
	}

	/**
	 * @return the help
	 */
	public String getHelp() {
		return help;
	}

	/**
	 * @param help the help to set
	 */
	public void setHelp(String help) {
		this.help = help;
	}

	/**
	 * @return the forgetPasswordURL
	 */
	public String getForgetPasswordURL() {
		return forgetPasswordURL;
	}

	/**
	 * @param forgetPasswordURL the forgetPasswordURL to set
	 */
	public void setForgetPasswordURL(String forgetPasswordURL) {
		this.forgetPasswordURL = forgetPasswordURL;
	}

	/**
	 * @return the loginHelp
	 */
	public String getLoginHelp() {
		return loginHelp;
	}

	/**
	 * @param loginHelp the loginHelp to set
	 */
	public void setLoginHelp(String loginHelp) {
		this.loginHelp = loginHelp;
	}
	
	
}
