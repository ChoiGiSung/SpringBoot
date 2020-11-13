package study.datajpa.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(of = {"id","username","age"}) //연관관계는 toString x
public class Member extends JpaBaseEntity{

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;
    private String username;
    private int age;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id")
    private Team team;

//    protected Member() {
//    }

    public Member( String username) {
        this.username = username;
    }

    public Member(String username, int age, Team team) {
        this.username=username;
        this.age=age;
        if(team != null){
            changeTeam(team);
        }
    }

    public Member(String name, int age) {
        this.username=name;
        this.age=age;
    }

    //연관관계 편의 메소드
    public void changeTeam(Team team){
        this.team=team;
        team.getMembers().add(this);
    }
    public void changeName(String name){
        this.username=name;
    }

}
