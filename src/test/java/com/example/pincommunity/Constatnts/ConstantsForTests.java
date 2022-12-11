package com.example.pincommunity.Constatnts;

import com.example.pincommunity.dto.ClubDto;
import com.example.pincommunity.dto.CreateClubDto;
import com.example.pincommunity.dto.CreateMemberDto;
import com.example.pincommunity.dto.MemberDto;
import com.example.pincommunity.models.Avatar;
import com.example.pincommunity.models.Club;
import com.example.pincommunity.models.Member;
import org.springframework.format.number.PercentStyleFormatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ConstantsForTests {

    public static final CreateMemberDto CREATE_MEMBER_DTO = new CreateMemberDto();
    public static final Member MEMBER = new Member();
    public static final ClubDto CLUB_DTO=new ClubDto();
    public static final CreateClubDto CREATE_CLUB_DTO=new CreateClubDto();
    public static final MemberDto MEMBER_DTO = new MemberDto();
    public static final Club CLUB = new Club();
    public static final Avatar AVATAR = new Avatar();
    public static final String CLUB_CITY = "BERLIN";
    public static final String EMAIL = "test@tset.ru";
    public static final String PASSWORD = "password";
    public static final String ENCODED_PASSWORD = "{bcrypt}$2a$10$XK/IbwdysgmNbJWq5lOSyuGtpm/XGrekpw3ymuraNSLKXX5EmO1HK";
    public static final String FULL_NAME = "Test";
    public static final String DATE_FORMAT = "dd-MM-yyyy";
    public static final String DATE = "10-12-2000";
    public static final LocalDate TEST_DATE = LocalDate.parse(DATE, DateTimeFormatter.ofPattern(DATE_FORMAT));
    public static final String TEST_URL = "test url";


}
