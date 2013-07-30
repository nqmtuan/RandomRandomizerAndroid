package com.zimerick.zmrklibrary.zmrkdatasync;

public class ZMRKDataEncryption {

	public enum EncryptionType  {EncryptionTypeNone, EncryptionTypeMD5, EncryptionTypeSHA1};
	private EncryptionType encryptionType;
	
	public ZMRKDataEncryption() {
		encryptionType = EncryptionType.EncryptionTypeNone;
	}
	
	public ZMRKDataEncryption (EncryptionType type) {
		if (type != null) {
			this.encryptionType = type;
		}
		else {
			this.encryptionType = EncryptionType.EncryptionTypeNone;
		}
	}
	
	public EncryptionType getEncryptionType ()
	{
		return this.encryptionType;
	}
	
	public void setEncryptionType (EncryptionType _encryptionType)
	{		
		this.encryptionType = _encryptionType;
		if (_encryptionType == null)
			this.encryptionType = EncryptionType.EncryptionTypeNone;
	}
	
	
	/**
	 * Encrypt data based on object.encryptionType
	 * @param data: The data to encrypt
	 * @return Encrypted data
	 */
	public String encryptData (String data)
	{
		String result = encryptData(data, encryptionType);
		return result;
	}
	
	
	
	/**
	 * Decrypt data based on object.encryptionType
	 * @param data: The data to decrypt
	 * @return decrypted data
	 */
	public String decryptData (String data)
	{
		String result = decryptData(data, encryptionType);
		return result;
	}
	
	
	public String encryptData (String data, EncryptionType encryptionType)
	{
		String result = "";
		if (encryptionType == EncryptionType.EncryptionTypeMD5)
			result = encryptDataUsingMD5(data);
		else if (encryptionType == EncryptionType.EncryptionTypeSHA1)
			result = encryptDataUsingSHA1(data);
		else //No encryption
			result = data;
		return result;		
	}
	
	
	public String decryptData(String data, EncryptionType encryptionType)
	{
		String result = "";
		if (encryptionType == EncryptionType.EncryptionTypeMD5)
			result = decryptDataUsingMD5(data);
		else if (encryptionType == EncryptionType.EncryptionTypeSHA1)
			result = decryptDataUsingSHA1(data);
		else //No encryption
			result = data;
		
		return result;
	}
	
	private String encryptDataUsingMD5 (String data) 
	{
		return data;
	}
	
	private String encryptDataUsingSHA1 (String data) 
	{
		return data;
	}
	
	
	private String decryptDataUsingMD5 (String data)
	{
		return data;
	}	
	
	private String decryptDataUsingSHA1 (String data)
	{
		return data;
	}
	
}
