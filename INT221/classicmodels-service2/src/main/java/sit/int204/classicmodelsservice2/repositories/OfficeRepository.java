package sit.int204.classicmodelsservice2.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import sit.int204.classicmodelsservice2.entities.Customer;
import sit.int204.classicmodelsservice2.entities.Office;

public interface OfficeRepository extends JpaRepository<Office,String> {
}
