package util;

/**
 * @author 李广辉
 * @date 2023/3/18 10:42
 */
public class MongoDBKeyUtil {

    private static final String Doctor_Patient_WaitingHeal_Key="Doctor_Patient_WaitingHeal";
    private static final String SPLIT=":";

    private static final String User_Waiting_Heal_Key="UserWaitingHeal";

    private static final String Doctor_Waiting_Prescribe="Doctor_Waiting_Prescribe";


    /**
     * 构造医生待接诊的患者的集合Key
     * @param doctorId
     * @return
     */
    public static String getDoctorPatientWaitingHealKey(String doctorId){
        return Doctor_Patient_WaitingHeal_Key+SPLIT+doctorId;
    }

    /**
     * 构造用户待接诊集合
     * @return
     */
    public static String getUserWaitingHealKey(){
        return User_Waiting_Heal_Key;
    }

    /**
     * 构造医生待开处方订单
     * @return
     */
    public static String getDoctorWaitingPrescribeKey(){
        return Doctor_Waiting_Prescribe;
    }

}
