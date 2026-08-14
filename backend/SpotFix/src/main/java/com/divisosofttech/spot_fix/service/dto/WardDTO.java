package com.divisosofttech.spot_fix.service.dto;

import jakarta.persistence.Lob;
import jakarta.validation.constraints.*;
import java.io.Serializable;
import java.util.Objects;

/**
 * A DTO for the {@link com.divisosofttech.spot_fix.domain.Ward} entity.
 */
@SuppressWarnings("common-java:DuplicatedBlocks")
public class WardDTO implements Serializable {

    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 20)
    private String code;

    @NotNull(message = "must not be null")
    @Size(max = 100)
    private String name;

    @Size(max = 100)
    private String municipality;

    @Lob
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof WardDTO)) {
            return false;
        }

        WardDTO wardDTO = (WardDTO) o;
        if (this.id == null) {
            return false;
        }
        return Objects.equals(this.id, wardDTO.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(this.id);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "WardDTO{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", municipality='" + getMunicipality() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
