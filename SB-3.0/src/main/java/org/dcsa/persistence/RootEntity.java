package org.dcsa.persistence;

import lombok.*;
import jakarta.persistence.*;

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
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany
    /*
    @JoinTable(
            name = "vessel_schedule",
            joinColumns = @JoinColumn(name = "service_id"),
            inverseJoinColumns = @JoinColumn(name = "vessel_id"))
     */
    private Set<SubEntity> subEntities = new LinkedHashSet<>();
}
