/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mailalertssportal;

/**
 *
 * @author damangrea
 */
public class AlertCheck {
       String sName,dateSchedule,timeScheduleStart,timeScheduleEnd,timeCheck,checkinout;
       String mailTo;

    public AlertCheck(String sName, String dateSchedule, String timeScheduleStart, String timeScheduleEnd, String timeCheck, String checkinout) {
        this.sName = sName;
        this.dateSchedule = dateSchedule;
        this.timeScheduleStart = timeScheduleStart;
        this.timeScheduleEnd = timeScheduleEnd;
        this.timeCheck = timeCheck;
        this.checkinout = checkinout;
    }

    public void setMailTo(String mailTo) {
        this.mailTo = mailTo;
    }

    public String getMailTo() {
        return mailTo;
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
            sb.append("Schedule Time : ");
            sb.append(dateSchedule).append(" from ").append(timeScheduleStart).append(" to ").append(timeScheduleEnd);
            sb.append("\n");
            sb.append("Checkin Time  : ");
            sb.append(timeCheck);
            sb.append("\n");
        }else{
            sb.append("Engineer Name : ");
            sb.append(sName);
            sb.append("\n");
            sb.append("Schedule Time : ");
            sb.append(dateSchedule).append(" from ").append(timeScheduleStart).append(" to ").append(timeScheduleEnd);
            sb.append("\n");
            sb.append("Checkout Time : ");
            sb.append(timeCheck);
            sb.append("\n");
        }
//        sb.append(getMailTo());
        return sb.toString();
    }
}
