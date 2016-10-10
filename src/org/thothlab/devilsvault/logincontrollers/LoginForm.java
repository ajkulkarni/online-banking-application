package org.thothlab.devilsvault.logincontrollers;

import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.NotEmpty;
public class LoginForm {
	@NotEmpty
	@Size(min = 1, max = 50)
	private String userName;
	@NotEmpty
	@Size(min = 1, max = 20)
	private String password;
	private String captcha;
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserName() {
		return userName;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getPassword() {
		return password;
	}
	public String getCaptcha() {
        return captcha;
    }
    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }
    
}
