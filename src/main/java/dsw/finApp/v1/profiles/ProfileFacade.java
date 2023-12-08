package dsw.finApp.v1.profiles;

import dsw.finApp.v1.profiles.service.ProfileService;
import dsw.finApp.utils.dto.OnlyCodeDto;
import dsw.finApp.utils.pagination.DefaultWrapper;
import dsw.finApp.utils.pagination.PaginationRequest;
import dsw.finApp.utils.pagination.PaginationUtils;
import dsw.finApp.v1.profiles.mapper.ProfileRequestMapper;
import dsw.finApp.v1.profiles.mapper.ProfileResponseMapper;
import dsw.finApp.v1.profiles.model.request.ProfileRequestInput;
import dsw.finApp.v1.profiles.model.response.ProfileResponseSummary;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroups;
import dsw.finApp.v1.profiles.model.response.ProfileResponseWithAccessGroupsAndPermissions;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class ProfileFacade {

    private final ProfileService service;
    private final PaginationUtils paginationUtils;

    public ProfileResponseSummary insert(ProfileRequestInput input) {
        var entity = ProfileRequestMapper.INSTANCE.mapModelToEntity(input);
        var savedEntity = service.insert(entity);
        var dto = ProfileResponseMapper.INSTANCE.mapEntityToResponseSummary(savedEntity);
        return dto;
    }

    public ProfileResponseWithAccessGroups update(String code, ProfileRequestInput input) {
        var entity = ProfileRequestMapper.INSTANCE.mapModelToEntity(input);
        var updatedEntity = service.update(code, entity);
        var dto = ProfileResponseMapper.INSTANCE.mapEntityToResponseWithAccessGroups(updatedEntity);
        return dto;
    }

    public DefaultWrapper findAll(PaginationRequest paginationRequest, String name) {
        Pageable pageable = paginationUtils.getPageableWithUserPreferencesSupport(paginationRequest);
        var entities = service.findAll(pageable, name);
        var dtos = ProfileResponseMapper.INSTANCE.mapEntityToModelWithAccessGroupsCollection(entities.getContent());
        var page = PaginationUtils.pageOf(List.copyOf(dtos), pageable, entities.getTotalElements());
        var wrapper = new DefaultWrapper(page);
        return wrapper;
    }

    public ProfileResponseWithAccessGroupsAndPermissions profileResponseWithAccessGroupsAndPermissions(String code){
        var profileById = service.findByCode(code);
        var response = ProfileResponseMapper.INSTANCE.mapEntityToModelSummary(profileById);
        return response;
    }

    public ProfileResponseWithAccessGroupsAndPermissions updateProfileAccessGroups(String code, List<OnlyCodeDto> accessGroups) {
        var savedEntity = service.updateProfileAccessGroups(code, accessGroups);
        var dto = ProfileResponseMapper.INSTANCE.mapEntityToModelSummary(savedEntity);
        return dto;
    }

    public void deleteByCode(String code) {
        service.deleteByCode(code);
    }
}
