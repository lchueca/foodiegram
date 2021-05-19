package main.persistence.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RoleEnum implements GrantedAuthority {
    ROLE_ADMIN, ROLE_MOD,ROLE_USER, ROLE_COL;

    @Override
    public String getAuthority() {
        return this.toString();
    }
}
