package com.nqmtuan.android.randomrandomizer;

import java.util.List;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.ActionBar.Tab;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.nqmtuan.android.randomrandomizer.DaoMaster.DevOpenHelper;
import com.nqmtuan.android.randomrandomizer.Common.Constant;
import com.nqmtuan.android.randomrandomizer.Database.EntityManager;
import com.nqmtuan.android.randomrandomizer.FragmentTab.FragmentTab1;
import com.nqmtuan.android.randomrandomizer.FragmentTab.FragmentTab2;
import com.nqmtuan.android.randomrandomizer.Logging.MyLog;

public class MainActivity extends SherlockFragmentActivity {
	public DaoMaster daoMaster = null;
	public SQLiteDatabase database = null;
	public DevOpenHelper helper = null; 	
	public static final String LOG_TAG = "MainActivity";
	Tab tab;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setupDatabase ();
//      setContentView(R.layout.activity_main);
        ActionBar actionBar = getSupportActionBar();
        
        actionBar.setDisplayShowHomeEnabled(true);
        
        actionBar.setDisplayShowTitleEnabled(true);
        
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
        
        tab = actionBar.newTab().setTabListener(new FragmentTab1());
        tab.setText("Tab 1");
        actionBar.addTab(tab);
        
        tab = actionBar.newTab().setTabListener(new FragmentTab2());
        tab.setText("Tab 2");
        actionBar.addTab(tab);
//
//        tab = actionBar.newTab().setTabListener(new FragmentsTab3());
//        tab.setText("Tab 3");
//        actionBar.addTab(tab);        
    }
    
    private void setupDatabase () {
    	helper = new DaoMaster.DevOpenHelper(this, Constant.DATABASE_NAME, null);
    	database = helper.getWritableDatabase();
    	daoMaster = new DaoMaster(database);
    	EntityManager entityManager = EntityManager.getSharedInstance();
    	entityManager.setDaoMaster(daoMaster);
    	entityManager.setDatabaseName(Constant.DATABASE_NAME);
    	entityManager.setDefaultPackageName(this.getClass().getPackage().getName());
    	this.createData();
    }
    
    
    private void createData () {
    	EntityManager entityManager = EntityManager.getSharedInstance();
    	RandomElement randomElement = (RandomElement) entityManager.createEntityWithName("RandomElement");
    	randomElement.setName("My name");
    	entityManager.insertEntity("RandomElement", randomElement);
    	
    	List <?> list = entityManager.getAllEntities("RandomElement", null, true);
    	MyLog.d(LOG_TAG, "List has " + list.size() + " elements");
    }

    
    
}
