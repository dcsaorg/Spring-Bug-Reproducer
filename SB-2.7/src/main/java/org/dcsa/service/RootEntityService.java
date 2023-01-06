package org.dcsa.service;

import javax.persistence.criteria.Predicate;
import lombok.RequiredArgsConstructor;
import org.dcsa.persistence.RootEntity;
import org.dcsa.repository.RootEntityRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@RequiredArgsConstructor
@Service
public class RootEntityService {

    private final RootEntityRepository rootEntityRepository;

    public Page<RootEntity> findAll(String withSubEntityName, PageRequest pageRequest) {
        return rootEntityRepository.findAll(withFilters(withSubEntityName), pageRequest);
    }

    private static Specification<RootEntity> withFilters(String withSubEntityName) {
        return (root, query, builder) -> {
            var predicates = new ArrayList<Predicate>();
            // Do the JOIN unconditionally to avoid hiding the difference when there is no filtering.
            var subEntityJoin = root.join("subEntities");
            if (withSubEntityName != null) {
                predicates.add(builder.equal(subEntityJoin.get("name"), withSubEntityName));
            }
            return builder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
