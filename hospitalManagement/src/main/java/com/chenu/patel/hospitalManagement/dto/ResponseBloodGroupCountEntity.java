package com.chenu.patel.hospitalManagement.dto;

import com.chenu.patel.hospitalManagement.entity.type.BloodGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResponseBloodGroupCountEntity {
    private BloodGroup bloodGroup;
    private Long count;
}
