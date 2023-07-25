package br.ufscar.dc.compiladores.alguma.semantico;

import java.util.LinkedList;
import java.util.List;

public class Escopo {
    private LinkedList<TabelaSimbolos> pilha; //empilhando tabelas

    public Escopo(TabelaSimbolos.TipoAlguma retorTipo){
        pilha = new LinkedList<>();
        criar(retorTipo);
    }

    public void criar(TabelaSimbolos.TipoAlguma retorTipo){
        pilha.push(new TabelaSimbolos(retorTipo));
    }

    public TabelaSimbolos getEscopo(){
        return pilha.peek();
    }

    public List<TabelaSimbolos> getPilha(){
        return pilha;
    }

    public void dropEscopo(){
        pilha.pop();
    }
}
