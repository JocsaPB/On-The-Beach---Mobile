package jaj.tct.com.onthebeach.models;

/**
 * Created by jocsa on 20/10/16.
 */
public class Usuario {
    /**Model utilizado para gerar JSON para executar autenticação na acitivity Login*/

    private String login;
    private String senha;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
