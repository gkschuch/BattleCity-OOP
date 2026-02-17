# Battle City

### Trabalho final para a disciplina de Programação Orientada a Objetos.

#### Desenvolvedores:

- [Gabriel Karini Schuch](https://github.com/gkschuch)
- [Tiago Wolowski](https://github.com/Tiago-w)
- [Henrique dos Reis](https://github.com/HenriqueReis31)

---

## Sobre o Projeto

Este trabalho consiste na reprodução e adaptação do clássico **Battle City**, jogo de tiro e estratégia lançado originalmente em 1985 pela Namco para o Nintendo Family Computer (NES).

## Instalação e Execução

Para rodar este projeto localmente na sua máquina, siga os passos abaixo:

1. **Clone este repositório:**
   ```bash
   git clone https://github.com/gkschuch/BattleCity-OOP.git
   ```
2. **Dependências do Projeto**
   Este projeto utiliza a biblioteca **Gson** da Google para manipulação de dados.

- Acesse o link oficial: [Gson 2.13.2 - Maven Repository](https://mvnrepository.com/artifact/com.google.code.gson/gson/2.13.2)
- Na linha **Files**, clique em "**jar**" para fazer o download do arquivo `gson-2.13.2.jar`.

**2. Adicione o `.jar` ao Classpath do seu projeto:**
O processo varia de acordo com a IDE que você está utilizando:

- **VS Code:** Crie uma pasta chamada `lib` na raiz do projeto e coloque o arquivo `.jar` lá dentro. Na aba "Java Projects", vá em "Referenced Libraries", clique no ícone de `+` e selecione o arquivo.
- **IntelliJ IDEA:** Vá em `File > Project Structure > Modules > Dependencies`. Clique no `+`, selecione `JARs or directories...` e adicione o arquivo baixado.
