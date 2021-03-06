package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.LeaveApplication;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LEAVE_APPLICATION".
*/
public class LeaveApplicationDao extends AbstractDao<LeaveApplication, Void> {

    public static final String TABLENAME = "LEAVE_APPLICATION";

    /**
     * Properties of entity LeaveApplication.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Laid = new Property(0, String.class, "laid", false, "LAID");
        public final static Property Eid = new Property(1, String.class, "eid", false, "EID");
        public final static Property Begintime = new Property(2, long.class, "begintime", false, "BEGINTIME");
        public final static Property Endtime = new Property(3, long.class, "endtime", false, "ENDTIME");
        public final static Property Reason = new Property(4, String.class, "reason", false, "REASON");
        public final static Property Type = new Property(5, int.class, "type", false, "TYPE");
        public final static Property NormalRest = new Property(6, long.class, "normalRest", false, "NORMAL_REST");
        public final static Property SwapRest = new Property(7, long.class, "swapRest", false, "SWAP_REST");
        public final static Property ShouldRest = new Property(8, long.class, "shouldRest", false, "SHOULD_REST");
        public final static Property SupplementRest = new Property(9, long.class, "supplementRest", false, "SUPPLEMENT_REST");
        public final static Property Orderby = new Property(10, int.class, "orderby", false, "ORDERBY");
    }


    public LeaveApplicationDao(DaoConfig config) {
        super(config);
    }
    
    public LeaveApplicationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LEAVE_APPLICATION\" (" + //
                "\"LAID\" TEXT," + // 0: laid
                "\"EID\" TEXT," + // 1: eid
                "\"BEGINTIME\" INTEGER NOT NULL ," + // 2: begintime
                "\"ENDTIME\" INTEGER NOT NULL ," + // 3: endtime
                "\"REASON\" TEXT," + // 4: reason
                "\"TYPE\" INTEGER NOT NULL ," + // 5: type
                "\"NORMAL_REST\" INTEGER NOT NULL ," + // 6: normalRest
                "\"SWAP_REST\" INTEGER NOT NULL ," + // 7: swapRest
                "\"SHOULD_REST\" INTEGER NOT NULL ," + // 8: shouldRest
                "\"SUPPLEMENT_REST\" INTEGER NOT NULL ," + // 9: supplementRest
                "\"ORDERBY\" INTEGER NOT NULL );"); // 10: orderby
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LEAVE_APPLICATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LeaveApplication entity) {
        stmt.clearBindings();
 
        String laid = entity.getLaid();
        if (laid != null) {
            stmt.bindString(1, laid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
        stmt.bindLong(3, entity.getBegintime());
        stmt.bindLong(4, entity.getEndtime());
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(5, reason);
        }
        stmt.bindLong(6, entity.getType());
        stmt.bindLong(7, entity.getNormalRest());
        stmt.bindLong(8, entity.getSwapRest());
        stmt.bindLong(9, entity.getShouldRest());
        stmt.bindLong(10, entity.getSupplementRest());
        stmt.bindLong(11, entity.getOrderby());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LeaveApplication entity) {
        stmt.clearBindings();
 
        String laid = entity.getLaid();
        if (laid != null) {
            stmt.bindString(1, laid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(2, eid);
        }
        stmt.bindLong(3, entity.getBegintime());
        stmt.bindLong(4, entity.getEndtime());
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(5, reason);
        }
        stmt.bindLong(6, entity.getType());
        stmt.bindLong(7, entity.getNormalRest());
        stmt.bindLong(8, entity.getSwapRest());
        stmt.bindLong(9, entity.getShouldRest());
        stmt.bindLong(10, entity.getSupplementRest());
        stmt.bindLong(11, entity.getOrderby());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public LeaveApplication readEntity(Cursor cursor, int offset) {
        LeaveApplication entity = new LeaveApplication( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // laid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // eid
            cursor.getLong(offset + 2), // begintime
            cursor.getLong(offset + 3), // endtime
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // reason
            cursor.getInt(offset + 5), // type
            cursor.getLong(offset + 6), // normalRest
            cursor.getLong(offset + 7), // swapRest
            cursor.getLong(offset + 8), // shouldRest
            cursor.getLong(offset + 9), // supplementRest
            cursor.getInt(offset + 10) // orderby
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LeaveApplication entity, int offset) {
        entity.setLaid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setEid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setBegintime(cursor.getLong(offset + 2));
        entity.setEndtime(cursor.getLong(offset + 3));
        entity.setReason(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setType(cursor.getInt(offset + 5));
        entity.setNormalRest(cursor.getLong(offset + 6));
        entity.setSwapRest(cursor.getLong(offset + 7));
        entity.setShouldRest(cursor.getLong(offset + 8));
        entity.setSupplementRest(cursor.getLong(offset + 9));
        entity.setOrderby(cursor.getInt(offset + 10));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(LeaveApplication entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(LeaveApplication entity) {
        return null;
    }

    @Override
    public boolean hasKey(LeaveApplication entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
