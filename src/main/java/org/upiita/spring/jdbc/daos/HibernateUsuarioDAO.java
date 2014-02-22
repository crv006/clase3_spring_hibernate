package org.upiita.spring.jdbc.daos;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.upiita.spring.jdbc.entidades.Usuario;

@Component("usuarioDAO")
public class HibernateUsuarioDAO implements UsuarioDAO {
	
	@Autowired
	private SessionFactory sessionFactory;

	public Usuario buscaUsuarioPorId(Integer usuarioId) {
		Session sesion = sessionFactory.openSession();		
		sesion.beginTransaction();
		//Inicia Transaccion
		Usuario usuario = (Usuario) sesion.get(Usuario.class, usuarioId);
		
		//Obteniendo la transaccion actual y guardando todos los cambios en la base
		sesion.getTransaction().commit();
		sesion.close();

		return usuario;
	}

	public void creaUsuario(Usuario usuario) {
		Session sesion = sessionFactory.openSession();		
		sesion.beginTransaction();
		//Inicia Transaccion
		sesion.saveOrUpdate(usuario);
		
		//Obteniendo la transaccion actual y guardando todos los cambios en la base
		sesion.getTransaction().commit();
		sesion.close();
	}

	public Usuario buscaPorEmailYPassword(String email, String password) {
		Session sesion = sessionFactory.openSession();		
		sesion.beginTransaction();
				
		//Formamos criterio de Hibernate
		Criteria criterio = sesion.createCriteria(Usuario.class);
		
		//EJEMPLO de criterio usando OR
		/*
		criterio.add(Restrictions.or(Restrictions.eq("email", email),
				Restrictions.eq("password", password)));
		*/
		
		//EJEMPLO de criterio usando AND
		criterio.add(Restrictions.eq("email", email));
		criterio.add(Restrictions.eq("password", password));
		
		//Obteniendo un solo resultado
		//Si no encuentra nada regresa NULL
		Usuario usuario = (Usuario) criterio.uniqueResult();

		sesion.getTransaction().commit();
		sesion.close();
		
		return usuario;
	}

	public List<Usuario> buscaPorNombre(String nombre) {
		Session sesion = sessionFactory.openSession();		
		sesion.beginTransaction();
				
		//Formamos criterio de Hibernate
		Criteria criterio = sesion.createCriteria(Usuario.class);
		criterio.add(Restrictions.like("nombre", "%" + nombre + "%"));
		List<Usuario> usuarios = criterio.list();
				
		sesion.getTransaction().commit();
		sesion.close();
		
		return usuarios;
	}

}
