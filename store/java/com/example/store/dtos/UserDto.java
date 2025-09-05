package com.example.store.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@AllArgsConstructor
public class UserDto {
    //this ignore this field. - serialization
    //@JsonIgnore
    @JsonProperty("user_id") //rename field
    private Integer id;
    private String name;
    private String email;
}
