package com.myapp.backend.repository;

<<<<<<< HEAD
import com.myapp.backend.domain.entity.KinderClass;
=======
import com.myapp.backend.domain.kinder.KinderClass;
>>>>>>> 7d9acd6d19de5ff401d4a1f9a90e178c6c527783
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface KinderRepository extends JpaRepository<KinderClass, String> {

    KinderClass findByClassCode(String classCode);
}
