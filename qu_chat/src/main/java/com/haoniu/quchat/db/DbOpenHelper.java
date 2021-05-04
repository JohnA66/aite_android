/**
 * Copyright (C) 2016 Hyphenate Inc. All rights reserved.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.haoniu.quchat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.haoniu.quchat.global.UserComm;

public class DbOpenHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 6;
    private static DbOpenHelper instance;
    private static final String USERNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private static final String APPLY_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME_APPLY + " ("
            + UserDao.APPLY_ISREAD + " TEXT, "
            + UserDao.APPLY_TYPE + " TEXT, "
            + UserDao.APPLY_ID + " TEXT PRIMARY KEY);";

    private static final String MSG_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_NAME_MSG + " ("
            + UserDao.MSG_ISREAD + " TEXT, "
            + UserDao.MSG_TYPE + " TEXT, "
            + UserDao.MSG_ID + " TEXT PRIMARY KEY);";

    private static final String ALLUSERNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.ALL_USERS_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_TYPE + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private static final String GROUPS_TABLE_CREATE = "CREATE TABLE "
            + UserDao.GROUPS_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_NAME_TYPE + " TEXT, "
            + UserDao.COLUMN_NAME_GROUP_TYPE + " TEXT, "
            + UserDao.COLUMN_SAY_FLAG + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT PRIMARY KEY);";

    private static final String INIVTE_MESSAGE_TABLE_CREATE = "CREATE TABLE "
            + InviteMessgeDao.TABLE_NAME + " ("
            + InviteMessgeDao.COLUMN_NAME_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + InviteMessgeDao.COLUMN_NAME_FROM + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_ID + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUP_Name + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_REASON + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_STATUS + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_ISINVITEFROMME + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_UNREAD_MSG_COUNT + " INTEGER, "
            + InviteMessgeDao.COLUMN_NAME_TIME + " TEXT, "
            + InviteMessgeDao.COLUMN_NAME_GROUPINVITER + " TEXT); ";

    private static final String ROBOT_TABLE_CREATE = "CREATE TABLE "
            + UserDao.ROBOT_TABLE_NAME + " ("
            + UserDao.ROBOT_COLUMN_NAME_ID + " TEXT PRIMARY KEY, "
            + UserDao.ROBOT_COLUMN_NAME_NICK + " TEXT, "
            + UserDao.ROBOT_COLUMN_NAME_AVATAR + " TEXT);";

    private static final String CREATE_PREF_TABLE = "CREATE TABLE "
            + UserDao.PREF_TABLE_NAME + " ("
            + UserDao.COLUMN_NAME_DISABLED_GROUPS + " TEXT, "
            + UserDao.COLUMN_NAME_DISABLED_IDS + " TEXT);";


    /**
     * 群成员列表
     */
    private static final String GROUPS_USER_LIST_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_GROUP_USER_LIST + " ("//数据库名
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.COLUMN_NAME_AVATAR + " TEXT, "
            + UserDao.COLUMN_USER_ID + " TEXT, "
            + UserDao.COLUMN_USER_RANK + " TEXT, "
            + UserDao.COLUMN_NAME_ID + " TEXT);";

    /**
     * 会话信息表（聊天背景）
     */
    private static final String CONVERSION_LIST_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_CONVERSION_LIST_BG + " ("
            + UserDao.COLUMN_SINGLE_CHAT_PATH + " TEXT, "
            + UserDao.COLUMN_CONVERSION_MSY_IS_FREE + " TEXT, "
            + UserDao.COLUMN_CONVERSION_IS_TOP + " TEXT, "
            + UserDao.COLUMN_CONVERSION_ID + " TEXT PRIMARY KEY);";


    /**
     * 群中用户的昵称
     */
    private static final String GROUP_USER_NICKNAME_TABLE_CREATE = "CREATE TABLE "
            + UserDao.TABLE_GROUP_USER_NICK + " ("
            + "id INTEGER PRIMARY KEY AUTOINCREMENT, "
            + UserDao.COLUMN_NAME_ID + " TEXT, "
            + UserDao.COLUMN_NAME_NICK + " TEXT, "
            + UserDao.GROUP_ID + " TEXT )";




    private DbOpenHelper(Context context) {
        super(context, getUserDatabaseName(), null, DATABASE_VERSION);
    }

    public static DbOpenHelper getInstance(Context context) {
        if (instance == null) {
            instance = new DbOpenHelper(context.getApplicationContext());
        }
        return instance;
    }

    private static String getUserDatabaseName() {
        return UserComm.getUserId() + "friendwhat.db";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ALLUSERNAME_TABLE_CREATE);
        db.execSQL(APPLY_TABLE_CREATE);
        db.execSQL(MSG_TABLE_CREATE);
        db.execSQL(GROUPS_TABLE_CREATE);
        db.execSQL(USERNAME_TABLE_CREATE);
        db.execSQL(INIVTE_MESSAGE_TABLE_CREATE);
        db.execSQL(CREATE_PREF_TABLE);
        db.execSQL(GROUPS_USER_LIST_TABLE_CREATE);
        db.execSQL(CONVERSION_LIST_TABLE_CREATE);
        db.execSQL(ROBOT_TABLE_CREATE);
        db.execSQL(GROUP_USER_NICKNAME_TABLE_CREATE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 3) {
            db.execSQL("ALTER TABLE " + UserDao.GROUPS_TABLE_NAME + " ADD COLUMN " +
                    UserDao.COLUMN_NAME_TYPE + " TEXT;");
        }
        if (oldVersion < 4) {
            db.execSQL("ALTER TABLE " + UserDao.GROUPS_TABLE_NAME + " ADD COLUMN " +
                    UserDao.COLUMN_NAME_GROUP_TYPE + " TEXT;");
        }
    }

    public void closeDB() {
        if (instance != null) {
            try {
                SQLiteDatabase db = instance.getWritableDatabase();
                db.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            instance = null;
        }
    }

}
