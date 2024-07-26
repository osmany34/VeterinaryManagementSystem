package dev.patika.VeterinaryManagementSystem.dto.request.appointment;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentUpdateRequest {

    @NotNull(message = "Randevu ID'si boş olamaz.")
    @Positive(message = "Randevu ID'si pozitif olmalıdır.")
    private Long id;

    @NotNull(message = "Randevu Tarihi boş olamaz.")
    private LocalDateTime appointmentDateTime;

    @NotNull(message = "Hayvan ID'si boş olamaz.")
    @Positive(message = "Hayvan ID'si pozitif olmalıdır.")
    private Long animalId;

    @NotNull(message = "Doktor ID'si boş olamaz.")
    @Positive(message = "Doktor ID'si pozitif olmalıdır.")
    private Long doctorId;
}
