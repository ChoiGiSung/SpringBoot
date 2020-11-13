package study.datajpa.entity;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.time.LocalDateTime;

@MappedSuperclass //내용만 상속해 진짜 상속은 아니고 ㅎㅎ
public class JpaBaseEntity {
    //테이블 마다 생성일 수정일을
    //만드는게 좋다
    //그래서 그거를 관리하는 법을 알아보자

    @Column(updatable = false)
    private LocalDateTime createDate;
    private LocalDateTime updatedDate;


    @PrePersist //등록하기 전에 동작
    public void prePersist(){
        LocalDateTime now=LocalDateTime.now();
        createDate =now;
        updatedDate=now;
    }

    @PreUpdate //업데이트 전에 동작
    public void preUpdate(){
        updatedDate=LocalDateTime.now();
    }
    
}
