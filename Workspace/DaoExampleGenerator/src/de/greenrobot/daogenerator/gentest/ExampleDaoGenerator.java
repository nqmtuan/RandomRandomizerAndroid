/*
 * Copyright (C) 2011 Markus Junginger, greenrobot (http://greenrobot.de)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.greenrobot.daogenerator.gentest;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;
import de.greenrobot.daogenerator.ToMany;

/**
 * Generates entities and DAOs for the example project DaoExample.
 * 
 * Run it as a Java application (not Android).
 * 
 * @author Markus
 */
public class ExampleDaoGenerator {

    public static void main(String[] args) throws Exception {
        Schema schema = new Schema(3, "com.nqmtuan.android.randomrandomizer");
        schema.enableKeepSectionsByDefault();
        addRandomizerSchema(schema);
        new DaoGenerator().generateAll(schema, "D:\\Projects\\RandomRandomizerAndroid\\Workspace\\RandomRandomizer\\src-gen");
    }
    
    private static void addRandomizerSchema (Schema schema)
    {
    	//RandomElement
    	Entity randomElement = schema.addEntity("RandomElement");
    	randomElement.implementsSerializable();
    	addRandomElementAttributes (randomElement);
    	
    	Entity randomElementCollection = schema.addEntity("RandomElementCollection");
    	randomElementCollection.implementsSerializable();
    	addRandomElementCollectionAttributes (randomElementCollection);
    	
 
    	Property randomElementCollectionProperty = randomElement.addLongProperty("collectionId").getProperty();
    	randomElement.addToOne(randomElementCollection, randomElementCollectionProperty).setName("collection");     			
    	randomElementCollection.addToMany(randomElement, randomElementCollectionProperty).setName("listElements");
    }
    
    private static void addRandomElementAttributes (Entity randomElement) {
    	randomElement.addLongProperty("id").primaryKey();
    	randomElement.addStringProperty("name");    	
    	randomElement.addStringProperty("imageName");
    }
    

