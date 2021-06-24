package com.backend.app.domain.entity;

import javax.persistence.*;
import java.util.List;

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;

    @OneToMany(targetEntity = Account.class,
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER)
    @OrderColumn
    private List<Account> accounts;

    public User() {

    }

    public void addAcounts(List<Account> accounts){
        for(Account account : accounts){
            account.setUser(this);
        }
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
