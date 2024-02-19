package org.choongang.commons.repositories;


import org.choongang.commons.entities.EmailAuth;
import org.springframework.data.repository.CrudRepository;

public interface EmailAuthRedisRepository extends CrudRepository<EmailAuth, Long> {

}
