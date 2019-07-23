package com.ua.repository;

import com.ua.domain.Message;
import org.hibernate.validator.constraints.ParameterScriptAssert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.*;

public interface MessageRepository extends JpaRepository<Message, Long> {
  List<Message> findByTag(String tag);
}
