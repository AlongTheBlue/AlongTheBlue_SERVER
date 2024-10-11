package org.alongtheblue.alongtheblue_server.global.data.weather;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JejuDivisionService {

    private final JejuDivisonRepository jejuDivisionRepository;

    @Transactional
    public void saveJejuDivision() {
        List<JejuDivision> divisions = Arrays.asList(
                new JejuDivision("5011000000", "제주특별자치도", "제주시", "", "53", "38"),
                new JejuDivision("5011025000", "제주특별자치도", "제주시", "한림읍", "48", "36"),
                new JejuDivision("5011025300", "제주특별자치도", "제주시", "애월읍", "49", "37"),
                new JejuDivision("5011025600", "제주특별자치도", "제주시", "구좌읍", "59", "38"),
                new JejuDivision("5011025900", "제주특별자치도", "제주시", "조천읍", "55", "39"),
                new JejuDivision("5011031000", "제주특별자치도", "제주시", "한경면", "46", "35"),
                new JejuDivision("5011032000", "제주특별자치도", "제주시", "추자면", "48", "48"),
                new JejuDivision("5011033000", "제주특별자치도", "제주시", "우도면", "60", "38"),
                new JejuDivision("5011051000", "제주특별자치도", "제주시", "일도1동", "53", "38"),
                new JejuDivision("5011052000", "제주특별자치도", "제주시", "일도2동", "53", "38"),
                new JejuDivision("5011053000", "제주특별자치도", "제주시", "이도1동", "53", "38"),
                new JejuDivision("5011054000", "제주특별자치도", "제주시", "이도2동", "53", "38"),
                new JejuDivision("5011055000", "제주특별자치도", "제주시", "삼도1동", "53", "38"),
                new JejuDivision("5011056000", "제주특별자치도", "제주시", "삼도2동", "53", "38"),
                new JejuDivision("5011057000", "제주특별자치도", "제주시", "용담1동", "52", "38"),
                new JejuDivision("5011058000", "제주특별자치도", "제주시", "용담2동", "52", "38"),
                new JejuDivision("5011059000", "제주특별자치도", "제주시", "건입동", "53", "38"),
                new JejuDivision("5011060000", "제주특별자치도", "제주시", "화북동", "53", "38"),
                new JejuDivision("5011061000", "제주특별자치도", "제주시", "삼양동", "54", "38"),
                new JejuDivision("5011062000", "제주특별자치도", "제주시", "봉개동", "54", "38"),
                new JejuDivision("5011063000", "제주특별자치도", "제주시", "아라동", "53", "37"),
                new JejuDivision("5011064000", "제주특별자치도", "제주시", "오라동", "52", "38"),
                new JejuDivision("5011065000", "제주특별자치도", "제주시", "연동", "52", "38"),
                new JejuDivision("5011066000", "제주특별자치도", "제주시", "노형동", "52", "38"),
                new JejuDivision("5011067000", "제주특별자치도", "제주시", "외도동", "51", "38"),
                new JejuDivision("5011068000", "제주특별자치도", "제주시", "이호동", "51", "38"),
                new JejuDivision("5011069000", "제주특별자치도", "제주시", "도두동", "52", "38"),
                new JejuDivision("5013000000", "제주특별자치도", "서귀포시", "", "52", "33"),
                new JejuDivision("5013025000", "제주특별자치도", "서귀포시", "대정읍/마라도포함", "48", "32"),
                new JejuDivision("5013025300", "제주특별자치도", "서귀포시", "남원읍", "56", "33"),
                new JejuDivision("5013025900", "제주특별자치도", "서귀포시", "성산읍", "60", "37"),
                new JejuDivision("5013031000", "제주특별자치도", "서귀포시", "안덕면", "49", "32"),
                new JejuDivision("5013032000", "제주특별자치도", "서귀포시", "표선면", "58", "34"),
                new JejuDivision("5013051000", "제주특별자치도", "서귀포시", "송산동", "53", "32"),
                new JejuDivision("5013052000", "제주특별자치도", "서귀포시", "정방동", "53", "32"),
                new JejuDivision("5013053000", "제주특별자치도", "서귀포시", "중앙동", "53", "32"),
                new JejuDivision("5013054000", "제주특별자치도", "서귀포시", "천지동", "53", "32"),
                new JejuDivision("5013055000", "제주특별자치도", "서귀포시", "효돈동", "54", "33"),
                new JejuDivision("5013056000", "제주특별자치도", "서귀포시", "영천동", "54", "33"),
                new JejuDivision("5013057000", "제주특별자치도", "서귀포시", "동홍동", "53", "33"),
                new JejuDivision("5013058000", "제주특별자치도", "서귀포시", "서홍동", "53", "33"),
                new JejuDivision("5013059000", "제주특별자치도", "서귀포시", "대륜동", "52", "32"),
                new JejuDivision("5013060000", "제주특별자치도", "서귀포시", "대천동", "52", "32"),
                new JejuDivision("5013061000", "제주특별자치도", "서귀포시", "중문동", "51", "32"),
                new JejuDivision("5013062000", "제주특별자치도", "서귀포시", "예래동", "50", "32")
        );

        jejuDivisionRepository.saveAll(divisions);
    }
}
