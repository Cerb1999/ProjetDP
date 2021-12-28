package com.projetdp.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.*;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"notificationsReceivers", "notificationsSenders", "activities", "friendsFirst", "friendsSecond"})
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    private String firstname;
    private String lastname;
    @Temporal(TemporalType.DATE)
    private Date birthdate;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @OneToMany(targetEntity = Notification.class, mappedBy = "receiver", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Notification> notificationsReceivers = new HashSet<>();
    @OneToMany(targetEntity = Notification.class, mappedBy = "sender", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Notification> notificationsSenders = new HashSet<>();
    @OneToMany(targetEntity = Activity.class, mappedBy = "user",cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Activity> activities = new HashSet<>();
    @OneToMany(targetEntity = Friend.class, mappedBy = "firstUser", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Friend> friendsFirst = new HashSet<Friend>();
    @OneToMany(targetEntity = Friend.class,mappedBy = "secondUser", cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
    private Set<Friend> friendsSecond = new HashSet<Friend>();


    public User(String username, String password, String firstname, String lastname, Date birthdate, UserRole role) {
        this.username = username;
        this.password = password;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.role = role;
    }

    public void clone(User user) {
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.birthdate = user.getBirthdate();
        this.role = user.getRole();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(role.name());
        return Collections.singleton(authority);
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
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
