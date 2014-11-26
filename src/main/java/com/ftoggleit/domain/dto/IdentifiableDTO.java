package com.ftoggleit.domain.dto;

import com.ftoggleit.domain.Identifiable;

public abstract class IdentifiableDTO extends AbstractDTO {
    private String id;

    public IdentifiableDTO(Identifiable identifiable) {
        if(identifiable != null) {
            id = identifiable.getId().toString();
        }
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
