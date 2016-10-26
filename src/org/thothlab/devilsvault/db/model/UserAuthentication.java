package org.thothlab.devilsvault.db.model;

public class UserAuthentication {
    private String username;
    private String password;
    private int enabled;
    private String role;
    private int accountNonExpired;
    private int accountNonLocked;
    private int credentialsNonExpired;
    private int otpNonLocked;
    public int getAccountNonExpired() {
        return accountNonExpired;
    }
    public void setAccountNonExpired(int accountNonExpired) {
        this.accountNonExpired = accountNonExpired;
    }
    public int getAccountNonLocked() {
        return accountNonLocked;
    }
    public void setAccountNonLocked(int accountNonLocked) {
        this.accountNonLocked = accountNonLocked;
    }
    public int getCredentialsNonExpired() {
        return credentialsNonExpired;
    }
    public void setCredentialsNonExpired(int credentialsNonExpired) {
        this.credentialsNonExpired = credentialsNonExpired;
    }
    public int getOtpNonLocked() {
        return otpNonLocked;
    }
    public void setOtpNonLocked(int otpNonLocked) {
        this.otpNonLocked = otpNonLocked;
    }
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public int getEnabled() {
        return enabled;
    }
    public void setEnabled(int enabled) {
        this.enabled = enabled;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

}