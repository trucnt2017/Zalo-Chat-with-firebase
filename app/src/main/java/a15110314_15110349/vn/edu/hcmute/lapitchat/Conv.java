package a15110314_15110349.vn.edu.hcmute.lapitchat;

/**
 * Created by GotPho on 10/06/2018.
 */

public class Conv {

    public boolean seen;
    public long timestamp;

    public Conv(){

    }

    public boolean isSeen() {
        return seen;
    }

    public void setSeen(boolean seen) {
        this.seen = seen;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public Conv(boolean seen, long timestamp) {
        this.seen = seen;
        this.timestamp = timestamp;
    }
}