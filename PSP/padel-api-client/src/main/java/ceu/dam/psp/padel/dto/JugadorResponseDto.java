package ceu.dam.psp.padel.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JugadorResponseDto {
    private Long id;
    private String nombre;
    private String genero;
    private String nivel;
    private int edad;
}

