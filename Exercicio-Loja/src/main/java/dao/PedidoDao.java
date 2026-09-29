package dao;

import java.util.List;

import Entities.Pedido;
import util.JPAUtil;
import jakarta.persistence.EntityManager;

/**
 * Acesso aos dados de Pedido via JPA (Hibernate).
 * Todas as consultas sao JPQL - nenhum SQL escrito a mao.
 *
 * As listagens usam FETCH JOIN para carregar itens, produtos, cliente e
 * pagamento junto com o pedido; assim e possivel trabalhar com a lista
 * depois de fechar o EntityManager (evita LazyInitializationException).
 */
public class PedidoDao {

    /**
     * Persiste o pedido (e, em cascata, seus itens e o pagamento).
     * Se o pedido ainda nao tem numero, o proximo numero disponivel e
     * calculado com uma consulta JPQL dentro da mesma transacao.
     *
     * @return o numero do pedido.
     */
    public int salvar(Pedido pedido) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();

            if (pedido.getNumeroPedido() <= 0) {
                Integer proximo = em.createQuery(
                                "select coalesce(max(p.numeroPedido), 0) + 1 from Pedido p",
                                Integer.class)
                        .getSingleResult();
                pedido.setNumeroPedido(proximo);
            }

            em.persist(pedido);
            em.getTransaction().commit();
            return pedido.getNumeroPedido();
        } catch (RuntimeException e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw e;
        } finally {
            em.close();
        }
    }

    public List<Pedido> listarTodos() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(FETCH_COMPLETO + " order by p.numeroPedido desc", Pedido.class)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public List<Pedido> listarPorCliente(Long clienteId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery(
                            FETCH_COMPLETO + " where p.cliente.id = :clienteId order by p.numeroPedido desc",
                            Pedido.class)
                    .setParameter("clienteId", clienteId)
                    .getResultList();
        } finally {
            em.close();
        }
    }

    public long contar() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("select count(p) from Pedido p", Long.class)
                    .getSingleResult();
        } finally {
            em.close();
        }
    }

    private static final String FETCH_COMPLETO =
            "select distinct p from Pedido p "
                    + "left join fetch p.itens i "
                    + "left join fetch i.produto "
                    + "left join fetch p.cliente "
                    + "left join fetch p.pagamento";
}
