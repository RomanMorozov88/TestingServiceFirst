package morozov.ru.models.controlsutil.forregistration;

public class ProfileForRespRegUtil implements MessageUtil<Integer> {

    private int idUser;

    public ProfileForRespRegUtil() {
    }

    @Override
    public void setData(Integer data) {
        this.idUser = data;
    }

    public int getIdUser() {
        return idUser;
    }

}
