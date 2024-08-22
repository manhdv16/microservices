package com.dvm.profile.entity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@Node("Department")
public class Department {

    @Id
    @GeneratedValue
    Long id;

    String name;

    @Relationship(type = "WORKS_IN", direction = Relationship.Direction.INCOMING)
    List<UserProfile> users;
}
