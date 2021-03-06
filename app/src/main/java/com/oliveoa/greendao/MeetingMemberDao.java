package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.MeetingMember;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "MEETING_MEMBER".
*/
public class MeetingMemberDao extends AbstractDao<MeetingMember, Void> {

    public static final String TABLENAME = "MEETING_MEMBER";

    /**
     * Properties of entity MeetingMember.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Maid = new Property(0, String.class, "maid", false, "MAID");
        public final static Property Eid = new Property(1, String.class, "eid", false, "EID");
        public final static Property Orderby = new Property(2, Integer.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(3, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(4, long.class, "updatetime", false, "UPDATETIME");
    }


    public MeetingMemberDao(DaoConfig config) {
        super(config);
    }
    
    public MeetingMemberDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"MEETING_MEMBER\" (" + //
                "\"MAID\" TEXT," + // 0: maid
                "\"EID\" TEXT," + // 1: eid
                "\"ORDERBY\" INTEGER," + // 2: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 3: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 4: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"MEETING_MEMBER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, MeetingMember entity) {
        stmt.clearBindings();
 
        String maid = entity.getMaid();
        if (maid != null) {
            stmt.bindString(1, maid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(3, orderby);
        }
        stmt.bindLong(4, entity.getCreatetime());
        stmt.bindLong(5, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, MeetingMember entity) {
        stmt.clearBindings();
 
        String maid = entity.getMaid();
        if (maid != null) {
            stmt.bindString(1, maid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(3, orderby);
        }
        stmt.bindLong(4, entity.getCreatetime());
        stmt.bindLong(5, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public MeetingMember readEntity(Cursor cursor, int offset) {
        MeetingMember entity = new MeetingMember( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // maid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // eid
            cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2), // orderby
            cursor.getLong(offset + 3), // createtime
            cursor.getLong(offset + 4) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, MeetingMember entity, int offset) {
        entity.setMaid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOrderby(cursor.isNull(offset + 2) ? null : cursor.getInt(offset + 2));
        entity.setCreatetime(cursor.getLong(offset + 3));
        entity.setUpdatetime(cursor.getLong(offset + 4));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(MeetingMember entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(MeetingMember entity) {
        return null;
    }

    @Override
    public boolean hasKey(MeetingMember entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
