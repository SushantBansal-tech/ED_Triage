package com.acme.encounter.entity;

import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Getter
@Builder
public class CurrentUserPrincipal implements UserDetails {
    private Long userId;
    private String username;
    private String password;
    private List<String> roles;
    private Long patientId;
    private Long staffId;
    private List<Long> facilityScope;
    private List<Long> departmentScope;
    private Collection<? extends GrantedAuthority> authorities;

    @Override
    public String getUsername() { return username; }

    @Override
    public String getPassword() { return password; }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() { return authorities; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return true; }

    public boolean hasRole(String role) {
        return roles != null && roles.contains(role);
    }
}