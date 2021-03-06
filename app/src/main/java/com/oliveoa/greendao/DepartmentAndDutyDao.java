package com.oliveoa.greendao;

import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.oliveoa.pojo.DepartmentAndDuty;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "DEPARTMENT_AND_DUTY".
*/
public class DepartmentAndDutyDao extends AbstractDao<DepartmentAndDuty, Void> {

    public static final String TABLENAME = "DEPARTMENT_AND_DUTY";

    /**
     * Properties of entity DepartmentAndDuty.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Dcid = new Property(0, String.class, "dcid", false, "DCID");
        public final static Property Dpname = new Property(1, String.class, "dpname", false, "DPNAME");
        public final static Property Pcid = new Property(2, String.class, "pcid", false, "PCID");
        public final static Property Pname = new Property(3, String.class, "pname", false, "PNAME");
    }


    public DepartmentAndDutyDao(DaoConfig config) {
        super(config);
    }
    
    public DepartmentAndDutyDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"DEPARTMENT_AND_DUTY\" (" + //
                "\"DCID\" TEXT," + // 0: dcid
                "\"DPNAME\" TEXT," + // 1: dpname
                "\"PCID\" TEXT," + // 2: pcid
                "\"PNAME\" TEXT);"); // 3: pname
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"DEPARTMENT_AND_DUTY\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, DepartmentAndDuty entity) {
        stmt.clearBindings();
 
        String dcid = entity.getDcid();
        if (dcid != null) {
            stmt.bindString(1, dcid);
        }
 
        String dpname = entity.getDpname();
        if (dpname != null) {
            stmt.bindString(2, dpname);
        }
 
        String pcid = entity.getPcid();
        if (pcid != null) {
            stmt.bindString(3, pcid);
        }
 
        String pname = entity.getPname();
        if (pname != null) {
            stmt.bindString(4, pname);
        }
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, DepartmentAndDuty entity) {
        stmt.clearBindings();
 
        String dcid = entity.getDcid();
        if (dcid != null) {
            stmt.bindString(1, dcid);
        }
 
        String dpname = entity.getDpname();
        if (dpname != null) {
            stmt.bindString(2, dpname);
        }
 
        String pcid = entity.getPcid();
        if (pcid != null) {
            stmt.bindString(3, pcid);
        }
 
        String pname = entity.getPname();
        if (pname != null) {
            stmt.bindString(4, pname);
        }
    }

    @Override
    public Void readKey(Cursor cursor, int offset) {
        return null;
    }    

    @Override
    public DepartmentAndDuty readEntity(Cursor cursor, int offset) {
        DepartmentAndDuty entity = new DepartmentAndDuty( //
            cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0), // dcid
            cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1), // dpname
            cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2), // pcid
            cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3) // pname
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, DepartmentAndDuty entity, int offset) {
        entity.setDcid(cursor.isNull(offset + 0) ? null : cursor.getString(offset + 0));
        entity.setDpname(cursor.isNull(offset + 1) ? null : cursor.getString(offset + 1));
        entity.setPcid(cursor.isNull(offset + 2) ? null : cursor.getString(offset + 2));
        entity.setPname(cursor.isNull(offset + 3) ? null : cursor.getString(offset + 3));
     }
    
    @Override
    protected final Void updateKeyAfterInsert(DepartmentAndDuty entity, long rowId) {
        // Unsupported or missing PK type
        return null;
    }
    
    @Override
    public Void getKey(DepartmentAndDuty entity) {
        return null;
    }

    @Override
    public boolean hasKey(DepartmentAndDuty entity) {
        // TODO
        return false;
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
}
