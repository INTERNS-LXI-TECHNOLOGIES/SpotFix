package com.divisosofttech.spot_fix.domain;

import jakarta.validation.constraints.*;
import java.io.Serial;
import java.io.Serializable;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * A Location.
 */
@Table("location")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Location implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @Column("id")
    private Long id;

    @NotNull(message = "must not be null")
    @Size(max = 500)
    @Column("address_text")
    private String addressText;

    @Size(max = 150)
    @Column("landmark")
    private String landmark;

    @DecimalMin(value = "-90")
    @DecimalMax(value = "90")
    @Column("latitude")
    private Double latitude;

    @DecimalMin(value = "-180")
    @DecimalMax(value = "180")
    @Column("longitude")
    private Double longitude;

    @org.springframework.data.annotation.Transient
    private Ward ward;

    @Column("ward_id")
    private Long wardId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Location id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressText() {
        return this.addressText;
    }

    public Location addressText(String addressText) {
        this.setAddressText(addressText);
        return this;
    }

    public void setAddressText(String addressText) {
        this.addressText = addressText;
    }

    public String getLandmark() {
        return this.landmark;
    }

    public Location landmark(String landmark) {
        this.setLandmark(landmark);
        return this;
    }

    public void setLandmark(String landmark) {
        this.landmark = landmark;
    }

    public Double getLatitude() {
        return this.latitude;
    }

    public Location latitude(Double latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return this.longitude;
    }

    public Location longitude(Double longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Ward getWard() {
        return this.ward;
    }

    public void setWard(Ward ward) {
        this.ward = ward;
        this.wardId = ward != null ? ward.getId() : null;
    }

    public Location ward(Ward ward) {
        this.setWard(ward);
        return this;
    }

    public Long getWardId() {
        return this.wardId;
    }

    public void setWardId(Long ward) {
        this.wardId = ward;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Location)) {
            return false;
        }
        return getId() != null && getId().equals(((Location) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Location{" +
            "id=" + getId() +
            ", addressText='" + getAddressText() + "'" +
            ", landmark='" + getLandmark() + "'" +
            ", latitude=" + getLatitude() +
            ", longitude=" + getLongitude() +
            "}";
    }
}
