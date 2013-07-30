package com.zimerick.zmrklibrary.zmrkdatasync;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.zimerick.zmrklibrary.zmrkdatasync.DaoMaster.DevOpenHelper;

import de.greenrobot.dao.AbstractDaoSession;

public class ZMRKCoreDataManager {
	private static ZMRKCoreDataManager sharedInstance = null;
	private ZMRKEntityManager entityManager = null;
	private ZMRKEntityManager queueEntityManager;
	private String entityPackageName;
	public static Context context;
	private ZMRKCoreDateManagerDataSource dataSource = null;
	public static final String TABLE_NAME = "tableName";
	


	private ZMRKCoreDataManager() {
		// TODO Auto-generated constructor stub
	}
	
	
	//GETTERS AND SETTERS
	
	public static ZMRKCoreDataManager getSharedInstance ()
	{
		if (sharedInstance == null)
		{
			if (context != null)
			{
				sharedInstance = new ZMRKCoreDataManager();
				
				//Create queueEntityManager
		        DevOpenHelper queueDBHelper = new com.zimerick.zmrklibrary.zmrkdatasync.DaoMaster.DevOpenHelper(context, "queue-db", null);
		        SQLiteDatabase queueDB = queueDBHelper.getWritableDatabase();
		        com.zimerick.zmrklibrary.zmrkdatasync.DaoMaster queueDaoMaster = new com.zimerick.zmrklibrary.zmrkdatasync.DaoMaster(queueDB);
		        sharedInstance.queueEntityManager = new ZMRKEntityManager("queue-db", queueDaoMaster);
			}
		}
		return sharedInstance;
	}

	public ZMRKEntityManager getEntityManager ()
	{
		return entityManager;
	}
	
	public AbstractDaoSession getDaoSession () {
		return this.entityManager.getDaoSession();
	}
	
	public void setEntityManager (String _entityPackageName, ZMRKEntityManager _entityManager)
	{
		if (entityManager == null)
		{
			this.entityPackageName = _entityPackageName;
			this.entityManager = _entityManager;
		}
	}
	
	public void setDataSource (ZMRKCoreDateManagerDataSource dataSource)
	{
		this.dataSource = dataSource;
	}
	


	
	public boolean removeAllQueueObjects ()
	{
		return queueEntityManager.deleteAll("ZMRKCoreDataQueueObject");		
	}
	
	
	//Queue Management
	//When the app make some changes to the database => add to queue to send to the server
	public boolean addEntityToQueue (String entityName, Object entity)
	{
		String packageName = this.getClass().getPackage().getName();
		HashMap <String, Object> entityAttributes = getAttributesForEntity(entityName, entity);
		if (entityAttributes == null)
			return false;
		
		entityAttributes.put(TABLE_NAME, entityName);
		JSONObject entityData = new JSONObject(entityAttributes);
		
		//Get the max modifiedIndex
		List <?> queueObjects = queueEntityManager.getAllEntities("ZMRKCoreDataQueueObject", "modifiedIndex" , false);
		if (queueObjects == null)
			return false;
		
		int nextModifiedIndex = 1;
		if (queueObjects.size() >= 1)
		{
			ZMRKCoreDataQueueObject queueObject = (ZMRKCoreDataQueueObject) queueObjects.get(0);
			nextModifiedIndex = queueObject.getModifiedIndex() + 1;
		}
		
		
		ZMRKCoreDataQueueObject newQueueObjectEntity = (ZMRKCoreDataQueueObject) entityManager.createEntityWithName("ZMRKCoreDataQueueObject", packageName);
		newQueueObjectEntity.setAppIndex(0);
		newQueueObjectEntity.setModifiedIndex(nextModifiedIndex);
		newQueueObjectEntity.setData(entityData.toString());
		
		boolean successful = queueEntityManager.insertEntity("ZMRKCoreDataQueueObject", newQueueObjectEntity);
		return successful;
	}
	
	

	
	
	public List <ZMRKCoreDataQueueObject> getNextBatch (int batchSize, int appIndexToSet)
	{
		List <?> list = null;
		list = queueEntityManager.getNEntitiesByAttributes("ZMRKCoreDataQueueObject", null, batchSize, "modifiedIndex", true);
		List <ZMRKCoreDataQueueObject> resultList = new ArrayList <ZMRKCoreDataQueueObject> ();
		if (list == null)
			return null;
		for (Object obj:list)
		{
			ZMRKCoreDataQueueObject queueObject = (ZMRKCoreDataQueueObject) obj;
			queueObject.setAppIndex(appIndexToSet);
			boolean success = queueEntityManager.updateEntity("ZMRKCoreDataQueueObject", queueObject);
			if (success == false)
				return null;
			resultList.add((ZMRKCoreDataQueueObject) queueObject);
		}
		return resultList;
	}
	
	
	public void removeObjectsWithAppIndex (int appIndex)
	{
		HashMap<String, Object> conditions = new HashMap <String, Object> ();
		conditions.put ("appIndex", appIndex);
		List <?> queueObjects = queueEntityManager.getNEntitiesByAttributes("ZMRKCoreDataQueueObject", conditions, 0, null, true);
		for (Object object:queueObjects)
		{
			ZMRKCoreDataQueueObject queueObject = (ZMRKCoreDataQueueObject) object;
			queueEntityManager.deleteEntity("ZMRKCoreDataQueueObject", queueObject);
		}
	}
	
