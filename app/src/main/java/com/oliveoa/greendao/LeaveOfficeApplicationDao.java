package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.LeaveOfficeApplication;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LEAVE_OFFICE_APPLICATION".
*/
public class LeaveOfficeApplicationDao extends AbstractDao<LeaveOfficeApplication, Void> {

    public static final String TABLENAME = "LEAVE_OFFICE_APPLICATION";

    /**
     * Properties of entity LeaveOfficeApplication.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Loaid = new Property(0, String.class, "loaid", false, "LOAID");
        public final static Property Eid = new Property(1, String.class, "eid", false, "EID");
        public final static Property Leavetime = new Property(2, long.class, "leavetime", false, "LEAVETIME");
        public final static Property Reason = new Property(3, String.class, "reason", false, "REASON");
        public final static Property HandoverMatters = new Property(4, String.class, "handoverMatters", false, "HANDOVER_MATTERS");
        public final static Property Orderby = new Property(5, Integer.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(6, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(7, long.class, "updatetime", false, "UPDATETIME");
    }


    public LeaveOfficeApplicationDao(DaoConfig config) {
        super(config);
    }
    
    public LeaveOfficeApplicationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LEAVE_OFFICE_APPLICATION\" (" + //
                "\"LOAID\" TEXT," + // 0: loaid
                "\"EID\" TEXT," + // 1: eid
                "\"LEAVETIME\" INTEGER NOT NULL ," + // 2: leavetime
                "\"REASON\" TEXT," + // 3: reason
                "\"HANDOVER_MATTERS\" TEXT," + // 4: handoverMatters
                "\"ORDERBY\" INTEGER," + // 5: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 6: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 7: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LEAVE_OFFICE_APPLICATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LeaveOfficeApplication entity) {
        stmt.clearBindings();
 
        String loaid = entity.getLoaid();
        if (loaid != null) {
            stmt.bindString(1, loaid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
        stmt.bindLong(3, entity.getLeavetime());
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(4, reason);
        }
 
        String handoverMatters = entity.getHandoverMatters();
        if (handoverMatters != null) {
            stmt.bindString(5, handoverMatters);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(6, orderby);
        }
        stmt.bindLong(7, entity.getCreatetime());
        stmt.bindLong(8, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LeaveOfficeApplication entity) {
        stmt.clearBindings();
 
        String loaid = entity.getLoaid();
        if (loaid != null) {
            stmt.bindString(1, loaid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
        stmt.bindLong(3, entity.getLeavetime());
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(4, reason);
        }
 
        String handoverMatters = entity.getHandoverMatters();
        if (handoverMatters != null) {
            stmt.bindString(5, handoverMatters);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(6, orderby);
        }
        stmt.bindLong(7, entity.getCreatetime());
        stmt.bindLong(8, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public LeaveOfficeApplication readEntity(Cursor cursor, int offset) {
        LeaveOfficeApplication entity = new LeaveOfficeApplication( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // loaid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // eid
            cursor.getLong(offset + 2), // leavetime
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // reason
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // handoverMatters
            cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5), // orderby
            cursor.getLong(offset + 6), // createtime
            cursor.getLong(offset + 7) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LeaveOfficeApplication entity, int offset) {
        entity.setLoaid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLeavetime(cursor.getLong(offset + 2));
        entity.setReason(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setHandoverMatters(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setOrderby(cursor.isNull(offset + 5) ? null : cursor.getInt(offset + 5));
        entity.setCreatetime(cursor.getLong(offset + 6));
        entity.setUpdatetime(cursor.getLong(offset + 7));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(LeaveOfficeApplication entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(LeaveOfficeApplication entity) {
        return null;
    }

    @Override
    public boolean hasKey(LeaveOfficeApplication entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}