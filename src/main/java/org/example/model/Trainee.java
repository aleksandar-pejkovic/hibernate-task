package org.example.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Trainee {

    private Long id;

    private Date dateOfBirth;

    private String address;

    private Long userId;
}
