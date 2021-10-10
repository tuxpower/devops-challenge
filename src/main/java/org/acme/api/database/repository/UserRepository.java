package org.acme.api.database.repository;

import javax.enterprise.context.ApplicationScoped;

import org.acme.api.database.entity.UserEntity;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

/**
 * User Repository.
 */
@ApplicationScoped
public class UserRepository implements PanacheRepositoryBase<UserEntity, String> {

}
