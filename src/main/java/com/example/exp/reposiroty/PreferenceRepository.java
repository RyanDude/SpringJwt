package com.example.exp.reposiroty;

import com.example.exp.entity.Preference;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface PreferenceRepository extends JpaRepository<Preference,Long>, PagingAndSortingRepository<Preference, Long> {
    Page<Preference> findByUser_id(long id, Pageable pageable);

}
