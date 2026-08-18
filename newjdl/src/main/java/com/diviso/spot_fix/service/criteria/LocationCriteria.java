package com.diviso.spot_fix.service.criteria;

import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;
import java.util.Optional;
import org.springdoc.core.annotations.ParameterObject;
import tech.jhipster.service.Criteria;
import tech.jhipster.service.filter.*;

/**
 * Criteria class for the {@link com.diviso.spot_fix.domain.Location} entity. This class is used
 * in {@link com.diviso.spot_fix.web.rest.LocationResource} to receive all the possible filtering options from
 * the Http GET request parameters.
 * For example the following could be a valid request:
 * {@code /locations?id.greaterThan=5&attr1.contains=something&attr2.specified=false}
 * As Spring is unable to properly convert the types, unless specific {@link Filter} class are used, we need to use
 * fix type specific filters.
 */
@ParameterObject
@SuppressWarnings("common-java:DuplicatedBlocks")
public class LocationCriteria implements Serializable, Criteria {

    @Serial
    private static final long serialVersionUID = 1L;

    private LongFilter id;

    private StringFilter addressText;

    private StringFilter landmark;

    private DoubleFilter latitude;

    private DoubleFilter longitude;

    private LongFilter wardId;

    private Boolean distinct;

    public LocationCriteria() {}

    public LocationCriteria(LocationCriteria other) {
        this.id = other.optionalId().map(LongFilter::copy).orElse(null);
        this.addressText = other.optionalAddressText().map(StringFilter::copy).orElse(null);
        this.landmark = other.optionalLandmark().map(StringFilter::copy).orElse(null);
        this.latitude = other.optionalLatitude().map(DoubleFilter::copy).orElse(null);
        this.longitude = other.optionalLongitude().map(DoubleFilter::copy).orElse(null);
        this.wardId = other.optionalWardId().map(LongFilter::copy).orElse(null);
        this.distinct = other.distinct;
    }

    @Override
    public LocationCriteria copy() {
        return new LocationCriteria(this);
    }

    public LongFilter getId() {
        return id;
    }

    public Optional<LongFilter> optionalId() {
        return Optional.ofNullable(id);
    }

    public LongFilter id() {
        if (id == null) {
            setId(new LongFilter());
        }
        return id;
    }

    public void setId(LongFilter id) {
        this.id = id;
    }

    public StringFilter getAddressText() {
        return addressText;
    }

    public Optional<StringFilter> optionalAddressText() {
        return Optional.ofNullable(addressText);
    }

    public StringFilter addressText() {
        if (addressText == null) {
            setAddressText(new StringFilter());
        }
        return addressText;
    }

    public void setAddressText(StringFilter addressText) {
        this.addressText = addressText;
    }

    public StringFilter getLandmark() {
        return landmark;
    }

    public Optional<StringFilter> optionalLandmark() {
        return Optional.ofNullable(landmark);
    }

    public StringFilter landmark() {
        if (landmark == null) {
            setLandmark(new StringFilter());
        }
        return landmark;
    }

    public void setLandmark(StringFilter landmark) {
        this.landmark = landmark;
    }

    public DoubleFilter getLatitude() {
        return latitude;
    }

    public Optional<DoubleFilter> optionalLatitude() {
        return Optional.ofNullable(latitude);
    }

    public DoubleFilter latitude() {
        if (latitude == null) {
            setLatitude(new DoubleFilter());
        }
        return latitude;
    }

    public void setLatitude(DoubleFilter latitude) {
        this.latitude = latitude;
    }

    public DoubleFilter getLongitude() {
        return longitude;
    }

    public Optional<DoubleFilter> optionalLongitude() {
        return Optional.ofNullable(longitude);
    }

    public DoubleFilter longitude() {
        if (longitude == null) {
            setLongitude(new DoubleFilter());
        }
        return longitude;
    }

    public void setLongitude(DoubleFilter longitude) {
        this.longitude = longitude;
    }

    public LongFilter getWardId() {
        return wardId;
    }

    public Optional<LongFilter> optionalWardId() {
        return Optional.ofNullable(wardId);
    }

    public LongFilter wardId() {
        if (wardId == null) {
            setWardId(new LongFilter());
        }
        return wardId;
    }

    public void setWardId(LongFilter wardId) {
        this.wardId = wardId;
    }

    public Boolean getDistinct() {
        return distinct;
    }

    public Optional<Boolean> optionalDistinct() {
        return Optional.ofNullable(distinct);
    }

    public Boolean distinct() {
        if (distinct == null) {
            setDistinct(true);
        }
        return distinct;
    }

    public void setDistinct(Boolean distinct) {
        this.distinct = distinct;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final LocationCriteria that = (LocationCriteria) o;
        return (
            Objects.equals(id, that.id) &&
            Objects.equals(addressText, that.addressText) &&
            Objects.equals(landmark, that.landmark) &&
            Objects.equals(latitude, that.latitude) &&
            Objects.equals(longitude, that.longitude) &&
            Objects.equals(wardId, that.wardId) &&
            Objects.equals(distinct, that.distinct)
        );
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, addressText, landmark, latitude, longitude, wardId, distinct);
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "LocationCriteria{" +
            optionalId().map(f -> "id=" + f + ", ").orElse("") +
            optionalAddressText().map(f -> "addressText=" + f + ", ").orElse("") +
            optionalLandmark().map(f -> "landmark=" + f + ", ").orElse("") +
            optionalLatitude().map(f -> "latitude=" + f + ", ").orElse("") +
            optionalLongitude().map(f -> "longitude=" + f + ", ").orElse("") +
            optionalWardId().map(f -> "wardId=" + f + ", ").orElse("") +
            optionalDistinct().map(f -> "distinct=" + f + ", ").orElse("") +
        "}";
    }
}
