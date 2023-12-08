package dsw.finApp.v1.profiles.repository;

import dsw.finApp.v1.profiles.domain.ProfileEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<ProfileEntity, Long> {

    Optional<ProfileEntity> findByCode(String code);

    Page<ProfileEntity> findByNameContainingIgnoreCase(Pageable pageable, String name);
}
