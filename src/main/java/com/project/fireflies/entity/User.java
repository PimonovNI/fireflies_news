package com.project.fireflies.entity;

import com.project.fireflies.entity.enums.Role;
import com.project.fireflies.entity.enums.Status;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "users")
public class User {

    @MongoId
    @Field(name = "_id")
    private ObjectId id;

    @Field(name = "username")
    @Indexed(unique = true)
    private String username;

    @Field(name = "password")
    private String password;

    @Field(name = "email")
    private String email;

    @Field(name = "photo")
    private String photo;

    @Field(name = "description")
    private String description;

    @Field(name = "status")
    private Status status;

    @Field(name = "role")
    private Role role;

    @Field(name = "activation_code")
    private String activationCode;

    @Field(name = "created_at")
    private Instant createdAt;
}
