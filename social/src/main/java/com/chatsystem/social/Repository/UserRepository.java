package com.chatsystem.social.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.chatsystem.social.Entity.User;

public interface UserRepository extends MongoRepository<User, String> {

}