package br.inatel.labs.labrest.server.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.inatel.labs.labrest.server.model.Curso;
import br.inatel.labs.labrest.server.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {
	
	private CursoService servico;
	
	@GetMapping
	public List<Curso> listar() {
		List<Curso> cursos = servico.pesquisarCurso();
		return cursos;
		
	}

}
