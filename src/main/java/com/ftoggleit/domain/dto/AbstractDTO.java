package com.ftoggleit.domain.dto;

public abstract class AbstractDTO {
    abstract protected Class getTypeClass();

    public String getType() {
        return getTypeClass().getSimpleName().toLowerCase();
    }

    public void setType(String type) {
        throw new UnsupportedOperationException("DTO is not enabled to set Type but setter is necessary for JSON IO");
    }
}
