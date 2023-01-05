package org.dcsa.repository;

import lombok.NonNull;
import org.dcsa.persistence.RootEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface RootEntityRepository extends JpaRepository<RootEntity, Long>, JpaSpecificationExecutor<RootEntity> {
    Page<RootEntity> findAll(Specification<RootEntity> spec, @NonNull Pageable pageable);
}
