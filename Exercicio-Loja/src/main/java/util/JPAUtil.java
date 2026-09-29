package util;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

/**
 * Ponto unico de acesso ao JPA/Hibernate.
 *
 * Cria uma unica EntityManagerFactory para a persistence-unit "loja"
 * (definida em src/main/resources/META-INF/persistence.xml) e fornece
 * EntityManager's para os DAOs.
 */
public final class JPAUtil {

    private static EntityManagerFactory fabrica;

    private JPAUtil() {
    }

    private static EntityManagerFactory fabrica() {
        if (fabrica == null || !fabrica.isOpen()) {
            fabrica = Persistence.createEntityManagerFactory("loja");
        }
        return fabrica;
    }

    /** Cria um EntityManager novo. Quem chama deve fechar (em.close()). */
    public static EntityManager getEntityManager() {
        return fabrica().createEntityManager();
    }

    /** Fecha a fabrica de entidades (encerrar da aplicacao). */
    public static void close() {
        if (fabrica != null && fabrica.isOpen()) {
            fabrica.close();
        }
    }
}
