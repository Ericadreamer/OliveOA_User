package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.LeaveApplicationApprovedOpinionList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "LEAVE_APPLICATION_APPROVED_OPINION_LIST".
*/
public class LeaveApplicationApprovedOpinionListDao extends AbstractDao<LeaveApplicationApprovedOpinionList, Void> {

    public static final String TABLENAME = "LEAVE_APPLICATION_APPROVED_OPINION_LIST";

    /**
     * Properties of entity LeaveApplicationApprovedOpinionList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Laaocid = new Property(0, String.class, "laaocid", false, "LAAOCID");
        public final static Property Laaopid = new Property(1, String.class, "laaopid", false, "LAAOPID");
        public final static Property Laid = new Property(2, String.class, "laid", false, "LAID");
        public final static Property Eid = new Property(3, String.class, "eid", false, "EID");
        public final static Property Isapproved = new Property(4, int.class, "isapproved", false, "ISAPPROVED");
        public final static Property Opinion = new Property(5, String.class, "opinion", false, "OPINION");
        public final static Property Orderby = new Property(6, int.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(7, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(8, long.class, "updatetime", false, "UPDATETIME");
    }


    public LeaveApplicationApprovedOpinionListDao(DaoConfig config) {
        super(config);
    }
    
    public LeaveApplicationApprovedOpinionListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"LEAVE_APPLICATION_APPROVED_OPINION_LIST\" (" + //
                "\"LAAOCID\" TEXT," + // 0: laaocid
                "\"LAAOPID\" TEXT," + // 1: laaopid
                "\"LAID\" TEXT," + // 2: laid
                "\"EID\" TEXT," + // 3: eid
                "\"ISAPPROVED\" INTEGER NOT NULL ," + // 4: isapproved
                "\"OPINION\" TEXT," + // 5: opinion
                "\"ORDERBY\" INTEGER NOT NULL ," + // 6: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 7: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 8: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"LEAVE_APPLICATION_APPROVED_OPINION_LIST\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, LeaveApplicationApprovedOpinionList entity) {
        stmt.clearBindings();
 
        String laaocid = entity.getLaaocid();
        if (laaocid != null) {
            stmt.bindString(1, laaocid);
        }
 
        String laaopid = entity.getLaaopid();
        if (laaopid != null) {
            stmt.bindString(2, laaopid);
        }
 
        String laid = entity.getLaid();
        if (laid != null) {
            stmt.bindString(3, laid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(4, eid);
        }
        stmt.bindLong(5, entity.getIsapproved());
 
        String opinion = entity.getOpinion();
        if (opinion != null) {
            stmt.bindString(6, opinion);
        }
        stmt.bindLong(7, entity.getOrderby());
        stmt.bindLong(8, entity.getCreatetime());
        stmt.bindLong(9, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, LeaveApplicationApprovedOpinionList entity) {
        stmt.clearBindings();
 
        String laaocid = entity.getLaaocid();
        if (laaocid != null) {
            stmt.bindString(1, laaocid);
        }
 
        String laaopid = entity.getLaaopid();
        if (laaopid != null) {
            stmt.bindString(2, laaopid);
        }
 
        String laid = entity.getLaid();
        if (laid != null) {
            stmt.bindString(3, laid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(4, eid);
        }
        stmt.bindLong(5, entity.getIsapproved());
 
        String opinion = entity.getOpinion();
        if (opinion != null) {
            stmt.bindString(6, opinion);
        }
        stmt.bindLong(7, entity.getOrderby());
        stmt.bindLong(8, entity.getCreatetime());
        stmt.bindLong(9, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public LeaveApplicationApprovedOpinionList readEntity(Cursor cursor, int offset) {
        LeaveApplicationApprovedOpinionList entity = new LeaveApplicationApprovedOpinionList( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // laaocid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // laaopid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // laid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // eid
            cursor.getInt(offset + 4), // isapproved
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // opinion
            cursor.getInt(offset + 6), // orderby
            cursor.getLong(offset + 7), // createtime
            cursor.getLong(offset + 8) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, LeaveApplicationApprovedOpinionList entity, int offset) {
        entity.setLaaocid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setLaaopid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setLaid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsapproved(cursor.getInt(offset + 4));
        entity.setOpinion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOrderby(cursor.getInt(offset + 6));
        entity.setCreatetime(cursor.getLong(offset + 7));
        entity.setUpdatetime(cursor.getLong(offset + 8));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(LeaveApplicationApprovedOpinionList entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(LeaveApplicationApprovedOpinionList entity) {
        return null;
    }

    @Override
    public boolean hasKey(LeaveApplicationApprovedOpinionList entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
