package com.jundaai.file.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("delete")
public class DeleteTextAction extends EditAction {

    private Long offset;
}
