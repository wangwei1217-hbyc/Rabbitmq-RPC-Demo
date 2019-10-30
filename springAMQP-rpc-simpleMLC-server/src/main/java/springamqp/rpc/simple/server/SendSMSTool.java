package springamqp.rpc.simple.server;

import java.util.Random;

/**
 * Created by wangwei on 2019/10/30 0030.
 */
public class SendSMSTool {
    public static boolean sendSMS(String phone, String content) {
        System.out.println("发送短信内容【" + content + "】到手机号：" + phone);
        return new Random().nextBoolean();
    }
}
