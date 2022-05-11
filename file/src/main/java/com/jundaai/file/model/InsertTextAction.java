package com.jundaai.file.model;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue("insert")
public class InsertTextAction extends EditAction {


}
