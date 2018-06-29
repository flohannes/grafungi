package grafungi.query.ws.data;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author fschmidt
 */

@Transactional
public interface GraphMetadataDBRepository extends JpaRepository<GraphMetadata, Long> {
}
