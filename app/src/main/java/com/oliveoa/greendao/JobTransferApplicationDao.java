package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.JobTransferApplication;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "JOB_TRANSFER_APPLICATION".
*/
public class JobTransferApplicationDao extends AbstractDao<JobTransferApplication, Void> {

    public static final String TABLENAME = "JOB_TRANSFER_APPLICATION";

    /**
     * Properties of entity JobTransferApplication.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Jtaid = new Property(0, String.class, "jtaid", false, "JTAID");
        public final static Property Aeid = new Property(1, String.class, "aeid", false, "AEID");
        public final static Property Eid = new Property(2, String.class, "eid", false, "EID");
        public final static Property Aimdcid = new Property(3, String.class, "aimdcid", false, "AIMDCID");
        public final static Property Aimpcid = new Property(4, String.class, "aimpcid", false, "AIMPCID");
        public final static Property Reason = new Property(5, String.class, "reason", false, "REASON");
        public final static Property Orderby = new Property(6, Integer.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(7, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(8, long.class, "updatetime", false, "UPDATETIME");
    }


    public JobTransferApplicationDao(DaoConfig config) {
        super(config);
    }
    
    public JobTransferApplicationDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"JOB_TRANSFER_APPLICATION\" (" + //
                "\"JTAID\" TEXT," + // 0: jtaid
                "\"AEID\" TEXT," + // 1: aeid
                "\"EID\" TEXT," + // 2: eid
                "\"AIMDCID\" TEXT," + // 3: aimdcid
                "\"AIMPCID\" TEXT," + // 4: aimpcid
                "\"REASON\" TEXT," + // 5: reason
                "\"ORDERBY\" INTEGER," + // 6: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 7: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 8: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"JOB_TRANSFER_APPLICATION\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, JobTransferApplication entity) {
        stmt.clearBindings();
 
        String jtaid = entity.getJtaid();
        if (jtaid != null) {
            stmt.bindString(1, jtaid);
        }
 
        String aeid = entity.getAeid();
        if (aeid != null) {
            stmt.bindString(2, aeid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(3, eid);
        }
 
        String aimdcid = entity.getAimdcid();
        if (aimdcid != null) {
            stmt.bindString(4, aimdcid);
        }
 
        String aimpcid = entity.getAimpcid();
        if (aimpcid != null) {
            stmt.bindString(5, aimpcid);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(6, reason);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(7, orderby);
        }
        stmt.bindLong(8, entity.getCreatetime());
        stmt.bindLong(9, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, JobTransferApplication entity) {
        stmt.clearBindings();
 
        String jtaid = entity.getJtaid();
        if (jtaid != null) {
            stmt.bindString(1, jtaid);
        }
 
        String aeid = entity.getAeid();
        if (aeid != null) {
            stmt.bindString(2, aeid);
        }
 
        String eid = entity.getEid();
        if (eid != null) {
            stmt.bindString(3, eid);
        }
 
        String aimdcid = entity.getAimdcid();
        if (aimdcid != null) {
            stmt.bindString(4, aimdcid);
        }
 
        String aimpcid = entity.getAimpcid();
        if (aimpcid != null) {
            stmt.bindString(5, aimpcid);
        }
 
        String reason = entity.getReason();
        if (reason != null) {
            stmt.bindString(6, reason);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(7, orderby);
        }
        stmt.bindLong(8, entity.getCreatetime());
        stmt.bindLong(9, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public JobTransferApplication readEntity(Cursor cursor, int offset) {
        JobTransferApplication entity = new JobTransferApplication( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // jtaid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // aeid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // eid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // aimdcid
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // aimpcid
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // reason
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // orderby
            cursor.getLong(offset + 7), // createtime
            cursor.getLong(offset + 8) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, JobTransferApplication entity, int offset) {
        entity.setJtaid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setAeid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setEid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setAimdcid(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setAimpcid(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setReason(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setOrderby(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setCreatetime(cursor.getLong(offset + 7));
        entity.setUpdatetime(cursor.getLong(offset + 8));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(JobTransferApplication entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(JobTransferApplication entity) {
        return null;
    }

    @Override
    public boolean hasKey(JobTransferApplication entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
