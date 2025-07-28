package com.ogl.clientes.controller;

import com.ogl.clientes.model.Cliente;
import com.ogl.clientes.model.ServicoPrestado;
import com.ogl.clientes.model.dto.ServicoPrestadoDTO;
import com.ogl.clientes.repository.ClienteRepository;
import com.ogl.clientes.repository.ServicoPrestadoRepository;
import com.ogl.clientes.util.BigDecimalConverter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/api/servicos-prestados")
@RequiredArgsConstructor
public class ServicoPrestadoController {

    private final ClienteRepository clienteRepository;
    private final ServicoPrestadoRepository servicoPrestadoRepository;
    private final BigDecimalConverter bigDecimalConverter;
    
    @PostMapping("/salvar")
    @ResponseStatus(HttpStatus.CREATED)
    public ServicoPrestado salvar(@RequestBody @Valid ServicoPrestadoDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getIdCliente()).orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cliente inexistente."));

        ServicoPrestado servicoPrestado = new ServicoPrestado();
        servicoPrestado.setDescricao(dto.getDescricao());
        servicoPrestado.setData(LocalDate.parse(dto.getData(), DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        servicoPrestado.setCliente(cliente);
        servicoPrestado.setValor(bigDecimalConverter.converter(dto.getPreco()));
        return servicoPrestadoRepository.save(servicoPrestado);
    }

    @GetMapping
    public List<ServicoPrestado> pesquisar(@RequestParam(value = "nome", required = false, defaultValue = "") String nome,
                                           @RequestParam(value = "mes", required = false, defaultValue = "") Integer mes) {

        return servicoPrestadoRepository.findByNomeClientAndMes("%" + nome + "%", mes);

    }

}
