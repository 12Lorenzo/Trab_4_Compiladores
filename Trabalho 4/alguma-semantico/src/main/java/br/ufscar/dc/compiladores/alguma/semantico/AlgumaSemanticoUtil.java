package br.ufscar.dc.compiladores.alguma.semantico;

import java.util.ArrayList;
import java.util.List;
import org.antlr.v4.runtime.Token;

import br.ufscar.dc.compiladores.alguma.semantico.TabelaSimbolos;

import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.Exp_aritmeticaContext;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.ExpressaoContext;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.FatorContext;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.Fator_logicoContext;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.ParcelaContext;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.TermoContext;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser.Termo_logicoContext;



public class AlgumaSemanticoUtil {
    public static List<String> errosSemanticos = new ArrayList<>();
    
    public static void adicionarErroSemantico(Token t, String mensagem) {
        int linha = t.getLine();
        int coluna = t.getCharPositionInLine();
        errosSemanticos.add(String.format("Linha %d: %s", linha, mensagem));
    }
    
    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.ExpressaoContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;
        for (Termo_logicoContext ta : ctx.termo_logico()) {
            TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, ta);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabelaSimbolos.TipoAlguma.INVALIDO) {
                ret = TabelaSimbolos.TipoAlguma.INVALIDO;
            }
        }
        return ret;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Termo_logicoContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;
        for (Fator_logicoContext ta : ctx.fator_logico()) {
            TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, ta);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabelaSimbolos.TipoAlguma.INVALIDO) {
                ret = TabelaSimbolos.TipoAlguma.INVALIDO;
            }
        }

        return ret;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Fator_logicoContext ctx) {
        //SemanticoUtils.adicionarErroSemantico(ctx.start, ctx.getText() + verificarTipo(escopos, ctx.parcela_logica()));
        return verificarTipo(escopos, ctx.parcela_logica());
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Parcela_logicaContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;
        if(ctx.exp_relacional() != null){
            ret = verificarTipo(escopos, ctx.exp_relacional());
        } else{
            ret = TabelaSimbolos.TipoAlguma.LOGICO;
        }

        
        return ret;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Exp_relacionalContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;
        if(ctx.op_relacional() != null){
            for (Exp_aritmeticaContext ta : ctx.exp_aritmetica()) {
                TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, ta);
                Boolean auxNumeric = aux == TabelaSimbolos.TipoAlguma.REAL || aux == TabelaSimbolos.TipoAlguma.INTEIRO;
                Boolean retNumeric = ret == TabelaSimbolos.TipoAlguma.REAL || ret == TabelaSimbolos.TipoAlguma.INTEIRO;
                if (ret == null) {
                    ret = aux;
                } else if (!(auxNumeric && retNumeric) && aux != ret) {
                    ret = TabelaSimbolos.TipoAlguma.INVALIDO;
                }
            }
            if(ret != TabelaSimbolos.TipoAlguma.INVALIDO){
                ret = TabelaSimbolos.TipoAlguma.LOGICO;
            }
        } else {
            ret = verificarTipo(escopos, ctx.exp_aritmetica(0));
        }

        
        return ret;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Exp_aritmeticaContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;
        for (TermoContext ta : ctx.termo()) {
            TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, ta);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabelaSimbolos.TipoAlguma.INVALIDO) {
                ret = TabelaSimbolos.TipoAlguma.INVALIDO;
            }
        }

        
        return ret;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.TermoContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;

        for (FatorContext fa : ctx.fator()) {
            TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, fa);
            Boolean auxNumeric = aux == TabelaSimbolos.TipoAlguma.REAL || aux == TabelaSimbolos.TipoAlguma.INTEIRO;
            Boolean retNumeric = ret == TabelaSimbolos.TipoAlguma.REAL || ret == TabelaSimbolos.TipoAlguma.INTEIRO;
            if (ret == null) {
                ret = aux;
            } else if (!(auxNumeric && retNumeric) && aux != ret) {
                ret = TabelaSimbolos.TipoAlguma.INVALIDO;
            }
        }
        
        return ret;
    }
    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.FatorContext ctx) {
        TabelaSimbolos.TipoAlguma ret = null;

        for (ParcelaContext fa : ctx.parcela()) {
            TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, fa);
            if (ret == null) {
                ret = aux;
            } else if (ret != aux && aux != TabelaSimbolos.TipoAlguma.INVALIDO) {
                ret = TabelaSimbolos.TipoAlguma.INVALIDO;
            }
        }
        
        return ret;
    }
    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.ParcelaContext ctx) {
        TabelaSimbolos.TipoAlguma ret = TabelaSimbolos.TipoAlguma.INVALIDO;

        if(ctx.parcela_nao_unario() != null){
            ret = verificarTipo(escopos, ctx.parcela_nao_unario());
        }
        else {
            
            ret = verificarTipo(escopos, ctx.parcela_unario());
        }
        return ret;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Parcela_nao_unarioContext ctx) {
        if (ctx.identificador() != null) {
            return verificarTipo(escopos, ctx.identificador());
        }
        return TabelaSimbolos.TipoAlguma.LITERAL;
    }

    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.IdentificadorContext ctx) {//kk suspeitos
        String nomeVar = "";
        TabelaSimbolos.TipoAlguma ret = TabelaSimbolos.TipoAlguma.INVALIDO;
        for(int i = 0; i < ctx.IDENT().size(); i++){
            nomeVar += ctx.IDENT(i).getText();
            if(i != ctx.IDENT().size() - 1){
                nomeVar += ".";
            }
        }
        for(TabelaSimbolos tabela : escopos.getPilha()){
            if (tabela.existe(nomeVar)) {
                ret = verificarTipo(escopos, nomeVar);
            }
        }
        System.out.println(nomeVar);
        return ret;
    }
    
    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, AlgumaParser.Parcela_unarioContext ctx) {
        if (ctx.NUM_INT() != null) {
            return TabelaSimbolos.TipoAlguma.INTEIRO;
        }
        if (ctx.NUM_REAL() != null) {
            return TabelaSimbolos.TipoAlguma.REAL;
        }
        if(ctx.identificador() != null){
            return verificarTipo(escopos, ctx.identificador());
        }
        if (ctx.IDENT() != null) {
            TabelaSimbolos.TipoAlguma ret = null;
            ret = verificarTipo(escopos, ctx.IDENT().getText());
            for (ExpressaoContext fa : ctx.expressao()) {
                TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, fa);
                if (ret == null) {
                    ret = aux;
                } else if (ret != aux && aux != TabelaSimbolos.TipoAlguma.INVALIDO) {
                    ret = TabelaSimbolos.TipoAlguma.INVALIDO;
                }
            }
            return ret;
        } else {
            TabelaSimbolos.TipoAlguma ret = null;
            for (ExpressaoContext fa : ctx.expressao()) {
                TabelaSimbolos.TipoAlguma aux = verificarTipo(escopos, fa);
                if (ret == null) {
                    ret = aux;
                } else if (ret != aux && aux != TabelaSimbolos.TipoAlguma.INVALIDO) {
                    ret = TabelaSimbolos.TipoAlguma.INVALIDO;
                }
            }
            return ret;
        }
    }
    
    public static TabelaSimbolos.TipoAlguma verificarTipo(Escopo escopos, String nomeVar) {
        TabelaSimbolos.TipoAlguma type = null;
        for(TabelaSimbolos tabela : escopos.getPilha()){
            type = tabela.verificar(nomeVar);
        }
        return type;
    }

    public static TabelaSimbolos.TipoAlguma getTipo(String val){
        TabelaSimbolos.TipoAlguma tipo = null;
                switch(val) {
                    case "literal": 
                        tipo = TabelaSimbolos.TipoAlguma.LITERAL;
                        break;
                    case "inteiro": 
                        tipo = TabelaSimbolos.TipoAlguma.INTEIRO;
                        break;
                    case "real": 
                        tipo = TabelaSimbolos.TipoAlguma.REAL;
                        break;
                    case "logico": 
                        tipo = TabelaSimbolos.TipoAlguma.LOGICO;
                        break;
                    default:
                        break;
                }
        return tipo;
    }
}
