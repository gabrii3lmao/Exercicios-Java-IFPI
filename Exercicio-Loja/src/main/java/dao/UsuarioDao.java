package dao;

import java.util.List;

import Entities.Usuario;
import util.JPAUtil;
import jakarta.persistence.EntityManager;

/**
 * Acesso aos dados de Usuario (e subclases) via JPA (Hibernate).
 * Todas as consultas sao JPQL - nenhum SQL escrito a mao.
 */
public class UsuarioDao {

    public void salvar(Usuario usuario) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (usuario.getId() == null) {
                em.persist(usuario);
            } else {
                em.merge(usuario);
            }
            em.getTransaction().commit();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    /** Autentica pelo email e senha. Retorna null se nao encontrar. */
    public Usuario autenticar(String email, String senha) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Usuario> resultados = em.createQuery(
                            "select u from Usuario u where u.email = :email and u.senha = :senha",
                            Usuario.class)
                    .setParameter("email", email)
                    .setParameter("senha", senha)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    public Usuario buscarPorEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            List<Usuario> resultados = em.createQuery(
                            "select u from Usuario u where u.email = :email",
                            Usuario.class)
                    .setParameter("email", email)
                    .getResultList();
            return resultados.isEmpty() ? null : resultados.get(0);
        } finally {
            em.close();
        }
    }

    public boolean existeAdministrador() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            Long total = em.createQuery("select count(a) from Administrador a", Long.class)
                    .getSingleResult();
            return total > 0;
        } finally {
            em.close();
        }
    }
}
