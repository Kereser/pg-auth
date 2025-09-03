package co.com.crediya.auth.r2dbc.user;

import co.com.crediya.auth.model.user.dto.FindAllUsersFiltersCommand;
import co.com.crediya.auth.r2dbc.user.entity.UserEntity;
import reactor.core.publisher.Flux;

public interface UserTemplateQueryRepository {

  Flux<UserEntity> findAllFiltered(FindAllUsersFiltersCommand command);
}
