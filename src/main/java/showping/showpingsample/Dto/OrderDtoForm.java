package showping.showpingsample.Dto;

import java.util.Date;

public class OrderDtoForm {
    private String user_name;
    private String item_name;
    private int item_mount;

    private String order_state;
    private Date order_date;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public int getItem_mount() {
        return item_mount;
    }

    public void setItem_mount(int item_mount) {
        this.item_mount = item_mount;
    }

    public String getOrder_state() {
        return order_state;
    }

    public void setOrder_state(String order_state) {
        this.order_state = order_state;
    }

    public Date getOrder_date() {
        return order_date;
    }

    public void setOrder_date(Date order_date) {
        this.order_date = order_date;
    }
}
