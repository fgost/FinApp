package dsw.finApp.v1.accesGroup.repository;

import dsw.finApp.v1.accesGroup.domain.AccessGroupEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccessGroupRepository extends JpaRepository<AccessGroupEntity, Long> {

    Optional<AccessGroupEntity> findByCode(String code);

    Page<AccessGroupEntity> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
