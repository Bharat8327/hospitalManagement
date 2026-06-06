INSERT INTO patient (name, email, birthDate, gender, bloodGroup)
VALUES
    ('John Doe', 'john.doe@example.com', '1990-05-15', 'MALE', 'O_POSITIVE'),
    ('Jane Smith', 'jane.smith@example.com', '1988-09-22', 'FEMALE', 'A_POSITIVE'),
    ('Michael Johnson', 'michael.johnson@example.com', '1995-01-10', 'MALE', 'B_POSITIVE'),
    ('Emily Davis', 'emily.davis@example.com', '1992-07-30', 'FEMALE', 'AB_POSITIVE'),
    ('Robert Wilson', 'robert.wilson@example.com', '1985-12-05', 'MALE', 'A_POSITIVE');
INSERT INTO doctor (name, email, specialization)
VALUES
    ('Dr. Sinha', 'sinha@gmail.com', 'Cardiology'),
    ('Dr. Sneha Kapoor', 'snehakapoor212@gmail.com', 'Dermatology'),
    ('Dr. Arjun Lal', 'arjunlal@gmail.com', 'Orthopedics');

INSERT INTO appointment
(appointmenttime, reason, patient_id, doctor_id)
VALUES
    ('2026-06-10 10:00:00', 'Regular health checkup', 1, 1),
    ('2026-06-11 14:30:00', 'Skin allergy consultation', 2, 2),
    ('2026-06-11 14:30:00', 'Skin allergy consultation', 2, 3),
    ('2026-06-12 09:15:00', 'Knee pain evaluation', 3, 3),
    ('2026-06-12 09:15:00', 'Knee pain evaluation', 2, 1);