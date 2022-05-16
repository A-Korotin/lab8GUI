package net.auth;

import org.apache.commons.codec.digest.DigestUtils;

public final class User {
    public String login;
    public String password;

    public User(){}

    public User(String login, String password) {
       this.login = login;
       this.password = DigestUtils.md5Hex(password).toUpperCase();
    }
}
