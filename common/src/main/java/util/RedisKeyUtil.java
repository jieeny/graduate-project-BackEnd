package util;

/**
 * 专门用来生成Redis Key
 * @author 李广辉
 * @date 2023/2/21 11:14
 */
public class RedisKeyUtil {

    //用来分隔key
    private static final String SPLIT=":";

    //每个医生对应的待接诊病人的key的前缀
    private static final String Doctor_Patient_WaitingHeal_ZSet="DoctorPatientWaitingHealZSet";

    //当前医生的待接诊的病人的数量
    private static final String Doctor_Patient_WaitingHeal_Number="DoctorPatientWaitingHealNumber";

    //每个医生对应的State
    private static final String Doctor_State="DoctorState";

    //每个医生对应的号源
    private static final String Doctor_Registration_Number="DoctorRegistrationNumber";

    //医生离线时间
    private static final String Doctor_Left_Time="DoctorLeftTime";

    //医生对应的待接诊患者的ID集合SET
    private static final String Doctor_Patient_WaitingHeal_Set="DoctorPatientWaitingHealSet";

    //已经完成接诊的订单的KEY
    private static final String CompletedHealed="CompletedHealed";

    //医生对应的已完成订单的KEY
    private static final String Doctor_CompletedHealed="DoctorCompletedHealed";

    //病人对应的已完成订单的KEY
    private static final String Patient_CompleteHealed="PatientCompletedHealed";

    //医生的待开方订单集合的KEY
    private static final String Doctor_Waiting_Prescribe_Order_Set="DoctorWaitingPrescribeOrderSet";

    private static final String User_Waiting_Heal="UserWaitingHeal";

    /**
     * 构造每个医生对应的订单(患者挂号)的ZSet key
     * @param doctorId
     * @return
     */
    public static String getDoctorPatientWaitingHealZSetKey(String doctorId){
        return Doctor_Patient_WaitingHeal_ZSet+SPLIT+doctorId;
    }

    /**
     * 构造每个医生对应的待接诊的患者的ID的集合SET
     * @param doctorId
     * @return
     */
    public static String getDoctorPatientWaitingHealSet(String doctorId){
        return Doctor_Patient_WaitingHeal_Set+SPLIT+doctorId;
    }

    /**
     * 构造每个医生对应的state_key
     * @param doctorId
     * @return
     */
    public static String getDoctorStateKey(String doctorId){
        return Doctor_State+SPLIT+doctorId;
    }

    /**
     * 构造每个医生对应的待接诊病人的数量number_key
     * @param doctorId
     * @return
     */
    public static String getDoctorPatientWaitingHealNumberKey(String doctorId){
        return Doctor_Patient_WaitingHeal_Number+SPLIT+doctorId;
    }

    /**
     * 构造每个医生剩余的号源
     * @param doctorId
     * @return
     */
    public static String getDoctorRegistrationNumberKey(String doctorId){
        return Doctor_Registration_Number+SPLIT+doctorId;
    }

    /**
     * 构造每个医生对应的下线时间key
     * @param doctorId
     * @return
     */
    public static String getDoctorLeftTime(String doctorId){
        return Doctor_Left_Time+SPLIT+doctorId;
    }

    /**
     * 已经完成接诊的订单的KEY
     * @param orderId
     * @return
     */
    public static String getCompletedOrderKey(String orderId){
        return CompletedHealed+SPLIT+orderId;
    }

    /**
     * 构造医生已完成的订单的KEY
     * @param doctorId
     * @return
     */
    public static String getDoctorCompletedHealed(String doctorId){
        return Doctor_CompletedHealed+SPLIT+doctorId;
    }

    /**
     * 构造患者已完成的订单key
     * @param userId
     * @return
     */
    public static String getPatientCompleteHealed(String userId){
        return Patient_CompleteHealed+SPLIT+userId;
    }

    /**
     * 构造医生的待开方订单的集合KEY
     * @param doctorId
     * @return
     */
    public static String getDoctorWaitingPrescribeOrderSetKEY(String doctorId){
        return Doctor_Waiting_Prescribe_Order_Set+SPLIT+doctorId;
    }

    /**
     * 构造用户等待接诊的订单KEY
     * @param userId
     * @return
     */
    public static String getUserWaitingHealKey(String userId){
        return User_Waiting_Heal+SPLIT+userId;
    }
}
