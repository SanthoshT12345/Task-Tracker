package com.santhosh.Todo.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.santhosh.Todo.entity.LearningGoal;
import com.santhosh.Todo.entity.LearningSession;
import com.santhosh.Todo.entity.User;

@Repository
public interface LearningSessionRepository extends JpaRepository<LearningSession, Long> {

    List<LearningSession> findByUserOrderBySessionDateDescCreatedAtDesc(User user);

    List<LearningSession> findByGoal(LearningGoal goal);

    @Query("SELECT COALESCE(SUM(s.durationMinutes), 0) FROM LearningSession s WHERE s.goal = :goal")
    Long sumDurationByGoal(@Param("goal") LearningGoal goal);

    @Query("SELECT COALESCE(SUM(s.durationMinutes), 0) FROM LearningSession s WHERE s.user = :user")
    Long sumDurationByUser(@Param("user") User user);

    @Query("SELECT COALESCE(SUM(s.durationMinutes), 0) FROM LearningSession s WHERE s.user = :user AND s.sessionDate = :date")
    Long sumDurationByUserAndSessionDate(@Param("user") User user, @Param("date") LocalDate date);

    List<LearningSession> findByUserAndSessionDateBetween(User user, LocalDate start, LocalDate end);

    List<LearningSession> findByUser(User user);
}
