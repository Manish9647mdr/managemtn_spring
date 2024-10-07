package com.tkk.projectmgtsystem.repository;

import com.tkk.projectmgtsystem.modal.Project;
import com.tkk.projectmgtsystem.modal.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    // 特定のユーザーが所有するプロジェクトを取得
    List<Project> findByOwner(User user);

    // 部分一致でプロジェクト名とチームメンバーでフィルタリングしたプロジェクトを取得
    List<Project> findByNameContainingAndTeamContains(String partialName, User user);

//    // 特定のユーザーがチームメンバーとして参加しているプロジェクトを取得
//    @Query("SELECT p FROM Project p JOIN p.team t Where t=:user")
//    List<Project> findProjectByTeam(@Param("user") User user);

    List<Project> findByTeamContainingOrOwner(User user, User owner);
}
