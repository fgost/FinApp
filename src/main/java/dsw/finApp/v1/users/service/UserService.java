package dsw.finApp.v1.users.service;

import dsw.finApp.v1.storage.StorageService;
import dsw.finApp.v1.users.mapper.UserRequestMapper;
import dsw.finApp.v1.users.model.response.permissions.UserResponsePermission;
import dsw.finApp.domain.Constants;
import dsw.finApp.exception.domain.ObjectNotFoundException;
import dsw.finApp.exception.util.ExceptionUtils;
import dsw.finApp.utils.dto.OnlyCodeDto;
import dsw.finApp.utils.pagination.PaginationUtils;
import dsw.finApp.v1.expense.service.ExpenseService;
import dsw.finApp.v1.profiles.service.ProfileService;
import dsw.finApp.v1.users.domain.Preference;
import dsw.finApp.v1.users.domain.RequiredPreferences;
import dsw.finApp.v1.users.domain.UserEntity;
import dsw.finApp.v1.users.domain.UserPhotoEntity;
import dsw.finApp.v1.users.mapper.UserResponseMapper;
import dsw.finApp.v1.users.repository.UserPhotoRepository;
import dsw.finApp.v1.users.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.function.Predicate;

@AllArgsConstructor
@Service
public class UserService {
    private final UserRepository repository;
    private final UserPhotoRepository photoRepository;
    private final ProfileService profileService;
    private final ExpenseService expenseService;
    private final PasswordEncoder passwordEncoder;
    private final StorageService storageService;

    public static final String DEFAULT_SUFFIX = "profile_photo";

    public Page<UserEntity> findAll(Pageable pageable, String name, String email) {
        boolean hasName = name != null && !name.isBlank();
        boolean hasEmail = email != null && !email.isBlank();
        boolean hasBoth = hasName && hasEmail;
        boolean noOne = !hasName && !hasEmail;

        if (hasBoth)
            return repository.findByNameContainingIgnoreCaseOrEmailContainingIgnoreCase(pageable, name, email);
        if (noOne)
            return repository.findAll(pageable);
        if (hasEmail)
            return repository.findByEmailContainingIgnoreCase(pageable, email);
        if (hasName)
            return repository.findByNameContainingIgnoreCase(pageable, name);

        return repository.findAll(pageable);
    }

    public UserEntity findByCode(String code) {
        return repository.findByCode(code)
                .orElseThrow(() -> new ObjectNotFoundException(Constants.USER_NOT_FOUND));
    }

    public Page<UserResponsePermission> getPermissions(String code, Pageable pageable) {
        var entity = findByCode(code);

        Map<String, List<String>> permissionsMap = new HashMap<>();

        entity.getProfiles().forEach(profile -> {
            profile.getAccessGroups().forEach(accessGroup -> {
                accessGroup.getPermissions().forEach(permission -> {
                    if (permissionsMap.containsKey(permission.getPermissionKey())) {
                        permissionsMap.get(permission.getPermissionKey()).add(permission.getPermissionValue());
                    } else {
                        List<String> values = new ArrayList<>();
                        values.add(permission.getPermissionValue());
                        permissionsMap.put(permission.getPermissionKey(), values);
                    }
                });
            });
        });
        List<UserResponsePermission> permissionsList = new ArrayList<>();
        permissionsMap.forEach((key, value) -> {
            permissionsList.add(new UserResponsePermission(key, value));
        });

        int firstResult = Integer.valueOf(String.valueOf(pageable.getOffset()));
        int totalElements = permissionsList.size();
        int pageSize = pageable.getPageSize() > totalElements ? totalElements : pageable.getPageSize();
        if (firstResult > pageSize)
            return PaginationUtils.emptyPage(pageable, totalElements);

        List<UserResponsePermission> paginatedPermissions = permissionsList.subList(firstResult, pageSize);

        Page<UserResponsePermission> page = PaginationUtils.pageOf(paginatedPermissions, pageable, totalElements);
        return page;
    }

