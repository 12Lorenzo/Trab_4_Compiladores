# Trabalho 4 de compiladores



<div>

 ## Integrante
  
Nome: Lorenzo Correia Maia RA: 759502


</div>

## Requisitos prévios
Estes são os requisitos para execução e compilação do projeto: **Java** e **Maven**.

## Comandos necessários

Para compilar e executar este programa, primeiro você deve entrar na pasta chamada `alguma-semantico`. Em seguida execute os comandos abaixo no terminal:

```
  mvn package
```
```
  java -jar target/alguma-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar /caminho/entrada.txt /caminho/saida.txt

```

*Importante* <br>
Talvez de um problema na compilação do alguma-semantico, caso isso aconteça digitar:
```
java -cp /caminho/alguma-semantico-1.0-SNAPSHOT-jar-with-dependencies.jar br.ufscar.dc.compiladores.alguma.semantico.Principal /caminho/entrada.txt /caminho/saida.txt
```
