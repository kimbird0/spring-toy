package hellojpa;

import jakarta.persistence.*;

import java.util.List;

public class JpaMain {

    public static void main(String[] args) {

        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();

        EntityTransaction tx = em.getTransaction();
        tx.begin();
        //code
        try {
            List<Member> result = em.createQuery("select m from Member as m", Member.class)
                            .getResultList();


            tx.commit();
        }catch (Exception e){
            tx.rollback();
        } finally {
            em.close();
        }
        emf.close();
    }
}
