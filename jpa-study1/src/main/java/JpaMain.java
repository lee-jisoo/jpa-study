import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {
    public static void main(String[] args) {
        final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("hello");
        final EntityManager entityManager = entityManagerFactory.createEntityManager();
        final EntityTransaction transaction = entityManager.getTransaction();

        // tx시작
        transaction.begin();

        try {
            // persistMember(entityManager);
            final List<Member> members = findMembers(entityManager);
            transaction.commit();
        } catch (Exception e) {
            transaction.rollback();
        } finally {
            entityManager.close();
        }

        // close
        entityManagerFactory.close();
    }

    private static List<Member> findMembers(EntityManager entityManager) {
        // JPQL를 통해서 JPQL문법 + 페이징 가능
        // 맴버 객체를 대상으로 SQL 수행 "MEMBER DB가 아니라 Member Table임 m은 엔티티 한개의 의미"
        return entityManager.createQuery("select m from Member as m", Member.class)
                            .setFirstResult(5)
                            .setMaxResults(10)
                            .getResultList();
    }

    private static void persistMember(EntityManager entityManager) throws Exception {
        Member member = new Member();
        member.setId(2L);
        member.setName("Hello B");
        entityManager.persist(member);
    }
}
