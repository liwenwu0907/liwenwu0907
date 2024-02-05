package com.example.demo.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldNameConstants;
import org.springframework.data.annotation.Id;

@Data
@FieldNameConstants
@AllArgsConstructor
@NoArgsConstructor
public class IndexSearch {

    @Id
    private Integer id;

    private String firstname;

    private String lastname;
}
