package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.IssueWork;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ISSUE_WORK".
*/
public class IssueWorkDao extends AbstractDao<IssueWork, Void> {

    public static final String TABLENAME = "ISSUE_WORK";

    /**
     * Properties of entity IssueWork.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Iwid = new Property(0, String.class, "iwid", false, "IWID");
        public final static Property Eid = new Property(1, String.class, "eid", false, "EID");
        public final static Property Content = new Property(2, String.class, "content", false, "CONTENT");
        public final static Property Begintime = new Property(3, long.class, "begintime", false, "BEGINTIME");
        public final static Property Endtime = new Property(4, long.class, "endtime", false, "ENDTIME");
        public final static Property Orderby = new Property(5, int.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(6, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(7, long.class, "updatetime", false, "UPDATETIME");
    }


    public IssueWorkDao(DaoConfig config) {
        super(config);
    }
    
    public IssueWorkDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ISSUE_WORK\" (" + //
                "\"IWID\" TEXT," + // 0: iwid
                "\"EID\" TEXT," + // 1: eid
                "\"CONTENT\" TEXT," + // 2: content
                "\"BEGINTIME\" INTEGER NOT NULL ," + // 3: begintime
                "\"ENDTIME\" INTEGER NOT NULL ," + // 4: endtime
                "\"ORDERBY\" INTEGER NOT NULL ," + // 5: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 6: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 7: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ISSUE_WORK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IssueWork entity) {
        stmt.clearBindings();
 
        String iwid = entity.getIwid();
        if (iwid != null) {
            stmt.bindString(1, iwid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getBegintime());
        stmt.bindLong(5, entity.getEndtime());
        stmt.bindLong(6, entity.getOrderby());
        stmt.bindLong(7, entity.getCreatetime());
        stmt.bindLong(8, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IssueWork entity) {
        stmt.clearBindings();
 
        String iwid = entity.getIwid();
        if (iwid != null) {
            stmt.bindString(1, iwid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(3, content);
        }
        stmt.bindLong(4, entity.getBegintime());
        stmt.bindLong(5, entity.getEndtime());
        stmt.bindLong(6, entity.getOrderby());
        stmt.bindLong(7, entity.getCreatetime());
        stmt.bindLong(8, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public IssueWork readEntity(Cursor cursor, int offset) {
        IssueWork entity = new IssueWork( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // iwid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // eid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // content
            cursor.getLong(offset + 3), // begintime
            cursor.getLong(offset + 4), // endtime
            cursor.getInt(offset + 5), // orderby
            cursor.getLong(offset + 6), // createtime
            cursor.getLong(offset + 7) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IssueWork entity, int offset) {
        entity.setIwid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setContent(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setBegintime(cursor.getLong(offset + 3));
        entity.setEndtime(cursor.getLong(offset + 4));
        entity.setOrderby(cursor.getInt(offset + 5));
        entity.setCreatetime(cursor.getLong(offset + 6));
        entity.setUpdatetime(cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(IssueWork entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(IssueWork entity) {
        return null;
    }

    @Override
    public boolean hasKey(IssueWork entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
