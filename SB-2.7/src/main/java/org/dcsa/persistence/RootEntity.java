package org.dcsa.persistence;

import lombok.*;
import javax.persistence.*;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "root_entity")
public class RootEntity {
    @Id
    @Column(name = "id")
    private Long id;

    @OneToMany
    @JoinTable(
            name = "root_entity_sub_entity",
            joinColumns = @JoinColumn(name = "root_entity_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_entity_id"))
    private Set<SubEntity> subEntities = new LinkedHashSet<>();
}
