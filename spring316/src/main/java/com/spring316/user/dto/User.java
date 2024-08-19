package com.spring316.user.dto;

import com.spring316.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
@ToString
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer no;

    @Column(unique = true)
    private String id;          //회원 아이디

    @Column(nullable = false)
    private String pw;          //비밀번호

    @Column(nullable = false)
    private String name;        //회원 이름

    @Column(nullable = false)
    private String tel;         //전화번호

    @Column(nullable = false)
    private String email;       //이메일

    @Column(nullable = false)
    private String addr1;       //주소1

    @Column(nullable = true)
    private String addr2;       //주소2

    @Column(nullable = true)
    private String postcode;    //주소번호

    @CreatedDate
    private LocalDateTime loginAt;  //최종 로그인시간

    @Enumerated(EnumType.STRING)
    private Status status;      //회원 활동상태

    @Enumerated(EnumType.STRING)
    private Role role;          //회원 권한

    public void change(UserDto userDTO) {
        this.tel = userDTO.getTel();
        this.addr1 = userDTO.getAddr1();
        this.addr2 = userDTO.getAddr2();
        this.postcode = userDTO.getPostcode();
    }

    public void emailUpdate(UserDto dto) {
        this.email = dto.getEmail();
    }

    public void stateUpdate(UserDto dto) {
        this.status = dto.getStatus();
    }

    public void roleUpdate(UserDto dto) {
        this.role = dto.getRole();
    }

}