package br.inatel.labs.labrest.server.controller;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.reactive.server.WebTestClient;

import br.inatel.labs.labrest.server.model.Curso;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CursoControllerTest {

	@Autowired
	private WebTestClient webTestClient;
	
	@Test
	void deveListarCursos() {
		
		webTestClient.get()
		.uri("/curso")
		.exchange()
		.expectStatus()
			.isOk()
		.expectBody()
			.returnResult();	
	}
	
	@Test
	void dadoCursoIdValido_quandoGetCursoPeloId_entaoRespondeComCursoValido() {
		Long cursoIdValido = 1L;
		Curso cursoRespondido = webTestClient.get()
				.uri("/curso/" + cursoIdValido)
				.exchange()
				.expectStatus().isOk()
				.expectBody(Curso.class)
					.returnResult()
					.getResponseBody();
		assertNotNull(cursoRespondido);
		assertEquals(cursoRespondido.getId(), cursoIdValido);
	}
	
	@Test
	void dadoCursoIdInvalido_quandoGetCursoPeloId_entaoRespondeComStatusNotFound() {
		Long cursoIdInvalido = 99L;
		webTestClient.get()
			.uri("/curso/" + cursoIdInvalido)
			.exchange()
			.expectStatus().isNotFound();
	}
	
	@Test
	void dadoNovoCurso_quandoPostCurso_entaoRespondeComStatusCreatedECursoValido() {
		Curso novoCurso = new Curso();
		novoCurso.setDescricao("Descricao teste");
		novoCurso.setCargaHoraria(100);
		Curso cursoRespondido = webTestClient.post()
			.bodyValue(novoCurso)
			.exchange()
			.expectStatus().isCreated()
			.expectBody(Curso.class)
				.returnResult()
				.getResponseBody();
		assertNotNull(cursoRespondido);
		assertNotNull(cursoRespondido.getId());
	}
	
	@Test
	void dadoAtualizarCurso_quandoPutCurso_entaoRespondeComStatusAccepted() {
		Curso cursoValido = new Curso();
		cursoValido.setDescricao("Descricao teste");
		cursoValido.setCargaHoraria(100);
		webTestClient.put()
			.uri("/curso")
			.bodyValue(cursoValido)
			.exchange()
			.expectStatus().isAccepted();
	}
	
	@Test
	void dadoCursoIdValido_quandoDeleteCursoPeloId_entaoRespondeComCursoValido() {
		Long cursoIdValido = 2L; //utilizou-se 2L para não usar um teste que vá influenciar em outro teste
		webTestClient.delete()
			.uri("/curso/" + cursoIdValido)
			.exchange()
			.expectStatus().isNoContent()
			.expectBody().isEmpty();
	}
	
	@Test
	void dadoCursoIdInvalido_quandoDeleteCursoPeloId_entaoRespondeComStatusNotFound() {
		Long cursoIdInvalido = null;
		webTestClient.delete()
			.uri("/curso/" + cursoIdInvalido)
			.exchange()
			.expectStatus().isNotFound();
	}
	
}