	public int getNQueueObjects ()
	{
		List <?> queueObjects = queueEntityManager.getAllEntities("ZMRKCoreDataQueueObject", null, true);
		if (queueObjects == null)
			return 0;
		else
			return queueObjects.size();
	}
	
	
	//Wrapper from Entity Manager
	
	public Object createEntityWithName (String entityName)
	{
		String packageName = entityPackageName;
		return entityManager.createEntityWithName(entityName, packageName);		
	}
	
	
	public Object createEntityWithNameAndAttributes (String entityName, HashMap <String, Object>  entityAttributes)
	{
		String packageName = entityPackageName;
		return entityManager.createEntityWithNameAndAttributes(entityName, entityAttributes, packageName);
	}
	
	
	public Object createOrUpdateEntityWithNameAndAttributes (String entityName, HashMap <String, Object> entityAttributes)
	{
		String packageName = entityPackageName;
		return entityManager.createOrUpdateEntityWithNameAndAttributes(entityName, entityAttributes, packageName);
	}
	
	public Object createOrUpdateEntityWithNameAndAttributes (String entityName, HashMap <String, Object> entityAttributes, String entityIDStr)
	{
		String packageName = entityPackageName;
		return entityManager.createOrUpdateEntityWithNameAndAttributes(entityName, entityAttributes, packageName, entityIDStr);
	}
	
	public boolean insertEntity (String entityName, Object entity)
	{
		return entityManager.insertEntity(entityName, entity);
	}
	
	
	public boolean updateEntity (String entityName, Object entity)
	{
		return entityManager.updateEntity(entityName, entity);
	}
	
	
	public boolean deleteEntity (String entityName, Object entity)
	{
		return entityManager.deleteEntity(entityName, entity);
	}
	
	public boolean deleteAll (String entityName)
	{
		return entityManager.deleteAll(entityName);
	}
	
	
	public boolean refreshEntity (String entityName, Object entity)
	{
		return entityManager.refreshEntity(entityName, entity);
	}
	
	
	public List<?> getAllEntities (String entityName, String orderBy, boolean isAscending)
	{
		return entityManager.getAllEntities(entityName, orderBy, isAscending);
	}
	
	public List <?> getNEntitiesByAttributes (String entityName, HashMap <String, Object> entityAttributes, int limit, String orderBy, boolean isAscending)
	{
		return entityManager.getNEntitiesByAttributes(entityName, entityAttributes, limit, orderBy, isAscending);
	}
	
	public List <?> getNEntitiesBySQL (String entityName, String whereClause, int limit, String rawOrder)
	{
		return entityManager.getNEntitiesBySQL(entityName, whereClause, limit, rawOrder);
	}
	
	Object getEntityByPrimaryKey (String entityName, String primaryKey)
	{
		return entityManager.getEntityByPrimaryKey(entityName, primaryKey);
	}
	
	public HashMap <String, Object> getAttributesForEntity (String entityName, Object entity)
	{
		if (dataSource == null)
			return entityManager.getAttributesForEntity(entityName, entity);
		List <String> listAttributes = dataSource.getAttributesToUploadForEntity(entityName);
		if (listAttributes == null || listAttributes.size() == 0)
			return entityManager.getAttributesForEntity(entityName, entity);
		return entityManager.getAttributesForEntity(entityName, entity, listAttributes);
	}	
	
	public List <String> getListExcludedAttributeForEntity (String entityName) {
		if (dataSource == null)
			return new ArrayList<String>();
		else {
			List <String> listAttribute = dataSource.getAttributesToExcludeForDownloadForEntity(entityName);
			if (listAttribute == null)
				return new ArrayList<String>();
			return listAttribute;
		}
	}
	
	public HashMap <String, String> getColumnMapping (String entityName)	
	{
		return entityManager.getColumnMapping(entityName);
	}
	
	public String getColumnNameForAttribute (String entityName, String attributeName)
	{
		return entityManager.getColumnNameForAttribute(entityName, attributeName);
	}
}
