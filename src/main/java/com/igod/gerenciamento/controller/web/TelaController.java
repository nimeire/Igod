package com.igod.gerenciamento.controller.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class TelaController {
    @GetMapping("/cozinha/{id}")
    public String cozinha(@PathVariable Long id, Model model) {
        model.addAttribute("estacaoId", id);
        return "cozinha";
    }
}
