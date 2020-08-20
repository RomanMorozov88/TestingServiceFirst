package morozov.ru.models.controlsutil.forregistration;

public class MsgForRespRegUtil implements MessageUtil<String> {

    private String msg;

    public MsgForRespRegUtil() {
    }

    @Override
    public void setData(String data) {
        this.msg = data;
    }

    public String getMsg() {
        return msg;
    }
}
