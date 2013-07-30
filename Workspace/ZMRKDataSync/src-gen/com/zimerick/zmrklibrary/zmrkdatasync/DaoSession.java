package com.zimerick.zmrklibrary.zmrkdatasync;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoConfig;
import de.greenrobot.dao.AbstractDaoSession;
import de.greenrobot.dao.IdentityScopeType;

import com.zimerick.zmrklibrary.zmrkdatasync.ZMRKCoreDataQueueObject;

import com.zimerick.zmrklibrary.zmrkdatasync.ZMRKCoreDataQueueObjectDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see de.greenrobot.dao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig zMRKCoreDataQueueObjectDaoConfig;

    private final ZMRKCoreDataQueueObjectDao zMRKCoreDataQueueObjectDao;

    public DaoSession(SQLiteDatabase db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        zMRKCoreDataQueueObjectDaoConfig = daoConfigMap.get(ZMRKCoreDataQueueObjectDao.class).clone();
        zMRKCoreDataQueueObjectDaoConfig.initIdentityScope(type);

        zMRKCoreDataQueueObjectDao = new ZMRKCoreDataQueueObjectDao(zMRKCoreDataQueueObjectDaoConfig, this);

        registerDao(ZMRKCoreDataQueueObject.class, zMRKCoreDataQueueObjectDao);
    }
    
    public void clear() {
        zMRKCoreDataQueueObjectDaoConfig.getIdentityScope().clear();
    }

    public ZMRKCoreDataQueueObjectDao getZMRKCoreDataQueueObjectDao() {
        return zMRKCoreDataQueueObjectDao;
    }

}
