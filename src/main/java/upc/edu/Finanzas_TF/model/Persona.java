package upc.edu.Finanzas_TF.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="nombre", length =50, nullable=false)
    private String nombre;

    @Column(name="direccion", length =200, nullable=false)
    private String direccion;

    @Column(name="telefono", length =9, nullable=false)
    private String telefono;

    @Column(name="correo", length =50, nullable=false)
    private String correo;

    @Column(name="dni", length =50, nullable=false)
    private String dni;

    @OneToOne(mappedBy = "persona", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private Credito credito;

    @OneToMany(mappedBy = "persona", cascade = CascadeType.ALL,fetch = FetchType.EAGER, orphanRemoval = true)
    private List<Compra> compras = new ArrayList<>();
}
