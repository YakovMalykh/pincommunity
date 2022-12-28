package com.example.pincommunity.Constatnts;

import com.example.pincommunity.dto.*;
import com.example.pincommunity.models.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

public class ConstantsForTests {

    public static final CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto();
    public static final Member MEMBER = new Member();
    public static final Member MEMBER_2 = new Member();
    public static final ClubDto CLUB_DTO = new ClubDto();
    public static final CreateClubDto CREATE_CLUB_DTO = new CreateClubDto();
    public static final MemberDto MEMBER_DTO = new MemberDto();
    public static final MemberDto MEMBER_DTO_2 = new MemberDto();
    public static final Club CLUB = new Club();
    public static final Avatar AVATAR = new Avatar();
    public static final Picture PICTURE = new Picture();
    public static final String CLUB_CITY = "BERLIN";
    public static final String EMAIL = "test@tset.ru";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "{bcrypt}$2a$10$XK/IbwdysgmNbJWq5lOSyuGtpm/XGrekpw3ymuraNSLKXX5EmO1HK";
    public static final String FULL_NAME = "Test";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE = "10-12-2000";
    public static final LocalDate TEST_DATE = LocalDate.parse(DATE, DateTimeFormatter.ofPattern(DATE_FORMAT));
    public static final String TEST_URL = "test url";

    public static final CreatePinsetDto CREATE_PINSET_DTO = new CreatePinsetDto();
    public static final PinsetDto PINSET_DTO = new PinsetDto();
    public static final Pinset PINSET = new Pinset();
    public static final Pin PIN = new Pin();
    public static final CreatePinDto CREATE_PIN_DTO = new CreatePinDto();


    public static final MultipartFile FILE = new MultipartFile() {
        @Override
        public String getName() {
            return null;
        }

        @Override
        public String getOriginalFilename() {
            return null;
        }

        @Override
        public String getContentType() {
            return null;
        }

        @Override
        public boolean isEmpty() {
            return false;
        }

        @Override
        public long getSize() {
            return 0;
        }

        @Override
        public byte[] getBytes() throws IOException {
            return new byte[0];
        }

        @Override
        public InputStream getInputStream() throws IOException {
            return null;
        }

        @Override
        public void transferTo(File dest) throws IOException, IllegalStateException {

        }
    };
    public static final Authentication AUTHENTICATION = new Authentication() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public Object getCredentials() {
            return null;
        }

        @Override
        public Object getDetails() {
            return null;
        }

        @Override
        public Object getPrincipal() {
            return null;
        }

        @Override
        public boolean isAuthenticated() {
            return false;
        }

        @Override
        public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {

        }

        @Override
        public String getName() {
            return "test";
        }
    };

    public static final UserDetails USER_DETAILS = new UserDetails() {
        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return null;
        }

        @Override
        public String getPassword() {
            return ENCODED_PASSWORD;
        }

        @Override
        public String getUsername() {
            return null;
        }

        @Override
        public boolean isAccountNonExpired() {
            return false;
        }

        @Override
        public boolean isAccountNonLocked() {
            return false;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return false;
        }

        @Override
        public boolean isEnabled() {
            return false;
        }
    };

}
