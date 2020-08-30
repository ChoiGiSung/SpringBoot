package showping.showpingsample.Dto;

public class OrderSearchDtoForm {
    //주문리스트에서의 검색

    private String user_name;
    private String state;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
