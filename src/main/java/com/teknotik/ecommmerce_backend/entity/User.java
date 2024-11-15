package com.teknotik.ecommmerce_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "user", schema = "ecommerce")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "user_role", schema = "ecommerce", joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    Set<Role> roles;

    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH}, mappedBy = "user")
    Set<Address> addresses;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    Set<CreditCard> cards;


    public User(String name, String email, String password, int roleId) {
        this.name = name;
        this.email = email;
        this.password = password;
        switch (roleId) {
            case 1:
                Role role1 = new Role(1, "admin", "Yönetici");
                roles.add(role1);
                break;
            case 2:
                Role role2 = new Role(2, "store", "MAĞAZA");
                roles.add(role2);
                break;
            case 3:
                Role role3 = new Role(3, "customer", "MÜŞTERİ");
                roles.add(role3);
                break;
        }
    }


    public void addCart(CreditCard creditCard){
        if(cards == null){
            this.cards=new HashSet<>();
            cards.add(creditCard);
        }
        cards.add(creditCard);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
