package co.com.crediya.auth.r2dbc.user;

import org.springframework.data.r2dbc.core.R2dbcEntityTemplate;
import org.springframework.data.relational.core.query.Criteria;
import org.springframework.data.relational.core.query.Query;
import org.springframework.stereotype.Repository;

import co.com.crediya.auth.model.user.dto.FindAllUsersFiltersCommand;
import co.com.crediya.auth.r2dbc.user.entity.UserEntity;
import lombok.RequiredArgsConstructor;
import reactor.core.publisher.Flux;

@Repository
@RequiredArgsConstructor
public class UserTemplateQueryRepositoryImpl implements UserTemplateQueryRepository {

  private final R2dbcEntityTemplate entityTemplate;

  static class DbFields {
    private DbFields() {}

    private static final String USER_ID = "user_id";
  }

  @Override
  public Flux<UserEntity> findAllFiltered(FindAllUsersFiltersCommand command) {
    Criteria criteria = buildCriteria(command);

    Query query = Query.query(criteria);

    return entityTemplate.select(query, UserEntity.class);
  }

  private Criteria buildCriteria(FindAllUsersFiltersCommand command) {
    Criteria criteria = Criteria.empty();

    if (command.getUserIds() != null) {
      criteria = criteria.and(DbFields.USER_ID).in(command.getUserIds());
    }

    return criteria;
  }
}
