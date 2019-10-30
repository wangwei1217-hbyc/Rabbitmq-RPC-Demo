package rabbitmq.rpc.server;

/**
 * Created by wangwei on 2019/10/28 0028.
 */
public class SendSMSTool {
    public static boolean sendSMS(String phone,String content){
        System.out.println("发送短信内容：【"+content+"】,到手机号："+phone);
        return phone.length() >= 7;
    }
}
