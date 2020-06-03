package com.fsd.stockmarket.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.fsd.stockmarket.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	User findByUsername(String username);

	@Modifying
	@Transactional
	@Query("update User u set u.confirmed = :confirmed where u.username=:username")
	int saveUserByUsernameAndConfirmed(@Param("username") String username, @Param("confirmed") boolean confirmed);

}