    private static void addRandomElementCollectionAttributes (Entity randomElementCollection) {
    	randomElementCollection.addLongProperty("id").primaryKey();
    	randomElementCollection.addStringProperty("name");    	
    	randomElementCollection.addIntProperty("nSelection").notNull();
    	randomElementCollection.addIntProperty("shouldAllowRepeat").notNull();    	
    }
    
    
//    private static void addSectionAttributes (Entity section) {
//    	section.addLongProperty("idx").primaryKey();
//    	section.addIntProperty("sortID").notNull();
//    	section.addStringProperty("name");
//    	section.addStringProperty("html");
//    	section.addStringProperty("image");
//    	section.addStringProperty("topLevelImage");
//    	section.addStringProperty("subText");
//    	section.addIntProperty("isHeroBanner").notNull();
//    	section.addIntProperty("isHidden").notNull();
//    	section.addIntProperty("isTopLevel").notNull();
//    	section.addIntProperty("accessLevel").notNull();
//    	section.addIntProperty("allowNotes").notNull();
//    	section.addIntProperty("allowShare").notNull();
//    	section.addStringProperty("metaData");
//    	section.addIntProperty("typeID");
//    	
//    	//Topics
//    	section.addStringProperty("predicate");
//    	
//    	//Twitter
//    	section.addStringProperty("hashTag");
//    	section.addStringProperty("shareText");
//    	section.addStringProperty("shareImage");
//    	
//    	section.addIntProperty("isdeleted").notNull();
//    	section.addStringProperty("displayName");
//    }
//    
//    
//    
//    private static void addTopicAttributes (Entity topic) {    	
//    	topic.addLongProperty("topicID").primaryKey();
//    	topic.addStringProperty("startTime");
//    	topic.addStringProperty("endTime");
//    	
//    	addContentObjectAttributes (topic, true);
//    	
//    	
//    }
//    
//    
//    private static void addPersonAttributes (Entity person) {    	
//    	person.addLongProperty("personID").primaryKey();
//    	person.addStringProperty("jobTitle");
//    	person.addStringProperty("salutation");
//    	person.addStringProperty("familyName");
//    	person.addStringProperty("givenName");
//    	person.addStringProperty("company");
//    	person.addStringProperty("address");
//    	person.addStringProperty("email");
//    	person.addStringProperty("faxNumber");
//    	person.addStringProperty("mobileNumber");
//    	person.addStringProperty("homeNumber");
//    	person.addStringProperty("thumbnail");
//    	person.addStringProperty("bio");
//    	person.addStringProperty("shareableFields");
//    	person.addIntProperty("allowContactExchange").notNull();
//    	person.addIntProperty("friendState").notNull();
//    	
//    	addContentObjectAttributes (person, false);
//    }
//    
//    
//    private static void addPageAttributes (Entity page) {    	
//    	page.addLongProperty("pageID").primaryKey();
//    	page.addStringProperty("url");
//    	page.addStringProperty("fileList");
//    	page.addStringProperty("html");
//    	page.addStringProperty("notes");
//    	page.addIntProperty("allowNavBar").notNull();
//    	addContentObjectAttributes (page, true);
//    }
//    
//    
//    private static void addCommentAttributes (Entity comment) {
//    	comment.addLongProperty("commentID").primaryKey();
//    	comment.addStringProperty("timeStamp");
//    	comment.addStringProperty("htmlContent");
//    	addContentObjectAttributes (comment, true);
//    }
//    
//    
//    private static void addAnnouncementAttributes (Entity announcement) {
//    	announcement.addLongProperty("announcementID").primaryKey();
//    	announcement.addStringProperty("timeStamp");
//    	announcement.addStringProperty("content");
//    	announcement.addIntProperty("isAlwaysShow").notNull();
//    	addContentObjectAttributes (announcement, true);
//    }
//    
//    private static void addNoteAttributes (Entity note) {
////    	note.addLongProperty("noteID").primaryKey().autoincrement();
////    	note.addStringProperty("timeStamp");
////    	note.addStringProperty("body");
////    	note.addStringProperty("tag");
//    	
//    	note.addLongProperty("noteID").primaryKey().autoincrement();
//    	note.addStringProperty("guid");
//    	note.addStringProperty("content");
//    	note.addLongProperty("timeStamp").notNull();
//    	note.addStringProperty("tag");
//    	note.addStringProperty("applicationData");
//    	note.addStringProperty("sectionName");
//    	
//    	addContentObjectAttributes (note, true);
//    }
//    
//    
//    private static void addMatchMakerAttributes (Entity matchMaker) {
//    	matchMaker.addLongProperty("ID").primaryKey().autoincrement();
//    	matchMaker.addLongProperty("requesterID").notNull();
//    	matchMaker.addLongProperty("requesteeID").notNull();
//    	matchMaker.addIntProperty("state").notNull();
//    }
//    
//    
//    
//    private static void addContentObjectAttributes (Entity contentObject, boolean shouldAddTitle) {
//    	if (shouldAddTitle)
//    		contentObject.addStringProperty("title");
//    	contentObject.addStringProperty("image");
//    	contentObject.addIntProperty("sortID").notNull();
//    	contentObject.addStringProperty("metaData");
//    	contentObject.addIntProperty("shouldAddBadge").notNull();
//    	contentObject.addIntProperty("isdeleted").notNull();
//    	contentObject.addIntProperty("allowNotes").notNull();
//    	contentObject.addIntProperty("allowShare").notNull();
//    	contentObject.addIntProperty("isHidden").notNull();
//    }
//    
//    
//    private static void addDeviceAttributes (Entity device) {
//    	device.addLongProperty("ID").primaryKey().autoincrement();
//    	device.addStringProperty("deviceID").unique();
//    	device.addStringProperty("pushToken");
//    	device.addIntProperty("isLoggedIn").notNull();
//    	device.addIntProperty("isdeleted").notNull();
//    }
//    
//    
//    private static void addMobileUserAttributes (Entity mobileUser) {
//    	mobileUser.addLongProperty("ID").primaryKey().autoincrement();
//    	mobileUser.addStringProperty("twitterAccount");
//    	mobileUser.addStringProperty("twitterPassword");
//    	mobileUser.addStringProperty("linkedInAccount");
//    	mobileUser.addStringProperty("linkedInPassword");
//    	mobileUser.addStringProperty("everNoteAccount");
//    	mobileUser.addStringProperty("everNotePassword");
//    	mobileUser.addIntProperty("isLoggedIn");
//    }
//    
//    
//    
//    
//
//    private static void addSection__TopicAttributes (Entity section__topic) {
//    	section__topic.addLongProperty("id").primaryKey().autoincrement();
//    	section__topic.addIntProperty("isdeleted").notNull();
//    	section__topic.addIntProperty("sortID").notNull();
//    }
//    
//    
//    private static void addSection__PersonAttributes (Entity section__person) {
//    	section__person.addLongProperty("id").primaryKey().autoincrement();
//    	section__person.addIntProperty("isdeleted").notNull();
//    	section__person.addIntProperty("sortID").notNull();
//    }
//    
//    
//    private static void addSection__PageAttributes (Entity section__page) {
//    	section__page.addLongProperty("id").primaryKey().autoincrement();
//    	section__page.addIntProperty("isdeleted").notNull();    	
//    	section__page.addIntProperty("sortID").notNull();
//    }
//    
//    
//    private static void addSection__SectionAttributes (Entity section__section) {
//    	section__section.addLongProperty("id").primaryKey().autoincrement();
//    	section__section.addIntProperty("isdeleted").notNull();    	
//    	section__section.addIntProperty("sortID").notNull();    	
//    }
//    
//    
//    private static void addAgendaAttributes (Entity agenda) {
//    	agenda.addLongProperty("agendaID").primaryKey();
//    	agenda.addIntProperty("isdeleted").notNull();    	
//    	agenda.addIntProperty("sortID").notNull();
//    }    
//    
//    
//    private static void addAnalyticAttributes (Entity analytic)
//    {
//    	analytic.addLongProperty("analyticsID").primaryKey();
//    	analytic.addStringProperty("deviceID");
//    	analytic.addStringProperty("analyticsType");    	
//    	analytic.addStringProperty("value");
//    	analytic.addStringProperty("timeStamp");
//    	analytic.addStringProperty("deviceOS");
//    	analytic.addStringProperty("deviceCountryCode");
//    	analytic.addLongProperty("personID");
//    }
    
}
