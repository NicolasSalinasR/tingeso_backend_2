package HU4.HU4.Repository;

import HU4.HU4.Entity.HistoryCountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository

public interface HistoryCountRepository extends JpaRepository<HistoryCountEntity, Long> {
    List<HistoryCountEntity> findAllByClientid(Long clientid);
}
