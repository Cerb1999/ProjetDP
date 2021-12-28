package com.projetdp.request;

import com.projetdp.model.UserRole;
import lombok.*;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateUserRequest {
    private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;
    @Temporal(TemporalType.DATE)
    private final Date birthdate;
    @Enumerated(EnumType.STRING)
    private final UserRole role;
}
