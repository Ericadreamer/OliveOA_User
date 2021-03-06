package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.OvertimeApplicationApprovedOpinionList;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "OVERTIME_APPLICATION_APPROVED_OPINION_LIST".
*/
public class OvertimeApplicationApprovedOpinionListDao extends AbstractDao<OvertimeApplicationApprovedOpinionList, Void> {

    public static final String TABLENAME = "OVERTIME_APPLICATION_APPROVED_OPINION_LIST";

    /**
     * Properties of entity OvertimeApplicationApprovedOpinionList.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Oaaocid = new Property(0, String.class, "oaaocid", false, "OAAOCID");
        public final static Property Oaaopid = new Property(1, String.class, "oaaopid", false, "OAAOPID");
        public final static Property Oaid = new Property(2, String.class, "oaid", false, "OAID");
        public final static Property Eid = new Property(3, String.class, "eid", false, "EID");
        public final static Property Isapproved = new Property(4, int.class, "isapproved", false, "ISAPPROVED");
        public final static Property Opinion = new Property(5, String.class, "opinion", false, "OPINION");
        public final static Property Orderby = new Property(6, int.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(7, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(8, long.class, "updatetime", false, "UPDATETIME");
    }


    public OvertimeApplicationApprovedOpinionListDao(DaoConfig config) {
        super(config);
    }
    
    public OvertimeApplicationApprovedOpinionListDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"OVERTIME_APPLICATION_APPROVED_OPINION_LIST\" (" + //
                "\"OAAOCID\" TEXT," + // 0: oaaocid
                "\"OAAOPID\" TEXT," + // 1: oaaopid
                "\"OAID\" TEXT," + // 2: oaid
                "\"EID\" TEXT," + // 3: eid
                "\"ISAPPROVED\" INTEGER NOT NULL ," + // 4: isapproved
                "\"OPINION\" TEXT," + // 5: opinion
                "\"ORDERBY\" INTEGER NOT NULL ," + // 6: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 7: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 8: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"OVERTIME_APPLICATION_APPROVED_OPINION_LIST\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, OvertimeApplicationApprovedOpinionList entity) {
        stmt.clearBindings();
 
        String oaaocid = entity.getOaaocid();
        if (oaaocid != null) {
            stmt.bindString(1, oaaocid);
        }
 
        String oaaopid = entity.getOaaopid();
        if (oaaopid != null) {
            stmt.bindString(2, oaaopid);
        }
 
        String oaid = entity.getOaid();
        if (oaid != null) {
            stmt.bindString(3, oaid);
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
    protected final void bindValues(SQLiteStatement stmt, OvertimeApplicationApprovedOpinionList entity) {
        stmt.clearBindings();
 
        String oaaocid = entity.getOaaocid();
        if (oaaocid != null) {
            stmt.bindString(1, oaaocid);
        }
 
        String oaaopid = entity.getOaaopid();
        if (oaaopid != null) {
            stmt.bindString(2, oaaopid);
        }
 
        String oaid = entity.getOaid();
        if (oaid != null) {
            stmt.bindString(3, oaid);
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
    public OvertimeApplicationApprovedOpinionList readEntity(Cursor cursor, int offset) {
        OvertimeApplicationApprovedOpinionList entity = new OvertimeApplicationApprovedOpinionList( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // oaaocid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // oaaopid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // oaid
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
    public void readEntity(Cursor cursor, OvertimeApplicationApprovedOpinionList entity, int offset) {
        entity.setOaaocid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setOaaopid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setOaid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setEid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setIsapproved(cursor.getInt(offset + 4));
        entity.setOpinion(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOrderby(cursor.getInt(offset + 6));
        entity.setCreatetime(cursor.getLong(offset + 7));
        entity.setUpdatetime(cursor.getLong(offset + 8));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(OvertimeApplicationApprovedOpinionList entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(OvertimeApplicationApprovedOpinionList entity) {
        return null;
    }

    @Override
    public boolean hasKey(OvertimeApplicationApprovedOpinionList entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
