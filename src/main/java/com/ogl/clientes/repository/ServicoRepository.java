package com.ogl.clientes.repository;

import com.ogl.clientes.model.Servico;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ServicoRepository extends JpaRepository <Servico, Integer> {
}
