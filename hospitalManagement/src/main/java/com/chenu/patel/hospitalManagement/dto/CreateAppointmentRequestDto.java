package com.chenu.patel.hospitalManagement.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateAppointmentRequestDto {
    private Long doctorId;
    private Long patientId;
    private String appointmentTime;
    private String reason;
}
