package com.backend.app.domain.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@NamedNativeQuery(
        name = "User.findByToken",
        query = "SELECT * FROM user WHERE token=:TOKEN",
        resultClass = User.class
)

@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String token;
    private LocalDateTime userCreationTime;

    @OneToMany(targetEntity = Account.class,
        mappedBy = "user",
        cascade = CascadeType.ALL,
        fetch = FetchType.EAGER)
    @OrderColumn
    private List<Account> accounts;

    public User() {

    }

    public User(String token, List<Account> accounts) {
        this.userCreationTime = LocalDateTime.now();
        this.token = token;
        this.accounts = accounts;
    }

    public void addAcount(Account account){
        if(account != null) {
            account.setUser(this);
        }
    }

    public Long getId() {
        return id;
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

    public LocalDateTime getUserCreationTime() {
        return userCreationTime;
    }
}
