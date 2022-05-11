package com.jundaai.file.model;

import lombok.*;
import net.minidev.json.annotate.JsonIgnore;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.List;

@Entity
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "file_id_sequence")
    @SequenceGenerator(name = "file_id_sequence", sequenceName = "file_id_sequence")
    private Long id;

    private String name;
    private String content;

    private ZonedDateTime dateTimeCreated;
    private ZonedDateTime dateTimeUpdated;

    @ElementCollection
    private List<Long> participantIds;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "file", orphanRemoval = true)
    @JsonIgnore
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<EditAction> editActions;

}
