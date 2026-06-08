package com.chenu.patel.hospitalManagement.dto;

import com.chenu.patel.hospitalManagement.entity.type.BloodGroup;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBloodGroupCountEntity {

    private BloodGroup bloodGroup;
    private Long count;

}