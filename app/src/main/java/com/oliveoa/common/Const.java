package com.oliveoa.common;

public class Const {
    public static final String HOSTPATH = "http://119.23.253.135:8080";

    //登录
    public static final String User_LOGIN = HOSTPATH + "/oliveoa/employee/login.do";

    //个人信息
    public static final String USER_PASSWORD_UPDATE = HOSTPATH +"/oliveoa/employee/update_password.do";
    public static final String USER_INFO_SEARCH = HOSTPATH +"/oliveoa/employee/get_info.do";
    public static final String USER_CONTACT = HOSTPATH +"/oliveoa/employee/get_contact.do";
    public static final String USER_LOGOUT = HOSTPATH +"/oliveoa/employee/logout.do";
    public static final String USER_INFO_UPDATE = HOSTPATH +"/oliveoa/employee/update_info.do";

    //根据部门获取职务
    public static  final  String DUTY_SEARCH = HOSTPATH + "/oliveoa/manage/position/get_position.do";

 //个人消息
    public static final String MESSAGE_SENT = HOSTPATH +"/oliveoa/employee/sent_message.do";
    public static final String MESSAGE_TOME = HOSTPATH +"/oliveoa/employee/get_message_sent_to_me.do";
    public static final String MESSAGE_BYME = HOSTPATH +"/oliveoa/employee/get_message_Isent.do";

    //出差申请
    public static final String BTAPPLICATION_ADD = HOSTPATH +"/oliveoa/employee/application/add_business_trip_application.do";
    public static final String BTAPPLICATION_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_business_trip_application.do";
    public static final String BTAPPLICATION_ALLSEARCH = HOSTPATH +"/oliveoa/employee/application/get_business_trip_application_details.do";
    public static final String BTAPPLICATION_NEEDAPPROVED_SEARCH = HOSTPATH +"/oliveoa/employee/application/get_business_trip_application_need_approved.do";
    public static final String BTAPPLICATION_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_business_trip_application_approved.do";
    public static final String BTAPPLICATION_SUBMIT_SEARCH = HOSTPATH +"/oliveoa/employee/application/get_business_trip_application_Isubmit.do";

    //加班申请
    public static final String OTAPPLICATION_ADD = HOSTPATH +"/oliveoa/employee/application/add_overtime_application.do";
    public static final String OTAPPLICATION_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_overtime_application.do";
    public static final String OTAPPLICATION_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_details.do";
    public static final String OTAPPLICATION_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_need_approved.do";
    public static final String OTAPPLICATION_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_approved.do";
    public static final String OTAPPLICATION_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_Isubmit.do";

    //请假申请
    public static final String LAPPLICATION_ADD = HOSTPATH +"/oliveoa/employee/application/add_leave_application.do";
    public static final String LAPPLICATION_APPROVED = HOSTPATH +"/oliveoa/employee/application/approved_leave_application.do";
    public static final String LAPPLICATION_ALL_SEARCH = HOSTPATH +"/oliveoa/employee/application/get_leave_application_details.do";
    public static final String LAPPLICATION_NEEDAPPROVED_SEARCH = HOSTPATH +"/oliveoa/employee/application/get_leave_application_need_approved.do";
    public static final String LAPPLICATION_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_leave_application_approved.do";
    public static final String LAPPLICATION_SUBMIT_SEARCH = HOSTPATH +"/oliveoa/employee/application/get_leave_application_Isubmit.do";

    //工作日程
    public static final String WORK_ASSIGING = HOSTPATH +"/oliveoa/employee/work/issue_work.do";
    public static final String WORK_STAFFSUBMIT = HOSTPATH +"/oliveoa/employee/work/submit_work.do";
    public static final String WORK_APPROVING = HOSTPATH +"/oliveoa/employee/work/approved_work.do";
    public static final String WORK_APPROVED_SEARCH = HOSTPATH +"/oliveoa/employee/work/get_work_unapproved.do";
    public static final String WORK_ASSIGED_SEARCH =HOSTPATH +"/oliveoa/employee/work/get_work_IIssue.do";
    public static final String WORK_SUBMITED_SEARCH = HOSTPATH +"/oliveoa/employee/work/get_submit_work.do";
    public static final String WORK_INFOSUBMITED_SEARCH =HOSTPATH+"/oliveoa/employee/work/get_work_detail.do";
    public static final String WORK_ALL_SUBMITED_SEARCH = HOSTPATH +"/oliveoa/employee/work/get_approved_work.do";
    public static final String WORK_ASSIGEDTOME_SEARCH =HOSTPATH +"/oliveoa/employee/work/get_work_tome.do";//获取发布（分配）给我的工作
    //公告
    public static final String ANNOUNCEMENT_APPROVED = HOSTPATH + "/oliveoa/employee/approved_announcement.do";
    public static final String ANNOUNCEMENT_SUBMITED = HOSTPATH + "/oliveoa/employee/submit_announcement.do";
    public static final String ANNOUNCEMENT_INFO = HOSTPATH + "/oliveoa/employee/get_announcement_details.do";
    public static final String ANNOUNCEMENT_PUBLISHED_SEARCH= HOSTPATH + "/oliveoa/employee/get_announcement_publish.do";
    public static final String ANNOUNCEMENT_NEEDAPPROVED_SEARCH= HOSTPATH + "/oliveoa/employee/get_announcement_need_approved.do";
    public static final String ANNOUNCEMENT_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/get_announcement_approved.do";
    public static final String ANNOUNCEMENT_ISUBMITED_SEARCH= HOSTPATH + "/oliveoa/employee/get_announcement_Isubmit.do";

