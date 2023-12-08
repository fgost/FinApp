package dsw.finApp.v1.accesGroup.mapper;

import dsw.finApp.v1.accesGroup.domain.AccessGroupEntity;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponse;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseSummary;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseWithPermissions;
import dsw.finApp.v1.accesGroup.model.response.AccessGroupResponseWithProfiles;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.Collection;

@Mapper
public interface AccessGroupResponseMapper {

    AccessGroupResponseMapper INSTANCE = Mappers.getMapper(AccessGroupResponseMapper.class);

    AccessGroupResponseSummary mapEntityToResponseSummary(AccessGroupEntity entity);

    Collection<AccessGroupResponseSummary> mapEntityToResponseSummaryCollection(Collection<AccessGroupEntity> entities);

    AccessGroupResponseWithPermissions mapEntityToResponseWithPermissions(AccessGroupEntity entity);


    AccessGroupResponseWithProfiles mapEntityToModel (AccessGroupEntity entity);

    AccessGroupResponse mapEntityToResponse (AccessGroupEntity entity);



    Collection<AccessGroupResponseWithProfiles> mapEntityToModelCollection (Collection<AccessGroupEntity> entities);

    Collection<AccessGroupResponseWithPermissions> mapEntityToResponseWithPermissionsCollection(Collection<AccessGroupEntity> entities);
}
