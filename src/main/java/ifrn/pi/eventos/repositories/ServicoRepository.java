package ifrn.pi.eventos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ifrn.pi.eventos.models.Evento;
import ifrn.pi.eventos.models.Resposta;

public interface ServicoRepository extends JpaRepository<Resposta, Long>{

	List<Resposta> findByEvento(Evento evento);
}
