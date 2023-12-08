package dsw.finApp.v1.profiles.mapper;

import dsw.finApp.v1.profiles.domain.ProfileEntity;
import dsw.finApp.v1.profiles.model.response.ProfileResponse;
import dsw.finApp.v1.profiles.model.response.ProfileResponseSummary;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroups;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroupsAndPermissions;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface ProfileResponseMapper {
    ProfileResponseMapper INSTANCE = Mappers.getMapper(ProfileResponseMapper.class);

    ProfileResponseSummary mapEntityToResponseSummary(ProfileEntity entity);

    Collection<ProfileResponseSummary> mapEntityToResponseSummaryCollection(Collection<ProfileEntity> entities);

    ProfileResponseWithAccessGroups mapEntityToResponseWithAccessGroups(ProfileEntity entity);

    ProfileResponse mapEntityToModel(ProfileEntity entity);

    ProfileResponseWithAccessGroupsAndPermissions mapEntityToModelSummary (ProfileEntity entity);

    Collection<ProfileResponseWithAccessGroups> mapEntityToModelWithAccessGroupsCollection(Collection<ProfileEntity> entities);
}
