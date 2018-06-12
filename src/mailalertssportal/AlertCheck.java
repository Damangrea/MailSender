/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailalertssportal;

import java.util.Date;

/**
 *
 * @author damangrea
 */
public class AlertCheck {
       public String sName,dateSchedule,timeScheduleStart,timeScheduleEnd,timeCheck,checkinout,customer,activity;
       String mailTo,mailTo2,mailTo3,mailSelf;
       public Date lateDate;
       public int lateCount;

    public AlertCheck(String sName, String dateSchedule, String timeScheduleStart, String timeScheduleEnd, String timeCheck, String customer, String activity, String checkinout) {
        this.sName = sName;
        this.dateSchedule = dateSchedule;
        this.timeScheduleStart = timeScheduleStart;
        this.timeScheduleEnd = timeScheduleEnd;
        this.timeCheck = timeCheck;
        this.checkinout = checkinout;
        this.customer = customer;
        this.activity = activity;
    }

    public void setMailSelf(String mailSelf) {
        this.mailSelf = mailSelf;
    }

    public String getMailSelf() {
        return mailSelf;
    }
    
    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailTo() {
        return mailTo;
    }
    public void setMailTo2(String mailTo) {
        this.mailTo2 = mailTo;
    }

    public String getMailTo2() {
        return (mailTo2==null && lateCount>1?"":","+mailTo2);
    }
    public void setMailTo3(String mailTo) {
        this.mailTo3 = mailTo;
        //additional 
        this.mailTo += ",sukirin@packet-systems.com";//VP
        this.mailTo += ",christina.yota@packet-systems.com";//HRD
    }

    public String getMailTo3() {
        return (mailTo3==null && lateCount>2?"":","+mailTo3);
    }
    

    public String getTitle(){
        StringBuilder sb = new StringBuilder();
        if(checkinout.equals("CHECKIN")){
            sb.append(sName);
            sb.append(" Checkin Past Time Scheduled");
        }else{
            sb.append(sName);
            sb.append(" Checkout Before Time Scheduled");
        }
        return sb.toString();
    }
    public String getContents(){
        StringBuilder sb = new StringBuilder();
        if(checkinout.equals("CHECKIN")){
            sb.append("Engineer Name : ");
            sb.append(sName);
            sb.append("\n");
            sb.append("Customer : ");
            sb.append(customer);
            sb.append("\n");
            sb.append("Activity : ");
            sb.append(activity);
            sb.append("\n");
            sb.append("Schedule Time : ");
            sb.append(dateSchedule).append(" from ").append(timeScheduleStart).append(" to ").append(timeScheduleEnd);
            sb.append("\n");
            sb.append("Checkin Time  : ");
            sb.append(timeCheck);
            sb.append("\n");
            sb.append("Break Count : ");
            sb.append(lateCount);
            sb.append("\n");
        }else{
            sb.append("Engineer Name : ");
            sb.append(sName);
            sb.append("\n");
            sb.append("Customer : ");
            sb.append(customer);
            sb.append("\n");
            sb.append("Activity : ");
            sb.append(activity);
            sb.append("\n");
            sb.append("Schedule Time : ");
            sb.append(dateSchedule).append(" from ").append(timeScheduleStart).append(" to ").append(timeScheduleEnd);
            sb.append("\n");
            sb.append("Checkout Time : ");
            sb.append(timeCheck);
            sb.append("\n");
            sb.append("Break Count : ");
            sb.append(lateCount);
            sb.append("\n");
        }
//        sb.append(getMailTo());
        return sb.toString();
    }
}
