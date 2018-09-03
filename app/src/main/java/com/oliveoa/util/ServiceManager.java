package com.oliveoa.util;

import android.content.Context;

import com.oliveoa.common.BusinessTripApplicationHttpResponseObject;
import com.oliveoa.controller.AnnouncementService;
import com.oliveoa.controller.BusinessTripApplicationService;
import com.oliveoa.controller.LeaveApplicationService;
import com.oliveoa.controller.LoginService;
import com.oliveoa.controller.MessageService;
import com.oliveoa.controller.OvertimeApplictionService;
import com.oliveoa.controller.UserInfoService;
import com.oliveoa.controller.WorkDetailService;
import com.oliveoa.jsonbean.AnnouncementInfoJsonBean;
import com.oliveoa.jsonbean.AnnouncementJsonBean;
import com.oliveoa.jsonbean.BusinessTripApplicationJsonBean;
import com.oliveoa.jsonbean.StatusAndMsgJsonBean;
import com.oliveoa.jsonbean.StatusJsonBean;
import com.oliveoa.pojo.AnnouncementInfo;
import com.oliveoa.pojo.BusinessTripApplication;
import com.oliveoa.pojo.BusinessTripApplicationApprovedOpinionList;
import com.oliveoa.pojo.Message;
import com.oliveoa.pojo.WorkDetail;

import java.util.ArrayList;

/**
 *  ClassName： ServiceManager
 *  Description： 管理Service类，实现基本操作
 *  @Author： Erica
 */
public class ServiceManager {
    private AnnouncementService announcementService;
    private BusinessTripApplicationService businessTripApplicationService;
    private LeaveApplicationService leaveApplicationService;
    private LoginService loginService;
    private MessageService messageService;
    private OvertimeApplictionService overtimeApplictionService;
    private UserInfoService userInfoService;
    private WorkDetailService workDetailService;

    public ServiceManager() {
    }

    /**
     *   Description：公告管理
     *   @Author： Erica
     */
    public void DoAnnouncementService(Context mcontext){

    }
    public AnnouncementJsonBean get_annoucementinfo(String s, String aid) {
        return announcementService.get_annoucementinfo(s, aid);
    }

    public StatusJsonBean approved_annoucements(String s, AnnouncementInfo announcementInfo) {
        return announcementService.approved_annoucements(s, announcementInfo);
    }

    public StatusAndMsgJsonBean submit_annoucements(String s, AnnouncementInfo announcementInfo, ArrayList<String> approvedMember) {
        return announcementService.submit_annoucements(s, announcementInfo, approvedMember);
    }

    public AnnouncementInfoJsonBean get_published_annoucements(String s) {
        return announcementService.get_published_annoucements(s);
    }

    public AnnouncementInfoJsonBean get_approvedannoucement(String s) {
        return announcementService.get_approvedannoucement(s);
    }

    public AnnouncementInfoJsonBean get_isubmitannoucements(String s) {
        return announcementService.get_isubmitannoucements(s);
    }

    /**
     *   Description： 出差申请
     *   @Author： Erica
     */
    public StatusAndMsgJsonBean sentbtapplication(String s, BusinessTripApplication businessTripApplication, ArrayList<String> approvedMember) {
        return businessTripApplicationService.sentbtapplication(s, businessTripApplication, approvedMember);
    }

    public StatusAndMsgJsonBean approvedbtapplication(String s, BusinessTripApplicationApprovedOpinionList btol) {
        return businessTripApplicationService.approvedbtapplication(s, btol);
    }

    public BusinessTripApplicationHttpResponseObject getbtapplicationinfo(String s, String btaid) {
        return businessTripApplicationService.getbtapplicationinfo(s, btaid);
    }

    public BusinessTripApplicationJsonBean getbtapplicationunapproved(String s) {
        return businessTripApplicationService.getbtapplicationunapproved(s);
    }

    public BusinessTripApplicationJsonBean getbtapplicationsubmited(String s) {
        return businessTripApplicationService.getbtapplicationsubmited(s);
    }

    /**
     *   Description： 请假申请
     *   @Author： Erica
     */


    /**
     *   Description： 登录
     *   @Author： Erica
     */


    /**
     *   Description： 消息管理
     *   @Author： Erica
     */


    /**
     *   Description： 加班申请
     *   @Author： Erica
     */


    /**
     *   Description： 用户信息
     *   @Author： Erica
     */


    /**
     *   Description： 工作日程
     *   @Author： Erica
     */
}
