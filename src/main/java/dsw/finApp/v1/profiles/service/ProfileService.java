package dsw.finApp.v1.profiles.service;

import dsw.finApp.exception.domain.ObjectNotFoundException;
import dsw.finApp.domain.Constants;
import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.utils.dto.OnlyCodeDto;
import dsw.finApp.v1.accesGroup.domain.AccessGroupEntity;
import dsw.finApp.v1.accesGroup.service.AccessGroupService;
import dsw.finApp.v1.profiles.domain.ProfileEntity;
import dsw.finApp.v1.profiles.repository.ProfileRepository;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Service
public class ProfileService {
    private final ProfileRepository repository;
    private final AccessGroupService accessGroupService;

    public Page<ProfileEntity> findAll(Pageable pageable, String name) {
        boolean hasName = name != null && !name.isBlank();
        if (hasName)
            return repository.findByNameContainingIgnoreCase(pageable, name);

        return repository.findAll(pageable);
    }

    public ProfileEntity findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ObjectNotFoundException(Constants.PROFILE_NOT_FOUND));
    }

    @Transactional
    public ProfileEntity insert(ProfileEntity profilesEntity) {
        try {
            return repository.save(profilesEntity);
        } catch (DataIntegrityViolationException ex) {
            throw ExceptionUtils.buildSameIdentifierException(Constants.PROFILE_DUPLICATED);
        }
    }

    @Transactional
    public ProfileEntity update(String code, ProfileEntity entity) {
        var existentEntity = findByCode(code);
        entity.setCode(code);
        entity.setId(existentEntity.getId());
        entity.setAccessGroups(existentEntity.getAccessGroups());

        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.PROFILE_NOT_PERSISTED);
        }
    }

    @Transactional
    public ProfileEntity updateProfileAccessGroups(String code, List<OnlyCodeDto> inputList) {
        var entity = findByCode(code);
        var accessGroups = new ArrayList<AccessGroupEntity>();
        inputList.forEach(input -> {
            var accessGroup = accessGroupService.findByCode(input.getCode());
            accessGroups.add(accessGroup);
        });

        entity.getAccessGroups().clear();
        entity.getAccessGroups().addAll(accessGroups);

        var savedEntity = repository.save(entity);
        return savedEntity;
    }

    @Transactional
    public void deleteByCode(String code) {
        try {
            var entity = findByCode(code);
            repository.delete(entity);
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.PROFILE_DELETION_ERROR);
        }
    }
}
