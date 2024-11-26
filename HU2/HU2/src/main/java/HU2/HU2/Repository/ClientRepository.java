package HU2.HU2.Repository;
import HU2.HU2.Entity.ClientEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface ClientRepository  extends JpaRepository<ClientEntity, Long>{
    ClientEntity findByEmail(String email);
    ClientEntity findByRut(String rut);
    ClientEntity findById(long id);


}
