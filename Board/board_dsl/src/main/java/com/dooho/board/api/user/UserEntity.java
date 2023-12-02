package com.dooho.board.api.user;

import com.dooho.board.api.auth.SignUpDto;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="User")
@Table(name="User")
//Entity(name="") -> 해당 클래스를 Entity 클래스로 사용하겠다.
//Table(name="") db에 있는 해당 테이블과 클래스를 매핑시킨다.
public class UserEntity {
    @Id
    private String userEmail;
    private String userPassword;
    private String userNickname;
    private String userPhoneNumber;
    private String userAddress;
    private String userProfile;

    public UserEntity(SignUpDto dto){
        this.userEmail = dto.getUserEmail();
        this.userPassword = dto.getUserPassword();
        this.userNickname = dto.getUserNickname();
        this.userPhoneNumber = dto.getUserPhoneNumber();
        this.userAddress = dto.getUserAddress();
        this.userProfile = dto.getUserProfile();
    }

}
