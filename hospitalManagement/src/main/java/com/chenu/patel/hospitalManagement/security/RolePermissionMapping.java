package com.chenu.patel.hospitalManagement.security;

import com.chenu.patel.hospitalManagement.entity.type.PermissionType;
import com.chenu.patel.hospitalManagement.entity.type.RoleType;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static com.chenu.patel.hospitalManagement.entity.type.PermissionType.*;
import static com.chenu.patel.hospitalManagement.entity.type.RoleType.*;

public class RolePermissionMapping {
    private static final Map<RoleType, Set<PermissionType>> map = Map.of(
            PATIENT,Set.of(PATIENT_READ,APPOINTEMNT_READ,APPOINTMENT_WRITE),
            DOCTOR,Set.of(APPOINTMENT_DELETE,APPOINTEMNT_READ,APPOINTMENT_WRITE,PATIENT_READ),
            ADMIN,Set.of(PATIENT_READ,APPOINTEMNT_READ,APPOINTMENT_WRITE,APPOINTMENT_DELETE,USER_MANAGE,REPORT_VIEW )
    );

    public static Set<SimpleGrantedAuthority> getAuthoritiesForRole(RoleType role) {

        return map.get(role).stream()
                .map(permission-> new SimpleGrantedAuthority(permission.getPermission()))
                .collect(Collectors.toSet());
    }

}
