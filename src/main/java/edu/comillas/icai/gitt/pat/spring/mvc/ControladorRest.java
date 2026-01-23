package edu.comillas.icai.gitt.pat.spring.mvc;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class ControladorRest {
    private final Map<String, ModeloContador> contadores = new HashMap<>();

    @PostMapping("/api/contadores")
    @ResponseStatus(HttpStatus.CREATED)
    public ModeloContador crea(@Valid @RequestBody ModeloContador contadorNuevo, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Si hay errores de validación, lanzar la excepción personalizada
            throw new ExcepcionContadorIncorrecto(bindingResult);
        }
        if (contadores.get(contadorNuevo.nombre())!= null) {
            // Si hay duplicados lanzo la excexpción de conflicto
            throw new ResponseStatusException(HttpStatus.CONFLICT);
        }
        contadores.put(contadorNuevo.nombre(), contadorNuevo);
        return contadorNuevo;
    }

    @GetMapping("/api/contadores/{nombre}")
    public ModeloContador contador(@PathVariable String nombre) {
        if (contadores.get(nombre)== null) {
            // Si no existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return contadores.get(nombre);
    }

    @PutMapping("/api/contadores/{nombre}/incremento/{incremento}")
    public ModeloContador incrementa(@PathVariable String nombre,
                                     @PathVariable Integer incremento) {
        if (contadores.get(nombre)== null) {
            // Si no existe
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        ModeloContador contadorActual = contadores.get(nombre);
        ModeloContador contadorIncrementado =
                new ModeloContador(nombre, contadorActual.valor() + incremento);
        contadores.put(nombre, contadorIncrementado);
        return contadorIncrementado;
    }
    @ExceptionHandler(ExcepcionContadorIncorrecto.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public List<ModeloCampoIncorrecto> contadorIncorrecto(ExcepcionContadorIncorrecto ex) {
        return ex.getErrores().stream().map(error -> new ModeloCampoIncorrecto(
                error.getDefaultMessage(), error.getField(), error.getRejectedValue()
        )).toList();
    }

}

