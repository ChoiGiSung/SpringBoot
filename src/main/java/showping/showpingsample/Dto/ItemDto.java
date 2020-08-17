package showping.showpingsample.Dto;

public class ItemDto {
    private String user_name;
    private String item_name;
    private int item_price;
    private int item_mount;
    private Long item_id;


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

    public int getItem_price() {
        return item_price;
    }

    public void setItem_price(int item_price) {
        this.item_price = item_price;
    }

    public int getItem_mount() {
        return item_mount;
    }

    public void setItem_mount(int item_mount) {
        this.item_mount = item_mount;
    }

    public Long getItem_id() {
        return item_id;
    }

    public void setItem_id(Long item_id) {
        this.item_id = item_id;
    }
}
