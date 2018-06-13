package com.oliveoa.util;

import com.oliveoa.greendao.BusinessTripApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.IssueWorkDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.MessageDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.ContactInfo;

public class EntityManager {
    private static EntityManager entityManager;
    public ContactInfoDao userDao;
    public DepartmentInfoDao departmentInfoDao;
    public DutyInfoDao dutyInfoDao;
    public MessageDao messageDao;
    public OvertimeApplicationDao overtimeApplicationDao;
    public OvertimeApplicationApprovedOpinionListDao overtimeApplicationApprovedOpinionListDao;
    public LeaveApplicationDao leaveApplicationDao;
    public LeaveApplicationApprovedOpinionListDao leaveApplicationApprovedOpinionListDao;
    public BusinessTripApplicationDao businessTripApplicationDao;
    public BusinessTripApplicationApprovedOpinionListDao businessTripApplicationApprovedOpinionList;
    public WorkDetailDao workDetailDao;
    public IssueWorkDao issueWorkDao;


    /**
     * 创建User表实例
     *
     * @return
     */
    public ContactInfoDao getContactInfo(){
        userDao = DaoManager.getInstance().getSession().getContactInfoDao();
        return userDao;
    }

    /**
     * 创建Message表实例
     *
     * @return
     */
    public MessageDao getMessageInfo(){
        messageDao = DaoManager.getInstance().getSession().getMessageDao();
        return messageDao;
    }

    /**
     * 创建Department表实例
     *
     * @return
     */
    public DepartmentInfoDao getDepartmentInfo(){
        departmentInfoDao = DaoManager.getInstance().getSession().getDepartmentInfoDao();
        return departmentInfoDao;
    }

    /**
     * 创建DutyInfo表实例
     *
     * @return
     */
    public DutyInfoDao getDutyInfoInfo(){
        dutyInfoDao = DaoManager.getInstance().getSession().getDutyInfoDao();
        return dutyInfoDao;
    }

    /**
     * 创建LeaveApplication表实例
     *
     * @return
     */
    public LeaveApplicationDao getLeaveApplicationInfo(){
        leaveApplicationDao = DaoManager.getInstance().getSession().getLeaveApplicationDao();
        return leaveApplicationDao;
    }

    /**
     * 创建LeaveApplicationApprovedOpinionList表实例
     *
     * @return
     */
    public LeaveApplicationApprovedOpinionListDao getLeaveApplicationApprovedOpinionListInfo(){
        leaveApplicationApprovedOpinionListDao = DaoManager.getInstance().getSession().getLeaveApplicationApprovedOpinionListDao();
        return leaveApplicationApprovedOpinionListDao;
    }

    /**
     * 创建BusinessTripApplication表实例
     *
     * @return
     */
    public BusinessTripApplicationDao getBusinessTripApplicationInfo(){
        businessTripApplicationDao = DaoManager.getInstance().getSession().getBusinessTripApplicationDao();
        return businessTripApplicationDao;
    }

    /**
     * 创建BusinessTripApplicationApprovedOpinionList表实例
     *
     * @return
     */
    public BusinessTripApplicationApprovedOpinionListDao getBusinessTripApplicationApprovedOpinionListInfo(){
       businessTripApplicationApprovedOpinionList = DaoManager.getInstance().getSession().getBusinessTripApplicationApprovedOpinionListDao();
        return businessTripApplicationApprovedOpinionList;
    }

    /**
     * 创建OvertimeApplication表实例
     *
     * @return
     */
    public OvertimeApplicationDao getOvertimeApplicationInfo(){
        overtimeApplicationDao = DaoManager.getInstance().getSession().getOvertimeApplicationDao();
        return overtimeApplicationDao;
    }

    /**
     * 创建OvertimeApplicationApprovedOpinionList表实例
     *
     * @return
     */
    public OvertimeApplicationApprovedOpinionListDao getOvertimeApplicationApprovedOpinionListInfo(){
        overtimeApplicationApprovedOpinionListDao = DaoManager.getInstance().getSession().getOvertimeApplicationApprovedOpinionListDao();
        return overtimeApplicationApprovedOpinionListDao;
    }

    /**
     * 创建IssueWork表实例
     *
     * @return
     */
    public IssueWorkDao getIssueWorkInfo(){
        issueWorkDao = DaoManager.getInstance().getSession().getIssueWorkDao();
        return issueWorkDao;
    }

    /**
     * 创建WorkDetail表实例
     *
     * @return
     */
    public WorkDetailDao getWorkDetailInfo(){
        workDetailDao = DaoManager.getInstance().getSession().getWorkDetailDao();
        return workDetailDao;
    }

    /**
     * 创建单例
     *
     * @return
     */
    public static EntityManager getInstance() {
        if (entityManager == null) {
            entityManager = new EntityManager();
        }
        return entityManager;
    }
}
