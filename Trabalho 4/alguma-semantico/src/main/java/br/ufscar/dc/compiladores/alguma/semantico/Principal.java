package br.ufscar.dc.compiladores.alguma.semantico;

import java.io.IOException;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import java.io.PrintWriter;
import java.io.File;
//import org.antlr.v4.runtime.Token;

import br.ufscar.dc.compiladores.alguma.semantico.AlgumaLexer;
import br.ufscar.dc.compiladores.alguma.semantico.AlgumaParser;

public class Principal {
    public static void main(String args[]) throws IOException {
        try(PrintWriter p = new PrintWriter(new File(args[1]))) {//saida
            CharStream c = CharStreams.fromFileName(args[0]);//entrada
            AlgumaLexer lex = new AlgumaLexer(c);
            CommonTokenStream cs = new CommonTokenStream(lex); //conversão para token stream
            AlgumaParser parser = new AlgumaParser(cs);
            AlgumaParser.ProgramaContext arvore = parser.programa();   
            AlgumaSemantico as = new AlgumaSemantico();  
            as.visitPrograma(arvore);
            for(String err: AlgumaSemanticoUtil.errosSemanticos){
                p.println(err);
            }
            p.println("Fim da compilacao");
            p.close();
        } catch (Exception e) {
            System.err.println(e);
        }
    }
}
