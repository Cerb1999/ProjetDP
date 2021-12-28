package com.projetdp.model;


import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@EqualsAndHashCode(exclude = {"firstUser", "secondUser"})
@NoArgsConstructor
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    private Date createdDate;
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinColumn(name = "first_user_id", referencedColumnName = "id")
    User firstUser;
    @ManyToOne(cascade = { CascadeType.MERGE, CascadeType.PERSIST }, fetch = FetchType.LAZY)
    @JoinColumn(name = "second_user_id", referencedColumnName = "id")
    User secondUser;

    @Enumerated(EnumType.STRING)
    private FriendState state;

    public Friend(Date createdDate, User firstUser, User secondUser, FriendState state) {
        this.createdDate = createdDate;
        this.firstUser = firstUser;
        this.secondUser = secondUser;
        this.state = state;
    }
}
