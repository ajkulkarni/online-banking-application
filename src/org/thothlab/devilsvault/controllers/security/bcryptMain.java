package org.thothlab.devilsvault.controllers.security;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class bcryptMain {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String input = "1111";
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		String password = (passwordEncoder.encode(input));
		System.out.println("The input password is " + input);
		System.out.println("The encrypted Password is " + password);
		String input2="1111";
		System.out.println(passwordEncoder.matches(input2, password));
	}
}
