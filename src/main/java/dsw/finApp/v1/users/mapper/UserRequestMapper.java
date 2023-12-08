package dsw.finApp.v1.users.mapper;

import dsw.finApp.v1.storage.StorageService;
import dsw.finApp.v1.users.dto.UserDtoUpdate;
import dsw.finApp.v1.users.model.request.UserRequest;
import dsw.finApp.v1.users.domain.UserEntity;
import dsw.finApp.v1.users.domain.UserPhotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserRequestMapper {
    UserRequestMapper INSTANCE = Mappers.getMapper(UserRequestMapper.class);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    UserEntity mapModelToEntity(UserRequest request);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "code", ignore = true)
    @Mapping(target = "password", ignore = true)
    UserEntity updateMapModelToEntity(UserDtoUpdate dtoUpdate);

    UserPhotoEntity mapPhotoModelToEntity(StorageService.File file);
}
