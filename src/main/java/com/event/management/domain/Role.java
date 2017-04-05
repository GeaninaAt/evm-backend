package com.event.management.domain;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import java.util.List;

/**
 * Created by gatomulesei on 4/5/2017.
 */
@Entity
public class Role extends BaseEntity implements GrantedAuthority {

    private static final long serialVersionUID = 1L;

    private String authority;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "role")
    private List<User> users;

    public void setAuthority(String authority){
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
