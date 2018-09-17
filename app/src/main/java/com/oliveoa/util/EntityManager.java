package com.oliveoa.util;

import com.oliveoa.greendao.AnnouncementApprovedOpinionListDao;
import com.oliveoa.greendao.AnnouncementInfoDao;
import com.oliveoa.greendao.ApplicationDao;
import com.oliveoa.greendao.ApproveNumberDao;
import com.oliveoa.greendao.BusinessTripApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.BusinessTripApplicationDao;
import com.oliveoa.greendao.ContactInfoDao;
import com.oliveoa.greendao.DaoManager;
import com.oliveoa.greendao.DepartmentAndDutyDao;
import com.oliveoa.greendao.DepartmentInfoDao;
import com.oliveoa.greendao.DutyInfoDao;
import com.oliveoa.greendao.FulltimeApplicationApprovedOpinionDao;
import com.oliveoa.greendao.FulltimeApplicationDao;
import com.oliveoa.greendao.IssueWorkDao;
import com.oliveoa.greendao.IssueWorkMemberDao;
import com.oliveoa.greendao.JobTransferApplicationApprovedOpinionDao;
import com.oliveoa.greendao.JobTransferApplicationDao;
import com.oliveoa.greendao.LeaveApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.LeaveApplicationDao;
import com.oliveoa.greendao.LeaveOfficeApplicationApprovedOpinionDao;
import com.oliveoa.greendao.LeaveOfficeApplicationDao;
import com.oliveoa.greendao.MeetingApplicationAndStatusDao;
import com.oliveoa.greendao.MeetingApplicationDao;
import com.oliveoa.greendao.MeetingMemberDao;
import com.oliveoa.greendao.MessageDao;
import com.oliveoa.greendao.OvertimeApplicationApprovedOpinionListDao;
import com.oliveoa.greendao.OvertimeApplicationDao;
import com.oliveoa.greendao.RecruitmentApplicationApprovedOpinionDao;
import com.oliveoa.greendao.RecruitmentApplicationItemDao;
import com.oliveoa.greendao.WorkDetailDao;
import com.oliveoa.pojo.RecruitmentApplicationItem;

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
    public ApplicationDao applicationDao;
    public AnnouncementApprovedOpinionListDao announcementApprovedOpinionListDao;
    public AnnouncementInfoDao announcementInfoDao;
    public ApproveNumberDao approveNumberDao;
    public FulltimeApplicationDao fulltimeApplicationDao;
    public FulltimeApplicationApprovedOpinionDao fulltimeApplicationApprovedOpinionDao;
    public IssueWorkMemberDao issueWorkMemberDao;
    public JobTransferApplicationDao jobTransferApplicationDao;
    public JobTransferApplicationApprovedOpinionDao jobTransferApplicationApprovedOpinionDao;
    public LeaveOfficeApplicationDao leaveOfficeApplicationDao;
    public LeaveOfficeApplicationApprovedOpinionDao leaveOfficeApplicationApprovedOpinionDao;
    public MeetingApplicationDao meetingApplicationDao;
    public MeetingMemberDao meetingMemberDao;
    public RecruitmentApplicationApprovedOpinionDao recruitmentApplicationApprovedOpinionDao;
    public RecruitmentApplicationItemDao recruitmentApplicationItemDao;
    private DepartmentAndDutyDao departmentAndDutyDao;
   private MeetingApplicationAndStatusDao meetingApplicationAndStatusDao;


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
     * 创建 ApplicationDao表实例
     *
     * @return
     */
    public ApplicationDao getApplicationDao(){
        applicationDao = DaoManager.getInstance().getSession().getApplicationDao();
        return applicationDao;
    }

    /**
     * 创建AnnouncementApprovedOpinionListDao表实例
     *
     * @return
     */
    public AnnouncementApprovedOpinionListDao getAnnouncementApprovedOpinionListDao(){
        announcementApprovedOpinionListDao = DaoManager.getInstance().getSession().getAnnouncementApprovedOpinionListDao();
        return announcementApprovedOpinionListDao;
    }

    /**
     * 创建AnnouncementInfoDao表实例
     *
     * @return
     */
    public AnnouncementInfoDao getAnnouncementInfoDao(){
        announcementInfoDao = DaoManager.getInstance().getSession().getAnnouncementInfoDao();
        return announcementInfoDao;
    }
    /**
     * 创建AnnouncementInfoDao表实例
     *
     * @return
     */
    public ApproveNumberDao getApproveNumberDao(){
        approveNumberDao = DaoManager.getInstance().getSession().getApproveNumberDao();
        return approveNumberDao;
    }

    public WorkDetailDao getWorkDetailDao() {
        workDetailDao = DaoManager.getInstance().getSession().getWorkDetailDao();
        return workDetailDao;
    }

    public IssueWorkDao getIssueWorkDao() {
        issueWorkDao = DaoManager.getInstance().getSession().getIssueWorkDao();
        return issueWorkDao;
    }

    public FulltimeApplicationDao getFulltimeApplicationDao() {
        fulltimeApplicationDao = DaoManager.getInstance().getSession().getFulltimeApplicationDao();
        return fulltimeApplicationDao;
    }

    public FulltimeApplicationApprovedOpinionDao getFulltimeApplicationApprovedOpinionDao() {
        fulltimeApplicationApprovedOpinionDao = DaoManager.getInstance().getSession().getFulltimeApplicationApprovedOpinionDao();
        return fulltimeApplicationApprovedOpinionDao;
    }

    public IssueWorkMemberDao getIssueWorkMemberDao() {
        issueWorkMemberDao = DaoManager.getInstance().getSession().getIssueWorkMemberDao();
        return issueWorkMemberDao;
    }

    public JobTransferApplicationDao getJobTransferApplicationDao() {
        jobTransferApplicationDao = DaoManager.getInstance().getSession().getJobTransferApplicationDao();
        return jobTransferApplicationDao;
    }

    public JobTransferApplicationApprovedOpinionDao getJobTransferApplicationApprovedOpinionDao() {
        jobTransferApplicationApprovedOpinionDao = DaoManager.getInstance().getSession().getJobTransferApplicationApprovedOpinionDao();
        return jobTransferApplicationApprovedOpinionDao;
    }

    public LeaveOfficeApplicationDao getLeaveOfficeApplicationDao() {
        leaveOfficeApplicationDao = DaoManager.getInstance().getSession().getLeaveOfficeApplicationDao();
        return leaveOfficeApplicationDao;
    }

    public LeaveOfficeApplicationApprovedOpinionDao getLeaveOfficeApplicationApprovedOpinionDao() {
        leaveApplicationApprovedOpinionListDao = DaoManager.getInstance().getSession().getLeaveApplicationApprovedOpinionListDao();
        return leaveOfficeApplicationApprovedOpinionDao;
    }

    public MeetingApplicationDao getMeetingApplicationDao() {
        meetingApplicationDao = DaoManager.getInstance().getSession().getMeetingApplicationDao();
        return meetingApplicationDao;
    }

    public MeetingMemberDao getMeetingMemberDao() {
        meetingMemberDao = DaoManager.getInstance().getSession().getMeetingMemberDao();
        return meetingMemberDao;
    }

  /*  public RecruitmentApplicationDao getRecruitmentApplicationDao() {
        recruitmentApplicationDao = DaoManager.getInstance().getSession().getRecruitmentApplicationDao();
        return recruitmentApplicationDao;
    }*/

    public RecruitmentApplicationApprovedOpinionDao getRecruitmentApplicationApprovedOpinionDao() {
        recruitmentApplicationApprovedOpinionDao = DaoManager.getInstance().getSession().getRecruitmentApplicationApprovedOpinionDao();
        return recruitmentApplicationApprovedOpinionDao;
    }

    public RecruitmentApplicationItemDao getRecruitmentApplicationItemDao() {
        recruitmentApplicationItemDao = DaoManager.getInstance().getSession().getRecruitmentApplicationItemDao();
        return recruitmentApplicationItemDao;
    }

    public DepartmentAndDutyDao getDepartmentAndDutyDao() {
        departmentAndDutyDao = DaoManager.getInstance().getSession().getDepartmentAndDutyDao();
        return departmentAndDutyDao;
    }

    public MeetingApplicationAndStatusDao getMeetingApplicationAndStatusDao(){
        meetingApplicationAndStatusDao = DaoManager.getInstance().getSession().getMeetingApplicationAndStatusDao();
        return  meetingApplicationAndStatusDao;

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
