package com.example.blogging.security;

import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class DynamicRoleHierarchy implements RoleHierarchy {

    private volatile RoleHierarchy delegate;

    public DynamicRoleHierarchy(String initialHierarchy) {
        this.delegate = RoleHierarchyImpl.fromHierarchy(initialHierarchy);
    }

    public void updateHierarchy(String hierarchyString) {
        this.delegate = RoleHierarchyImpl.fromHierarchy(hierarchyString);
    }

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return delegate.getReachableGrantedAuthorities(authorities);
    }
}
