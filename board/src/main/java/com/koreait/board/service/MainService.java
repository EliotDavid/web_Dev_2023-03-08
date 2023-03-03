package com.koreait.board.service;

import java.util.List;

import org.apache.catalina.connector.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.koreait.board.dto.GetTestResponseDto;
import com.koreait.board.dto.PostTestRequestDto;
import com.koreait.board.dto.ResponseDto;
import com.koreait.board.entity.ExampleEntity;
import com.koreait.board.repository.ExampleRepository;

//# Service 
//? 실제 비즈니스 로직을 담당하는 레이어
//? 일반적인 연산작업
//? Controller로 사용자의 입력을 받아오면 해당 기능을 진행하기 위해
//? Repository에서 데이터를 가져와 작업을 진행함
@Service
public class MainService {

    @Autowired
    private ExampleRepository exampleRepository;
    
    public ResponseDto<String> getMain() {

        ExampleEntity exampleEntity = 
          ExampleEntity
          .builder()
          .comment("Hello")
          .number(10)
          .build();
        exampleRepository.save(exampleEntity);

        ResponseDto<String> result = ResponseDto.setSuccess("success", "Hello World!");
        return result;
    }

    public ResponseDto<String> getVariable(String data) {
        System.out.println(data);
        ExampleEntity exampleEntity = exampleRepository.findByComment(data);
        String string = exampleEntity.toString();

        ResponseDto<String> result = ResponseDto.setSuccess("success", string);
        return result;
    }

    public ResponseDto<String> postMain() {
        ResponseDto<String> result = ResponseDto.setSuccess("success", "POST main Response!");
        return result;
    }

    public ResponseDto<String> postRequestBody(String data) {
        String string = "Post body data is '" + data + "'";
        ResponseDto<String> result = ResponseDto.setSuccess("success", string);
        return result;
    }

    public ResponseDto<String> patchMain() {
        String string = 
          "Patch 메서드는 수정 작업을 지정한 메서드입니다. 클라이언트로부터 데이터를 받을 땐 request body로 받습니다.";
        ResponseDto<String> result = ResponseDto.setSuccess("success", string);
        return result;
    }

    public ResponseDto<String> deleteMain() {
        String string = 
          "Delete 메서드는 삭제 작업을 지정한 메서드입니다. 클라이언트로부터 데이터를 받을 땐 path variable로 받습니다.";
        ResponseDto<String> result = ResponseDto.setSuccess("success", string);
        return result;
    }

    public ResponseDto<String> postTest(PostTestRequestDto dto) {
        String string = dto.toString();
        ResponseDto<String> result = ResponseDto.setSuccess("success", string);
        return result;
    }

    public ResponseDto<GetTestResponseDto> getTest() {
        GetTestResponseDto data = new GetTestResponseDto(10, "Comment");
        ResponseDto<GetTestResponseDto> result = ResponseDto.setSuccess("success", data);
        return result;
    }

}