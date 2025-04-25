package nathja.finalproject.baitapview.listview;

public class MonHoc {
    public MonHoc(String name,String desc, int pic) {
        this.pic = pic;
        this.desc = desc;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getPic() {
        return pic;
    }

    public void setPic(int pic) {
        this.pic = pic;
    }

    private String name;
    private String desc;
    private int pic;
}
