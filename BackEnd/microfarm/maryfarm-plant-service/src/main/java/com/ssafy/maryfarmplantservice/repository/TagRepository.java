package com.ssafy.maryfarmplantservice.repository;

import com.ssafy.maryfarmplantservice.domain.tag.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, String> {

}
