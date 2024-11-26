package com.andy.enigmask.repository;

import com.andy.enigmask.model.Status;
import com.andy.enigmask.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    List<User> findAllByStatus(Status status);
}
