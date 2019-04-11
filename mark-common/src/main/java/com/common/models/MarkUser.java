package com.common.models;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
public class MarkUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Integer id;
	private String username;
	private String password;
	private Collection<? extends GrantedAuthority> authorities;
	private Boolean accountNonExpired = true;
	private Boolean accountNonLocked = true;
	private Boolean credentialsNonExpired = true;
	private Boolean enabled;
    private String emailId;
    private String parentId;
    private String crn;
    private String hashedUserImage;
    


	public MarkUser(Integer id, String username, String password,
			Collection<? extends GrantedAuthority> authorities,
			String emailId, boolean isActive,String parentId,String crn, String hashedUserImage) {

		this.setId(id);
		this.setUsername(username);
		this.setPassword(password);
		this.setAuthorities(authorities);
		this.setEmailId(emailId);		
        this.setEnabled(isActive);
        this.setParentId(parentId);
        this.setCrn(crn);
        this.setHashedUserImage(hashedUserImage);
	}

	@JsonIgnore
	public String getPassword() {
		return this.password;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.authorities;
	}

	@JsonIgnore
	public Boolean getAccountNonExpired() {
		return this.accountNonExpired;
	}

	@Override
	public boolean isAccountNonExpired() {
		return this.getAccountNonExpired();
	}

	@JsonIgnore
	public Boolean getAccountNonLocked() {
		return this.accountNonLocked;
	}

	@Override
	public boolean isAccountNonLocked() {
		return this.getAccountNonLocked();
	}

	@JsonIgnore
	public Boolean getCredentialsNonExpired() {
		return this.credentialsNonExpired;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return this.getCredentialsNonExpired();
	}

	@JsonIgnore
	public Boolean getEnabled() {
		return this.enabled;
	}

	@Override
	public boolean isEnabled() {
		return this.getEnabled();
	}
}