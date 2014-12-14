package com.featuretoggle.domain.dto;

import com.featuretoggle.domain.Identifiable;

public abstract class IdentifiableDTO {
    private String id;

    public IdentifiableDTO(Identifiable identifiable) {
        if (identifiable != null) {
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
