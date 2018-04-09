package joslabs.a4gcapitalapp.reglogin;

/**
 * Created by OSCAR on 4/9/2018.
 */

public class UserMethod {
    String username,idno,ulocation,ustation,udob,imgprofile;

    public UserMethod() {
    }

    public UserMethod(String username, String idno, String ulocation, String ustation, String udob, String imgprofile) {
        this.username = username;
        this.idno = idno;
        this.ulocation = ulocation;
        this.ustation = ustation;
        this.udob = udob;
        this.imgprofile = imgprofile;
    }

    public String getImgprofile() {
        return imgprofile;
    }

    public void setImgprofile(String imgprofile) {
        this.imgprofile = imgprofile;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getIdno() {
        return idno;
    }

    public void setIdno(String idno) {
        this.idno = idno;
    }

    public String getUlocation() {
        return ulocation;
    }

    public void setUlocation(String ulocation) {
        this.ulocation = ulocation;
    }

    public String getUstation() {
        return ustation;
    }

    public void setUstation(String ustation) {
        this.ustation = ustation;
    }

    public String getUdob() {
        return udob;
    }

    public void setUdob(String udob) {
        this.udob = udob;
    }
}
