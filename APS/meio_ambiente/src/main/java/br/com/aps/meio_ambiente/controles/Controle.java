package br.com.aps.meio_ambiente.controles;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import br.com.aps.meio_ambiente.servicos.Servicos;

@RestController
public class Controle {
    Servicos serv = new Servicos();

    @GetMapping("/")
    public String helloWorld() {
        return "Olá Mundo";
    }

    @GetMapping("/api/carregarInformacoes/{cidade}/{tp}")
    public String carregarInformacoes(@PathVariable String cidade, @PathVariable String tp) {
        return serv.carregarInformacoes(cidade, tp);
    }
}
