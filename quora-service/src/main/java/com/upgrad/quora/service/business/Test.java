package com.upgrad.quora.service.business;import ch.qos.logback.core.pattern.color.CyanCompositeConverter;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		PasswordCryptographyProvider cryptographyProvider=new PasswordCryptographyProvider();
		System.out.println(cryptographyProvider.encrypt("Mind@1234", "xyz123"));

	}

}
