package springamqp.rpc.rabbitlistener.server;

/**
 * Created by wangwei on 2019/10/30 0030.
 */
public class SendStatus {
    private String phone;
    private String result;

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }
}
