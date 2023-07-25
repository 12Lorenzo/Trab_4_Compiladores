package br.ufscar.dc.compiladores.alguma.semantico;

import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.atn.ATNConfigSet;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.ANTLRErrorListener;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import java.io.PrintWriter;
import java.util.BitSet;

public class MyCustomErrorListener implements ANTLRErrorListener {
   PrintWriter pw;
   boolean sintError = false;
   public MyCustomErrorListener(PrintWriter pw) {
       this.pw = pw;    
   }

    @Override
    public void	reportAmbiguity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, boolean exact, BitSet ambigAlts, ATNConfigSet configs) {
       
    }
    
    @Override
    public void reportAttemptingFullContext(Parser recognizer, DFA dfa, int startIndex, int stopIndex, BitSet conflictingAlts, ATNConfigSet configs) {
        
    }

    @Override
    public void reportContextSensitivity(Parser recognizer, DFA dfa, int startIndex, int stopIndex, int prediction, ATNConfigSet configs) {
        
    }

    @Override
    public void	syntaxError(Recognizer<?, ?> arg0, Object arg1, int arg2, int arg3, String arg4, RecognitionException arg5) {
        // Colocar o tratamento de erro customizado

        Token t = (Token) arg1;
        String text = t.getText();
        // o texto de EOF vem com <>, portanto aqui realiza uma conversão em tal caso
        if(text == "<EOF>")
            text = "EOF";
        String aType = AlgumaLexer.VOCABULARY.getDisplayName(t.getType()); // Converte o tipo desse token para string
        if(!sintError){

            if(aType == "Nao_Fechado"){ //Caso seja comentario nao fechado
                pw.println("Linha " + t.getLine() + ": " + "comentario nao fechado");
                //MyCustomErrorListener.sintError = true;
                sintError = true;
            }
            else if(aType == "Literal_Nao_Fechada"){ //Caso seja literal "" nao fechado
                pw.println("Linha " + t.getLine() + ": " + "cadeia literal nao fechada");
                //MyCustomErrorListener.sintError = true;
                sintError = true;
            }
            else if(aType == "ERR"){ //Em caso de Simbolo nao identificado
                pw.println("Linha " + t.getLine() + ": " + text + " - simbolo nao identificado");
                //yCustomErrorListener.sintError = true;
                sintError = true;
            }
            else{
                pw.println("Linha " + arg2 + ": erro sintatico proximo a " + text);
                //MyCustomErrorListener.sintError = true;
                sintError = true;
            }
        }
    }
}