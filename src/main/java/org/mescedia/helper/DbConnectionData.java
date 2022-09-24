package org.mescedia.helper;

public class DbConnectionData {

    private String name;
    private String uri;
    private String username;
    private String password ;

    public void setName(String name) {
        this.name = name;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public void setUserName(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getUri() {
        return uri;
    }

    public String getUserName() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
