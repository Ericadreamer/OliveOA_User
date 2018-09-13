package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.SubmitWork;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "SUBMIT_WORK".
*/
public class SubmitWorkDao extends AbstractDao<SubmitWork, Void> {

    public static final String TABLENAME = "SUBMIT_WORK";

    /**
     * Properties of entity SubmitWork.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Swid = new Property(0, String.class, "swid", false, "SWID");
        public final static Property Seid = new Property(1, String.class, "seid", false, "SEID");
        public final static Property Aeid = new Property(2, String.class, "aeid", false, "AEID");
        public final static Property Content = new Property(3, String.class, "content", false, "CONTENT");
        public final static Property Begintime = new Property(4, long.class, "begintime", false, "BEGINTIME");
        public final static Property Endtime = new Property(5, long.class, "endtime", false, "ENDTIME");
        public final static Property Isapproved = new Property(6, Integer.class, "isapproved", false, "ISAPPROVED");
        public final static Property Opinion = new Property(7, String.class, "opinion", false, "OPINION");
        public final static Property Orderby = new Property(8, Integer.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(9, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(10, long.class, "updatetime", false, "UPDATETIME");
    }


    public SubmitWorkDao(DaoConfig config) {
        super(config);
    }
    
    public SubmitWorkDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"SUBMIT_WORK\" (" + //
                "\"SWID\" TEXT," + // 0: swid
                "\"SEID\" TEXT," + // 1: seid
                "\"AEID\" TEXT," + // 2: aeid
                "\"CONTENT\" TEXT," + // 3: content
                "\"BEGINTIME\" INTEGER NOT NULL ," + // 4: begintime
                "\"ENDTIME\" INTEGER NOT NULL ," + // 5: endtime
                "\"ISAPPROVED\" INTEGER," + // 6: isapproved
                "\"OPINION\" TEXT," + // 7: opinion
                "\"ORDERBY\" INTEGER," + // 8: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 9: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 10: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"SUBMIT_WORK\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, SubmitWork entity) {
        stmt.clearBindings();
 
        String swid = entity.getSwid();
        if (swid != null) {
            stmt.bindString(1, swid);
        }
 
        String seid = entity.getSeid();
        if (seid != null) {
            stmt.bindString(2, seid);
        }
 
        String aeid = entity.getAeid();
        if (aeid != null) {
            stmt.bindString(3, aeid);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
        stmt.bindLong(5, entity.getBegintime());
        stmt.bindLong(6, entity.getEndtime());
 
        Integer isapproved = entity.getIsapproved();
        if (isapproved != null) {
            stmt.bindLong(7, isapproved);
        }
 
        String opinion = entity.getOpinion();
        if (opinion != null) {
            stmt.bindString(8, opinion);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(9, orderby);
        }
        stmt.bindLong(10, entity.getCreatetime());
        stmt.bindLong(11, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, SubmitWork entity) {
        stmt.clearBindings();
 
        String swid = entity.getSwid();
        if (swid != null) {
            stmt.bindString(1, swid);
        }
 
        String seid = entity.getSeid();
        if (seid != null) {
            stmt.bindString(2, seid);
        }
 
        String aeid = entity.getAeid();
        if (aeid != null) {
            stmt.bindString(3, aeid);
        }
 
        String content = entity.getContent();
        if (content != null) {
            stmt.bindString(4, content);
        }
        stmt.bindLong(5, entity.getBegintime());
        stmt.bindLong(6, entity.getEndtime());
 
        Integer isapproved = entity.getIsapproved();
        if (isapproved != null) {
            stmt.bindLong(7, isapproved);
        }
 
        String opinion = entity.getOpinion();
        if (opinion != null) {
            stmt.bindString(8, opinion);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(9, orderby);
        }
        stmt.bindLong(10, entity.getCreatetime());
        stmt.bindLong(11, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public SubmitWork readEntity(Cursor cursor, int offset) {
        SubmitWork entity = new SubmitWork( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // swid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // seid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // aeid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3), // content
            cursor.getLong(offset + 4), // begintime
            cursor.getLong(offset + 5), // endtime
            cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6), // isapproved
            cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7), // opinion
            cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8), // orderby
            cursor.getLong(offset + 9), // createtime
            cursor.getLong(offset + 10) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, SubmitWork entity, int offset) {
        entity.setSwid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setSeid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setAeid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setContent(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
        entity.setBegintime(cursor.getLong(offset + 4));
        entity.setEndtime(cursor.getLong(offset + 5));
        entity.setIsapproved(cursor.isNull(offset + 6) ? null : cursor.getInt(offset + 6));
        entity.setOpinion(cursor.isNull(offset + 7) ? null : cursor.getString(offset + 7));
        entity.setOrderby(cursor.isNull(offset + 8) ? null : cursor.getInt(offset + 8));
        entity.setCreatetime(cursor.getLong(offset + 9));
        entity.setUpdatetime(cursor.getLong(offset + 10));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(SubmitWork entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(SubmitWork entity) {
        return null;
    }

    @Override
    public boolean hasKey(SubmitWork entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}