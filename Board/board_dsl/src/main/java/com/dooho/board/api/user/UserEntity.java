package com.dooho.board.api.user;

import com.dooho.board.api.auth.dto.SignUpDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;

import java.util.Objects;

@Getter
@Setter
@ToString
@Builder
@Entity(name="User")
@Table(name="User")
//Entity(name="") -> 해당 클래스를 Entity 클래스로 사용하겠다.
//Table(name="") db에 있는 해당 테이블과 클래스를 매핑시킨다.
public class UserEntity {
    @Id
    @Column(nullable = false)
    private String userEmail;
    @Column(nullable = false)
    private String userPassword;
    @Column(nullable = false)
    private String userNickname;
    @Column(nullable = false)
    private String userPhoneNumber;
    @Column(nullable = false)
    private String userAddress;
    @Column
    private String userProfile;

    public UserEntity(SignUpDto dto){
        this.userEmail = dto.userEmail();
        this.userPassword = dto.userPassword();
        this.userNickname = dto.userNickname();
        this.userPhoneNumber = dto.userPhoneNumber();
        this.userAddress = dto.userAddress();
        this.userProfile = dto.userProfile();
    }

    protected UserEntity(){

    }

    private UserEntity(String userEmail, String userPassword, String userNickname, String userPhoneNumber, String userAddress, String userProfile) {
        this.userEmail = userEmail;
        this.userPassword = userPassword;
        this.userNickname = userNickname;
        this.userPhoneNumber = userPhoneNumber;
        this.userAddress = userAddress;
        this.userProfile = userProfile;
    }

    public static UserEntity of(String userEmail, String userPassword, String userNickname, String userPhoneNumber, String userAddress, String userProfile) {
        return new UserEntity(userEmail,userPassword,userNickname,userPhoneNumber,userAddress,userProfile);
    }



    @Override
    public final boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Class<?> oEffectiveClass = o instanceof HibernateProxy ? ((HibernateProxy) o).getHibernateLazyInitializer().getPersistentClass() : o.getClass();
        Class<?> thisEffectiveClass = this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass() : this.getClass();
        if (thisEffectiveClass != oEffectiveClass) return false;
        UserEntity that = (UserEntity) o;
        return getUserEmail() != null && Objects.equals(getUserEmail(), that.getUserEmail());
    }

    @Override
    public final int hashCode() {
        return this instanceof HibernateProxy ? ((HibernateProxy) this).getHibernateLazyInitializer().getPersistentClass().hashCode() : getClass().hashCode();
    }
}
