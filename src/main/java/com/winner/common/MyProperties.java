package com.winner.common;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

/**
 * 
 * @description 获取my配置文件参数
 * @author 创建人:dxw
 * @date 时间：2021年6月4日-上午10:05:53
 * @version 1.0.0
 *
 */
@Component
@ConfigurationProperties(prefix = "my") // 用来指定properties配置文件中的key前缀
@PropertySource("classpath:application.properties") // 配置文件所在位置
public class MyProperties {

	/**
	 * 文件头信息
	 */
	private String WpsPassToken;

	public String getWpsPassToken() {
		return WpsPassToken;
	}

	public void setWpsPassToken(String wpsPassToken) {
		WpsPassToken = wpsPassToken;
	}

	// 新联登

	/**
	 * 合作方编号 merchantId
	 */
	private String new_merchantId;
	/**
	 * appID
	 */
	private String new_appID;
	/**
	 * url
	 */
	private String new_url;
	/**
	 * 商户私钥 privateKey
	 */
	private String new_privateKey;
	/**
	 * 商户公钥 publicKey
	 */
	private String new_publicKey;
	/**
	 * 服开公钥 sopPublicKey
	 */
	private String new_sopPublicKey;

	// 老联登

	/**
	 * tokenUrl
	 */
	private String tokenUrl;

	/**
	 * UserUrl
	 */
	private String UserUrl;

	/**
	 * appId
	 */
	private String appId;
	/**
	 * secret
	 */
	private String secret;
	/**
	 * RSA_PK
	 */
	private String RSA_PK;
	/**
	 * RSAPublicKey
	 */
	private String RSAPublicKey;

	// 资源图片 上传与申请

	private String file_prvkey;
	private String file_pubKey;
	private String file_sopPubKey;
	private String file_apiVersion;
	private String file_merchantId;
	private String file_appId;
	private String file_recvSysCode;
	private String file_urlPrefix;
	private String file_urlPrefix_activity;
	private String file_downloadPath;
	
	

	public String getNew_merchantId() {
		return new_merchantId;
	}

	public void setNew_merchantId(String new_merchantId) {
		this.new_merchantId = new_merchantId;
	}

	public String getNew_appID() {
		return new_appID;
	}

	public void setNew_appID(String new_appID) {
		this.new_appID = new_appID;
	}

	public String getNew_url() {
		return new_url;
	}

	public void setNew_url(String new_url) {
		this.new_url = new_url;
	}

	public String getNew_privateKey() {
		return new_privateKey;
	}

	public void setNew_privateKey(String new_privateKey) {
		this.new_privateKey = new_privateKey;
	}

	public String getNew_publicKey() {
		return new_publicKey;
	}

	public void setNew_publicKey(String new_publicKey) {
		this.new_publicKey = new_publicKey;
	}

	public String getNew_sopPublicKey() {
		return new_sopPublicKey;
	}

	public void setNew_sopPublicKey(String new_sopPublicKey) {
		this.new_sopPublicKey = new_sopPublicKey;
	}

	/**
	 * RSAPrivateKey
	 */
	public String RSAPrivateKey;

	public String getAppId() {
		return appId;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getRSA_PK() {
		return RSA_PK;
	}

	public void setRSA_PK(String rSA_PK) {
		RSA_PK = rSA_PK;
	}

	public String getRSAPublicKey() {
		return RSAPublicKey;
	}

	public void setRSAPublicKey(String rSAPublicKey) {
		RSAPublicKey = rSAPublicKey;
	}

	public String getRSAPrivateKey() {
		return RSAPrivateKey;
	}

	public void setRSAPrivateKey(String rSAPrivateKey) {
		RSAPrivateKey = rSAPrivateKey;
	}

	public String getFile_prvkey() {
		return file_prvkey;
	}

	public void setFile_prvkey(String file_prvkey) {
		this.file_prvkey = file_prvkey;
	}

	public String getFile_pubKey() {
		return file_pubKey;
	}

	public void setFile_pubKey(String file_pubKey) {
		this.file_pubKey = file_pubKey;
	}

	public String getFile_sopPubKey() {
		return file_sopPubKey;
	}

	public void setFile_sopPubKey(String file_sopPubKey) {
		this.file_sopPubKey = file_sopPubKey;
	}

	public String getFile_urlPrefix_activity() {
		return file_urlPrefix_activity;
	}

	public void setFile_urlPrefix_activity(String file_urlPrefix_activity) {
		this.file_urlPrefix_activity = file_urlPrefix_activity;
	}

	public String getFile_apiVersion() {
		return file_apiVersion;
	}

	public void setFile_apiVersion(String file_apiVersion) {
		this.file_apiVersion = file_apiVersion;
	}

	public String getFile_merchantId() {
		return file_merchantId;
	}

	public void setFile_merchantId(String file_merchantId) {
		this.file_merchantId = file_merchantId;
	}

	public String getFile_appId() {
		return file_appId;
	}

	public void setFile_appId(String file_appId) {
		this.file_appId = file_appId;
	}

	public String getFile_recvSysCode() {
		return file_recvSysCode;
	}

	public void setFile_recvSysCode(String file_recvSysCode) {
		this.file_recvSysCode = file_recvSysCode;
	}

	public String getFile_urlPrefix() {
		return file_urlPrefix;
	}

	public void setFile_urlPrefix(String file_urlPrefix) {
		this.file_urlPrefix = file_urlPrefix;
	}

	public String getFile_downloadPath() {
		return file_downloadPath;
	}

	public void setFile_downloadPath(String file_downloadPath) {
		this.file_downloadPath = file_downloadPath;
	}

	public String getTokenUrl() {
		return tokenUrl;
	}

	public void setTokenUrl(String tokenUrl) {
		this.tokenUrl = tokenUrl;
	}

	public String getUserUrl() {
		return UserUrl;
	}

	public void setUserUrl(String userUrl) {
		UserUrl = userUrl;
	}

}