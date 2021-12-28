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
@EqualsAndHashCode(exclude = {"receiver", "sender"})
@NoArgsConstructor
@Entity
@Table(name = "notifications")
public class Notification {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    private String message;

    @Temporal(TemporalType.DATE)
    private Date createdAt;

    private boolean isRead;

    @ManyToOne
    private User receiver;

    @ManyToOne
    private User sender;

    public Notification(String message, Date createdAt, boolean isRead, User receiver, User sender) {
        this.message = message;
        this.createdAt = createdAt;
        this.isRead = isRead;
        this.receiver = receiver;
        this.sender = sender;
    }
}