package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.IssueWorkMember;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "ISSUE_WORK_MEMBER".
*/
public class IssueWorkMemberDao extends AbstractDao<IssueWorkMember, Void> {

    public static final String TABLENAME = "ISSUE_WORK_MEMBER";

    /**
     * Properties of entity IssueWorkMember.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Iwmid = new Property(0, String.class, "iwmid", false, "IWMID");
        public final static Property Iwid = new Property(1, String.class, "iwid", false, "IWID");
        public final static Property Eid = new Property(2, String.class, "eid", false, "EID");
        public final static Property Orderby = new Property(3, Integer.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(4, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(5, long.class, "updatetime", false, "UPDATETIME");
    }


    public IssueWorkMemberDao(DaoConfig config) {
        super(config);
    }
    
    public IssueWorkMemberDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"ISSUE_WORK_MEMBER\" (" + //
                "\"IWMID\" TEXT," + // 0: iwmid
                "\"IWID\" TEXT," + // 1: iwid
                "\"EID\" TEXT," + // 2: eid
                "\"ORDERBY\" INTEGER," + // 3: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 4: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 5: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"ISSUE_WORK_MEMBER\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, IssueWorkMember entity) {
        stmt.clearBindings();
 
        String iwmid = entity.getIwmid();
        if (iwmid != null) {
            stmt.bindString(1, iwmid);
        }
 
        String iwid = entity.getIwid();
        if (iwid != null) {
            stmt.bindString(2, iwid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(3, eid);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(4, orderby);
        }
        stmt.bindLong(5, entity.getCreatetime());
        stmt.bindLong(6, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, IssueWorkMember entity) {
        stmt.clearBindings();
 
        String iwmid = entity.getIwmid();
        if (iwmid != null) {
            stmt.bindString(1, iwmid);
        }
 
        String iwid = entity.getIwid();
        if (iwid != null) {
            stmt.bindString(2, iwid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(3, eid);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(4, orderby);
        }
        stmt.bindLong(5, entity.getCreatetime());
        stmt.bindLong(6, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public IssueWorkMember readEntity(Cursor cursor, int offset) {
        IssueWorkMember entity = new IssueWorkMember( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // iwmid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // iwid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // eid
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // orderby
            cursor.getLong(offset + 4), // createtime
            cursor.getLong(offset + 5) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, IssueWorkMember entity, int offset) {
        entity.setIwmid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setIwid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setOrderby(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setCreatetime(cursor.getLong(offset + 4));
        entity.setUpdatetime(cursor.getLong(offset + 5));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(IssueWorkMember entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(IssueWorkMember entity) {
        return null;
    }

    @Override
    public boolean hasKey(IssueWorkMember entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
