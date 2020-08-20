package morozov.ru.models.controlsutil.forregistration;

public class MsgForRespUtil implements MessageUtil<String> {

    private String msg;

    public MsgForRespUtil() {
    }

    @Override
    public void setData(String data) {
        this.msg = data;
    }

    public String getMsg() {
        return msg;
    }
}
