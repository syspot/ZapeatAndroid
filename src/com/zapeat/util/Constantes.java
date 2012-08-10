package com.zapeat.util;


public class Constantes {

	private Constantes() {

	}
	
	public interface Preferencias {
		
		String PREFERENCE_DEFAULT = "preferenceDefault";
		String USUARIO_LOGADO = "usuarioLogado";
		
	}

	public interface Http {

		public interface Mensagens {
			String VALIDACAO_USUARIO = "Usuário não pode estar nulo!";
			String FALHA_AUTENTICACAO = "A autenticação falhou por motivos técnicos. Tente novamente mais tarde";
		}

		String URL_AUTH = "http://192.168.1.102:8080/TestAndroid/autenticar";
		String URL_ZAPEAT = "http://www.google.com.br";
		String PARAMETRO_RETORNO = "PARAM_RETORNO";
	}
}
