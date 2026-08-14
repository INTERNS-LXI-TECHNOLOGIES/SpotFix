package com.divisosofttech.spot_fix.domain;

import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Ward.
 */
@Table("ward")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Ward implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 20)
    @Column("code")
    private String code;

    @NotNull(message = "must not be null")
    @Size(max = 100)
    @Column("name")
    private String name;

    @Size(max = 100)
    @Column("municipality")
    private String municipality;

    @Column("description")
    private String description;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Ward id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCode() {
        return this.code;
    }

    public Ward code(String code) {
        this.setCode(code);
        return this;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return this.name;
    }

    public Ward name(String name) {
        this.setName(name);
        return this;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMunicipality() {
        return this.municipality;
    }

    public Ward municipality(String municipality) {
        this.setMunicipality(municipality);
        return this;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getDescription() {
        return this.description;
    }

    public Ward description(String description) {
        this.setDescription(description);
        return this;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Ward)) {
            return false;
        }
        return getId() != null && getId().equals(((Ward) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Ward{" +
            "id=" + getId() +
            ", code='" + getCode() + "'" +
            ", name='" + getName() + "'" +
            ", municipality='" + getMunicipality() + "'" +
            ", description='" + getDescription() + "'" +
            "}";
    }
}
