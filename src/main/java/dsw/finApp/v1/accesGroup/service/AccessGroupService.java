package dsw.finApp.v1.accesGroup.service;

import dsw.finApp.domain.Constants;
import dsw.finApp.exception.domain.ObjectNotFoundException;
import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.v1.accesGroup.domain.AccessGroupEntity;
import dsw.finApp.v1.accesGroup.repository.AccessGroupRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class AccessGroupService {
    private final AccessGroupRepository repository;

    public Page<AccessGroupEntity> findAll(Pageable pageable, String name) {
        boolean hasName = name != null && !name.isBlank();
        if (hasName)
            return repository.findByNameContainingIgnoreCase(pageable, name);

        return repository.findAll(pageable);
    }

    public AccessGroupEntity findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ObjectNotFoundException(Constants.ACCESS_GROUP_NOT_FOUND));
    }

    @Transactional
    public List<AccessGroupEntity> insert(List<AccessGroupEntity> entities) {
        try {
            entities.forEach(entity -> {
                entity = repository.save(entity);
            });

            return entities;
        } catch (Exception e) {
            throw ExceptionUtils.buildSameIdentifierException(Constants.ACCESS_GROUP_DUPLICATED);
        }
    }

    public AccessGroupEntity update(String code, AccessGroupEntity entity) {
        var existentEntity = findByCode(code);
        entity.setCode(code);
        entity.setId(existentEntity.getId());

        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.ACCESS_GROUP_NOT_PERSISTED);
        }
    }

    @Transactional
    public AccessGroupEntity updatePermissions(String code, AccessGroupEntity entity) {
        var existentEntity = findByCode(code);
        existentEntity.getPermissions().clear();
        existentEntity.getPermissions().addAll(entity.getPermissions());
        var savedEntity = repository.save(existentEntity);
        return savedEntity;
    }

    public void deleteByCode(String code) {
        var entity = findByCode(code);
        repository.delete(entity);
    }
}
