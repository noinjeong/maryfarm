package com.ssafy.myfarm.repository;

import com.ssafy.myfarm.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

}
