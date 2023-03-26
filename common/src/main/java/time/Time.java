package time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 李广辉
 * @date 2023/2/21 23:22
 */
public class Time {

    //传入时间戳即可
//    public String conversionTime(String timeStamp) {
//        //yyyy-MM-dd HH:mm:ss 转换的时间格式  可以自定义
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        //转换
//        String time = sdf.format(new Date(Long.parseLong(timeStamp)));
//        return time;
//    }

//
//    public static void main(String[] args) {
////        String date1="2013-06-24 12:30:30"; //
////        String date2="2013-06-26 12:30:31";
////        try {
////            if(jisuan(date1,date2)){
////                System.out.println("可用");
////
////            }else{
////                System.out.println("已过期");
////            }
////        } catch (Exception e) {
////            // TODO: handle exception
////        }
//        long l = System.currentTimeMillis();
//        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time= sdf.format(new Date(l));
//        System.out.println(time);
//    }
//

    public static boolean between24hours(long leftTime,long nowTime) throws ParseException {
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String LeftTime= sdf.format(new Date(leftTime));
        String NowTime= sdf.format(new Date(nowTime));
        Date LT = sdf.parse(LeftTime);
        Date NT = sdf.parse(NowTime);
        long betweenTime= NT.getTime()- LT.getTime();
        double result=betweenTime*1.0/(1000*60*60);
        if(result<=24){
            //System.out.println("可用");
            return true;
        }else{
            //System.out.println("已过期");
            return false;
        }
    }

    //判断是否超过24小时
//    public static boolean jisuan(String date1, String date2) throws Exception {
//        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("yyyy-M-d HH:mm:ss");
//        java.util.Date start = sdf.parse(date1);
//        java.util.Date end = sdf.parse(date2);
//        long cha = end.getTime() - start.getTime();
//        double result = cha * 1.0 / (1000 * 60 * 60);
//        if(result<=24){
//            //System.out.println("可用");
//            return true;
//        }else{
//            //System.out.println("已过期");
//            return false;
//        }
//    }
}
