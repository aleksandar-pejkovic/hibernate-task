package org.example.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Training {

    private Long id;

    private Long traineeId;

    private Long trainerId;

    private String trainingName;

    private Long trainingTypeId;

    private Date trainingDate;

    private int trainingDuration;
}
