package vn.ohana.post;

import org.springframework.stereotype.Component;
import vn.ohana.Post;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component
public class CPostRepository {
    @PersistenceContext
    private EntityManager entityManager;


    public List<Post> findAllByUtilitiesFilter(List<Long> utilities) {
        StringBuilder query = new StringBuilder("SELECT * FROM  POST WHERE ");
        query.append("(");
        for (int i = 0; i < utilities.size(); i++) {
            Long utility = utilities.get(i);
            String format = "JSON_CONTAINS(utilities,'%s')";
            query.append(String.format(format, utility))
                    .append(i < utilities.size() - 1 ? " AND " : "");
        }
        query.append(")");
        return (List<Post>) entityManager.createNativeQuery(query.toString(), Post.class).getResultList();

    }


}