    public UserPhotoEntity getProfilePhotoInfo(String code) {
        var userEntity = findByCode(code);
        var photoEntity = photoRepository.findByUserId(userEntity.getId())
                .orElseThrow(() -> new ObjectNotFoundException(Constants.USER_PHOTO_NOT_FOUND));
        return photoEntity;
    }

    public StorageService.File getProfilePhoto(String code){
        var photoEntity = getProfilePhotoInfo(code);
        var dto = storageService.get(photoEntity.getFileName());
        var mappedDto = UserResponseMapper.INSTANCE.mapPhotoEntityToModel(photoEntity);
        mappedDto.setInputStream(dto.getInputStream());
        return mappedDto;
    }

    @Transactional
    public UserEntity insert(UserEntity entity) {
        try {
            entity.setPassword(passwordEncoder.encode(entity.getPassword()));
            entity.setPreferences(RequiredPreferences.getRequiredPreference());
            return repository.save(entity);
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.USER_NOT_PERSISTED);
        }
    }

    @Transactional
    public UserEntity update(String code, UserEntity entity) {
        var existentEntity = findByCode(code);
        entity.setCode(code);
        entity.setPassword(existentEntity.getPassword());
        entity.setId(existentEntity.getId());
        entity.setPreferences(existentEntity.getPreferences());
        entity.setProfiles(existentEntity.getProfiles());

        try {
            return repository.save(entity);
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.USER_NOT_PERSISTED);
        }
    }

    //TODO verificar forma de cadastrar novas preferencias, se terá um novo ID
    @Transactional
    public UserEntity updatePreferences(String code, Set<Preference> inputList) {
        var entity = findByCode(code);

        inputList.forEach(input -> {
            Predicate<? super Preference> samePreferenceKey =
                    preference -> input.getPreferenceKey().equals(preference.getPreferenceKey());

            var matchedPreference = entity.getPreferences().stream()
                    .filter(samePreferenceKey)
                    .findFirst();
            if (matchedPreference.isPresent()) {
                matchedPreference.get().setPreferenceValue(input.getPreferenceValue());
            } else {
                entity.getPreferences().add(new Preference(input.getPreferenceKey(), input.getPreferenceValue()));
            }
        });

        return repository.save(entity);
    }

    @Transactional
    public UserEntity updateProfiles(String code, Set<OnlyCodeDto> inputList) {
        var entity = findByCode(code);
        entity.getProfiles().clear();
        inputList.forEach(input -> {
            var profile = profileService.findByCode(input.getCode());
            entity.getProfiles().add(profile);
        });

        return repository.save(entity);
    }

    @Transactional
    public UserEntity updateExpense(String code, Set<OnlyCodeDto> inputList) {
        var entity = findByCode(code);

        // Adiciona novas despesas ao invés de limpar e adicionar
        inputList.forEach(input -> {
            var expense = expenseService.findByCode(input.getCode());
            entity.getExpense().add(expense);
        });

        return repository.save(entity);
    }

    @Transactional
    public UserPhotoEntity updatePhoto(StorageService.File file, String code) {
        var user = findByCode(code);
        var savedFile = storageService.save(file);

        try {
            var entity = UserRequestMapper.INSTANCE.mapPhotoModelToEntity(savedFile);
            entity.setUser(user);
            entity.setId(user.getId());
            entity.setContentType(file.getContentType());
            var savedEntity = photoRepository.save(entity);
            photoRepository.flush();
            return savedEntity;
        } catch (Exception e) {
            storageService.delete(savedFile.getFileName());
            throw ExceptionUtils.buildNotPersistedException(Constants.USER_PHOTO_NOT_PERSISTED);
        }
    }

    @Transactional
    public void deleteByCode(String code) {
        try {
            var entity = findByCode(code);
            repository.delete(entity);
        } catch (Exception e) {
            throw ExceptionUtils.buildNotPersistedException(Constants.USER_DELETION_ERROR);
        }
    }
}
