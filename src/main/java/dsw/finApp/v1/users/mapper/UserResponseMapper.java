package dsw.finApp.v1.users.mapper;

import dsw.finApp.v1.storage.StorageService;
import dsw.finApp.v1.users.model.response.*;
import dsw.finApp.v1.users.model.response.permissions.UserResponsePermissionWrapper;
import dsw.finApp.v1.users.domain.UserEntity;
import dsw.finApp.v1.users.domain.UserPhotoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserResponseMapper {

    UserResponseMapper INSTANCE = Mappers.getMapper(UserResponseMapper.class);

    @Mapping(target = "nomeCompleto", expression = "java(entity.getNomeCompleto())")
    UserResponseSummary mapEntityToResponse(UserEntity entity);

    @Mapping(target = "fullName", expression = "java(entity.getNomeCompleto())")
    UserResponseProfile mapEntityToResponseProfile(UserEntity entity);

    @Mapping(target = "fullName", expression = "java(entity.getNomeCompleto())")
    UserResponsePreference mapEntityToPreference(UserEntity entity);


    @Mapping(target = "fullName", expression = "java(entity.getNomeCompleto())")
    @Mapping(target = "expense.expenseType", expression = "java(entity.getExpense().getExpenseType())")
    UserResponseExpense mapEntityToExpense(UserEntity entity);

    UserResponsePermissionWrapper mapEntityToPermission(UserEntity entity);

    UserResponse mapEntityToUserResponse(UserEntity entity);

    StorageService.File mapPhotoEntityToModel(UserPhotoEntity entity);

    //UsersResponseMapper mapResponseMapperToModelO(UserEntity entity);
}
