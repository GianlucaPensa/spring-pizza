package org.generation.italy.controller;

import javax.validation.Valid;

import org.generation.italy.model.Ingredienti;
import org.generation.italy.service.IngredientiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ingredienti")
public class IngredientiController {
	
	@Autowired
	private IngredientiService service;
	
	@GetMapping
	public String list(Model model) {
		model.addAttribute("list", service.findAllSortedByNome());
		return "/ingredienti/list";
	}
	
	@GetMapping("/create")
	public String create(Model model) {
		model.addAttribute("ingredienti", new Ingredienti());
		return "/ingredienti/edit";
	}
	
	@PostMapping("/create")
	public String doCreate(@Valid @ModelAttribute("ingredienti") Ingredienti ingredienti, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", false);
			return "/ingredienti/edit";
		}
		service.save(ingredienti);
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/delete/{id}")
	public String doDelete(Model model, @PathVariable("id") Integer id) {
		service.deleteById(id);
		return "redirect:/ingredienti";
	}
	
	@GetMapping("/edit/{id}")
	public String edit (@PathVariable("id") Integer id, Model model) {
		model.addAttribute("edit", true);
		model.addAttribute("ingredienti", service.getById(id));
		return "/ingredienti/edit";
	}
	
	@PostMapping("/edit/{id}")
	public String doUpdate(@Valid @ModelAttribute("ingredienti") Ingredienti ingredienti, BindingResult bindingResult, Model model) {
		if(bindingResult.hasErrors()) {
			model.addAttribute("edit", true);
			return "/ingredienti/edit";
		}
		service.save(ingredienti);
		return "redirect:/ingredienti";
	}
}
