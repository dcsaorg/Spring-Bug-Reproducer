package org.dcsa.persistence;

import javax.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter(AccessLevel.PRIVATE)
@Entity
@Table(name = "sub_entity")
public class SubEntity {

    @Id
    @Column(name = "id", nullable = false)
    private Long subEntityID;

    @Column(name = "name", nullable = false)
    private String name;
}
