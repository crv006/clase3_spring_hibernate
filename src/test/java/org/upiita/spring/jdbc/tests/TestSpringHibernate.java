package org.upiita.spring.jdbc.tests;

import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.upiita.spring.jdbc.daos.UsuarioDAO;
import org.upiita.spring.jdbc.entidades.Usuario;

public class TestSpringHibernate {

	public static void main(String[] args) {
		//creamos el contexto de Spring
		ApplicationContext contexto = new ClassPathXmlApplicationContext("/contexto.xml");
		
		//nos traemos el bean HibernateUsuarioDAO
		UsuarioDAO usuarioDAO = (UsuarioDAO) contexto.getBean("usuarioDAO");
		
		Usuario usuario = new Usuario();
		usuario.setUsuarioId(3);
		usuario.setNombre("Ciro");
		usuario.setEmail("ciro_rv@hotmail.com");
		usuario.setPassword("12345");
			
		usuarioDAO.creaUsuario(usuario);
		
		usuario.setPassword("123");
		
		usuarioDAO.creaUsuario(usuario);
		
		System.out.println("Datos guardados...");
		
		
		Usuario usuarioBD = usuarioDAO.buscaUsuarioPorId(3);		
		System.out.println("Usuario encontrado es: "+usuarioBD);
		
		//Probando el criterio de Hibernate
		Usuario usuarioCriterio = usuarioDAO.buscaPorEmailYPassword("ciro_rv@hotmail.com", "1235");
		System.out.println("usuario por email y password es: "+usuarioCriterio);
		
		//Prueba del criterio like
		List<Usuario> usuarios = usuarioDAO.buscaPorNombre("z");
		System.out.println("usuarios por nombre: "+usuarios);
		
	}

}
