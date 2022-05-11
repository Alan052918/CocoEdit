package com.jundaai.user;

import lombok.*;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_id_sequence")
    @SequenceGenerator(name = "user_id_sequence", sequenceName = "user_id_sequence")
    private Long id;

    private String name;
    private String email;
    private String password;

    private ZonedDateTime dateTimeCreated;
    private ZonedDateTime dateTimeUpdated;

}
