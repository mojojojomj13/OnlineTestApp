/**
 *
 */
package com.exams.vo;

import org.springframework.security.core.GrantedAuthority;

/**
 * @author Prithvish Mukherjee
 */
public class Role implements GrantedAuthority {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8876010809831319426L;

	private String name;

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.security.core.GrantedAuthority#getAuthority()
	 */
	@Override
	public String getAuthority() {
		// TODO Auto-generated method stub
		return name;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}
}
