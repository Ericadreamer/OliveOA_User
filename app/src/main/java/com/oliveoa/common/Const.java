package com.oliveoa.common;

public class Const {
    public static final String HOSTPATH = "http://119.23.253.135:8080";

    //登录
    public static final String User_LOGIN = HOSTPATH + "/oliveoa/manage/user/login.do";

    //个人信息
    public static final String USER_PASSWORD_UPDATE = HOSTPATH +"/oliveoa/employee/update_password.do";
    public static final String USER_INFO_SEARCH = HOSTPATH +"/oliveoa/employee/get_info.do";
    public static final String USER_CONTACT = HOSTPATH +"/oliveoa/employee/get_contact.do";
    public static final String USER_LOGOUT = HOSTPATH +"/oliveoa/employee/logout.do";

    public static final String USER_INFO_UPDATE = HOSTPATH +"/oliveoa/employee/update_info.do";

    //加班申请
    public static final String APPLICATION_ADD = HOSTPATH +"/oliveoa/employee/application/add_overtime_application.do";
    public static final String APPLICATION_APPROVED =HOSTPATH+"/oliveoa/employee/application/approved_overtime_application.do";
    public static final String APPLICATION_ALL_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_details.do";
    public static final String APPLICATION_NEEDAPPROVED_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_need_approved.do";
    public static final String APPLICATION_SUBMIT_SEARCH =HOSTPATH+"/oliveoa/employee/application/get_overtime_application_Isubmit.do";

    //工作日程
    public static final String WORK_ASSIGING = HOSTPATH +"/oliveoa/employee/work/issue_work.do";
    public static final String WORK_STAFFSUBMIT = HOSTPATH +"/oliveoa/employee/work/submit_work.do";
    public static final String WORK_APPROVING = HOSTPATH +"/oliveoa/employee/work/approved_work.do";
    public static final String WORK_APPROVED_SEARCH = HOSTPATH +"/oliveoa/employee/work/get_work_unapproved.do";
    public static final String WORK_ASSIGED_SEARCH =HOSTPATH +"/oliveoa/employee/work/get_work_IIssue.do";
    public static final String WORK_SUBMITED_SEARCH = HOSTPATH +"/oliveoa/employee/work/get_submit_work.do";
    public static final String WORK_INFOSUBMITED_SEARCH =HOSTPATH+"/oliveoa/employee/work/get_work_detail.do";
    public static final String WORK_ALL_SUBMITED_SEARCH = HOSTPATH +"/oliveoa/employee/work/get_approved_work.do";
}
