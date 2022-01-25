# Como executar o projeto

## 1. Primeiro passo
* Esteja com o terminal dentro da pasta $out/$

## 2. Segundo passo
* Execute o comando:
    ```sh
    ls -all
    ```
* Caso os arquivos **Server.jar** e **App.jar** não estejam com permissão para executar, execute os seguintes comandos:

    ```sh
    chmod +x Server.jar
    ```
    ```sh
    chmod +x App.jar
    ```
## 3. Terceiro passo
* Abra dois terminais dentro da pasta $out/$, um para o Server e outro para o User

## 4. Quarto passo
* Agora com as permissões para executar, execute os comandos:

    Para executar o servidor:
    ```java
    java -jar Server.jar
    ```
    Para executar a aplicação:
    ```java
    java -jar App.jar
    ```

## 5. Quinto passo
* Agora com os programas rodando, faça a interação com a aplicação através dos menus

* É possível analisar o lado servidor também, sabendo qual método ele está executando
    