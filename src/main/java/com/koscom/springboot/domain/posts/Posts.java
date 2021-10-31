package com.koscom.springboot.domain.posts;

import com.koscom.springboot.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
// Setter는 왜 없는가? 이전에는 서비스에서 로직을, 도메인은 데이터를 가지고 있었는데 요즘 개발 트렌드는 TDA(Tell, don't ask)라고 해서 도메인이 로직과 데이터를 전부 가짐! 서비스는 요청만!
// 객체지향 프로그래밍을 따른다고 볼 수 있음 -> 왜 why? -> 객체의 상태와 행위를 한 곳에서 관리하고 있는 거니까~
// 그렇다면, 어떻게 값을 DB에 insert 하나?? -> 생성자가 전부 한다 -> Builder
@NoArgsConstructor // 디폴트 생성자 만들어 줌, JPA는 거의 디폴트 생성자를 기반으로 값을 넣어주는 거라서 디폴트 생성자가 필수임.
                    //  원래도 디폴트 생성자는 있지만 얘 없이 다른 생성자 만들면 디폴트 생성자 사라짐!
@Entity // JPA에서 지원하는 Entity 써야함! deprecated 된거 쓰면 안됨!
public class Posts extends BaseTimeEntity {

    @Id // pk
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id 채번 방식, IDENTITY가 Auto Increment 방식임.
    private Long id; // pk

    // @Column 어노테이션 없어도 됨.
    // @Column 없으면 varchar(255), nullable = true가 디폴트임.
    @Column(length = 500, nullable = false) // varchar(500), not null
    private String title;

    @Column(length = 2000, nullable = false)
    private String content;

    private String author;

    @Builder // lombok의 Builder, Builder를 쓰면 어느 자리에 어느 값을 넣는건지 명확히 할 수 있음.
    public Posts(String title, String content, String author) { // id 빼고 나머지 필드만 있는 생성자
        this.title = title;
        this.content = content;
        this.author = author;
    }

    // 수정 기능
    // title, content만 수정 가능(author는 수정 안됨)
    // 수정일자도 새로 표시
    public void update(String title, String content) {
        this.title = title;
        this.content = content;
    }
}
