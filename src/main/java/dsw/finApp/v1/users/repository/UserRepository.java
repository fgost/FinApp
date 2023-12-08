package dsw.finApp.v1.users.repository;

import dsw.finApp.v1.users.domain.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByCode(String code);

    Page<UserEntity> findAll(Pageable pageable);

    Page<UserEntity> findByEmailContainingIgnoreCase(Pageable pageable, String email);

    Page<UserEntity> findByNameContainingIgnoreCase(Pageable pageable, String name);

    Page<UserEntity> findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(Pageable pageable, String name, String email);

}
