package com.zimerick.zmrklibrary.zmrkdatasync;


public class ZMRKDataCompression {

	public enum CompressionType  {CompressionTypeNone, CompressionTypeZLIB, CompressionTypeGZIP};
	private CompressionType compressionType;
	
	public ZMRKDataCompression() {
		compressionType = CompressionType.CompressionTypeNone;
	}
	
	public ZMRKDataCompression (CompressionType type)
	{
		if (type != null)
			compressionType = type;
		else
			compressionType = CompressionType.CompressionTypeNone;
	}

	public CompressionType getCompressionType()
	{
		return this.compressionType;
	}
	
	public void setCompressionType (CompressionType _compressionType)
	{
		this.compressionType = _compressionType;	
		if (this.compressionType == null)
			this.compressionType = CompressionType.CompressionTypeNone;
	}
	
	/**
	 * Compress data based on object.compressionType
	 * @param data: the data to compress
	 * @return compressedData
	 */
	public String compressData (String data)
	{
		String result = compressData(data, this.compressionType);
		return result;
	}
	
	
	/**
	 * Uncompress data based on object.compressionType
	 * @param data: The data to uncompressed
	 * @return uncompressed data
	 */
	public String uncompressData (String data)
	{
		String result = uncompressData(data, this.compressionType);
		return result;
	}
	
	
	private String compressData (String data, CompressionType compressionType)
	{
		String result = "";
		if (compressionType == CompressionType.CompressionTypeGZIP)
			result = compressDataUsingGZIP(data);
		else if (compressionType == CompressionType.CompressionTypeZLIB)
			result = compressDataUsingZLIB(data);
		else //No compression
			result = data;
		return result;		
	}
	

	private String compressDataUsingGZIP(String data) {
		return data;
	}

	private String compressDataUsingZLIB(String data) {
		return data;
	}
	

	private String uncompressData(String data, CompressionType compressionType)
	{
		String result = "";
		if (compressionType == CompressionType.CompressionTypeGZIP)
			result = uncompressDataUsingGZIP(data);
		else if (compressionType == CompressionType.CompressionTypeZLIB)
			result = uncompressDataUsingZLIB(data);
		else //No compression
			result = data;
		return result;		
	}

	private String uncompressDataUsingZLIB(String data) {
		return data;
	}

	
	private String uncompressDataUsingGZIP(String data) {
		return data;
	}
}