    //会议申请
    public static final String MEETING_ADD = HOSTPATH +"/oliveoa/employee/application/add_meeting_application.do";
    public static final String MEETING_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_meeting_application.do";
    public static final String MEETING_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_meeting_application_details.do";
    public static final String MEETING_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_meeting_application_need_approved.do";
    public static final String MEETING_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_meeting_application_approved.do";
    public static final String MEETING_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_meeting_application_Isubmit.do";
    public static final String MEETING_WILLDO_SEARCH = HOSTPATH+"/oliveoa/employee/application/get_my_meeting_will_do.do";
    public static final String MEETING_DONE_SEARCH = HOSTPATH+"/oliveoa/employee/application/get_my_meeting_done.do";
    public static final String MEETING_DOING_SEARCH = HOSTPATH+"/oliveoa/employee/application/get_my_meeting_doing.do";

    //离职申请
    public static final String LEAVEOFFICE_ADD = HOSTPATH +"/oliveoa/employee/application/add_leave_office_application.do";
    public static final String LEAVEOFFICE_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_leave_office_application.do";
    public static final String LEAVEOFFICE_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_leave_office_application_details.do";
    public static final String LEAVEOFFICE_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_leave_office_application_need_approved.do";
    public static final String LEAVEOFFICE_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_leave_office_application_approved.do";
    public static final String LEAVEOFFICE_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_leave_office_application_Isubmit.do";


    //转正申请
    public static final String FULLTIME_ADD = HOSTPATH +"/oliveoa/employee/application/add_fulltime_application.do";
    public static final String FULLTIME_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_fulltime_application.do";
    public static final String FULLTIME_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_fulltime_application_details.do";
    public static final String FULLTIME_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_fulltime_application_need_approved.do";
    public static final String FULLTIME_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_fulltime_application_approved.do";
    public static final String FULLTIME_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_fulltime_application_Isubmit.do";


    //调岗申请
    public static final String JOBTRANSFER_ADD = HOSTPATH +"/oliveoa/employee/application/add_job_transfer_application.do";
    public static final String JOBTRANSFER_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_job_transfer_application.do";
    public static final String JOBTRANSFER_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_job_transfer_application_details.do";
    public static final String JOBTRANSFER_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_job_transfer_application_need_approved.do";
    public static final String JOBTRANSFER_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_job_transfer_application_approved.do";
    public static final String JOBTRANSFER_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_job_transfer_application_Isubmit.do";


    //招聘申请
    public static final String RECRUITMENT_ADD = HOSTPATH +"/oliveoa/employee/application/add_recruitment_application.do";
    public static final String RECRUITMENT_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_recruitment_application.do";
    public static final String RECRUITMENT_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_recruitment_application_details.do";
    public static final String RECRUITMENT_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_recruitment_application_need_approved.do";
    public static final String RECRUITMENT_APPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_recruitment_application_approved.do";
    public static final String RECRUITMENT_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_recruitment_application_Isubmit.do";

    //公文流转
    public static final  String DOCUMENTFLOW_DRAFT = HOSTPATH+"/oliveoa/documentflow/draft.do"; //拟稿
    public static final  String DOCUMENTFLOW_IDRAFTED_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_Idraft.do";//获取我拟稿的
    public static final  String DOCUMENTFLOW_NEEDNUCLEAR = HOSTPATH+"/oliveoa/documentflow/get_document_need_nuclear.do";//获取待核稿的
    public static final  String DOCUMENTFLOW_NUCLEAR = HOSTPATH+"/oliveoa/documentflow/nuclear.do";//核稿
    public static final  String DOCUMENTFLOW_NUCLEARED_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_done_nuclear.do";//获取已经核稿的
    public static final  String DOCUMENTFLOW_NEEDISSUE_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_need_Issue.do";//获取待签发的
    public static final  String DOCUMENTFLOW_ISSUE = HOSTPATH+"/oliveoa/documentflow/issue.do";//签发
    public static final  String DOCUMENTFLOW_ISSUEED_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_done_Issue.do";//获取已经签发的
    public static final  String DOCUMENTFLOW_NEEDRECEIVE_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_need_receive.do";//获取待签收的
    public static final  String DOCUMENTFLOW_RECEIVE = HOSTPATH+"/oliveoa/documentflow/receive.do";//签收
    public static final  String DOCUMENTFLOW_RECEIVEED_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_received.do";//获取已经签收
    public static final  String DOCUMENTFLOW_NEEDREAD_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_need_read.do";//获取待办理的
    public static final  String DOCUMENTFLOW_READ = HOSTPATH+"/oliveoa/documentflow/read.do";//办理公文
    public static final  String DOCUMENTFLOW_READED_SEARCH = HOSTPATH+"/oliveoa/documentflow/get_document_have_read.do";//获取已经办理的
    public static final  String DOCUMENTFLOW_GETDETAILS = HOSTPATH+"/oliveoa/documentflow/get_document_details.do";//获取公文详情
    public static final  String DOCUMENTFLOW_GETALL = HOSTPATH+"/oliveoa/documentflow/get_document_list.do";//获取全部公文
    public static final  String DOCUMENTFLOW_DOWNLOAD = HOSTPATH+"/oliveoa/documentflow/download.do?odid=";//下载文件接口




}
