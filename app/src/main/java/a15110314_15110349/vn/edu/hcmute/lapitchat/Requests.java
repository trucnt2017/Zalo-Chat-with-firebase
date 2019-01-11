package a15110314_15110349.vn.edu.hcmute.lapitchat;

/**
 * Created by GotPho on 13/06/2018.
 */

public class Requests {
    private String name, status, image;
    public Requests(){}
    public Requests(String name, String status, String image){
        this.name=name;
        this.status=status;
        this.image=image;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
