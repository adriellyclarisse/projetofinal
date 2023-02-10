package ifrn.pi.eventos.controllers;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import ifrn.pi.eventos.models.Evento;
import ifrn.pi.eventos.repositories.EventoRepository;

@Controller
@RequestMapping("/eventos")
public class EventosController {
	
	@Autowired
	private EventoRepository er;
	
	@GetMapping("/form")
	public String form(Evento evento) {
		return "eventos/formEvento";
	}
	
	@PostMapping
	public String salvar(@Valid Evento evento, BindingResult result, RedirectAttributes attributes) {
		
		if(result.hasErrors()){
			return form(evento);
			
		}
		
		System.out.println(evento);
		er.save(evento);
		attributes.addFlashAttribute("mensagem", "Seu evento foi alterado com sucesso!");
		
		return "redirect:/eventos";
	}
	
	@GetMapping
	public ModelAndView listar() {
		List<Evento> eventos = er.findAll();
		ModelAndView mv = new ModelAndView("eventos/lista");
		mv.addObject("eventos", eventos);
		return mv;	
	}
	
	@GetMapping("/{id}")
	public ModelAndView detalhar(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		
		if(opt.isEmpty()) {
			md.setViewName("redirect:/eventos");
			return md;
			
		}
		
		md.setViewName("eventos/detalhes");
		Evento evento = opt.get();
		md.addObject("evento", evento);
		
		return md;
		
	}
	
	@GetMapping("/{id}/selecionar")
	public ModelAndView selecionarEvento(@PathVariable Long id) {
		ModelAndView md = new ModelAndView();
		Optional<Evento> opt = er.findById(id);
		if(opt.isEmpty()){
			md.setViewName("redirect:/eventos");
			return md;
		}
		
		Evento evento = opt.get();
		md.setViewName("eventos/formEvento");
		md.addObject("evento", evento);

		return md;

	}

	
	@GetMapping("/{id}/remover")
	public String apagarEvento(@PathVariable Long id, RedirectAttributes attributes) {
		
		Optional<Evento> opt = er.findById(id);
		
		if(!opt.isEmpty()) {
			Evento evento = opt.get();
			er.delete(evento);
			attributes.addFlashAttribute("mensgem", "Evento removido com sucesso!");
		}
		
		return "redirect:/eventos";
		
	}
	
}
