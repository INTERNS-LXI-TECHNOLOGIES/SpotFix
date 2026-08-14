package com.divisosofttech.spot_fix.repository;

import com.divisosofttech.spot_fix.domain.Authority;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;

/**
 * Spring Data R2DBC repository for the Authority entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AuthorityRepository extends R2dbcRepository<Authority, String> {}
