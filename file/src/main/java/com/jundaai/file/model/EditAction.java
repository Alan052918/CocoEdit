package com.jundaai.file.model;


import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "edit_action_type", discriminatorType = DiscriminatorType.STRING)
public class EditAction {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "edit_action_id_sequence")
    @SequenceGenerator(name = "edit_action_id_sequence", sequenceName = "edit_action_id_sequence")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "file_id", referencedColumnName = "id")
    private File file;

    private Long position;

}
