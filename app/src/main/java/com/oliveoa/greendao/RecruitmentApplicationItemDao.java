package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.RecruitmentApplicationItem;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "RECRUITMENT_APPLICATION_ITEM".
*/
public class RecruitmentApplicationItemDao extends AbstractDao<RecruitmentApplicationItem, Void> {

    public static final String TABLENAME = "RECRUITMENT_APPLICATION_ITEM";

    /**
     * Properties of entity RecruitmentApplicationItem.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Raiid = new Property(0, String.class, "raiid", false, "RAIID");
        public final static Property Raid = new Property(1, String.class, "raid", false, "RAID");
        public final static Property Pcid = new Property(2, String.class, "pcid", false, "PCID");
        public final static Property Number = new Property(3, Integer.class, "number", false, "NUMBER");
        public final static Property PositionDescribe = new Property(4, String.class, "positionDescribe", false, "POSITION_DESCRIBE");
        public final static Property PositionRequest = new Property(5, String.class, "positionRequest", false, "POSITION_REQUEST");
        public final static Property Salary = new Property(6, String.class, "salary", false, "SALARY");
        public final static Property Orderby = new Property(7, Integer.class, "orderby", false, "ORDERBY");
        public final static Property Createtime = new Property(8, long.class, "createtime", false, "CREATETIME");
        public final static Property Updatetime = new Property(9, long.class, "updatetime", false, "UPDATETIME");
    }


    public RecruitmentApplicationItemDao(DaoConfig config) {
        super(config);
    }
    
    public RecruitmentApplicationItemDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"RECRUITMENT_APPLICATION_ITEM\" (" + //
                "\"RAIID\" TEXT," + // 0: raiid
                "\"RAID\" TEXT," + // 1: raid
                "\"PCID\" TEXT," + // 2: pcid
                "\"NUMBER\" INTEGER," + // 3: number
                "\"POSITION_DESCRIBE\" TEXT," + // 4: positionDescribe
                "\"POSITION_REQUEST\" TEXT," + // 5: positionRequest
                "\"SALARY\" TEXT," + // 6: salary
                "\"ORDERBY\" INTEGER," + // 7: orderby
                "\"CREATETIME\" INTEGER NOT NULL ," + // 8: createtime
                "\"UPDATETIME\" INTEGER NOT NULL );"); // 9: updatetime
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"RECRUITMENT_APPLICATION_ITEM\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, RecruitmentApplicationItem entity) {
        stmt.clearBindings();
 
        String raiid = entity.getRaiid();
        if (raiid != null) {
            stmt.bindString(1, raiid);
        }
 
        String raid = entity.getRaid();
        if (raid != null) {
            stmt.bindString(2, raid);
        }
 
        String pcid = entity.getPcid();
        if (pcid != null) {
            stmt.bindString(3, pcid);
        }
 
        Integer number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(4, number);
        }
 
        String positionDescribe = entity.getPositionDescribe();
        if (positionDescribe != null) {
            stmt.bindString(5, positionDescribe);
        }
 
        String positionRequest = entity.getPositionRequest();
        if (positionRequest != null) {
            stmt.bindString(6, positionRequest);
        }
 
        String salary = entity.getSalary();
        if (salary != null) {
            stmt.bindString(7, salary);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(8, orderby);
        }
        stmt.bindLong(9, entity.getCreatetime());
        stmt.bindLong(10, entity.getUpdatetime());
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, RecruitmentApplicationItem entity) {
        stmt.clearBindings();
 
        String raiid = entity.getRaiid();
        if (raiid != null) {
            stmt.bindString(1, raiid);
        }
 
        String raid = entity.getRaid();
        if (raid != null) {
            stmt.bindString(2, raid);
        }
 
        String pcid = entity.getPcid();
        if (pcid != null) {
            stmt.bindString(3, pcid);
        }
 
        Integer number = entity.getNumber();
        if (number != null) {
            stmt.bindLong(4, number);
        }
 
        String positionDescribe = entity.getPositionDescribe();
        if (positionDescribe != null) {
            stmt.bindString(5, positionDescribe);
        }
 
        String positionRequest = entity.getPositionRequest();
        if (positionRequest != null) {
            stmt.bindString(6, positionRequest);
        }
 
        String salary = entity.getSalary();
        if (salary != null) {
            stmt.bindString(7, salary);
        }
 
        Integer orderby = entity.getOrderby();
        if (orderby != null) {
            stmt.bindLong(8, orderby);
        }
        stmt.bindLong(9, entity.getCreatetime());
        stmt.bindLong(10, entity.getUpdatetime());
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public RecruitmentApplicationItem readEntity(Cursor cursor, int offset) {
        RecruitmentApplicationItem entity = new RecruitmentApplicationItem( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // raiid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // raid
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pcid
            cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3), // number
            cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4), // positionDescribe
            cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5), // positionRequest
            cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6), // salary
            cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7), // orderby
            cursor.getLong(offset + 8), // createtime
            cursor.getLong(offset + 9) // updatetime
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, RecruitmentApplicationItem entity, int offset) {
        entity.setRaiid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setRaid(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPcid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setNumber(cursor.isNull(offset + 3) ? null : cursor.getInt(offset + 3));
        entity.setPositionDescribe(cursor.isNull(offset + 4) ? null : cursor.getString(offset + 4));
        entity.setPositionRequest(cursor.isNull(offset + 5) ? null : cursor.getString(offset + 5));
        entity.setSalary(cursor.isNull(offset + 6) ? null : cursor.getString(offset + 6));
        entity.setOrderby(cursor.isNull(offset + 7) ? null : cursor.getInt(offset + 7));
        entity.setCreatetime(cursor.getLong(offset + 8));
        entity.setUpdatetime(cursor.getLong(offset + 9));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(RecruitmentApplicationItem entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(RecruitmentApplicationItem entity) {
        return null;
    }

    @Override
    public boolean hasKey(RecruitmentApplicationItem entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}