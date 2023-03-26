package helper;

import java.util.UUID;

/**
 * @author 李广辉
 * @date 2023/2/15 16:54
 */
public class UUIDHelper {
    public static String getRandomUUID(){
        return UUID.randomUUID().toString().replaceAll("-","");
    }

    /**
     * 根据命名空间生成唯一编号
     * @param username
     * @return
     */
    public static String getUsernameUUID(String username){
        return UUID.nameUUIDFromBytes(username.getBytes()).toString().replaceAll("-","");
    }

//    public static String getUUIDByPatientIDDoctorIdTime(String patientId){
//
//    }

    public static void main(String[] args) {
        for (int i=0;i<10;i++){
            String s=getUsernameUUID(i+"123");
            System.out.println(s);
            System.out.println(s.length());
        }
    }
}
