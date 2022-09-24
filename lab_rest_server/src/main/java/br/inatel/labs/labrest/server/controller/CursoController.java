package br.inatel.labs.labrest.server.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import br.inatel.labs.labrest.server.exception.CursoNaoEncontradoException;
import br.inatel.labs.labrest.server.model.Curso;
import br.inatel.labs.labrest.server.service.CursoService;

@RestController
@RequestMapping("/curso")
public class CursoController {
	
	@Autowired
	private CursoService servico;
	
	@GetMapping
	public List<Curso> listar() {
		List<Curso> listaCurso = servico.pesquisarCurso();
		return listaCurso;
	}
	
	@GetMapping("/{id}")
	public Curso buscar(@PathVariable("id") Long cursoId) {
		Optional<Curso> opCurso = servico.buscarCursoPeloId(cursoId);
		if(opCurso.isPresent()) {
			return opCurso.get();
		} else {
			throw new CursoNaoEncontradoException(cursoId);
		}	
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED)
	public Curso criar(@RequestBody Curso curso) {
		Curso cursoCriado = servico.criarCurso(curso);
		return cursoCriado;
	}
	
	@PutMapping //atualizar todo o objeto (para atualizar apenas um atributo é o PATCH)
	@ResponseStatus(code = HttpStatus.ACCEPTED)
	public void atualizar(@RequestBody Curso curso) {
		servico.atualizarCurso(curso);
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT)
	public void remover(@PathVariable("id") Long cursoIdParaRemover) {
		Optional<Curso> opCurso = servico.buscarCursoPeloId(cursoIdParaRemover);
		if (opCurso.isEmpty()) {
			throw new CursoNaoEncontradoException(cursoIdParaRemover);
		} else {
			Curso cursoASerRemovido = opCurso.get();
			servico.removerCursoPeloId(cursoASerRemovido);
		}
	}
	
	@GetMapping("/pesquisa")
	public List<Curso> listarPeloFragDescricao(@RequestParam("descricao") String fragDescricao) {
		return servico.pesquisarCursoPeloFragDescricao(fragDescricao);
	}
	

}
