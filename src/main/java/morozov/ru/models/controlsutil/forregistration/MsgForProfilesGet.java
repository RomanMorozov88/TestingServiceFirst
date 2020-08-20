package morozov.ru.models.controlsutil.forregistration;

public class MsgForProfilesGet implements MessageUtil<String> {

    private String email;

    @Override
    public void setData(String data) {
        this.email = data;
    }

    public String getEmail() {
        return this.email;
    }
}
