package springamqp.rpc.simple.server;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 1.消息处理方法，一定要有返回值，这个返回值就是server回复客户端的结果
 * 2：server端返回的结果，一定要注意。和MessageConverter有关，默认的Simple MessageConverter，会把基本数据类型转换成Serializable对象，这样的话，client端接收的也是序列化的java对象。所以，需要合理设置MessageConverter

 * Created by wangwei on 2019/10/30 0030.
 */
public class MessageHandler {

    public void handleMessage(byte[] body){
        System.out.println("-----handleMessage(byte)------");
        System.out.println(new String(body));
    }


    public SendStatus handleMessage(Map body) throws Exception{
        System.out.println("-----handleMessage(Map)------");
        System.out.println(body.toString());
        String phone = body.get("phone").toString();
        String content = body.get("content").toString();
        boolean result = SendSMSTool.sendSMS(phone, content);
        SendStatus sendStatus = new SendStatus();
        sendStatus.setPhone(phone);
        sendStatus.setResult(result ? "SUCCESS" : "FAIL");
        TimeUnit.SECONDS.sleep(6);
        return sendStatus;
    }
}
