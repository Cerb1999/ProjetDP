package com.projetdp.request;

import lombok.*;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationUserRequest {
    private final String username;
    private final String password;
    private final String firstname;
    private final String lastname;
    @Temporal(TemporalType.DATE)
    private final Date birthdate;
}
