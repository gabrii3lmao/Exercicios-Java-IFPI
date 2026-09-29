package dao;

import java.util.List;

import Entities.Interfaces.Produto;
import Entities.ItemPedido;
import util.JPAUtil;
import jakarta.persistence.EntityManager;

/**
 * Acesso aos dados de Produto via JPA (Hibernate).
 */
public class ProdutoDao {

    public void salvar(Produto produto) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            if (produto.getId() == null) {
                em.persist(produto);
            } else {
                em.merge(produto);
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

    /**
     * Remove o produto, desde que nenhum ItemPedido o utilize.
     *
     * @return true se realmente removeu; false se nao existe ou esta em uso.
     */
    public boolean remover(Produto produto) {
        if (produto.getId() == null) {
            return false;
        }
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            Long emUso = em.createQuery(
                            "select count(i) from ItemPedido i where i.produto.id = :id",
                            Long.class)
                    .setParameter("id", produto.getId())
                    .getSingleResult();

            Produto referencia = em.find(Produto.class, produto.getId());
            boolean removido = false;
            if (emUso == 0 && referencia != null) {
                em.remove(referencia);
                removido = true;
            }

            em.getTransaction().commit();
            return removido;
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Produto> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select p from Produto p order by p.nome", Produto.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public Produto buscarPorId(Long id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.find(Produto.class, id);
        } finally {
            em.close();
        }
    }

    public long contar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select count(p) from Produto p", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }
}
