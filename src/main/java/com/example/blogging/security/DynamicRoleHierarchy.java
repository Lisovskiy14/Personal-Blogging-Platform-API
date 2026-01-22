package com.example.blogging.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Slf4j
public class DynamicRoleHierarchy implements RoleHierarchy {

    private volatile RoleHierarchy delegate;

    public DynamicRoleHierarchy(String initialHierarchy) {
        this.delegate = RoleHierarchyImpl.fromHierarchy(initialHierarchy);
        log.info("Initialized role hierarchy:\n{}", initialHierarchy);
    }

    public void updateHierarchy(String hierarchyString) {
        this.delegate = RoleHierarchyImpl.fromHierarchy(hierarchyString);
        log.info("Role hierarchy was updated:\n{}", hierarchyString);
    }

    @Override
    public Collection<? extends GrantedAuthority> getReachableGrantedAuthorities(Collection<? extends GrantedAuthority> authorities) {
        return delegate.getReachableGrantedAuthorities(authorities);
    }
}
